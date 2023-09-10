package com.example.movepagersample.fragment

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.core.app.SharedElementCallback
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener
import com.example.movepagersample.MainActivity
import com.example.movepagersample.R
import com.example.movepagersample.adapter.ImagePagerAdapter
import com.example.movepagersample.base.BaseFragment
import com.example.movepagersample.databinding.FragmentPagerBinding

class ImagePagerFragment : BaseFragment<FragmentPagerBinding>(FragmentPagerBinding::inflate) {

    override fun setupOnViewCreated(savedInstanceState: Bundle?) {
        with(binding.viewPager) {
            adapter = ImagePagerAdapter(this@ImagePagerFragment)
            currentItem = MainActivity.currentPosition
            addOnPageChangeListener(object : SimpleOnPageChangeListener() {
                override fun onPageSelected(position: Int) {
                    MainActivity.currentPosition = position
                }
            })
        }
        prepareSharedElementTransition()
        if (savedInstanceState == null) {
            postponeEnterTransition()
        }
    }

    private fun prepareSharedElementTransition() {
        val transition = TransitionInflater.from(context).inflateTransition(R.transition.image_shared_element_transition)
        sharedElementEnterTransition = transition
        setEnterSharedElementCallback(object : SharedElementCallback() {
            override fun onMapSharedElements(names: MutableList<String>?, sharedElements: MutableMap<String, View>?) {
                super.onMapSharedElements(names, sharedElements)
                val fragment = binding.viewPager.adapter!!.instantiateItem(
                    binding.viewPager,
                    MainActivity.currentPosition
                ) as Fragment
                val view = fragment.view ?: return
                if (sharedElements.isNullOrEmpty() || names.isNullOrEmpty()) return
                sharedElements[names[0]] = view.findViewById(R.id.image)
            }
        })
    }

}