package DroidSocial.Facebook;

import DroidSocial.Events.Facebook.*;
import DroidSocial.Network.SocialNetwork;
import DroidSocial.Network.SocialNetworkRequest;
import DroidSocial.Network.SocialNetworkResponseListener;

import java.util.*;

/**
 * 
 * @author Joey
 *
 */
public class Facebook extends SocialNetwork {

	private String FB_Secret = null;
	private String AuthTokenVal = "";
	private String AuthTokenKey = "";	
	private FacebookRequest Request = null;
	
	public Facebook(String api_key, String secret) {
		// Handle the under the hood stuff
		super("Facebook", "api_key", api_key);
		
		// set the request object to the facebook specific request object
		this.Request = new FacebookRequest(this, "http://api.facebook.com/restserver.php");
		
		this.ResponseListeners.put("UnsupportedResponse", new FacebookUnsupportedResponse());
	}
	
	/**
	 * Set the default methods that the response object will look for when parsing XML responses from Facebook.
	 * If you want to add a new event and listener, they must be added to the fbSaxParser class.
	 * 
	 */
	public void SetDefaultResponseListeners() {
		this.ResponseListeners.put("auth_createToken_response", new AuthCreateTokenResponse());
	}
	/**
	 * Log a user in
	 * @param uid
	 * @param password
	 * @return
	 */
	public boolean LogIn(String uid, String password) {
		Map<String, String> params = new HashMap<String, String>();
		this.Request.FBGetRequest(params);
		return false;
	}
	
 }
