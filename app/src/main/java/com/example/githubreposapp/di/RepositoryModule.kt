package com.example.githubreposapp.di

import com.example.githubreposapp.data.repository.UserReposRepository
import org.koin.dsl.module

val repositoryModule = module {
   single { UserReposRepository(get()) }
}