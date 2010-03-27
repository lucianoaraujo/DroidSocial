package DroidSocial.Network;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.AttributeList;
import org.xml.sax.HandlerBase;

public abstract class AbstractSocialNetworkResponseHandler extends HandlerBase implements ISocialNetworkResponseHandler {
	// Used as the main communication back to the outside world
	protected Map<String, String> ResponseVals = new HashMap<String, String>();
	public abstract void startElement(String elem_name, AttributeList atts);
	public abstract void characters(char elem_value[], int start, int length);
}
