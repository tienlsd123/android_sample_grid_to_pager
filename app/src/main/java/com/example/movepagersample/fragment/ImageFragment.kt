package com.example.movepagersample.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.movepagersample.MainActivity
import com.example.movepagersample.base.BaseFragment
import com.example.movepagersample.databinding.FragmentImageBinding
import com.example.movepagersample.util.Constants

class ImageFragment : BaseFragment<FragmentImageBinding>(FragmentImageBinding::inflate) {

    private lateinit var url: String
    private var position = 0

    override fun setupOnViewCreated(savedInstanceState: Bundle?) {
        val arguments = arguments
        @DrawableRes val imageRes = arguments!!.getInt(MainActivity.CURRENT_POSITION)
        binding.image.transitionName = "transitionName $position"
        Glide.with(this)
            .load(Constants.SAMPLE_IMAGE[imageRes])
            .into(binding.image)
        requireParentFragment().startPostponedEnterTransition()
    }

    companion object {
        fun newInstance(url: String, position: Int): ImageFragment {
            val argument = Bundle()
            argument.putInt(MainActivity.CURRENT_POSITION, position)
            return ImageFragment().apply {
                this.url = url
                this.arguments = argument
                this.position = position
            }
        }
    }
}