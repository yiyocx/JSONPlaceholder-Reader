package yiyo.com.postreader.ui.main

import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import yiyo.com.postreader.PostListState
import yiyo.com.postreader.data.PostRepository
import yiyo.com.postreader.data.models.PostFull
import yiyo.com.postreader.utils.MvRxViewModel
import yiyo.com.postreader.utils.toPostFull

class PageViewModel @AssistedInject constructor(
    @Assisted initialState: PostListState,
    postRepository: PostRepository
) : MvRxViewModel<PostListState>(initialState) {

    init {
        postRepository.getPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .execute { result ->
                val posts = result().orEmpty().mapIndexed { index, post ->
                    post.toPostFull(index < 20)
                }
                copy(posts = posts)
            }
    }

    fun removePost(post: PostFull) = setState {
        val newPosts = posts.filter { it != post }
        copy(posts = newPosts)
    }

    fun removeAllPosts() = setState { copy(posts = emptyList()) }

    @AssistedInject.Factory
    interface Factory {
        fun create(initialState: PostListState): PageViewModel
    }

    companion object : MvRxViewModelFactory<PageViewModel, PostListState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: PostListState
        ): PageViewModel? {
            val fragment =
                (viewModelContext as FragmentViewModelContext).fragment<PlaceholderFragment>()
            return fragment.viewModelFactory.create(state)
        }
    }
}