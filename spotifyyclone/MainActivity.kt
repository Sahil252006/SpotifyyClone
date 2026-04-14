package com.example.spotifyyclone

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.spotifyyclone.activities.NowPlayingActivity
import com.example.spotifyyclone.fragments.HomeFragment
import com.example.spotifyyclone.fragments.LibraryFragment
import com.example.spotifyyclone.fragments.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView
    private lateinit var miniPlayerLayout: LinearLayout
    private lateinit var ivMiniPlayerImage: ImageView
    private lateinit var tvMiniPlayerTitle: TextView
    private lateinit var tvMiniPlayerArtist: TextView
    private lateinit var btnMiniPlayPause: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 🔥 Step 6: Ask notification permission
        requestNotificationPermission()

        bottomNav = findViewById(R.id.bottomNav)
        miniPlayerLayout = findViewById(R.id.miniPlayerLayout)
        ivMiniPlayerImage = findViewById(R.id.ivMiniPlayerImage)
        tvMiniPlayerTitle = findViewById(R.id.tvMiniPlayerTitle)
        tvMiniPlayerArtist = findViewById(R.id.tvMiniPlayerArtist)
        btnMiniPlayPause = findViewById(R.id.btnMiniPlayPause)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, HomeFragment())
                .commit()
        }

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, HomeFragment())
                        .commit()
                    true
                }

                R.id.search -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, SearchFragment())
                        .commit()
                    true
                }

                R.id.library -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, LibraryFragment())
                        .commit()
                    true
                }

                else -> false
            }
        }

        miniPlayerLayout.setOnClickListener {
            if (MusicManager.getCurrentSong() != null) {
                startActivity(Intent(this, NowPlayingActivity::class.java))
            }
        }

        btnMiniPlayPause.setOnClickListener {
            MusicManager.togglePlayPause(this)
            updateMiniPlayer()
        }

        updateMiniPlayer()
    }

    override fun onResume() {
        super.onResume()
        updateMiniPlayer()
    }

    private fun updateMiniPlayer() {
        val currentSong = MusicManager.getCurrentSong()

        if (currentSong != null) {
            miniPlayerLayout.visibility = LinearLayout.VISIBLE
            ivMiniPlayerImage.setImageResource(currentSong.imageResId)
            tvMiniPlayerTitle.text = currentSong.title
            tvMiniPlayerArtist.text = currentSong.artist

            if (MusicManager.isPlaying) {
                btnMiniPlayPause.setImageResource(android.R.drawable.ic_media_pause)
            } else {
                btnMiniPlayPause.setImageResource(android.R.drawable.ic_media_play)
            }
        } else {
            miniPlayerLayout.visibility = LinearLayout.GONE
        }
    }

    // 🔥 Step 6 function (IMPORTANT)
    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
            }
        }
    }
}