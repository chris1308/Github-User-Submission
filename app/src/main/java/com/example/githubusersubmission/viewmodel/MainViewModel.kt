package com.example.githubusersubmission.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubusersubmission.data.response.GithubResponse
import com.example.githubusersubmission.data.response.ItemsItem
import com.example.githubusersubmission.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _listItems = MutableLiveData<List<ItemsItem>>()
    val listItems = _listItems
    private val _listFollowers = MutableLiveData<List<ItemsItem>>()
    val listFollowers = _listFollowers
    private val _listFollowing = MutableLiveData<List<ItemsItem>>()
    val listFollowing = _listFollowing
    init {
        findUsers()
    }

    companion object{
        private const val TAG = "MainViewModel"
        //isi params awal string dengan nilai sembarang agar halaman home tidak kosong
        var USER_PARAM = "ch"
    }
    fun setParams(param : String){//dipanggil setiap masukkan username yang dicari
        USER_PARAM = param
        findUsers()
    }
    fun setFollowers(){//dipanggil tiap fragment followwer ditampilkan
        getFollowers()
    }
    fun setFollowing(){//dipanggil tiap fragment following ditampilkan
        getFollowing()
    }
    private fun findUsers() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers(USER_PARAM)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listItems.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")            }


        })
    }

    private fun getFollowers(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(USER_PARAM)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollowers.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")            }

        })
    }

    private fun getFollowing(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(USER_PARAM)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollowing.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")            }

        })
    }
}