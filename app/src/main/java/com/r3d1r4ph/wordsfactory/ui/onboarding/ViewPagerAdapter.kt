package com.r3d1r4ph.wordsfactory.ui.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.r3d1r4ph.wordsfactory.R
import com.r3d1r4ph.wordsfactory.data.intro.IntroItem
import com.r3d1r4ph.wordsfactory.databinding.ItemIntroViewPagerBinding

class ViewPagerAdapter : ListAdapter<IntroItem, ViewPagerAdapter.PagerViewHolder>(DIFF) {

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<IntroItem>() {
            override fun areItemsTheSame(oldItem: IntroItem, newItem: IntroItem) =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: IntroItem, newItem: IntroItem) =
                oldItem == newItem

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder =
        PagerViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_intro_view_pager, parent, false)
        )

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemIntroViewPagerBinding.bind(view)

        fun bind(item: IntroItem) {
            binding.itemViewPagerImageView.setImageResource(item.image)
            binding.itemViewPagerTitleTextView.setText(item.title)
        }
    }
}