package DroidSocial.Network;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import DroidSocial.Network.*;
import DroidSocial.Facebook.*;

import java.util.*;
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
 * @author Joey
 *
 */
public class main extends Activity {
    
	private static final String fb_api_key = "aaf81111730a8fa14eeabee5d18c3f91";
	
	private static final String fb_secret = "f90ab6a2de28f13db2c9445e6300f809";
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);
        
        Facebook fb = new Facebook(fb_api_key, fb_secret);
        fb.LogIn("AS", "as");
    }
}