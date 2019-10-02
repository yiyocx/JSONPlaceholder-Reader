package yiyo.com.postreader.data.services

import io.reactivex.Single
import retrofit2.http.GET
import yiyo.com.postreader.data.models.Post

interface PostService {

    @GET("/posts")
    fun getPosts(): Single<List<Post>>
}