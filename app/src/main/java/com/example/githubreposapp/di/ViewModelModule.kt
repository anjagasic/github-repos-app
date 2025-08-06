package com.example.githubreposapp.di

import com.example.githubreposapp.ui.UserReposViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
   viewModel { UserReposViewModel(get()) }
}