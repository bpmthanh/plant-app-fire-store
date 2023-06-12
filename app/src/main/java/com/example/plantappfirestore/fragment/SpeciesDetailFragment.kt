package com.example.plantappfirestore.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plantappfirestore.R
import com.example.plantappfirestore.adapter.SpeciesDetailAdapter
import com.example.plantappfirestore.callback.CallBack
import com.example.plantappfirestore.databinding.SpeciesDetailFragmentBinding
import com.example.plantappfirestore.model.Species
import com.google.firebase.firestore.FirebaseFirestore

class SpeciesDetailFragment : Fragment() {
    private lateinit var binding: SpeciesDetailFragmentBinding
    private var alphabet: String = ""
    private var listSpecies: ArrayList<Species> = ArrayList()
    private lateinit var speciesAdapter: SpeciesDetailAdapter
    private lateinit var dbFireStore: FirebaseFirestore
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = SpeciesDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindComponent()
        bindData()
        bindEvent()
    }

    private fun bindComponent() {
        dbFireStore = FirebaseFirestore.getInstance()
        speciesAdapter = SpeciesDetailAdapter()
        alphabet = arguments?.getString("alphabet") ?: ""
        binding.tvName.text = alphabet
    }

    private fun bindData() {
        getListSpecies()
    }

    private fun bindEvent() {
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        speciesAdapter.speciesDetail(object : CallBack.SpeciesDetail {
            override fun speciesDetail(
                category: String,
                title: String,
                description: String,
                kingdom: String,
                family: String,
                star: String,
                image: String,
                heart: String
            ) {
                val bundle = Bundle()
                bundle.putString("category", category)
                bundle.putString("title", title)
                bundle.putString("description", description)
                bundle.putString("kingdom", kingdom)
                bundle.putString("family", family)
                bundle.putString("star", star)
                bundle.putString("image", image)
                bundle.putString("heart", heart)
                findNavController().navigate(R.id.action_speciesDetailFragment_to_speciesProfileFragment, bundle)

            }

        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getListSpecies() {
        if (listSpecies.isNotEmpty()) {
            listSpecies.clear()
            speciesAdapter.notifyDataSetChanged()
        }
        dbFireStore.collection("Species").addSnapshotListener { documentSnapshots, error ->
                if (documentSnapshots != null) {
                    for (snapshot in documentSnapshots) {
                        if (snapshot?.getString("category") == alphabet) {
                            listSpecies.add(
                                Species(
                                    snapshot?.getString("category") ?: "",
                                    snapshot?.getString("title") ?: "",
                                    snapshot.getString("description") ?: "",
                                    snapshot.getString("kingdom") ?: "",
                                    snapshot.getString("family") ?: "",
                                    snapshot.getString("star") ?: "",
                                    snapshot.getString("image") ?: "",
                                    snapshot.getString("heart") ?: ""
                                )
                            )
                        }
                    }
                }
                onSetRecyclerView()
            }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onSetRecyclerView() {
        try {
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            //đổ data vào adapter
            speciesAdapter.setData(listSpecies)
//        //set adapter show lên màn hình người dùng
            binding.recyclerView.adapter = speciesAdapter
            speciesAdapter.notifyDataSetChanged()
        } catch (ex: Exception) {

        }
    }
}