package yiyo.com.postreader.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.mvrx.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_post_detail_dialog.*
import yiyo.com.postreader.R
import yiyo.com.postreader.data.models.PostFull
import yiyo.com.postreader.ui.MainViewModel
import yiyo.com.postreader.utils.component
import javax.inject.Inject

class PostDetailDialogFragment : BottomSheetDialogFragment(), MvRxView {

    override val mvrxViewId: String by MvRxViewId()
    override val mvrxViewModelStore by lazy { MvRxViewModelStore(viewModelStore) }

    @Inject
    lateinit var viewModelFactory: PostDetailViewModel.Factory

    private val sharedViewModel: MainViewModel by activityViewModel()
    private val viewModel: PostDetailViewModel by fragmentViewModel()
    private val args by args<PostDetailArgs>()
    private val controller by lazy { PostDetailController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post_detail_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbar.setNavigationOnClickListener { dismiss() }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = controller.adapter

        val post = args.post
        if (post.unseen) {
            sharedViewModel.markAsSeen(post.id)
        }
    }

    override fun invalidate() {
        withState(viewModel) { state -> controller.setData(state) }
    }

    companion object {
        @JvmStatic
        fun newInstance(post: PostFull): PostDetailDialogFragment {
            return PostDetailDialogFragment().apply {
                arguments = bundleOf(MvRx.KEY_ARG to PostDetailArgs(post))
            }
        }
    }
}
