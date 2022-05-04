package Server.RockMinigame;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import Server.main.ServerMain;

public class RockTornado extends RockAbility implements Listener {

	@EventHandler
	public void shift(PlayerToggleSneakEvent event) {
		Player player = event.getPlayer();
		if (player.getInventory().getItemInMainHand().getItemMeta() != null
				&& player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Coal Pickaxe")) {
			new BukkitRunnable() {
				Location loc = player.getLocation();
				double t = 0, r = 0.5;

				public void run() {
					for (int i = 0; i < 2; i++) {
						t = t + Math.PI / 32;
						double x = Math.min(r, 0.5 + 0.005 * 100) * Math.cos(t);
						double y = t / 16;
						double z = Math.min(r, 0.5 + 0.005 * 100) * Math.sin(t);
						r += 0.005;
						loc.add(x, y, z);
						loc.getWorld().spawnParticle(Particle.DRIP_WATER, loc, 10, 0, 0, 0);
						loc.subtract(x, y, z);
						if (t > Math.PI * 10) {
							this.cancel();
						}
					}
				}
			}.runTaskTimer(ServerMain.getPlugin(ServerMain.class), 0, 1);
		}
	}
}
