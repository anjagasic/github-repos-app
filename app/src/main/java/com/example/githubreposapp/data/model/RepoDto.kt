package com.example.githubreposapp.data.model

import com.google.gson.annotations.SerializedName

data class RepoDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("open_issues_count")
    val openIssuesCount: Int
)