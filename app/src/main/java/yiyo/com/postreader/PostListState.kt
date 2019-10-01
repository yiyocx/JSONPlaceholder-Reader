package yiyo.com.postreader

import com.airbnb.mvrx.MvRxState
import yiyo.com.postreader.data.models.PostFull

data class PostListState(val posts: List<PostFull> = emptyList()) : MvRxState