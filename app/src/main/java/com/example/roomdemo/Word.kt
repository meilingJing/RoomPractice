package com.example.roomdemo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//表名为word_table,若不设置则默认为Word;（SQLite表明不区分大小写）
@Entity(tableName = "word_table")
//注解Entity的primaryKeys属性用来设置复合主键
//@Entity(tableName = "word_table",primaryKeys = ["id", "word"])
data class Word(
    //主键（autoGenerate自动分配ID）
    @PrimaryKey(autoGenerate = true) var id: Int,
    //表中的列名（如果不设定nam属性值，默认为字段名）
    @ColumnInfo(name = "word") val word: String)