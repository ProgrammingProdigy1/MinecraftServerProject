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

public class LoanCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length != 1) {
			sender.sendMessage(ChatColor.RED + "Incorrect command!");
			return false;
		}
		
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can loan money!");
			return false;
		}
		
		Player player = (Player)sender;
		if(!(BankServices.hasAccount(player))) {
			player.sendMessage(ChatColor.RED + "You don't have a bank account!");
			return false;
		}
		
		SettingsManager sm = ServerMain.getManager();
		
		if(!(BankServices.hasAccount(player)))
			BankServices.createAccount(player);
		FileConfiguration config = sm.getConfig();
		String path = "bank." + player.getName();
		double loanNew = config.getDouble(path + ".loan") + Double.valueOf(args[0]);
		config.set(path + ".loan", loanNew);
		sm.saveConfig();
		double balanceNew = config.getDouble(path + ".balance") + Double.valueOf(args[0]);
		config.set(path + ".balance", balanceNew);
		sm.saveConfig();
		Bukkit.getPluginManager().callEvent(new MoneyReceiveEvent(Double.valueOf(args[0]), player));
		player.sendMessage(ChatColor.GREEN + "you've Loaned " + args[0] + "$ from the bank");
		return true;
	}
}
