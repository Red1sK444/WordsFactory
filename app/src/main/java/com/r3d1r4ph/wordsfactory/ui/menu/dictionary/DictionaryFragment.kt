package com.r3d1r4ph.wordsfactory.ui.menu.dictionary

import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.r3d1r4ph.wordsfactory.R
import com.r3d1r4ph.wordsfactory.databinding.FragmentDictionaryBinding

class DictionaryFragment : Fragment(R.layout.fragment_dictionary) {

    private val viewBinding by viewBinding(FragmentDictionaryBinding::bind)

    companion object {
        val TAG: String = DictionaryFragment::class.java.simpleName
        fun newInstance() = DictionaryFragment()
    }
}