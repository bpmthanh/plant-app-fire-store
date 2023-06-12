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
import com.example.plantappfirestore.adapter.SpeciesAdapter
import com.example.plantappfirestore.adapter.SpeciesAdapter2
import com.example.plantappfirestore.callback.CallBack
import com.example.plantappfirestore.databinding.SpeciesFragmentBinding

class SpeciesFragment : Fragment() {
    private lateinit var binding: SpeciesFragmentBinding

    private var arrayAlphabet = ArrayList<String>()

    private var arrayAlphabet2 = ArrayList<String>()

    private lateinit var speciesAdapter: SpeciesAdapter
    private lateinit var speciesAdapter2: SpeciesAdapter2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = SpeciesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindComponent()
        bindData()
        bindEvent()
    }

    private fun bindComponent() {

    }

    private fun bindData() {
        arrayAlphabet = arrayListOf(
            "C",
            "CACTUS",
            "CISTUS",
            "CAESALPINIA",
            "CINNAMOMUM",
            "CIRSIUM",
            "CISSUS",
            "D",
            "DIERAMA",
            "DIGITALIS",
            "DAHLIA",
            "DAPHNE",
            "E",
            "ECHINACEA",
            "ECHINOPS"
        )

        arrayAlphabet2 = arrayListOf(
            "#",
            "A",
            "B",
            "C",
            "D",
            "E",
            "F",
            "G",
            "H",
            "I",
            "K",
            "L",
            "M",
            "N",
            "O",
            "P",
            "Q",
            "R",
            "S",
            "T",
            "U",
            "V",
            "W",
            "X",
            "Y"
        )

        onSetRecyclerView()
    }

    private fun bindEvent() {
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        speciesAdapter.alphabet(object : CallBack.Alphabet {
            override fun alphabet(alphabet: String) {
                val bundle = Bundle()
                bundle.putString("alphabet", alphabet)
                findNavController().navigate(R.id.action_species_to_speciesDetailFragment, bundle)
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onSetRecyclerView() {
        if (arrayAlphabet.isEmpty()) {
//            binding.tvEmpty.setVisibility(View.VISIBLE)
        } else {
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            speciesAdapter = SpeciesAdapter(arrayAlphabet)
            binding.recyclerView.adapter = speciesAdapter
            speciesAdapter.notifyDataSetChanged()


            binding.recyclerView2.layoutManager = LinearLayoutManager(requireContext())
            speciesAdapter2 = SpeciesAdapter2(arrayAlphabet2)
            binding.recyclerView2.adapter = speciesAdapter2
            speciesAdapter2.notifyDataSetChanged()
        }
    }
}