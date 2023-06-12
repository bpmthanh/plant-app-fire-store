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
import com.example.plantappfirestore.databinding.SpeciesProfileFragmentBinding
import com.example.plantappfirestore.extensions.hide
import com.example.plantappfirestore.extensions.show
import com.example.plantappfirestore.model.Species
import com.google.firebase.firestore.FirebaseFirestore

class SpeciesProfileFragment : Fragment() {
    private lateinit var binding: SpeciesProfileFragmentBinding
    private var strCategory: String = ""
    private var strTitle: String = ""
    private var strDescription: String = ""
    private var strKingdom: String = ""
    private var strFamily: String = ""
    private var strStar: String = ""
    private var strImage: String = ""
    private var strHeart: String = ""
    private lateinit var dbFireStore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SpeciesProfileFragmentBinding.inflate(inflater, container, false)
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
        strCategory = arguments?.getString("category") ?: ""
        strTitle = arguments?.getString("title") ?: ""
        strDescription = arguments?.getString("description") ?: ""
        strKingdom = arguments?.getString("kingdom") ?: ""
        strFamily = arguments?.getString("family") ?: ""
        strStar = arguments?.getString("star") ?: ""
        strImage = arguments?.getString("image") ?: ""
        strHeart = arguments?.getString("heart") ?: ""
        dbFireStore = FirebaseFirestore.getInstance()
        setLayout()
    }

    private fun setLayout() {
        Glide.with(requireContext()).load(strImage).into(binding.ivBackground)
        binding.tvTitle.text = strTitle
        binding.tvDescription.text = strDescription
        binding.tvKingdom.text = strKingdom
        binding.tvFamily.text = strFamily
        binding.tvNumberStar.text = strStar
        setHeart()

        if (strStar[0].toString() == "1") {
            binding.ivStart2.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_image_start_disable))
            binding.ivStart3.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_image_start_disable))
            binding.ivStart4.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_image_start_disable))
            binding.ivStart5.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_image_start_disable))
        } else if (strStar[0].toString() == "2") {
            binding.ivStart3.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_image_start_disable))
            binding.ivStart4.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_image_start_disable))
            binding.ivStart5.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_image_start_disable))
        } else if (strStar[0].toString() == "3") {
            binding.ivStart4.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_image_start_disable))
            binding.ivStart5.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_image_start_disable))
        } else if (strStar[0].toString() == "4") {
            binding.ivStart5.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_image_start_disable))
        }
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
                    dbFireStore.collection("Species").document(strTitle).update("heart", "false")
                    binding.cvHeart.background.setTint(ContextCompat.getColor(requireContext(), R.color.mini_black))
                }
                "false" -> {
                    strHeart = "true"
                    dbFireStore.collection("Species").document(strTitle).update("heart", "true")
                    binding.cvHeart.background.setTint(ContextCompat.getColor(requireContext(), R.color.red))
                }
            }
            binding.backgroundLoading.hide()
            binding.progressBar.hide()
        }
    }

    private fun getHeart() {
        dbFireStore.collection("Species").addSnapshotListener { documentSnapshots, error ->
            if (documentSnapshots != null) {
                for (snapshot in documentSnapshots) {
                    if (snapshot?.getString("category") == strCategory &&
                        snapshot.getString("title") == strTitle &&
                        snapshot.getString("start") == strStar  ) {

                        strHeart = snapshot.getString("heart") ?: "false"

                    }
                }
            }
        }
    }
}