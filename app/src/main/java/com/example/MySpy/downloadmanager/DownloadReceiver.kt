package com.example.MySpy.downloadmanager


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class DownloadReceiver : BroadcastReceiver() {

    val DOWNLOAD_URL = "com.example.MySpy.DOWNLOAD_URL"
    val TITLE = "com.example.MySpy.TITLE"
    val FILE_NAME = "com.example.MySpy.FILE_NAME"

    override fun onReceive(context: Context, intent: Intent) {

        val downloadUrl = intent.getStringExtra(DOWNLOAD_URL)
        val title = intent.getStringExtra(TITLE)
        val fileName = intent.getStringExtra(FILE_NAME)
        if (downloadUrl != null && title != null && fileName != null) {
            context.startService(
                DownloadVideoService.getDownloadService(
                    context,
                    downloadUrl,
                    title,
                    fileName
                )
            )
        } else {
            Toast.makeText(context, "Not enough data to download the video!", Toast.LENGTH_SHORT).show()
        }


    }

}


