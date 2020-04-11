package com.example.MySpy.ui.settings.appmanager.add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.MySpy.data.AppManager
import com.example.MySpy.model.AppElement
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class AddAppsViewModel(private val appManager: AppManager) : ViewModel() {

    val loading = MutableLiveData<Boolean>()
    var apps = MutableLiveData<List<AppElement>>()
    private val compositeDisposable = CompositeDisposable()

    init {

        loading.postValue(true)
        compositeDisposable.add(appManager.getAddableApps().subscribe({
            loading.postValue(false)
            apps.postValue(it)
        }, {
            Timber.e(it)
            loading.postValue(false)
        }))

    }

    public override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

}