package com.example.learnuxclean.features_videos.domain.use_cases

import com.google.android.youtube.player.YouTubePlayer
import javax.inject.Inject

class PauseVideo @Inject constructor(){

    operator fun invoke(mPlayer: YouTubePlayer) {
        mPlayer.pause()
    }

}