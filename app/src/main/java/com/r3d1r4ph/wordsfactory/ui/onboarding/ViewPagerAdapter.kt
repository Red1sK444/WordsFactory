package com.r3d1r4ph.wordsfactory.ui.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.r3d1r4ph.wordsfactory.R
import com.r3d1r4ph.wordsfactory.databinding.ItemIntroViewPagerBinding
import com.r3d1r4ph.wordsfactory.domain.IntroItem

class ViewPagerAdapter : ListAdapter<IntroEnum, ViewPagerAdapter.PagerViewHolder>(DIFF) {

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<IntroEnum>() {
            override fun areItemsTheSame(oldItem: IntroEnum, newItem: IntroEnum) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: IntroEnum, newItem: IntroEnum) =
                oldItem == newItem

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder =
        PagerViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_intro_view_pager, parent, false)
        )

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(getItem(position).getIntroItem())
    }

    inner class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemIntroViewPagerBinding.bind(view)

        fun bind(item: IntroItem) = with(binding) {
            itemViewPagerImageView.setImageResource(item.image)
            itemViewPagerTitleTextView.setText(item.title)
        }
    }
}