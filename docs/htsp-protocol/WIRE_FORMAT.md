# HTSP binary wire format

The golden frame fixtures under `src/test/resources/htsp-wire/` pin complete
example frames for the codec tests. The codec's deliberate differences from the
pinned Tvheadend server are described below.

Each `.hex` file holds one complete frame. Whitespace separates two-digit
hexadecimal bytes, and `#` starts a comment that runs to the end of the line.
The test loader rejects any other token.

`scalar-types.hex` is decoded and then re-encoded to the same bytes in
`HtspGoldenCorpusTest`; `hello.hex` is a plain follow-on frame for the
malformed-frame catalog.

The `scripted-*-response.hex` fixtures are deterministic server replies used by
the public socket-factory integration test. They drive `hello`, anonymous
`authenticate`, and `getSysTime` in request order without opening a network
connection.

## Frame layout

A frame starts with a four-byte unsigned big-endian body length, followed by
that many body bytes. A map or list body is a sequence of fields. Each field
contains:

1. a one-byte type ID;
2. a one-byte UTF-8 name length (zero for nameless list members);
3. a four-byte unsigned big-endian data length;
4. the name bytes; and
5. the data bytes.

Nested MAP and LIST data recursively uses the same field sequence, without
another four-byte root prefix.

| ID | Local type | Pinned serializer |
|---:|---|---|
| 1 | MAP | yes |
| 2 | S64 | yes |
| 3 | STR (UTF-8 bytes) | yes |
| 4 | BIN | yes |
| 5 | LIST | yes |
| 6 | Unknown (raw bytes on decode only) | **no** |
| 7 | BOOL | yes |
| 8 | UUID (16 bytes) | yes |

The binary format has no double type. Although `htsmsg.h` defines `HMF_DBL=6`,
the pinned `htsmsg_binary_des0` has no decode case and rejects it; the
`htsmsg_binary_write` length switch falls through to `default: abort()`.
The local decoder treats type 6 like any other unknown type, preserving raw
bytes, and the encoder rejects Double/Float values as unsupported.
`scalar-types.hex` pins the supported scalar IDs and re-encodes them locally.

## Signed 64-bit values

S64 data is little-endian and variable length. Zero has data length zero.
Positive values use the shortest byte sequence that retains all nonzero bits.
Negative values from the local encoder use all eight little-endian bytes. These
rules match the unsigned-bit-pattern loop in pinned `htsmsg_binary_count` and
the byte loop in `htsmsg_binary_write`; `htsmsg_binary_des0` rebuilds the value
from the last data byte toward the first.

Zero-length S64 decodes as `0L` on both implementations. S64 payloads longer
than eight bytes keep their low eight little-endian bytes on both; the local
tests also pin following-field alignment in
`HtspMalformedFrameCatalogTest.signed64LongerThanEight_usesLowEightLittleEndianBytesAndPreservesAlignment`.

## Booleans

BOOL decodes true only when its data length is exactly one and that byte is
nonzero. Every other length decodes false, consuming the entire declared field
(`src/htsmsg_binary.c:133–135,146–147`).
`HtspMalformedFrameCatalogTest.booleanLengths_followUpstreamAndPreserveFollowingFieldAndFrameAlignment`
pins lengths zero, one (0x00, 0x01, 0xFF), and greater than one.

The pinned serializer normally emits false with data length zero, which the
length-zero case above decodes as `false`. The local encoder emits false as one
zero byte (`HtspCodecEncodingTest.localFalse_hasOneByteAndEmptyBinaryHasNone`).

## Where the local codec is more lenient

The local codec is intentionally more permissive than the pinned server for
forward compatibility with other Tvheadend builds. The draining described
below applies only inside a scalar field's declared data slice;
residue at the root or inside a container is parsed as another field, never
silently drained.

| Input | Local behavior | Pinned server | Classification | Pinning test |
|---|---|---|---|---|
| Unknown type ID | Preserve the complete field data as a raw `ByteArray`. | Frees the field and rejects the whole message. | Local leniency diverging from pin. | `HtspMalformedFrameCatalogTest.typeSix_decodesLikeUnknownTypeAndPreservesFollowingFieldAlignment` |
| Type 6, including eight-byte data | Preserve the complete field data as a raw `ByteArray`, like other unknown types. | No decode case: rejects every type 6; serializer has no case and aborts. | Unknown-type leniency, not a supported double type. | `HtspMalformedFrameCatalogTest.typeSix_decodesLikeUnknownTypeAndPreservesFollowingFieldAlignment` |
| One to five residual bytes after the last complete root/container field | Attempts another field and rejects; residual root or container bytes report `HtspFramingException` with `field byte exceeds enclosing frame`. | Rejects when the decode loop returns with any residue. | Both reject; the local failure taxonomy differs. | `HtspCodecTest.shortRootAndNestedHeadersPreserveFramingOffsetsAndPhysicalEofPriority` |

## Malformed and truncated input

`HtspFramingException` carries the local failure taxonomy and byte offset.
Physical EOF while reading a declared header, name, or data region remains
`EOFException`.

| Condition | Local outcome | Pinned server | Pinning test |
|---|---|---|---|
| Root length zero or greater than 32 MiB | `HtspFramingException` with `invalid root length` at byte offset 0. | Local safety bound and taxonomy; not asserted as pinned-server behavior. | `HtspMalformedFrameCatalogTest.rootLengthZero_isInvalidRootFramingFailure`; `HtspCodecTest.maximumRootLengthReadsBinaryDirectlyAndOversizeNeverReadsBody` |
| EOF inside the four-byte root header, a declared field name, or declared field data | `EOFException`. | Physical truncation is rejected by both; `EOFException` is local taxonomy. | `HtspMalformedFrameCatalogTest.eofMidRootHeader_isEof`; `HtspCodecTest.bulkReadsKeepRegionSpecificEofMessages` |
| Field name plus data exceeds its enclosing root or container bound | `HtspFramingException` with `field name and data exceed enclosing frame`. | Rejects an over-bound field. | `HtspCodecTest.invalidFieldLengthIsRejectedBeforeAttemptingItsMissingNameOrBody`; `HtspMalformedFrameCatalogTest.fieldNameAndDataBeyondEnclosingContainer_isFramingFailure` |
| Nesting beyond 32 containers | `HtspFramingException` with `nesting exceeds limit`; the 32-container boundary remains parseable. | The depth limit and taxonomy are local safety policy. | `HtspMalformedFrameCatalogTest.nestingBeyond32_isFramingFailure`; `nestingAt32_remainsParseable` |

## Pinned source

The fixtures were derived by hand from Tvheadend `src/htsmsg_binary.c` at
revision `51ec612fa22c4c78b4697e4c262c943da9cbd6cc`, Git blob
`48a1bf985ed554df473adb3a9251b479dfcdaf26`. The governing functions are
`htsmsg_binary_deserialize`, `htsmsg_binary_des0`, `htsmsg_binary_count`,
`htsmsg_binary_write`, and `htsmsg_binary_serialize`. The exact immutable
source is at
<https://github.com/tvheadend/tvheadend/blob/51ec612fa22c4c78b4697e4c262c943da9cbd6cc/src/htsmsg_binary.c>.
