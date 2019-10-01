package yiyo.com.postreader.data.models

data class PostFull(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
    val unseen: Boolean,
    val favorite: Boolean = false
)