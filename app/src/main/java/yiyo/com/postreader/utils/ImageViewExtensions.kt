package yiyo.com.postreader.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("android:visibility")
fun ImageView.setVisibility(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}