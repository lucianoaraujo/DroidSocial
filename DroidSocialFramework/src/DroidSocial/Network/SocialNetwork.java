package DroidSocial.Network;

/**
 * Base class for representing all that is common to social networks.
 * @author Joey
 *
 */
public abstract class SocialNetwork {

	private String ServiceURI = "";
	private String API_KEY = "";
	private String NetworkName = "";
	protected String NetworkCode;
	private SocialNetworkLoginListener NetworkLogin = null;
	
	private SocialNetworkRequest Req = null;
	private SocialNetworkUser User = null;
	
	public SocialNetwork(String network_name, String api_key, SocialNetworkLoginListener net_login) throws InvalidLoginListenerException {
		this.API_KEY = api_key;
		this.NetworkName = network_name;
		if (net_login == null) throw new InvalidLoginListenerException();
	}
}