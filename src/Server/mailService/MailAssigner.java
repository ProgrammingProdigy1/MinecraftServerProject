package Server.mailService;

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

public class MailAssigner implements Listener, CommandExecutor {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		MailManager mm = ServerMain.getMailManager();
		mm.reloadData();
		if (!(mm.getData().contains(player.getName()))) {
			mm.getData().set(player.getName(), "");
			mm.saveData();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "only players can perform mail methods");
			return false;
		}
		Player player = (Player) sender;
		if (args.length == 0) {
			player.sendMessage(ChatColor.RED + "Please specify a mail method (READ/SEND)");
			return false;
		}
		if (args[0].equalsIgnoreCase("send") && args.length > 2) {
			String receiver = args[1];
			Player target = Bukkit.getServer().getPlayerExact(receiver);
			if (target == null) {
				player.sendMessage(ChatColor.RED + "The player " + args[1]
						+ " does not exist or isn't connected to the server at this moment");
				return false;
			}
			AdressManager am = ServerMain.getAdressManager();
			am.reloadData();
			MailBox box = new MailBox(target, am.getData().getString(receiver));
			MailManager mm = ServerMain.getMailManager();
			mm.reloadData();
			MailBoxManager mbm = ServerMain.getMailBoxManager();
			mbm.reloadData();
			box.setUnreadMails(mbm.getData().getInt(receiver + ".unreadMails"));
			int unread = box.getUnreadMails();
			for(int i = 1; i <= unread; i++) {
				Mail m = new Mail(null, null, null);
				m.setSender(mm.getData().getString(receiver + ".mail" + i + ".sender"));
				m.setReceiver(mm.getData().getString(receiver + ".mail" + i + ".receiver"));
				m.setSenderAdress(mm.getData().getString(receiver + ".mail" + i + ".senderAdress"));
				m.setReceiverAdress(mm.getData().getString(receiver + ".mail" + i + ".receiverAdress"));
				m.setSendTime(mm.getData().getString(receiver + ".mail" + i + ".sendTime"));
				m.setContent(mm.getData().getString(receiver + ".mail" + i + ".content"));
				box.receiveMail(m);
			}
			String adressS = am.getData().getString(player.getName());
			String adressR = am.getData().getString(receiver);
			Mail mail = new Mail(player.getName(), receiver, "", adressS, adressR);
			StringBuilder build = new StringBuilder();
			int len = args.length;
			for (int i = 2; i < len; i++)
				build.append(args[i] + " ");
			mail.setContent(build.toString());
			box.receiveMail(mail);
			mbm.getData().set(receiver + ".unreadMails", box.getUnreadMails());
			mbm.saveData();
			mm.getData().set(player.getName() + ".mail" + box.getUnreadMails() + ".sender", player.getName());
			mm.getData().set(player.getName() + ".mail" + box.getUnreadMails() + ".receiver", receiver);
			mm.getData().set(player.getName() + ".mail" + box.getUnreadMails() + ".senderAdress", adressS);
			mm.getData().set(player.getName() + ".mail" + box.getUnreadMails() + ".receiverAdress", adressR);
			mm.getData().set(player.getName() + ".mail" + box.getUnreadMails() + ".sendTime", mail.getSendTime());
			mm.getData().set(player.getName() + ".mail" + box.getUnreadMails() + ".content", mail.getContent());
			mm.getData().set(player.getName() + ".mail" + box.getUnreadMails() + ".SIZE",
			mail.getMaxSize(player.getName()));
			mm.saveData();
		} else if (args[0].equalsIgnoreCase("send")){
			player.sendMessage(ChatColor.RED + "Invalid send method activation");
		}

		if (args[0].equalsIgnoreCase("read")) {
			AdressManager am = ServerMain.getAdressManager();
			am.reloadData();
			String adressS = am.getData().getString(player.getName());
			MailBox box = new MailBox(player, adressS);
			MailBoxManager mbm = ServerMain.getMailBoxManager();
			int unread = mbm.getData().getInt(player.getName() + ".unreadMails");
			MailManager mm = ServerMain.getMailManager();
			for(int i = 1; i <= unread; i++) {
				Mail mail = new Mail(null, null, null);
				mail.setSender(mm.getData().getString(player.getName() + ".mail" + i + ".sender"));
				mail.setReceiver(mm.getData().getString(player.getName() + ".mail" + i + ".receiver"));
				mail.setSenderAdress(mm.getData().getString(player.getName() + ".mail" + i + ".senderAdress"));
				mail.setReceiverAdress(mm.getData().getString(player.getName() + ".mail" + i + ".receiverAdress"));
				mail.setSendTime(mm.getData().getString(player.getName() + ".mail" + i + ".sendTime"));
				mail.setContent(mm.getData().getString(player.getName() + ".mail" + i + ".content"));
				box.receiveMail(mail);
			}
			Mail mail = box.readMail();
			if(unread <= 0)
				return false;
			player.sendMessage("Content: " + mail.getContent());
			mm.getData().set(player.getName() + ".mail" + (box.getUnreadMails() + 1), null);
			mm.saveData();
			mbm.getData().set(player.getName() + ".unreadMails", box.getUnreadMails());
			mbm.saveData();
		}
		return true;
	}
}
