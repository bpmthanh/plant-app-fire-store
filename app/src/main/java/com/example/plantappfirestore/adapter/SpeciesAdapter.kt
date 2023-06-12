package com.example.plantappfirestore.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.plantappfirestore.R
import com.example.plantappfirestore.callback.CallBack

class SpeciesAdapter(private var listAlphabet: ArrayList<String>) : RecyclerView.Adapter<SpeciesAdapter.ViewHolder>() {
    private lateinit var context: Context

    private var callBack: CallBack.Alphabet? = null

    fun alphabet(callBack: CallBack.Alphabet) {
        this.callBack = callBack
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_alphabet, parent, false)
        context = parent.context
        return ViewHolder(itemView)
    }

    override fun getItemCount() = listAlphabet.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvAlphabet: TextView = itemView.findViewById(R.id.tvAlphabet)
        val root: ConstraintLayout = itemView.findViewById(R.id.root)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvAlphabet.text = listAlphabet[position]
        holder.root.setOnClickListener {
            callBack?.alphabet(listAlphabet[position])
        }
    }
}


