package yiyo.com.postreader

import android.app.Application
import yiyo.com.postreader.di.components.AppComponent
import yiyo.com.postreader.di.components.DaggerAppComponent

class App : Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent.builder()
            .build()
    }
}