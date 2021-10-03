package az.zero.nsacoviddoctor.presentation.adapter.post_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import az.zero.nsacoviddoctor.common.setImageUsingGlide
import az.zero.nsacoviddoctor.databinding.ItemPostBinding
import az.zero.nsacoviddoctor.domain.model.covid_post.CovidPost

class PostAdapter() :
    ListAdapter<CovidPost, PostAdapter.PostViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class PostViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                        val post = getItem(bindingAdapterPosition)
                        onPostClickListener?.let { it(post) }
                    }
                }

            }
        }


        fun bind(post: CovidPost) {
            binding.apply {
                setImageUsingGlide(postImageIv, post.image)
                postTitleTv.text = post.title
            }
        }

    }

    private var onPostClickListener: ((CovidPost) -> Unit)? = null
    fun setOnPostClickListener(listener: (CovidPost) -> Unit) {
        onPostClickListener = listener
    }

    class DiffCallback : DiffUtil.ItemCallback<CovidPost>() {
        override fun areItemsTheSame(oldItem: CovidPost, newItem: CovidPost): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: CovidPost, newItem: CovidPost): Boolean =
            oldItem == newItem
    }
}