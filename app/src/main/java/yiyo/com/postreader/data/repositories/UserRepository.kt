package yiyo.com.postreader.data.repositories

import yiyo.com.postreader.data.services.UserService
import javax.inject.Inject

class UserRepository @Inject constructor(private val userService: UserService) {

    fun getUserInfo(userId: Int) = userService.getUserInfo(userId)
}