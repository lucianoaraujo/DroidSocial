package DroidSocial.Facebook;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import DroidSocial.Network.SocialNetwork;
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
	
	public FacebookRequest(SocialNetwork sn, String service_uri, String secret, String api_key) {
		// SocialNetworkRequest objects require an 
		super(service_uri);
		
		this.NetworkObj = sn;
		this.Secret = secret;
		this.API_KEY = api_key;
	}

	public FacebookResponse FBGetRequest(Map<String, String> get_params) {
		get_params.put(Facebook.uri_Version_key, Facebook.uri_Version_val);
		// Generate the sig value
		
		SocialNetworkResponse response = this.SendGetRequest(get_params);
		FacebookResponse fb_response = new FacebookResponse(response, this.NetworkObj);
		return fb_response;
	}
	
	public FacebookResponse PostAPICall(String method_name, Map<String, String> args) {
		Map<String, String> post_data = new HashMap<String, String>();
		Map<String, String> sig_params = new HashMap<String, String>();
		
		// Put the appropriate required data in for every facebook api call
		post_data.put(Facebook.uri_ApiKey_key, this.API_KEY);
		post_data.put(Facebook.uri_Version_key, Facebook.uri_Version_val);
		post_data.put(Facebook.uri_Method_key, method_name);
		
		// Put the appropriate data in generating the request signature
		sig_params.put(Facebook.uri_Method_key, method_name);
		sig_params.put(Facebook.uri_ApiKey_key, this.API_KEY);
		sig_params.put(Facebook.uri_Version_key, Facebook.uri_Version_val);
		
		// Add the method params to the sig params
		Set<String> keys = args.keySet();
		for (String key : keys) {
			sig_params.put(key, args.get(key));
		}
		
		String sig = this.Sig(new HashMap<String, String>(sig_params));
		post_data.put(Facebook.uri_Sig_key, sig);

		SocialNetworkResponse response = this.SendPostRequest(post_data, null);
		FacebookResponse fb_response = new FacebookResponse(response, this.NetworkObj);
		return fb_response;
	}
	
	/**
	 * Generate the sig value for facebook API requests.  
	 * @param params
	 * @return
	 */
	private String Sig(Map<String, String> params) {
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
		for (int i = 0; i < sortedKeys.length; i++) {				
			sb.append(sortedKeys[i] + "=" + sortedMap.get(sortedKeys[i]).toLowerCase());
		}
		
		sb.append(this.Secret);
		String s = sb.toString();

		// DO THAT FUNKY MD5 SHIT!
		byte[] defaultBytes = sb.toString().getBytes();
		byte[] digested;
		StringBuffer hexStr = new StringBuffer();
		try {
			MessageDigest algorithm = MessageDigest.getInstance(("MD5"));
			algorithm.reset();
			algorithm.update(defaultBytes, 0, defaultBytes.length);
			digested = algorithm.digest();
			for (int i = 0; i < digested.length; i++) {
				String hex = Integer.toHexString(0xFF & digested[i]);
				if (hex.length() == 1) hexStr.append('0');
				hexStr.append(hex);
			}
		}
		catch (Exception e) {}
		return hexStr.toString();
	}
}