package Server.RockMinigame;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import Server.main.ServerMain;

public class PushRockWall extends RockAbility implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
			Player player = event.getPlayer();
			Entity entity = (Entity) player;
			if (player.getInventory().getItemInMainHand().getItemMeta() != null
					&& player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Coal Pickaxe")
					&& entity.isOnGround()) {
				Location loc = player.getLocation();
				Vector dir = loc.getDirection().normalize();
				Entity mc = player.getWorld().spawn(player.getEyeLocation().clone().add(0, 0.5, 0), MagmaCube.class,
						MagmaCube -> {
							MagmaCube.setSize(1);
							MagmaCube.setSilent(true);
							MagmaCube.setRotation(loc.getYaw(), loc.getPitch());
							MagmaCube.setGravity(false);
							MagmaCube.setVelocity(player.getLocation().getDirection().multiply(2));
							MagmaCube.addScoreboardTag("Rock");
						});
				
			}
		}
	}

	public static void setType(final Block b, final Material m) {
		new BukkitRunnable() {
			public void run() {
				b.setType(m);
			}
		}.runTask(ServerMain.getPlugin(ServerMain.class));
	}
}