package com.lifecalendar.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val imageUpdateRequest = PeriodicWorkRequestBuilder<WallpaperWorker>(24, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "MidnightWallpaperUpdater",
            ExistingPeriodicWorkPolicy.KEEP,
            imageUpdateRequest
        )

        val immediateRequest = OneTimeWorkRequestBuilder<WallpaperWorker>().build()
        WorkManager.getInstance(this).enqueue(immediateRequest)

        finish()
    }
}
