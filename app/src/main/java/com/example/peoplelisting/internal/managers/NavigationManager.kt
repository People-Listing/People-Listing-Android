package com.example.peoplelisting.internal.managers

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.peoplelisting.ui.main.PeopleListingScreenRoute
import timber.log.Timber

class NavigationManager(private val navController: NavController) {
    fun currentRoute(routName: String?) = PeopleListingScreenRoute.valueOf(
        routName ?: PeopleListingScreenRoute.Listing.name
    )
    fun canNavigateBack() = navController.previousBackStackEntry != null
    fun navigateUp() {
        try {
            navController.navigateUp()
        } catch (ex: IllegalArgumentException) {
            Timber.e(ex)
        }
    }

    fun navigateToRoute(
        route: String, popUpInclusive: Boolean, popUpTo: String?
    ) {
        try {
            val navOptions = getNavOptions(
                popUpTo,
                popUpInclusive
            )
            navController.navigate(route = route, navOptions)
        } catch (ex: IllegalArgumentException) {
            Timber.e(ex)
        }
    }

    private fun getNavOptions(
        popUpTo: String?,
        popUpInclusive: Boolean
    ): NavOptions {
        val options = NavOptions
            .Builder()
        if (popUpTo != null) options.setPopUpTo(popUpTo, popUpInclusive)
        return options.build()
    }
}