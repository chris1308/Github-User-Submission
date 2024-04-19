package com.example.githubusersubmission.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubusersubmission.data.response.DetailUserResponse
import com.example.githubusersubmission.data.retrofit.ApiConfig
import com.example.githubusersubmission.database.FavoriteUser
import com.example.githubusersubmission.repository.FavoriteRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel (application: Application): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _user = MutableLiveData<DetailUserResponse>()
    val user : LiveData<DetailUserResponse> = _user
    private val _name = MutableLiveData<String>()
    val name :LiveData<String> = _name
    private val _following  =MutableLiveData<Int>()
    val following = _following

    private val mFavRepository: FavoriteRepository = FavoriteRepository(application)
    fun insert(favuser: FavoriteUser) {
        mFavRepository.insert(favuser)
    }
    fun update(favuser: FavoriteUser) {
        mFavRepository.update(favuser)
    }
    fun delete(favuser: FavoriteUser) {
        mFavRepository.delete(favuser)
    }
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser>{
        return mFavRepository.getFavoriteUserByUsername(username)
    }
    fun detailUser(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()
                    _name.value = response.body()?.name
                    _following.value = response.body()?.following
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")            }


        })
    }

    companion object{
        private const val TAG = "DetailViewModel"
    }
}