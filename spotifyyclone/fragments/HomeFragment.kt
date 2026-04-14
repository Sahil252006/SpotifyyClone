package com.example.spotifyyclone.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spotifyyclone.MusicManager
import com.example.spotifyyclone.R
import com.example.spotifyyclone.activities.NowPlayingActivity
import com.example.spotifyyclone.adapters.SongAdapter
import com.example.spotifyyclone.models.SongModel

class HomeFragment : Fragment() {

    private lateinit var recyclerSongs: RecyclerView
    private lateinit var songAdapter: SongAdapter
    private lateinit var songList: ArrayList<SongModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerSongs = view.findViewById(R.id.recyclerSongs)

        songList = arrayListOf(
            SongModel("Romantic Vibes", "Anuv jain", R.drawable.album1, R.raw.song3),
            SongModel("Silent Moments", "Arijit singh", R.drawable.album2, R.raw.song1),
            SongModel("Hollywood Nights", "Arijit singh", R.drawable.album3, R.raw.song2)
        )

        MusicManager.setSongList(songList)

        songAdapter = SongAdapter(songList) { _, position ->
            MusicManager.playSong(requireContext(), position)

            val intent = Intent(requireContext(), NowPlayingActivity::class.java)
            startActivity(intent)
        }

        recyclerSongs.layoutManager = LinearLayoutManager(requireContext())
        recyclerSongs.adapter = songAdapter

        return view
    }
}