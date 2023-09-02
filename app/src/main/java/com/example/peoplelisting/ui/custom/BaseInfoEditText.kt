package com.example.peoplelisting.ui.custom

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.peoplelisting.R
import com.example.peoplelisting.databinding.BaseInfoEditTextBinding

class BaseInfoEditText @JvmOverloads constructor(context: Context, private val attrs: AttributeSet) : LinearLayout
    (context, attrs) {
    private val binding: BaseInfoEditTextBinding = BaseInfoEditTextBinding.inflate(
        LayoutInflater.from(context),
        this, true
    )

    init {
        applyAttributes()
    }

    private fun applyAttributes() {
        context.theme.obtainStyledAttributes(attrs, R.styleable.BaseInfoEditText, 0, 0).apply {
            try {
                val title = getString(R.styleable.BaseInfoEditText_title) ?: ""
                val inputType = getInt(R.styleable.BaseInfoEditText_inputType, InputType.TYPE_CLASS_TEXT)
                binding.infoTitle.text = title
                binding.infoEditText.inputType = inputType
            } finally {
                recycle()
            }

        }
    }

}