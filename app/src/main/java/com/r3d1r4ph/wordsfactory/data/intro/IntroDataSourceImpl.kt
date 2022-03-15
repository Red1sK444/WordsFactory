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

    private var currentIntro: Int = 0

    override fun getCurrentIntro() = currentIntro

    override fun toNextIntro(): Int {
        if (currentIntro < introList.lastIndex) {
            currentIntro += 1
        }
        return getCurrentIntro()
    }

    override fun toPreviousIntro(): Int {
        if (currentIntro > 0) {
            currentIntro -= 1
        }
        return getCurrentIntro()
    }

    override fun getIntroList() = introList

    override fun isLastIntro(): Boolean = currentIntro == introList.lastIndex
}