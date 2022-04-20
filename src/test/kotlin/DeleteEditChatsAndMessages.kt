import org.junit.After
import org.junit.Test

import org.junit.Assert.*

class DeleteEditChatsAndMessages {

    @After
    fun clear() {
        ChatService.clear()
    }

    @Test
    fun deleteMessageIsTrue() {
        ChatService.createMessage(1, 2, "Text")
        ChatService.createMessage(2, 1, "Text2")
        ChatService.createMessage(1, 2, "Text3")
        ChatService.createMessage(4, 5, "Text3")
        val result = ChatService.deleteMessage(1, 3)
        assertTrue(result)
    }

    @Test
    fun deleteMessageIsTrueAsLastMessage() {
        ChatService.createMessage(4, 3, "Text")
        val result = ChatService.deleteMessage(1, 1)
        assertTrue(result)
    }

    @Test
    fun deleteMessageIsFalse() {
        ChatService.createMessage(1, 2, "Text")
        ChatService.createMessage(2, 1, "Text2")
        ChatService.createMessage(1, 2, "Text3")
        val result = ChatService.deleteMessage(4, 2)
        assertFalse(result)
    }

    @Test
    fun deleteChatIsTrue() {
        ChatService.createMessage(1, 2, "Text")
        ChatService.createMessage(3, 1, "Text2")
        ChatService.createMessage(4, 2, "Text3")
        val result = ChatService.deleteChat(3)
        assertTrue(result)
    }


    @Test
    fun editMessageIsTrue() {
        ChatService.createMessage(1, 2, "Text")
        ChatService.createMessage(1, 2, "Text")
        ChatService.createMessage(3, 4, "Text")
        val mess = Message(1, 1, "EditText")
        val result = ChatService.editMessage(1, 2, mess)
        assertTrue(result)
    }

    @Test
    fun editMessageIsFalse() {
        ChatService.createMessage(1, 2, "Text")
        ChatService.createMessage(1, 2, "Text")
        val mess = Message(1, 1, "EditText")
        val result = ChatService.editMessage(2, 1, mess)
        assertFalse(result)
    }
}