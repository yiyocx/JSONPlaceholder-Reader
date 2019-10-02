package yiyo.com.postreader.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import yiyo.com.postreader.R
import yiyo.com.postreader.ui.posts.PostListFragment


class SectionsPagerAdapter(fragment: FragmentActivity) :
    FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        return PostListFragment.newInstance()
    }

    override fun getItemCount(): Int = TAB_TITLES.size

    companion object {
        val TAB_TITLES = arrayOf(R.string.tab_text_1, R.string.tab_text_2)
    }
}