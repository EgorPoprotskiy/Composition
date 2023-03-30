package com.egorpoprotskiy.composition.domain.entity

//1.1.1.2 Создание сущности Question
data class Question(
    //значение суммы, которая отображается в кружке
    val sum: Int,
    //отображает видимое число
    val visibleNumber: Int,
    //содержит варианты ответов
    val options: List<Int>
) {
    val rightAnswer: Int
        get() = sum - visibleNumber
}