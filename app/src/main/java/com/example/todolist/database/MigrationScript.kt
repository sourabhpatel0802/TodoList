package com.example.todolist.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class MigrationScript {
    companion object {
        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS `todo_items_new` " +
                        "(`_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        "`title` TEXT NOT NULL, " +
                        "`author` TEXT NOT NULL, " +
                        "`pages` TEXT NOT NULL, " +
                        "`book_link` TEXT NOT NULL)")

                // Copy the data from the old table to the new table
                database.execSQL("INSERT INTO `todo_items_new` (`_id`, `title`, `author`, `pages`, `book_link`) " +
                        "SELECT `_id`, `title`, `author`, `pages`, '' FROM `todo_items`")

                // Drop the old table
                database.execSQL("DROP TABLE `todo_items`")

                // Rename the new table to the original table name
                database.execSQL("ALTER TABLE `todo_items_new` RENAME TO `todo_items`")
            }
        }
    }
}