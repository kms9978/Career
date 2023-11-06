package com.example.jobhunt.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jobhunt.R
import com.example.jobhunt.DataModel.Chat

class GptAdapter : RecyclerView.Adapter<GptAdapter.ViewHolder>() {

    private val chatList = mutableListOf<Chat>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val leftChatView: LinearLayout = itemView.findViewById(R.id.left_chat_view)
        val rightChatView: LinearLayout = itemView.findViewById(R.id.right_chat_view)
        val leftChatTextView: TextView = itemView.findViewById(R.id.left_chat_text_view)
        val rightChatTextView: TextView = itemView.findViewById(R.id.right_chat_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chat = chatList[position]

        if (chat.isUserPrompt) {
            holder.leftChatView.visibility = View.VISIBLE
            holder.rightChatView.visibility = View.GONE
            holder.leftChatTextView.text = chat.prompt
        } else {
            holder.leftChatView.visibility = View.GONE
            holder.rightChatView.visibility = View.VISIBLE
            holder.rightChatTextView.text = chat.prompt
        }
    }

    override fun getItemCount(): Int = chatList.size

    fun addChat(chat: Chat) {
        chatList.add(chat)
        notifyItemInserted(chatList.lastIndex)
    }
}