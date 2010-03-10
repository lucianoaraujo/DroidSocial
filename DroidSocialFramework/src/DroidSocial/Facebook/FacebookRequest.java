package DroidSocial.Facebook;

import java.util.Map;

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
	
	
	public FacebookRequest(SocialNetwork sn, String service_uri) {
		// SocialNetworkRequest objects require an 
		super(service_uri);
		this.NetworkObj = sn;
	}

	public FacebookResponse FBGetRequest(Map<String, String> get_params) {
		SocialNetworkResponse response = this.SendGetRequest(get_params);
		FacebookResponse fb_response = new FacebookResponse(response, this.NetworkObj);
		return fb_response;
	}
}
