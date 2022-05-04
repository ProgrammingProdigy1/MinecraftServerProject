package Server.bank;

import java.util.Random;

import org.bukkit.entity.Player;

public class BankAccount {

	private Player player;
	private String userName, password;
	private double loanFromBank, balance;
	private int axisSituation, loanPeriodInDays = 0, interest = 0;
	static Random rnd = new Random();

	public BankAccount(Player player) {
		this.player = player;
		this.loanFromBank = 0;
		this.loanPeriodInDays = 0;
		this.balance = 1000;
		this.axisSituation = 0;
		this.userName = randomUser();
		this.password = "";
		this.interest = 0;
	}

	public String randomUser() {
		if (player == null)
			return null;
		String userName = player.getName();
		userName += rnd.nextInt(999) + 1;
		return userName;
	}

	public Player getPlayer() {
		return player;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public double getLoanFromBank() {
		return loanFromBank;
	}

	public double getBalance() {
		return balance;
	}

	public int getAxisSituation() {
		return axisSituation;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setLoanFromBank(double loanFromBank) {
		this.loanFromBank = loanFromBank;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public void setAxisSituation(int axisSituation) {
		this.axisSituation = axisSituation;
	}

	public int getLoanPeriodInDays() {
		return loanPeriodInDays;
	}

	public int getInterest() {
		return interest;
	}

	public void setLoanPeriodInDays(int loanPeriodInDays) {
		this.loanPeriodInDays = loanPeriodInDays;
	}
}
