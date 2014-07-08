package com.osi.socialmedia.twitter;

import java.io.Serializable;
import java.util.ArrayList;

import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.hintdesk.core.util.StringUtil;
import com.osi.socialmedia.SocialMediaActivity;
import com.osi.socialmedia.model.FeedsModel;
import com.osi.socialmedia.utils.ConstantValues;
import com.osi.socialmedia.utils.Preferences;
import com.osi.socialmedia.utils.Utility;

public class TwitterGetAccessToken extends AsyncTask<String, String, String>  {

	private Context context;
	private  ArrayList<FeedsModel> feeds_list;
	public static ArrayList<FeedsModel> messg_list;
	//private  TransparentProgressDialog pd;
	private ProgressDialog pd; 
	public TwitterGetAccessToken(Context context)
	{
		this.context = context;
		feeds_list = new ArrayList<FeedsModel>();
		messg_list = new ArrayList<FeedsModel>();
	}
	
	
	 @Override
     protected void onPostExecute(String userName) {
         //textViewUserName.setText(Html.fromHtml("<b> Welcome " + userName + "</b>"));
		 if(pd != null && pd.isShowing()) {
				pd.dismiss();
				
			}
		 Preferences.setProfileName(context, userName);
     }

	 @Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		//super.onPreExecute();
		 pd = ProgressDialog.show(context, "", "Loading...");
		// pd = new TransparentProgressDialog(context, R.drawable.spinner);
			//pd.show();
	}
	 
	 @Override
		protected String doInBackground(String... params) {
		
			Twitter twitter = TwitterUtil.getInstance().getTwitter();
			RequestToken requestToken = TwitterUtil.getInstance().getRequestToken();
			if (!StringUtil.isNullOrWhitespace(params[0])) {
				try {

					AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, params[0]);
					SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
					SharedPreferences.Editor editor = sharedPreferences.edit();
					editor.putString(ConstantValues.PREFERENCE_TWITTER_OAUTH_TOKEN, accessToken.getToken());
					editor.putString(ConstantValues.PREFERENCE_TWITTER_OAUTH_TOKEN_SECRET, accessToken.getTokenSecret());
					editor.putBoolean(ConstantValues.PREFERENCE_TWITTER_IS_LOGGED_IN, true);
					editor.commit();
					long userID = accessToken.getUserId();
					Preferences.setProfileName(context, twitter.showUser(accessToken.getUserId()).getName());
					Preferences.setFollowersCount(context, String.valueOf(twitter.showUser(accessToken.getUserId()).getFollowersCount()));
					Preferences.setprofileimageUrl(context, twitter.showUser(accessToken.getUserId()).getBiggerProfileImageURL());

					ResponseList<twitter4j.Status> status = twitter.getHomeTimeline();
					

					for(int i=0; i<status.size();i++){
						String timeposted = Utility.getDateDifference(status.get(i).getCreatedAt());
						
						FeedsModel ff = new FeedsModel();
						ff.setAuthorName(status.get(i).getUser().getName());
						ff.setFeedText(status.get(i).getText());
						ff.setPostedAt(timeposted);
						ff.setUserImageurl(status.get(i).getUser().getBiggerProfileImageURL());
						feeds_list.add(ff);

						Log.d("Name: ", "Name: "+status.get(i).getUser().getName()+" "+"Text: "+status.get(i).getText()+"Image Url"+status.get(i).getUser().getBiggerProfileImageURL());

						
						Log.d("POSTEDTIME","POSTEDTIME"+timeposted);
					}
					Log.d("Count", "Count"+TwitterUtil.getInstance().getTwitter().showUser(accessToken.getUserId()).getProfileBackgroundColor()+"Image Url"+TwitterUtil.getInstance().getTwitter().showUser(accessToken.getUserId()).getProfileBackgroundImageURL());
					Log.d("FEEDSLIST","FEEDSLIST"+feeds_list.size());

					
					
					Intent intent = new Intent(context, SocialMediaActivity.class);
					intent.putExtra("provider", "twitter");
					intent.putExtra("feed", (Serializable) feeds_list);
					context.startActivity(intent);
					return twitter.showUser(accessToken.getUserId()).getName();
				} catch (TwitterException e) {
					e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
				}
			} else {
				SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
				String accessTokenString = sharedPreferences.getString(ConstantValues.PREFERENCE_TWITTER_OAUTH_TOKEN, "");
				String accessTokenSecret = sharedPreferences.getString(ConstantValues.PREFERENCE_TWITTER_OAUTH_TOKEN_SECRET, "");
				AccessToken accessToken = new AccessToken(accessTokenString, accessTokenSecret);
				try {
					TwitterUtil.getInstance().setTwitterFactory(accessToken);

					// twitter4j.Status sd=  TwitterUtil.getInstance().getTwitter().showUser(accessToken.getUserId()).getStatus();


					Preferences.setProfileName(context, TwitterUtil.getInstance().getTwitter().showUser(accessToken.getUserId()).getName());
					Preferences.setFollowersCount(context, String.valueOf(TwitterUtil.getInstance().getTwitter().showUser(accessToken.getUserId()).getFollowersCount()));
					Preferences.setStatus(context,"Feeling Happy...!!!");
					Preferences.setprofileimageUrl(context, TwitterUtil.getInstance().getTwitter().showUser(accessToken.getUserId()).getProfileImageURL());
					Preferences.setprofilebackgroundimageUrl(context, TwitterUtil.getInstance().getTwitter().showUser(accessToken.getUserId()).getProfileBackgroundImageURL());
					
					/*ResponseList<DirectMessage> messg= TwitterUtil.getInstance().getTwitter().getDirectMessages();
					int count =0;
					for(int i=0; i<messg.size();i++){
						String timeposted = Utility.getDateDifference(messg.get(i).getCreatedAt());


						FeedsModel ff = new FeedsModel();
						ff.setMessgFrom( messg.get(i).getSender().getName());
						ff.setMessgText(messg.get(i).getText().toString());
						ff.setMessgDate(timeposted);
						messg_list.add(ff);
						Log.d("MESSF","MESSF"+messg_list.size());
						Log.d("POSTEDTIME","POSTEDTIME"+timeposted);
					}*/



					ResponseList<twitter4j.Status> status = TwitterUtil.getInstance().getTwitter().getHomeTimeline();

					for(int i=0; i<status.size();i++){
						String timeposted = Utility.getDateDifference(status.get(i).getCreatedAt());
						
						FeedsModel ff = new FeedsModel();
						ff.setAuthorName(status.get(i).getUser().getName());
						ff.setFeedText(status.get(i).getText());
						ff.setPostedAt(timeposted);
						ff.setUserImageurl(status.get(i).getUser().getBiggerProfileImageURL());
						feeds_list.add(ff);

						Log.d("Name: ", "Name: "+status.get(i).getUser().getName()+" "+"Text: "+status.get(i).getText()+"Image Url"+status.get(i).getUser().getBiggerProfileImageURL());

						
						Log.d("POSTEDTIME","POSTEDTIME"+timeposted);
					}
					Log.d("Count", "Count"+TwitterUtil.getInstance().getTwitter().showUser(accessToken.getUserId()).getProfileBackgroundColor()+"Image Url"+TwitterUtil.getInstance().getTwitter().showUser(accessToken.getUserId()).getProfileBackgroundImageURL());
					Log.d("FEEDSLIST","FEEDSLIST"+feeds_list.size());

					
					
					Intent intent = new Intent(context, SocialMediaActivity.class);
					intent.putExtra("provider", "twitter");
					intent.putExtra("feed", (Serializable) feeds_list);
					context.startActivity(intent);
					return TwitterUtil.getInstance().getTwitter().showUser(accessToken.getUserId()).getName();


				} catch (TwitterException e) {
					e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
				}
			}

			return null;  //To change body of implemented methods use File | Settings | File Templates.
		}


}
