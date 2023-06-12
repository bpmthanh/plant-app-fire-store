package com.example.plantappfirestore

import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.core.view.WindowCompat
import androidx.viewpager.widget.ViewPager
import com.example.plantappfirestore.adapter.IntroduceProfileViewPagerADP
import com.example.plantappfirestore.databinding.ActivityIntroProfileBinding
import com.example.plantappfirestore.utils.Constant
import com.example.plantappfirestore.utils.Util


class IntroProfileActivity : BaseActivity() {
    private lateinit var binding: ActivityIntroProfileBinding
    private val mViewPagerAdapter by lazy { IntroduceProfileViewPagerADP(supportFragmentManager) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setLightStatusBars(true)
        initAdp()

        binding.btnNext.setOnClickListener {
            if (binding.profileViewpager.currentItem < mViewPagerAdapter.count - 1) {
                binding.profileViewpager.currentItem = binding.profileViewpager.currentItem + 1
            } else {
                val intent = Intent(applicationContext, LogInActivity::class.java)
                startActivity(intent)
                Util.setPref(this, Constant.IS_FIRST_TIME_INTRO,"true")
            }
        }
    }

    private fun Window.setLightStatusBars(b: Boolean) {
        WindowCompat.getInsetsController(this, decorView)?.isAppearanceLightStatusBars = b
    }

    private fun initAdp(){
        binding.profileViewpager.offscreenPageLimit = mViewPagerAdapter.count
        binding.profileViewpager.adapter = mViewPagerAdapter
        binding.profileViewpager.currentItem = 0

        binding.indicator.attachToPager(binding.profileViewpager)

        binding.profileViewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                if(position == 2){
                    binding.btnNext.text = getString(R.string.sign_in)
                } else {
                    binding.btnNext.text = getString(R.string.next)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
    }
}