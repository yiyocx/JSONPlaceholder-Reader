package yiyo.com.postreader.ui.detail

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.mvrx.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val customDialog = super.onCreateDialog(savedInstanceState)
        customDialog.setOnShowListener { dialog ->
            val sheetDialog = dialog as BottomSheetDialog

            val bottomSheet =
                sheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
        }
        return customDialog
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

        fab.setOnClickListener { viewModel.toggleFavorite() }

        viewModel.selectSubscribe(this, PostDetailState::post) {
            val favoriteIcon = when {
                it.favorite -> R.drawable.ic_star_filled
                else -> R.drawable.ic_star_empty
            }
            fab.setImageResource(favoriteIcon)

            sharedViewModel.handleFavorite(it)
        }

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
