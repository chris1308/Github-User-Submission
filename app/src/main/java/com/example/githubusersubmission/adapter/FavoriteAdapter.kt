package com.example.githubusersubmission.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubusersubmission.database.FavoriteUser
import com.example.githubusersubmission.databinding.ItemUserBinding
import com.example.githubusersubmission.ui.DetailActivity

class FavoriteAdapter : ListAdapter<FavoriteUser, FavoriteAdapter.MyViewHolder>(DIFF_CALLBACK) {
    lateinit var myContext: Context

    fun setContext(context : Context){
        myContext = context
    }
    class MyViewHolder (val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user: FavoriteUser){
            binding.tvUsername.text = "${user.username}"
            Glide.with(binding.ivUser.context)
                .load(user.avatarUrl)
                .into(binding.ivUser)

        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteAdapter.MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteAdapter.MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.MyViewHolder, position: Int) {
        val user =  getItem(position)
        holder.bind(user)
        //redirect to Detailactivity saat salah satu user di klik
        holder.itemView.setOnClickListener {
            val intent = Intent(myContext, DetailActivity::class.java)
            intent.putExtra("username",user.username)
            myContext.startActivity(intent)
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteUser>() {
            override fun areItemsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
                return oldItem == newItem
            }
        }
    }
}