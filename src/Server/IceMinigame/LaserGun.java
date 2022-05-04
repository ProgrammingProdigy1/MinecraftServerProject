package Server.IceMinigame;

import java.util.List;
import java.util.function.Predicate;

import org.bukkit.Color;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.World;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import Server.main.ServerMain;
import Server.utils.PointMarker;
import Server.utils.SQL;

public class LaserGun implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			Player player = event.getPlayer();
			if (player.getInventory().getItemInMainHand().getItemMeta() != null && player.getInventory()
					.getItemInMainHand().getItemMeta().getDisplayName().contains("Coal Pickaxe")) {
				final World world = player.getWorld();
				Vector direction = player.getEyeLocation().getDirection().normalize();
				Location loc = player.getEyeLocation();
				RayTraceResult res = world.rayTrace(loc, direction, 1, FluidCollisionMode.ALWAYS, true, 0.05,
						new Predicate<Entity>() {

							@Override
							public boolean test(Entity t) {
								return !(t instanceof Player)
										|| !(((Player) t).getUniqueId().equals(player.getUniqueId()));
							}
						});

				if (res != null && res.getHitBlock() != null)
					return;
				new BukkitRunnable() {
					double t = 0;
					Vector direction = player.getEyeLocation().getDirection().normalize();
					Location loc = player.getEyeLocation();
					double x, y, z;
					int i;
					RayTraceResult res = world.rayTrace(loc, direction, 1, FluidCollisionMode.ALWAYS, true, 0.05,
							new Predicate<Entity>() {

								@Override
								public boolean test(Entity t) {
									return !(t instanceof Player)
											|| !(((Player) t).getUniqueId().equals(player.getUniqueId()));
								}
							});

					@Override
					public void run() {
						for (i = 0; i < 8; i++) {
							if (res != null && res.getHitBlock() != null)
								this.cancel();
							else if (res != null && res.getHitEntity() != null
									&& res.getHitEntity() instanceof Damageable)
								((Damageable) res.getHitEntity()).damage(4);

							t += 0.25;
							x = direction.getX() * t;
							y = direction.getY() * t;
							z = direction.getZ() * t;
							loc.add(x, y, z);
							res = world.rayTrace(loc, direction, 1, FluidCollisionMode.ALWAYS, true, 0.05,
									new Predicate<Entity>() {

										@Override
										public boolean test(Entity t) {
											return !(t instanceof Player);
										}
									});
							DustOptions dust = new DustOptions(Color.fromRGB(255, 0, 0), 1);
							loc.getWorld().spawnParticle(Particle.REDSTONE, loc.getX(), loc.getY(), loc.getZ(), 10, 0,
									0, 0, dust);
							loc.subtract(x, y, z);
							if (t > 15 * (player.isGliding() ? 1.5 : 1))
								this.cancel();
						}
					}
				}.runTaskTimer(ServerMain.getPlugin(ServerMain.class), 0, 1);
			}
		}
	}
}
