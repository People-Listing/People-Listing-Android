package com.example.peoplelisting.ui.listuser

import android.os.Bundle
import android.text.SpannableString
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.peoplelisting.R
import com.example.peoplelisting.data.model.dto.ListItem
import com.example.peoplelisting.data.model.dto.PersonDto
import com.example.peoplelisting.data.model.dto.SectionTitle
import com.example.peoplelisting.data.model.dto.WidgetType
import com.example.peoplelisting.data.resource.ResourceState
import com.example.peoplelisting.databinding.ListUsersFragmentBinding
import com.example.peoplelisting.internal.CustomTypefaceSpan
import com.example.peoplelisting.internal.extensions.hide
import com.example.peoplelisting.internal.extensions.show
import com.example.peoplelisting.internal.extensions.viewBinding
import com.example.peoplelisting.internal.utilities.getFont
import com.example.peoplelisting.ui.base.BaseFragment
import com.example.peoplelisting.ui.snackbar.CustomSnackBar
import com.example.peoplelisting.ui.snackbar.SnackBarButtonData
import com.example.peoplelisting.ui.snackbar.SnackBarData
import com.jakewharton.rxbinding4.swiperefreshlayout.refreshes
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class ListUsersFragment : BaseFragment(R.layout.list_users_fragment) {
    override val screenTitle: String
        get() = getString(R.string.list_user_title)
    override val showBackButton: Boolean
        get() = false
    private var items: MutableList<ListItem>? = mutableListOf()
    private val binding: ListUsersFragmentBinding by viewBinding(ListUsersFragmentBinding::bind)
    private val viewModel: ListUsersViewModel by viewModels(ownerProducer = { requireActivity() })
    private val simpleCallBack = object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN or
                ItemTouchHelper.START or ItemTouchHelper.END, 0
    ) {
        private var originalPosition = RecyclerView.NO_POSITION
        private var draggingCurated = false
        override fun isLongPressDragEnabled(): Boolean {
            return false
        }

        override fun canDropOver(
            recyclerView: RecyclerView,
            current: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            if (current is PeopleListingAdapter.PersonViewHolder && target is PeopleListingAdapter.PersonViewHolder) {
                val fromWidget = current.widgetType
                val toWidget = target.widgetType
                return (fromWidget == WidgetType.CURATED_WIDGET && toWidget == WidgetType.MY_WIDGET || (fromWidget
                        == WidgetType.MY_WIDGET && toWidget == WidgetType.MY_WIDGET))
            }
            if (current is PeopleListingAdapter.PersonViewHolder && target is PeopleListingAdapter.TitleViewHolder) {
                return target.widgetType == WidgetType.CURATED_WIDGET && current.adapterPosition < target
                    .adapterPosition && draggingCurated
            }
            return false
        }

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            if (actionState == ItemTouchHelper.ACTION_STATE_DRAG && (viewHolder as PeopleListingAdapter.PersonViewHolder)
                    .widgetType == WidgetType.CURATED_WIDGET
            ) {
                draggingCurated = true
                originalPosition = viewHolder.adapterPosition
            } else {
                draggingCurated = false
            }
            super.onSelectedChanged(viewHolder, actionState)
        }

        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
            val finalPosition = viewHolder.adapterPosition
            val curatedTitlePosition = (recyclerView.adapter as PeopleListingAdapter).getCuratedTitlePosition()
            if (finalPosition >= curatedTitlePosition) {
                binding.people.adapter?.notifyItemMoved(finalPosition, originalPosition)
            }
            draggingCurated = false
            originalPosition = RecyclerView.NO_POSITION
            super.clearView(recyclerView, viewHolder)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val fromPosition = viewHolder.adapterPosition
            val toPosition = target.adapterPosition
            binding.swipeRefresh.isEnabled = false
            val item = items?.removeAt(fromPosition)
            if (target is PeopleListingAdapter.TitleViewHolder) {
                (viewHolder as PeopleListingAdapter.PersonViewHolder).widgetType = WidgetType.CURATED_WIDGET
                (item as PersonDto).widgetType = WidgetType.CURATED_WIDGET
            } else {
                (viewHolder as PeopleListingAdapter.PersonViewHolder).widgetType = WidgetType.MY_WIDGET
                (item as PersonDto).widgetType = WidgetType.MY_WIDGET
            }
            items?.add(toPosition, item)
            binding.people.adapter?.notifyItemMoved(fromPosition, toPosition)
            Timber.tag("TOUCH HELPER").i("onMove")
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

    }
    private val itemTouchListener = ItemTouchHelper(simpleCallBack)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        manageSubscription()
        manageEvents()
        Timber.tag("getMyPeople").i("onViewCreated")
        if (savedInstanceState == null)
            viewModel.getMyPeople(checkIfFetched = true)

    }

    private fun startLoading() {
        if (!binding.swipeRefresh.isRefreshing) {
            binding.emptyResult.hide()
            binding.fab.hide()
            binding.totalCount.hide()
            (binding.people.adapter as PeopleListingAdapter).submitList(List(5) {
                PersonDto().apply { isLoading = true }
            })
        }
    }

    private fun stopLoading() {
        binding.emptyResult.hide()
        binding.fab.show()
        binding.totalCount.show()
        if (binding.swipeRefresh.isRefreshing) {
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun setEmptyResult() {
        binding.totalCount.hide()
        binding.people.hide()
        binding.emptyResult.show()
        binding.fab.show()
        if (binding.swipeRefresh.isRefreshing) {
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun manageEvents() {
        binding.fab.setOnClickListener {
            navManager.navigateToDirection(
                ListUsersFragmentDirections.actionListUsersFragmentToCreateUserFragment(),
                enterAnim = R.anim.enter_from_right,
                exitAnim = R.anim.exit_to_left,
                popEnterAnim = R.anim.enter_from_left,
                popExitAnim = R.anim.exit_to_right
            )
        }
        binding.swipeRefresh.refreshes().subscribe {
            viewModel.getMyPeople()
        }.isDisposed
    }

    private fun setOnTouchHelper() {
        itemTouchListener.attachToRecyclerView(binding.people)
    }

    private fun clearOnTouchHelper() {
        itemTouchListener.attachToRecyclerView(null)
    }

    private fun manageSubscription() {
        viewModel.usersResponse.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            when (it.resourceState) {
                ResourceState.LOADING -> {
                    clearOnTouchHelper()
                    startLoading()
                }

                ResourceState.SUCCESS -> {
                    setOnTouchHelper()
                    if (it.data?.isEmpty() == true) {
                        setEmptyResult()
                    } else {
                        stopLoading()
                        items = it.data?.map { d -> d.copy() }?.toMutableList()
                        items?.forEachIndexed { index, listItem ->
                            if (index in 0..2) (listItem as PersonDto).widgetType = WidgetType.MY_WIDGET
                            else (listItem as PersonDto).widgetType = WidgetType.CURATED_WIDGET
                        }
                        items?.add(0, SectionTitle("My Widgets", "TITLE-1", WidgetType.MY_WIDGET))
                        items?.add(4, SectionTitle("Curated Widgets", "TITLE-2", WidgetType.CURATED_WIDGET))
                        (binding.people.adapter as PeopleListingAdapter).submitList(items)
                        setTotalCount(it.data?.count() ?: 0)
                    }
                }

                ResourceState.ERROR -> {
                    if (binding.swipeRefresh.isRefreshing) {
                        binding.swipeRefresh.isRefreshing = false
                    }
                    val message = it.message ?: getString(R.string.get_people_error)
                    val snackBarData = SnackBarData(message, snackBarButtonData = SnackBarButtonData(listener = {
                        viewModel.getMyPeople()
                    }))
                    CustomSnackBar(viewLifecycleOwner).showSnackBar(requireView(), snackBarData)
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.people.layoutManager = LinearLayoutManager(requireContext())
        val adapter = PeopleListingAdapter(itemTouchListener)
        binding.people.adapter = adapter
        (binding.people.adapter as PeopleListingAdapter).hasStableIds()
    }

    private fun setTotalCount(totalCount: Int) {
        val dinBold = CustomTypefaceSpan("din", getFont(R.font.din_bold)!!)
        val completeText = getString(R.string.total_count, totalCount.toString())
        val toBeBold = totalCount.toString()
        val spannableString = SpannableString(completeText)
        val start = completeText.indexOf(toBeBold)
        val end = start + toBeBold.length
        spannableString.setSpan(dinBold, start, end, SpannableString.SPAN_INCLUSIVE_INCLUSIVE)
        binding.totalCount.text = spannableString

    }
}