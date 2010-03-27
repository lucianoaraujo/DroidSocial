package DroidSocial.Events.Facebook;

import DroidSocial.Network.*;
import org.xml.sax.*;
import java.util.*;

public class AuthCreateTokenResponse extends HandlerBase implements SocialNetworkResponseHandler  {
	
	public static final String ResponseType = "auth_createtoken_response";
	private boolean authTokenFound = false;
	private String authTokenVal = "";
	//<xml a=3 b=4>blah</xml>
	// Used as the main communication back to the outside world
	private Map<String, String> ResponseVals = new HashMap<String, String>();
	
	public void startElement(String elem_name, AttributeList atts) {
		if (elem_name.equalsIgnoreCase("auth_createToken_response ")) {
			// Nice we've got an auth token
			this.authTokenFound = true;
		}
	}
	
	public void characters(char elem_value[], int start, int length) {
		if (this.authTokenFound) {
			this.authTokenVal = new String(elem_value, start, length);
			this.ResponseVals.put("AuthToken", this.authTokenVal);
		}
	}
	
	public Map<String, String> Respond() {
		return this.ResponseVals;
	}
}
