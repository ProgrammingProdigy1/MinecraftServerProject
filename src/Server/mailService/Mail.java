package Server.mailService;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Mail {

	private String sender, receiver;
	private final String DATE_FORMAT = "dd/MM/yyyy";
	private String senderAdress, receiverAdress, sendTime, content;
	private final Integer SIZE;

	SimpleDateFormat format;

	public Mail(String sender, String receiver, String[] content, String space, String adressS, String adressR) {
		this.sender = sender;
		this.receiver = receiver;
		format = new SimpleDateFormat(DATE_FORMAT);
		sendTime = format.format(new Date());
		int len = content.length;
		for (int i = 0; i < len; i++)
			this.content += content[i] + space;
		this.content = this.content.substring(0, len - 1);
		this.SIZE = Mail.getMaxSize(sender);
		this.senderAdress = adressS;
		this.receiverAdress = adressR;
	}

	public Mail(String sender, String receiver, String content, String adressS, String adressR) {
		this.sender = sender;
		this.receiver = receiver;
		format = new SimpleDateFormat(DATE_FORMAT);
		sendTime = format.format(new Date());
		this.content = content;
		this.SIZE = Mail.getMaxSize(sender);
		this.senderAdress = adressS;
		this.receiverAdress = adressR;
	}

	public Mail(String receiver, String content, String adressR) {
		this.sender = null;
		this.receiver = receiver;
		format = new SimpleDateFormat(DATE_FORMAT);
		sendTime = format.format(new Date());
		this.content = content;
		this.SIZE = null;
		this.senderAdress = "Console";
		this.receiverAdress = adressR;
	}

	public String getSender() {
		return sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public String getSenderAdress() {
		return senderAdress;
	}

	public String getReceiverAdress() {
		return receiverAdress;
	}

	public String getSendTime() {
		return sendTime;
	}

	public String getContent() {
		return content;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public void setSenderAdress(String senderAdress) {
		this.senderAdress = senderAdress;
	}

	public void setReceiverAdress(String receiverAdress) {
		this.receiverAdress = receiverAdress;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public static Integer getMaxSize(String sender) {
		Player player = Bukkit.getServer().getPlayerExact(sender);
		if (player == null)
			return null;
		return (player.isOp()) ? 20 : 10;
	}

	@Override
	public String toString() {
		return sender + "-" + receiver + "-" + content + "-" + senderAdress + "-" + receiverAdress + "-"
				+ sendTime;
	}
}
