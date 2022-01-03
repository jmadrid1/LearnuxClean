package com.example.learnuxclean.features_videos.presentation
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.learnuxclean.databinding.ListRowVideoBinding
import com.example.learnuxclean.features_videos.domain.model.Video

class VideoAdapter : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    inner class VideoViewHolder(val binding: ListRowVideoBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Video>() {
        override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = ListRowVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding)
    }

    private var onItemClickListener : ((Video) -> Unit)? = null

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = differ.currentList[position]

        holder.binding.rowTextviewTitle.text = video.title
        holder.binding.rowTextviewDescription.text = video.description

        holder.binding.rowTextviewDuration.text = video.duration

        Glide.with(holder.binding.rowImageviewThumbnail.context)
            .load(video.thumbnail)
            .into(holder.binding.rowImageviewThumbnail)

        holder.binding.root.setOnClickListener {
            onItemClickListener?.let {
                it(video)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setOnItemClickListener(listener : (Video) -> Unit){
        onItemClickListener = listener
    }

}