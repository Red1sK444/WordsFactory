package com.r3d1r4ph.wordsfactory.ui.menu.dictionary

import android.annotation.SuppressLint
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.r3d1r4ph.wordsfactory.R
import com.r3d1r4ph.wordsfactory.databinding.FragmentDictionaryBinding
import com.r3d1r4ph.wordsfactory.domain.models.Dictionary
import com.r3d1r4ph.wordsfactory.ui.menu.dictionary.recycler.MeaningItemDecoration
import com.r3d1r4ph.wordsfactory.ui.menu.dictionary.recycler.MeaningsAdapter
import com.r3d1r4ph.wordsfactory.ui.utils.ExceptionHolder
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.util.*

@AndroidEntryPoint
class DictionaryFragment : Fragment(R.layout.fragment_dictionary) {

    private val viewBinding by viewBinding(FragmentDictionaryBinding::bind)
    private val viewModel by viewModels<DictionaryViewModel>()
    private val meaningsAdapter = MeaningsAdapter()
    private val mediaPlayer = MediaPlayer()

    companion object {
        val TAG: String = DictionaryFragment::class.java.simpleName
        fun newInstance() = DictionaryFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        configureRecycler()

        with(viewBinding) {
            dictionarySearchTextInputEditText.addTextChangedListener {
                viewModel.dismissValidationError()
            }

            dictionarySearchTextInputLayout.setEndIconOnClickListener {
                viewModel.search(dictionarySearchTextInputEditText.text.toString())
            }

            dictionaryAddButton.setOnClickListener {
                viewModel.addToSaved()
            }

            dictionaryVolumeImageButton.setOnClickListener {
                mediaPlayer.start()
            }
        }

        mediaPlayer.setAudioAttributes(
            AudioAttributes
                .Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
        )

        setObservers()
    }

    private fun configureRecycler() {
        viewBinding.dictionaryRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = meaningsAdapter
            addItemDecoration(MeaningItemDecoration())
        }
    }

    private fun setObservers() = with(viewModel) {
        uiState.observe(viewLifecycleOwner) { uiState ->

            //searchError Handling
            with(viewBinding.dictionarySearchTextInputLayout) {
                error = resources.getString(uiState.searchError ?: R.string.empty)
                isErrorEnabled = uiState.searchError != null
                if (isErrorEnabled) {
                    return@observe
                }
            }

            with(viewBinding) {

                //loading Handling
                dictionarySearchTextInputLayout.isEndIconVisible = !uiState.isLoading
                dictionaryAddButton.isEnabled = !uiState.isLoading

                //Word state handling
                when (uiState.wordUiState) {
                    is WordUiState.Success -> {
                        dictionaryMatchWordGroup.isVisible = true
                        dictionaryNoWordGroup.isVisible = false

                        dictionaryAddButton.isVisible = !uiState.wordUiState.isWordSaved

                        uiState.wordUiState.dictionary.let(::fillWithWordInfo)
                    }
                    is WordUiState.NoWord -> {
                        dictionaryMatchWordGroup.isVisible = false
                        dictionaryNoWordGroup.isVisible = true
                    }
                }


            }
        }

        uiAction.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { action ->
                if (action is DictionaryAction.Error) {
                    val errorMessage = when (val holder = action.exceptionHolder) {
                        is ExceptionHolder.Server -> holder.message
                        is ExceptionHolder.Resource -> getString(holder.messageId)
                    }

                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun fillWithWordInfo(dictionary: Dictionary) = with(viewBinding) {
        dictionaryWordTextView.text =
            "${dictionary.word.first().uppercase(Locale.ENGLISH)}${dictionary.word.substring(1)}"
        dictionaryTranscriptionTextView.text = dictionary.phonetic

        dictionarySpeechPartTextView.text = "${
            dictionary.partOfSpeech.first().uppercase(Locale.ENGLISH)
        }${dictionary.partOfSpeech.substring(1)}"

        meaningsAdapter.submitList(dictionary.meanings)

        mediaPlayer.reset()
        if (dictionary.audio.isNotEmpty()) {
            dictionaryVolumeImageButton.isVisible = true

            try {
                mediaPlayer.setDataSource(dictionary.audio)
                mediaPlayer.prepareAsync()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            dictionaryVolumeImageButton.isVisible = false
        }
    }
}