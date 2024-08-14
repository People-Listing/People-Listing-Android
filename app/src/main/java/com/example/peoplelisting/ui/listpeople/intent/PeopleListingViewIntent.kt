package com.example.peoplelisting.ui.listpeople.intent

import com.example.peoplelisting.ui.base.ViewIntent

sealed interface PeopleListingViewIntent : ViewIntent {
    data object LoadData : PeopleListingViewIntent
    data object RefreshData : PeopleListingViewIntent
}