package com.example.todolist

import MyDatabaseHelper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.database.TodoDatabase
import com.example.todolist.database.TodoRepository
import com.example.todolist.model.TodoItem
import com.example.todolist.viewModel.TodoViewModel
import com.example.todolist.viewModel.TodoViewModelFactory

class UpdateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        getAndSetIntentData()
        val updateButton = findViewById<Button>(R.id.update_button)
        updateButton.setOnClickListener {
            val title = findViewById<EditText>(R.id.title_input).text.toString()
            val author = findViewById<EditText>(R.id.author_input).text.toString()
            val pages = findViewById<EditText>(R.id.pages_input).text.toString()
            val book_link = findViewById<EditText>(R.id.book_link_input).text.toString()
            val isbn = findViewById<EditText>(R.id.isbn_input).text.toString()
            val id = getIntent().getStringExtra("id").toString()
            val database = TodoDatabase.getDatabase(this)
            val repository = TodoRepository(database.todoItemDao())
            val todoViewModel = ViewModelProvider(this,TodoViewModelFactory(repository)).get(TodoViewModel::class.java)
            val todo = TodoItem(id.toLong(),title,author,pages,book_link,isbn)
            todoViewModel.update(todo)
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getAndSetIntentData() {

        findViewById<EditText>(R.id.title_input).setText(getIntent().getStringExtra("title"))
        findViewById<EditText>(R.id.author_input).setText(getIntent().getStringExtra("author").toString())
        findViewById<EditText>(R.id.pages_input).setText(getIntent().getStringExtra("pages").toString())
        findViewById<EditText>(R.id.book_link_input).setText(getIntent().getStringExtra("book_link").toString())
        findViewById<EditText>(R.id.isbn_input).setText(getIntent().getStringExtra("isbn").toString())
    }

}