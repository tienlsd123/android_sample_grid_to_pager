package com.example.movepagersample.adapter

import android.transition.TransitionSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.movepagersample.MainActivity
import com.example.movepagersample.R
import com.example.movepagersample.databinding.ImageCardBinding
import com.example.movepagersample.fragment.ImagePagerFragment
import java.util.concurrent.atomic.AtomicBoolean


class GridAdapter(private val fragment: Fragment) :
    ListAdapter<String, GridAdapter.GridViewHolder>(object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem
        override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
    }) {

    private val enterTransitionStarted = AtomicBoolean()

    inner class GridViewHolder(val binding: ImageCardBinding) : ViewHolder(binding.root) {
        fun onBind(position: Int) {
            setupImage(position)
            setupOnclickImage(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val layoutInflater = ImageCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GridViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        holder.onBind(position)
    }

    private fun GridViewHolder.setupImage(position: Int) {
        binding.cardImage.transitionName = "transitionName $position"
        Glide.with(fragment).load(getItem(position)).into(binding.cardImage)
        if (MainActivity.currentPosition != position) return
        if (enterTransitionStarted.getAndSet(true)) return
        fragment.startPostponedEnterTransition()
    }

    private fun GridViewHolder.setupOnclickImage(position: Int) {
        binding.cardImage.setOnClickListener { view ->
            MainActivity.currentPosition = position

            (fragment.exitTransition as TransitionSet).excludeTarget(view, true)
            val transitioningView: ImageView = view as ImageView
            fragment.parentFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .addSharedElement(transitioningView, transitioningView.transitionName)
                .replace(
                    R.id.fragment_container, ImagePagerFragment(), ImagePagerFragment::class.java
                        .simpleName
                )
                .addToBackStack(null)
                .commit()
        }
    }
}
