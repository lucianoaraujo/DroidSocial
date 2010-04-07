package DroidSocial.Twitter;

import DroidSocial.Events.Twitter.*;
import DroidSocial.Network.SocialNetwork;
import DroidSocial.Network.SocialNetworkAPIException;

import java.net.URLEncoder;
import java.util.*;

import android.content.Context;
import android.content.Intent;

/**
 * 
 * @author Luciano Araujo
 *
 */
public class Twitter extends SocialNetwork {
	
	public static final String REST_API_SERVICE_URI = "http://api.twitter.com/1/";
	public static final String SEARCH_API_SERVICE_URI = "http://search.twitter.com/";	
	public static final String API_RESPONSE_FORMAT = ".xml";
	
	public static final String uri_Search = "search";
	public static final String uri_Trends = "trends";
	public static final String uri_Trends_Current = "trends/current";
	public static final String uri_Trends_Daily = "trends/daily";
	public static final String uri_Trends_Weekly = "trends/weekly";
	
	public static final String uri_Statuses_Public_Timeline = "statuses/public_timeline";
	public static final String uri_Statuses_Home_Timeline = "statuses/home_timeline";
	public static final String uri_Statuses_Friends_Timeline = "statuses/friends_timeline";

	public static final String uri_Statuses_Show = "statuses/show";
	public static final String uri_Statuses_Update = "statuses/update";
	public static final String uri_Statuses_Destroy = "statuses/destroy";
    
	public static final String uri_Users_Show = "users/show";
	public static final String uri_Users_Lookup = "users/lookup";
	public static final String uri_Users_Search = "users/search";
	
	public static final String uri_Account_Verify_Credentials = "account/verify_credentials";
    
    public static final int REQUIRED_PARAMS_LOGIN = 0;
	public static final int REQUIRED_PARAMS_LOGIN_SESSION_ONLY = 1;
	public static final int REQUIRED_PARAMS_REST_API = 2;
	
	public static final String RESPONSE_KEY_AUTH_TOKEN = "auth_createtoken_response";
	public static final String RESPONSE_KEY_GET_SESSION = "auth_getSession_response";
	public static final String RESPONSE_KEY_ERROR = "error_response";
	
	private TwitterRequest Request = null;	

	/**
	 * Create a new instance of Twitter.
	 */
	public Twitter( String username, String password )
	{
		// Handle the under the hood stuff
		super("Twitter", "");
		// set the request object to the twitter specific request object
		this.Request = new TwitterRequest(this, Twitter.REST_API_SERVICE_URI, Twitter.SEARCH_API_SERVICE_URI, username, password );
		
		//this.ResponseHandlers.put("UnsupportedResponse", FacebookUnsupportedResponse.class.getName());
	}
	
	/**
	 * Set the default methods that the response object will look for when parsing XML responses from Facebook.
	 * If you want to add a new event and handler, they must be added to the fbSaxParser class.
	 */
	public void SetDefaultResponseHandlers() {
		//this.ResponseHandlers.put(Twitter.RESPONSE_KEY_AUTH_TOKEN, AuthCreateTokenResponse.class.getName());
		//this.ResponseHandlers.put(Twitter.RESPONSE_KEY_ERROR, ErrorResponse.class.getName());
	}
	
	/**
	 * Authenticates the user credentials. Use this method to test if supplied user credentials are valid.
	 */
	public String accountVerifyCredentials()
	{
		String valid = "false";
		
		try {
			TwitterResponse twResponse = this.Request.GetRequest(null, null);
		} 
		catch (SocialNetworkAPIException e) {
			// TODO Auto-generated catch block	
		}
		
		return valid;
	}
	
	/**
	 * Get the user id of the user we're currently managing.  Typically this method will be called only if
	 * the sessionKey is already known, meaning that the application that is managing the user should store
	 * sessionKey somewhere.  When the sessionKey is known, this method is generally the first one in line
	 * to be called upon application startup. 
	 * @param sessionKey
	 * @return the logged in user's id
	 */
	/*public String Users_getLoggedInUser(String sessionKey) {
		String uid = "";
		String time_ms = ((Long)System.currentTimeMillis()).toString();
		Map<String, String> post_params = new HashMap<String, String>();

		post_params.put(Twitter.uri_SessionKey_key, sessionKey);
		post_params.put(Twitter.uri_CallId_key, time_ms);
		
		try {
			TwitterResponse fbr = this.Request.PostAPICall(Twitter.uri_Method_Users_getLoggedInUser, post_params, null, null);
		} 
		catch (SocialNetworkAPIException e) {
			// TODO Auto-generated catch block	
		}
		return uid;
	}*/
	
	/**
	 * Log a user into facebook.  Invoking this method starts an activity which contains a 
	 * webview that points to http://www.facebook.com/login.php with the appropriate arguments
	 * concatenated to the GET request string.  Upon the user's completion of logging in, the
	 * <callback> method will be invoked on the Activity object that invoked LogIn
	 * @param emailAddr
	 * @param password
	 * @param requested_perms
	 * @param cntxt
	 * @return
	 */
	/*public void LogIn(String[] requested_perms, Context cntxt) {
		// First get a mapping of the required params as the base map of query name=value pairs
		Map<String, String> query_params = this.Request.getRequiredParams(Twitter.REQUIRED_PARAMS_LOGIN);
		
		try {
			// First create an Intent to launch the webview login module.
			Intent fbLoginIntent = new Intent(cntxt, FBWebViewLogin.class);
			// Next build the appropriate GET query string and pass it as an extra to the Intent object
		}
		catch (Exception e) {
			
		}
	}*/
	
	
	
	
 }
