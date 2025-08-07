package com.example.githubreposapp.data

import com.example.githubreposapp.data.model.RepoDetailsDto
import com.example.githubreposapp.data.model.RepoDto
import com.example.githubreposapp.data.model.TagDto
import com.example.githubreposapp.data.model.UserDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Service {

    @GET("users/octocat/repos")
    suspend fun getRepos(): Response<List<RepoDto>>

    @GET("users/octocat")
    suspend fun getUserDetails(): Response<UserDto>

    @GET("repos/octocat/{repo}")
    suspend fun getRepoDetails(@Path("repo") repo: String): Response<RepoDetailsDto>

    @GET("repos/octocat/{repo}/tags")
    suspend fun getTags(@Path("repo") repo: String): Response<List<TagDto>>
}