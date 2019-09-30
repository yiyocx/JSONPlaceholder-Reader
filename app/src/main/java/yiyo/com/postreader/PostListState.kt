package yiyo.com.postreader

import com.airbnb.mvrx.MvRxState
import yiyo.com.postreader.data.models.Post

data class PostListState(val posts: List<Post> = emptyList()) : MvRxState