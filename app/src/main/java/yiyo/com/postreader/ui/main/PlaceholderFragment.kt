package yiyo.com.postreader.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_main.*
import yiyo.com.postreader.PostItemBindingModel_
import yiyo.com.postreader.R
import yiyo.com.postreader.utils.SwipeTouchCallback
import yiyo.com.postreader.utils.component
import javax.inject.Inject


class PlaceholderFragment : BaseMvRxFragment() {

    @Inject
    lateinit var viewModelFactory: PageViewModel.Factory

    private val viewModel: PageViewModel by fragmentViewModel()
    private val postsController by lazy { PostsController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    private val deleteIcon by lazy {
        ContextCompat.getDrawable(requireContext(), R.drawable.ic_delete)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()

        val fab: FloatingActionButton = view.findViewById(R.id.fab)
        fab.setOnClickListener {
            viewModel.removeAllPosts()
            Snackbar.make(it, R.string.all_post_deleted, Snackbar.LENGTH_LONG).show()
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
        fun newInstance(): PlaceholderFragment = PlaceholderFragment()
    }
}