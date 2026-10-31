package at.bernhardberger.tvheadend.protocolconsumer

import at.bernhardberger.tvheadend.htsp.messages.HtspDvrEntryAddMessage
import at.bernhardberger.tvheadend.htsp.messages.HtspDvrEntryUpdateMessage
import at.bernhardberger.tvheadend.htsp.messages.HtspEventUpdateMessage
import at.bernhardberger.tvheadend.htsp.model.HtspDvrEntry
import at.bernhardberger.tvheadend.htsp.model.HtspDvrRecordingFile
import at.bernhardberger.tvheadend.htsp.model.HtspEvent
import at.bernhardberger.tvheadend.htsp.model.HtspProgrammeCredit
import at.bernhardberger.tvheadend.htsp.model.HtspProgrammeCredits
import at.bernhardberger.tvheadend.htsp.requests.HtspEpgBroadcastObject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ArtworkBinaryCompatibilityTest {
    @Test
    fun `rc 1 constructors link and default all new artwork to absent`() {
        val event = Rc1ArtworkConsumer.event()
        val broadcast = Rc1ArtworkConsumer.broadcast()
        val dvr = Rc1ArtworkConsumer.dvr()
        listOf(
            event.images(), broadcast.images(), dvr.images(),
            Rc1ArtworkConsumer.eventUpdate().event.images(),
            Rc1ArtworkConsumer.dvrAdd().entry.images(), Rc1ArtworkConsumer.dvrUpdate().entry.images(),
        ).flatten().forEach { assertNull(it) }
        // Candidate calls are independent of the constructor descriptors in the old bytecode.
        assertEquals(
            HtspEvent(
                eventId = 7, channelId = 8, startEpochSeconds = 100, stopEpochSeconds = 200,
                title = "Original", categories = listOf("Drama"), isNew = true,
                episodeOnscreen = "S1E2", image = "legacy-image", dvrId = 9, nextEventId = 10,
                imagePoster = null, imageBackdrop = null, imageStill = null,
            ), event,
        )
        assertEquals(
            HtspEpgBroadcastObject(
                id = 7, updatedEpochSeconds = 90, startEpochSeconds = 100, stopEpochSeconds = 200,
                grabber = null, channelUuid = null, dvbEventId = 7, externalEventId = null,
                widescreen = true, highDefinition = true, blackAndWhite = false, deafSigned = false,
                subtitled = true, audioDescribed = false, isNew = true, isRepeat = false, lines = 1080,
                aspectRatio = null, starRating = null, ageRating = null, ratingLabelUuid = null,
                image = "legacy-image", titles = mapOf("eng" to "Original"), subtitles = null,
                summaries = null, descriptions = null, episodeNumber = null, genres = listOf(1),
                copyrightYear = null, firstAiredEpochSeconds = null, categories = listOf("Drama"),
                keywords = null, seriesLinkUri = "series-link", episodeLinkUri = "episode-link",
                imagePoster = null, imageBackdrop = null, imageStill = null,
            ), broadcast,
        )
        assertEquals(
            HtspDvrEntry(
                entryId = 7, enabled = true, startEpochSeconds = 100, stopEpochSeconds = 200,
                startExtraMinutes = 0, stopExtraMinutes = 0, retentionDays = 0, removalDays = 0,
                priority = 0, contentType = 0, state = "scheduled", title = "Original",
                seasonCount = 2, episodeOnscreen = "S1E2",
                files = listOf(HtspDvrRecordingFile("recording.ts", null, null, null)),
                imagePoster = null, imageBackdrop = null, imageStill = null,
            ), dvr,
        )
        assertEquals(HtspEventUpdateMessage(event), Rc1ArtworkConsumer.eventUpdate())
        assertEquals(HtspDvrEntryAddMessage(dvr), Rc1ArtworkConsumer.dvrAdd())
        assertEquals(HtspDvrEntryUpdateMessage(dvr), Rc1ArtworkConsumer.dvrUpdate())
    }

    @Test
    fun `rc 1 copy bridges retain artwork and replace fields across both default masks`() {
        val images = listOf(
            Triple("poster", "backdrop", "still"), Triple("", "", ""),
            Triple(null, null, null), Triple("poster", "", null),
        )
        for ((poster, backdrop, still) in images) {
            val event = Rc1ArtworkConsumer.event().copy(
                credits = HtspProgrammeCredits(listOf(HtspProgrammeCredit("Performer", "actor"))),
                imagePoster = poster, imageBackdrop = backdrop, imageStill = still,
            )
            assertEquals(event.copy(title = "Updated", credits = null), Rc1ArtworkConsumer.copyEvent(event))
            val broadcast = Rc1ArtworkConsumer.broadcast().copy(
                imagePoster = poster, imageBackdrop = backdrop, imageStill = still,
            )
            assertEquals(
                broadcast.copy(titles = mapOf("eng" to "Updated"), episodeLinkUri = "updated-link"),
                Rc1ArtworkConsumer.copyBroadcast(broadcast),
            )
            val dvr = Rc1ArtworkConsumer.dvr().copy(
                imagePoster = poster, imageBackdrop = backdrop, imageStill = still,
            )
            val copiedDvr = dvr.copy(title = "Updated", episodeOnscreen = "S1E3", dataSizeBytes = 4096)
            assertEquals(copiedDvr, Rc1ArtworkConsumer.copyDvr(dvr))
            val update = HtspEventUpdateMessage(event)
            assertEquals(
                update.copy(event = event.copy(title = "Updated", nextEventId = 11)),
                Rc1ArtworkConsumer.copyEventUpdate(update),
            )
            assertEquals(HtspDvrEntryAddMessage(copiedDvr), Rc1ArtworkConsumer.copyDvrAdd(HtspDvrEntryAddMessage(dvr)))
            assertEquals(HtspDvrEntryUpdateMessage(copiedDvr), Rc1ArtworkConsumer.copyDvrUpdate(HtspDvrEntryUpdateMessage(dvr)))
        }
    }

    @Test
    fun `rc 1 bridges retain entity validation and defensive snapshots`() {
        val originalCategories = mutableListOf("Drama")
        val update = Rc1ArtworkConsumer.eventUpdate(originalCategories)
        originalCategories.clear()
        assertEquals(listOf("Drama"), update.event.categories)
        val replacementCategories = mutableListOf("Sport")
        val copy = Rc1ArtworkConsumer.replaceCategories(update, replacementCategories)
        replacementCategories.clear()
        assertEquals(listOf("Sport"), copy.event.categories)
        val files = Rc1ArtworkConsumer.dvr().files!!.toMutableList()
        val dvr = Rc1ArtworkConsumer.replaceFiles(Rc1ArtworkConsumer.dvr(), files)
        files.clear()
        assertEquals(1, dvr.files!!.size)
        assertThrows(UnsupportedOperationException::class.java) {
            @Suppress("UNCHECKED_CAST")
            (dvr.files as MutableList<HtspDvrRecordingFile>).clear()
        }
        assertThrows(IllegalArgumentException::class.java) {
            Rc1ArtworkConsumer.replaceEntryId(Rc1ArtworkConsumer.dvrUpdate(), -1)
        }
    }

    private fun HtspEvent.images() = listOf(imagePoster, imageBackdrop, imageStill)
    private fun HtspDvrEntry.images() = listOf(imagePoster, imageBackdrop, imageStill)
    private fun HtspEpgBroadcastObject.images() = listOf(imagePoster, imageBackdrop, imageStill)
}
