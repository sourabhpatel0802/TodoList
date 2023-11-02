package com.example.todolist

import MyDatabaseHelper
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.todolist.adapter.CustomAdapter
import com.example.todolist.adapter.TodoAdapter
import com.example.todolist.database.TodoDatabase
import com.example.todolist.database.TodoRepository
import com.example.todolist.model.TodoItem
import com.example.todolist.viewModel.TodoViewModel
import com.example.todolist.viewModel.TodoViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Dispatchers.IO
        setContentView(R.layout.activity_main)
        val addButton = findViewById<ImageView>(R.id.add_button)
        addButton.setOnClickListener{
            val intent = Intent(this,AddActivity::class.java)
            startActivity(intent)
        }
        val database = TodoDatabase.getDatabase(this)
        val repository = TodoRepository(database.todoItemDao())
        val todoViewModel = ViewModelProvider(this, TodoViewModelFactory(repository)).get(
            TodoViewModel::class.java)
        val list = ArrayList<TodoItem>()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val todoAdapter = TodoAdapter(this,this,list)
        recyclerView.adapter = todoAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val id = todoAdapter.getIdByPosition(position);
                todoViewModel.deleteTodoById(id.toLong())
                todoAdapter.removeAtPosition(position)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        todoViewModel.allTodoItem.observe(this, { todoItems ->
            list.clear()
            list.addAll(todoItems)
            recyclerView.adapter?.notifyDataSetChanged()
        })

    }



}