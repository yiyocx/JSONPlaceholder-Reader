package yiyo.com.postreader.ui.detail

import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.schedulers.Schedulers
import yiyo.com.postreader.data.repositories.PostRepository
import yiyo.com.postreader.data.repositories.UserRepository
import yiyo.com.postreader.utils.MvRxViewModel

class PostDetailViewModel @AssistedInject constructor(
    @Assisted initialState: PostDetailState,
    userRepository: UserRepository,
    postRepository: PostRepository
) : MvRxViewModel<PostDetailState>(initialState) {

    init {
        userRepository.getUserInfo(initialState.post.userId)
            .subscribeOn(Schedulers.io())
            .execute { copy(user = it) }

        postRepository.getCommentsForPost(initialState.post.id)
            .subscribeOn(Schedulers.io())
            .execute { copy(comments = it) }
    }

    fun toggleFavorite() {
        setState { copy(post = post.copy(favorite = !post.favorite)) }
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(initialState: PostDetailState): PostDetailViewModel
    }

    companion object : MvRxViewModelFactory<PostDetailViewModel, PostDetailState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: PostDetailState
        ): PostDetailViewModel? {
            val fragment =
                (viewModelContext as FragmentViewModelContext).fragment<PostDetailDialogFragment>()
            return fragment.viewModelFactory.create(state)
        }
    }
}