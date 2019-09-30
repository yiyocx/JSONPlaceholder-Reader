package yiyo.com.postreader.data.services

import io.reactivex.Observable
import retrofit2.http.GET
import yiyo.com.postreader.data.models.Post

interface PostService {

    @GET("/posts")
    fun getPosts(): Observable<List<Post>>
}