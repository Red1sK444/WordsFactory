package com.r3d1r4ph.wordsfactory.data.intro

interface IntroDataSource {
    fun getIntroList(): List<IntroItem>
    fun getNextIntro(currentIntro: Int): Int
    fun getPrevIntro(currentIntro: Int): Int
    fun isLastIntro(currentIntro: Int): Boolean
    fun getInitIntro(): Int
}