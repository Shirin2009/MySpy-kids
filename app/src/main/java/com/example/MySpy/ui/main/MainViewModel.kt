package com.example.MySpy.ui.main

import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.MySpy.data.AppManager
import com.example.MySpy.data.MyFileObserver
import com.example.MySpy.data.SharedPreferencesManager
import com.example.MySpy.data.VideoManager
import com.example.MySpy.model.VideoElement
import com.example.MySpy.ui.settings.SettingsActivity
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class MainViewModel(
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val appManager: AppManager,
    private val videoManager: VideoManager
) : ViewModel() {

    enum class ExtraUi {
        NONE,
        APPS,
        UNLOCK,
        TIMER
    }

    val extraUi = MutableLiveData<ExtraUi>()

    val loading = MutableLiveData<Boolean>()
    var videos = MutableLiveData<List<VideoElement>>()
    private val compositeDisposable = CompositeDisposable()

    init {
        val fileObserver = MyFileObserver(videoManager.sdCardPath)

        extraUi.postValue(ExtraUi.NONE)

        compositeDisposable.add(fileObserver.observable
            .subscribe(
                { path ->
                    Timber.i("@@@ path: $path")
                    reloadVideos()
                },
                { t ->
                    Timber.e(t)
                }
            ))
    }

    fun reloadVideos() {
        loading.postValue(true)
//        videos.postValue(videoManager.getVideos())
//        loading.postValue(false)

        compositeDisposable.add(videoManager.loadVideos().subscribe({
            videos.postValue(it)
            loading.postValue(false)
        }, {
            it.printStackTrace()
            loading.postValue(false)
        }))
    }


    public override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }


    fun isPinSet(): Boolean {
        return sharedPreferencesManager.hasPin
    }

    fun showApps() {
        if (extraUi.value == ExtraUi.APPS) {
            extraUi.postValue(ExtraUi.NONE)
        } else {
            extraUi.postValue(ExtraUi.APPS)
        }
    }

    fun showSettings(context: Context) {

        if (isPinSet()) {
            if (extraUi.value != ExtraUi.UNLOCK) {
                extraUi.postValue(ExtraUi.UNLOCK)
            }
        } else {
            resetExtraUi()
            context.startActivity(Intent(context, SettingsActivity::class.java))
        }
    }

    fun showTimer() {
        if (extraUi.value == ExtraUi.TIMER) {
            extraUi.postValue(ExtraUi.NONE)
        } else {
            extraUi.postValue(ExtraUi.TIMER)
        }
    }

    fun onPlayVideo(video: VideoElement) {
        resetExtraUi()
    }

    fun resetExtraUi() {
        extraUi.postValue(ExtraUi.NONE)
    }

}