package yiyo.com.postreader.utils

import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState
import yiyo.com.postreader.BuildConfig

abstract class MvRxViewModel<S : MvRxState>(initialState: S) : BaseMvRxViewModel<S>(
    initialState,
    debugMode = BuildConfig.DEBUG
)