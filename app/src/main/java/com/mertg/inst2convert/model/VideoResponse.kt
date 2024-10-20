package com.mertg.inst2convert.model

data class VideoResponse(
    val status: String,
    val data: VideoData?
)

data class VideoData(
    val filename: String,
    val width: String,
    val height: String,
    val videoUrl: String
)
