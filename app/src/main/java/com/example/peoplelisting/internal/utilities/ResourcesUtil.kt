package com.example.peoplelisting.internal.utilities

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.peoplelisting.ListingApplication.Companion.context

fun getDimen(@DimenRes id: Int): Float = context.resources.getDimension(id)

fun getDrawable(@DrawableRes id: Int): Drawable? = ContextCompat.getDrawable(context, id)

fun getColor(@ColorRes id: Int): Int = ContextCompat.getColor(context, id)

fun getString(@StringRes id: Int) = context.getString(id)

fun getString(@StringRes id: Int, vararg args: Any): String = context.getString(id, *args)

fun getQuantityString(@PluralsRes id: Int, quantity: Int): String = context.resources.getQuantityString(id, quantity)

fun getQuantityString(@PluralsRes id: Int, quantity: Int, vararg args: Any): String = context.resources.getQuantityString(id,
    quantity, *args)

fun getColorStateList(@ColorRes id: Int): ColorStateList? = ContextCompat.getColorStateList(context, id)

fun getFont(@FontRes id: Int): Typeface? = ResourcesCompat.getFont(context, id)

