package yiyo.com.postreader.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import yiyo.com.postreader.PostListState
import yiyo.com.postreader.data.PostRepository
import yiyo.com.postreader.utils.MvRxViewModel

class PageViewModel @AssistedInject constructor(
    @Assisted initialState: PostListState,
    postRepository: PostRepository
) : MvRxViewModel<PostListState>(initialState) {

    init {
        postRepository.getPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .execute {
                copy(it().orEmpty())
            }
    }

    private val _index = MutableLiveData<Int>()
    val text: LiveData<String> = Transformations.map(_index) {
        "Hello world from section: $it"
    }

    fun setIndex(index: Int) {
        _index.value = index
    }

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