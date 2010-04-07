package DroidSocial.Twitter;

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
 * @author Luciano Araujo
 *
 */
public class TwitterRequest extends SocialNetworkRequest {
	
	private String serviceURI;
	private String searchURI;
	private String base64AuthToken;
	
	public TwitterRequest(SocialNetwork sn, String serviceUri, String searchUri, String username, String password )
	{
		// SocialNetworkRequest objects require an 
		super(serviceUri);
		this.NetworkObj = sn;
		this.serviceURI = serviceUri;
		this.searchURI = searchUri;
		this.base64AuthToken = generateAuthToken( username + ":" + password );
		String luch = "blah";
	}

	public TwitterResponse GetRequest(Map<String, String> get_params, Map<String, String> headers) throws SocialNetworkAPIException {
		// Add the base64AuthToken here!
		headers.put( "Authorization", "Basic " + this.base64AuthToken );
		TwitterResponse tw_response = null;
		try {
			String response = this.SendGetRequest(get_params, headers);
			tw_response = new TwitterResponse(response, this.NetworkObj);
		}
		catch (Exception e) {
			throw new SocialNetworkAPIException(e.getMessage());
		}
		return tw_response;
	}
	
	/*public TwitterResponse PostAPICall(String method_name, Map<String, String> args, Map<String, String> get_args, Map<String, String> headers) throws SocialNetworkAPIException {
		return this.PostAPICall(this.ServiceURI, method_name, args, get_args, headers);
	}
	
	public TwitterResponse PostAPICall(String URI, String method_name, Map<String, String> args, Map<String, String> get_args, Map<String, String> headers) throws SocialNetworkAPIException {
		// Get params are not yet implemented!
		Map<String, String> post_data = this.getRequiredParams();
		Map<String, String> sig_params = this.getRequiredParams();
		TwitterResponse fb_response;
		
		// If we've got a method name, put it into the post params
		if (!method_name.equals("")) {
			post_data.put(Twitter.uri_Method_key, method_name);
			sig_params.put(Twitter.uri_Method_key, method_name);
		}
		
		if (args != null) {
			// Add the method params to the sig params
			Set<String> keys = args.keySet();
			for (String key : keys) {sig_params.put(key, args.get(key)); }
		}
		
		try {
			String sig = this.Sig(new HashMap<String, String>(sig_params));
			post_data.put(Twitter.uri_Sig_key, sig);
	
			String response = this.SendPostRequest(URI, post_data, get_args, headers);
			fb_response = new TwitterResponse(response, this.NetworkObj);
		}
		catch (Exception e) {
			throw new SocialNetworkAPIException(e.getMessage());
		}
		return fb_response;
	}*/
	
	/*public Map<String, String> getRequiredParams() {
		return this.getRequiredParams(Twitter.REQUIRED_PARAMS_REST_API);
	}*/
	
	/**
	 * Returns a new Map instance containing key value parameters that are
	 * required or preferred for the type of request represented by key.
	 * Note that these parameters do NOT directly follow the specification
	 * of any family of methods on the facebook developer wiki.    
	 */
	/*public Map<String, String> getRequiredParams(int key) {
		Map<String, String> data = new HashMap<String, String>();
		
		// These two values are always required, we will simply add 
		// params based on the key that is requested.
		data.put(Twitter.uri_ApiKey_key, this.API_KEY);
		data.put(Twitter.uri_Version_key, Twitter.uri_Version_val);
		
		switch (key) {
		
			// Perhaps we can abstract this routine further into hard coding these maps as
			// final and then return a new Map instance of a copy of one of them that equals the key
			// for now, this will work just fine though.
			case Twitter.REQUIRED_PARAMS_LOGIN_SESSION_ONLY:
				data.put(Twitter.uri_SessionOnly_key, "true");
				data.put(Twitter.uri_FBConnect_key, "true");
				data.put(Twitter.uri_ConnectDisplay_key, Twitter.uri_ConnectDisplay_val);
				break;
				
			case Twitter.REQUIRED_PARAMS_LOGIN:
				data.put(Twitter.uri_FBConnect_key, "true");	
				break;
				
			case Twitter.REQUIRED_PARAMS_REST_API:
				data.put(Twitter.uri_Format_key, Twitter.uri_Format_val);
				break;
		
			default:
		}
		return data;
	}*/
	
	private String generateAuthToken( String credentials )
	{
		Base64 encoder = new Base64();
		return encoder.encode( credentials );
	}
	
	
	public class Base64 {
		 
	    private static final String base64code = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
	            + "abcdefghijklmnopqrstuvwxyz" + "0123456789" + "+/";
	 
	    private static final int splitLinesAt = 76;
	 
	    public byte[] zeroPad(int length, byte[] bytes) {
	        byte[] padded = new byte[length]; // initialized to zero by JVM
	        System.arraycopy(bytes, 0, padded, 0, bytes.length);
	        return padded;
	    }
	 
	    public String encode(String string)
	    {	 
	        String encoded = "";
	        byte[] stringArray;
	        try {
	            stringArray = string.getBytes("UTF-8");  // use appropriate encoding string!
	        } catch (Exception ignored) {
	            stringArray = string.getBytes();  // use locale default rather than croak
	        }
	        // determine how many padding bytes to add to the output
	        int paddingCount = (3 - (stringArray.length % 3)) % 3;
	        // add any necessary padding to the input
	        stringArray = zeroPad(stringArray.length + paddingCount, stringArray);
	        // process 3 bytes at a time, churning out 4 output bytes
	        // worry about CRLF insertions later
	        for (int i = 0; i < stringArray.length; i += 3) {
	            int j = ((stringArray[i] & 0xff) << 16) +
	                ((stringArray[i + 1] & 0xff) << 8) + 
	                (stringArray[i + 2] & 0xff);
	            encoded = encoded + base64code.charAt((j >> 18) & 0x3f) +
	                base64code.charAt((j >> 12) & 0x3f) +
	                base64code.charAt((j >> 6) & 0x3f) +
	                base64code.charAt(j & 0x3f);
	        }
	        // replace encoded padding nulls with "="
	        return splitLines(encoded.substring(0, encoded.length() -
	            paddingCount) + "==".substring(0, paddingCount));	 
	    }
	    
	    public String splitLines(String string)
	    {	 
	        String lines = "";
	        for (int i = 0; i < string.length(); i += splitLinesAt)
	        {	 
	            lines += string.substring(i, Math.min(string.length(), i + splitLinesAt));
	            lines += "\r\n";	 
	        }
	        return lines;
	    }
	    
	 
	}
	
}