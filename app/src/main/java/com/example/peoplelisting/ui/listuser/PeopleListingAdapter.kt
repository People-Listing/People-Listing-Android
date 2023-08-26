package com.example.peoplelisting.ui.listuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.peoplelisting.R
import com.example.peoplelisting.data.model.dto.PersonDto
import com.example.peoplelisting.databinding.PersonCardLayoutBinding
import com.example.peoplelisting.internal.utilities.getQuantityString

class PeopleListingAdapter : ListAdapter<PersonDto, PeopleListingAdapter.PersonViewHolder>(PeopleDiffCallBack()) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PersonCardLayoutBinding.inflate(inflater, parent, false)
        return PersonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(getItem(position))
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