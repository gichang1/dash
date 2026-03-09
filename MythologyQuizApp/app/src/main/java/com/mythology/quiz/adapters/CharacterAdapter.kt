package com.mythology.quiz.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mythology.quiz.data.MythCharacter
import com.mythology.quiz.databinding.ItemCharacterBinding

class CharacterAdapter(
    private val onCharacterClick: (MythCharacter) -> Unit
) : ListAdapter<MythCharacter, CharacterAdapter.CharacterViewHolder>(CharacterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemCharacterBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CharacterViewHolder(
        private val binding: ItemCharacterBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(character: MythCharacter) {
            binding.apply {
                val resId = root.context.resources.getIdentifier(
                    character.characterDrawable, "drawable", root.context.packageName)
                if (resId != 0) {
                    ivCharacterImage.setImageResource(resId)
                    ivCharacterImage.visibility = View.VISIBLE
                    tvCharacterEmoji.visibility = View.GONE
                } else {
                    ivCharacterImage.visibility = View.GONE
                    tvCharacterEmoji.visibility = View.VISIBLE
                    tvCharacterEmoji.text = character.emoji
                }
                tvCharacterName.text = character.nameKorean
                tvCharacterGreek.text = character.nameGreek
                tvCharacterCategory.text = character.category
                tvDifficulty.text = "★".repeat(character.difficulty)

                root.setOnClickListener {
                    // Scale animation on click
                    root.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100)
                        .withEndAction {
                            root.animate().scaleX(1f).scaleY(1f).setDuration(100).start()
                            onCharacterClick(character)
                        }.start()
                }
            }
        }
    }

    class CharacterDiffCallback : DiffUtil.ItemCallback<MythCharacter>() {
        override fun areItemsTheSame(oldItem: MythCharacter, newItem: MythCharacter): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MythCharacter, newItem: MythCharacter): Boolean =
            oldItem == newItem
    }
}
