package yiyo.com.postreader.ui.favorites


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.activityViewModel
import com.airbnb.mvrx.withState
import kotlinx.android.synthetic.main.fragment_post_list.*
import yiyo.com.postreader.R
import yiyo.com.postreader.ui.MainViewModel
import yiyo.com.postreader.ui.posts.PostsController

class FavoritesFragment : BaseMvRxFragment() {

    private val sharedViewModel: MainViewModel by activityViewModel()
    private val postsController by lazy { PostsController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = postsController.adapter
    }

    override fun invalidate() = withState(sharedViewModel) { state ->
        postsController.setData(state.favorites)
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavoritesFragment()
    }
}
