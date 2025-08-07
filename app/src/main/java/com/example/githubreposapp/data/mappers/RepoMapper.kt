package com.example.githubreposapp.data.mappers

import com.example.githubreposapp.data.model.RepoDetailsDto
import com.example.githubreposapp.data.model.RepoDto
import com.example.githubreposapp.data.model.TagDto
import com.example.githubreposapp.ui.RepoDetailsUI
import com.example.githubreposapp.ui.RepoUI
import com.example.githubreposapp.ui.TagUI

fun RepoDto.toUI(): RepoUI {
    return RepoUI(
        name = this.name,
        openIssuesCount = this.openIssuesCount,
    )
}

fun TagDto.toUI(): TagUI {
    return TagUI(
        name = this.name,
        commitSha = this.commit.sha,
    )
}

fun RepoDetailsDto.toUI(): RepoDetailsUI {
    return RepoDetailsUI(
        name = this.name,
        watchersCount = this.watchersCount,
        forksCount = this.forksCount
    )
}