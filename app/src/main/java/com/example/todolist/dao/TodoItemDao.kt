package com.example.todolist.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todolist.model.TodoItem

@Dao
interface TodoItemDao {
    @Insert
    fun insert(todoItem: TodoItem)

    @Update
    fun update(todoItem: TodoItem)

    @Delete
    fun delete(todoItem: TodoItem)

    @Query("DELETE FROM todo_items WHERE _id = :todoId")
    fun deleteTodoById(todoId: Long)

    @Query("SELECT * FROM todo_items")
    fun getAllTodoItems(): LiveData<List<TodoItem>>
}