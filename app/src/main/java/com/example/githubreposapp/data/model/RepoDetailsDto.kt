package com.example.githubreposapp.data.model

import com.google.gson.annotations.SerializedName

data class RepoDetailsDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("watchers_count")
    val watchersCount: Int,
    @SerializedName("forks_count")
    val forksCount: Int
)