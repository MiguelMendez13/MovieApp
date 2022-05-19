package com.tesoem.movieapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ViewAdapter(private val images:List<String>,private val nombres:List<String>):RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            return MovieViewHolder(layoutInflater.inflate(R.layout.item_movie,parent,false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item:String = images[position]
        val nombres:String = nombres[position]

        holder.bind(item,nombres)
    }

    override fun getItemCount(): Int = images.size

}