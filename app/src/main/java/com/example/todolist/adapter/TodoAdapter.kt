package com.example.todolist.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
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
import com.example.todolist.database.TodoRepository
import com.example.todolist.model.Cover
import com.example.todolist.model.TodoItem
import com.example.todolist.retrofit.RetrofitClient
import com.example.todolist.viewModel.TodoViewModel

class TodoAdapter(
    private val activity: Activity,
    private val context: Context,
    private val todoList: List<TodoItem>,
    private var todoViewModel: TodoViewModel
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
            intent.putExtra("book_link", item.book_link.toString())
            activity.startActivityForResult(intent,1)
        }
        val bookLink = holder.mainLayout.findViewById<TextView>(R.id.book_link)

        bookLink.setOnClickListener {
            todoViewModel.getcoverByIsbn(item.isbn.toInt())
            todoViewModel?.cover?.observeForever { cover ->
                Log.d("my bok cover url is ","url-> ${cover.source_url}")
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(cover.source_url))
                activity.startActivityForResult(intent,1)
            }
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