package com.example.githubreposapp.ui.repoDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubreposapp.data.mappers.toUI
import com.example.githubreposapp.data.repository.UserReposRepository
import com.example.githubreposapp.data.repository.UserRepository
import com.example.githubreposapp.ui.common.model.RepoDetailsUI
import com.example.githubreposapp.ui.common.model.TagUI
import com.example.githubreposapp.ui.common.model.UserUI
import com.example.githubreposapp.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RepoDetailsViewModel(
    private val userReposRepository: UserReposRepository,
    private val userRepository: UserRepository,
    private val repoName: String
) : ViewModel() {
    private val _tags = MutableStateFlow<UiState<List<TagUI>>>(UiState.Loading)
    val tags: StateFlow<UiState<List<TagUI>>> = _tags

    private val _repoDetails = MutableStateFlow<UiState<RepoDetailsUI>>(UiState.Loading)
    val repoDetails: StateFlow<UiState<RepoDetailsUI>> = _repoDetails

    private val _userData = MutableStateFlow<UiState<UserUI>>(UiState.Loading)
    val userData: StateFlow<UiState<UserUI>> = _userData

    val isLoading: StateFlow<Boolean> = combine(
        userData,
        repoDetails,
        tags
    ) { userState, repoState, tagsState ->
        listOf(userState, repoState, tagsState).any { it is UiState.Loading }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        true
    )

    init {
        fetchRepoTags()
        fetchRepoDetails()
        fetchUserData()
    }

    fun fetchRepoTags() {
        viewModelScope.launch {
            _tags.value = UiState.Loading
            val result = userReposRepository.getRepoTags(repoName)

            _tags.value = when (result) {
                is UiState.Success -> UiState.Success(result.data.map { it.toUI() })
                is UiState.Error -> UiState.Error(result.message)
                is UiState.Loading -> UiState.Loading
            }
        }
    }

    private fun fetchRepoDetails() {
        viewModelScope.launch {
            _repoDetails.value = UiState.Loading
            val result = userReposRepository.getRepoDetails(repoName)

            _repoDetails.value = when (result) {
                is UiState.Success -> UiState.Success(result.data.toUI())
                is UiState.Error -> UiState.Error(result.message)
                is UiState.Loading -> UiState.Loading
            }
        }
    }

    private fun fetchUserData() {
        viewModelScope.launch {
            _userData.value = UiState.Loading
            val result = userRepository.getUserData()

            _userData.value = when (result) {
                is UiState.Success -> UiState.Success(result.data.toUI())
                is UiState.Error -> UiState.Error(result.message)
                is UiState.Loading -> UiState.Loading
            }
        }
    }
}