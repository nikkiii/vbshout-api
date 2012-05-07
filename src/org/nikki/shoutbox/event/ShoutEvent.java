package org.nikki.shoutbox.event;

import org.nikki.shoutbox.Shout;

/**
 * A basic event which wraps around a Shout
 * @author Nikki
 *
 */
public class ShoutEvent {
	
	private Shout shout;
	
	public ShoutEvent(Shout shout) {
		this.shout = shout;
	}

	public Shout getShout() {
		return shout;
	}
}
