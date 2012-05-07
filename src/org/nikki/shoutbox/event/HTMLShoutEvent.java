package org.nikki.shoutbox.event;

/**
 * Represents an event which can be handled for custom parsing of the html
 * 
 * @author Nikki
 *
 */
public class HTMLShoutEvent extends ShoutEvent {

	private String html;
	
	public HTMLShoutEvent(String html) {
		super(null);
		this.html = html;
	}

	public String getHTML() {
		return html;
	}
}
