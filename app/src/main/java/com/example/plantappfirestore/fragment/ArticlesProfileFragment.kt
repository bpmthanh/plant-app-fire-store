package com.example.plantappfirestore.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.plantappfirestore.R
import com.example.plantappfirestore.databinding.ArtclesProfileFragmentBinding
import com.example.plantappfirestore.extensions.hide
import com.example.plantappfirestore.extensions.show
import com.google.firebase.firestore.FirebaseFirestore

class ArticlesProfileFragment : Fragment() {
    private lateinit var binding: ArtclesProfileFragmentBinding
    private var strName: String = ""
    private var strTitle: String = ""
    private var strDescription: String = ""
    private var strAvatar: String = ""
    private var strImage: String = ""
    private var strHeart: String = ""
    private var strDate: String = ""
    private lateinit var dbFireStore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ArtclesProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindComponent()
        bindData()
        bindEvent()
    }

    private fun bindComponent() {
        binding.backgroundLoading.show()
        binding.progressBar.show()
        strName = arguments?.getString("name") ?: ""
        strTitle = arguments?.getString("title") ?: ""
        strDescription = arguments?.getString("description") ?: ""
        strAvatar = arguments?.getString("avatar") ?: ""
        strDate = arguments?.getString("date") ?: ""
        strImage = arguments?.getString("image") ?: ""
        strHeart = arguments?.getString("heart") ?: ""
        dbFireStore = FirebaseFirestore.getInstance()
        setLayout()
    }

    private fun setLayout() {
        Glide.with(requireContext()).load(strImage).into(binding.ivBackground)
        binding.tvTitle.text = strTitle
        binding.tvDescription.text = strDescription
        Glide.with(requireContext()).load(strAvatar).into(binding.ivAvatar)
        binding.tvName.text = strName
        binding.tvDate.text = strDate.replace("00000", " . ")
        setHeart()
    }

    private fun setHeart() {
        if (strHeart == "true") {
            binding.cvHeart.background.setTint(ContextCompat.getColor(requireContext(), R.color.red))
        } else {
            binding.cvHeart.background.setTint(ContextCompat.getColor(requireContext(), R.color.mini_black))
        }
        binding.backgroundLoading.hide()
        binding.progressBar.hide()
    }

    private fun bindData() {

    }

    private fun bindEvent() {
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.cvHeart.setOnClickListener {
            binding.backgroundLoading.show()
            binding.progressBar.show()
            when (strHeart) {
                "true" -> {
                    strHeart = "false"
                    dbFireStore.collection("Articles").document(strName).update("heart", "false")
                    binding.cvHeart.background.setTint(ContextCompat.getColor(requireContext(), R.color.mini_black))
                }
                "false" -> {
                    strHeart = "true"
                    dbFireStore.collection("Articles").document(strName).update("heart", "true")
                    binding.cvHeart.background.setTint(ContextCompat.getColor(requireContext(), R.color.red))
                }
            }
            binding.backgroundLoading.hide()
            binding.progressBar.hide()
        }
    }
}