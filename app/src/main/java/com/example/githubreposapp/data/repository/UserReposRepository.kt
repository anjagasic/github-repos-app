package com.example.githubreposapp.data.repository

import com.example.githubreposapp.data.Service
import com.example.githubreposapp.data.model.RepoDto
import com.example.githubreposapp.utils.UiState
import com.example.githubreposapp.utils.safeApiCall

class UserReposRepository(private val api: Service) {

    suspend fun getUserRepos(): UiState<List<RepoDto>> {
        return safeApiCall { api.getRepos() }
    }
}