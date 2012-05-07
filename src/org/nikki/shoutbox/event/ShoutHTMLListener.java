package org.nikki.shoutbox.event;

/**
 * Basically a ShoutEventListener, though it provides methods for HTML
 * @author Nikki
 *
 */
public interface ShoutHTMLListener extends ShoutEventListener {
	public void shoutHTMLReceived(HTMLShoutEvent event);
}
