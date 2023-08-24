package com.example.peoplelisting.internal.extensions

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(crossinline factory: (LayoutInflater) -> T): Lazy<T> =
    lazy {
        factory(layoutInflater)
    }

inline fun <T : ViewBinding> Fragment.viewBinding(crossinline factory: (View) -> T): ReadOnlyProperty<Fragment, T> = object :
    ReadOnlyProperty<Fragment,
            T>, DefaultLifecycleObserver {
    private var binding: T? = null
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        return binding ?: factory(requireView()).also {
            if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)) {
                viewLifecycleOwner.lifecycle.addObserver(this)
            }
        }
    }
    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        binding = null
    }

}