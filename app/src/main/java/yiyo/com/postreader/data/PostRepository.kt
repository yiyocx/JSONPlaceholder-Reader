package yiyo.com.postreader.data

import io.reactivex.Observable
import yiyo.com.postreader.data.models.Post
import yiyo.com.postreader.data.services.PostService
import javax.inject.Inject

class PostRepository @Inject constructor(private val postService: PostService) {

    fun getPosts(): Observable<List<Post>> = postService.getPosts()
}