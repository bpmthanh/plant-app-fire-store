package com.example.plantappfirestore.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plantappfirestore.R
import com.example.plantappfirestore.adapter.HomePhotoAdapter
import com.example.plantappfirestore.adapter.HomePlantAdapter
import com.example.plantappfirestore.databinding.HomeFragmentBinding
import com.example.plantappfirestore.utils.Constant
import com.example.plantappfirestore.utils.Util
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.annotations.Nullable
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception


class HomeFragment : Fragment() {
    private lateinit var binding: HomeFragmentBinding
    private lateinit var dbFireStore: FirebaseFirestore
    private var imageUri: Uri = Uri.parse("")

    private var listPlant: MutableList<String> = ArrayList()
    private var listPhoto: MutableList<String> = ArrayList()

    private var planeHomePlantAdapter = HomePlantAdapter()
    private var photoHomePlantAdapter = HomePhotoAdapter()

    private var db: FirebaseDatabase? = null
    private var ref: DatabaseReference? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
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
        db = FirebaseDatabase.getInstance()
        ref = db?.reference

        if (Util.getPref(requireContext(), Constant.ADD, "false") == "true") {
            checkPermissionCamera()
        }
    }

    private fun bindData() {
        getUser()

        listPlant.apply {
            if (this.isEmpty()) {
                add("https://media.baothaibinh.com.vn/upload/news/3_2020/98126_top_50_anh_thien_nhien_2020_co_nhieu_canh_dep_cua_viet_nam_1583396532.jpg")
                add("https://znews-photo.zingcdn.me/w660/Uploaded/wyhktpu/2021_05_18/pexels_felix_mittermeier_957024.jpg")
                add("https://nhipsongvanphong.com/wp-content/uploads/2021/10/beautiful-green-tree-background-download-beautiful-green-tree-background-here-picture-16-IRTCFrt4E.jpg")
            }
        }
        listPhoto.apply {
            if (this.isEmpty()) {
                add("https://image.phunuonline.com.vn/fckeditor/upload/2020/20200227/images/truyen-ngan-nuoc-mat-_1582808061.jpg")
                add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTPD-6Iko9XidM52_NYSBjcl6a-dBib63eeyKo0bgsJXa48TJ0EKwLFoDk07FuvQ4cBdNU&usqp=CAU")
                add("https://dulichdalat.pro/wp-content/uploads/2020/05/Kombi-land-coffee.jpg")
            }
        }

        onSetRecyclerViePlant()
        onSetRecyclerViePhoto()
    }

    private fun bindEvent() {
        binding.cvSpecies.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_nav_species)
        }
        binding.cvArticles.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_nav_articles)
        }
        binding.cvCamera.setOnClickListener {
            checkPermissionCamera()
        }
    }

    private fun getUser() {
        val friendsRef: DatabaseReference? = ref?.child("Users")
        friendsRef?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    for (dataSnap in snapshot.children) {
                        binding.tvName.text = "Hello ${dataSnap.child("fullName").value.toString()}"
                    }

                } catch (e: Exception) {

                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun checkPermissionCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA), 1)
            } else {
                openCamera()
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                Toast.makeText(requireContext(), "Please provide required permission", Toast.LENGTH_SHORT).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun openCamera() {
        try {
            val values = ContentValues(1)
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            imageUri = requireContext().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)!!
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            startActivityForResult(intent, 2)
        } catch (ex: Exception) {}
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2) {
            val car: MutableMap<String, Any> = HashMap()
            val random = "${(0..20000).random()}"
            car["picture"] = imageUri
            car["keyPicture"] = random
            dbFireStore.collection("Camera").document(random).set(car)
            val bundle = Bundle()
            bundle.putString("keyPicture", random)
            findNavController().navigate(R.id.action_home_to_addingNewFragment, bundle)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onSetRecyclerViePlant() {
        binding.recyclerViewPlant.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        planeHomePlantAdapter.setData(listPlant)
        binding.recyclerViewPlant.adapter = planeHomePlantAdapter
        planeHomePlantAdapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onSetRecyclerViePhoto() {
        binding.recyclerViewPhoto.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        photoHomePlantAdapter.setData(listPhoto)
        binding.recyclerViewPhoto.adapter = photoHomePlantAdapter
        photoHomePlantAdapter.notifyDataSetChanged()
    }
}