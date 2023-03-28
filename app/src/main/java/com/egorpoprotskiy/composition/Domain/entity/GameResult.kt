package com.egorpoprotskiy.composition.Domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

//1.1.1.3 Создание сущности GameResult
//Аннотация для того, чтобы не пришлось переопределять все методы вручную, при указании интерфейса Parcelable
@Parcelize
data class GameResult(
    //победили мы или нет
    val winner: Boolean,
    //количество правильных ответов
    val countOfRightAnswers: Int,
    //общее количество вопросов, на которые ответил пользователь
    val countOfQuestions: Int,
    //настройки игры
    val gameSettings: GameSettings
): Parcelable