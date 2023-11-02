package com.example.todolist

import MyDatabaseHelper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.todolist.database.TodoDatabase
import com.example.todolist.database.TodoRepository
import com.example.todolist.model.TodoItem
import com.example.todolist.viewModel.TodoViewModel
import com.example.todolist.viewModel.TodoViewModelFactory

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)


        val addButton = findViewById<Button>(R.id.add_button)
        addButton.setOnClickListener {
            val title_input = findViewById<EditText>(R.id.title_input).text.toString()
            val author_input = findViewById<EditText>(R.id.author_input).text.toString()
            val pages_input = findViewById<EditText>(R.id.pages_input).text.toString()
            val book_link_input = findViewById<EditText>(R.id.book_link_input).text.toString()
            val database = TodoDatabase.getDatabase(this)
            val repository = TodoRepository(database.todoItemDao())
            val todoViewModel = ViewModelProvider(this, TodoViewModelFactory(repository)).get(TodoViewModel::class.java)
            val todo = TodoItem(0,title_input,author_input,pages_input,book_link_input)
            Log.d("YourTag", "Your log message here ${todo}")
            todoViewModel.insert(todo)
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}