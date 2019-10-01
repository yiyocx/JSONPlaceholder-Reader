package yiyo.com.postreader.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import yiyo.com.postreader.R

val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2
)

class SectionsPagerAdapter(fragment: FragmentActivity) :
    FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        return PlaceholderFragment.newInstance()
    }

    override fun getItemCount(): Int = TAB_TITLES.size
}