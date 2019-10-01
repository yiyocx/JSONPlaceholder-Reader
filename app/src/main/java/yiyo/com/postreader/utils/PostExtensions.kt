package yiyo.com.postreader.utils

import yiyo.com.postreader.data.models.Post
import yiyo.com.postreader.data.models.PostFull

fun Post.toPostFull(unseen: Boolean): PostFull {
    return PostFull(userId, id, title, body, unseen)
}