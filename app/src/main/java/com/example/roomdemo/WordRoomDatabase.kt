package com.example.roomdemo

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordRoomDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao

    companion object {

        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): WordRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    WordRoomDatabase::class.java, "word_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(WordDatabaseCallBack(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class WordDatabaseCallBack(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        //Note: If you only want to populate the database the first time the app is launched,
        // you can override the onCreate() method within the RoomDatabase.Callback.

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            //Add some data when the database is opened
            //Because you cannot do Room database operations on the UI thread, onOpen() launches a coroutine on the IO Dispatcher.
            INSTANCE?.let {database->
                scope.launch {
                    var wordDao = database.wordDao()
                    wordDao.deleteAll()

                    wordDao.insert(Word(0,"hello"))
                    wordDao.insert(Word(0,"word!"))
                    wordDao.insert(Word(0,"room demo"))

                }
            }

        }

    }
}

