package com.example.spotifyyclone

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import com.example.spotifyyclone.models.SongModel

object MusicManager {

    private var mediaPlayer: MediaPlayer? = null
    private var songList: List<SongModel> = emptyList()

    var currentPosition: Int = 0
    var isPlaying: Boolean = false

    fun setSongList(list: List<SongModel>) {
        songList = list
    }

    fun getSongList(): List<SongModel> = songList

    fun getCurrentSong(): SongModel? {
        return if (songList.isNotEmpty() && currentPosition in songList.indices) {
            songList[currentPosition]
        } else {
            null
        }
    }

    fun playSong(context: Context, position: Int) {
        if (songList.isEmpty()) return

        currentPosition = position

        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(context.applicationContext, songList[position].audioResId)
        mediaPlayer?.start()
        isPlaying = true


        val currentSong = getCurrentSong()
        if (currentSong != null) {
            NotificationHelper.showMusicNotification(context, currentSong.title, currentSong.artist)
        }

        mediaPlayer?.setOnCompletionListener {
            nextSong(context)
        }
    }

    fun togglePlayPause(context: Context) {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
                isPlaying = false
                NotificationHelper.cancelNotification(context)
            } else {
                it.start()
                isPlaying = true



                val currentSong = getCurrentSong()
                if (currentSong != null) {
                    NotificationHelper.showMusicNotification(context, currentSong.title, currentSong.artist)
                }
            }
        }
    }

    fun nextSong(context: Context) {
        if (songList.isEmpty()) return
        currentPosition++
        if (currentPosition >= songList.size) currentPosition = 0
        playSong(context, currentPosition)
    }

    fun previousSong(context: Context) {
        if (songList.isEmpty()) return
        currentPosition--
        if (currentPosition < 0) currentPosition = songList.size - 1
        playSong(context, currentPosition)
    }

    fun getMediaPlayer(): MediaPlayer? = mediaPlayer

    fun release(context: Context) {
        mediaPlayer?.release()
        mediaPlayer = null
        isPlaying = false
        NotificationHelper.cancelNotification(context)
    }
}