package Server.bank;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import Server.main.ServerMain;
import Server.utils.SettingsManager;
public class BalanceCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length != 0) {
			sender.sendMessage(ChatColor.RED + "Incorrect command!");
			return false;
		}
		
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can have a bank account!");
			return false;
		}
		Player player = (Player)sender;
		SettingsManager sm = ServerMain.getManager();
		if(!(BankServices.hasAccount(player)))
			BankServices.createAccount(player);
		String path = "bank." + player.getName() + ".balance";
		double balance = sm.getConfig().getDouble(path);
		player.sendMessage("Your balance is: " + balance);
		return true;
	}
}
