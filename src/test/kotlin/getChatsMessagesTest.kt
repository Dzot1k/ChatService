import org.junit.After
import org.junit.Test

import org.junit.Assert.*

class getChatsMessagesTest {


    @After
    fun clear() {
        ChatService.clear()
    }

    @Test
    fun getUnreadChatsCountTest() {
        ChatService.createMessage(1, 2, "Text")
        ChatService.getMessages(1,1,1)
        ChatService.createMessage(2, 3, "Text")
        ChatService.createMessage(2, 4, "Text")
        ChatService.createMessage(1, 3, "Text")
        ChatService.createMessage(1, 4, "Text")

        val actualResult = ChatService.getUnreadChatsCount(1)
        val expectedResult = 2
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun getChatsTest() {
        ChatService.createMessage(1, 2, "Text")
        ChatService.createMessage(3, 4, "Text")
        val actualResult = ChatService.getChats(2)
        val expectedResult = listOf(Chat(1, listOf(1,2), listOf(Message(1, 1, "Text",))))
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun getMessagesReturnNotEmptyList() {
        ChatService.createMessage(1, 2, "Text")
        ChatService.createMessage(1, 2, "Text2")
        ChatService.createMessage(1, 2, "Text3")
        ChatService.createMessage(2, 3, "Text4")
        val actualResult = ChatService.getMessages(1,2,2)
        val expectedResult = listOf(Message(2,1,"Text2", isRead = true), Message(3, 1, "Text3", isRead = true))
        assertEquals(expectedResult, actualResult)

    }

    @Test
    fun getMessagesReturnEmptyList() {
        ChatService.createMessage(1, 2, "Text")
        ChatService.createMessage(1, 2, "Text2")
        ChatService.createMessage(2, 3, "Text44")
        val actualResult = ChatService.getMessages(3,1,2)
        val expectedResult = emptyList<Message>()
        assertEquals(expectedResult, actualResult)

    }
}