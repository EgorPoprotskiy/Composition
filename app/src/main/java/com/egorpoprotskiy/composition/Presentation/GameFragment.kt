package com.egorpoprotskiy.composition.Presentation

import android.media.MediaFormat.KEY_LEVEL
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.egorpoprotskiy.composition.Domain.entity.GameResult
import com.egorpoprotskiy.composition.Domain.entity.GameSettings
import com.egorpoprotskiy.composition.Domain.entity.Level
import com.egorpoprotskiy.composition.R
import com.egorpoprotskiy.composition.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    //5.2 переменная для хранения уровня
    private lateinit var level: Level

    //4.4  создать нулабельную ссылку на объект binding
    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw java.lang.RuntimeException("FragmentGameBinding == null")
    //5.2 Вызов этой функции в методе onCreate()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //4.4 привязать макет к binding
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        //4.4 вернуть root
        return binding.root
    }
    //5.3 Вызвать эту функцию на слушателе кликов
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvLeftNumber.setOnClickListener {
            launchGameFinishedFragment(GameResult(true, 12,23, GameSettings(9,9,9,9)))
        }
    }

    //5.3 Создание фугнкции переключения экрана
    private fun launchGameFinishedFragment(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main_container, GameFinishedFragment.newInstance(gameResult)).addToBackStack(null).commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //4.4 В фрагментах указывать _binding = null
        _binding = null
    }
    //5.2 получение аргументов
    private fun parseArgs() {
        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
            level = it
        }
    }
    //5.2 Создать фабричный метод в GameFragment, который в качестве параметра принимает уровень
    // (putSerializable - т.к. Level - это перечисление enum и наследуется от интерфейса Serializable)
    companion object {
        //5.5 Указать константу для перехода. Указывать в том фрагмента, в который нам надо перейти.
        const val NAME = "GameFragment"
        private const val KEY_LEVEL = "level"
        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, level)
                }
            }
        }
    }
}