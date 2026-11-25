package at.bernhardberger.tvheadend.htsp.messages

import at.bernhardberger.tvheadend.htsp.model.HtspAutorecEntry
import at.bernhardberger.tvheadend.htsp.model.HtspDvrEntry
import at.bernhardberger.tvheadend.htsp.model.HtspTimerecEntry
import at.bernhardberger.tvheadend.htsp.wire.requireU32

/** Full recording add (pinned htsp_server.c:963–1211). */
public data class HtspDvrEntryAddMessage(
    /** Complete recording snapshot. */
    public val entry: HtspDvrEntry,
) : HtspMetadataMessage

/** Full recording update; inserts unseen identities or replaces the snapshot (pinned htsp_server.c:963–1211). */
public data class HtspDvrEntryUpdateMessage(
    /** Complete recording snapshot; absent optional fields clear previous values. */
    public val entry: HtspDvrEntry,
) : HtspMetadataMessage

/** Statistics-only recording update from htsp_build_dvrentry(statsonly=1), :976–979,1166–1209. */
public data class HtspDvrEntryStatsMessage(
    /** Unsigned recording identity (:976). */
    public val entryId: Long,
    /** Recording UUID, required on the wire at v41+ (:977). */
    public val entryUuid: String?,
    /** Always-emitted recording state (:1199). */
    public val state: String,
    /** Only emitted for upcoming recordings (:1196–1197). */
    public val duplicate: Boolean? = null,
    /** Recording error when present (:1200–1201). */
    public val error: String? = null,
    /** Subscription error when present (:1202–1203). */
    public val subscriptionError: String? = null,
    /** Stream errors, only when nonzero (:1204–1205). */
    public val streamErrors: Long? = null,
    /** Data errors, only when nonzero (:1206–1207). */
    public val dataErrors: Long? = null,
    /** Known file size for recording or completed entries (:1172,1182,1208–1209). */
    public val dataSizeBytes: Long? = null,
) : HtspMetadataMessage {
    init {
        requireU32("entryId", entryId)
        streamErrors?.let { requireU32("streamErrors", it) }
        dataErrors?.let { requireU32("dataErrors", it) }
    }
    override fun toString(): String = "HtspDvrEntryStatsMessage(<redacted>)"
}

/** Identifies a deleted recording. */
public data class HtspDvrEntryDeleteMessage(
    /** Unsigned recording identity. */
    public val entryId: Long,
) : HtspMetadataMessage {
    init { requireU32("entryId", entryId) }
    override fun toString(): String = "HtspDvrEntryDeleteMessage(<redacted>)"
}

/** Full automatic-rule add (pinned htsp_server.c:1218–1277). */
public data class HtspAutorecEntryAddMessage(
    /** Complete automatic-rule snapshot. */
    public val entry: HtspAutorecEntry,
) : HtspMetadataMessage

/** Full automatic-rule update; inserts unseen identities or replaces the snapshot (pinned htsp_server.c:1218–1277). */
public data class HtspAutorecEntryUpdateMessage(
    /** Complete automatic-rule snapshot. */
    public val entry: HtspAutorecEntry,
) : HtspMetadataMessage

/** Identifies a deleted automatic recording rule. */
public data class HtspAutorecEntryDeleteMessage(
    /** Rule UUID. */
    public val id: String,
) : HtspMetadataMessage {
    override fun toString(): String = "HtspAutorecEntryDeleteMessage(<redacted>)"
}

/** Full time-rule add (pinned htsp_server.c:1284–1320). */
public data class HtspTimerecEntryAddMessage(
    /** Complete time-rule snapshot. */
    public val entry: HtspTimerecEntry,
) : HtspMetadataMessage

/** Full time-rule update; inserts unseen identities or replaces the snapshot (pinned htsp_server.c:1284–1320). */
public data class HtspTimerecEntryUpdateMessage(
    /** Complete time-rule snapshot. */
    public val entry: HtspTimerecEntry,
) : HtspMetadataMessage

/** Identifies a deleted time recording rule. */
public data class HtspTimerecEntryDeleteMessage(
    /** Rule UUID. */
    public val id: String,
) : HtspMetadataMessage {
    override fun toString(): String = "HtspTimerecEntryDeleteMessage(<redacted>)"
}
