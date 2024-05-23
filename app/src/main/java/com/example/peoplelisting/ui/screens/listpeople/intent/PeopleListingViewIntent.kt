package com.example.peoplelisting.ui.screens.listpeople.intent

import com.example.peoplelisting.ui.screens.base.ViewIntent

sealed interface PeopleListingViewIntent : ViewIntent {
    data object LoadData : PeopleListingViewIntent
    data object RefreshData : PeopleListingViewIntent
}