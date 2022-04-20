data class Chat(
    val id: Int,
    val users: List<Int>,
    val messages: List<Message>
)


object ChatService {
    private val chats = mutableListOf<Chat>()
    private var idForChat = 1
    private var idForMessage = 1

    fun clear() {
        chats.clear()
        idForChat = 1
        idForMessage = 1
    }

    fun createMessage(
        senderId: Int,
        recipientId: Int,
        text: String
    ): Boolean {
        val message = Message(
            id = idForMessage++,
            ownerId = senderId,
            text = text
        )
        val newChat = chats.firstOrNull { chat ->
            chat.users
                .containsAll(listOf(recipientId, senderId))
        }
            ?.let { chat ->
                chat.copy(messages = chat.messages + message)
            } ?: Chat(
            id = idForChat++,
            users = listOf(senderId, recipientId),
            messages = listOf(message)
        )

        chats.removeIf { newChat.id == it.id }
        chats.add(newChat)
        return true
    }

    fun getUnreadChatsCount(userId: Int): Int {
        chats.filter {
            it.users.contains(userId)
        }.filter { chat ->
            chat.messages.any {
                !it.isRead
            }
        }.let { return it.size }

    }

    fun getChats(userId: Int): List<Chat> {
        chats.filter {
            it.users.contains(userId)
        }.let { return it }

    }

    fun getMessages(idChat: Int, idLastMessageFrom: Int, countMessage: Int): List<Message> {
        val chat = chats.firstOrNull { it.id == idChat } ?: return emptyList()
        val updateMessages = chat.messages
            .filter { it.id >= idLastMessageFrom }
            .take(countMessage)
            .map { it.copy(isRead = true) }

        val notUpdateMessages = chat.messages.filterNot { it.id >= idLastMessageFrom }

        val updatedChat = chat.copy(messages = notUpdateMessages + updateMessages)

        chats.removeIf { updatedChat.id == it.id }
        chats.add(updatedChat)

        return updateMessages

    }

    fun deleteMessage(idChat: Int, idMessage: Int): Boolean {
        val chat = chats.firstOrNull { it.id == idChat } ?: return false
        val updateMessage = chat.messages
            .filter { it.id == idMessage }
            .map { it.copy(isDelete = true) }

        val notUpdateMessage = chat.messages.filterNot { it.id == idMessage }
        val updatedChat = chat.copy(messages = notUpdateMessage + updateMessage)

        deleteChat(idChat)
        if (notUpdateMessage.isNotEmpty()) chats.add(updatedChat)

        return true
    }

    fun deleteChat(idChat: Int): Boolean {
        chats.removeIf { idChat == it.id }
        return true
    }

    fun editMessage(idChat: Int, idMessage: Int, message: Message): Boolean {
        val chat = chats.firstOrNull { it.id == idChat } ?: return false
        val editMessage = chat.messages
            .filter { it.id == idMessage }
            .map { it.copy(text = message.text, isDelete = message.isDelete) }
        val updateChat = chat.copy(messages = editMessage)
        chats.removeIf { updateChat.id == it.id }
        chats.add(updateChat)

        return true
    }
}
