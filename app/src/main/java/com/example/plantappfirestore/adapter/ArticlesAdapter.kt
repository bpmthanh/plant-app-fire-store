package com.example.plantappfirestore.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.plantappfirestore.R
import com.example.plantappfirestore.callback.CallBack
import com.example.plantappfirestore.model.Articles


class ArticlesAdapter : RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {
    private lateinit var context: Context
    private var listArticles: ArrayList<Articles> = ArrayList()
    private var callBack: CallBack.ArticlesDetail? = null

    fun setData(listArticles: ArrayList<Articles>) {
        this.listArticles = listArticles
    }

    fun articlesDetail(callBack: CallBack.ArticlesDetail) {
        this.callBack = callBack
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_articles, parent, false)
        context = parent.context
        return ViewHolder(itemView)
    }

    override fun getItemCount() = listArticles.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivArticles: ImageView = itemView.findViewById(R.id.ivArticles)
        val ivHeart: ImageView = itemView.findViewById(R.id.ivHeart)
        val ivAvatar: ImageView = itemView.findViewById(R.id.ivAvatar)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val root: ConstraintLayout = itemView.findViewById(R.id.root)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(listArticles[position].image).into(holder.ivArticles)
        Glide.with(context).load(listArticles[position].avatar).into(holder.ivAvatar)
        holder.tvTitle.text = listArticles[position].title
        holder.tvName.text = listArticles[position].name
        holder.tvDate.text = listArticles[position].date.replace("00000", " . ")
        if (listArticles[position].heart == "true") {
            holder.ivHeart.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_heart))
            holder.ivHeart.setColorFilter(ContextCompat.getColor(context, R.color.red), android.graphics.PorterDuff.Mode.MULTIPLY);
        } else {
            holder.ivHeart.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_heart_articles))
        }
        holder.root.setOnClickListener {
            callBack?.articlesDetail(
                listArticles[position].title,
                listArticles[position].description,
                listArticles[position].name,
                listArticles[position].avatar,
                listArticles[position].date,
                listArticles[position].image,
                listArticles[position].heart
            )
        }
    }
}


