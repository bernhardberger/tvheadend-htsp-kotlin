package at.bernhardberger.tvheadend.protocolconsumer

import at.bernhardberger.tvheadend.htsp.messages.HtspEventUpdateMessage
import at.bernhardberger.tvheadend.htsp.model.HtspEvent

/** Compiled only against rc.2: these call sites must never see the repeat flag parameter. */
object Rc2Consumer {
    fun event(): HtspEvent = HtspEvent(
        eventId = 7,
        channelId = 8,
        startEpochSeconds = 100,
        stopEpochSeconds = 200,
        title = "Original",
        subtitle = null,
        summary = null,
        description = null,
        categories = listOf("Drama"),
        keywords = null,
        seriesLinkUri = null,
        episodeUri = null,
        contentType = null,
        ageRating = null,
        ratingLabel = null,
        ratingIcon = null,
        ratingAuthority = null,
        ratingCountry = null,
        starRating = null,
        copyrightYear = null,
        firstAiredEpochSeconds = null,
        isNew = true,
        seasonNumber = null,
        seasonCount = null,
        episodeNumber = null,
        episodeCount = null,
        partNumber = null,
        partCount = null,
        episodeOnscreen = "S1E2",
        image = "legacy-image",
        dvrId = 9,
        nextEventId = 10,
    )

    fun eventUpdate(categories: List<String> = listOf("Drama")): HtspEventUpdateMessage =
        HtspEventUpdateMessage(event().copy(categories = categories))

    // Defaults in both Int masks, plus explicit replacements before and after parameter 32.
    // Each old default bridge also invokes the old full constructor/copy descriptor.
    fun copyEvent(value: HtspEvent): HtspEvent = value.copy(title = "Updated", credits = null)

    fun copyEventUpdate(value: HtspEventUpdateMessage): HtspEventUpdateMessage =
        value.copy(event = value.event.copy(title = "Updated", nextEventId = 11))

    fun replaceCategories(value: HtspEventUpdateMessage, categories: List<String>): HtspEventUpdateMessage =
        value.copy(event = value.event.copy(categories = categories))
}
