package com.mertg.inst2convert.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    protected fun setLoading(loading: Boolean) {
        isLoading.value = loading
    }

    fun setError(errorMessage: String) {
        error.value = errorMessage
    }

    override fun onCleared() {
        super.onCleared()
        // Ekstra temizleme işlemleri gerekiyorsa burada tanımlayabilirsiniz
    }
}
