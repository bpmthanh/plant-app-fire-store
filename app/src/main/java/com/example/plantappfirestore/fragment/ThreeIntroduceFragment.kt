package com.example.plantappfirestore.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.plantappfirestore.R
import com.example.plantappfirestore.databinding.FragmentOneIntroduceBinding

class ThreeIntroduceFragment : Fragment() {
    private lateinit var binding: FragmentOneIntroduceBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentOneIntroduceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageView.setImageResource(R.drawable.image_introduce_three)
        binding.tvTitle.text = requireContext().getString(R.string.title_introduce_three)
        binding.tvDescription.text = requireContext().getString(R.string.description_introduce_three)

        bindComponent()
        bindData()
        bindEvent()
    }

    private fun bindComponent() {

    }

    private fun bindData() {

    }

    private fun bindEvent() {

    }
}