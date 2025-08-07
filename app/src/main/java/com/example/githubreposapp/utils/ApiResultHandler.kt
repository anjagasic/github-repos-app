package com.example.githubreposapp.utils

import com.example.githubreposapp.data.model.ErrorMessage
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import retrofit2.Response
import java.io.IOException

suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): UiState<T> {
    return try {
        val response = apiCall()

        if (response.isSuccessful && response.code() in 200..299) {
            val body = response.body()
            if (body != null) {
                UiState.Success(body)
            } else {
                UiState.Error("Empty response body")
            }
        } else {
            val errorMessage = response.errorBody()?.string()

            val errorMsg = if (errorMessage.isNullOrBlank()) {
                response.message()
            } else {
                try {
                    GsonBuilder().create()
                        .fromJson(errorMessage, ErrorMessage::class.java).message
                } catch (e: JsonSyntaxException) {
                    errorMessage
                }
            }
            UiState.Error(errorMsg)
        }
    } catch (e: IOException) {
        UiState.Error("Network Error: ${e.message}")
    } catch (e: Exception) {
        UiState.Error("Unexpected Error: ${e.message}")
    }
}
