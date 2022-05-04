package Server.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import Server.bank.MoneySpendEvent;
import Server.main.ServerMain;
import Server.utils.SettingsManager;

public class MoneyListener implements Listener {

	@EventHandler
	public void onMoneySpent(MoneySpendEvent event) {
		SettingsManager sm = ServerMain.getManager();
		Player p = event.getPlayer();
		String pBalance = "bank." + p.getName() + ".balance";
		double currentBalance = sm.getConfig().getDouble(pBalance);
		double withraw = event.getMoney();
		sm.getConfig().set(pBalance, Math.max(0, currentBalance - withraw));
		sm.saveConfig();
		double reallySpent = currentBalance - Math.max(currentBalance - withraw, 0);
		event.getPlayer().sendMessage("you just spent " + reallySpent + "$");
	}
}
