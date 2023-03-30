package com.egorpoprotskiy.composition.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.egorpoprotskiy.composition.domain.entity.Level

//10.7 Создать ViewModelFactory
class GameViewModelFactory(
    private val level: Level,
    private val application: Application
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(application, level) as T
        }
        throw java.lang.RuntimeException("Unknown view model class $modelClass")
    }
}