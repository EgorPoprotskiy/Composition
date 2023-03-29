package com.egorpoprotskiy.composition.Presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.egorpoprotskiy.composition.R
import com.egorpoprotskiy.composition.databinding.FragmentWelcomeBinding


class WelcomeFragment : Fragment() {
    //4.4  создать нулабельную ссылку на объект binding
    private var _binding: FragmentWelcomeBinding? = null
    private val binding: FragmentWelcomeBinding
        get() = _binding ?: throw java.lang.RuntimeException("FragmentWelcomeBinding == null")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //4.4 привязать макет к binding
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        //4.4 вернуть root
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonUnderstand.setOnClickListener {
            //5.1 Вызвов функции открытия фрагмента
            launchChooseLevelFragment()
        }
    }

    //5.1 Создать функцию переключения экрана(добавить в бэкстэк, чтобы при нажатии кнопки назад закрывался только этот фрагмент)

    private fun launchChooseLevelFragment() {
        //11.5 Переход от WelcomFragment в ChooseLevelFragment с помощью Navigation
        findNavController().navigate(R.id.action_welcomeFragment_to_chooseLevelFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //4.4 В фрагментах указывать _binding = null
        _binding = null
    }
}