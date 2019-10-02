package yiyo.com.postreader.ui.detail

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import yiyo.com.postreader.data.models.Comment
import yiyo.com.postreader.data.models.PostFull
import yiyo.com.postreader.data.models.User

data class PostDetailState(
    val post: PostFull,
    val user: Async<User> = Uninitialized,
    val comments: Async<List<Comment>> = Uninitialized
) : MvRxState {
    constructor(args: PostDetailArgs) : this(args.post)
}