package yiyo.com.postreader.ui

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

class MainViewModel @AssistedInject constructor(
    @Assisted initialState: PostListState,
    private val postRepository: PostRepository
) : MvRxViewModel<PostListState>(initialState) {

    init {
        loadAllPosts()
    }

    fun loadAllPosts() {
        postRepository.getPosts()
            .map { it.mapIndexed { index, post -> post.toPostFull(index < 20) } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .execute { result -> copy(posts = result().orEmpty()) }
    }

    fun removePost(post: PostFull) = setState {
        val newPosts = posts.filter { it != post }
        copy(posts = newPosts)
    }

    fun removeAllPosts() = setState { copy(posts = emptyList()) }

    @AssistedInject.Factory
    interface Factory {
        fun create(initialState: PostListState): MainViewModel
    }

    companion object : MvRxViewModelFactory<MainViewModel, PostListState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: PostListState
        ): MainViewModel? {
            val fragment = viewModelContext.activity<MainActivity>()
            return fragment.viewModelFactory.create(state)
        }
    }
}