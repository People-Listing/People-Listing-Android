package com.example.peoplelisting.ui.createuser

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.peoplelisting.R
import com.example.peoplelisting.ui.base.BaseFragment

class CreateUserFragment: BaseFragment(R.layout.create_user_fragment) {
    override val screenTitle: String
        get() = getString(R.string.create_user_title)
    override val showBackButton: Boolean
        get() = true
}