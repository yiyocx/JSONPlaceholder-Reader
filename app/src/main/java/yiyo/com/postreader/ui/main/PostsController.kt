package yiyo.com.postreader.ui.main

import com.airbnb.epoxy.TypedEpoxyController
import yiyo.com.postreader.data.models.PostFull
import yiyo.com.postreader.postItem

class PostsController : TypedEpoxyController<List<PostFull>>() {

    override fun buildModels(data: List<PostFull>) {
        data.forEach { post ->
            postItem {
                id(post.id)
                post(post)
            }
        }
    }
}