package com.egorpoprotskiy.composition.Domain.entity

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

//1.1.1.4 Создание сущности GameSettings
//Аннотация для того, чтобы не пришлось переопределять все методы вручную, при указании интерфейса Parcelable
@Parcelize
data class GameSettings(
    //максимальная сумма значания
    val maxSumValue: Int,
    //min кол-во очков для победы
    val minCountOfRightAnswers: Int,
    //min процент правидьных ответов для победы
    val minPercentOfRightAnswers: Int,
    //время игры в секундах
    val gameTimeInSeconds: Int
): Parcelable