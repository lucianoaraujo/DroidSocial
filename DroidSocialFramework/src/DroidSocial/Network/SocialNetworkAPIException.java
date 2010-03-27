package DroidSocial.Network;

public class SocialNetworkAPIException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3282349121216656395L;
	private String message;
	
	public SocialNetworkAPIException(String msg) {
		this.message = msg;
	}
	
	public String getMessage() {
		return this.message;
	}
} 
