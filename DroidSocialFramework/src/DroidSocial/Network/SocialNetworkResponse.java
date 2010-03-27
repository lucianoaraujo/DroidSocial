package DroidSocial.Network;

import java.util.Map;
 
public class SocialNetworkResponse {
 
	protected SocialNetwork Owner = null;
	protected ISocialNetworkResponseHandler Handler = null;
	protected String ResponseText = null;
	protected String ResponseType = null;

	// NO ARG CONSTRUCTOR for implementations
	protected SocialNetworkResponse() {}
	
	protected Map<String, String> Parse() { return null;}
	
	public SocialNetworkResponse(String raw_response) { this.ResponseText = raw_response; }
 
	public String getResponseString() { return this.ResponseText; }
 
	public String getResponseType() { return Response.Resolver.getType(this.ResponseText); }
 

}