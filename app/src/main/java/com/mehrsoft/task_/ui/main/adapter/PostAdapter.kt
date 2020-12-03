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
import com.mehrsoft.task_.ui.main.view.CommentActivity
import kotlinx.android.synthetic.main.post_item.view.*

class PostAdapter(private val posts: ArrayList<PostsItem>, private val context: Context) :
    RecyclerView.Adapter<PostAdapter.DataViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(post = posts[position])
        val postItem = posts[position]

        holder.itemView.setOnClickListener {


            val intent = Intent(context, CommentActivity::class.java)
            intent.putExtra("id", postItem.userId)
            context.startActivity(intent)


            // Toast.makeText(context, postItem.id.toString(), Toast.LENGTH_SHORT).show()
        }


    }

    override fun getItemCount(): Int {
        return posts.size
    }

    fun addUsers(posts: List<PostsItem>) {
        this.posts.apply {
            clear()
            addAll(posts)
        }

    }

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bind(post: PostsItem) {
            itemView.apply {
                textView3.text = "uId: \t" + post.userId.toString()
                textView2.text = "Title: \t" + post.title
                textView.text = "Body: \t" + post.body
            }
        }
    }

}

