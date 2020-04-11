package com.example.MySpy.data

import android.os.Environment
import java.io.File

class AppFolder{

    val externalVideoPath = "/MySpy/"
    private val videoFolder = File(Environment.getExternalStorageDirectory(),externalVideoPath)
    val sdCardPath : String get() = videoFolder.absolutePath

    init{
        if(!videoFolder.exists()){
            videoFolder.mkdirs()
        }
    }

}