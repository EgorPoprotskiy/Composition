package com.egorpoprotskiy.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.egorpoprotskiy.composition.domain.entity.Level
import com.egorpoprotskiy.composition.databinding.FragmentChooseLevelBinding

class ChooseLevelFragment : Fragment() {
    //4.4  создать нулабельную ссылку на объект binding
    private var _binding: FragmentChooseLevelBinding? = null
    private val binding: FragmentChooseLevelBinding
        get() = _binding ?: throw java.lang.RuntimeException("FragmentChooseLevelBinding == null")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //4.4 привязать макет к binding
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        //4.4 вернуть root
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //5.2 Вызвать эту функцию на слушателе кликов
        with(binding) {
            buttonLevelTest.setOnClickListener {
                launchGameFragment(Level.TEST)
            }
            buttonLevelEasy.setOnClickListener {
                launchGameFragment(Level.EASY)
            }
            buttonLevelNormal.setOnClickListener {
                launchGameFragment(Level.NORMAL)
            }
            buttonLevelHard.setOnClickListener {
                launchGameFragment(Level.HARD)
            }
        }
    }
    //5.2 Создание фугнкции переключения экрана
    private fun launchGameFragment(level: Level) {
        //12.2 Указать навигацию и параметры, которые надо передать
        findNavController().navigate(ChooseLevelFragmentDirections.actionChooseLevelFragmentToGameFragment(level))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //4.4 В фрагментах указывать _binding = null
        _binding = null
    }
    //5.1 Создать фабричный метод
//    companion object {
//        //5.4 Создать константу дле перехода на этот фрагмент
//        const val NAME = "ShooseLevelFragment"
//        fun newInstance(): ChooseLevelFragment {
//            return ChooseLevelFragment()
//        }
//    }
}