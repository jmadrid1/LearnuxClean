package com.example.learnuxclean.features_videos.domain.use_cases

import com.google.android.youtube.player.YouTubePlayer
import javax.inject.Inject

class PlayVideo @Inject constructor(){

    operator fun invoke(id: String, mPlayer: YouTubePlayer) {
        mPlayer!!.loadVideo(id)
        mPlayer.play()
    }

}