package com.ingreatsol.loadingbutton;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class LoadingButton extends ConstraintLayout {
    private static final int[] VISIBILITY_FLAGS = {VISIBLE, INVISIBLE, GONE};
    private TextView textView;
    private ProgressBar progressBar;

    private String mTextLoading;
    private String mText;


    public LoadingButton(@NonNull Context context) {
        super(context);
        init(null, 0, 0);
    }

    public LoadingButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0, 0);
    }

    public LoadingButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, 0);
    }

    public LoadingButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr, defStyleRes);
    }

    private void init(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        LayoutInflater.from(getContext()).inflate(R.layout.loading_button, this, true);
        textView = findViewById(R.id.text_progress_button);
        progressBar = findViewById(R.id.progress_button_button);

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(
                    attrs,
                    R.styleable.LoadingButton,
                    defStyleAttr,
                    defStyleRes);
            final int N = a.getIndexCount();

            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                if (attr == R.styleable.LoadingButton_progressVisibility) {
                    final int visibility = a.getInt(attr, 0);
                    if (visibility != 0) {
                        progressBar.setVisibility(VISIBILITY_FLAGS[visibility]);
                    }
                } else if (attr == R.styleable.LoadingButton_text) {
                    mText = a.getString(attr);
                } else if (attr == R.styleable.LoadingButton_textLoading) {
                    mTextLoading = a.getString(attr);
                } else if (attr == R.styleable.LoadingButton_textSize) {
                    textView.setTextSize(
                            TypedValue.COMPLEX_UNIT_PX,
                            a.getDimensionPixelSize(attr, -1)
                    );
                } else if (attr == R.styleable.LoadingButton_textColor) {
                    ColorStateList colorStateList = a.getColorStateList(attr);
                    if (colorStateList != null) {
                        textView.setTextColor(colorStateList);
                    }
                } else if (attr == R.styleable.LoadingButton_progressColor) {
                    ColorStateList colorStateList = a.getColorStateList(attr);
                    if (colorStateList != null) {
                        textView.setTextColor(colorStateList);
                    }
                } else if (attr == R.styleable.LoadingButton_textStyle) {
                    textView.setTypeface(textView.getTypeface(), a.getInt(attr, 0));
                }
            }
            a.recycle();

            if (isInProgress()) {
                textView.setText(mTextLoading);
            } else {
                textView.setText(mText);
            }
        }
    }

    public void setTextColor(@ColorInt Integer color) {
        textView.setTextColor(color);
    }

    public void setProgressColor(@ColorRes Integer color) {
        progressBar.setIndeterminateTintList(getResources().getColorStateList(color, getContext().getTheme()));
    }

    public void setText(String text) {
        this.mText = text;
    }

    public void setTextLoading(String text) {
        this.mTextLoading = text;
    }

    public void setProgressVisibility(@Visibility Integer visibility) {
        progressBar.setVisibility(visibility);
    }

    public void onStartLoading() {
        textView.setText(mTextLoading);
        setProgressVisibility(View.VISIBLE);
    }

    public void onStopLoading() {
        textView.setText(mText);
        setProgressVisibility(View.GONE);
    }

    public Boolean isInProgress() {
        return progressBar.getVisibility() == View.VISIBLE;
    }

    @IntDef({VISIBLE, INVISIBLE, GONE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Visibility {
    }
}
