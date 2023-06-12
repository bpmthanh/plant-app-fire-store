package com.example.plantappfirestore.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.plantappfirestore.R


class PictureAdapter : RecyclerView.Adapter<PictureAdapter.ViewHolder>() {
    private lateinit var context: Context
    private var listPicture: ArrayList<String> = ArrayList()

    fun setData(listPicture: ArrayList<String>) {
        this.listPicture = listPicture
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_picture, parent, false)
        context = parent.context
        return ViewHolder(itemView)
    }

    override fun getItemCount() = listPicture.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPicture: ImageView = itemView.findViewById(R.id.ivPicture)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ivPicture.setImageURI(Uri.parse(listPicture[position]))
    }
}


