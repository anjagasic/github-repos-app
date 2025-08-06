package com.example.githubreposapp.data

import com.example.githubreposapp.data.model.RepoDto
import retrofit2.Response
import retrofit2.http.GET

interface Service {

    @GET("users/octocat/repos")
    suspend fun getRepos(): Response<List<RepoDto>>
}