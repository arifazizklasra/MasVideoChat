package com.rifcode.masvideochat.FontsWedgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;

import com.rifcode.masvideochat.R;


public class TvNextBold extends androidx.appcompat.widget.AppCompatTextView {


    AttributeSet attr;

    public TvNextBold(Context context) {
        super(context);
        setCustomFont(context, attr);
    }

    public TvNextBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public TvNextBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        String customFont = null;
        TypedArray a = null;
        if (attrs != null) {
            a = ctx.obtainStyledAttributes(attrs, R.styleable.NextBold);
            customFont = a.getString(R.styleable.NextBold_customFontNextBold);
        }
        if (customFont == null)
            customFont = "fonts/avenir_next_bold.otf";
        setCustomFont(ctx, customFont);
        if (a != null) {
            a.recycle();
        }
    }

    public boolean setCustomFont(Context ctx, String asset) {
        Typeface tf = null;
        try {
            tf = Typeface.createFromAsset(ctx.getAssets(), asset);
        } catch (Exception e) {
            Log.e("textView", "Could not get typeface", e);
            return false;
        }
        setTypeface(tf);
        return true;
    }

}
