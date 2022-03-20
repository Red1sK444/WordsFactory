package com.r3d1r4ph.wordsfactory.ui.menu

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import by.kirich1409.viewbindingdelegate.viewBinding
import com.r3d1r4ph.wordsfactory.R
import com.r3d1r4ph.wordsfactory.databinding.ActivityMenuBinding
import com.r3d1r4ph.wordsfactory.ui.menu.dictionary.DictionaryFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuActivity : AppCompatActivity() {

    private val viewBinding by viewBinding(ActivityMenuBinding::bind, R.id.rootLayout)
    private var currentTag = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        configureBottomNavigationView()
    }

    private fun configureBottomNavigationView() {
        with(viewBinding.menuBottomNavigationView) {
            setOnItemSelectedListener { item ->
                performBottomNavigation(item)
            }
            selectedItemId = R.id.dictionaryItem
        }
    }

    private fun performBottomNavigation(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.dictionaryItem -> {
                if (currentTag != DictionaryFragment.TAG) {
                    navigateToFragment(DictionaryFragment.TAG, DictionaryFragment.newInstance())
                }
            }
            R.id.trainingItem -> {
                //TODO navigateToTrainingFragment
            }
            R.id.videoItem -> {
                //TODO navigateToVideoFragment
            }
        }
        return true
    }

    private fun navigateToFragment(tag: String, fragment: Fragment) {
        supportFragmentManager.commit {
            replace(
                R.id.menuFragmentContainerView,
                fragment,
                tag
            )
        }
        currentTag = tag
    }
}