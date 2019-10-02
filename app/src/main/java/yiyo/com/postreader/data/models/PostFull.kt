package yiyo.com.postreader.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostFull(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
    val unseen: Boolean,
    val favorite: Boolean = false
) : Parcelable