package com.osi.socialmedia.listeners;

import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public final class MessageListner implements SocialAuthListener<Integer> {
	private Context context;
	
	public MessageListner(Context context)
	{
		this.context = context;
		
		
	}
	
	@Override
	public void onExecute(String provider, Integer t) {
		Integer status = t;
		Log.d("FFFF", "FFFF");
		
		if (status.intValue() == 200 || status.intValue() == 201 || status.intValue() == 204)
			Toast.makeText(context, "Message posted on " + provider, Toast.LENGTH_LONG).show();
		else
			Toast.makeText(context, "Message not posted on " + provider, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onError(SocialAuthError e) {
		Log.d("ErrF", "Err");
	}
}
