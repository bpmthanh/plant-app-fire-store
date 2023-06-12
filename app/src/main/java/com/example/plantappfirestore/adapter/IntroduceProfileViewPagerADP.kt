package com.example.plantappfirestore.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.plantappfirestore.fragment.OneIntroduceFragment
import com.example.plantappfirestore.fragment.ThreeIntroduceFragment
import com.example.plantappfirestore.fragment.TwoIntroduceFragment

class IntroduceProfileViewPagerADP(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        val tp: Fragment = when (position) {
            0 -> {
                OneIntroduceFragment()
            }
            1 -> {
                TwoIntroduceFragment()
            }
            2 -> {
                ThreeIntroduceFragment()
            }

            else -> {
                OneIntroduceFragment()
            }
        }
        return tp
    }

    override fun getCount(): Int {
        return 3
    }
}
