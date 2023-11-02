package com.example.todolist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todolist.dao.TodoItemDao
import com.example.todolist.model.TodoItem

@Database(entities = [TodoItem::class], version = 2, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoItemDao(): TodoItemDao

    companion object {
        @Volatile
        private var INSTANCE: TodoDatabase? = null

        fun getDatabase(context: Context): TodoDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    "todo_database"
                )
                    .addMigrations(MigrationScript.MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
