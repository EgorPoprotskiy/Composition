package com.egorpoprotskiy.composition.Domain.entity

//1.1.1.3 Создание сущности GameResult
data class GameResult(
    //победили мы или нет
    val winner: Boolean,
    //количество правильных ответов
    val countOfRightAnswers: Int,
    //общее количество вопросов, на которые ответил пользователь
    val countQuestions: Int,
    //настройки игры
    val gameSettings: GameSettings
)