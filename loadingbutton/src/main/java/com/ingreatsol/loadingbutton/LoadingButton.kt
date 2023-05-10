package com.ingreatsol.loadingbutton

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.IntDef
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat

class LoadingButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    style: Int = 0
) : ConstraintLayout(context, attrs, style) {
    private var textView: TextView
    private var progressBar: ProgressBar

    private var mTextLoading: String? = null
    private var mText: String? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.loading_button, this, true)
        textView = findViewById(R.id.text_progress_button)
        progressBar = findViewById(R.id.progress_button_button)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.LoadingButton,
            0, 0
        ).apply {
            try {
                val visible = getInt(R.styleable.LoadingButton_progressVisibility, 0)
                progressBar.visibility =
                    if (visible == 0) View.VISIBLE else if (visible == 1) View.INVISIBLE else View.GONE
                mText = getString(R.styleable.LoadingButton_text)
                mTextLoading = getString(R.styleable.LoadingButton_textLoading)

                textView.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    getDimensionPixelSize(R.styleable.LoadingButton_textSize, -1).toFloat()
                )
                getColorStateList(R.styleable.LoadingButton_textColor)?.let {
                    textView.setTextColor(it)
                }
                getColorStateList(R.styleable.LoadingButton_progressColor)?.let {
                    progressBar.indeterminateTintList = it
                }
                textView.setTypeface(
                    textView.typeface,
                    getInt(R.styleable.LoadingButton_textStyle, 0)
                )

                if (isInProgress()) {
                    textView.text = mTextLoading
                } else {
                    textView.text = mText
                }

            } finally {
                recycle()
            }
        }
    }

    fun setTextColor(@ColorInt color: Int) {
        textView.setTextColor(color)
    }

    fun setProgressColor(@ColorRes color: Int) {
        progressBar.indeterminateTintList = ContextCompat.getColorStateList(context, color)
    }

    fun setText(text: String) {
        this.mText = text
    }

    fun setTextLoading(text: String) {
        this.mTextLoading = text
    }

    fun setProgressVisibility(@Visibility visibility: Int) {
        progressBar.visibility = visibility
    }

    public fun onStartLoading() {
        textView.text = mTextLoading
        progressBar.visibility = View.VISIBLE
    }

    public fun onStopLoading() {
        textView.text = mText
        progressBar.visibility = View.GONE
    }

    public fun isInProgress(): Boolean {
        return progressBar.visibility == View.VISIBLE
    }

    @IntDef(View.VISIBLE, View.INVISIBLE, View.GONE)
    @Retention(AnnotationRetention.SOURCE)
    annotation class Visibility
}