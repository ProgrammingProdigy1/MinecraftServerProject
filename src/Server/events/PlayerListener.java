package Server.events;

import java.lang.reflect.Constructor;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;

import Server.bank.BankServices;
import Server.main.ServerMain;
import Server.utils.Reflection;
import Server.utils.SettingsManager;
import Server.utils.Util;

public class PlayerListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		event.setJoinMessage(null);
		SettingsManager sm = ServerMain.getManager();
		String motd = sm.getConfig().getString("motd.message");
		motd = motd.replaceAll("&", "�");
		player.sendMessage(ChatColor.GREEN + motd);
		World w = Bukkit.getWorld("world");
		double x = sm.getConfig().getDouble("spawn.x"), y = sm.getConfig().getDouble("spawn.y"),
				z = sm.getConfig().getDouble("spawn.z");
		Location loc = new Location(w, x, y, z);
		if (loc.equals(new Location(w, 0, 0, 0))) {
			player.teleport(Util.getSpawn());
		} else
			player.teleport(loc);
		if (!player.hasPlayedBefore())
			BankServices.createAccount(player);
	}

	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		event.setLeaveMessage(null);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		event.setQuitMessage(null);
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		String msg = event.getDeathMessage();
		msg = ChatColor.GOLD + msg;
		if (event.getEntity().getKiller() instanceof Player) {
			event.setDeathMessage("[PVP] " + msg);
			return;
		}
		event.setDeathMessage("[PVE] " + msg);
	}

	@EventHandler
	public void onPlayerHunger(FoodLevelChangeEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Location from = event.getFrom(), to = event.getTo();
		Player player = event.getPlayer();
		if (Util.inSpawn(to)) {
			player.setHealth(20);
		} else if (Util.inSpawn(from))
			player.sendMessage(ChatColor.RED + "You are now in PvP zone! Be careful!");
		if (to.getBlock().getType() == Material.LIGHT_WEIGHTED_PRESSURE_PLATE) {
			player.setVelocity(player.getLocation().getDirection().multiply(4));
			player.setVelocity(
					new Vector(event.getPlayer().getVelocity().getX(), 1.0D, event.getPlayer().getVelocity().getZ()));
		}
		if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SLIME_BLOCK || player
				.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.LEGACY_SLIME_BLOCK) {
			player.setVelocity(
					new Vector(event.getPlayer().getVelocity().getX(), 1.5D, event.getPlayer().getVelocity().getZ()));
		}
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		ChatColor colour = Util.getColour(player);
		String msg = colour + player.getName() + ": " + ChatColor.RESET + event.getMessage();
		event.setFormat(msg);
		try {
			Object enumTitle = Reflection.getClass("{nms}.PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE")
					.get(null);
			Object chat = Reflection.getClass("{nms}.IChatBaseComponent").getDeclaredClasses()[0]
					.getMethod("a", String.class)
					.invoke(null, String.format("{\"text\":\"You sent a message\",\"color\":\"%s\"}",
							Util.getColour(player).name().toLowerCase()));

			Constructor<?> titleConstructor = Reflection.getClass("{nms}.PacketPlayOutTitle").getConstructor(
					Reflection.getClass("{nms}.PacketPlayOutTitle").getDeclaredClasses()[0],
					Reflection.getClass("{nms}.IChatBaseComponent"), int.class, int.class, int.class);
			Object packet = titleConstructor.newInstance(enumTitle, chat, 20, 40, 20);
			Reflection.sendPlayerPacket(player, packet);
		}

		catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@EventHandler
	public void onPlayerDamaged(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			DamageCause dc = event.getCause();
			switch (dc) {
			case ENTITY_ATTACK:
			case ENTITY_SWEEP_ATTACK:
				break;
			case FALL:
				Player player = (Player) event.getEntity();
				Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
				break;
			default:
				event.setDamage(0);
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Block b = event.getClickedBlock();
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK && b.getState() instanceof Sign) {
			Sign sign = (Sign) b.getState();
			if (sign.getLine(0).equalsIgnoreCase("[Name]")) {
				Player player = event.getPlayer();
				String[] args = { player.getName(), "", "", "" };
				player.sendSignChange(b.getLocation(), args);
			}
		}
	}

	@EventHandler
	public void onPlayerFall(EntityDamageByBlockEvent event) {
		if (event.getEntity() instanceof Player && event.getCause() == DamageCause.FALL
				&& event.getDamager().getType() == Material.LIGHT_WEIGHTED_PRESSURE_PLATE) {
			Player player = (Player) event.getEntity();
			event.setDamage(0);
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {

	}

	@EventHandler
	public void playerEnterPortal(PlayerPortalEvent event) {
		Location loc = event.getPlayer().getLocation();
		loc.setWorld(Bukkit.getWorld("world"));
		event.setTo(loc);
	}
}
