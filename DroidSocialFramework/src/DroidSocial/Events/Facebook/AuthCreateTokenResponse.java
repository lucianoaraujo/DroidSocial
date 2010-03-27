package DroidSocial.Events.Facebook;

import DroidSocial.Facebook.Facebook;
import DroidSocial.Network.*;
import org.xml.sax.*;
import java.util.*;

	

public class AuthCreateTokenResponse extends AbstractSocialNetworkResponseHandler {
	
	private boolean authTokenFound = false;
	private String authTokenVal = "";
	
	public void startElement(String elem_name, AttributeList atts) {
		if (elem_name.equalsIgnoreCase("auth_createtoken_response")) {
			// Nice we've got an auth token
			this.authTokenFound = true;
		}
	}
	
	public void characters(char elem_value[], int start, int length) {
		if (this.authTokenFound) {
			this.authTokenVal = new String(elem_value, start, length);
			this.ResponseVals.put(Facebook.uri_AuthToken_key, this.authTokenVal);
		}
	}
	
	public Map<String, String> Respond() {
		return this.ResponseVals;
	}
}
