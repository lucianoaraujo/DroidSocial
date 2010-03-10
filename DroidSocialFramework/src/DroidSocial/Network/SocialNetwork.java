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
	
	private String API_KEY_ID = ""; // the name that a particular network looks for to identify the api key

	protected SocialNetworkRequest Request = null;
	
	protected SocialNetworkUser User = null;
	
	protected Map<String, SocialNetworkResponseListener> ResponseListeners;
	
	protected abstract void SetDefaultResponseListeners();
	
	public SocialNetwork(String network_name, String api_identifier, String api_key)  {
		// set the network name for shits and giggles
		this.NetworkDisplayName = network_name;
		
		// remeber these values just in case
		this.API_KEY_ID = api_identifier;
		this.API_KEY_VALUE = api_key;
		
		// Set up the initial social network event handlers
		this.ResponseListeners = new HashMap<String, SocialNetworkResponseListener>();
	}
	
	/**
	 * Use this method to check whether a certain event handler is defined by the social network
	 * @param HandlerKey The key to access this social network's event handler
	 * @return
	 */
	public final boolean ResponseSupported(String HandlerKey) {
		return this.ResponseListeners.containsKey(HandlerKey);
	}
	
	public SocialNetworkResponseListener GetResponseListener(String ResponseListenerKey) {
		if (this.ResponseSupported(ResponseListenerKey))return this.ResponseListeners.get(ResponseListenerKey);
		else return this.ResponseListeners.get("UnsupportedResponse");
	}
}