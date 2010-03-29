package DroidSocial.Facebook;

import DroidSocial.Network.*;
import java.io.*;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.HandlerBase;
import org.xml.sax.InputSource;

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
	
	public FacebookResponse(String response, SocialNetwork owner) {	
		super(response, owner);
	}
}
