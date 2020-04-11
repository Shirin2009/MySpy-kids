package com.example.MySpy.model

import android.graphics.drawable.Drawable
import com.example.MySpy.model.db.UserApp

data class AppElement(val packageName: String,
                      val title: String,
                      val icon: Drawable,
                      val dbModel :UserApp)