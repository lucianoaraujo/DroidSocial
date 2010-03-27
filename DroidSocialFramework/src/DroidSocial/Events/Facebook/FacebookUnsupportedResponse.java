package DroidSocial.Events.Facebook;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.HandlerBase;
import DroidSocial.Network.SocialNetworkResponseHandler;

public class FacebookUnsupportedResponse extends HandlerBase implements SocialNetworkResponseHandler {

	private Map<String, String> ResponseMap = new HashMap<String, String>();
	@Override
	public Map<String, String> Respond() {
		return this.ResponseMap;
	}
	
}
