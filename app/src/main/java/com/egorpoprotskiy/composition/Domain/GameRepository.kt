package com.egorpoprotskiy.composition.Domain

import com.egorpoprotskiy.composition.Domain.entity.GameSettings
import com.egorpoprotskiy.composition.Domain.entity.Level
import com.egorpoprotskiy.composition.Domain.entity.Question
//2.1 СОздание репозитория, который будет иметь 2 метода
interface GameRepository {
    //2.1 Метод, генерирующий вопрос и возвращающий его
    fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question
    //2.1 Метод, принимающий уровень и возвращающий настройки игры
    fun getGameSettings(level: Level): GameSettings
}