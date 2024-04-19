package com.example.githubusersubmission.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubusersubmission.data.response.ItemsItem
import com.example.githubusersubmission.databinding.ItemUserBinding
import com.example.githubusersubmission.ui.DetailActivity

class UserAdapter : ListAdapter<ItemsItem, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {
    lateinit var myContext: Context

    fun setContext(context : Context){
        myContext = context
    }

    class MyViewHolder (val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user: ItemsItem){
            binding.tvUsername.text = "${user.login}"
            Glide.with(binding.ivUser.context)
                .load(user.avatarUrl)
                .into(binding.ivUser)

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user =  getItem(position)
        holder.bind(user)
        //redirect to Detailactivity
        holder.itemView.setOnClickListener {
            val intent = Intent(myContext, DetailActivity::class.java)
            intent.putExtra("username",user.login)
            myContext.startActivity(intent)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}