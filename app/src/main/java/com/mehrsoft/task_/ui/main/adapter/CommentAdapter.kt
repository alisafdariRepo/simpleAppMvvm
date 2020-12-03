package com.mehrsoft.task_.ui.main.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mehrsoft.task_.R
import com.mehrsoft.task_.data.model.PostsItem
import com.mehrsoft.task_.data.model.comments.CommentItem
import com.mehrsoft.task_.ui.main.view.CommentActivity
import kotlinx.android.synthetic.main.comment_item.view.*
import kotlinx.android.synthetic.main.post_item.view.*
import kotlinx.android.synthetic.main.post_item.view.textView
import kotlinx.android.synthetic.main.post_item.view.textView2

class CommentAdapter (private val comment:ArrayList<CommentItem>):RecyclerView.Adapter<CommentAdapter.CommentHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
         return CommentHolder(LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent, false))
    }

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        holder.bind(comment = comment[position])


    }

    override fun getItemCount(): Int {
        return comment.size
    }

    fun addComment(comment: List<CommentItem>) {
        this.comment.apply {
            clear()
            addAll(comment)
        }

    }
    class CommentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bind(comment: CommentItem) {
            itemView.apply {
                c_name.text=comment.name
                c_email.text=comment.email
                c_body.text="Body: \t"+comment.body
            }
        }
    }

}

