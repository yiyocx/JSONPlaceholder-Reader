package yiyo.com.postreader.ui.posts

import com.airbnb.epoxy.TypedEpoxyController
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import yiyo.com.postreader.data.models.PostFull
import yiyo.com.postreader.postItem

class PostsController : TypedEpoxyController<List<PostFull>>() {

    private val actions = PublishSubject.create<Any>()

    override fun buildModels(data: List<PostFull>) {
        data.forEach { post ->
            postItem {
                id(post.id)
                post(post)
                onClick { _ -> actions.onNext(post) }
            }
        }
    }

    fun observeActions(): Observable<Any> = actions.hide()
}