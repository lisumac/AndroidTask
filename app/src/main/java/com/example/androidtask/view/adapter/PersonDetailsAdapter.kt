package com.example.androidtask.view.adapter

import android.annotation.SuppressLint
import android.app.Person
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidtask.databinding.PersonDetailsBinding
import com.example.androidtask.model.Data


class PersonDetailsAdapter(var mcontext: Context,var data: List<Data>): RecyclerView.Adapter<PersonDetailsAdapter.PersonDetailsViewHolder>() {

    class PersonDetailsViewHolder(private val binding: PersonDetailsBinding):RecyclerView.ViewHolder(binding.root){

        @SuppressLint("SetTextI18n")
        fun bind(data: Data) {
           binding.tvPersonName.text = data.first_name +" "+data.last_name
            binding.tvPersonEmail.text ="Email - " + data.email
            Glide.with(binding.ivPersonImage.context).load(data.avatar).into(binding.ivPersonImage)




        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonDetailsViewHolder {

        val binding = PersonDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonDetailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonDetailsViewHolder, position: Int) {
        val data = data[position]
        holder.bind(data)

    }

    override fun getItemCount(): Int {
       return  data.size
    }
}