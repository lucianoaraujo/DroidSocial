package DroidSocial.Network;

// necessary packages for using streams
import java.io.*;

// necessary for CGI encoding
import java.net.URLEncoder;

// necessary for 
import java.util.*;
import java.util.Set;

// necessary for communicating via http
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.DefaultHttpClient;

// necessary for generating and parsing xml 
import org.xml.sax.*;
import org.xml.sax.helpers.*;


public abstract class SocialNetworkRequest {
	
	// The base URI that all services of this network reside at
	private String ServiceURI = "";
	
	// Objects that are critical to http interaction
	private HttpClient Client = null;
	private HttpResponse Response = null;
	
	protected SocialNetwork NetworkObj = null;
	
	
	
	/**
	 * Constructor: 
	 * @param service_uri : the base uri of all services on this network
	 */
	public SocialNetworkRequest(String service_uri) {

		this.ServiceURI = service_uri + "?";
		// Create the http client object
		this.Client = new DefaultHttpClient();
		
	}
	
	protected SocialNetworkResponse SendGetRequest(Map<String, String> get_params) {
		String output = "none";
	
		try {
			// Create the get request object with any parameters
			HttpGet getReq = new HttpGet(this.ServiceURI  + ToURIStr(get_params));
			this.Response = this.Client.execute(getReq);
			
			// Get the HttpEntity that contains the response data
			HttpEntity en = this.Response.getEntity();
			InputStream instream = en.getContent();
			output = StreamToString(instream);
		}
		catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			String p = e.getMessage();
		}
		
		catch (IOException i) {
			String p = i.getMessage();
		}
		catch (Exception e) {
			String p = e.getMessage();
		}
		
		return new SocialNetworkResponse(output);
	}
	
	protected SocialNetworkResponse SendPostRequest(Map<String, String> get_params) {
		String output = "none";
		
		try {
			HttpPost p = new HttpPost(this.ServiceURI + ToURIStr(get_params));
			this.Response = this.Client.execute(p);
			
			HttpEntity en = this.Response.getEntity();
			InputStream in = en.getContent();
			output = StreamToString(in);
		}
		catch (Exception e) {
			
		}
		
		return new SocialNetworkResponse(output);
	}
	
	/**
	 * TODO: ADD other methods for performing HTTP Post Requests
	 * 		 that carry data in the message by using File and FileEntity objects
	 * 		 http://www.mail-archive.com/android-developers@googlegroups.com/msg72456.html
	 */


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
		catch (Exception e) {}
		return sb.toString();
	}
	
    protected static String StreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
 
        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                is.	close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}