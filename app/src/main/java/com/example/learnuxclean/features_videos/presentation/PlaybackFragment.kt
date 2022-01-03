package com.example.learnuxclean.features_videos.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.learnuxclean.R
import com.example.learnuxclean.databinding.FragmentPlaybackBinding
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaybackFragment : Fragment(R.layout.fragment_playback), YouTubePlayer.OnInitializedListener {

    companion object {
        const val YOUTUBE_API = "AIzaSyBKbyxJZ_TYpR4P9YSp14C8bTCLOTOEwWk"
    }

    private var _binding: FragmentPlaybackBinding? = null
    private val binding get() = _binding!!

    private val args: PlaybackFragmentArgs by navArgs()
    private val viewModel : VideoViewModel by viewModels()

    private var mPlayer: YouTubePlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlaybackBinding.inflate(inflater, container, false)
        val view = binding.root

        val frag = YouTubePlayerSupportFragmentX()
        frag.initialize(YOUTUBE_API, this)

        val ft = requireFragmentManager().beginTransaction()
        ft.add(R.id.frag_container, frag).commit()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBackButtonPress()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onBackButtonPress(){
        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if (mPlayer != null) {
                    viewModel.pauseVideo(mPlayer!!)
                }
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        player: YouTubePlayer?,
        wasRestored: Boolean
    ) {
        mPlayer = player
        mPlayer!!.setFullscreen(false)
        mPlayer!!.setShowFullscreenButton(false)

        if(!wasRestored) {
            val id : String = args.videoID
            viewModel.playVideo(id, mPlayer!!)
        }
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        youTubeInitializationResult: YouTubeInitializationResult?
    ) {
        if (!youTubeInitializationResult!!.isUserRecoverableError) {
            youTubeInitializationResult.getErrorDialog(activity, 1).show()
        }
    }

}