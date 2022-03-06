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
        viewBinding.onboardingViewPager.adapter = adapter
        TabLayoutMediator(
            viewBinding.onboardingTabLayout,
            viewBinding.onboardingViewPager
        ) { _, _ -> }.attach()

        viewBinding.onboardingViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewBinding.onboardingNextButton.text = if (position + 1 == adapter.itemCount) {
                    "Let's Start"
                } else {
                    "Next"
                }
            }
        })
        viewBinding.onboardingNextButton.setOnClickListener {
            if (viewBinding.onboardingViewPager.currentItem + 1 < adapter.itemCount) {
                viewBinding.onboardingViewPager.setCurrentItem(viewBinding.onboardingViewPager.currentItem + 1, true)
            } else {
                Toast.makeText(this, "agfagag", Toast.LENGTH_LONG).show()
            }
        }
    }
}