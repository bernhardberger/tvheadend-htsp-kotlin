package at.bernhardberger.tvheadend.protocolconsumer

import at.bernhardberger.tvheadend.htsp.messages.HtspEventUpdateMessage
import at.bernhardberger.tvheadend.htsp.model.HtspEvent
import at.bernhardberger.tvheadend.htsp.model.HtspProgrammeCredit
import at.bernhardberger.tvheadend.htsp.model.HtspProgrammeCredits
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Rc2BinaryCompatibilityTest {
    @Test
    fun `rc 2 constructors link and default the repeat flag to absent`() {
        val event = Rc2Consumer.event()
        assertNull(event.isRepeat)
        assertNull(Rc2Consumer.eventUpdate().event.isRepeat)
        // Candidate calls are independent of the constructor descriptors in the old bytecode.
        assertEquals(
            HtspEvent(
                eventId = 7, channelId = 8, startEpochSeconds = 100, stopEpochSeconds = 200,
                title = "Original", categories = listOf("Drama"), isNew = true,
                episodeOnscreen = "S1E2", image = "legacy-image", dvrId = 9, nextEventId = 10,
                isRepeat = null,
            ), event,
        )
        assertEquals(HtspEventUpdateMessage(event), Rc2Consumer.eventUpdate())
    }

    @Test
    fun `rc 2 copy bridges retain the repeat flag and replace fields across both default masks`() {
        for (isRepeat in listOf(true, false, null)) {
            val event = Rc2Consumer.event().copy(
                credits = HtspProgrammeCredits(listOf(HtspProgrammeCredit("Performer", "actor"))),
                isRepeat = isRepeat,
            )
            assertEquals(event.copy(title = "Updated", credits = null), Rc2Consumer.copyEvent(event))
            val update = HtspEventUpdateMessage(event)
            assertEquals(
                update.copy(event = event.copy(title = "Updated", nextEventId = 11)),
                Rc2Consumer.copyEventUpdate(update),
            )
        }
    }

    @Test
    fun `rc 2 bridges retain entity validation and defensive snapshots`() {
        val originalCategories = mutableListOf("Drama")
        val update = Rc2Consumer.eventUpdate(originalCategories)
        originalCategories.clear()
        assertEquals(listOf("Drama"), update.event.categories)
        val replacementCategories = mutableListOf("Sport")
        val copy = Rc2Consumer.replaceCategories(update, replacementCategories)
        replacementCategories.clear()
        assertEquals(listOf("Sport"), copy.event.categories)
    }
}
