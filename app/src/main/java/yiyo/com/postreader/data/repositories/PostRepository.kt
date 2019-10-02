package yiyo.com.postreader.data.repositories

import io.reactivex.Single
import yiyo.com.postreader.data.models.Post
import yiyo.com.postreader.data.services.PostService
import javax.inject.Inject

class PostRepository @Inject constructor(private val postService: PostService) {

    fun getPosts(): Single<List<Post>> = postService.getPosts()

    fun getCommentsForPost(postId: Int) = postService.getCommentsForPost(postId)
}