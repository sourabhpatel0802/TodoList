package com.example.todolist

import MyDatabaseHelper
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.adapter.CustomAdapter


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val addButton = findViewById<ImageView>(R.id.add_button)
        addButton.setOnClickListener{
            val intent = Intent(this,AddActivity::class.java)
            startActivity(intent)
        }
        val myDb = MyDatabaseHelper(this)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val list:ArrayList<ArrayList<String>> = storeDataInArrays(myDb)
        val customAdapter = CustomAdapter(this,this,list[0],list[1],list[2],list[3])
        recyclerView.adapter = customAdapter
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
                val id = customAdapter.getIdByPosition(position);
                myDb.deleteItemFromDatabase(id)
                customAdapter.removeAtPosition(position)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun storeDataInArrays(myDb:MyDatabaseHelper):ArrayList<ArrayList<String>> {
        return myDb.readAllData()
    }


}