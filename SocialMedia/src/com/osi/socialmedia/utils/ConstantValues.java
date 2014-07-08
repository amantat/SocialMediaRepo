package com.osi.socialmedia.utils;

/**
 * Created with IntelliJ IDEA.
 * User: ServusKevin
 * Date: 5/2/13
 * Time: 8:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConstantValues {
	
	//twitter
    public static String TWITTER_CONSUMER_KEY = "QTgUhccSBrMs4oTSPv3tsVIxn";
    public static String TWITTER_CONSUMER_SECRET = "FZ8eR7owDQqIQbIs3hAxI2VPpRCTf39wcZjvglcUABmr28kEFw";
    public static String TWITTER_CALLBACK_URL = "oauth://com.osi.socialmedia";
    public static String URL_PARAMETER_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
    public static String PREFERENCE_TWITTER_OAUTH_TOKEN="TWITTER_OAUTH_TOKEN";
    public static String PREFERENCE_TWITTER_OAUTH_TOKEN_SECRET="TWITTER_OAUTH_TOKEN_SECRET";
    public static String PREFERENCE_TWITTER_IS_LOGGED_IN="TWITTER_IS_LOGGED_IN";
    public static String STRING_EXTRA_AUTHENCATION_URL = "AuthencationUrl";
    
    
    //linkedin
    public static String LINKEDIN_CONSUMER_KEY = "75lkav8yym7y7t";
	public static String LINKEDIN_CONSUMER_SECRET = "NmMeVUteifB72Ei4";
	public static String scopeParams = "rw_nus+r_basicprofile";
	public static String OAUTH_CALLBACK_SCHEME = "x-oauthflow-linkedin";
	public static String OAUTH_CALLBACK_HOST = "callback";
	public static String OAUTH_CALLBACK_URL = OAUTH_CALLBACK_SCHEME + "://" + OAUTH_CALLBACK_HOST;
    
    
    
}
