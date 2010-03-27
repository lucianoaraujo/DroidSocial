package DroidSocial.Events.Facebook;

import java.util.Map;

import org.xml.sax.AttributeList;

import DroidSocial.Facebook.Facebook;
import DroidSocial.Network.AbstractSocialNetworkResponseHandler;

public class AuthGetSessionResponse extends AbstractSocialNetworkResponseHandler {

	private boolean sessionKeyFound = false;
	
	@Override
	public Map<String, String> Respond() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void startElement(String elem_name, AttributeList atts) {
		if (elem_name.equalsIgnoreCase(Facebook.RESPONSE_KEY_GET_SESSION)) {
			this.sessionKeyFound = true;
		}
	}
	
	public void characters(char elem_value[], int start, int length) {
		if (this.sessionKeyFound) {
			
		}
	}

}
