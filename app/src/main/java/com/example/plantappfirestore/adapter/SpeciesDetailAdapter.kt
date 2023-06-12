package com.example.plantappfirestore.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.plantappfirestore.R
import com.example.plantappfirestore.callback.CallBack
import com.example.plantappfirestore.model.Species
import java.net.URL


class SpeciesDetailAdapter : RecyclerView.Adapter<SpeciesDetailAdapter.ViewHolder>() {
    private lateinit var context: Context
    private var listSpecies: ArrayList<Species> = ArrayList()
    private var callBack: CallBack.SpeciesDetail? = null

    fun setData(listSpecies: ArrayList<Species>) {
        this.listSpecies = listSpecies
    }

    fun speciesDetail(callBack: CallBack.SpeciesDetail) {
        this.callBack = callBack
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_species, parent, false)
        context = parent.context
        return ViewHolder(itemView)
    }

    override fun getItemCount() = listSpecies.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivSpecies: ImageView = itemView.findViewById(R.id.ivSpecies)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvKingDom: TextView = itemView.findViewById(R.id.tvKingDom)
        val tvFamily: TextView = itemView.findViewById(R.id.tvFamily)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val root: ConstraintLayout = itemView.findViewById(R.id.root)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(listSpecies[position].image).into(holder.ivSpecies)
        holder.tvTitle.text = listSpecies[position].title
        holder.tvKingDom.text = listSpecies[position].kingdom
        holder.tvFamily.text = listSpecies[position].family
        holder.tvDescription.text = listSpecies[position].description
        holder.root.setOnClickListener {
            callBack?.speciesDetail(
                listSpecies[position].category,
                listSpecies[position].title,
                listSpecies[position].description,
                listSpecies[position].kingdom,
                listSpecies[position].family,
                listSpecies[position].star,
                listSpecies[position].image,
                listSpecies[position].heart
            )
        }
    }
}


