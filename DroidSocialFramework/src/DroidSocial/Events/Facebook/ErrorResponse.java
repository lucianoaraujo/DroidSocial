package DroidSocial.Events.Facebook;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.AttributeList;
import org.xml.sax.HandlerBase;

import DroidSocial.Network.AbstractSocialNetworkResponseHandler;

public class ErrorResponse extends AbstractSocialNetworkResponseHandler {

	private Map<String, String> ResponseVals = new HashMap<String, String>();
	
	public Map<String, String> Respond() {
		// TODO Auto-generated method stub
		this.ResponseVals.put("error", "errorMessage!!!");
		return this.ResponseVals;
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
