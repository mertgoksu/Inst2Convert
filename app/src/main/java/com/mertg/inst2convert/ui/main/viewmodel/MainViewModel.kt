package com.mertg.inst2convert.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mertg.inst2convert.base.BaseViewModel
import com.mertg.inst2convert.service.InstApiService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.awaitResponse

class MainViewModel : BaseViewModel() {

    private val _videoUrl = MutableLiveData<String?>()
    val videoUrl: LiveData<String?> get() = _videoUrl

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://charm-chemical-banjo.glitch.me")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(InstApiService::class.java)

    fun clearVideoUrl() {
        _videoUrl.value = null // Video URL'yi sıfırla
    }

    fun convertUrl(url: String) {
        setLoading(true)
        viewModelScope.launch {
            try {
                val response = api.getVideoUrl(url).awaitResponse()
                if (response.isSuccessful && response.body()?.status == "success") {
                    _videoUrl.value = response.body()?.data?.videoUrl
                } else {
                    setError("Hata: URL bulunamadı")
                }
            } catch (e: Exception) {
                setError("Bir hata oluştu: ${e.message}")
            } finally {
                setLoading(false)
            }
        }
    }
}
