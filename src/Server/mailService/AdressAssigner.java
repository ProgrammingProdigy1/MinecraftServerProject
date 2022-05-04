package Server.mailService;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.TabCompleteEvent;

import Server.main.ServerMain;

public class AdressAssigner implements Listener, CommandExecutor{
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		AdressManager am = ServerMain.getAdressManager();
		am.reloadData();
		if(!(am.getData().contains(player.getName()))) {
			String name = player.getName();
			am.getData().set(name, "");
			am.saveData();
		}
	}
	
	 @EventHandler
	 public void onChatTab(TabCompleteEvent event) {
		if(event.getBuffer().equals("/mailadress set ")) {
			List<String> completions = event.getCompletions();
			completions.add("@Server.com");
			event.setCompletions(completions);
		}
		if(event.getBuffer().contains("@Server.com")) {
			List<String> completions = event.getCompletions();
			completions.remove("@Server.com");
			event.setCompletions(completions);
		}
	 }
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "only players can perform mail adress methods");
			return false;
		}
		Player player = (Player)sender;
		if(args.length == 0) {
			player.sendMessage(ChatColor.RED + "Please specify an adress method (GET/SET)");
			return false;
		}
		AdressManager am = ServerMain.getAdressManager();
		String name = player.getName();
		if(args[0].equalsIgnoreCase("get")) {
			am.reloadData();
			String adress = am.getData().getString(name);
			player.sendMessage("Your current mail adress is: '" + adress + "'");
			return true;
		}
		if(args[0].equalsIgnoreCase("set") && args.length == 2) {
			if(isValid(args[1])) {
			am.getData().set(name, args[1]);
			am.saveData();
			player.sendMessage(ChatColor.GREEN + "you mail adress was changed successfully!");
			return true;
			} else {
				player.sendMessage(ChatColor.RED + "the specified mail adress '" + args[1] + "' is not valid.");
				return false;
			}
		}
		player.sendMessage(ChatColor.RED + "Invalid command");
		return false;
	}
	
	public static boolean isValid(String mailAdress) {
		String sp = String.valueOf((char)32); //space key ' '
		return mailAdress.endsWith("@Server.com") && !(mailAdress.contains(sp));
	}
}
