package com.mertg.inst2convert.service


import com.mertg.inst2convert.model.VideoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface InstApiService {
    @GET("/api/video")
    fun getVideoUrl(@Query("postUrl") url: String): Call<VideoResponse>
}
