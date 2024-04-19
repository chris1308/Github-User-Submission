package com.example.githubusersubmission.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubusersubmission.database.FavoriteUser
import com.example.githubusersubmission.repository.FavoriteRepository
import android.os.Handler

class FavoriteViewModel (application: Application) : ViewModel()  {//penghubung activity ke repository
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val mFavRepository: FavoriteRepository = FavoriteRepository(application)

    fun getFavoriteUser() : LiveData<List<FavoriteUser>> {
        _isLoading.value = true
        //delay untuk loading indicator
        val handler = Handler()
        handler.postDelayed({
            _isLoading.value = false
        },400)
        return mFavRepository.getFavoriteUser()
    }
}