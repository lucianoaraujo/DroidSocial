package DroidSocial.Network;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.AttributeList;
import org.xml.sax.HandlerBase;


public class Response {

	/**
	 * Static class used specifically for understanding certain things about xml responses
	 * without actually having to parse through them.  Of course it does parse through the xml
	 * response, but only for the delivery of meta data to outside resources.
	 * @author Joey
	 *
	 */
	public static class Resolver {

		public static String getType(String xml_doc) {
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

			private boolean is_junk = true;
			private boolean found_root = false;
			private String root_val = null;

			public String GetRootObjectName() {
				return this.root_val;
			}

			public void startElement(String name, AttributeList atts) {
				if (!this.found_root) {
					this.is_junk = false;
					this.root_val = name;
					this.found_root = true;
				}
			}

			public void characters(char val[], int start, int length) {
				if (!this.is_junk) {
					
				}
			}
		}
	}
}
