package com.egorpoprotskiy.composition.Presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.egorpoprotskiy.composition.Domain.entity.GameResult
import com.egorpoprotskiy.composition.R
import com.egorpoprotskiy.composition.databinding.FragmentGameFinishedBinding

class GameFinishedFragment : Fragment() {
    //12.3 Принимает аргументы из GameFragment
    private val args by navArgs<GameFinishedFragmentArgs>()
//    private lateinit var gameResult: GameResult
    //4.4  создать нулабельную ссылку на объект binding
    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw java.lang.RuntimeException("FragmentGameFinishedBinding == null")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //4.4 привязать макет к binding
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        //4.4 вернуть root
        return binding.root
    }
    //5.3 Вызов этой функции в методе onCreate()
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        parseArgs()
//    }
    override fun onDestroyView() {
        super.onDestroyView()
        //4.4 В фрагментах указывать _binding = null
        _binding = null
    }
    //5.4 Переход на нужный фрагмент с помощью кнопки назад
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //9.1 Вызов перезапуска игры
        setupOnClickListeners()
        //9.2 Вызов метода установки значений
        bindViews()
    }
    //9.1 Слушатель кликов на перезапуск игры
    private fun setupOnClickListeners() {
        binding.buttonRetry.setOnClickListener {
            retryGame()

        }
    }
    //9.2 Создать метод, в котором устанавливаем значения
    private fun bindViews() {
        with(binding) {
            //установка картинки
            emojiResult.setImageResource(getSmileResId())
            //установка текстов
            tvRequiredAnswers.text = String.format(getString(R.string.required_score),
                //12.4 Получение аргументов из GameFragment
                args.gameResult.gameSettings.minCountOfRightAnswers)
            tvScoreAnswers.text = String.format(getString(R.string.score_answer),
                args.gameResult.countOfRightAnswers)
            tvRequiredPercentage.text = String.format(getString(R.string.reauired_percentage),
                args.gameResult.gameSettings.minPercentOfRightAnswers)
            tvScorePercentage.text = String.format(getString(R.string.score_percentage), getPercentOfRightAnswers())
        }
    }
    //9.3 Выбор смайлика, грустный или довольный
    private fun getSmileResId(): Int {
        return if (args.gameResult.winner) {
            R.drawable.smile
        } else {
            R.drawable.smile_sad
        }
    }
    //9.4 Метод, возвращающий процент правильных ответов
    private fun getPercentOfRightAnswers() = with(args.gameResult) {
        if (countOfQuestions == 0) {
            0
        } else {
            ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
        }
    }
    //5.3 Получение аргументов
//    private fun parseArgs() {
//        requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT)?.let {
//            gameResult = it
//        }
//    }
    //5.4 Функция, возвращающая нас не на предыдущий экран, а на фрагмент выбора уровня сложности
    //5.5 Это второй способ, Чтобы вернуться на предпредыдущий фрагмент, удавлив при этом предыдущий
    private fun retryGame() {
        findNavController().popBackStack()
    }

    //5.3 Создать фабричный метод в GameFinishedFragment, который в качестве параметра принимает
    // gameResult(putSerializable - т.к. GameResult наследуется от интерфейса Serializable)
//    companion object {
//        const val KEY_GAME_RESULT = "game_result"
//        fun newInstance(gameResult: GameResult): GameFinishedFragment {
//            return GameFinishedFragment().apply {
//                arguments = Bundle().apply {
//                    putParcelable(KEY_GAME_RESULT, gameResult)
//                }
//            }
//        }
//    }
}