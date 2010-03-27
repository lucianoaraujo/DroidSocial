package DroidSocial.Network;

// necessary packages for using streams
import java.io.*;

// necessary for CGI encoding
import java.net.URLEncoder;

// necessary for 
import java.util.*;

// necessary for communicating via http
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

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

		this.ServiceURI = service_uri;
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
	
	protected SocialNetworkResponse SendPostRequest(String URI, Map<String, String> post_data, Map<String, String> get_params) {
		//TODO: Add support for the get params that may be on this POST request
		List<NameValuePair> post_req_fields = new ArrayList<NameValuePair>();
		Set<String> keys = post_data.keySet();
		String output = "";
		
		// Create key = value pairs
		for (String key : keys) post_req_fields.add(new BasicNameValuePair(key, post_data.get(key)));
		
		
		try {
			// Create the post object and entity that it will carry
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(post_req_fields);
			
			String getStr = "";
			if (get_params != null) getStr = ToURIStr(get_params);
			
			HttpPost p = new HttpPost(URI + "?" + getStr);
			p.setEntity(entity);
			
			// execute the request
			this.Response = this.Client.execute(p);

			HttpEntity en = this.Response.getEntity();
			InputStream in = en.getContent();
			output = StreamToString(in);
		}
		catch (Exception e) {
			String msg = e.getMessage();
		}
		finally {
			this.Response = null;
		}
		
		return new SocialNetworkResponse(output);	
	}
	
	protected SocialNetworkResponse SendPostRequest(Map<String, String> post_data, Map<String, String> get_params) {
		return this.SendPostRequest(this.ServiceURI, post_data, get_params);
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