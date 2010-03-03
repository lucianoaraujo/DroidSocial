package DroidSocial.Network;

/**
 * Thrown when SocialNetwork object has been assigned a null SocialNetworkLoginListener reference.
 * @author Joey
 *
 */
public class InvalidLoginListenerException extends Exception {
	public InvalidLoginListenerException() {
		super("SocialNetwork requires a valid SocialNetworkLoginListener object reference.");
	}
}
