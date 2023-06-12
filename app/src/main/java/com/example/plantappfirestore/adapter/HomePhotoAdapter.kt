package com.example.plantappfirestore.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.plantappfirestore.R


class HomePhotoAdapter : RecyclerView.Adapter<HomePhotoAdapter.ViewHolder>() {
    private lateinit var context: Context
    private var listPicture: MutableList<String> = ArrayList()

    fun setData(listPicture: MutableList<String>) {
        this.listPicture = listPicture
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_home_photo, parent, false)
        context = parent.context
        return ViewHolder(itemView)
    }

    override fun getItemCount() = listPicture.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPicture: ImageView = itemView.findViewById(R.id.ivPicture)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(listPicture[position]).into(holder.ivPicture)
    }
}


