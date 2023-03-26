package com.egorpoprotskiy.composition.Presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.egorpoprotskiy.composition.Domain.entity.GameResult
import com.egorpoprotskiy.composition.R
import com.egorpoprotskiy.composition.databinding.FragmentGameFinishedBinding

class GameFinishedFragment : Fragment() {
    private lateinit var gameResult: GameResult
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        //4.4 В фрагментах указывать _binding = null
        _binding = null
    }
    //5.4 Переход на нужный фрагмент с помощью кнопки назад
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                retryGame()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        binding.buttonRetry.setOnClickListener {
            retryGame()
        }
    }

    //5.3 Получение аргументов
    private fun parseArgs() {
        requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT)?.let {
            gameResult = it
        }
    }
    //5.4 Функция, возвращающая нас не на предыдущий экран, а на фрагмент выбора уровня сложности
    //5.5 Это второй способ, Чтобы вернуться на предпредыдущий фрагмент, удавлив при этом предыдущий
    private fun retryGame() {
        requireActivity().supportFragmentManager.popBackStack(GameFragment.NAME, FragmentManager.POP_BACK_STACK_INCLUSIVE )
    }

    //5.3 Создать фабричный метод в GameFinishedFragment, который в качестве параметра принимает
    // gameResult(putSerializable - т.к. GameResult наследуется от интерфейса Serializable)
    companion object {
        private const val KEY_GAME_RESULT = "game_result"
        fun newInstance(gameResult: GameResult): GameFinishedFragment {
            return GameFinishedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_GAME_RESULT, gameResult)
                }
            }
        }
    }
}