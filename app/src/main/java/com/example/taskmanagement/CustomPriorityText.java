package com.example.taskmanagement;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class CustomPriorityText extends TextView {
    public CustomPriorityText(Context context) {
        super(context);
        init();
    }

    public CustomPriorityText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomPriorityText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomPriorityText(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);init();

    }
    private void init() {
        setTextSize(10);
        setTextColor(android.graphics.Color.WHITE);
        setTypeface(getTypeface(), android.graphics.Typeface.BOLD);
        setGravity(android.view.Gravity.CENTER);
        setBackgroundResource(R.drawable.customprioritycolour);
        setPadding(12, 6, 12, 6);
    }
}
