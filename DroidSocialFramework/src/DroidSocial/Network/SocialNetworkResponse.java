package DroidSocial.Network;

import java.io.ByteArrayInputStream;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.HandlerBase;
 
public class SocialNetworkResponse {
 
	protected SocialNetwork Owner = null;
	protected ISocialNetworkResponseHandler Handler = null;
	protected String ResponseText = null;
	protected String ResponseType = null;

	public SocialNetworkResponse(String raw_response, SocialNetwork owner) { 
		this.ResponseText = raw_response; 
		this.Owner = owner;
	}

	public String getResponseString() { return this.ResponseText; }
	 
	public String getResponseType() { return Response.Resolver.getType(this.ResponseText); }
	
	public Map<String, String> Parse() {
		Map<String, String> response = null;
		try {
			
			// Get an xml parser instance from android
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser parser = spf.newSAXParser();
			
			// Put the response XML blob into a byte stream
			String response_text = new String(this.getResponseString());
			ByteArrayInputStream bais = new ByteArrayInputStream(response_text.getBytes());
			
			// Get the type of the response
			String response_type = this.getResponseType();
			
			// Get the instance of the listener that is associated with the response type
			Class listener_class = Class.forName(this.Owner.GetResponseListener(response_type));
			this.Handler = (AbstractSocialNetworkResponseHandler) listener_class.newInstance();
			
			// parse the xml response witht the handler that we've just instantiated
			parser.parse(bais, (HandlerBase) this.Handler);
			response = this.Handler.Respond();
	
		}
		catch (Exception e) {
			String s = e.getMessage();
		}
		return response;
	}
}