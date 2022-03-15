package com.r3d1r4ph.wordsfactory.ui.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.r3d1r4ph.wordsfactory.data.intro.IntroDataSource
import com.r3d1r4ph.wordsfactory.data.intro.IntroItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val introDataSource: IntroDataSource
) : ViewModel() {

    private val _uiState =
        MutableLiveData(
            OnboardingUiState(
                currentIntro = introDataSource.getCurrentIntro(),
                isLastIntro = introDataSource.isLastIntro()
            )
        )
    val uiState: LiveData<OnboardingUiState>
        get() = _uiState

    private val _introList = MutableLiveData<List<IntroItem>>()
    val introList: LiveData<List<IntroItem>>
        get() = _introList

    fun loadIntroList() {
        viewModelScope.launch {
            _introList.value = introDataSource.getIntroList()
        }
    }

    fun updateCurrentIntroByGesture(intro: Int) {
        uiState.value?.let {
            if (intro < it.currentIntro) {
                changeIntro(false)
            } else if (intro > it.currentIntro) {
                changeIntro(true)
            }
        }
    }

    fun changeIntro(toNext: Boolean) {
        viewModelScope.launch {
            _uiState.value = OnboardingUiState(
                currentIntro = if (toNext) {
                    introDataSource.toNextIntro()
                } else {
                    introDataSource.toPreviousIntro()
                },
                isLastIntro = introDataSource.isLastIntro()
            )
        }
    }
}