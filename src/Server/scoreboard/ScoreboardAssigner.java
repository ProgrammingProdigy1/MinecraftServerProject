package Server.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import Server.bank.MoneyReceiveEvent;
import Server.bank.MoneySpendEvent;
import Server.main.ServerMain;
import Server.utils.SettingsManager;

public class ScoreboardAssigner implements Listener{
	
	private ScoreboardManager serverScoreboards;
	
	public ScoreboardAssigner(ScoreboardManager manager) {
		this.serverScoreboards = manager;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Scoreboard serverScoreboard = serverScoreboards.getNewScoreboard();
		Objective obj = serverScoreboard.registerNewObjective("ServerPro", "dummy", "§9Server§4Pro");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		SettingsManager sm = ServerMain.getManager();
		Player player = event.getPlayer();
		String path = "bank." + player.getName() + ".balance";
		double balance = sm.getConfig().getDouble(path);
		Score score = obj.getScore(ChatColor.GOLD + "Balance: ");
		score.setScore((int)balance);
		player.setScoreboard(serverScoreboard);
	}
	
	@EventHandler
	public void PlayerSpendMoney(MoneySpendEvent event) {
		Player player = event.getPlayer();
		Scoreboard serverScoreboard = player.getScoreboard();
		Objective obj = serverScoreboard.getObjective("ServerPro");
		SettingsManager sm = ServerMain.getManager();
		String path = "bank." + player.getName() + ".balance";
		double balance = sm.getConfig().getDouble(path);
		Score score = obj.getScore(ChatColor.GOLD + "Balance: ");
		score.setScore((int)balance);
	}
	
	@EventHandler
	public void PlayerReceiveMoney(MoneyReceiveEvent event) {
		Player player = event.getPlayer();
		Scoreboard serverScoreboard = player.getScoreboard();
		Objective obj = serverScoreboard.getObjective("ServerPro");
		SettingsManager sm = ServerMain.getManager();
		String path = "bank." + player.getName() + ".balance";
		double balance = sm.getConfig().getDouble(path);
		Score score = obj.getScore(ChatColor.GOLD + "Balance: ");
		score.setScore((int)balance);
	}
}
