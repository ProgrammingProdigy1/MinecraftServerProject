package Server.cmds;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Server.bank.MoneySpendEvent;
import Server.main.ServerMain;
import Server.utils.SettingsManager;

public class WithrawMoneyCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can withraw money!");
			return false;
		}
		Player player = (Player)sender;
		if(args.length != 1) {
			player.sendMessage(ChatColor.RED + "Illegal command");
			return false;
		}
		double toWithraw = Double.valueOf(args[0]);
		Bukkit.getPluginManager().callEvent(new MoneySpendEvent(toWithraw, player));
		player.sendMessage(ChatColor.GREEN + "The withrawal successfully completed");
		return true;
	}
}
