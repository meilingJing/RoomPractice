package com.example.roomdemo

import androidx.lifecycle.LiveData
import androidx.room.*

//对于Room来说这是一个Dao class
@Dao
interface WordDao {
    //查询语句,返回对象为LiveData时自动在后台子线程运行
    @Query("SELECT * from word_table ORDER BY word ASC")
    fun getAlphabetizedWords(): LiveData<List<Word>>

    //用suspend关键字 是因为操作数据库在协程或另一个挂起函数中调用
    //此注解不需要SQL语句
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()

}