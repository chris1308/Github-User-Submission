package com.example.githubusersubmission.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubusersubmission.helper.SettingPreferences
import kotlinx.coroutines.launch

class SettingViewModel (private val pref: SettingPreferences?) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> {
        //harus diconvert ke livedata karena output raw berupa Flow dari settingpreference
        return pref!!.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref!!.saveThemeSetting(isDarkModeActive)
        }
    }
}