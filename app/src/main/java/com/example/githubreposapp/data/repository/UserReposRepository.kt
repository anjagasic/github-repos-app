package com.example.githubreposapp.data.repository

import com.example.githubreposapp.data.network.GithubApiService
import com.example.githubreposapp.data.model.RepoDetailsDto
import com.example.githubreposapp.data.model.RepoDto
import com.example.githubreposapp.data.model.TagDto
import com.example.githubreposapp.utils.UiState
import com.example.githubreposapp.utils.safeApiCall

class UserReposRepository(private val api: GithubApiService) {

    suspend fun getUserRepos(): UiState<List<RepoDto>> {
        return safeApiCall { api.getRepos() }
    }

    suspend fun getRepoTags(repo: String): UiState<List<TagDto>> {
        return safeApiCall { api.getTags(repo) }
    }

    suspend fun getRepoDetails(repo: String): UiState<RepoDetailsDto> {
        return safeApiCall { api.getRepoDetails(repo) }
    }
}