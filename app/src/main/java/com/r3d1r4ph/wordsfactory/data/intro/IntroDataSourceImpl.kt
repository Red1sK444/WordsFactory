package com.r3d1r4ph.wordsfactory.data.intro

import com.r3d1r4ph.wordsfactory.R

class IntroDataSourceImpl : IntroDataSource {

    private val introList = listOf(
        IntroItem(
            image = R.drawable.img_onboarding_1,
            title = R.string.onboarding_intro_1_title
        ),
        IntroItem(
            image = R.drawable.img_onboarding_2,
            title = R.string.onboarding_intro_2_title
        ),
        IntroItem(
            image = R.drawable.img_onboarding_3,
            title = R.string.onboarding_intro_3_title
        )
    )

    override fun getIntroList() = introList

    override fun getNextIntro(currentIntro: Int): Int =
        if (currentIntro == introList.lastIndex) currentIntro else currentIntro + 1

    override fun getPrevIntro(currentIntro: Int): Int =
        if (currentIntro == 0) currentIntro else currentIntro - 1

    override fun isLastIntro(currentIntro: Int): Boolean = currentIntro == introList.lastIndex

    override fun getInitIntro(): Int = 0
}