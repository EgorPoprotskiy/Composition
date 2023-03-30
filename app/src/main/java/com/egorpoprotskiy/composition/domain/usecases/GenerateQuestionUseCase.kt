package com.egorpoprotskiy.composition.domain.usecases

import com.egorpoprotskiy.composition.domain.GameRepository
import com.egorpoprotskiy.composition.domain.entity.Question

//3.2 Т.к. useCase делает что-то одно, то нет смысла создавать функцию с таким же именем.
// Для этого просто переопределяют оператор invoke(), чтобы useCase можно было вызывать как метод
class GenerateQuestionUseCase(private val repository: GameRepository) {
    operator fun invoke(maxSumValue: Int): Question {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    private companion object {
        private const val COUNT_OF_OPTIONS = 6
    }
}