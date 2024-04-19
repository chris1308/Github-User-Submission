package com.example.githubusersubmission.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusersubmission.adapter.FavoriteAdapter
import com.example.githubusersubmission.database.FavoriteUser
import com.example.githubusersubmission.databinding.ActivityFavoriteBinding
import com.example.githubusersubmission.helper.ViewModelFactory
import com.example.githubusersubmission.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {
    lateinit var binding : ActivityFavoriteBinding
    //jangan lupa daftarkan dulu viewmodelnya di viewmodelfactory
    private val favoriteViewModel by viewModels<FavoriteViewModel>(){
        ViewModelFactory.getInstance(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set action bar back button and title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Favorite User"

        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager
        favoriteViewModel.getFavoriteUser().observe(this){
            setUserData(it)
        }

        favoriteViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            //go back
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun showLoading(isLoading: Boolean)  {
        if (isLoading){
            binding.progressBar5.visibility = View.VISIBLE
        }else binding.progressBar5.visibility = View.GONE
    }
    private fun setUserData(items : List<FavoriteUser>){
        val adapter = FavoriteAdapter()
        adapter.submitList(items)
        binding.rvFavorite.adapter = adapter
        adapter.setContext(this)
    }
}