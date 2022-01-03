package com.example.learnuxclean.features_videos.domain.use_cases

import com.example.learnuxclean.features_videos.data.repository.FirebaseVideoRepo

import javax.inject.Inject

class GetVideos @Inject constructor(
    private val _firebase: FirebaseVideoRepo
) {

    suspend operator fun invoke() = _firebase.getVideos()

}