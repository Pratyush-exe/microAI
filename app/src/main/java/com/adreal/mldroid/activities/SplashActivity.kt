package com.adreal.mldroid.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.adreal.mldroid.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
//        var mediaPlayer =  MediaPlayer.create(applicationContext, R.raw.splashAud)
//        mediaPlayer.start()

        @Suppress("DEPRECIATION")
        Handler().postDelayed(
            {
                startActivity(Intent(this, LoginActivity::class.java))
//                mediaPlayer.stop()
                finish()
            },
            2000
        )
    }
}