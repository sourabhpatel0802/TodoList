package com.example.todolist

import MyDatabaseHelper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

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
            val id = getIntent().getStringExtra("id").toString()
            val myDb = MyDatabaseHelper(this)
            Log.d("test data is","${title}")
            myDb.updateBook(title,author,pages,id)
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getAndSetIntentData() {

        findViewById<EditText>(R.id.title_input).setText(getIntent().getStringExtra("title"))
        findViewById<EditText>(R.id.author_input).setText(getIntent().getStringExtra("author").toString())
        findViewById<EditText>(R.id.pages_input).setText(getIntent().getStringExtra("pages").toString())

    }

}