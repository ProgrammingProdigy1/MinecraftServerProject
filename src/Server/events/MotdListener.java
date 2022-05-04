package Server.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import Server.main.ServerMain;
import Server.utils.SettingsManager;

public class MotdListener implements Listener{

	@EventHandler
	public void onServerPing(ServerListPingEvent event) {
		SettingsManager sm = ServerMain.getManager();
		String msg = sm.getConfig().getString("motd.system");
		msg = msg.replaceAll("&", "\u00A7");
		event.setMotd(msg);
	}
}
