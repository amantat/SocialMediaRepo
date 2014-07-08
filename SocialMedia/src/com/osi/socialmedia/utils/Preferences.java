package com.osi.socialmedia.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Preferences {


	public static final String PREFERENCES_NAME = "SalesApp_Android";
	
	public static String getProfileName(Context context) {
		return getSharedPreference(context).getString("profilename","");
	}

	public static void setProfileName(Context context,String profilename) {
		Editor edit = getSharedPreference(context).edit();
		edit.putString("profilename", profilename);
		edit.commit();
	}

	public static String getFollowersCount(Context context) {
		return getSharedPreference(context).getString("followerscount","");
	}

	public static void setFollowersCount(Context context,String followerscount) {
		Editor edit = getSharedPreference(context).edit();
		edit.putString("followerscount", followerscount);
		edit.commit();
	}


	public static String getprofileimageUrl(Context context) {
		return getSharedPreference(context).getString("profileimage","");
	}

	public static void setprofileimageUrl(Context context,String profileimage) {
		Editor edit = getSharedPreference(context).edit();
		edit.putString("profileimage", profileimage);
		edit.commit();
	}

	public static String getprofilebackgroundimageUrl(Context context) {
		return getSharedPreference(context).getString("profilebackgroundimage","");
	}

	public static void setprofilebackgroundimageUrl(Context context,String profilebackgroundimage) {
		Editor edit = getSharedPreference(context).edit();
		edit.putString("profilebackgroundimage", profilebackgroundimage);
		edit.commit();
	}

	public static String getfProfileName(Context context) {
		return getSharedPreference(context).getString("fprofilename","");
	}

	public static void setfProfileName(Context context,String profilename) {
		Editor edit = getSharedPreference(context).edit();
		edit.putString("fprofilename", profilename);
		edit.commit();
	}

	public static String getfFollowersCount(Context context) {
		return getSharedPreference(context).getString("ffollowerscount","");
	}

	public static void setfFollowersCount(Context context,String followerscount) {
		Editor edit = getSharedPreference(context).edit();
		edit.putString("ffollowerscount", followerscount);
		edit.commit();
	}
	public static String getlProfileName(Context context) {
		return getSharedPreference(context).getString("lprofilename","");
	}

	public static void setlProfileName(Context context,String profilename) {
		Editor edit = getSharedPreference(context).edit();
		edit.putString("lprofilename", profilename);
		edit.commit();
	}

	public static String getlFollowersCount(Context context) {
		return getSharedPreference(context).getString("lfollowerscount","");
	}

	public static void setlFollowersCount(Context context,String followerscount) {
		Editor edit = getSharedPreference(context).edit();
		edit.putString("lfollowerscount", followerscount);
		edit.commit();
	}

	public static String getgProfileName(Context context) {
		return getSharedPreference(context).getString("gprofilename","");
	}

	public static void setgProfileName(Context context,String profilename) {
		Editor edit = getSharedPreference(context).edit();
		edit.putString("gprofilename", profilename);
		edit.commit();
	}

	public static String getgFollowersCount(Context context) {
		return getSharedPreference(context).getString("gfollowerscount","");
	}

	public static void setgFollowersCount(Context context,String followerscount) {
		Editor edit = getSharedPreference(context).edit();
		edit.putString("gfollowerscount", followerscount);
		edit.commit();
	}

	public static String getMessage(Context context) {
		return getSharedPreference(context).getString("message","");
	}

	public static void setMessage(Context context,String message) {
		Editor edit = getSharedPreference(context).edit();
		edit.putString("message", message);
		edit.commit();
	}

	public static String getMessageFrom(Context context) {
		return getSharedPreference(context).getString("messagefrom","");
	}

	public static void setMessageFrom(Context context,String messagefrom) {
		Editor edit = getSharedPreference(context).edit();
		edit.putString("messagefrom",messagefrom);
		edit.commit();
	}
	public static String getStatus(Context context) {
		return getSharedPreference(context).getString("status","");
	}

	public static void setStatus(Context context,String status) {
		Editor edit = getSharedPreference(context).edit();
		edit.putString("status",status);
		edit.commit();
	}


	public static String getgprofileimage(Context context) {
		return getSharedPreference(context).getString("gprofileimage","");
	}

	public static void setgprofileimage(Context context,String gprofileimage) {
		Editor edit = getSharedPreference(context).edit();
		edit.putString("gprofileimage",gprofileimage);
		edit.commit();
	}
	public static String getgemail(Context context) {
		return getSharedPreference(context).getString("gemail","");
	}

	public static void setgemail(Context context,String gemail) {
		Editor edit = getSharedPreference(context).edit();
		edit.putString("gemail",gemail);
		edit.commit();
	}

	//booleans
	public static Boolean getfacebookloggedin(Context context) {
		return getSharedPreference(context).getBoolean("flogin", false);
	}

	public static void setfacebookloggedin(Context context,Boolean login) {
		Editor edit = getSharedPreference(context).edit();
		edit.putBoolean("flogin",login);
		edit.commit();
	}

	public static Boolean getlinkedinloggedin(Context context) {
		return getSharedPreference(context).getBoolean("llogin", false);
	}

	public static void setlinkedinloggedin(Context context,Boolean login) {
		Editor edit = getSharedPreference(context).edit();
		edit.putBoolean("llogin",login);
		edit.commit();
	}


	public static long getlastseendate(Context context) {
		return getSharedPreference(context).getLong("llogin", 0);
	}

	public static void setlastseendate(Context context,long login) {
		Editor edit = getSharedPreference(context).edit();
		edit.putLong("llogin",login);
		edit.commit();
	}



	//shared preferences
	public static SharedPreferences getSharedPreference(Context context) {
		return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
	}

}	
