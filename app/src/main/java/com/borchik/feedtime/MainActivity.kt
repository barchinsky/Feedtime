package com.borchik.feedtime

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.borchik.addFeeding.service.AddFeedingService
import com.borchik.feedtime.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(500)
        setTheme(R.style.Theme_Feedtime)
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)

        Timber.plant(Timber.DebugTree())

        setContentView(activityMainBinding.root)
    }

    override fun onPostResume() {
        super.onPostResume()

        val shortcut = intent?.extras?.get("feeding")
        Timber.i("Intent extras: $shortcut")

        when (intent?.extras?.get("feeding")) {
            "feed" -> {
                startService(Intent(this, AddFeedingService::class.java))
                finish()
            }
            else -> Timber.w("Unhandled extra Intent.feeding=${intent?.extras?.get("feeding")}")
        }
    }
}