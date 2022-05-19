package com.tesoem.movieapp

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tesoem.movieapp.databinding.ItemMovieBinding

class MovieViewHolder(view:View):RecyclerView.ViewHolder(view) {
    private  val binding = ItemMovieBinding.bind(view)

    fun bind(image:String,txtName:String){
        Picasso.get().load(image).into(binding.ivPoster)
        binding.tvNombre.text = txtName

        itemView.setOnClickListener {
            Toast.makeText(binding.ivPoster.context, txtName, Toast.LENGTH_SHORT).show()
        }

    }

}