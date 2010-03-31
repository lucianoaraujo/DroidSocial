package DroidSocial.Facebook;

import DroidSocial.Events.Facebook.*;
import DroidSocial.Network.SocialNetwork;
import DroidSocial.Network.SocialNetworkAPIException;

import java.net.URLEncoder;
import java.util.*;

import android.content.Context;
import android.content.Intent;

/**
 * 
 * @author Joey
 *
 */
public class Facebook extends SocialNetwork {

	
	public static final String REST_API_SERVICE_URI = "http://api.facebook.com/restserver.php";
	
	public static final int REQUIRED_PARAMS_LOGIN = 0;
	public static final int REQUIRED_PARAMS_LOGIN_SESSION_ONLY = 1;
	public static final int REQUIRED_PARAMS_REST_API = 2;
	
	public static final String uri_Method_Users_getLoggedInUser = "users.getloggedinuser";
	public static final String uri_Method_Auth_createToken = "auth.createtoken";
	public static final String uri_Method_Auth_getSession = "auth.getsession";
	
	public static final String uri_ConnectDisplay_key = "connect_display";
	public static final String uri_ConnectDisplay_val = "popup";
	
	public static final String uri_SessionOnly_key = "session_key_only";
	
	public static final String uri_Sig_key = "sig";

	public static final String uri_Method_key = "method";
	
	public static final String uri_Version_key = "v";
	public static final String uri_Version_val = "1.0";
	
	public static final String uri_ApiKey_key = "api_key";
	
	public static final String uri_Secret_key = "secret";
	
	public static final String uri_CallId_key 	= "call_id";
	
	public static final String uri_AuthToken_key = "auth_token";
	
	public static final String uri_SessionKey_key = "session_key";
	
	public static final String uri_Format_key     = "format";
	public static final String uri_Format_val	  = "xml";
	
	public static final String uri_FBConnect_key  = "fbconnect";
	
	public static final String RESPONSE_KEY_AUTH_TOKEN = "auth_createtoken_response";
	public static final String RESPONSE_KEY_GET_SESSION = "auth_getSession_response";
	public static final String RESPONSE_KEY_ERROR = "error_response";
	
	private String Secret = null;
	private String AuthTokenVal = "";
	private String AuthTokenKey = "";
	private String API_KEY = "";
	
	private FacebookRequest Request = null;
	

	/**
	 * Create a new instance of Facebook.
	 * @param api_key
	 * @param secret
	 */
	public Facebook(String api_key, String secret) {
		// Handle the under the hood stuff
		super("Facebook", api_key);
		this.API_KEY = api_key;
		this.Secret = secret;
		// set the request object to the facebook specific request object
		this.Request = new FacebookRequest(this, Facebook.REST_API_SERVICE_URI, secret, api_key);
		
		this.ResponseHandlers.put("UnsupportedResponse", FacebookUnsupportedResponse.class.getName());
	}
	
	/**
	 * Set the default methods that the response object will look for when parsing XML responses from Facebook.
	 * If you want to add a new event and handler, they must be added to the fbSaxParser class.
	 */
	public void SetDefaultResponseHandlers() {
		this.ResponseHandlers.put(Facebook.RESPONSE_KEY_AUTH_TOKEN, AuthCreateTokenResponse.class.getName());
		this.ResponseHandlers.put(Facebook.RESPONSE_KEY_ERROR, ErrorResponse.class.getName());
	}
	
	/**
	 * Get the user id of the user we're currently managing.  Typically this method will be called only if
	 * the sessionKey is already known, meaning that the application that is managing the user should store
	 * sessionKey somewhere.  When the sessionKey is known, this method is generally the first one in line
	 * to be called upon application startup. 
	 * @param sessionKey
	 * @return the logged in user's id
	 */
	public String Users_getLoggedInUser(String sessionKey) {
		String uid = "";
		String time_ms = ((Long)System.currentTimeMillis()).toString();
		Map<String, String> post_params = new HashMap<String, String>();

		post_params.put(Facebook.uri_SessionKey_key, sessionKey);
		post_params.put(Facebook.uri_CallId_key, time_ms);
		
		try {
			FacebookResponse fbr = this.Request.PostAPICall(Facebook.uri_Method_Users_getLoggedInUser, post_params, null, null);
		} 
		catch (SocialNetworkAPIException e) {
			// TODO Auto-generated catch block	
		}
		return uid;
	}
	
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
	public void LogIn(String[] requested_perms, Context cntxt) {
		// First get a mapping of the required params as the base map of query name=value pairs
		Map<String, String> query_params = this.Request.getRequiredParams(Facebook.REQUIRED_PARAMS_LOGIN);
		
		try {
			// First create an Intent to launch the webview login module.
			Intent fbLoginIntent = new Intent(cntxt, FBWebViewLogin.class);
			// Next build the appropriate GET query string and pass it as an extra to the Intent object
		}
		catch (Exception e) {
			
		}
	}
	
	
	/* 
	 * MAY NOT NEED THESE SINCE WE'RE USING THE FACEOOK CONNECT ROUTE OF AUTHENTICAITON
	 * 
	 * */
	public String AuthGetSession(String authTokenVal) throws SocialNetworkAPIException  {
		String sessionKey = "";
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put(Facebook.uri_AuthToken_key, authTokenVal);
			FacebookResponse fbr = this.Request.PostAPICall(Facebook.uri_Method_Auth_getSession, params, null, null);
			Map<String, String> vals = fbr.Parse();
		} 
		catch (SocialNetworkAPIException e) {
			throw e;
		}
		return sessionKey;
	}
	
	public String AuthCreateToken() throws SocialNetworkAPIException {
		String authToken = "";
		try {
			// Set up the parameters we're going to need for the request
			//Map<String, String> params = new HashMap<String, String>();
			FacebookResponse fbr = this.Request.PostAPICall(Facebook.uri_Method_Auth_createToken, null, null, null);
			Map<String, String> vals = fbr.Parse();
			
			authToken = vals.get(Facebook.uri_AuthToken_key);
		}
		catch (SocialNetworkAPIException e) {
			throw e;
		}
		return authToken;
	}

 }
