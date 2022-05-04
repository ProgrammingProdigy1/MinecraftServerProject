package Server.mailService;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import unit4.collectionsLib.Queue;

public class MailBox {

	private Mail[] mails;
	private Player player;
	private String emailAdress;
	private final static int SIZE = 10;
	private int unreadMails;
	
	public MailBox(Player player, String emailAdress) {
		this.player = player;
		this.mails = new Mail[SIZE];
		this.emailAdress = emailAdress;
		this.unreadMails = 0;
	}
	
	public void receiveMail(Mail mail) {
		Player sender = Bukkit.getServer().getPlayerExact(mail.getSender());
		if(sender == null)
			return;
		if(this.unreadMails < this.SIZE) {
			mails[unreadMails] = mail;
			this.unreadMails++;
			sender.sendMessage(ChatColor.GREEN + "Mail received successfully!");
		}
		else {
			sender.sendMessage(ChatColor.RED + "The player " + mail.getReceiver() + " cannot receive your mail because his mailbox is full!");	
		}
	}
	
	public Mail readMail() {
		if(this.unreadMails == 0) {
			player.sendMessage("You don't have unread mails");
			return null;
		}
		this.unreadMails--;
		Mail mail = mails[0];
		Mail[] arr = new Mail[SIZE];
		int i = 1;
		while(mails[i] != null) {
			arr[i-1] = mails[i];
			i++;
		}
		mails = arr;
		player.sendMessage("A mail sent by " + mail.getSender() + " was received at " + mail.getSendTime());
		return mail;
	}

	public Mail[] getMails() {
		return mails;
	}
	public Player getPlayer() {
		return player;
	}

	public String getEmailAdress() {
		return emailAdress;
	}

	public static int getSIZE() {
		return SIZE;
	}

	public int getUnreadMails() {
		return unreadMails;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setEmailAdress(String emailAdress) {
		this.emailAdress = emailAdress;
	}

	public void setUnreadMails(int unreadMails) {
		this.unreadMails = unreadMails;
	}

	@Override
	public String toString() {
		return "MailBox [mails=" + Arrays.toString(mails) + ", player=" + player.getName() + ", emailAdress=" + emailAdress + ", SIZE=" + SIZE
				+ ", unreadMails=" + unreadMails + "]";
	}
}