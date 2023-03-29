package com.egorpoprotskiy.composition.Presentation

import android.content.res.ColorStateList
import android.media.MediaFormat.KEY_LEVEL
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.egorpoprotskiy.composition.Domain.entity.GameResult
import com.egorpoprotskiy.composition.Domain.entity.GameSettings
import com.egorpoprotskiy.composition.Domain.entity.Level
import com.egorpoprotskiy.composition.R
import com.egorpoprotskiy.composition.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    //12.3 Принимает аргументы из ChooseLevelFragment
    private val args by navArgs<GameFragmentArgs>()
    //5.2 переменная для хранения уровня
//    private lateinit var level: Level
    //10.8 изменить ленивую инициализацию viewModel
    //12.3 Вставляем полученные аргументы
    private val viewModelFactory by lazy {
        GameViewModelFactory(args.level, requireActivity().application)
    }
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
    }
    //8.1 Все варианты ответов записать в список, чтобы с ними можно было работать в цикле
    private val tvOptions by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvOption1)
            add(binding.tvOption2)
            add(binding.tvOption3)
            add(binding.tvOption4)
            add(binding.tvOption5)
            add(binding.tvOption6)
        }
    }

    //4.4  создать нулабельную ссылку на объект binding
    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw java.lang.RuntimeException("FragmentGameBinding == null")
    //5.2 Вызов этой функции в методе onCreate()
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        parseArgs()
//    }

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
        //Вызов функции, в которой подписывались на все объекты LiveData
        observeViewModel()
        //8.4 Вызов функции слушателя кликов
        setClickListenersToOption()
//        //8.3 Старт игры из ViewModel
//        viewModel.startGame(level)
    }
    //8.4 СЛушатель кликов на варианты ответов
    private fun setClickListenersToOption() {
        for (tvOption in tvOptions) {
            tvOption.setOnClickListener {
                viewModel.shooseAnswer(tvOption.text.toString().toInt())
            }
        }
    }
    //8.2 Подписываемся на все объекты LiveData
    private fun observeViewModel() {
        //Запись вариантов ответа в текст
        viewModel.question.observe(viewLifecycleOwner){
            binding.tvSum.text = it.sum.toString()
            binding.tvLeftNumber.text = it.visibleNumber.toString()
            for (i in 0 until tvOptions.size) {
                tvOptions[i].text = it.options[i].toString()
            }
        }
        //Процент правильных ответов(в прогрессБаре)
        viewModel.percenOfRightAnswers.observe(viewLifecycleOwner) {
            binding.progressBar.setProgress(it, true)
        }
        //Установка цвета в тексте
        viewModel.enoughCount.observe(viewLifecycleOwner) {
            binding.tvAnswerProgress.setTextColor(getColorByState(it))
        }
        //Установка цвета в прогресс баре
        viewModel.enoughPercent.observe(viewLifecycleOwner) {
            val color = getColorByState(it)
            binding.progressBar.progressTintList = ColorStateList.valueOf(color)
        }
        //Подписываемся на время
        viewModel.formattedTime.observe(viewLifecycleOwner) {
            binding.tvTimer.text = it
        }
        //Подписываемя на минимальный процент
        viewModel.minPercent.observe(viewLifecycleOwner) {
            binding.progressBar.secondaryProgress = it
        }
        //Подписываемся на gameResult
        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchGameFinishedFragment(it)
        }
        //прогресс правильных ответов(в штуках)
        viewModel.progressAnswers.observe(viewLifecycleOwner) {
            binding.tvAnswerProgress.text = it
        }
    }
    // Функция определения цвета состояния(если состояние Хорошее, то цвет зеленый, иначе - красный)
    private fun getColorByState(goodState: Boolean): Int {
        val colorResId = if (goodState) {
            android.R.color.holo_green_light
        } else {
            android.R.color.holo_red_light
        }
        return ContextCompat.getColor(requireContext(), colorResId)
    }

    //5.3 Создание фугнкции переключения экрана
    private fun launchGameFinishedFragment(gameResult: GameResult) {
        //12.2 Указать навигацию и параметры, которые надо передать
        findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameFinishedFragment(gameResult))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //4.4 В фрагментах указывать _binding = null
        _binding = null
    }
    //5.2 получение аргументов
//    private fun parseArgs() {
//        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
//            level = it
//        }
//    }
    //5.2 Создать фабричный метод в GameFragment, который в качестве параметра принимает уровень
    // (putSerializable - т.к. Level - это перечисление enum и наследуется от интерфейса Serializable)
//    companion object {
//        //5.5 Указать константу для перехода. Указывать в том фрагмента, в который нам надо перейти.
//        const val NAME = "GameFragment"
//        const val KEY_LEVEL = "level"
//        fun newInstance(level: Level): GameFragment {
//            return GameFragment().apply {
//                arguments = Bundle().apply {
//                    putParcelable(KEY_LEVEL, level)
//                }
//            }
//        }
//    }
}