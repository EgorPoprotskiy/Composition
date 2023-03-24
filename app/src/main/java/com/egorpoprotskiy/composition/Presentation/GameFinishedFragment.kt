package com.egorpoprotskiy.composition.Presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.egorpoprotskiy.composition.R
import com.egorpoprotskiy.composition.databinding.FragmentGameFinishedBinding

class GameFinishedFragment : Fragment() {
    //4.4  создать нулабельную ссылку на объект binding
    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw java.lang.RuntimeException("FragmentGameFinishedBinding == null")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //4.4 привязать макет к binding
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        //4.4 вернуть root
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //4.4 В фрагментах указывать _binding = null
        _binding = null
    }
}