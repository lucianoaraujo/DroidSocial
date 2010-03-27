package DroidSocial.Network;

import java.util.*;

/**
 * Base class for representing all that is common to social networks.
 * @author Joey
 */
public abstract class SocialNetwork {

	// for external identification
	private String NetworkDisplayName = "";
	
	// necessary for identification
	private String ServiceURI = "";
	
	private String API_KEY_VALUE = "";
	
	protected SocialNetworkRequest Request = null;
	
	protected SocialNetworkUser User = null;
	
	// A map of fully qualified class names to be instantiated on the fly
	protected Map<String, String> ResponseHandlers;
	
	protected abstract void SetDefaultResponseHandlers();
	
	public SocialNetwork(String network_name, String api_key)  {
		this.NetworkDisplayName = network_name;		
		this.API_KEY_VALUE = api_key;
		// Set up the initial social network event handlers
		this.ResponseHandlers = new HashMap<String,String>();
	}
	
	/**
	 * Use this method to check whether a certain event handler is defined by the social network
	 * @param HandlerKey The key to access this social network's event handler
	 * @return
	 */
	
	public final boolean ResponseSupported(String HandlerKey) {
		return this.ResponseHandlers.containsKey(HandlerKey);
	}
	
	/**
	 * Get the fully qualified name of the SocialNetworkResponse listener represented by ResponseListenerKey
	 * @param ResponseListenerKey
	 * @return
	 */
	public String GetResponseListener(String ResponseListenerKey) {
		if (this.ResponseSupported(ResponseListenerKey))return this.ResponseHandlers.get(ResponseListenerKey);
		else return this.ResponseHandlers.get("UnsupportedResponse");
	}
	
}