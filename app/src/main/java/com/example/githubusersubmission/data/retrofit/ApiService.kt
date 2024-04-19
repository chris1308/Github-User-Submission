package com.example.githubusersubmission.data.retrofit

import com.example.githubusersubmission.data.response.DetailUserResponse
import com.example.githubusersubmission.data.response.GithubResponse
import com.example.githubusersubmission.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    //search user
    @GET("search/users")
    fun getUsers(@Query("q") q: String): Call<GithubResponse>

    //detail user
    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}