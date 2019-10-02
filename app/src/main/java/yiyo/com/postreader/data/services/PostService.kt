package yiyo.com.postreader.data.services

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import yiyo.com.postreader.data.models.Comment
import yiyo.com.postreader.data.models.Post

interface PostService {

    @GET("/posts")
    fun getPosts(): Single<List<Post>>

    @GET("posts/{id}/comments")
    fun getCommentsForPost(@Path("id") postId: Int): Single<List<Comment>>
}