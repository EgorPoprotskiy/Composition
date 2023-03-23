package com.egorpoprotskiy.composition.Data

import com.egorpoprotskiy.composition.Domain.GameRepository
import com.egorpoprotskiy.composition.Domain.entity.GameSettings
import com.egorpoprotskiy.composition.Domain.entity.Level
import com.egorpoprotskiy.composition.Domain.entity.Question
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object GameRepositoryImpl: GameRepository {

    private const val MIN_SUM_VALUE = 2
    private const val MIN_ANSWER_VALUE = 1

    override fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question {
        //значение суммы
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue+1)
        //значение числа, которое отображается
        val visibleNumber = Random.nextInt(MIN_ANSWER_VALUE, sum)
        val options = HashSet<Int>()
        //загаданное число
        val rightAnswer = sum - visibleNumber
        //загаданное число помещается в HashSet
        options.add(rightAnswer)
        //варианты ответов(нижняя граница)
        val from = max(rightAnswer - countOfOptions, MIN_ANSWER_VALUE)
        //варианты ответов(верхняя граница)
        val to = min(maxSumValue, rightAnswer + countOfOptions)
        //генерируем варианты ответов до тех пор, пока размер коллекции не будет равен числу countOfOptions(6)
        while (options.size < countOfOptions) {
            options.add(Random.nextInt(from, to))
        }
        //возвращает сущность Questions, которая принимает значение суммы, видимое число, варианты ответов.
        return  Question(sum, visibleNumber, options.toList())
    }

    override fun getGameSettings(level: Level): GameSettings {
        //Выбор уровня сложности и соотвутствующие ему настройки
        return when (level) {
            Level.TEST -> GameSettings(10, 3, 50, 8)
            Level.EASY -> GameSettings(10, 10, 70, 60)
            Level.NORMAL -> GameSettings(20, 20, 80, 40)
            Level.HARD -> GameSettings(30, 30, 90, 40)
        }
    }
}