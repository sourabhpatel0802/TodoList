package com.example.todolist.database

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.todolist.dao.TodoItemDao
import com.example.todolist.model.TodoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TodoRepository(private val todoItemDao: TodoItemDao) {
    val allTodoItem: LiveData<List<TodoItem>> = todoItemDao.getAllTodoItems()
    suspend fun insert(todoItem: TodoItem) {
        withContext(Dispatchers.IO){
            todoItemDao.insert(todoItem)
        }
    }

    suspend fun update(todoItem: TodoItem){
        withContext(Dispatchers.IO){
           todoItemDao.update(todoItem)
        }
    }

    suspend fun  delete(todoItem: TodoItem){
        withContext(Dispatchers.IO) {
            todoItemDao.delete(todoItem)
        }
    }

    suspend fun deleteTodoById(todoId: Long){
        withContext(Dispatchers.IO) {
            todoItemDao.deleteTodoById(todoId)
        }
    }

}
