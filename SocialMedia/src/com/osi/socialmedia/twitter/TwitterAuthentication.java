package com.osi.socialmedia.twitter;

import twitter4j.auth.RequestToken;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.osi.socialmedia.R;
import com.osi.socialmedia.SocialMediaActivity;
import com.osi.socialmedia.utils.ConstantValues;

public class TwitterAuthentication extends AsyncTask<String, String, RequestToken> {

private Context context;
private Dialog twitterdidalog;
private WebView webView;
public String authenticationUrl;
private boolean istwitterlogedin=false;  
private LayoutInflater inflater;
private ProgressDialog pd; 

	public TwitterAuthentication(Context context)
	{
		this.context = context;
		inflater = LayoutInflater.from(context);
	}
	
	
	
	@Override
    protected void onPostExecute(RequestToken requestToken) {
        if (requestToken!=null)
        {
            
          
            	authenticationUrl=requestToken.getAuthorizationURL();
            	
            	twitterDialog();
                
            
        }
    }

    @Override
    protected RequestToken doInBackground(String... params) {
        return TwitterUtil.getInstance().getRequestToken();
    }
    
    public void twitterDialog(){
		twitterdidalog = new Dialog(context);
		View vi =inflater.inflate(R.layout.twitter_dialog, null);
		twitterdidalog.setTitle("Login Twitter");
		twitterdidalog.setContentView(vi);
		webView = (WebView)vi.findViewById(R.id.webViewOAuth);

		webView.loadUrl(authenticationUrl);
		webView.setWebViewClient(new WebViewClient()
		{
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (url.contains("oauth_verifier="))
				{
					
					Intent intent = new Intent(context, SocialMediaActivity.class);
					intent.setData( Uri.parse(url));
					intent.putExtra("provider", "twitter");
					context.startActivity(intent);
					
					
				}
				view.loadUrl(url);

				return true;
			}


		});
		WebSettings webSettings= webView.getSettings();
		webSettings.setJavaScriptEnabled(true);

		twitterdidalog.show();


	}
}
