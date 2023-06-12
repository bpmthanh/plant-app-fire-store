package com.example.plantappfirestore.fragment

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plantappfirestore.adapter.PictureAdapter
import com.example.plantappfirestore.databinding.AddingNewFragmentBinding
import com.example.plantappfirestore.utils.Constant
import com.example.plantappfirestore.utils.Util
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception

class AddingNewFragment : Fragment() {
    private lateinit var binding: AddingNewFragmentBinding
    private lateinit var dbFireStore: FirebaseFirestore
    private lateinit var pictureAdapter: PictureAdapter
    private var listPicture: ArrayList<String> = ArrayList()
    private var strKeyPicture = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = AddingNewFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindComponent()
        bindData()
        bindEvent()
    }

    private fun bindComponent() {
        Util.setPref(requireContext(), Constant.ADD, "false")
        dbFireStore = FirebaseFirestore.getInstance()
        strKeyPicture = arguments?.getString("keyPicture") ?: ""
        pictureAdapter = PictureAdapter()
    }

    private fun bindData() {
        getPictureNear()
        getPicture()
    }

    private fun bindEvent() {
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getPictureNear() {
        dbFireStore.collection("Camera").addSnapshotListener { documentSnapshots, error ->
            if (documentSnapshots != null) {
                for (snapshot in documentSnapshots) {
                    if (snapshot?.getString("keyPicture") == strKeyPicture) {
                        binding.background.setImageURI(Uri.parse(snapshot.getString("picture")))
                    }
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getPicture() {
        dbFireStore.collection("Camera").addSnapshotListener { documentSnapshots, error ->
            if (documentSnapshots != null) {
                for (snapshot in documentSnapshots) {
                    listPicture.add(snapshot?.getString("picture") ?: "")
                }
                onSetRecyclerView()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onSetRecyclerView() {
        try {
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            pictureAdapter.setData(listPicture)
            binding.recyclerView.adapter = pictureAdapter
            pictureAdapter.notifyDataSetChanged()
        } catch (ex: Exception) { }
    }
}