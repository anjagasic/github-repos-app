package com.example.githubreposapp.data.mappers

import com.example.githubreposapp.data.model.RepoDto
import com.example.githubreposapp.ui.RepoUI

fun RepoDto.toUI(): RepoUI {
    return RepoUI(
        name = this.name,
        openIssuesCount = this.openIssuesCount,
    )
}