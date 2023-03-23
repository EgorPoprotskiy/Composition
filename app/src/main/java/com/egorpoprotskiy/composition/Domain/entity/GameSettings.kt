package com.egorpoprotskiy.composition.Domain.entity

//1.1.1.4 Создание сущности GameSettings
data class GameSettings(
    //максимальная сумма значания
    val maxSumValue: Int,
    //min кол-во очков для победы
    val minCountOfRightAnswers: Int,
    //min процент правидьных ответов для победы
    val minPercentOfRightAnswers: Int,
    //время игры в секундах
    val gameTimeInSeconds: Int
)