package DroidSocial.Facebook;

import android.app.Activity;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.*;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsoluteLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

public class FBWebViewLogin extends Activity {
	
	private WebView fb_login_view = null;
	private RelativeLayout LoginScreen;
	private ViewGroup.LayoutParams LoginScreenParams = new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT, 250);
	
	/**
	 * @Opens a web view allowing the user to log into their facebook account
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// get the bundle
		Bundle extras = this.getIntent().getExtras();
		
		// get the query string
		String queryStr = extras.getString(Facebook.FB_LOGIN_PARAMS);
		
		String url = "http://www.facebook.com/login.php?";
		
		if (queryStr != null) url += queryStr;
		
		// Create the objects we need			
		this.fb_login_view = new WebView(this);
		this.LoginScreen = new RelativeLayout(this);
		this.setContentView(this.LoginScreen);
		
		// Set the web view's layout params and the client to intercept url events
		this.fb_login_view.setLayoutParams(this.LoginScreenParams);
		this.fb_login_view.setWebViewClient(new FBLoginWebClient());
		
		// Add the web view to the screen
		this.LoginScreen.addView(this.fb_login_view);
		this.fb_login_view.loadUrl(url);
		
	}
	
	private class FBLoginWebClient extends WebViewClient {
		
		public FBLoginWebClient() {super();}
		
		@Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        view.loadUrl(url);
	        return true;
	    }
	}
}