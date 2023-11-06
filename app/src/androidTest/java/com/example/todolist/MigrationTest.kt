package com.example.todolist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.todolist.database.MigrationScript
import com.example.todolist.database.TodoDatabase
import com.example.todolist.model.TodoItem
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals
import org.junit.Assert.fail


@RunWith(AndroidJUnit4::class)
class MigrationTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    val migrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        TodoDatabase::class.java.canonicalName,
        FrameworkSQLiteOpenHelperFactory()
    )
    @Test
    fun testMigrations() {
        val db = Room.databaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            TodoDatabase::class.java,
            "my_todo_database"
        ).addMigrations(MigrationScript.MIGRATION_1_2)
            .build()

        val todoItem = TodoItem(
            title = "java",
            author = "vishnu",
            pages = "155",
            book_link = "https://www.facebook.com/"
        )

        runBlocking {
            db.todoItemDao().insert(todoItem)
        }

        migrationTestHelper.runMigrationsAndValidate(
            "my_todo_database",
            2,
            true,
            MigrationScript.MIGRATION_1_2
        )


        val todoItems: LiveData<List<TodoItem>> = db.todoItemDao().getAllTodoItems()

        todoItems.observeForever { todoItems ->
            if (todoItems?.isNotEmpty() == true) {
                val retrievedTodoItem = todoItems[0]
                val bookLink = retrievedTodoItem.book_link
                assertEquals("https://www.facebook.com/", bookLink)
            } else {
                fail("No data found in the migrated table")
            }
        }

    }
}