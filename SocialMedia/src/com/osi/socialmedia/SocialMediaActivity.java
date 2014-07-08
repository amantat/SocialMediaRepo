package com.osi.socialmedia;

import java.util.ArrayList;

import org.brickred.socialauth.Profile;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.plus.PlusShare;
import com.hintdesk.core.util.StringUtil;
import com.osi.socialmedia.adapter.FeedsAdapter;
import com.osi.socialmedia.listeners.MessageListner;
import com.osi.socialmedia.listeners.ResponseListner;
import com.osi.socialmedia.model.FeedsModel;
import com.osi.socialmedia.twitter.TwitterGetAccessToken;
import com.osi.socialmedia.twitter.TwitterUpdateStatus;
import com.osi.socialmedia.twitter.TwitterUtil;
import com.osi.socialmedia.utils.ConstantValues;
import com.osi.socialmedia.utils.ImageLoader;
import com.osi.socialmedia.utils.Preferences;

public class SocialMediaActivity extends Activity {

	private Button post_btn,signout_btn;
	private ArrayList<FeedsModel> list;
	private ListView listview;
	private FeedsAdapter feedsadp;
	private TextView display_name_tv,followers_count,email_tv,provider_name_tv;
	private String provider_name;
	private Profile profileMap;
	private Dialog dd;
	private EditText status_edt;
	private SocialAuthAdapter authadapter;
	private int contactlist_count;
	private ImageView profile_image;
	private ImageLoader imgloader;
	private LinearLayout main_layout;
	

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.socialmedia_mainview);
		Init();
		list = (ArrayList<FeedsModel>) getIntent().getSerializableExtra("feed");
		profileMap = (Profile) getIntent().getSerializableExtra("profile");

		Uri uri = getIntent().getData();
		if (uri != null && uri.toString().startsWith(ConstantValues.TWITTER_CALLBACK_URL)) {
			String verifier = uri.getQueryParameter(ConstantValues.URL_PARAMETER_TWITTER_OAUTH_VERIFIER);
			new TwitterGetAccessToken(SocialMediaActivity.this).execute(verifier);
		}
		provider_name = getIntent().getStringExtra("provider");
		contactlist_count = getIntent().getIntExtra("contact", 10);
		Log.d("Name", "Name"+Preferences.getProfileName(getApplicationContext()));

		provider_name_tv.setText(provider_name);

		if(list!=null && list.size()>0){
			feedsadp= new FeedsAdapter(list,provider_name, SocialMediaActivity.this);
			listview.setAdapter(feedsadp);
		}
		if(provider_name!=null){
			if(provider_name.equalsIgnoreCase("linkedin")){
				main_layout.setBackgroundResource(R.drawable.customborder_linkedin);
				display_name_tv.setText(profileMap.getFirstName()+" "+profileMap.getLastName());
				followers_count.setText(Preferences.getlFollowersCount(getApplicationContext()));
				imgloader.DisplayImage(profileMap.getProfileImageURL(), profile_image);
				email_tv.setText(profileMap.getEmail());

			}else if(provider_name.equalsIgnoreCase("Facebook")){
				main_layout.setBackgroundResource(R.drawable.customborder_facebook);
				display_name_tv.setText(profileMap.getFirstName()+" "+profileMap.getLastName());
				followers_count.setText(Preferences.getlFollowersCount(getApplicationContext()));
				profile_image.setBackgroundResource(R.drawable.facebook_icon);
				email_tv.setText(profileMap.getEmail());
			}
			else if( provider_name.equalsIgnoreCase("instagram")|| provider_name.equalsIgnoreCase("salesforce")){

				display_name_tv.setText(profileMap.getDisplayName());
				followers_count.setText(String.valueOf(contactlist_count));
				email_tv.setText(profileMap.getEmail());
				profile_image.setBackgroundResource(R.drawable.salesforce);
			}else if(provider_name.equalsIgnoreCase("twitter")){

				display_name_tv.setText(Preferences.getProfileName(getApplicationContext()));
				followers_count.setText(Preferences.getFollowersCount(getApplicationContext()));
				//imgloader.DisplayImage(Preferences.getprofilebackgroundimageUrl(getApplicationContext()), background_image);
				imgloader.DisplayImage(Preferences.getprofileimageUrl(getApplicationContext()), profile_image);
				email_tv.setVisibility(View.GONE);

			}else if(provider_name.equalsIgnoreCase("googleplus")){
				main_layout.setBackgroundResource(R.drawable.customborder_googleplus);
				display_name_tv.setText(Preferences.getgProfileName(getApplicationContext()));
				followers_count.setText(Preferences.getgFollowersCount(getApplicationContext()));
				email_tv.setText(Preferences.getgemail(getApplicationContext()));
				imgloader.DisplayImage(Preferences.getgprofileimage(getApplicationContext()), profile_image);

			}else if(provider_name.equalsIgnoreCase("runkeeper")){
				main_layout.setBackgroundResource(R.drawable.customborder_runkeeper);
				display_name_tv.setText(profileMap.getFirstName());
				followers_count.setText(Preferences.getgFollowersCount(getApplicationContext()));
				email_tv.setText(profileMap.getLocation());
				imgloader.DisplayImage(profileMap.getProfileImageURL(), profile_image);
				
			}else if(provider_name.equalsIgnoreCase("flickr")){
				main_layout.setBackgroundResource(R.drawable.customborder_flickr);
				display_name_tv.setText(profileMap.getDisplayName());
				followers_count.setText(Preferences.getgFollowersCount(getApplicationContext()));
				email_tv.setText(profileMap.getCountry());
				imgloader.DisplayImage(profileMap.getProfileImageURL(), profile_image);
				
			}else if(provider_name.equalsIgnoreCase("foursquare")){
				main_layout.setBackgroundResource(R.drawable.customborder_foursquare);
				display_name_tv.setText(profileMap.getFirstName()+" "+profileMap.getLastName());
				followers_count.setText(String.valueOf(contactlist_count));
				email_tv.setText(profileMap.getLocation());
				profile_image.setBackgroundResource(R.drawable.foursquare_icon);
			}
		}


		post_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				postStatusdilaog(provider_name);

			}
		});

		signout_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub																																										
				
				if(provider_name.equalsIgnoreCase("")){
					
				}else if(provider_name.equalsIgnoreCase("twitter")){
					 SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			            SharedPreferences.Editor editor = sharedPreferences.edit();
			            editor.putString(ConstantValues.PREFERENCE_TWITTER_OAUTH_TOKEN, "");
			            editor.putString(ConstantValues.PREFERENCE_TWITTER_OAUTH_TOKEN_SECRET, "");
			            editor.putBoolean(ConstantValues.PREFERENCE_TWITTER_IS_LOGGED_IN, false);
			            editor.commit();
			            TwitterUtil.getInstance().reset();
			          //  Intent intent = new Intent(SocialMediaActivity.this, MainActivity.class);
			         //   startActivity(intent);

				}else if(provider_name.equalsIgnoreCase("facebook")){

					authadapter.authorize(getApplicationContext(), Provider.FACEBOOK);

				}else if(provider_name.equalsIgnoreCase("Google+")){

				}else if(provider_name.equalsIgnoreCase("linkedin")){

					authadapter.authorize(getApplicationContext(), Provider.LINKEDIN);

				}else if(provider_name.equalsIgnoreCase("foursquare")){

					authadapter.authorize(getApplicationContext(), Provider.FOURSQUARE);

				}else if(provider_name.equalsIgnoreCase("flickr")){

					authadapter.authorize(getApplicationContext(), Provider.FLICKR);

				}else if(provider_name.equalsIgnoreCase("salesforce")){

					authadapter.authorize(getApplicationContext(), Provider.SALESFORCE);

				}else if(provider_name.equalsIgnoreCase("runkeeper")){

					authadapter.authorize(getApplicationContext(), Provider.RUNKEEPER);

				}


				signout(provider_name);

			}
		});

	}


	private void Init(){
		
		main_layout=(LinearLayout)findViewById(R.id.main_layout);
		
		listview = (ListView) findViewById(R.id.list_iv);
		display_name_tv=(TextView)findViewById(R.id.display_name_tv);
		email_tv=(TextView)findViewById(R.id.display_email_tv);
		followers_count=(TextView)findViewById(R.id.follower_count_tv);
		post_btn=(Button)findViewById(R.id.post_btn);
		profile_image=(ImageView)findViewById(R.id.profile_image);
		signout_btn=(Button)findViewById(R.id.signout_btn);
		provider_name_tv=(TextView)findViewById(R.id.provider_name_tv);
		
		
		imgloader= new ImageLoader(SocialMediaActivity.this);
		authadapter = new SocialAuthAdapter(new ResponseListner(SocialMediaActivity.this));
	}

	private void postStatusdilaog(final String provider){


		dd = new Dialog(SocialMediaActivity.this);

		dd.setContentView(R.layout.poststatus_dialog);
		status_edt = (EditText)dd.findViewById(R.id.status_edt);
		//notes_edt.setHeight(280);
		status_edt.setHint("Text");
		Button ok = (Button)dd.findViewById(R.id.ok);
		Button cancel = (Button)dd.findViewById(R.id.cancel);

		dd.setTitle("Post");



		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String status = status_edt.getText().toString();
				if (!StringUtil.isNullOrWhitespace(status)) {
					if(provider!=null){
						if(provider.equalsIgnoreCase("")){

						}else if(provider.equalsIgnoreCase("twitter")){

							new TwitterUpdateStatus(SocialMediaActivity.this).execute(status);

						}else if(provider.equalsIgnoreCase("facebook")){

							authadapter.authorize(getApplicationContext(), Provider.FACEBOOK);
							authadapter.updateStatus(status, new MessageListner(SocialMediaActivity.this), true);

						}else if(provider.equalsIgnoreCase("googleplus")){

							Intent shareIntent = new PlusShare.Builder(SocialMediaActivity.this)
							.setType("text/plain")
							.setText(status)
							.setContentUrl(Uri.parse("https://developers.google.com/+/"))
							.getIntent();

							startActivityForResult(shareIntent, 0);


						}else if(provider.equalsIgnoreCase("linkedin")){

							authadapter.authorize(getApplicationContext(), Provider.LINKEDIN);
							authadapter.updateStatus(status, new MessageListner(SocialMediaActivity.this), true);

						}else if(provider.equalsIgnoreCase("foursquare")){

							authadapter.authorize(getApplicationContext(), Provider.FOURSQUARE);
							authadapter.updateStatus(status, new MessageListner(SocialMediaActivity.this), true);

						}else if(provider.equalsIgnoreCase("Flickr")){

							authadapter.authorize(getApplicationContext(), Provider.FLICKR);
							authadapter.updateStatus(status, new MessageListner(SocialMediaActivity.this), true);

						}

					}
				} else {
					Toast.makeText(getApplicationContext(), "Please enter a status", Toast.LENGTH_SHORT).show();
				}


				dd.cancel();

			}
		});

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


				dd.cancel();
			}
		});

		dd.show();
	}


	private void signout(final String providername){


		AlertDialog.Builder adb = new AlertDialog.Builder(SocialMediaActivity.this);

		adb.setTitle("Alert");

		adb.setMessage("Are you sure you want to signout"+" "+providername);

		adb.setIcon(android.R.drawable.ic_dialog_alert);

		adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				if(providername.equalsIgnoreCase("twitter")|| providername.equalsIgnoreCase("googleplus")){
				}else{
				authadapter.signOut(getApplicationContext(), providername);
				}
				Intent inte= new Intent(SocialMediaActivity.this,MainActivity.class);
				startActivity(inte);
				SocialMediaActivity.this.finish();

			} 	
		});

		adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});	    
		adb.show();
	}

}
