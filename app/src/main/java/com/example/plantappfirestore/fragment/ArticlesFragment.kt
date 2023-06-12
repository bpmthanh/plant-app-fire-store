package com.example.plantappfirestore.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plantappfirestore.R
import com.example.plantappfirestore.adapter.ArticlesAdapter
import com.example.plantappfirestore.callback.CallBack
import com.example.plantappfirestore.databinding.ArticlesFragmentBinding
import com.example.plantappfirestore.model.Articles
import com.google.firebase.firestore.FirebaseFirestore

class ArticlesFragment : Fragment() {
    private lateinit var binding: ArticlesFragmentBinding
    private lateinit var dbFireStore: FirebaseFirestore
    private lateinit var articlesAdapter: ArticlesAdapter
    private var listArticles: ArrayList<Articles> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ArticlesFragmentBinding.inflate(inflater, container, false)
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
        articlesAdapter = ArticlesAdapter()
    }

    private fun bindData() {
        getListArticles()
    }

    private fun bindEvent() {
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        articlesAdapter.articlesDetail(object : CallBack.ArticlesDetail {
            override fun articlesDetail(
                title: String,
                description: String,
                name: String,
                avatar: String,
                date: String,
                image: String,
                heart: String
            ) {
                val bundle = Bundle()
                bundle.putString("title", title)
                bundle.putString("description", description)
                bundle.putString("name", name)
                bundle.putString("avatar", avatar)
                bundle.putString("date", date)
                bundle.putString("image", image)
                bundle.putString("heart", heart)
                findNavController().navigate(R.id.action_articlesFragment_to_articlesProfileFragment, bundle)
            }

        })
    }

    override fun onResume() {
        super.onResume()
//        getListArticles()
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
            onSetRecyclerView()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onSetRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        //đổ data vào adapter
        articlesAdapter.setData(listArticles)
//        //set adapter show lên màn hình người dùng
        binding.recyclerView.adapter = articlesAdapter
        articlesAdapter.notifyDataSetChanged()
    }
}