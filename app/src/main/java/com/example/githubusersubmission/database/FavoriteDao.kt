package com.example.githubusersubmission.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteDao {//untuk handle query
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favuser: FavoriteUser)
    @Update
    fun update(favuser: FavoriteUser)
    @Delete
    fun delete(favuser: FavoriteUser)

    @Query("SELECT * FROM FavoriteUser WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser>

    @Query("SELECT * FROM FavoriteUser")
    fun getFavoriteUser() : LiveData<List<FavoriteUser>>
}