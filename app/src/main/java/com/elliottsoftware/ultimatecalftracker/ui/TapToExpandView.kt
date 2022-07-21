package com.elliottsoftware.ultimatecalftracker.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import com.elliottsoftware.ultimatecalftracker.R

class TapToExpandView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int =0
) : RelativeLayout(context, attrs,defStyleAttr){

    init {
        // used to signify that our custom view is clickable
        isClickable = true
        context.withStyledAttributes(attrs, R.styleable.ExpandOnClickView){
            childToCollapse = getInt(R.styleable.ExpandOnClickView_childToCollapse,0)
        }
    }
    private var childToCollapse: Int = 0

    /**
     * method where logic is put to perform when this view is clicked
     * */
    override fun performClick(): Boolean {
        if (super.performClick()) return true
        this.getChildAt(4).apply {
            isVisible = visibilityCheck(isVisible)
        }

        invalidate()
        return true
    }
    //
    private fun visibilityCheck(isVisible:Boolean):Boolean{
        return !isVisible
    }
}