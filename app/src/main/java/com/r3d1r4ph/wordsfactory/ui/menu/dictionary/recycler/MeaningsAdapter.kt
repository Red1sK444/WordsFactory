package com.r3d1r4ph.wordsfactory.ui.menu.dictionary.recycler

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.r3d1r4ph.wordsfactory.R
import com.r3d1r4ph.wordsfactory.databinding.ItemRecyclerMeaningBinding
import com.r3d1r4ph.wordsfactory.domain.models.Meaning

class MeaningsAdapter : ListAdapter<Meaning, MeaningsAdapter.ViewHolder>(DIFF) {

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<Meaning>() {
            override fun areItemsTheSame(oldItem: Meaning, newItem: Meaning) =
                oldItem.definition == newItem.definition

            override fun areContentsTheSame(oldItem: Meaning, newItem: Meaning) =
                oldItem == newItem

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_recycler_meaning, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemRecyclerMeaningBinding.bind(view)

        fun bind(meaning: Meaning) = with(binding) {
            itemMeaningDefinitionTextView.text = meaning.definition

            if (meaning.example == null) {
                itemMeaningExampleTextView.visibility = View.GONE
                return
            }
            itemMeaningExampleTextView.visibility = View.VISIBLE

            val spannableExample =
                SpannableString("${binding.root.context.resources.getString(R.string.meaning_example_start)} ${meaning.example}")

            spannableExample.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(binding.root.context, R.color.blue)),
                0, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            itemMeaningExampleTextView.setText(
                spannableExample,
                TextView.BufferType.SPANNABLE
            )
        }
    }
}