package com.mythology.quiz.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mythology.quiz.data.MythCharacter
import com.mythology.quiz.databinding.ItemGalleryBinding

class GalleryAdapter(
    private val onCharacterClick: (MythCharacter) -> Unit
) : ListAdapter<MythCharacter, GalleryAdapter.GalleryViewHolder>(GalleryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val binding = ItemGalleryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return GalleryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class GalleryViewHolder(
        private val binding: ItemGalleryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(character: MythCharacter) {
            binding.apply {
                if (character.isUnlocked) {
                    val resId = root.context.resources.getIdentifier(
                        character.characterDrawable, "drawable", root.context.packageName)
                    if (resId != 0) {
                        ivGalleryImage.setImageResource(resId)
                        ivGalleryImage.visibility = android.view.View.VISIBLE
                        tvGalleryEmoji.visibility = android.view.View.GONE
                    } else {
                        ivGalleryImage.visibility = android.view.View.GONE
                        tvGalleryEmoji.visibility = android.view.View.VISIBLE
                        tvGalleryEmoji.text = character.emoji
                    }
                    tvGalleryName.text = character.nameKorean
                    cardGallery.alpha = 1f
                    ivLock.visibility = android.view.View.GONE
                    overlayLock.visibility = android.view.View.GONE

                    root.setOnClickListener {
                        root.animate().scaleX(0.9f).scaleY(0.9f).setDuration(100)
                            .withEndAction {
                                root.animate().scaleX(1f).scaleY(1f).setDuration(100).start()
                                onCharacterClick(character)
                            }.start()
                    }
                } else {
                    ivGalleryImage.visibility = android.view.View.GONE
                    tvGalleryEmoji.visibility = android.view.View.VISIBLE
                    tvGalleryEmoji.text = "❓"
                    tvGalleryName.text = "???"
                    cardGallery.alpha = 0.6f
                    ivLock.visibility = android.view.View.VISIBLE
                    overlayLock.visibility = android.view.View.VISIBLE
                    root.setOnClickListener(null)
                }
            }
        }
    }

    class GalleryDiffCallback : DiffUtil.ItemCallback<MythCharacter>() {
        override fun areItemsTheSame(oldItem: MythCharacter, newItem: MythCharacter): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MythCharacter, newItem: MythCharacter): Boolean =
            oldItem == newItem
    }
}
