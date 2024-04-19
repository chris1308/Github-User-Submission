package com.example.githubusersubmission.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubusersubmission.database.FavoriteDao
import com.example.githubusersubmission.database.FavoriteRoomDatabase
import com.example.githubusersubmission.database.FavoriteUser
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    //penghubung viewmodel dengan resource/database
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favUser()
    }
    fun insert(note: FavoriteUser) {
        executorService.execute { mFavoriteDao.insert(note) }
    }
    fun delete(note: FavoriteUser) {
        executorService.execute { mFavoriteDao.delete(note) }
    }
    fun update(note: FavoriteUser) {
        executorService.execute { mFavoriteDao.update(note) }
    }
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> = mFavoriteDao.getFavoriteUserByUsername(username)

    fun getFavoriteUser() : LiveData<List<FavoriteUser>> = mFavoriteDao.getFavoriteUser()
}