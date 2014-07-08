package com.osi.socialmedia.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;



public class CustomText extends TextView{

	public CustomText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public CustomText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CustomText(Context context) {
		super(context);
		init();
	}

	private void init() {
		if (!isInEditMode()) {
			Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Roboto-Light.ttf");
			setTypeface(tf);
		}
	}

}




