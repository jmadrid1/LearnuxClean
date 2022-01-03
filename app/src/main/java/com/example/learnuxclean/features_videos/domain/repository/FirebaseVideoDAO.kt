package com.example.learnuxclean.features_videos.domain.repository

import com.example.learnuxclean.features_videos.data.Result
import com.example.learnuxclean.features_videos.domain.model.Video
import kotlinx.coroutines.flow.Flow

interface FirebaseVideoDAO {

    fun getVideos(): Flow<Result<List<Video>>>

}