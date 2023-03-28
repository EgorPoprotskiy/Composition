package com.egorpoprotskiy.composition.Presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.egorpoprotskiy.composition.Data.GameRepositoryImpl
import com.egorpoprotskiy.composition.Domain.entity.GameResult
import com.egorpoprotskiy.composition.Domain.entity.GameSettings
import com.egorpoprotskiy.composition.Domain.entity.Level
import com.egorpoprotskiy.composition.Domain.entity.Question
import com.egorpoprotskiy.composition.Domain.usecases.GenerateQuestionUseCase
import com.egorpoprotskiy.composition.Domain.usecases.GetGameSettingsUseCase
import com.egorpoprotskiy.composition.R
//Наследование от AndroidViewModel для того, чтобы можно было получить строку из строковых ресурсов в методе updateProgress()
class GameViewModel(application: Application): AndroidViewModel(application) {
    //7 Переменная для сохранения настроек игры
    private lateinit var gameSettings: GameSettings
    //7 Переменная для сохранения уровня игры
    private lateinit var level: Level

    // Переменная для метода updateProgress()
    private val context = application

    //7 Репозиторий для useCase
    private val repository = GameRepositoryImpl
    //7 Ссылки на useCase
    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    //7 Переменная для таймера(для его запуска и остановки)
    private var timer: CountDownTimer? = null

    // Переменная для формата даты
    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    //переменная для генерации нового вопроса
    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    //Переменная для отображения процента правильных ответов
    private val _percenOfRightAnswers = MutableLiveData<Int>()
    val percenOfRightAnswers: LiveData<Int>
        get() = _percenOfRightAnswers

    //Переменная прогресса правильных ответов
    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String>
        get() = _progressAnswers

    //Переменная хранит достаточное количество правильных ответов до победы
    private val _enoughCount = MutableLiveData<Boolean>()
    val enoughCount: LiveData<Boolean>
        get() = _enoughCount
    //Переменная хранит достаточный процент правильных ответов до победы
    private val _enoughPercent = MutableLiveData<Boolean>()
    val enoughPercent: LiveData<Boolean>
        get() = _enoughPercent

    //переменная, какой должен быть минимальный процент правильных ответов
    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    //переменная, в которую сохраниться результат игры
    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    //количество правильных ответов
    private var countOfRightAnswers = 0
    //количество вопросов
    private var countOfQuestions = 0

    //7 Метод будет вызываться из фрагмента
    fun startGame(level: Level) {
        //получение настроек игры
        getGameSettings(level)
        //запуск таймера
        startTimer()
        //генерация вопроса
        generateQuestion()
        //Обновление прогресса
        updateProgress()
    }

    //7 В этот метод будет передаваться выбранный вариант ответа
    fun shooseAnswer(number: Int) {
        //Получение правильного ответа
        checkAnswer(number)
        //Обновление прогресса
        updateProgress()
        //генерация вопроса
        generateQuestion()
    }

    //7 Метод, в который сохраняется прогресс
    private fun updateProgress() {
        //сохранене процента правильных ответов в переменную
        val percent = calculatePercentOfRightAnswers()
        //сохранене процента правильных ответов в переменную LiveData
        _percenOfRightAnswers.value = percent
        //Сохранение в переменную строки с прогрессам по ответам
        _progressAnswers.value = String.format(
            //Чтобы получить строковый ресурс, нужен context(для этого класс должен наследоваться от AndroidViewModel)
            context.resources.getString(R.string.progress_answers),
            //передаем количество правильных ответов
            countOfRightAnswers,
            //передаем минимальное коичество правильных ответов до победы
            gameSettings.minCountOfRightAnswers
        )
        //Если мы набрали количество очков больше или равно минимальному, то будет true и наоборот
        _enoughCount.value = countOfRightAnswers >= gameSettings.minCountOfRightAnswers
        //Если мы набрали процент очков больше или равно минимальному, то будет true и наоборот
        _enoughPercent.value = percent >= gameSettings.minPercentOfRightAnswers
    }
    //7 Метод получения процента правильных ответов
    private fun calculatePercentOfRightAnswers(): Int {
        if (countOfQuestions == 0) {
            return 0
        }
        return ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }

    //7 Получение правильного ответа
    private fun checkAnswer(number: Int) {
        // получение ответа из объекта question с помощью rightAnswer(геттер из класса Question)
        val rightAnswer = question.value?.rightAnswer
        //если ответ совпадает
        if (number == rightAnswer) {
            //то количество правильных ответов увеличивается на 1
            countOfRightAnswers++
        }
        //количество вопросов всегда увеличивается на 1
        countOfQuestions++
        //И после ответа генерируется следующий вопрос
        generateQuestion()
    }

    //7 Получение настроек игры(уровня)
    private fun getGameSettings(level: Level) {
        this.level = level
        this.gameSettings = getGameSettingsUseCase(level)
        //присвивание значения переменной из gameSetting
        _minPercent.value = gameSettings.minPercentOfRightAnswers
    }

    //7 Запуск таймера после получения настроек игры(уровня)
    private fun startTimer() {
        // Создание таймера
        timer = object : CountDownTimer(
            //Умножить на 1000L, чтобы получить миллисекунды.
            gameSettings.gameTimeInSeconds * MILLIS_IN_SECONDS,
            // Счетчик таймера каждую секунду
            MILLIS_IN_SECONDS
        ) {
            //переопределяем методы
            override fun onTick(millisUntilFinished: Long) {
                //Логика формата времени будет реализована в ViewModel(здесь) и сохранена в переменную LiveData,
                // чтобы в дальнейшем можно было обратиться к ней из фрагмента
                _formattedTime.value = formatTime(millisUntilFinished)
            }

            override fun onFinish() {
                finishGame()
            }

        }
        //старт таймера
        timer?.start()
    }
    //7 Генерация следующего вопроса, после  запуска таймера
    private fun generateQuestion() {
        //сохранение в переменную максимального значения суммы из gameSettings
        _question.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }

    //7 Функция для форматирования времени. Вызывается из функции onTick
    private fun formatTime(millisUntilFinished: Long): String {
        //Получения количества секунд
        val seconds = millisUntilFinished / MILLIS_IN_SECONDS
        //Перевод в минуты
        val minuts = seconds / SECONDS_IN_MINUTES
        //Оставшееся количество секунд
        val leftSeconds = seconds - (minuts * SECONDS_IN_MINUTES)
        //возврат строки. (%02d - если 1 секунда, то будет отображаться 01)
        return String.format("%02d:%02d", minuts, leftSeconds)
    }

    //7 Функция завершения игры
    private fun finishGame() {
        //Присваивание значения переменной
        _gameResult.value = GameResult(
            //победили мы или нет
            winner = enoughCount.value == true && enoughPercent.value == true,
            //количесво правильных ответов
            countOfRightAnswers = countOfRightAnswers,
            //количество вопросов
            countOfQuestions = countOfQuestions,
            //настрйоки игры
            gameSettings = gameSettings
        )
    }
    //7 Метод для отмены таймера, после ухода с фрагмента
    override fun onCleared() {
        super.onCleared()
        //остановка таймера
        timer?.cancel()
    }

    companion object {
        //
        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES = 60
    }
}