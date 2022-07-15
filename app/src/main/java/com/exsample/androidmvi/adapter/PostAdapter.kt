package com.exsample.androidmvi.adapter

import com.exsample.androidmvi.R
import com.exsample.androidmvi.model.Post
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PostAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var longClick: ((Post) -> Unit)? = null
    var click: ((Post) -> Unit)? = null

    private var list = ArrayList<Post>()


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_post_list, parent, false)
        return PosterViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post: Post = list[position]
        if (holder is PosterViewHolder) {
            val ll_poster = holder.ll_poster
            val tv_title = holder.tv_title
            val tv_body = holder.tv_body

            tv_title.setText(post.title?.toUpperCase())
            tv_body.setText(post.body)

            ll_poster.setOnLongClickListener {
                longClick?.invoke(post)
                true
            }

            ll_poster.setOnClickListener {
                click?.invoke(post)
            }
        }
    }

    inner class PosterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tv_title: TextView
        var tv_body: TextView
        var ll_poster: LinearLayout

        init {
            ll_poster = view.findViewById(R.id.ll_poster)
            tv_title = view.findViewById(R.id.tv_title)
            tv_body = view.findViewById(R.id.tv_body)
        }
    }



    fun submitData(list: List<Post>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}