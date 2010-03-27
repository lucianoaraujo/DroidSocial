package DroidSocial.Facebook;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class FBWebViewLogin extends Activity {
	
	private WebView fb_login_view = null;
	/**
	 * @Opens a web view allowing the user to log into their facebook account
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String url = "http://www.facebook.com/login.php";
		
		// Create the view and set it on the screen
		fb_login_view = new WebView(this);
		this.setContentView(fb_login_view);
		
		this.fb_login_view.loadUrl(url);
	}
}
