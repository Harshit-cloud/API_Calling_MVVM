package com.sample.androidtask.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sample.androidtask.databinding.VideoListItemBinding
import com.sample.androidtask.models.Data


class UsersPagerAdapter: PagingDataAdapter<Data, UsersPagerAdapter.VideosViewHolder>(
    VideoComparator
) {

    override fun onBindViewHolder(holder: VideosViewHolder, position: Int) {
        val data = getItem(position)!!
        holder.view.model=data

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideosViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = VideoListItemBinding.inflate(inflater, parent, false)
        return VideosViewHolder(binding)
    }

   inner class VideosViewHolder(val view: VideoListItemBinding): RecyclerView.ViewHolder(view.root)

    object VideoComparator: DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }
}