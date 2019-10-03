package yiyo.com.postreader.ui.posts

import com.airbnb.mvrx.MvRxState
import yiyo.com.postreader.data.models.PostFull

data class PostListState(
    val posts: List<PostFull> = emptyList(),
    val favorites: List<PostFull> = emptyList()
) : MvRxState