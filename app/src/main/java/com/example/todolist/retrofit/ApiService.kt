package com.example.todolist.retrofit

import com.example.todolist.model.Cover
import com.example.todolist.model.TodoItem
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("{id}.json")
    suspend fun getCoverByIsbn(@Path("id") isbn: Int): Cover
}