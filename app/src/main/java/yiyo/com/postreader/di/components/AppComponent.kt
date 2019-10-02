package yiyo.com.postreader.di.components

import dagger.Component
import yiyo.com.postreader.di.modules.AssistedInjectModule
import yiyo.com.postreader.di.modules.NetworkModule
import yiyo.com.postreader.ui.MainActivity
import yiyo.com.postreader.ui.detail.PostDetailDialogFragment

@Component(modules = [AssistedInjectModule::class, NetworkModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: PostDetailDialogFragment)
}