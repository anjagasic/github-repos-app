package com.example.githubreposapp.utils

import retrofit2.Response
import java.io.IOException

suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): UiState<T> {
    return try {
        val response = apiCall()

        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                UiState.Success(body)
            } else {
                UiState.Error("Empty response body")
            }
        } else {
            UiState.Error("API Error: ${response.code()} ${response.message()}")
        }
    } catch (e: IOException) {
        UiState.Error("Network Error: ${e.message}")
    } catch (e: Exception) {
        UiState.Error("Unexpected Error: ${e.message}")
    }
}
