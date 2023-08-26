package com.example.peoplelisting.ui.listuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.peoplelisting.R
import com.example.peoplelisting.data.model.dto.PersonDto
import com.example.peoplelisting.databinding.PersonCardLayoutBinding
import com.example.peoplelisting.databinding.PersonCardShimmeringBinding
import com.example.peoplelisting.internal.utilities.getQuantityString

class PeopleListingAdapter : ListAdapter<PersonDto, ViewHolder>(PeopleDiffCallBack()) {
    inner class PersonViewHolder(binding: PersonCardLayoutBinding) : ViewHolder(binding.root) {
        private val nameTv = binding.name
        private val ageTv = binding.age
        private val professionTv = binding.profession
        fun bind(person: PersonDto) {
            val age = person.age
            ageTv.text = getQuantityString(R.plurals.age_years_old, age, age.toString())
            nameTv.text = person.name
            professionTv.text = person.profession
        }
    }

    inner class LoadingViewHolder(private val binding: PersonCardShimmeringBinding) : ViewHolder(binding.root) {
        fun startLoading() {
            binding.shimmeringLayout.startShimmer()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            LOADING -> {
                val binding = PersonCardShimmeringBinding.inflate(inflater, parent, false)
                LoadingViewHolder(binding)
            }

            else -> {
                val binding = PersonCardLayoutBinding.inflate(inflater, parent, false)
                PersonViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is PersonViewHolder)
            holder.bind(getItem(position))
        else if (holder is LoadingViewHolder)
            holder.startLoading()
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).isLoading) LOADING else OTHER
    }

    companion object {
        const val LOADING = 0
        const val OTHER = 1
    }
}

class PeopleDiffCallBack : DiffUtil.ItemCallback<PersonDto>() {
    override fun areItemsTheSame(oldItem: PersonDto, newItem: PersonDto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PersonDto, newItem: PersonDto): Boolean {
        return oldItem == newItem
    }

}