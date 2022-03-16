package com.r3d1r4ph.wordsfactory.ui.menu.dictionary

import android.annotation.SuppressLint
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.r3d1r4ph.wordsfactory.R
import com.r3d1r4ph.wordsfactory.databinding.FragmentDictionaryBinding
import java.io.IOException
import java.util.*

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
        setObserver()
    }

    private fun initView() {
        configureRecycler()

        viewBinding.dictionarySearchTextInputLayout.setEndIconOnClickListener {
            viewModel.search(viewBinding.dictionarySearchTextInputEditText.text.toString())
        }

        viewBinding.dictionaryVolumeImageButton.setOnClickListener {
            mediaPlayer.start()
        }

        mediaPlayer.setAudioAttributes(
            AudioAttributes
                .Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
        )
    }

    private fun configureRecycler() {
        viewBinding.dictionaryRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = meaningsAdapter
            addItemDecoration(MeaningItemDecoration())
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setObserver() {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            with(viewBinding) {
                if (uiState.noWord) {
                    dictionaryMatchWordGroup.visibility = View.GONE
                    dictionaryNoWordGroup.visibility = View.VISIBLE
                    return@observe
                }

                dictionaryMatchWordGroup.visibility = View.VISIBLE
                dictionaryNoWordGroup.visibility = View.GONE

                uiState.dictionary?.let {
                    dictionaryWordTextView.text =
                        "${it.word.first().uppercase(Locale.ENGLISH)}${it.word.substring(1)}"
                    dictionaryTranscriptionTextView.text = it.phonetic
                    dictionarySpeechPartTextView.text = "${
                        it.partOfSpeech.first().uppercase(Locale.ENGLISH)
                    }${it.partOfSpeech.substring(1)}"
                    meaningsAdapter.submitList(it.meanings)

                    try {
                        mediaPlayer.reset()
                        mediaPlayer.setDataSource(it.audio)
                        mediaPlayer.prepareAsync()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}