package yiyo.com.postreader.ui.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.airbnb.epoxy.TypedEpoxyController
import yiyo.com.postreader.data.models.ActionsUIModel
import yiyo.com.postreader.data.models.PostFull
import yiyo.com.postreader.postItem

class PostsController : TypedEpoxyController<List<PostFull>>() {

    private val actions = MutableLiveData<ActionsUIModel>()

    override fun buildModels(data: List<PostFull>) {
        data.forEach { post ->
            postItem {
                id(post.id)
                post(post)
                showIcon(post.unseen || post.favorite)
                onClick { _ -> actions.value = ActionsUIModel.ShowPost(post) }
            }
        }
    }

    fun uiActions(): LiveData<ActionsUIModel> = actions
}