package com.r3d1r4ph.wordsfactory.data.intro

class IntroRepositoryImpl(private val introDataSource: IntroDataSource) : IntroRepository {

    override fun getIntroList() = introDataSource.getIntroList()

    override fun getNextIntro(currentIntro: Int): Int = introDataSource.getNextIntro(currentIntro)

    override fun isLastIntro(currentIntro: Int): Boolean = introDataSource.isLastIntro(currentIntro)

    override fun getInitIntro(): Int = introDataSource.getInitIntro()
}