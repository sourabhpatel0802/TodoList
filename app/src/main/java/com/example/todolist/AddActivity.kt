package com.example.todolist

import MyDatabaseHelper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        val title_input = findViewById<EditText>(R.id.title_input)
        val author_input = findViewById<EditText>(R.id.author_input)
        val pages_input = findViewById<EditText>(R.id.pages_input)

        val addButton = findViewById<Button>(R.id.add_button)
        addButton.setOnClickListener {
            val myDb = MyDatabaseHelper(this)
            myDb.addBook(title_input.text.toString(),author_input.text.toString(),pages_input.text.toString())
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}