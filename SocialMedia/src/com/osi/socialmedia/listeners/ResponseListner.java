package com.osi.socialmedia.listeners;

import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthError;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.osi.socialmedia.utils.Preferences;

public final class ResponseListner implements DialogListener {
	
private Context context;

	
	public ResponseListner(Context context)
	{
		this.context = context;
		
	}
	
	@Override
	public void onComplete(Bundle values) {

		Log.d("ShareButton", "Authentication Successful");

		// Get name of provider after authentication
		 String providerName = values.getString(SocialAuthAdapter.PROVIDER);
		if(providerName.equalsIgnoreCase("facebook")){
			
			Preferences.setfacebookloggedin(context, true);
			
		}
		if(providerName.equalsIgnoreCase("linkedin")){
			
			Preferences.setlinkedinloggedin(context, true);
			
		}
		Log.d("ShareButton", "Provider Name = " + providerName);
		//Toast.makeText(HomeScreenActivity.this, providerName + " connected", Toast.LENGTH_LONG).show();

		// adapter.getFeedsAsync(new FeedDataListener()); 
		//adapter.getUserProfileAsync(new ProfileDataListener());
		//adapter.getContactListAsync(new ContactDataListener());
		//adapter.getFeedsAsync(new FeedDataListener()); 
		//adapter.updateStatus(message, new MessageListner(context),true);
	}

	@Override
	public void onError(SocialAuthError error) {
		Log.d("ShareButton", "Authentication Error: " + error.getMessage());
	}

	@Override
	public void onCancel() {
		Log.d("ShareButton", "Authentication Cancelled");
	}

	@Override
	public void onBack() {
		Log.d("Share-Button", "Dialog Closed by pressing Back Key");
	}

}


