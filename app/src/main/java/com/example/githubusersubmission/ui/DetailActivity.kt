package com.example.githubusersubmission.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubusersubmission.R
import com.example.githubusersubmission.adapter.SectionsPagerAdapter
import com.example.githubusersubmission.database.FavoriteUser
import com.example.githubusersubmission.databinding.ActivityDetailBinding
import com.example.githubusersubmission.helper.ViewModelFactory
import com.example.githubusersubmission.viewmodel.DetailViewModel
import com.example.githubusersubmission.viewmodel.MainViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>(){
        ViewModelFactory.getInstance(application)
    }
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding .inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail User"
        val username = intent.getStringExtra("username").toString()
        detailViewModel.detailUser(username)
        detailViewModel.user.observe(this){
            //it berisi object detailuserresponse
            Glide.with(this).load(it.avatarUrl).into(binding.ivDetailuser)
            binding.tvFollowers.text = "Followers : ${it.followers}"
            binding.tvUsernamedetail.text = username
            binding.tvNamedetail.text = it.name
            binding.tvFollowing.text = "Following : ${it.following}"
            binding.tvHiddenUrl.text = it.avatarUrl
        }
        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        //cek apa user tsb sudah ada dalam database
        detailViewModel.getFavoriteUserByUsername(username).observe(this){
            if (it == null){
                binding.fabFavorite.tag = "belum"
                binding.fabFavorite.setImageResource(R.drawable.ic_favorite)
            }else{
                //user sudah masuk favorite
                binding.fabFavorite.tag = "sudah"
                binding.fabFavorite.setImageResource(R.drawable.ic_favorite_full)
            }
        }
        //atur param dengan nama user yang lagi dilihat detailnya
        MainViewModel.USER_PARAM = username

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabLayout)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        binding.fabFavorite.setOnClickListener {
            //cek apa mode insert atau delete dari icon
            val username = binding.tvUsernamedetail.text.toString()
            val avatarUrl = binding.tvHiddenUrl.text.toString()

            if (it.tag == "belum"){
                //user masih belum masuk favorite
                //INSERT
                detailViewModel.insert(FavoriteUser(username,avatarUrl))
                Toast.makeText(this, "Berhasil tambah user ke favorite", Toast.LENGTH_SHORT).show()
                it.tag = "sudah"
            }else {
                //DELETE
                detailViewModel.delete(FavoriteUser(username,avatarUrl))
            }
        }
    }

    private fun showLoading(isLoading: Boolean)  {
        if (isLoading){
            binding.progressBar2.visibility = View.VISIBLE
        }else binding.progressBar2.visibility = View.GONE
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}