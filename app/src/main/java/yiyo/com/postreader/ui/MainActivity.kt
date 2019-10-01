package yiyo.com.postreader.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.mvrx.BaseMvRxActivity
import com.airbnb.mvrx.viewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import yiyo.com.postreader.R
import yiyo.com.postreader.ui.posts.SectionsPagerAdapter
import yiyo.com.postreader.ui.posts.TAB_TITLES
import yiyo.com.postreader.utils.component
import javax.inject.Inject

class MainActivity : BaseMvRxActivity() {

    @Inject
    lateinit var viewModelFactory: MainViewModel.Factory

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        component.inject(this)

        setSupportActionBar(findViewById(R.id.toolbar))

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.isUserInputEnabled = false
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = getString(TAB_TITLES[position])
        }.attach()

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            viewModel.removeAllPosts()
            Snackbar.make(it, R.string.all_post_deleted, Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.reload -> viewModel.loadAllPosts().let { true }
            else -> super.onOptionsItemSelected(item)
        }
    }
}