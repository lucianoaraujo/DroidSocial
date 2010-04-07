package DroidSocial.Network;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import DroidSocial.Network.*;
import DroidSocial.Network.R.layout;
import DroidSocial.Network.R.id;
import DroidSocial.Facebook.*;
import DroidSocial.Twitter.*;
/**
 * 
 * FACEBOOK
 * 
	Application Id: 327301107325
	API Key: aaf81111730a8fa14eeabee5d18c3f91
	Secret: f90ab6a2de28f13db2c9445e6300f809
	
	Documentation on the RESTful API:  http://wiki.developers.facebook.com/index.php/API
	API URL: http://api.facebook.com/restserver.php
	
	sample call:  http://api.facebook.com/restserver.php?method=Auth.createToken&api_key=aaf81111730a8fa14eeabee5d18c3f91&sig=
	
	
	New Application Credentials
	api key: e9c48bbb18c3887c7c8cabd46c66dd9b
	secret:  386b44cee55d8fdfef23a77e52ea524c
	
	
	
 * @author Joey
 *
 */
public class main extends Activity {
    
	private static final String fb_api_key = "e9c48bbb18c3887c7c8cabd46c66dd9b";	
	private static final String fb_secret = "386b44cee55d8fdfef23a77e52ea524c";
	
	private TextView apiKeyView;
	private TextView secretView;
	private TextView out;
	
	// Twitter Test Values
	private static final String tw_username = "dalooch";
	private static final String tw_password = "1234567890";
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);
    
        /* Facebook test run * /
        this.apiKeyView = (TextView) this.findViewById(R.id.api_key);
        this.secretView = (TextView) this.findViewById(R.id.secret);
        this.out = (TextView) this.findViewById(R.id.output);
        
        this.apiKeyView.setText("API KEY: " + fb_api_key);
        this.secretView.setText("Secret: " + fb_secret);
      
       
        Facebook fb = new Facebook(fb_api_key, fb_secret);
        fb.SetDefaultResponseHandlers();
        String auth = "";
        try {
        	fb.LogIn(null, this, fblogin.class);
        }
        catch (Exception e) {
        	String s = e.getMessage();
        }
        this.out.setText(auth);
        /**/
        
        /* Twitter test run */
        this.out = (TextView) this.findViewById(R.id.output);
        
        Twitter tw = new Twitter( tw_username, tw_password );
        
        String auth = "";
        try {
        	auth = tw.accountVerifyCredentials();
        }
        catch (Exception e) {
        	String s = e.getMessage();
        }
        this.out.setText(auth);
        /**/
       
    }
	public void onResume() {
		super.onResume();
		this.out.setText("back");
	}
}