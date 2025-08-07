package com.example.githubreposapp.data.model

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("name")
    val fullName: String,
    @SerializedName("avatar_url")
    val avatarImage: String?,
    @SerializedName("login")
    val userName: String
)