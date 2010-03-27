package DroidSocial.Events.Facebook;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.HandlerBase;

import DroidSocial.Network.SocialNetworkResponseHandler;

public class ErrorResponse extends HandlerBase implements SocialNetworkResponseHandler {

	private Map<String, String> ResponseVals = new HashMap<String, String>();
	
	public Map<String, String> Respond() {
		// TODO Auto-generated method stub
		this.ResponseVals.put("error", "errorMessage!!!");
		return this.ResponseVals;
	}

}
