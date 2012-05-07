package org.nikki.shoutbox;

/**
 * A class to represent an individual Shout
 * 
 * @author Nikki
 * 
 */
public class Shout {
	private String time;
	private String user;
	private String message;

	public Shout(String time, String user, String message) {
		this.time = time;
		this.user = user;
		this.message = message;
	}

	public String getTime() {
		return time;
	}

	public String getUser() {
		return user;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "[" + time + "] " + user + ": " + message;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Shout)) {
			return false;
		}
		Shout other = (Shout) object;
		if (!time.equals(other.getTime())) {
			return false;
		}
		if (!user.equals(other.getUser())) {
			return false;
		}
		if (!message.equals(other.getMessage())) {
			return false;
		}
		return true;
	}
}