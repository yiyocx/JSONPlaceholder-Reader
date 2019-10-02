package yiyo.com.postreader.data.services

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import yiyo.com.postreader.data.models.User

interface UserService {

    @GET("/users/{id}")
    fun getUserInfo(@Path("id") userId: Int): Single<User>
}