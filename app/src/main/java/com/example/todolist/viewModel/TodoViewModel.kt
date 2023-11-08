package com.example.todolist.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todolist.database.TodoRepository
import com.example.todolist.model.Cover
import com.example.todolist.model.TodoItem
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoRepository) : ViewModel() {
    val allTodoItem:LiveData<List<TodoItem>> = repository.allTodoItem
    private val _cover = MutableLiveData<Cover>()
    val cover: LiveData<Cover> get() = _cover

    fun getcoverByIsbn(isbn: Int) {
        viewModelScope.launch {
            val items = repository.getCoverByIsbn(isbn)
            _cover.postValue(items)
        }
    }
    fun insert(todoItem: TodoItem) {
        Log.d("add data on todo is","${todoItem}")
        viewModelScope.launch {
            repository.insert(todoItem)
        }
    }

    fun update(todoItem: TodoItem) {
        viewModelScope.launch {
            repository.update(todoItem)
        }
    }

    fun delete(todoItem: TodoItem) {
        viewModelScope.launch {
            repository.delete(todoItem)
        }
    }

    fun deleteTodoById(todoId: Long){
        viewModelScope.launch {
            repository.deleteTodoById(todoId)
        }
    }
}

class TodoViewModelFactory(private val repository: TodoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TodoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
