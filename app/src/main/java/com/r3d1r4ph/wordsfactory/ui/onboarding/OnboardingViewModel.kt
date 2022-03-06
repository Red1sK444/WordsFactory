package com.r3d1r4ph.wordsfactory.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.r3d1r4ph.wordsfactory.data.intro.IntroDataSourceImpl
import com.r3d1r4ph.wordsfactory.data.intro.IntroItem
import com.r3d1r4ph.wordsfactory.data.intro.IntroRepository
import com.r3d1r4ph.wordsfactory.data.intro.IntroRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class OnboardingUiState(
    val introList: List<IntroItem>,
    val currentIntro: Int
)

class OnboardingViewModel : ViewModel() {

    private val introRepository: IntroRepository = IntroRepositoryImpl(IntroDataSourceImpl())

    private val _uiState =
        MutableStateFlow(
            OnboardingUiState(
                introList = introRepository.getIntroList(),
                currentIntro = introRepository.getInitIntro()
            )
        )
    val uiState: StateFlow<OnboardingUiState>
        get() = _uiState.asStateFlow()

    fun isLastIntro(currentIntro: Int): Boolean = introRepository.isLastIntro(currentIntro)

    fun updateCurrentIntroByGesture(intro: Int) {
        if (intro < uiState.value.currentIntro) {
            changeIntro(false)
        } else if (intro > uiState.value.currentIntro) {
            changeIntro(true)
        }
    }

    fun changeIntro(toNext: Boolean) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    currentIntro = if (toNext) introRepository.getNextIntro(_uiState.value.currentIntro)
                    else introRepository.getPrevIntro(_uiState.value.currentIntro)
                )
            }
        }
    }
}