import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class MyDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    val database = this.writableDatabase
    companion object {
        private const val DATABASE_NAME = "todoList.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Create your database tables
        db.execSQL("CREATE TABLE my_todo (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, author TEXT, pages TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS my_todo")
        onCreate(db)
    }

    fun addBook(title:String, author:String, pages:String){
        val values = ContentValues()
        values.put("pages", pages)
        values.put("title", title)
        values.put("author", author)
        val result = database.insert("my_todo", null, values)
        if(result.equals(-1)){
            Log.d("Faild to insert","fail")
        }
        else{
            Log.d("Inserted Successfully","data ${title}, ${author}, ${pages}")
        }
    }
    fun updateBook(title: String, author: String, pages: String,id: String) {
        val values = ContentValues()
        values.put("title", title)
        values.put("author", author)
        values.put("pages", pages)
        val whereClause = "_id = ?"
        val whereArgs = arrayOf(id)
        val rowsUpdated = database.update("my_todo", values, whereClause, whereArgs)

    }

    fun deleteItemFromDatabase(itemId: String) {
        val whereClause = "_id = ?"
        val whereArgs = arrayOf(itemId)

        // Delete the item from the database
        database.delete("my_todo", whereClause, whereArgs)
    }

    @SuppressLint("Range")
    fun readAllData():ArrayList<ArrayList<String>>{
        val list = ArrayList<ArrayList<String>>()
        val bookId = ArrayList<String>()
        val bookTitle = ArrayList<String>()
        val bookAuthor = ArrayList<String>()
        val bookPages = ArrayList<String>()
        val cursor = database.query("my_todo", null, null, null, null, null, null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex("_id")).toString()
            val title = cursor.getString(cursor.getColumnIndex("title")).toString()
            val author = cursor.getString(cursor.getColumnIndex("author")).toString()
            val pages = cursor.getString(cursor.getColumnIndex("pages")).toString()
            bookId.add(id)
            bookTitle.add(title)
            bookAuthor.add(author)
            bookPages.add(pages)
        }
        cursor.close()
        list.add(bookId)
        list.add(bookTitle)
        list.add(bookAuthor)
        list.add(bookPages)
        return list
    }
}

