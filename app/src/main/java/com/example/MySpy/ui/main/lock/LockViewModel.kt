package com.example.MySpy.ui.main.lock

import androidx.lifecycle.ViewModel
import com.example.MySpy.data.SharedPreferencesManager
import io.reactivex.disposables.CompositeDisposable

class LockViewModel(private val sharedPreferencesManager: SharedPreferencesManager) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    init {


    }

    public override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun isPinCorrect(otp: String): Boolean {
        return sharedPreferencesManager.getPin().toString() == otp
    }

    fun isPinSet(): Boolean {
        return sharedPreferencesManager.hasPin
    }

}