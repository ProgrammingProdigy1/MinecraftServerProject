package Server.bank;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import Server.main.ServerMain;
import Server.utils.SettingsManager;

public class BankServices {

	public static boolean hasAccount(Player player) {
		SettingsManager sm = ServerMain.getManager();
		sm.reloadConfig();
		if (sm.getConfig().getConfigurationSection("bank") != null) {
			return sm.getConfig().getConfigurationSection("bank").contains(player.getName());
		}
		return false;
	}

	public static void createAccount(Player player) {
		if (hasAccount(player))
			return;
		SettingsManager sm = ServerMain.getManager();
		if (sm.getConfig().getConfigurationSection("bank") == null) {
			sm.getConfig().createSection("bank");
			sm.saveConfig();
		}

		FileConfiguration config = sm.getConfig();
		ConfigurationSection cs = config.getConfigurationSection("bank");
		BankAccount ba = new BankAccount(player);
		String sec = "bank." + player.getName() + ".";
		config.set("bank." + player.getName(), ba.getPlayer().getName());
		config.set(sec + "username", ba.getUserName());
		config.set(sec + "password", ba.getPassword());
		config.set(sec + "balance", ba.getBalance());
		config.set(sec + "loan", ba.getLoanFromBank());
		config.set(sec + "loanperiod", ba.getLoanPeriodInDays());
		config.set(sec + "axis", ba.getAxisSituation());
		config.set(sec + "interest", ba.getInterest());
		sm.saveConfig();
	}
}
