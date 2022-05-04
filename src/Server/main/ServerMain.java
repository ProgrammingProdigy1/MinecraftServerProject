package Server.main;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import Server.IceMinigame.FlightSuit;
import Server.IceMinigame.GameManager;
import Server.IceMinigame.LaserGun;
import Server.RockMinigame.PushRockWall;
import Server.RockMinigame.RockTornado;
import Server.Sounds.SoundPlayer;
import Server.bank.BalanceCommand;
import Server.bank.LoanCommand;
import Server.clothings.DiscoArmor;
import Server.cmds.ChestPlaceCommand;
import Server.cmds.FakejoinCommand;
import Server.cmds.FakeopCommand;
import Server.cmds.KitCommand;
import Server.cmds.MarkCommand;
import Server.cmds.MotdCommands;
import Server.cmds.NpcAddition;
import Server.cmds.SpawnCommand;
import Server.cmds.SpawnsetCommand;
import Server.cmds.TeleportCommand;
import Server.cmds.WarnCommand;
import Server.cmds.WithrawMoneyCommand;
import Server.events.BlockListener;
import Server.events.BombListener;
import Server.events.DamageListener;
import Server.events.Grappler;
import Server.events.Graves;
import Server.events.InventoryListener;
import Server.events.MedicListener;
import Server.events.MoneyListener;
import Server.events.MotdListener;
import Server.events.PlayerListener;
import Server.events.Subscribe;
import Server.mailService.AdressAssigner;
import Server.mailService.AdressManager;
import Server.mailService.MailAssigner;
import Server.mailService.MailBoxAssigner;
import Server.mailService.MailBoxManager;
import Server.mailService.MailManager;
import Server.notifications.ResourcePackUpdate;
import Server.scoreboard.ScoreboardAssigner;
//import Server.utils.NpcLauncher;
import Server.utils.SettingsManager;
import Server.utils.Util;

public class ServerMain extends JavaPlugin {

	static SettingsManager manager = SettingsManager.getInstance();
	static AdressManager adressManager = AdressManager.getInstance();
	static MailBoxManager mailBoxManager = MailBoxManager.getInstance();
	static MailManager mailManager = MailManager.getInstance();
	public static final int LOBBIES = 3;

	public void onEnable() {

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerListener(), this);
		pm.registerEvents(new DamageListener(), this);
		pm.registerEvents(new InventoryListener(), this);
		pm.registerEvents(new BlockListener(), this);
		pm.registerEvents(new MedicListener(), this);
		pm.registerEvents(new MoneyListener(), this);
		pm.registerEvents(new MotdListener(), this);
		pm.registerEvents(new BombListener(), this);
		pm.registerEvents(new AdressAssigner(), this);
		pm.registerEvents(new MailBoxAssigner(), this);
		pm.registerEvents(new MailAssigner(), this);
		pm.registerEvents(new SoundPlayer(), this);
		pm.registerEvents(new Grappler(), this);
		pm.registerEvents(new FlightSuit(), this);
		pm.registerEvents(new Graves(), this);
		pm.registerEvents(new Subscribe(), this);
		pm.registerEvents(new DiscoArmor(), this);
		pm.registerEvents(new LaserGun(), this);
		pm.registerEvents(new RockTornado(), this);
		pm.registerEvents(new PushRockWall(), this);
		pm.registerEvents(new ScoreboardAssigner(Bukkit.getServer().getScoreboardManager()), this);
		pm.registerEvents(new ResourcePackUpdate(), this);
		pm.registerEvents(new GameManager(), this);
		pm.registerEvents(new Server.RockMinigame.GameManager(), this);
		
//		Server.utils.NpcLauncher ice;
//		try {
//			ice = new Server.utils.NpcLauncher();
//			pm.registerEvents(ice, this);
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
		
		Util.initialize();

		getCommand("kit").setExecutor(new KitCommand());
		getCommand("fakeop").setExecutor(new FakeopCommand());
		getCommand("fakejoin").setExecutor(new FakejoinCommand());
		getCommand("MOTD").setExecutor(new MotdCommands());
		getCommand("tp").setExecutor(new TeleportCommand());
		getCommand("setspawn").setExecutor(new SpawnsetCommand());
		getCommand("spawn").setExecutor(new SpawnCommand());
		getCommand("balance").setExecutor(new BalanceCommand());
		getCommand("loan").setExecutor(new LoanCommand());
		getCommand("withraw").setExecutor(new WithrawMoneyCommand());
		getCommand("warn").setExecutor(new WarnCommand());
		getCommand("mailadress").setExecutor(new AdressAssigner());
		getCommand("mail").setExecutor(new MailAssigner());
		getCommand("mark").setExecutor(new MarkCommand());
		getCommand("playerAdd").setExecutor(new NpcAddition());
		getCommand("placeChest").setExecutor(new ChestPlaceCommand());

		manager.setup(this);
		adressManager.setup(this);
		mailBoxManager.setup(this);
		mailManager.setup(this);
			
//		try {
//				for(int i = 0; i < 1; i++) {
//					NpcLauncher.addServerNpc(
//							NpcLauncher.getNpcData()[i].getName(), 
//							NpcLauncher.getNpcData()[i].getX(), 
//							NpcLauncher.getNpcData()[i].getY(), 
//							NpcLauncher.getNpcData()[i].getZ(), 
//							NpcLauncher.getNpcData()[i].getPitch(), 
//							NpcLauncher.getNpcData()[i].getYaw(), 
//							NpcLauncher.getNpcData()[i].getTexture(), 
//							NpcLauncher.getNpcData()[i].getSignature());
//				}
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//		}
//		
		getConfig().options().copyDefaults(true);
		saveConfig();
//			Collection<? extends Player> lst = Bukkit.getOnlinePlayers();
//			for(Player p : lst) {
//				try {
//					Server.utils.NpcLauncher.packetReader().inject(p);
//				} catch (Exception e) {
//					e.printStackTrace();
//			}
//		}
	}

//	@Override
//	public void onDisable() {
//		Collection<? extends Player> lst = Bukkit.getOnlinePlayers();
//		for(Player p : lst) {
//			NpcLauncher.packetReader().uninject(p);
//		}
//	}
	
	public static SettingsManager getManager() {
		return manager;
	}

	public static AdressManager getAdressManager() {
		return adressManager;
	}

	public static MailBoxManager getMailBoxManager() {
		return mailBoxManager;
	}

	public static MailManager getMailManager() {
		return mailManager;
	}
}
