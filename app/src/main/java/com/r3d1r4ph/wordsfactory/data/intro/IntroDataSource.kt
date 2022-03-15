package com.r3d1r4ph.wordsfactory.data.intro

interface IntroDataSource {
    fun getIntroList(): List<IntroItem>
    fun toNextIntro(): Int
    fun toPreviousIntro(): Int
    fun isLastIntro(): Boolean
    fun getCurrentIntro(): Int
}