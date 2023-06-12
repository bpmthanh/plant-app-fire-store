package com.example.plantappfirestore

import android.content.Intent
import android.os.Bundle
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.plantappfirestore.databinding.ActivityMainBinding
import com.example.plantappfirestore.fragment.AddFragment
import com.example.plantappfirestore.utils.Constant
import com.example.plantappfirestore.utils.Util
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dbFireStore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindComponent()
        bindData()
    }

    private fun bindComponent() {
        dbFireStore = FirebaseFirestore.getInstance()

        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(1).isEnabled = false

        val navController = findNavController(this@MainActivity, R.id.nav_host_fragment)
        setupWithNavController(binding.bottomNavigationView, navController)

        binding.cvAdd.setOnClickListener {
            Util.setPref(applicationContext, Constant.ADD, "true")
            navController.navigate(R.id.home)
        }
    }

    private fun bindData() {
        if (Util.getPref(applicationContext, Constant.DATA_FIRST, "false") == " false") {
            setDataSpecies()
            setDataArticles()
            Util.setPref(applicationContext, Constant.DATA_FIRST, "true")
        }
    }

    private fun setDataArticles() {
        val car1: MutableMap<String, Any> = HashMap()
        car1["title"] = "David Austin, Who Breathed Life Into the Rose, Is Dead at 92"
        car1["description"] = "Public parks aside, getting a dose of nature can ure should and can fit into that city getaway, according to Kally Ellis, the founder of the London florist company McQueens and the in-house florist for the Maybourne Hotel Group. “Connecting with ote to jet lag and travel tiredness,” she said. “Plants and flowers can refresh us, boost our energy and help us recalibrate.”"
        car1["name"] = "Shivani Vora"
        car1["avatar"] = "https://media.maybe.vn/attachments/13avatar-fx-1-3a70-videosixteenbynine3000-jpg.55258/"
        car1["date"] = "201900000010000001"
        car1["image"] = "https://suckhoedoisong.qltns.mediacdn.vn/324455921873985536/2022/2/8/9-cong-dung-va-han-che-cua-ca-chua-doi-voi-cuoc-song-hang-ngay-2021031420263300541-1644333027055216472883.jpg"
        car1["heart"] = "false"
        dbFireStore.collection("Articles").document("Shivani Vora").set(car1)

        val car2: MutableMap<String, Any> = HashMap()
        car2["title"] = "Even on Urban Excursions, Finding Mother Nature's Charms"
        car2["description"] = "Public parks aside, getting a dose of nature can ure should and can fit into that city getaway, according to Kally Ellis, the founder of the London florist company McQueens and the in-house florist for the Maybourne Hotel Group. “Connecting with ote to jet lag and travel tiredness,” she said. “Plants and flowers can refresh us, boost our energy and help us recalibrate.”"
        car2["name"] = "Shylla Monic"
        car2["avatar"] = "https://www.fao.org.vn/wp-content/uploads/2022/12/hinh-anh-ca-canh-cuc-dep.jpg"
        car2["date"] = "201900000090000019"
        car2["image"] = "https://allimages.sgp1.digitaloceanspaces.com/tipeduvn/2022/08/1660483140_363_Hinh-Nen-Ca-Vang-Dien-Thoai-Dep-Nhat.jpg"
        car2["heart"] = "false"
        dbFireStore.collection("Articles").document("Shylla Monic").set(car2)
    }

    private fun setDataSpecies() {
        val car1: MutableMap<String, Any> = HashMap()
        car1["category"] = "CACTUS"
        car1["title"] = "Red Cactus"
        car1["description"] = "The word cactus derives, through Latin, from the Ancient Greek κάκτος, kaktos, a name originally used by Theophrastus for a spiny plant whose identity is not certain. Cacti occur in a wide range of shapes and sizes. Most cacti live in habitats subject to at least some drought. "
        car1["kingdom"] = "Plantae"
        car1["family"] = "Cactaceae"
        car1["star"] = "4.1"
        car1["image"] = "https://live.staticflickr.com/65535/51210582507_eef41ee748_b.jpg"
        car1["heart"] = "false"
        dbFireStore.collection("Species").document("Red Cactus").set(car1)

        val car2: MutableMap<String, Any> = HashMap()
        car2["category"] = "CACTUS"
        car2["title"] = "Fat Cactus"
        car2["description"] = "The word cactus derives, through Latin, from the Ancient Greek κάκτος, kaktos, a name originally used by Theophrastus for a spiny plant whose identity is not certain. Cacti occur in a wide range of shapes and sizes. Most cacti live in habitats subject to at least some drought. "
        car2["kingdom"] = "Plantae"
        car2["family"] = "Cactaceae"
        car2["star"] = "5.0"
        car2["image"] = "https://thumbs.dreamstime.com/b/fat-cactus-taken-singapore-45303707.jpg"
        car2["heart"] = "false"
        dbFireStore.collection("Species").document("Fat Cactus").set(car2)

        val car3: MutableMap<String, Any> = HashMap()
        car3["category"] = "CACTUS"
        car3["title"] = "Circle Cactus"
        car3["description"] = "The word cactus derives, through Latin, from the Ancient Greek κάκτος, kaktos, a name originally used by Theophrastus for a spiny plant whose identity is not certain. Cacti occur in a wide range of shapes and sizes. Most cacti live in habitats subject to at least some drought. "
        car3["kingdom"] = "Plantae"
        car3["family"] = "Cactaceae"
        car3["star"] = "5.0"
        car3["image"] = "https://upload.wikimedia.org/wikipedia/commons/0/0d/Echinocactus_grusonii_1.jpg"
        car3["heart"] = "false"
        dbFireStore.collection("Species").document("Circle Cactus").set(car3)

        val car4: MutableMap<String, Any> = HashMap()
        car4["category"] = "CACTUS"
        car4["title"] = "Rice Cactus"
        car4["description"] = "The word cactus derives, through Latin, from the Ancient Greek κάκτος, kaktos, a name originally used by Theophrastus for a spiny plant whose identity is not certain. Cacti occur in a wide range of shapes and sizes. Most cacti live in habitats subject to at least some drought. "
        car4["kingdom"] = "Plantae"
        car4["family"] = "Cactaceae"
        car4["star"] = "5.0"
        car4["image"] = "https://s3g2u3k4.rocketcdn.me/wp-content/uploads/sites/4/2022/09/rhipsalis.jpg"
        car4["heart"] = "false"
        dbFireStore.collection("Species").document("Rice Cactus").set(car4)
    }
}