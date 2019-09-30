package yiyo.com.postreader.utils

import android.app.Activity
import androidx.fragment.app.Fragment
import yiyo.com.postreader.App

val Activity.component get() = (application as App).component

val Fragment.component get() = requireActivity().component