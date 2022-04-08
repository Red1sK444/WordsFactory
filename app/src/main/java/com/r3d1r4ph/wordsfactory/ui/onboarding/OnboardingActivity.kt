package com.r3d1r4ph.wordsfactory.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.r3d1r4ph.wordsfactory.R
import com.r3d1r4ph.wordsfactory.databinding.ActivityOnboardingBinding
import com.r3d1r4ph.wordsfactory.ui.menu.MenuActivity
import com.r3d1r4ph.wordsfactory.ui.signup.SignUpActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

    private fun initView() = with(viewBinding) {
        configureViewPager()
        onboardingSkipButton.setOnClickListener { openSignUpScreen() }
        onboardingNextButton.setOnClickListener {
            if (viewModel.getCurrentIntro() == IntroEnum.THIRD) {
                openSignUpScreen()
            } else {
                nextIntro()
            }
        }
    }

    private fun configureViewPager() {
        val viewPager = viewBinding.onboardingViewPager
        viewPager.adapter = viewPagerAdapter
        viewPagerAdapter.submitList(viewModel.introList)

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
        viewModel.uiState.observe(this) { uiState ->
            with(viewBinding) {
                if (uiState.openDictionaryScreen) {
                    openDictionaryScreen()
                }

                onboardingViewPager.setCurrentItem(uiState.currentIntro.ordinal, true)

                onboardingNextButton.text =
                    resources.getString(uiState.currentIntro.getButtonTextRes())
            }
        }
    }

    private fun openSignUpScreen() {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    private fun openDictionaryScreen() {
        startActivity(Intent(this, MenuActivity::class.java))
    }

    private fun nextIntro() {
        viewModel.toNextIntro()
    }
}