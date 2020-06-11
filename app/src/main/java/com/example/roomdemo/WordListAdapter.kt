package com.example.roomdemo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WordListAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mInflate: LayoutInflater = LayoutInflater.from(context)
    private var mWords = emptyList<Word>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        WordViewHolder(
            mInflate.inflate(R.layout.layout_recyclerview_item, parent, false)
        )

    override fun getItemCount(): Int = mWords.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is WordViewHolder) {
            holder.wordText.text = mWords[position].word
        }
    }

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordText: TextView = itemView.findViewById(R.id.textView)
    }

    fun setData(words: List<Word>) {
        this.mWords = words
        notifyDataSetChanged()
    }
}