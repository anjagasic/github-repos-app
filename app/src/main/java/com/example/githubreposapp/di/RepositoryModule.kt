package com.example.githubreposapp.di

import com.example.githubreposapp.data.repository.UserReposRepository
import com.example.githubreposapp.data.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
   single { UserReposRepository(get()) }
   single { UserRepository(get()) }
}