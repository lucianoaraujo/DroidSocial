package DroidSocial.Network;

import java.io.IOException;
import java.io.*;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;

public class SocialNetworkRequest {

	/*
	 * Define some constants that are important to HTTP
	 */
	public static final String _POST_ 	= "POST";
	public static final String _GET_ 	= "GET";
	public static final String _PUT_ 	= "PUT";
	public static final String _DELETE_ = "DELETE";
	
	private String ServiceURI = "";
	
	// Objects that are critical to http interaction
	private HttpClient Client = null;
	private HttpResponse Response = null;
	
	// Default the RequestMethod to get
	private String RequestMethod = _GET_;
	
	// Strings to store the param strings
	private String GetParamString = "";
	private String PostParamString = "";
	
	private String ErrorString = "";
	
	private Map<String, String> Headers = new HashMap<String, String>();
	
	public SocialNetworkRequest(String service_uri) {

		this.ServiceURI = service_uri + "?";
		// Create the http client object
		this.Client = new DefaultHttpClient();
		
	}
	
	public String SendGetRequest(Map<String, String> params) throws IOException {
		String output = "none";
		String u = this.ServiceURI  + ToURIStr(params);
		try {
			// PROBLEM HERE!
			HttpGet getReq = new HttpGet(u);
			this.Response = this.Client.execute(getReq);
			HttpEntity en = this.Response.getEntity();
			InputStream instream = en.getContent();
			output = convertStreamToString(instream);
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			this.ErrorString = e.getMessage();
		}
		catch (Exception e) {
			this.ErrorString = e.getMessage();
		}
		
		return output;
	}
	
	public void SendPostRequest() {
		
	}
	

	/**
	 * Convert a String indexed Map of Strings to a URI encoded UTF-8 string
	 * @param params Map of String indexed Strings
	 * @return A URI encoded representation of the key value pairs represented by params
	 */
	public static String ToURIStr(Map<String, String> params) {
		Set<String> keys = params.keySet();
		StringBuilder sb = new StringBuilder();
		
		try {
			for (String key : keys) {				
				sb.append(URLEncoder.encode(key + "=" + params.remove(key), "UTF-8"));
				if (!keys.isEmpty()) sb.append("&");
			}
		}
		catch(UnsupportedEncodingException e) { /* Do we need to do anything here? */ }
		catch (Exception e) {
			
		}
		return sb.toString();
	}
	
    private static String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader return null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
 
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
	
}