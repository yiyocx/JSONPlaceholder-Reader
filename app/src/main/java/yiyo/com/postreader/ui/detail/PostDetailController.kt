package yiyo.com.postreader.ui.detail

import com.airbnb.epoxy.TypedEpoxyController
import com.airbnb.mvrx.Success
import yiyo.com.postreader.postDetailItem
import yiyo.com.postreader.userItem

class PostDetailController : TypedEpoxyController<PostDetailState>() {

    override fun buildModels(data: PostDetailState) {
        val post = data.post

        postDetailItem {
            id(post.id)
            title(post.title)
            description(post.body)
        }

        if (data.user is Success) {
            val user = data.user.invoke()
            userItem {
                id(user.id)
                user(user)
            }
        }
    }
}