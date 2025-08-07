package com.example.githubreposapp.di

import com.example.githubreposapp.ui.repoDetails.RepoDetailsViewModel
import com.example.githubreposapp.ui.userRepos.UserReposViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { UserReposViewModel(get()) }
    viewModel { (repoName: String) ->
        RepoDetailsViewModel(
            userReposRepository = get(),
            userRepository = get(),
            repoName = repoName
        )
    }
}