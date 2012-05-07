package org.nikki.shoutbox;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.nikki.shoutbox.event.HTMLShoutEvent;
import org.nikki.shoutbox.event.ShoutEvent;
import org.nikki.shoutbox.event.ShoutEventListener;
import org.nikki.shoutbox.event.ShoutEventType;
import org.nikki.shoutbox.event.ShoutHTMLListener;
import org.nikki.shoutbox.util.VBulletinUtil;

/**
 * The main shoutbox api class
 * 
 * @author Nikki
 * 
 */
public class ShoutboxApi {

	private ExecutorService parseService = Executors.newSingleThreadExecutor();

	private AdvancedHttpConnection connection;

	private LinkedList<ShoutEventListener> listeners = new LinkedList<ShoutEventListener>();

	private boolean active = true;

	private String username;

	public ShoutboxApi(String url) {
		connection = new AdvancedHttpConnection(url);
	}

	public void start() {
		parseService.execute(new ShoutParser(this));
	}

	public boolean login(String username, String password)
			throws ShoutException {
		this.username = username;
		try {
			String resp = connection.post("login.php",
					VBulletinUtil.constructLogin(username, password));
			return !resp.contains("Invalid") && resp.contains("Thank you for");
		} catch (IOException e) {
			throw new ShoutException(e);
		}
	}

	public void shout(String message) throws ShoutException {
		try {
			String resp = connection.post("infernoshout.php",
					VBulletinUtil.constructShout(message));
			resp = resp.trim();
			if (resp.equals("completed")) {
				event(new ShoutEvent(new Shout("", username, message)),
						ShoutEventType.SHOUT_POSTED);
			} else {
				event(new ShoutEvent(new Shout("", username, message)),
						ShoutEventType.SHOUT_POST_FAILED);
			}
		} catch (IOException e) {
			throw new ShoutException(e);
		}
	}

	public void event(ShoutEvent event, ShoutEventType type) {
		synchronized (listeners) {
			for (ShoutEventListener listener : listeners) {
				switch (type) {
				case SHOUT_POSTED:
					listener.shoutPosted(event);
					break;
				case SHOUT_RECEIVED:
					listener.shoutReceived(event);
					break;
				case SHOUT_POST_FAILED:
					listener.shoutFailed(event);
					break;
				case SHOUT_HTML:
					if (listener instanceof ShoutHTMLListener) {
						((ShoutHTMLListener) listener)
								.shoutHTMLReceived((HTMLShoutEvent) event);
					}
					break;
				}
			}
		}
	}

	public void registerListener(ShoutEventListener listener) {
		synchronized (listeners) {
			listeners.add(listener);
		}
	}

	public AdvancedHttpConnection getConnection() {
		return connection;
	}

	public boolean isActive() {
		return active;
	}
}
