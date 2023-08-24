package com.example.peoplelisting.ui.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI

class BaseFragment: Fragment, DIAware {
    constructor(): super()
    constructor(@LayoutRes layoutRes: Int): super(layoutRes)

    override val di: DI by closestDI()
}