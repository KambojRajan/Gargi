package com.darkrai.gargi.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class User(
    val username:String,
    val profileImg:String?,
    val balance:Float,
    val userId:String,
    val password:String,
    val isAdmin:Boolean?=false,
    val email:String
)

fun UserDto.toUser(): User {
    return User(
        username = this.username,
        profileImg = this.profileImg,
        balance = this.balance ?: 0f,
        userId = this.userId,
        password = this.password ?: "",
        isAdmin = this.isAdmin,
        email = this.email
    )
}


@Serializable
data class UserDto(

    @SerialName("id")
    val id:Int = 0,

    @SerialName("username")
    val username:String = "",

    @SerialName("password")
    val password:String ?= "",

    @SerialName("email")
    val email:String = "",

    @SerialName("balance")
    val balance:Float ?= 0f,

    @SerialName("is_admin")
    val isAdmin: Boolean = false,

    @SerialName("user_id")
    val userId:String = "",

    @SerialName("profile_img")
    val profileImg:String?,
)