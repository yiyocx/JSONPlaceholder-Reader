package yiyo.com.postreader.ui.detail

import com.airbnb.epoxy.TypedEpoxyController
import com.airbnb.mvrx.Success
import yiyo.com.postreader.commentItem
import yiyo.com.postreader.header
import yiyo.com.postreader.postDetailItem
import yiyo.com.postreader.userItem

class PostDetailController : TypedEpoxyController<PostDetailState>() {

    override fun buildModels(state: PostDetailState) {
        val post = state.post

        postDetailItem {
            id(post.id)
            title(post.title)
            description(post.body)
        }

        if (state.user is Success) {
            val user = state.user.invoke()
            userItem {
                id(user.id)
                user(user)
            }
        }

        if (state.comments is Success) {
            val comments = state.comments.invoke()
            header {
                id("comments_header")
                title("Comments")
            }
            comments.forEach {
                commentItem {
                    id("${it.postId}_${it.id}")
                    comment(it)
                }
            }
        }
    }
}