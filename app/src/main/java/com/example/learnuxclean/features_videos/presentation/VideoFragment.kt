package com.example.learnuxclean.features_videos.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnuxclean.R
import com.example.learnuxclean.core.utils.ConnectionLiveData
import com.example.learnuxclean.databinding.FragmentVideosBinding
import com.example.learnuxclean.features_videos.data.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VideosFragment : Fragment(R.layout.fragment_videos) {

    private var _binding: FragmentVideosBinding? = null
    private val binding get() = _binding!!

    private val viewModel : VideoViewModel by viewModels()
    private lateinit var videoAdapter: VideoAdapter

    lateinit var connectionLiveData : ConnectionLiveData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVideosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBackButtonPress()
        setupRecyclerView()
        setupObservers()

        videoAdapter.setOnItemClickListener {
            val id : String = it.id
            val action = VideosFragmentDirections.actionVideosFragmentToPlayBackFragment(id)
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView(){
        videoAdapter = VideoAdapter()
        binding.fragmentVideosRecyclerview.apply{
            adapter = videoAdapter
            layoutManager = LinearLayoutManager(context)
        }
        videoAdapter.notifyDataSetChanged()
    }

    private fun setupObservers(){
        connectionLiveData = ConnectionLiveData(requireContext())
        connectionLiveData.observe(viewLifecycleOwner, {
            if (it){
                hideProgressBar()
                showList()
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.getVideos()
                }
            }else{
                hideList()
                hideProgressBar()
            }
        })

        viewModel.videoList.observe(viewLifecycleOwner) { result ->
            when(result.status) {
                Status.SUCCESS -> {
                    hideProgressBar()
                    result.data?.let { videos ->
                        videoAdapter.differ.submitList(videos)
                        showList()
                    }
                }
                Status.ERROR -> {
                    hideList()
                }
                Status.LOADING -> {
                    showProgressBar()
                }
            }
        }

    }

    private fun showList(){
        binding.fragmentVideosRecyclerview.visibility = View.VISIBLE
        binding.fragmentVideosImageviewEmptyList.visibility = View.GONE
        binding.fragmentVideosTextviewEmptyList.visibility = View.GONE
    }

    private fun hideList(){
        binding.fragmentVideosRecyclerview.visibility = View.GONE
        binding.fragmentVideosImageviewEmptyList.visibility = View.VISIBLE
        binding.fragmentVideosTextviewEmptyList.visibility = View.VISIBLE
    }

    private fun showProgressBar(){
        binding.fragmentVideosProgressbar.visibility = View.VISIBLE
        showList()
    }

    private fun hideProgressBar(){
        binding.fragmentVideosProgressbar.visibility = View.GONE
    }

    private fun onBackButtonPress(){
        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                activity!!.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

}