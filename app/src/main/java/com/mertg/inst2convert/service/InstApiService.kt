package com.mertg.inst2convert.service


import com.mertg.inst2convert.model.VideoResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface InstApiService {
    @GET("/api/video")
    fun getVideoUrl(@Query("postUrl") url: String): Call<VideoResponse>

    // YouTube için video URL'si çekme
    @GET("/api/youtube/video")
    fun getYoutubeVideoUrl(@Query("postUrl") url: String): Call<VideoResponse>

    // Genel video indirme (Instagram/YouTube)
    @POST("/download/{resolution}")
    fun downloadVideo(@Path("resolution") resolution: String, @Body request: VideoDownloadRequest): Call<DownloadResponse>

}

// Instagram ve YouTube için kullanılan video talebi sınıfı
data class VideoDownloadRequest(
    val url: String
)

// Genel indirme yanıtı sınıfı
data class DownloadResponse(
    val message: String,
    val error: String?
)