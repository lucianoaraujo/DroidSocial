package DroidSocial.Events.Facebook;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.AttributeList;
import org.xml.sax.HandlerBase;

import DroidSocial.Network.AbstractSocialNetworkResponseHandler;

public class FacebookUnsupportedResponse extends AbstractSocialNetworkResponseHandler {

	private Map<String, String> ResponseMap = new HashMap<String, String>();
	@Override
	public Map<String, String> Respond() {
		return this.ResponseMap;
	}
	@Override
	public void characters(char[] elemValue, int start, int length) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void startElement(String elemName, AttributeList atts) {
		// TODO Auto-generated method stub
		
	}
	
}
