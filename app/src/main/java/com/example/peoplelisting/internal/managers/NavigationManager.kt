package com.example.peoplelisting.internal.managers

import android.os.Bundle
import androidx.annotation.AnimRes
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import timber.log.Timber

class NavigationManager(private val navController: NavController) {
    fun navigateUp() {
        try {
            navController.navigateUp()
        } catch (ex: IllegalArgumentException) {
            Timber.e(ex)
        }
    }

    fun navigateToAction(@IdRes id: Int, args: Bundle) {
        try {
            navController.navigate(id, args)
        } catch (ex: IllegalArgumentException) {
            Timber.e(ex)
        }
    }

    fun navigateToAction(@IdRes id: Int) {
        try {
            navController.navigate(id)
        } catch (ex: IllegalArgumentException) {
            Timber.e(ex)
        }
    }

    fun navigateToRoute(
        route: String, popUpInclusive: Boolean, popUpTo: String?, @AnimRes enterAnim: Int? = null,
        @AnimRes exitAnim: Int? = null,
        @AnimRes popEnterAnim: Int? = null,
        @AnimRes popExitAnim: Int? = null
    ) {
        try {
            val navOptions = getNavOptions(
                popUpTo,
                enterAnim,
                exitAnim,
                popEnterAnim,
                popExitAnim,
                popUpInclusive
            )
            navController.navigate(route = route, navOptions)
        } catch (ex: IllegalArgumentException) {
            Timber.e(ex)
        }
    }

    private fun getNavOptions(
        popUpTo: String?,
        @AnimRes enterAnim: Int?,
        @AnimRes exitAnim: Int?,
        @AnimRes popEnterAnim: Int?,
        @AnimRes popExitAnim: Int?,
        popUpInclusive: Boolean
    ): NavOptions {
        val options = NavOptions
            .Builder()
        if (enterAnim != null) options.setEnterAnim(enterAnim)
        if (exitAnim != null) options.setExitAnim(exitAnim)
        if (popEnterAnim != null) options.setPopEnterAnim(popEnterAnim)
        if (popExitAnim != null) options.setPopExitAnim(popExitAnim)
        if (popUpTo != null) options.setPopUpTo(popUpTo, popUpInclusive)
        return options.build()
    }
}