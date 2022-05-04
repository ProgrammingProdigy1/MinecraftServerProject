package Server.IceMinigame;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import Server.main.ServerMain;

public class FlightSuit implements Listener {

	final int BOOST_SLOT = 1;
	private List<Player> cooldowns;

	public FlightSuit() {
		cooldowns = new ArrayList<Player>();
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (player.isGliding()) {
			Vector direction = player.getLocation().getDirection();
			player.setVelocity(direction.multiply(0.75));
			} 
			Vector direction = player.getLocation().getDirection();
			if(direction.add(direction.clone().normalize()).toLocation(player.getWorld()).getBlock().getType() != Material.AIR) {
//				System.out.println(direction.clone().add(direction.clone().normalize()).toLocation(player.getWorld()).toString());
			}
		
	}

	@EventHandler
	public void onToggleElytra(EntityToggleGlideEvent event) {
		if (event.getEntity() instanceof Player && event.isGliding()) {
			Player player = (Player) event.getEntity();
			player.sendMessage(ChatColor.GREEN + "Flight suit enabled");
			player.setMaxHealth(30);
			player.setHealth(player.getMaxHealth());
			ItemStack chest = player.getInventory().getChestplate();
			ItemMeta meta = chest.getItemMeta();
			if (!(meta.isUnbreakable())) {
				meta.setUnbreakable(true);
				chest.setItemMeta(meta);
				player.getInventory().setChestplate(chest);
			}
		} else if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			cooldowns.remove(player);
			player.sendMessage(ChatColor.RED + "Flight suit disabled");
			player.setMaxHealth(20);
			player.setHealth(player.getMaxHealth());
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (player.isGliding() && event.getAction() == Action.RIGHT_CLICK_AIR) {
			player.setVelocity(player.getLocation().getDirection().multiply(3));
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ServerMain.getPlugin(ServerMain.class),
					new Runnable() {
						@Override
						public void run() {
							player.setVelocity(player.getVelocity().multiply(1 / 3));
						}
					}, 100);
		}
	}

	@EventHandler
	public void onPlayerItemHoldEvent(PlayerItemHeldEvent e) {
		Player player = e.getPlayer();
		if (player.isGliding() && e.getNewSlot() == BOOST_SLOT && player.getInventory().getItem(e.getNewSlot()) == null
				&& !(cooldowns.contains(player))) {
			new BukkitRunnable() {
				double u = 1.25;
				int sign = 1;

				@Override
				public void run() {
					if (player.isGliding())
						player.setVelocity(player.getLocation().getDirection().normalize().multiply(u));
					else
						this.cancel();
					if (u >= 5)
						sign = -1;
					else if (u <= 1) {
						player.setVelocity(player.getLocation().getDirection().normalize().multiply(0.75));
						this.cancel();
					}
					u += 0.25 * sign;
				}
			}.runTaskTimer(ServerMain.getPlugin(ServerMain.class), 0, 1);
			cooldowns.add(player);
			Bukkit.getPluginManager().callEvent(new ElytraBoostEvent(player));
		}
	}

	@EventHandler
	public void boostCooldownEvent(ElytraBoostEvent e) {
		new BukkitRunnable() {

			@Override
			public void run() {
				cooldowns.remove(e.getPlayer());
			}
		}.runTaskLater(ServerMain.getPlugin(ServerMain.class), e.coolDownInSeconds * 20);
	}
}