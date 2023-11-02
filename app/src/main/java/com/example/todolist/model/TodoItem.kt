package com.example.todolist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_items")
data class TodoItem(
    @PrimaryKey(autoGenerate = true)val _id: Long = 0,
    @ColumnInfo(name = "title")val title: String,
    @ColumnInfo(name = "author")val author: String,
    @ColumnInfo(name = "pages")val pages: String,
    @ColumnInfo(name = "book_link")val book_link: String
)

