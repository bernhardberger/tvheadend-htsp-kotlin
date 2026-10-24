package at.bernhardberger.tvheadend.htsp.model

import at.bernhardberger.tvheadend.htsp.wire.immutableSnapshot
import at.bernhardberger.tvheadend.htsp.wire.requireU32

/** Full recording snapshot (pinned htsp_server.c:963–1200); null properties are not set. */
@ConsistentCopyVisibility
public data class HtspDvrEntry private constructor(
    /** Unsigned recording identity (:975). */
    public val entryId: Long,
    /** UUID introduced in v41; required on the wire at v41+ (:976). */
    public val entryUuid: String?,
    /** Always-emitted enable flag (:979). */
    public val enabled: Boolean,
    /** Linked channel, absent after channel deletion (:980–981). */
    public val channelId: Long?,
    /** Saved channel name, when available (:982–983). */
    public val channelName: String?,
    /** Linked broadcast identity (:985–986). */
    public val eventId: Long?,
    /** Linked automatic rule UUID (:988–989). */
    public val autorecEntryUuid: String?,
    /** Linked time rule UUID (:991–992). */
    public val timerecEntryUuid: String?,
    /** Scheduled start in epoch seconds (:994). */
    public val startEpochSeconds: Long,
    /** Scheduled stop in epoch seconds (:995). */
    public val stopEpochSeconds: Long,
    /** Pre-recording padding in minutes (:996). */
    public val startExtraMinutes: Long,
    /** Post-recording padding in minutes (:997). */
    public val stopExtraMinutes: Long,
    /** Retention days or policy sentinel (:999–1003). */
    public val retentionDays: Long,
    /** Removal days or policy sentinel (:1005). */
    public val removalDays: Long,
    /** Effective priority (:1006–1011). */
    public val priority: Long,
    /** Content code, including zero (:1012). */
    public val contentType: Long,
    /** Age rating (:1013); null only on v36 sessions where the server omits it. Required from v37. */
    public val ageRating: Long?,
    /** Parental display label when enabled and set (:1015–1072). */
    public val ratingLabel: String?,
    /** Parental icon when enabled and set (:1015–1072). */
    public val ratingIcon: String?,
    /** Rating authority when enabled and set (:1015–1072). */
    public val ratingAuthority: String?,
    /** Rating country when enabled and set (:1015–1072). */
    public val ratingCountry: String?,
    /** Play count, only for recording or completed entries (:1074–1077). */
    public val playCount: Long?,
    /** Playback position in seconds, only for recording or completed entries (:1074–1077). */
    public val playPositionSeconds: Long?,
    /** Season ordinal when set (:712–728,1103). */
    public val seasonNumber: Long?,
    /** Season total when set (:712–728,1103). */
    public val seasonCount: Long?,
    /** Episode ordinal when set (:712–728,1103). */
    public val episodeNumber: Long?,
    /** Episode total when set (:712–728,1103). */
    public val episodeCount: Long?,
    /** Part ordinal when set (:712–728,1103). */
    public val partNumber: Long?,
    /** Part total when set (:712–728,1103). */
    public val partCount: Long?,
    /** Episode display text, wire `episode` (:728,1103). */
    public val episodeOnscreen: String?,
    /** Localized title when set (:1079–1080). */
    public val title: String?,
    /** Localized description when set (:1095–1101). */
    public val description: String?,
    /** Localized summary when set (:1095–1101). */
    public val summary: String?,
    /** Localized subtitle when set (:1095–1101). */
    public val subtitle: String?,
    /** Owner when set (:1104). */
    public val owner: String?,
    /** Creator when set (:1105). */
    public val creator: String?,
    /** Comment when set (:1106). */
    public val comment: String?,
    /** Image URI when nonempty (:1111–1113). */
    public val image: String?,
    /** Fanart URI when nonempty (:1115–1117). */
    public val fanartImage: String?,
    /** Copyright year when nonzero (:1118–1119). */
    public val copyrightYear: Long?,
    /** Immutable file snapshot when files and a configuration exist (:1122–1145). */
    public val files: List<HtspDvrRecordingFile>?,
    /** Last file's relative path when available (:1147–1149). */
    public val path: String?,
    /** Recording configuration UUID, wire `configId`, when linked (:1151–1152). */
    public val dvrConfigUuid: String?,
    /** Duplicate flag, only for upcoming entries (:1185–1186). */
    public val duplicate: Boolean?,
    /** Always-emitted recording state (:1155–1188). */
    public val state: String,
    /** Recording error when present (:1189–1190). */
    public val error: String?,
    /** Subscription error when present (:1191–1192). */
    public val subscriptionError: String?,
    /** Nonzero stream-error count (:1193–1194). */
    public val streamErrors: Long?,
    /** Nonzero data-error count (:1195–1196). */
    public val dataErrors: Long?,
    /** Known file size for recording or completed entries (:1161,1171,1197–1198). */
    public val dataSizeBytes: Long?,
    private val immutableSnapshot: Unit,
) {
    /** Constructs a validated full recording snapshot. */
    public constructor(
        entryId: Long,
        entryUuid: String? = null,
        enabled: Boolean,
        channelId: Long? = null,
        channelName: String? = null,
        eventId: Long? = null,
        autorecEntryUuid: String? = null,
        timerecEntryUuid: String? = null,
        startEpochSeconds: Long,
        stopEpochSeconds: Long,
        startExtraMinutes: Long,
        stopExtraMinutes: Long,
        retentionDays: Long,
        removalDays: Long,
        priority: Long,
        contentType: Long,
        ageRating: Long? = null,
        ratingLabel: String? = null,
        ratingIcon: String? = null,
        ratingAuthority: String? = null,
        ratingCountry: String? = null,
        playCount: Long? = null,
        playPositionSeconds: Long? = null,
        seasonNumber: Long? = null,
        seasonCount: Long? = null,
        episodeNumber: Long? = null,
        episodeCount: Long? = null,
        partNumber: Long? = null,
        partCount: Long? = null,
        episodeOnscreen: String? = null,
        title: String? = null,
        description: String? = null,
        summary: String? = null,
        subtitle: String? = null,
        owner: String? = null,
        creator: String? = null,
        comment: String? = null,
        image: String? = null,
        fanartImage: String? = null,
        copyrightYear: Long? = null,
        files: List<HtspDvrRecordingFile>? = null,
        path: String? = null,
        dvrConfigUuid: String? = null,
        duplicate: Boolean? = null,
        state: String,
        error: String? = null,
        subscriptionError: String? = null,
        streamErrors: Long? = null,
        dataErrors: Long? = null,
        dataSizeBytes: Long? = null,
    ) : this(
        entryId, entryUuid, enabled, channelId, channelName, eventId, autorecEntryUuid, timerecEntryUuid,
        startEpochSeconds, stopEpochSeconds, startExtraMinutes, stopExtraMinutes, retentionDays, removalDays,
        priority, contentType, ageRating, ratingLabel, ratingIcon, ratingAuthority, ratingCountry,
        playCount, playPositionSeconds, seasonNumber, seasonCount, episodeNumber, episodeCount, partNumber,
        partCount, episodeOnscreen, title, description, summary, subtitle, owner, creator, comment,
        image, fanartImage, copyrightYear, files?.immutableSnapshot(), path, dvrConfigUuid, duplicate,
        state, error, subscriptionError, streamErrors, dataErrors, dataSizeBytes, Unit,
    )

    /** Returns a validated copy with an immutable file snapshot. */
    public fun copy(
        entryId: Long = this.entryId,
        entryUuid: String? = this.entryUuid,
        enabled: Boolean = this.enabled,
        channelId: Long? = this.channelId,
        channelName: String? = this.channelName,
        eventId: Long? = this.eventId,
        autorecEntryUuid: String? = this.autorecEntryUuid,
        timerecEntryUuid: String? = this.timerecEntryUuid,
        startEpochSeconds: Long = this.startEpochSeconds,
        stopEpochSeconds: Long = this.stopEpochSeconds,
        startExtraMinutes: Long = this.startExtraMinutes,
        stopExtraMinutes: Long = this.stopExtraMinutes,
        retentionDays: Long = this.retentionDays,
        removalDays: Long = this.removalDays,
        priority: Long = this.priority,
        contentType: Long = this.contentType,
        ageRating: Long? = this.ageRating,
        ratingLabel: String? = this.ratingLabel,
        ratingIcon: String? = this.ratingIcon,
        ratingAuthority: String? = this.ratingAuthority,
        ratingCountry: String? = this.ratingCountry,
        playCount: Long? = this.playCount,
        playPositionSeconds: Long? = this.playPositionSeconds,
        seasonNumber: Long? = this.seasonNumber,
        seasonCount: Long? = this.seasonCount,
        episodeNumber: Long? = this.episodeNumber,
        episodeCount: Long? = this.episodeCount,
        partNumber: Long? = this.partNumber,
        partCount: Long? = this.partCount,
        episodeOnscreen: String? = this.episodeOnscreen,
        title: String? = this.title,
        description: String? = this.description,
        summary: String? = this.summary,
        subtitle: String? = this.subtitle,
        owner: String? = this.owner,
        creator: String? = this.creator,
        comment: String? = this.comment,
        image: String? = this.image,
        fanartImage: String? = this.fanartImage,
        copyrightYear: Long? = this.copyrightYear,
        files: List<HtspDvrRecordingFile>? = this.files,
        path: String? = this.path,
        dvrConfigUuid: String? = this.dvrConfigUuid,
        duplicate: Boolean? = this.duplicate,
        state: String = this.state,
        error: String? = this.error,
        subscriptionError: String? = this.subscriptionError,
        streamErrors: Long? = this.streamErrors,
        dataErrors: Long? = this.dataErrors,
        dataSizeBytes: Long? = this.dataSizeBytes,
    ): HtspDvrEntry = HtspDvrEntry(
        entryId, entryUuid, enabled, channelId, channelName, eventId, autorecEntryUuid, timerecEntryUuid,
        startEpochSeconds, stopEpochSeconds, startExtraMinutes, stopExtraMinutes, retentionDays, removalDays,
        priority, contentType, ageRating, ratingLabel, ratingIcon, ratingAuthority, ratingCountry,
        playCount, playPositionSeconds, seasonNumber, seasonCount, episodeNumber, episodeCount, partNumber,
        partCount, episodeOnscreen, title, description, summary, subtitle, owner, creator, comment,
        image, fanartImage, copyrightYear, files, path, dvrConfigUuid, duplicate,
        state, error, subscriptionError, streamErrors, dataErrors, dataSizeBytes,
    )

    init {
        requireU32("entryId", entryId)
        requireU32("retentionDays", retentionDays)
        requireU32("removalDays", removalDays)
        requireU32("priority", priority)
        requireU32("contentType", contentType)
        listOfNotNull(
            channelId, eventId, ageRating, playCount, playPositionSeconds, seasonNumber, seasonCount,
            episodeNumber, episodeCount, partNumber, partCount, copyrightYear, streamErrors, dataErrors,
        ).forEach { requireU32("recording field", it) }
    }

    override fun toString(): String = "HtspDvrEntry(<redacted>)"
}
