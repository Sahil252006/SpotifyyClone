package com.example.spotifyyclone.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.spotifyyclone.R
import com.example.spotifyyclone.models.SongModel

class SongAdapter(
    private val songList: List<SongModel>,
    private val onSongClick: (SongModel, Int) -> Unit
) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivSongImage: ImageView = itemView.findViewById(R.id.ivSongImage)
        val tvSongTitle: TextView = itemView.findViewById(R.id.tvSongTitle)
        val tvSongArtist: TextView = itemView.findViewById(R.id.tvSongArtist)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songList[position]

        holder.ivSongImage.setImageResource(song.imageResId)
        holder.tvSongTitle.text = song.title
        holder.tvSongArtist.text = song.artist

        holder.itemView.setOnClickListener {
            onSongClick(song, position)
        }
    }

    override fun getItemCount(): Int = songList.size
}