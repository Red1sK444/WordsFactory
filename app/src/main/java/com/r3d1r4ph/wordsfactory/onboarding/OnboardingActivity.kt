package com.r3d1r4ph.wordsfactory.onboarding

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.r3d1r4ph.wordsfactory.R
import com.r3d1r4ph.wordsfactory.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {

    private val viewBinding by viewBinding(ActivityOnboardingBinding::bind, R.id.rootLayout)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        val adapter = ViewPagerAdapter()
        viewBinding.viewPager.adapter = adapter
        TabLayoutMediator(
            viewBinding.tabLayout,
            viewBinding.viewPager
        ) { _, _ -> }.attach()

        viewBinding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewBinding.button.text = if (position + 1 == adapter.itemCount) {
                    "Let's Start"
                } else {
                    "Next"
                }
            }
        })
        viewBinding.button.setOnClickListener {
            if (viewBinding.viewPager.currentItem + 1 < adapter.itemCount) {
                viewBinding.viewPager.setCurrentItem(viewBinding.viewPager.currentItem + 1, true)
            } else {
                Toast.makeText(this, "agfagag", Toast.LENGTH_LONG).show()
            }
        }
    }
}