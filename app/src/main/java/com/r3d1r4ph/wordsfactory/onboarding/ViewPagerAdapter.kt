package com.r3d1r4ph.wordsfactory.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.r3d1r4ph.wordsfactory.R
import com.r3d1r4ph.wordsfactory.databinding.ItemOnboardingViewPagerBinding

class ViewPagerAdapter : RecyclerView.Adapter<ViewPagerAdapter.PagerViewHolder>() {

    private val colors = intArrayOf(
        android.R.color.black,
        android.R.color.holo_red_light,
        android.R.color.holo_blue_dark
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder =
        PagerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_onboarding_view_pager, parent, false)
        )

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = colors.size

    inner class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemOnboardingViewPagerBinding.bind(view)

        fun bind(position: Int) {
            binding.itemViewPagerImageView.setImageResource(R.drawable.img_splash_screen)
            binding.root.setBackgroundResource(colors[position])
        }
    }
}