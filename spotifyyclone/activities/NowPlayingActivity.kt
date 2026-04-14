package com.example.spotifyyclone.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.spotifyyclone.MusicManager
import com.example.spotifyyclone.R

class NowPlayingActivity : AppCompatActivity() {

    private lateinit var ivNowPlayingImage: ImageView
    private lateinit var tvNowPlayingTitle: TextView
    private lateinit var tvNowPlayingArtist: TextView
    private lateinit var btnPrevious: ImageButton
    private lateinit var btnPlayPause: ImageButton
    private lateinit var btnNext: ImageButton
    private lateinit var seekBar: SeekBar

    private val handler = Handler(Looper.getMainLooper())

    private val updateSeekBar = object : Runnable {
        override fun run() {
            val mediaPlayer = MusicManager.getMediaPlayer()
            if (mediaPlayer != null) {
                seekBar.progress = mediaPlayer.currentPosition
                handler.postDelayed(this, 1000)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_now_playing)

        ivNowPlayingImage = findViewById(R.id.ivNowPlayingImage)
        tvNowPlayingTitle = findViewById(R.id.tvNowPlayingTitle)
        tvNowPlayingArtist = findViewById(R.id.tvNowPlayingArtist)
        btnPrevious = findViewById(R.id.btnPrevious)
        btnPlayPause = findViewById(R.id.btnPlayPause)
        btnNext = findViewById(R.id.btnNext)
        seekBar = findViewById(R.id.seekBar)

        updateUI()

        btnPlayPause.setOnClickListener {
            MusicManager.togglePlayPause(this)
            updatePlayPauseButton()
        }

        btnNext.setOnClickListener {
            MusicManager.nextSong(this)
            updateUI()
        }

        btnPrevious.setOnClickListener {
            MusicManager.previousSong(this)
            updateUI()
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    MusicManager.getMediaPlayer()?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun updateUI() {
        val currentSong = MusicManager.getCurrentSong()
        val mediaPlayer = MusicManager.getMediaPlayer()

        if (currentSong != null && mediaPlayer != null) {
            ivNowPlayingImage.setImageResource(currentSong.imageResId)
            tvNowPlayingTitle.text = currentSong.title
            tvNowPlayingArtist.text = currentSong.artist
            seekBar.max = mediaPlayer.duration
            updatePlayPauseButton()

            handler.removeCallbacks(updateSeekBar)
            handler.post(updateSeekBar)
        }
    }

    private fun updatePlayPauseButton() {
        if (MusicManager.isPlaying) {
            btnPlayPause.setImageResource(android.R.drawable.ic_media_pause)
        } else {
            btnPlayPause.setImageResource(android.R.drawable.ic_media_play)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(updateSeekBar)
    }
}