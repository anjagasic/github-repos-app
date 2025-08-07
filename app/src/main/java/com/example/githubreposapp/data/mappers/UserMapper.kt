package com.example.githubreposapp.data.mappers

import com.example.githubreposapp.data.model.UserDto
import com.example.githubreposapp.ui.UserUI

fun UserDto.toUI(): UserUI {
    return UserUI(
        fullName = this.fullName,
        avatarImage = this.avatarImage,
        userName = this.userName,
    )
}