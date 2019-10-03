package yiyo.com.postreader.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import yiyo.com.postreader.data.models.ActionsUIModel
import yiyo.com.postreader.data.models.PostFull
import yiyo.com.postreader.data.repositories.PostRepository
import yiyo.com.postreader.ui.posts.PostListState
import yiyo.com.postreader.utils.MvRxViewModel
import yiyo.com.postreader.utils.toPostFull

class MainViewModel @AssistedInject constructor(
    @Assisted initialState: PostListState,
    private val postRepository: PostRepository
) : MvRxViewModel<PostListState>(initialState) {

    init {
        loadAllPosts()
    }

    private val mutableActions = MutableLiveData<ActionsUIModel>()

    fun loadAllPosts() {
        postRepository.getPosts()
            .map { it.mapIndexed { index, post -> post.toPostFull(index < 20) } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .execute { result -> copy(posts = result().orEmpty(), favorites = emptyList()) }
    }

    fun removePost(post: PostFull) = setState {
        val newPosts = posts.filter { it != post }
        val newFavorites = favorites.filter { it != post }
        copy(posts = newPosts, favorites = newFavorites)
    }

    fun removeAllPosts() = setState { copy(posts = emptyList(), favorites = emptyList()) }

    fun showPostDetail(post: PostFull) {
        mutableActions.value = ActionsUIModel.ShowPost(post)
    }

    fun markAsSeen(postId: Int) = setState {
        val newData = posts.map { post ->
            if (post.id == postId) post.copy(unseen = false) else post
        }
        copy(posts = newData)
    }

    fun actions(): LiveData<ActionsUIModel> = mutableActions

    fun handleFavorite(post: PostFull) {
        setState {
            val allPosts = posts.map {
                if (it.id == post.id) {
                    it.copy(favorite = post.favorite)
                } else it
            }

            val allFavorites = if (post.favorite) {
                favorites.plus(post)
            } else {
                favorites.filter { it.id != post.id }
            }
            copy(posts = allPosts, favorites = allFavorites)
        }
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(initialState: PostListState): MainViewModel
    }

    companion object : MvRxViewModelFactory<MainViewModel, PostListState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: PostListState
        ): MainViewModel? {
            val mainActivity = viewModelContext.activity<MainActivity>()
            return mainActivity.viewModelFactory.create(state)
        }
    }
}