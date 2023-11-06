package com.example.jobhunt.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jobhunt.R
import com.example.jobhunt.DataModel.BoardData

class AddBoardAdapter : RecyclerView.Adapter<AddBoardAdapter.ViewHolder>() {
    private val boardList: MutableList<BoardData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_board, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val board = boardList[position]
        holder.bind(board)
    }

    override fun getItemCount(): Int {
        return boardList.size
    }

    fun setBoardList(list: List<BoardData>) {
        boardList.clear()
        boardList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtWriter: TextView = itemView.findViewById(R.id.board_writer)
        private val txtSubject: TextView = itemView.findViewById(R.id.board_subject)
        private val txtTitle: TextView = itemView.findViewById(R.id.board_title)
        private val txtContent: TextView = itemView.findViewById(R.id.board_content)

        fun bind(board: BoardData) {
            txtWriter.text = board.writer
            txtSubject.text = board.subject
            txtTitle.text = board.title
            txtContent.text = board.content
        }
    }
}