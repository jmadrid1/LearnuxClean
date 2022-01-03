package com.example.learnuxclean.features_videos.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.learnuxclean.features_videos.data.Result
import com.example.learnuxclean.features_videos.data.Status
import com.example.learnuxclean.features_videos.domain.model.Video
import com.example.learnuxclean.features_videos.domain.use_cases.GetVideos
import com.example.learnuxclean.features_videos.domain.use_cases.PauseVideo
import com.example.learnuxclean.features_videos.domain.use_cases.PlayVideo
import com.google.android.youtube.player.YouTubePlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val getVideos: GetVideos,
    private val playVideo: PlayVideo,
    private val pauseVideo: PauseVideo,
): ViewModel() {

    private val _videoList = MutableLiveData<Result<List<Video>>>()
    val videoList: LiveData<Result<List<Video>>> = _videoList

    suspend fun getVideos(){
        getVideos.invoke().collect {
            val videoList = mutableListOf<Video>()
            _videoList.postValue(Result.loading(null))
            when(it.status) {
                Status.SUCCESS -> {
                    it.data?.let { items ->
                        for(e in items){
                            videoList.add(e)
                        }
                        _videoList.postValue(Result.success(videoList))
                    }
                }
                Status.ERROR -> {  _videoList.postValue(Result.error("Failed to grab items from Firebase", emptyList())) }
            }
        }
    }

    fun pauseVideo(mPlayer: YouTubePlayer){
        pauseVideo.invoke(mPlayer)
    }

    fun playVideo(id: String, mPlayer: YouTubePlayer){
        playVideo.invoke(id, mPlayer)
    }

}