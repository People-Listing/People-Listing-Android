package com.example.peoplelisting.ui.listuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import com.example.peoplelisting.R
import com.example.peoplelisting.data.model.dto.CardWidgetItem
import com.example.peoplelisting.data.model.dto.ListItem
import com.example.peoplelisting.data.model.dto.PersonDto
import com.example.peoplelisting.data.model.dto.SectionTitle
import com.example.peoplelisting.data.model.dto.WidgetType
import com.example.peoplelisting.databinding.ItemBoxBinding
import com.example.peoplelisting.databinding.ItemTitleBinding
import com.example.peoplelisting.databinding.PersonCardLayoutBinding
import com.example.peoplelisting.databinding.PersonCardShimmeringBinding
import com.example.peoplelisting.internal.utilities.getQuantityString

class PeopleListingAdapter(private val onTouchHelper: ItemTouchHelper) :
    ListAdapter<ListItem, ViewHolder>(PeopleDiffCallBack()) {

    abstract inner class DraggableViewHolder(binding: ViewBinding) : ViewHolder(binding.root) {
        var widgetType = WidgetType.OTHER
    }
    inner class PersonViewHolder(private val binding: PersonCardLayoutBinding) : DraggableViewHolder(binding) {
        private val nameTv = binding.name
        private val ageTv = binding.age
        private val professionTv = binding.profession
        fun bind(person: PersonDto) {
            val age = person.age
            ageTv.text = getQuantityString(R.plurals.age_years_old, age, age.toString())
            nameTv.text = person.name
            widgetType = person.widgetType
            binding.root.setOnLongClickListener {
                onTouchHelper.startDrag(this)
                false
            }
            professionTv.text = person.profession
        }
    }

    inner class CardViewHolder(private val binding: ItemBoxBinding) : DraggableViewHolder(binding) {
        fun bind(card: CardWidgetItem) {
            binding.tvHeading.text = card.heading
            binding.tvSubHeading.text = card.subHeading
            widgetType = card.widgetType
            binding.root.setOnLongClickListener {
                onTouchHelper.startDrag(this)
                false
            }
        }
    }

    inner class LoadingViewHolder(private val binding: PersonCardShimmeringBinding) : ViewHolder(binding.root) {
        fun startLoading() {
            binding.shimmeringLayout.startShimmer()
        }
    }

    inner class TitleViewHolder(private val binding: ItemTitleBinding) : ViewHolder(binding.root) {
        var widgetType = WidgetType.OTHER
        fun bind(title: SectionTitle) {
            binding.title.text = title.title
            widgetType = title.widgetType
        }
    }

    fun getCuratedTitlePosition(): Int {
        return currentList.indexOfFirst { it.id == "TITLE-2" }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            LOADING -> {
                val binding = PersonCardShimmeringBinding.inflate(inflater, parent, false)
                LoadingViewHolder(binding)
            }

            TITLE -> {
                val binding = ItemTitleBinding.inflate(inflater, parent, false)
                TitleViewHolder(binding)
            }

            CARD -> {
                val binding = ItemBoxBinding.inflate(inflater, parent, false)
                CardViewHolder(binding)
            }

            else -> {
                val binding = PersonCardLayoutBinding.inflate(inflater, parent, false)
                PersonViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is PersonViewHolder -> holder.bind(getItem(position) as PersonDto)
            is TitleViewHolder -> holder.bind((getItem(position) as SectionTitle))
            is CardViewHolder -> holder.bind(getItem(position) as CardWidgetItem)
            is LoadingViewHolder -> holder.startLoading()
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item is SectionTitle) TITLE
        else if (item is PersonDto) {
            if (item.isLoading) {
                LOADING
            } else {
                OTHER
            }
        } else if (item is CardWidgetItem) CARD
        else {
            OTHER
        }

    }

//    fun addPerson(person: PersonDto, section: String) {
//        val current = currentList.filter { it is PersonDto }.map { (it as PersonDto).copy() }.toMutableList()
//        current.add(person)
//        submitList(current)
//    }

    companion object {
        const val LOADING = 0
        const val OTHER = 1
        const val TITLE = 2
        const val CARD = 3
    }
}

class PeopleDiffCallBack : DiffUtil.ItemCallback<ListItem>() {
    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return if (oldItem is PersonDto && newItem is PersonDto)
            oldItem == newItem
        else if (oldItem is SectionTitle && newItem is SectionTitle)
            oldItem == newItem
        else
            false
    }

}