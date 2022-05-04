package Server.mailService;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import Server.main.ServerMain;
import unit4.collectionsLib.Queue;

public class MailBoxAssigner implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		MailBoxManager mbm = ServerMain.getMailBoxManager();
		mbm.reloadData();
		if (!(mbm.getData().contains(player.getName()))) {
			String name = player.getName();
			AdressManager am = ServerMain.getAdressManager();
			am.reloadData();
			String adress = am.getData().getString(player.getName());
			MailBox box = new MailBox(player, adress);
			mbm.getData().set(name + ".owner", box.getPlayer().getName());
			mbm.getData().set(name + ".mailAdress", box.getEmailAdress());
			mbm.getData().set(name + ".size", box.getSIZE());
			mbm.getData().set(name + ".unreadMails", box.getUnreadMails());
			mbm.saveData();
		}
	}
}
