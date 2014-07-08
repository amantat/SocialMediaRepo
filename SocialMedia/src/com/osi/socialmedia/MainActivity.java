package com.osi.socialmedia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.brickred.socialauth.Contact;
import org.brickred.socialauth.Feed;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.People.LoadPeopleResult;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;
import com.osi.socialmedia.adapter.MainGridAdapter;
import com.osi.socialmedia.model.FeedsModel;
import com.osi.socialmedia.model.GridBlock;
import com.osi.socialmedia.twitter.TwitterAuthentication;
import com.osi.socialmedia.twitter.TwitterGetAccessToken;
import com.osi.socialmedia.utils.ConnectionDetector;
import com.osi.socialmedia.utils.ConstantValues;
import com.osi.socialmedia.utils.Preferences;
import com.osi.socialmedia.utils.Utility;

public class MainActivity extends Activity implements ConnectionCallbacks,
OnConnectionFailedListener,ResultCallback<People.LoadPeopleResult> {

	private GridView gd;
	private MainGridAdapter adapter;
	private ArrayList<GridBlock> gridArray;
	private SharedPreferences sharedPreferences ;
	SocialAuthAdapter authadapter;
	private ArrayList<FeedsModel> feedsList;
	String providerName;
	Intent intent ;
	private ProgressDialog pd; 
	private ConnectionDetector connectionDetector;
	

	
	//googleplus
	private static final int RC_SIGN_IN = 0;
	private GoogleApiClient mGoogleApiClient;
	private boolean mIntentInProgress;
	private boolean mSignInClicked;
	private ConnectionResult mConnectionResult;
	//private boolean isSignedin=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.landing_screen);

		init();
	}

	private void init() {
		intent = new Intent(MainActivity.this, SocialMediaActivity.class);
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		authadapter = new SocialAuthAdapter(new ResponseListener());
		feedsList=new ArrayList<FeedsModel>();
		connectionDetector = new ConnectionDetector(MainActivity.this);

		
		gd = (GridView)findViewById(R.id.mainGridView);

		gridArray = new ArrayList<GridBlock>();

		GridBlock gd1 = new GridBlock("twitter_icon","Twitter");		
		gridArray.add(gd1);		
		GridBlock gd2 = new GridBlock("facebook_icon","Facebook");
		gridArray.add(gd2);
		GridBlock gd3 = new GridBlock("google_icon","Google+");
		gridArray.add(gd3);
		GridBlock gd4 = new GridBlock("linkedin_icon","Linkedin");
		gridArray.add(gd4);
		GridBlock gd5 = new GridBlock("foursquare_icon","Four Square");
		gridArray.add(gd5);
		GridBlock gd6 = new GridBlock("flickr","Flickr");
		gridArray.add(gd6);
		GridBlock gd7 = new GridBlock("runkeeper","Runkeeper");
		gridArray.add(gd7);
		GridBlock gd8 = new GridBlock("salesforce","Sales Force");
		gridArray.add(gd8);

		adapter = new MainGridAdapter(getApplicationContext(),gridArray);

		gd.setAdapter(adapter);
		goolgeonCreate();
		//mGoogleApiClient.connect();
		gd.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				
				if(connectionDetector.isConnectingToInternet()==true){

				switch(position){
				case 0:			
					intent.putExtra("provider", "twitter");

					if (!sharedPreferences.getBoolean(ConstantValues.PREFERENCE_TWITTER_IS_LOGGED_IN,false))
					{

						new TwitterAuthentication(MainActivity.this).execute();

					}else{

						new TwitterGetAccessToken(MainActivity.this).execute("");


					}

					break;
				case 1:
					intent.putExtra("provider", "facebook");
					authadapter.authorize(MainActivity.this, Provider.FACEBOOK);

					break;
				case 2:

					
					mGoogleApiClient.connect();
					Log.d("PRESSED", "PRESSED");
						signInWithGplus();
					
					//pd = ProgressDialog.show(MainActivity.this, "", "Loading...");
					intent.putExtra("provider", "googleplus");
					//}

					break;
				case 3:
					intent.putExtra("provider", "linkedin");
					pd = ProgressDialog.show(MainActivity.this, "", "Loading...");
					authadapter.authorize(MainActivity.this, Provider.LINKEDIN);
					break;
				case 4:
					intent.putExtra("provider", "foursquare");
					pd = ProgressDialog.show(MainActivity.this, "", "Loading...");
					authadapter.authorize(MainActivity.this, Provider.FOURSQUARE);

					break;
				case 5:
					intent.putExtra("provider", "flickr");
					pd = ProgressDialog.show(MainActivity.this, "", "Loading...");
					authadapter.authorize(MainActivity.this, Provider.FLICKR);
					break;
				case 6:
					intent.putExtra("provider", "runkeeper");
					pd = ProgressDialog.show(MainActivity.this, "", "Loading...");
					authadapter.authorize(MainActivity.this, Provider.RUNKEEPER);
					break;
				case 7:
					intent.putExtra("provider", "salesforce");
					pd = ProgressDialog.show(MainActivity.this, "", "Loading...");
					authadapter.authorize(MainActivity.this, Provider.SALESFORCE);
					break;

				}

				//Intent in = new Intent(MainActivity.this,HomeScreenActivity.class);
				//in.putExtra("selected",position);
				//startActivity(in);	            	
				}else{
					AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);

					adb.setTitle("Alert");

					adb.setMessage("Please try again when you are connected to internet");

					adb.setIcon(android.R.drawable.ic_dialog_alert);

					adb.setNeutralButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							
							dialog.dismiss();
						}
					});

					
					adb.show();
				}
			}
		});	


	}	

	private final class ResponseListener implements DialogListener {
		@Override
		public void onComplete(Bundle values) {

			Log.d("Social App", "Authentication Successful");

			// Get name of provider after authentication
			providerName = values.getString(SocialAuthAdapter.PROVIDER);
			Log.d("PROVIDER", "PROVIDER"+providerName);
			//intent.putExtra("provider", providerName);
			if(providerName.equalsIgnoreCase("facebook")){

				Preferences.setfacebookloggedin(getApplicationContext(), true);

			}
			if(providerName.equalsIgnoreCase("linkedin")){

				Preferences.setlinkedinloggedin(getApplicationContext(), true);

			}
			Log.d("Social App", "Provider Name = " + providerName);
			//Toast.makeText(HomeScreenActivity.this, providerName + " connected", Toast.LENGTH_LONG).show();


			authadapter.getUserProfileAsync(new ProfileDataListener());
			authadapter.getContactListAsync(new ContactDataListener());
			authadapter.getFeedsAsync(new FeedDataListener());
		}

		@Override
		public void onError(SocialAuthError error) {
			Log.d("Social App", "Authentication Error: " + error.getMessage());
			if(pd != null && pd.isShowing()) {
				pd.dismiss();

			}
		}

		@Override
		public void onCancel() {
			Log.d("Social App", "Authentication Cancelled");
			if(pd != null && pd.isShowing()) {
				pd.dismiss();

			}
		}

		@Override
		public void onBack() {
			Log.d("Social App", "Dialog Closed by pressing Back Key");
			if(pd != null && pd.isShowing()) {
				pd.dismiss();

			}
		}

	}



	private final class ProfileDataListener implements SocialAuthListener<Profile>{

		@Override
		public void onError(SocialAuthError arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onExecute(String arg0, Profile t) {
			// TODO Auto-generated method stub

			Log.d("Social App", "Receiving Data");
			
			Profile profileMap = t;
			Log.d("Social App",  "Validate ID         = " + profileMap.getProfileImageURL()+"EMAIL"+profileMap.getEmail());
			Log.d("Social App",  "First Name          = " + profileMap.getFirstName()+" D"+profileMap.getDisplayName());


			intent.putExtra("profile", profileMap);

			/*//startActivity(intent);
			String fpersonnamme=profileMap.getFirstName()+" "+profileMap.getLastName();
			if(providerName.equalsIgnoreCase("facebook")){
				//Preferences.setfProfileName(getApplicationContext(), fpersonnamme);
			}else if(providerName.equalsIgnoreCase("linkedin")){
				Preferences.setlProfileName(getApplicationContext(),fpersonnamme);
				//Intent in = new Intent(MainActivity.this,HomeScreenActivity.class);
				//in.putExtra("selected",position);
				//startActivity(in);
			}*/
			//childItemData();
		}
	}

	private final class FeedDataListener implements SocialAuthListener<List<Feed>> {

		@Override
		public void onExecute(String provider, List<Feed> t) {

			Log.d("Social App", "Receiving Data");
			//mDialog.dismiss();
			List<Feed> feedList = t;



			if (feedList != null && feedList.size() > 0) {

				for (Feed f : feedList) {

					FeedsModel feeds= new FeedsModel();
					feeds.setAuthorName(f.getFrom());
					feeds.setFeedText(f.getMessage());
					String timeposted= Utility.getDateDifference(f.getCreatedAt());

					feeds.setPostedAt(timeposted);

					feedsList.add(feeds);
					//Intent intent = new Intent(MainActivity.this, SocialMediaActivity.class);
					intent.putExtra("feed", (Serializable) feedsList);


				}

				startActivity(intent);
				if(pd != null && pd.isShowing()) {
					pd.dismiss();

				}
			}  else{
				Log.d("Social App", "Feedlist List Empty");

				startActivity(intent);
				if(pd != null && pd.isShowing()) {
					pd.dismiss();

				}
			}
		}

		@Override
		public void onError(SocialAuthError e) {
		}
	}


	private final class ContactDataListener implements SocialAuthListener<List<Contact>>{

		@Override
		public void onExecute(String provider, List<Contact> t) {

			Log.d("Social App", "Receiving DataC");
			List<Contact> contactsList = t;


			if (contactsList != null && contactsList.size() > 0) {
				Log.d("ContactListSize", "ContactListSize"+contactsList.size());
				if(providerName.equalsIgnoreCase("facebook")){
					Preferences.setfFollowersCount(getApplicationContext(), "295");
				}else if(providerName.equalsIgnoreCase("linkedin")){
					Preferences.setlFollowersCount(getApplicationContext(), String.valueOf(contactsList.size()));
				}

				intent.putExtra("contact", contactsList.size());

			} else {
				Log.d("Social App", "Contact List Empty");
			}
		}

		@Override
		public void onError(SocialAuthError e) {

		}
	}

	//googleplusCode

	private void goolgeonCreate(){

		mGoogleApiClient = new GoogleApiClient.Builder(this)
		.addScope(Plus.SCOPE_PLUS_LOGIN)
		.addConnectionCallbacks(this)
		.addOnConnectionFailedListener(this).addApi(Plus.API)
		.build();

	}

	private void signInWithGplus() {
		if (!mGoogleApiClient.isConnecting()) {
			mSignInClicked = true;
			resolveSignInError();
		}
	}
	@Override
	public void onResult(LoadPeopleResult peopleData) {
		if (peopleData.getStatus().getStatusCode() == CommonStatusCodes.SUCCESS) {
			PersonBuffer personBuffer = peopleData.getPersonBuffer();


			try {
				int count = personBuffer.getCount();
				Preferences.setgFollowersCount(getApplicationContext(), String.valueOf(count));

				startActivity(intent);

			} finally {
				personBuffer.close();
			}
		} else {
			Log.e("", "Error requesting visible circles: " + peopleData.getStatus());
		}
	}

	protected void onStart() {
		super.onStart();
		//mGoogleApiClient.connect();
	}

	

	protected void onStop() {
		super.onStop();
		if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();

		}
	}


	private void resolveSignInError() {
		if (mConnectionResult.hasResolution()) {
			try {
				mIntentInProgress = true;
				mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
			} catch (SendIntentException e) {
				mIntentInProgress = false;
				mGoogleApiClient.connect();
			}
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (!result.hasResolution()) {
			GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
					0).show();
			return;
		}

		if (!mIntentInProgress) {
			// Store the ConnectionResult for later usage
			mConnectionResult = result;

			if (mSignInClicked) {
				// The user has already clicked 'sign-in' so we attempt to
				// resolve all
				// errors until the user is signed in, or they cancel.
				resolveSignInError();
			} 
		} 

	} 

	@Override
	protected void onActivityResult(int requestCode, int responseCode,
			Intent intent) {
		if (requestCode == RC_SIGN_IN) {
			if (responseCode != RESULT_OK) {
				mSignInClicked = false;
			}

			mIntentInProgress = false;

			if (!mGoogleApiClient.isConnecting()) {
				mGoogleApiClient.connect();
			}
		}
	}

	@Override
	public void onConnected(Bundle arg0) {

		Plus.PeopleApi.loadVisible(mGoogleApiClient, null)
		.setResultCallback(this);

		mSignInClicked = false;
		//isSignedin=true;
		
		Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();


		// Get user's information
		getProfileInformation();




	}

	private void getProfileInformation() {
		try {
			if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {




				Person currentPerson = Plus.PeopleApi
						.getCurrentPerson(mGoogleApiClient);


				Preferences.setgProfileName(getApplicationContext(), currentPerson.getDisplayName());
				String personPhotoUrl = currentPerson.getImage().getUrl();
				String personGooglePlusProfile = currentPerson.getUrl();
				String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
				Preferences.setgemail(getApplicationContext(), email);
				Preferences.setgprofileimage(getApplicationContext(), personPhotoUrl);
				currentPerson.getCircledByCount();

				Log.e("", "Name: " + currentPerson.getDisplayName() + ", plusProfile: "
						+ personGooglePlusProfile + ", email: " + email
						+ ", Image: " + personPhotoUrl);



			} else {
				Toast.makeText(getApplicationContext(),
						"Person information is null", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		mGoogleApiClient.connect();
	}


}