package DroidSocial.Network;

import java.io.ByteArrayInputStream;

import org.xml.sax.*;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SocialNetworkResponse {
	
	protected SocialNetwork Owner = null;
	private String ResponseText = null;
	
	// This will describe the type of response so that derived implementations of
	// this class can decide which response listener to use to parse it.
	private String ResponseType = null;
	
	public SocialNetworkResponse(String raw_response) {
		this.ResponseText = raw_response;
		this.ResponseType = ResponseTypeResolver.GetResponseType(raw_response);
	}
	
	public String getResponseString() {
		return this.ResponseText;
	}
	
	public String getResponseType() {
		return this.ResponseType;
	}
	
	/**
	 * Static class used specifically for understanding certain things about xml responses
	 * without actually having to parse through them.  Of course it does parse through the xml
	 * response, but only for the delivery of meta data to outside resources.
	 * @author Joey
	 *
	 */
	public static class ResponseTypeResolver {
		
		public static String GetResponseType(String xml_doc) {
			String returnval = "";
			try {
			SAXParserFactory f = SAXParserFactory.newInstance();
			SAXParser parser = f.newSAXParser();
			ResponseMetaHandler handler = new ResponseMetaHandler();
			
			ByteArrayInputStream bais = new ByteArrayInputStream(xml_doc.getBytes());
			
			parser.parse(bais, (HandlerBase)handler);
			returnval = handler.GetRootObjectName();
			}
			catch (Exception e) {
				String s = e.getMessage();
				returnval = "couldn't extract root object name";
			}
			return returnval;
		}
		
		private static class ResponseMetaHandler extends HandlerBase {
			
			private boolean found_root = false;
			private String root_val = null;
			
			public String GetRootObjectName() {
				return this.root_val;
			}
			
			public void startElement(String name, AttributeList atts) {
				
			}
			
			public void characters(char val[], int start, int length) {
				// Grab the root real quick and state that we know what the root is
				if (!this.found_root) {
					this.found_root = true;
					this.root_val = new String(val, start, length);
				}
			}
		}
	}
}