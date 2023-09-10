package com.example.movepagersample.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.movepagersample.fragment.ImageFragment
import com.example.movepagersample.util.Constants

class ImagePagerAdapter(fragment: Fragment) :
    FragmentStatePagerAdapter(fragment.childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount() = Constants.SAMPLE_IMAGE.size

    override fun getItem(position: Int): Fragment {
        return ImageFragment.newInstance(Constants.SAMPLE_IMAGE[position], position)
    }
}