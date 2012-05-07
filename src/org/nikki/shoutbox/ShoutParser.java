package org.nikki.shoutbox;

import java.io.IOException;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.nikki.shoutbox.event.HTMLShoutEvent;
import org.nikki.shoutbox.event.ShoutEvent;
import org.nikki.shoutbox.event.ShoutEventType;

public class ShoutParser implements Runnable {

	private LinkedList<Shout> shouts = new LinkedList<Shout>();

	private static Pattern shoutPattern = Pattern
			.compile("\\[(.*?)\\]\\s(.*?):\\s(.*)\\s*");

	private ShoutboxApi api;

	public ShoutParser(ShoutboxApi api) {
		this.api = api;
	}

	@Override
	public void run() {
		while (api.isActive()) {
			try {
				String parsed = api.getConnection().get("infernoshout.php");
				String[] data = parsed.split("</div>");
				// Reversed so it parses older messages FIRST
				for (int i = data.length - 1; i >= 0; i--) {
					String s = data[i];
					s = s.replaceAll("\n", "");
					s = s.replaceAll("<(.|\\n)*?>", "");
					Matcher matcher = shoutPattern.matcher(s);
					if (matcher.find()) {
						Shout shout = new Shout(matcher.group(1).trim(),
								matcher.group(2).trim(), matcher.group(3)
										.trim());
						if (!shouts.contains(shout)) {
							// NEW
							api.event(new ShoutEvent(shout),
									ShoutEventType.SHOUT_RECEIVED);
							push(shout);
						}
					}
				}
				api.event(new HTMLShoutEvent(parsed), ShoutEventType.SHOUT_HTML);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void push(Shout shout) {
		// Store 100 shouts max
		if (shouts.size() + 1 >= 100) {
			shouts.removeFirst();
		}
		shouts.add(shout);
	}
}
