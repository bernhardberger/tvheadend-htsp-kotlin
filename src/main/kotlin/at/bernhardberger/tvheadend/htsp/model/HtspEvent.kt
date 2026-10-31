package at.bernhardberger.tvheadend.htsp.model

import at.bernhardberger.tvheadend.htsp.wire.immutableSnapshot
import at.bernhardberger.tvheadend.htsp.wire.requireU32

/** Full event snapshot (pinned htsp_server.c:1316–1456); nullable fields are not set. */
@ConsistentCopyVisibility
public data class HtspEvent private constructor(
    /** Unsigned event identity (:1337). */
    public val eventId: Long,
    /** Channel identity when linked (:1338–1339). */
    public val channelId: Long? = null,
    /** HTSP `start`, always-emitted epoch seconds (:1340). */
    public val startEpochSeconds: Long,
    /** HTSP `stop`, always-emitted epoch seconds (:1341). */
    public val stopEpochSeconds: Long,
    /** Localized title when set (:1342–1343). */
    public val title: String? = null,
    /** Localized subtitle, omitted in basic output mode (:1347–1368). */
    public val subtitle: String? = null,
    /** Localized summary, omitted in basic output mode (:1347–1368). */
    public val summary: String? = null,
    /** Localized description, omitted in basic output mode (:1347–1368). */
    public val description: String? = null,
    /** Category list when supplied outside basic output mode (:1373–1374). */
    public val categories: List<String>? = null,
    /** Keyword list when supplied outside basic output mode (:1375–1376). */
    public val keywords: List<String>? = null,
    /** Series-link URI when linked (:1379–1380). */
    public val seriesLinkUri: String? = null,
    /** Episode URI unless internal to Tvheadend (:1382–1384). */
    public val episodeUri: String? = null,
    /** First genre's unsigned content code (:1386–1390). */
    public val contentType: Long? = null,
    /** Age rating when nonzero (:1391–1393). */
    public val ageRating: Long? = null,
    /** Parental display label when enabled and set (:1395–1426). */
    public val ratingLabel: String? = null,
    /** Parental icon when enabled and set (:1395–1426). */
    public val ratingIcon: String? = null,
    /** Rating authority when enabled and set (:1395–1426). */
    public val ratingAuthority: String? = null,
    /** Rating country when enabled and set (:1395–1426). */
    public val ratingCountry: String? = null,
    /** Star rating when nonzero (:1428–1429). */
    public val starRating: Long? = null,
    /** Copyright year when nonzero (:1430–1431). */
    public val copyrightYear: Long? = null,
    /** HTSP `firstAired`, first-air epoch seconds; null means absent (htsp_server.c:1433). */
    public val firstAiredEpochSeconds: Long? = null,
    /** HTSP `isNew`: optional new-broadcast flag (pinned htsp_server.c:1435). */
    public val isNew: Boolean? = null,
    /** Season ordinal when set (:712–728,1436–1437). */
    public val seasonNumber: Long? = null,
    /** Season total when set (:712–728,1436–1437). */
    public val seasonCount: Long? = null,
    /** Episode ordinal when set (:712–728,1436–1437). */
    public val episodeNumber: Long? = null,
    /** Episode total when set (:712–728,1436–1437). */
    public val episodeCount: Long? = null,
    /** Part ordinal when set (:712–728,1436–1437). */
    public val partNumber: Long? = null,
    /** Part total when set (:712–728,1436–1437). */
    public val partCount: Long? = null,
    /** Episode display text when set (:712–728,1436–1437). */
    public val episodeOnscreen: String? = null,
    /** Image URI when nonempty (:1438–1440). */
    public val image: String? = null,
    /** Accessible recording identity when linked (:1442–1451). */
    public val dvrId: Long? = null,
    /** Following event identity when available (:1453–1454). */
    public val nextEventId: Long? = null,
    /** Programme credits, HTSP `credits` (src/htsp_server.c:1371–1372). */
    public val credits: HtspProgrammeCredits? = null,
    /** HTSP `imagePoster`, typed programme poster since v45; empty means none, null means absent. */
    public val imagePoster: String? = null,
    /** HTSP `imageBackdrop`, typed programme backdrop since v45; empty means none, null means absent. */
    public val imageBackdrop: String? = null,
    /** HTSP `imageStill`, typed programme still since v45; empty means none, null means absent. */
    public val imageStill: String? = null,
    private val immutableSnapshot: Unit,
) {
    /** Constructs a validated full event with immutable text lists. */
    public constructor(
        eventId: Long,
        channelId: Long? = null,
        startEpochSeconds: Long,
        stopEpochSeconds: Long,
        title: String? = null,
        subtitle: String? = null,
        summary: String? = null,
        description: String? = null,
        categories: List<String>? = null,
        keywords: List<String>? = null,
        seriesLinkUri: String? = null,
        episodeUri: String? = null,
        contentType: Long? = null,
        ageRating: Long? = null,
        ratingLabel: String? = null,
        ratingIcon: String? = null,
        ratingAuthority: String? = null,
        ratingCountry: String? = null,
        starRating: Long? = null,
        copyrightYear: Long? = null,
        firstAiredEpochSeconds: Long? = null,
        isNew: Boolean? = null,
        seasonNumber: Long? = null,
        seasonCount: Long? = null,
        episodeNumber: Long? = null,
        episodeCount: Long? = null,
        partNumber: Long? = null,
        partCount: Long? = null,
        episodeOnscreen: String? = null,
        image: String? = null,
        dvrId: Long? = null,
        nextEventId: Long? = null,
        credits: HtspProgrammeCredits? = null,
        imagePoster: String? = null,
        imageBackdrop: String? = null,
        imageStill: String? = null,
    ) : this(
        eventId = eventId,
        channelId = channelId,
        startEpochSeconds = startEpochSeconds,
        stopEpochSeconds = stopEpochSeconds,
        title = title,
        subtitle = subtitle,
        summary = summary,
        description = description,
        categories = categories?.immutableSnapshot(),
        keywords = keywords?.immutableSnapshot(),
        seriesLinkUri = seriesLinkUri,
        episodeUri = episodeUri,
        contentType = contentType,
        ageRating = ageRating,
        ratingLabel = ratingLabel,
        ratingIcon = ratingIcon,
        ratingAuthority = ratingAuthority,
        ratingCountry = ratingCountry,
        starRating = starRating,
        copyrightYear = copyrightYear,
        firstAiredEpochSeconds = firstAiredEpochSeconds,
        isNew = isNew,
        seasonNumber = seasonNumber,
        seasonCount = seasonCount,
        episodeNumber = episodeNumber,
        episodeCount = episodeCount,
        partNumber = partNumber,
        partCount = partCount,
        episodeOnscreen = episodeOnscreen,
        image = image,
        dvrId = dvrId,
        nextEventId = nextEventId,
        credits = credits,
        imagePoster = imagePoster,
        imageBackdrop = imageBackdrop,
        imageStill = imageStill,
        immutableSnapshot = Unit,
    )

    /** Returns a validated copy with immutable snapshots of replacement collections. */
    public fun copy(
        eventId: Long = this.eventId,
        channelId: Long? = this.channelId,
        startEpochSeconds: Long = this.startEpochSeconds,
        stopEpochSeconds: Long = this.stopEpochSeconds,
        title: String? = this.title,
        subtitle: String? = this.subtitle,
        summary: String? = this.summary,
        description: String? = this.description,
        categories: List<String>? = this.categories,
        keywords: List<String>? = this.keywords,
        seriesLinkUri: String? = this.seriesLinkUri,
        episodeUri: String? = this.episodeUri,
        contentType: Long? = this.contentType,
        ageRating: Long? = this.ageRating,
        ratingLabel: String? = this.ratingLabel,
        ratingIcon: String? = this.ratingIcon,
        ratingAuthority: String? = this.ratingAuthority,
        ratingCountry: String? = this.ratingCountry,
        starRating: Long? = this.starRating,
        copyrightYear: Long? = this.copyrightYear,
        firstAiredEpochSeconds: Long? = this.firstAiredEpochSeconds,
        isNew: Boolean? = this.isNew,
        seasonNumber: Long? = this.seasonNumber,
        seasonCount: Long? = this.seasonCount,
        episodeNumber: Long? = this.episodeNumber,
        episodeCount: Long? = this.episodeCount,
        partNumber: Long? = this.partNumber,
        partCount: Long? = this.partCount,
        episodeOnscreen: String? = this.episodeOnscreen,
        image: String? = this.image,
        dvrId: Long? = this.dvrId,
        nextEventId: Long? = this.nextEventId,
        credits: HtspProgrammeCredits? = this.credits,
        imagePoster: String? = this.imagePoster,
        imageBackdrop: String? = this.imageBackdrop,
        imageStill: String? = this.imageStill,
    ): HtspEvent = HtspEvent(
        eventId = eventId,
        channelId = channelId,
        startEpochSeconds = startEpochSeconds,
        stopEpochSeconds = stopEpochSeconds,
        title = title,
        subtitle = subtitle,
        summary = summary,
        description = description,
        categories = categories,
        keywords = keywords,
        seriesLinkUri = seriesLinkUri,
        episodeUri = episodeUri,
        contentType = contentType,
        ageRating = ageRating,
        ratingLabel = ratingLabel,
        ratingIcon = ratingIcon,
        ratingAuthority = ratingAuthority,
        ratingCountry = ratingCountry,
        starRating = starRating,
        copyrightYear = copyrightYear,
        firstAiredEpochSeconds = firstAiredEpochSeconds,
        isNew = isNew,
        seasonNumber = seasonNumber,
        seasonCount = seasonCount,
        episodeNumber = episodeNumber,
        episodeCount = episodeCount,
        partNumber = partNumber,
        partCount = partCount,
        episodeOnscreen = episodeOnscreen,
        image = image,
        dvrId = dvrId,
        nextEventId = nextEventId,
        credits = credits,
        imagePoster = imagePoster,
        imageBackdrop = imageBackdrop,
        imageStill = imageStill,
    )

    /** Links constructors compiled against rc.1, defaulting typed artwork to absent. */
    @Deprecated("Retained for binary compatibility", level = DeprecationLevel.HIDDEN)
    public constructor(
        eventId: Long,
        channelId: Long? = null,
        startEpochSeconds: Long,
        stopEpochSeconds: Long,
        title: String? = null,
        subtitle: String? = null,
        summary: String? = null,
        description: String? = null,
        categories: List<String>? = null,
        keywords: List<String>? = null,
        seriesLinkUri: String? = null,
        episodeUri: String? = null,
        contentType: Long? = null,
        ageRating: Long? = null,
        ratingLabel: String? = null,
        ratingIcon: String? = null,
        ratingAuthority: String? = null,
        ratingCountry: String? = null,
        starRating: Long? = null,
        copyrightYear: Long? = null,
        firstAiredEpochSeconds: Long? = null,
        isNew: Boolean? = null,
        seasonNumber: Long? = null,
        seasonCount: Long? = null,
        episodeNumber: Long? = null,
        episodeCount: Long? = null,
        partNumber: Long? = null,
        partCount: Long? = null,
        episodeOnscreen: String? = null,
        image: String? = null,
        dvrId: Long? = null,
        nextEventId: Long? = null,
        credits: HtspProgrammeCredits? = null,
    ) : this(
        eventId = eventId,
        channelId = channelId,
        startEpochSeconds = startEpochSeconds,
        stopEpochSeconds = stopEpochSeconds,
        title = title,
        subtitle = subtitle,
        summary = summary,
        description = description,
        categories = categories,
        keywords = keywords,
        seriesLinkUri = seriesLinkUri,
        episodeUri = episodeUri,
        contentType = contentType,
        ageRating = ageRating,
        ratingLabel = ratingLabel,
        ratingIcon = ratingIcon,
        ratingAuthority = ratingAuthority,
        ratingCountry = ratingCountry,
        starRating = starRating,
        copyrightYear = copyrightYear,
        firstAiredEpochSeconds = firstAiredEpochSeconds,
        isNew = isNew,
        seasonNumber = seasonNumber,
        seasonCount = seasonCount,
        episodeNumber = episodeNumber,
        episodeCount = episodeCount,
        partNumber = partNumber,
        partCount = partCount,
        episodeOnscreen = episodeOnscreen,
        image = image,
        dvrId = dvrId,
        nextEventId = nextEventId,
        credits = credits,
        imagePoster = null,
        imageBackdrop = null,
        imageStill = null,
    )

    /** Keeps the rc.1 copy ABI while retaining this record's typed artwork. */
    @Deprecated("Retained for binary compatibility", level = DeprecationLevel.HIDDEN)
    public fun copy(
        eventId: Long = this.eventId,
        channelId: Long? = this.channelId,
        startEpochSeconds: Long = this.startEpochSeconds,
        stopEpochSeconds: Long = this.stopEpochSeconds,
        title: String? = this.title,
        subtitle: String? = this.subtitle,
        summary: String? = this.summary,
        description: String? = this.description,
        categories: List<String>? = this.categories,
        keywords: List<String>? = this.keywords,
        seriesLinkUri: String? = this.seriesLinkUri,
        episodeUri: String? = this.episodeUri,
        contentType: Long? = this.contentType,
        ageRating: Long? = this.ageRating,
        ratingLabel: String? = this.ratingLabel,
        ratingIcon: String? = this.ratingIcon,
        ratingAuthority: String? = this.ratingAuthority,
        ratingCountry: String? = this.ratingCountry,
        starRating: Long? = this.starRating,
        copyrightYear: Long? = this.copyrightYear,
        firstAiredEpochSeconds: Long? = this.firstAiredEpochSeconds,
        isNew: Boolean? = this.isNew,
        seasonNumber: Long? = this.seasonNumber,
        seasonCount: Long? = this.seasonCount,
        episodeNumber: Long? = this.episodeNumber,
        episodeCount: Long? = this.episodeCount,
        partNumber: Long? = this.partNumber,
        partCount: Long? = this.partCount,
        episodeOnscreen: String? = this.episodeOnscreen,
        image: String? = this.image,
        dvrId: Long? = this.dvrId,
        nextEventId: Long? = this.nextEventId,
        credits: HtspProgrammeCredits? = this.credits,
    ): HtspEvent = copy(
        eventId = eventId,
        channelId = channelId,
        startEpochSeconds = startEpochSeconds,
        stopEpochSeconds = stopEpochSeconds,
        title = title,
        subtitle = subtitle,
        summary = summary,
        description = description,
        categories = categories,
        keywords = keywords,
        seriesLinkUri = seriesLinkUri,
        episodeUri = episodeUri,
        contentType = contentType,
        ageRating = ageRating,
        ratingLabel = ratingLabel,
        ratingIcon = ratingIcon,
        ratingAuthority = ratingAuthority,
        ratingCountry = ratingCountry,
        starRating = starRating,
        copyrightYear = copyrightYear,
        firstAiredEpochSeconds = firstAiredEpochSeconds,
        isNew = isNew,
        seasonNumber = seasonNumber,
        seasonCount = seasonCount,
        episodeNumber = episodeNumber,
        episodeCount = episodeCount,
        partNumber = partNumber,
        partCount = partCount,
        episodeOnscreen = episodeOnscreen,
        image = image,
        dvrId = dvrId,
        nextEventId = nextEventId,
        credits = credits,
        imagePoster = this.imagePoster,
        imageBackdrop = this.imageBackdrop,
        imageStill = this.imageStill,
    )

    override fun toString(): String = "HtspEvent(<redacted>)"

    init {
        requireU32("eventId", eventId)
        channelId?.let { requireU32("channelId", it) }
        contentType?.let { requireU32("contentType", it) }
        ageRating?.let { requireU32("ageRating", it) }
        starRating?.let { requireU32("starRating", it) }
        copyrightYear?.let { requireU32("copyrightYear", it) }
        seasonNumber?.let { requireU32("seasonNumber", it) }
        seasonCount?.let { requireU32("seasonCount", it) }
        episodeNumber?.let { requireU32("episodeNumber", it) }
        episodeCount?.let { requireU32("episodeCount", it) }
        partNumber?.let { requireU32("partNumber", it) }
        partCount?.let { requireU32("partCount", it) }
        dvrId?.let { requireU32("dvrId", it) }
        nextEventId?.let { requireU32("nextEventId", it) }
    }
}
