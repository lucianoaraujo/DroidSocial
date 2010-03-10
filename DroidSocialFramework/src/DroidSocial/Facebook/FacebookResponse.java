package DroidSocial.Facebook;

import DroidSocial.Network.*;
import org.xml.sax.*;
import java.io.*;
import org.xml.sax.helpers.ParserFactory;
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

	public FacebookResponse(SocialNetworkResponse response, SocialNetwork owner) {
		super(response.getResponseString());
		try {	
			this.Owner = owner;
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser parser = spf.newSAXParser();
			
			String response_text = response.getResponseString();
			ByteArrayInputStream bais = new ByteArrayInputStream(response_text.getBytes());
			
			String response_type = response.getResponseType();
			parser.parse(bais, (HandlerBase) this.Owner.GetResponseListener(response_type));
		}
		catch (Exception e) {
			String s = e.getMessage();
		}
	}
	
}
