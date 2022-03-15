package com.r3d1r4ph.wordsfactory.ui.onboarding

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.r3d1r4ph.wordsfactory.R
import com.r3d1r4ph.wordsfactory.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {

    private val viewBinding by viewBinding(ActivityOnboardingBinding::bind, R.id.rootLayout)
    private val viewModel by viewModels<OnboardingViewModel>()
    private val viewPagerAdapter: ViewPagerAdapter = ViewPagerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        initView()
        setObservers()
    }

    private fun initView() {
        configureViewPager()
        viewModel.loadIntroList()
        viewBinding.onboardingSkipButton.setOnClickListener { openSignUpScreen() }
    }

    private fun configureViewPager() {
        val viewPager = viewBinding.onboardingViewPager
        viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(
            viewBinding.onboardingTabLayout,
            viewPager
        ) { _, _ -> }.attach()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.updateCurrentIntroByGesture(position)
            }
        })
    }

    private fun setObservers() {
        viewModel.introList.observe(this) { list ->
            viewPagerAdapter.submitList(list)
        }

        viewModel.uiState.observe(this) { uiState ->
            swipeToIntro(uiState.currentIntro)

            with(viewBinding.onboardingNextButton) {
                if (uiState.isLastIntro) {
                    text = resources.getString(R.string.onboarding_button_lets_start)
                    setOnClickListener { openSignUpScreen() }
                } else {
                    text = resources.getString(R.string.onboarding_button_next)
                    setOnClickListener { nextIntro() }
                }
            }
        }
    }

    private fun openSignUpScreen() {
        Toast.makeText(this, "openSignUpScreen", Toast.LENGTH_SHORT).show()
        //TODO openSignUpScreen
    }

    private fun nextIntro() {
        viewModel.changeIntro(toNext = true)
    }

    private fun swipeToIntro(intro: Int) {
        viewBinding.onboardingViewPager.setCurrentItem(intro, true)
    }
}