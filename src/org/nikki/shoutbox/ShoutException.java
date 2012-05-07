package org.nikki.shoutbox;

/**
 * A basic ShoutException...
 * 
 * @author Nikki
 * 
 */
public class ShoutException extends RuntimeException {

	private static final long serialVersionUID = -8097183361524228876L;

	public ShoutException(String message) {
		super(message);
	}

	public ShoutException(Exception e) {
		super(e);
	}
}
