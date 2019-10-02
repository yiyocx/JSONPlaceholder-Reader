package yiyo.com.postreader.ui.detail

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import yiyo.com.postreader.data.models.PostFull

@Parcelize
class PostDetailArgs(val post: PostFull) : Parcelable