package com.example.roomdemo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val wordViewModel: WordViewModel by lazy {
        ViewModelProvider(this).get(WordViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()

    }

    private fun initView() {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = WordListAdapter(this)
        recyclerView.adapter = adapter

        val floatingButton: FloatingActionButton = findViewById(R.id.fab)
        floatingButton.setOnClickListener {
            startActivityForResult(
                Intent(this, NewWordActivity::class.java),
                NEWWORDACTIVITYREQUESTCODE
            )
        }

        wordViewModel.allWords.observe(this, Observer { words ->
            words?.let {
                adapter.setData(it)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NEWWORDACTIVITYREQUESTCODE && resultCode == Activity.RESULT_OK) {
            var word = data?.getStringExtra(NewWordActivity.EXTRA_REPLY) ?: ""
            word.takeIf { it.isNotEmpty() }?.let {
                wordViewModel.insert(Word(0, it))
            }

        } else {
            Toast.makeText(this, R.string.empty_not_save, Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        private const val NEWWORDACTIVITYREQUESTCODE = 1
    }

}
