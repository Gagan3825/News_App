package com.gagandeep.news_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewListAdapter (private val listener:newItemClicked): RecyclerView.Adapter<NewsViewholder>() {
    private  val items:ArrayList<News> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        val viewholdr = NewsViewholder(view)
        view.setOnClickListener{
      listener.onItemclicked(items[viewholdr.adapterPosition])
        }
        return viewholdr
    }

    override fun onBindViewHolder(holder: NewsViewholder, position: Int) {
        val currentItem = items[position]
        holder.titleView.text = currentItem.title
        holder.author.text = currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.image)
    }

    override fun getItemCount(): Int {
        return items.size
    }
    fun updatedNews(updatedNews:ArrayList<News>){
        items.clear()
        items.addAll(updatedNews)
        notifyDataSetChanged()
    }
}
class NewsViewholder(itemView: View): RecyclerView.ViewHolder(itemView){
    val titleView : TextView = itemView.findViewById(R.id.titleView)
    val image:ImageView = itemView.findViewById(R.id.image)
    val author :TextView = itemView.findViewById(R.id.author)
}
interface newItemClicked{
    fun onItemclicked(item:News)
}