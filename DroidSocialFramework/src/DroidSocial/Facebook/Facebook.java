package DroidSocial.Facebook;

import DroidSocial.Events.Facebook.*;
import DroidSocial.Network.SocialNetwork;
import DroidSocial.Network.SocialNetworkRequest;
import DroidSocial.Network.SocialNetworkResponseHandler;

import java.net.URLEncoder;
import java.util.*;

/**
 * 
 * @author Joey
 *
 */
public class Facebook extends SocialNetwork {

	public static final String uri_Method_key = "method";
	public static final String uri_Method_Auth_createToken = "auth.createtoken";
	public static final String uri_Sig_key = "sig";
	public static final String uri_Version_key = "v";
	public static final String uri_Version_val = "1.0";
	
	public static final String uri_ApiKey_key = "api_key";
	public static final String uri_Secret_key = "secret";
	
	private String Secret = null;
	private String AuthTokenVal = "";
	private String AuthTokenKey = "";
	private String API_KEY = "";
	
	private FacebookRequest Request = null;
	
	public Facebook(String api_key, String secret) {
		// Handle the under the hood stuff
		super("Facebook", "api_key", api_key);
		this.API_KEY = api_key;
		this.Secret = secret;
		// set the request object to the facebook specific request object
		this.Request = new FacebookRequest(this, "http://api.facebook.com/restserver.php", secret, api_key);
		
		this.ResponseHandlers.put("UnsupportedResponse", FacebookUnsupportedResponse.class.getName());
	}
	
	/**
	 * Set the default methods that the response object will look for when parsing XML responses from Facebook.
	 * If you want to add a new event and handler, they must be added to the fbSaxParser class.
	 */
	public void SetDefaultResponseHandlers() {
		this.ResponseHandlers.put("auth_createToken_response", AuthCreateTokenResponse.class.getName());
		this.ResponseHandlers.put("error_response", ErrorResponse.class.getName());
	}
	
	/**
	 * Log a user in
	 * @param uid
	 * @param password
	 * @return
	 */
	public boolean LogIn(String uid, String password) {
		Map<String, String> params = new HashMap<String, String>();
	
		
		return false;
	}
	
	public String AuthCreateToken() {
		// Set up the parameters we're going to need for the request
		Map<String, String> params = new HashMap<String, String>();
		FacebookResponse r = this.Request.PostAPICall(Facebook.uri_Method_Auth_createToken, params);
		return r.raw;
	}
	
 }
