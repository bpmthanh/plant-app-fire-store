package com.example.plantappfirestore.fragment

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plantappfirestore.R
import com.example.plantappfirestore.adapter.ArticlesAdapter
import com.example.plantappfirestore.adapter.SpeciesDetailAdapter
import com.example.plantappfirestore.databinding.ProfileFragmentBinding
import com.example.plantappfirestore.extensions.hide
import com.example.plantappfirestore.extensions.show
import com.example.plantappfirestore.model.Articles
import com.example.plantappfirestore.model.Species
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : Fragment() {
    private lateinit var binding: ProfileFragmentBinding
    private var db: FirebaseDatabase? = null
    private var ref: DatabaseReference? = null
    private var listSpecies: ArrayList<Species> = ArrayList()
    private var listArticles: ArrayList<Articles> = ArrayList()
    private lateinit var speciesAdapter: SpeciesDetailAdapter
    private lateinit var dbFireStore: FirebaseFirestore
    private lateinit var articlesAdapter: ArticlesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindComponent()
        bindData()
        bindEvent()
    }

    private fun bindComponent() {
        db = FirebaseDatabase.getInstance()
        ref = db?.reference
        dbFireStore = FirebaseFirestore.getInstance()
        speciesAdapter = SpeciesDetailAdapter()
        articlesAdapter = ArticlesAdapter()
    }

    private fun bindData() {
        binding.backgroundLoading.show()
        binding.progressBar.show()
        getUser()
        getListSpecies()
        getListArticles()
    }

    private fun bindEvent() {
        binding.apply {
            cvSpecies.setOnClickListener {
                cvSpecies.background.setTint(ContextCompat.getColor(requireContext(), R.color.blue))
                tvSpecies.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                cvArticles.background.setTint(ContextCompat.getColor(requireContext(), R.color.white))
                tvArticles.setTextColor(ContextCompat.getColor(requireContext(), R.color.light_gray))
                recyclerViewSpecies.show()
                recyclerViewArticles.hide()
            }

            cvArticles.setOnClickListener {
                cvSpecies.background.setTint(ContextCompat.getColor(requireContext(), R.color.white))
                tvSpecies.setTextColor(ContextCompat.getColor(requireContext(), R.color.light_gray))
                cvArticles.background.setTint(ContextCompat.getColor(requireContext(), R.color.blue))
                tvArticles.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                recyclerViewSpecies.hide()
                recyclerViewArticles.show()
            }
        }
    }

    private fun getUser() {
        val friendsRef: DatabaseReference? = ref?.child("Users")
        friendsRef?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    for (dataSnap in snapshot.children) {
                        binding.tvName.text = dataSnap.child("fullName").value.toString()
                    }

                } catch (e: Exception) {

                }
            }

            override fun onCancelled(error: DatabaseError) {}
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
                    if (snapshot?.getString("heart") == "true") {
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
    private fun getListArticles() {
        if (listArticles.isNotEmpty()) {
            listArticles.clear()
            articlesAdapter.notifyDataSetChanged()
        }
        dbFireStore.collection("Articles").addSnapshotListener { documentSnapshots, error ->
            if (documentSnapshots != null) {
                for (snapshot in documentSnapshots) {
                    if (snapshot.getString("heart") == "true") {
                        listArticles.add(
                            Articles(
                                snapshot?.getString("title") ?: "",
                                snapshot?.getString("description") ?: "",
                                snapshot.getString("name") ?: "",
                                snapshot.getString("avatar") ?: "",
                                snapshot.getString("date") ?: "",
                                snapshot.getString("image") ?: "",
                                snapshot.getString("heart") ?: "false"
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
            binding.recyclerViewSpecies.layoutManager = LinearLayoutManager(requireContext())
            speciesAdapter.setData(listSpecies)
            binding.recyclerViewSpecies.adapter = speciesAdapter
            speciesAdapter.notifyDataSetChanged()

            binding.recyclerViewArticles.layoutManager = LinearLayoutManager(requireContext())
            //đổ data vào adapter
            articlesAdapter.setData(listArticles)
            //set adapter show lên màn hình người dùng
            binding.recyclerViewArticles.adapter = articlesAdapter
            articlesAdapter.notifyDataSetChanged()
        } catch (ex: Exception) { }
        binding.backgroundLoading.hide()
        binding.progressBar.hide()
    }
}