package com.example.MySpy


import android.app.Application
import android.content.IntentFilter
import android.util.Log
import androidx.room.Room
import com.example.MySpy.data.AppManager
import com.example.MySpy.data.SharedPreferencesManager
import com.example.MySpy.data.VideoManager
import com.example.MySpy.data.db.AppDatabase
import com.example.MySpy.downloadmanager.DownloadReceiver
import com.example.MySpy.ui.main.MainViewModel
import com.example.MySpy.ui.main.apps.AppsViewModel
import com.example.MySpy.ui.main.lock.LockViewModel
import com.example.MySpy.ui.main.timer.TimerViewModel
import com.example.MySpy.ui.openVideoPlugin.SampleVideoViewModel
import com.example.MySpy.ui.settings.appmanager.AppManagerViewModel
import com.example.MySpy.ui.settings.appmanager.add.AddAppsViewModel
import com.example.MySpy.ui.settings.pin.PinViewModel
import com.example.MySpy.ui.settings.videomanager.ViewManagerViewModel
import org.koin.android.ext.android.startKoin
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import timber.log.Timber
import timber.log.Timber.DebugTree

class MainApp : Application() {


    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(appModule))



        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(object : Timber.Tree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    Log.e(tag, message, t)
                }
            })
        }

        val receiver = DownloadReceiver()
        registerReceiver(receiver, IntentFilter("com.example.MySpy.action.START_DOWNLOAD_SERVICE"))
    }


    val appModule = module {

        single { AppManager(get(), get()) }

        single { SharedPreferencesManager(get()) }

        single { VideoManager(applicationContext) }

        //room db for stats and app whitelist
        single { Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app-data").build() }
//
//        // Simple Presenter Factory, create one each time
//        factory { MySimplePresenter(get()) }

        // MyViewModel ViewModel
        viewModel { PinViewModel(get()) }

        viewModel { AppManagerViewModel(get()) }

        viewModel { AddAppsViewModel(get()) }

        viewModel { AppsViewModel(get()) }

        viewModel { TimerViewModel(get()) }

        viewModel { LockViewModel(get()) }

        viewModel { MainViewModel(get(), get(), get()) }

        viewModel { ViewManagerViewModel(applicationContext, get()) }

        viewModel { SampleVideoViewModel(get()) }


    }


}