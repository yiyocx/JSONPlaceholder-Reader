package yiyo.com.postreader.data.models

sealed class ActionsUIModel {
    data class ShowPost(val post: PostFull) : ActionsUIModel()
}