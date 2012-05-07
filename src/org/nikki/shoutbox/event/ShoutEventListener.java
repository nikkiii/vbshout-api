package org.nikki.shoutbox.event;

/**
 * A basic Shout Listener, when implemented provides shoutPosted, shoutReceived, shoutFailed methods
 * @author Nikki
 *
 */
public interface ShoutEventListener {
	
	public void shoutPosted(ShoutEvent event);
	
	public void shoutReceived(ShoutEvent event);
	
	public void shoutFailed(ShoutEvent event);
}
