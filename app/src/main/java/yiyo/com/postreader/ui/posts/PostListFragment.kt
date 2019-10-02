package yiyo.com.postreader.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.activityViewModel
import com.airbnb.mvrx.withState
import kotlinx.android.synthetic.main.fragment_post_list.*
import yiyo.com.postreader.PostItemBindingModel_
import yiyo.com.postreader.R
import yiyo.com.postreader.data.models.PostFull
import yiyo.com.postreader.ui.MainViewModel
import yiyo.com.postreader.utils.SwipeTouchCallback


class PostListFragment : BaseMvRxFragment() {

    private val viewModel: MainViewModel by activityViewModel()
    private val postsController by lazy { PostsController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post_list, container, false)
    }

    private val deleteIcon by lazy {
        ContextCompat.getDrawable(requireContext(), R.drawable.ic_delete)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        postsController.observeActions()
            .subscribe {
                if (it is PostFull) {
                    viewModel.showPostDetail(it)
                }
            }
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = postsController.adapter

        val touchCallback = object : SwipeTouchCallback<PostItemBindingModel_>(
            postsController,
            PostItemBindingModel_::class.java,
            requireNotNull(deleteIcon)
        ) {
            override fun onSwipeCompleted(
                model: PostItemBindingModel_,
                itemView: View,
                position: Int,
                direction: Int
            ) = viewModel.removePost(model.post())
        }
        val itemTouchHelper = ItemTouchHelper(touchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun invalidate() {
        withState(viewModel) { state ->
            postsController.setData(state.posts)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): PostListFragment = PostListFragment()
    }
}