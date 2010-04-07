package DroidSocial.Facebook;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import DroidSocial.Network.SocialNetwork;
import DroidSocial.Network.SocialNetworkAPIException;
import DroidSocial.Network.SocialNetworkRequest;
import DroidSocial.Network.SocialNetworkResponse;

/**
 * Applied SocialNetworkRequest class for building requests that are specific to the SocialNetwork in
 * question.  We'll use this class to further encapsulate http request details like the nuances that
 * each network will expect us to conform to.  We can store api keys and other important details here.
 * @author Joey
 *
 */
public class FacebookRequest extends SocialNetworkRequest {
	
	private String Secret;
	private String API_KEY;
	private String ServiceURI;
	
	public FacebookRequest(SocialNetwork sn, String service_uri, String secret, String api_key) {
		// SocialNetworkRequest objects require an 
		super(service_uri);
		this.ServiceURI = service_uri;
		this.NetworkObj = sn;
		this.Secret = secret;
		this.API_KEY = api_key;
	}

	private FacebookResponse FBGetRequest(Map<String, String> get_params, Map<String, String> headers) throws SocialNetworkAPIException {
		get_params.put(Facebook.uri_Version_key, Facebook.uri_Version_val);
		FacebookResponse fb_response = null;
		// Generate the sig value
		try {
			String response = this.SendGetRequest(get_params, headers);
			fb_response = new FacebookResponse(response, this.NetworkObj);
		}
		catch (Exception e) {
			throw new SocialNetworkAPIException(e.getMessage());
		}
		return fb_response;
	}
	
	public FacebookResponse PostAPICall(String method_name, Map<String, String> args, Map<String, String> get_args, Map<String, String> headers) throws SocialNetworkAPIException {
		return this.PostAPICall(this.ServiceURI, method_name, args, get_args, headers);
	}
	
	public FacebookResponse PostAPICall(String URI, String method_name, Map<String, String> args, Map<String, String> get_args, Map<String, String> headers) throws SocialNetworkAPIException {
		// Get params are not yet implemented!
		Map<String, String> post_data = this.getRequiredParams();
		Map<String, String> sig_params = this.getRequiredParams();
		FacebookResponse fb_response;
		
		// If we've got a method name, put it into the post params
		if (!method_name.equals("")) {
			post_data.put(Facebook.uri_Method_key, method_name);
			sig_params.put(Facebook.uri_Method_key, method_name);
		}
		
		if (args != null) {
			// Add the method params to the sig params
			Set<String> keys = args.keySet();
			for (String key : keys) {sig_params.put(key, args.get(key)); }
		}
		
		try {
			String sig = this.Sig(new HashMap<String, String>(sig_params));
			post_data.put(Facebook.uri_Sig_key, sig);
	
			String response = this.SendPostRequest(URI, post_data, get_args, headers);
			fb_response = new FacebookResponse(response, this.NetworkObj);
		}
		catch (Exception e) {
			throw new SocialNetworkAPIException(e.getMessage());
		}
		return fb_response;
	}
	
	public Map<String, String> getRequiredParams() {
		return this.getRequiredParams(Facebook.REQUIRED_PARAMS_REST_API);
	}
	
	/**
	 * Returns a new Map instance containing key value parameters that are
	 * required or preferred for the type of request represented by key.
	 * Note that these parameters do NOT directly follow the specification
	 * of any family of methods on the facebook developer wiki.    
	 */
	public Map<String, String> getRequiredParams(int key) {
		Map<String, String> data = new HashMap<String, String>();
		
		// These two values are always required, we will simply add 
		// params based on the key that is requested.
		data.put(Facebook.uri_ApiKey_key, this.API_KEY);
		data.put(Facebook.uri_Version_key, Facebook.uri_Version_val);
		
		switch (key) {
		
			// Perhaps we can abstract this routine further into hard coding these maps as
			// final and then return a new Map instance of a copy of one of them that equals the key
			// for now, this will work just fine though.
			case Facebook.REQUIRED_PARAMS_LOGIN_SESSION_ONLY:
				data.put(Facebook.uri_SessionOnly_key, "true");
				data.put(Facebook.uri_FBConnect_key, "true");
				data.put(Facebook.uri_ReturnSession_key, "true");
				data.put(Facebook.uri_ConnectDisplay_key, Facebook.uri_ConnectDisplay_val);
				data.put(Facebook.uri_CancelUrl_key, "http://www.facebook.com/connect/login_failure.html");
				data.put(Facebook.uri_Next_key, Facebook.LOGIN_NEXT);
				break;
				
			case Facebook.REQUIRED_PARAMS_LOGIN:
				data.put(Facebook.uri_FBConnect_key, "true");	
				break;
				
			case Facebook.REQUIRED_PARAMS_REST_API:
				data.put(Facebook.uri_Format_key, Facebook.uri_Format_val);
				break;
		
			default:
		}
		return data;
	}
	
	/**
	 * Generate the sig value for facebook API requests.  
	 * @param params
	 * @return
	 * @throws Exception
	 */
	private String Sig(Map<String, String> params) throws Exception {
		// Seperate the keys and values so that we can sort the map
		List<String> keys = new ArrayList<String>(params.keySet());
		List<String> vals = new ArrayList<String>(params.values());
		// The map that we will use after it's been sorted
		Map<String, String> sortedMap = new HashMap<String, String>();
		
		// The tree structure we will temporarily store the keys inside 
		// which will automatically contain them sorted
		TreeSet<String> sortedVals = new TreeSet<String>(keys);
		Object sortedKeys[] = sortedVals.toArray();
		
		for(int i = 0; i < sortedKeys.length; i++) {
			String id = (String)sortedKeys[i];
			sortedMap.put(id, params.get(id));
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < sortedKeys.length; i++) sb.append(sortedKeys[i] + "=" + sortedMap.get(sortedKeys[i]).toLowerCase());
		
		sb.append(this.Secret);
		String unencrypted = sb.toString();

		// Create the md5 hash
		byte[] defaultBytes = unencrypted.getBytes();
		byte[] encryptedBytes;
		StringBuffer hexStr = new StringBuffer();
		
		try {
			MessageDigest algorithm = MessageDigest.getInstance(("MD5"));
			algorithm.reset();
			algorithm.update(defaultBytes, 0, defaultBytes.length);
			encryptedBytes = algorithm.digest();
			for (int i = 0; i < encryptedBytes.length; i++) {
				String hex = Integer.toHexString(0xFF & encryptedBytes[i]);
				if (hex.length() == 1) hexStr.append('0');
				hexStr.append(hex);
			}
		}
		catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return hexStr.toString();
	}
}