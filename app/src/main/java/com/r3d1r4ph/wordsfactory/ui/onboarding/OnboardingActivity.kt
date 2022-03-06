package com.r3d1r4ph.wordsfactory.ui.onboarding

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.r3d1r4ph.wordsfactory.R
import com.r3d1r4ph.wordsfactory.databinding.ActivityOnboardingBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class OnboardingActivity : AppCompatActivity() {

    private val viewBinding by viewBinding(ActivityOnboardingBinding::bind, R.id.rootLayout)
    private val viewModel by viewModels<OnboardingViewModel>()
    private val viewPagerAdapter: ViewPagerAdapter = ViewPagerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        configureViewPager()
        setOnClickListener()
        setCollector()
    }

    private fun configureViewPager() {
        val viewPager = viewBinding.onboardingViewPager
        viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(
            viewBinding.onboardingTabLayout,
            viewPager
        ) { _, _ -> }.attach()

        viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                nextButtonStateUpdate(position)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                val prevPosition = viewModel.uiState.value.currentIntro

                if (position < prevPosition) {
                    viewPager.setCurrentItem(prevPosition, false)
                } else if (position > prevPosition) {
                    nextIntro()
                }
            }
        })
    }

    private fun nextButtonStateUpdate(currentIntro: Int) = with(viewBinding.onboardingNextButton) {
        if (viewModel.isLastIntro(currentIntro)) {
            text = resources.getString(R.string.onboarding_button_lets_start)
            setOnClickListener { openSignUpScreen() }
        } else {
            text = resources.getString(R.string.onboarding_button_next)
            setOnClickListener { nextIntro() }
        }
    }

    private fun setOnClickListener() {
        viewBinding.onboardingSkipButton.setOnClickListener { openSignUpScreen() }
    }

    private fun setCollector() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    viewPagerAdapter.submitList(uiState.introList)
                    swipeToNextIntro(uiState.currentIntro)
                }
            }
        }
    }

    private fun openSignUpScreen() {
        Toast.makeText(this, "openSignUpScreen", Toast.LENGTH_SHORT).show()
    }

    private fun nextIntro() {
        viewModel.nextIntro()
    }

    private fun swipeToNextIntro(nextIntro: Int) {
        viewBinding.onboardingViewPager.setCurrentItem(nextIntro, true)
    }
}