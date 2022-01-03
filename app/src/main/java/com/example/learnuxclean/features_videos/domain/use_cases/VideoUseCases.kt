package com.example.learnuxclean.features_videos.domain.use_cases

data class VideoUseCases(
    val getVideos: GetVideos,
    val playVideo: PlayVideo,
    val pauseVideo: PauseVideo
)