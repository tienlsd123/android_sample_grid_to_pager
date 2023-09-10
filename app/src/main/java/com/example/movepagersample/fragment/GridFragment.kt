package com.example.movepagersample.fragment

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import android.view.View.OnLayoutChangeListener
import androidx.core.app.SharedElementCallback
import androidx.recyclerview.widget.RecyclerView
import com.example.movepagersample.MainActivity
import com.example.movepagersample.R
import com.example.movepagersample.adapter.GridAdapter
import com.example.movepagersample.base.BaseFragment
import com.example.movepagersample.databinding.FragmentGridBinding
import com.example.movepagersample.util.Constants

class GridFragment : BaseFragment<FragmentGridBinding>(FragmentGridBinding::inflate) {

    override fun setupOnViewCreated(savedInstanceState: Bundle?) {
        val adapter = GridAdapter(this)
        binding.recyclerView.adapter = adapter
        (binding.recyclerView.adapter as GridAdapter).submitList(Constants.SAMPLE_IMAGE)
        prepareTransitions()
        postponeEnterTransition()
        scrollToPosition()
    }

    private fun prepareTransitions() {
        exitTransition = TransitionInflater.from(context).inflateTransition(R.transition.grid_exit_transition)
        setExitSharedElementCallback(object : SharedElementCallback() {
            override fun onMapSharedElements(names: List<String>, sharedElements: MutableMap<String, View>) {
                val selectedViewHolder: RecyclerView.ViewHolder = binding.recyclerView
                    .findViewHolderForAdapterPosition(MainActivity.currentPosition) ?: return
                sharedElements[names[0]] = selectedViewHolder.itemView.findViewById(R.id.card_image)
            }
        })
    }

    private fun scrollToPosition() {
        val recyclerView = binding.recyclerView
        recyclerView.addOnLayoutChangeListener(object : OnLayoutChangeListener {
            override fun onLayoutChange(
                v: View,
                left: Int,
                top: Int,
                right: Int,
                bottom: Int,
                oldLeft: Int,
                oldTop: Int,
                oldRight: Int,
                oldBottom: Int
            ) {
                recyclerView.removeOnLayoutChangeListener(this)
                val layoutManager = recyclerView.layoutManager ?: return
                val viewAtPosition = layoutManager.findViewByPosition(MainActivity.currentPosition)
                if (viewAtPosition == null || layoutManager.isViewPartiallyVisible(viewAtPosition, false, true)) {
                    recyclerView.post { layoutManager.scrollToPosition(MainActivity.currentPosition) }
                }
            }
        })
    }

}