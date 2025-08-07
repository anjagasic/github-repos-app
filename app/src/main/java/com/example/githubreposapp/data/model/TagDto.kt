package com.example.githubreposapp.data.model

import com.google.gson.annotations.SerializedName

data class TagDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("commit")
    val commit: CommitDto
)

data class CommitDto(
    @SerializedName("sha")
    val sha: String
)