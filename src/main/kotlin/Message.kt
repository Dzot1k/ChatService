data class Message(
    val id: Int,
    val ownerId: Int,
    val text: String,
    val isRead: Boolean = false,
    val isDelete: Boolean = false

)

