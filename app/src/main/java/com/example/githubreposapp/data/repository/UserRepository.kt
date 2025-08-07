package com.example.githubreposapp.data.repository

import com.example.githubreposapp.data.network.GithubApiService
import com.example.githubreposapp.data.model.UserDto
import com.example.githubreposapp.utils.UiState
import com.example.githubreposapp.utils.safeApiCall

class UserRepository(private val api: GithubApiService) {

    suspend fun getUserData(): UiState<UserDto> {
        return safeApiCall { api.getUserDetails() }
    }
}