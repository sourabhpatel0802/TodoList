package com.example.todolist.model

data class Cover(
    val archived: Boolean,
    val author: String,
    val category_id: Int,
    val created: String,
    val deleted: Boolean,
    val failed: Boolean,
    val filename: String,
    val filename_l: String,
    val filename_m: String,
    val filename_s: String,
    val height: Int,
    val id: Int,
    val ip: String,
    val isbn: Any,
    val isbn13: Any,
    val last_modified: String,
    val olid: String,
    val source: Any,
    val source_url: String,
    val uploaded: Boolean,
    val width: Int
)