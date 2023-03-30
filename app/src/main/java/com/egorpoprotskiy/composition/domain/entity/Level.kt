package com.egorpoprotskiy.composition.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


//1.1.1.1 Создание сущности Level
@Parcelize
enum class Level: Parcelable {
    TEST, EASY, NORMAL, HARD
}