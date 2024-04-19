package com.example.githubusersubmission.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusersubmission.R
import com.example.githubusersubmission.adapter.UserAdapter
import com.example.githubusersubmission.data.response.ItemsItem
import com.example.githubusersubmission.databinding.ActivityMainBinding
import com.example.githubusersubmission.helper.SettingPreferences
import com.example.githubusersubmission.helper.ViewModelFactory
import com.example.githubusersubmission.helper.dataStore
import com.example.githubusersubmission.viewmodel.MainViewModel
import com.example.githubusersubmission.viewmodel.SettingViewModel

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    var darkMode : Boolean = false
    //atur warna menuitem sesuai keadaan theme
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val settingIcon = menu!!.findItem(R.id.mi_settings)
        val favoriteIcon = menu!!.findItem(R.id.mi_favorite)
        if (darkMode){
            settingIcon.setIcon(R.drawable.ic_setting_white)
            favoriteIcon.setIcon(R.drawable.ic_favorite_white)
        }else{
            settingIcon.setIcon(R.drawable.ic_settings)
            favoriteIcon.setIcon(R.drawable.ic_favorite_full)
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pref = SettingPreferences.getInstance(application.dataStore)

        val settingViewModel = ViewModelProvider(this, ViewModelFactory(application, pref)).get(
            SettingViewModel::class.java
        )

        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                darkMode = true
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                darkMode = false
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        supportActionBar?.title = "Github User's Search"
        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        //hubungkan mainactivity dengan mainviewmodel
        mainViewModel.listItems.observe(this){items->
            setUserData(items)
        }
        //untuk atur loading progress bar
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }


        //hubungkan searchbar dan searchview
        with(binding){
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { v, actionId, event ->
                searchView.hide()
                //modify user search params setiap di enter pencariannya
                mainViewModel.setParams(searchView.text.toString())
                false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_fav,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.mi_favorite->{
                //redirect ke activity favorite
                startActivity(Intent(this,FavoriteActivity::class.java))
            }
            R.id.mi_settings->{
                startActivity(Intent(this,SettingActivity::class.java))
            }

        }
        return super.onOptionsItemSelected(item)
    }
    private fun showLoading(isLoading: Boolean)  {
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else binding.progressBar.visibility = View.GONE
    }

    private fun setUserData(items : List<ItemsItem>){
        val adapter = UserAdapter()
        adapter.submitList(items)
        binding.rvUser.adapter = adapter
        adapter.setContext(this)
    }


}