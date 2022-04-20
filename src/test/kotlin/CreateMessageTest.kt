import org.junit.After
import org.junit.Test

import org.junit.Assert.*

class CreateMessageTest {

    @After
    fun clear() {
        ChatService.clear()
    }

    @Test
    fun createMessageTest() {
        val result = ChatService.createMessage(1, 2, "Text")
        assertTrue(result)
    }

}