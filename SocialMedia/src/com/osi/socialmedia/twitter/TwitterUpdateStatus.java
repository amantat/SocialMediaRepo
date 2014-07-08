package com.osi.socialmedia.twitter;

import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.hintdesk.core.util.StringUtil;
import com.osi.socialmedia.utils.ConstantValues;

public class TwitterUpdateStatus extends AsyncTask<String, String, Boolean> {

	private Context context;


	public TwitterUpdateStatus(Context context)
	{
		this.context = context;

	}

	@Override
	protected void onPostExecute(Boolean result) {
		if (result){
			Toast.makeText(context, "Tweet successfully", Toast.LENGTH_SHORT).show();
		new TwitterGetAccessToken(context).execute("");
		}else{
			Toast.makeText(context, "Tweet failed", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected Boolean doInBackground(String... params) {
		try {
			SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
			String accessTokenString = sharedPreferences.getString(ConstantValues.PREFERENCE_TWITTER_OAUTH_TOKEN, "");
			String accessTokenSecret = sharedPreferences.getString(ConstantValues.PREFERENCE_TWITTER_OAUTH_TOKEN_SECRET, "");

			if (!StringUtil.isNullOrWhitespace(accessTokenString) && !StringUtil.isNullOrWhitespace(accessTokenSecret)) {
				AccessToken accessToken = new AccessToken(accessTokenString, accessTokenSecret);
				twitter4j.Status status = TwitterUtil.getInstance().getTwitterFactory().getInstance(accessToken).updateStatus(params[0]);
				return true;
			}

		} catch (TwitterException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		return false;  //To change body of implemented methods use File | Settings | File Templates.

	}
}
