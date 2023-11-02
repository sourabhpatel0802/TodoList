package com.example.todolist.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.UpdateActivity
import com.example.todolist.model.TodoItem

class TodoAdapter(
    private val activity: Activity,
    private val context: Context,
    private val todoList: List<TodoItem>
):
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(context).inflate(R.layout.my_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TodoAdapter.TodoViewHolder, position: Int) {
        val item = todoList[position]
        holder.book_id_txt.text = item._id.toString()
        holder.book_title_txt.text = item.title.toString()
        holder.book_author_txt.text = item.author.toString()
        holder.book_pages_txt.text = item.pages.toString()
        holder.mainLayout.setOnClickListener {
            val intent = Intent(context, UpdateActivity::class.java)
            intent.putExtra("id", item._id.toString())
            intent.putExtra("title", item.title.toString())
            intent.putExtra("author", item.author.toString())
            intent.putExtra("pages", item.pages.toString())
            activity.startActivityForResult(intent,1)
        }
    }

    fun removeAtPosition(position: Int){
        /*val updatedList = todoList.toMutableList()
        updatedList.removeAt(position)
        todoList = updatedList.toList()
        notifyItemRemoved(position)*/
    }
    fun getIdByPosition(position: Int): String{
        return todoList[position]._id.toString()
    }
    override fun getItemCount(): Int {
        return todoList.size
    }


    inner class TodoViewHolder(@NonNull itemView: View): RecyclerView.ViewHolder(itemView){
        val book_id_txt: TextView = itemView.findViewById(R.id.book_id_txt)
        val book_title_txt: TextView = itemView.findViewById(R.id.book_title_txt)
        val book_author_txt: TextView = itemView.findViewById(R.id.book_author_txt)
        val book_pages_txt: TextView = itemView.findViewById(R.id.book_pages_txt)
        val mainLayout: LinearLayout = itemView.findViewById(R.id.mainLayout)

    }

    /*interface TodoClickListener {
        fun onItemClicked(todo: Todo)
    }*/
}