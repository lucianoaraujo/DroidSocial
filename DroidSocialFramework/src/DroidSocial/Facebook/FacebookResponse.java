package DroidSocial.Facebook;

import DroidSocial.Network.*;
import java.io.*;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Class to encapsulate details that may be specific to facebook responses.
 * Objects of this class will be returned by the derived implementations of 
 * SocialNetworkRequest objects.  
 * 
 * This class adds greater functionality by
 * allowing an event driven read of the XML responses.
 * @author Joey
 *
 */
public class FacebookResponse extends SocialNetworkResponse {

	public String raw = "";
	
	public FacebookResponse(SocialNetworkResponse response, SocialNetwork owner) {	
		this.Owner = owner;
		this.raw = response.getResponseString();
	}
	
	public Map<String, String> Parse() {
		Map<String, String> response = null;
		try {
			
			// Get an xml parser instance from android
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser parser = spf.newSAXParser();
			
			// Put the response XML blob into a byte stream
			String response_text = this.getResponseString();
			ByteArrayInputStream bais = new ByteArrayInputStream(response_text.getBytes());
			
			// Get the type of the response
			String response_type = this.getResponseType();
			
			// Get the instance of the listener that is associated with the response type
			Class listener_class = Class.forName(this.Owner.GetResponseListener(response_type));
			this.Handler = (SocialNetworkResponseHandler) listener_class.newInstance();
			response = this.Handler.Respond();
	
		}
		catch (Exception e) {
			String s = e.getMessage();
		}
		return response;
	}
}
