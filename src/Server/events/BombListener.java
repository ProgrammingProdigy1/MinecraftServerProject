package Server.events;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class BombListener implements Listener {

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {
		Projectile projectile = event.getEntity();
		if (projectile instanceof Snowball) {
			projectile = (Snowball) projectile;
			Location loc = projectile.getLocation();
			projectile.getWorld().createExplosion(loc, 25f);
			List<Entity> nearbyPlayers = projectile.getNearbyEntities(10, 10, 10);
			for (Entity e : nearbyPlayers) {
				if (e instanceof Player) {
					PotionEffectType type = PotionEffectType.POISON;
					PotionEffect effect = new PotionEffect(type, 200, 1);
					ItemMeta meta = new ItemStack(Material.POTION, 1).getItemMeta();
					PotionMeta pMeta = (PotionMeta) meta;
					pMeta.addCustomEffect(effect, true);
					ItemStack item = new ItemStack(Material.POTION, 1);
					item.setItemMeta(pMeta);
					((Player) e).addPotionEffect(effect);
					((Player) e).getInventory().addItem(item);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerConsume(PlayerItemConsumeEvent event) {
		Player player = event.getPlayer();
		if (event.getItem().getType() == Material.POTION) {
			ItemStack item = event.getItem();
			PotionMeta meta = (PotionMeta) item.getItemMeta();
			if (meta.getBasePotionData().getType() == PotionType.POISON) {
				PotionEffectType type = PotionEffectType.POISON;
				player.addPotionEffect(new PotionEffect(type, 200, 255));
				player.getInventory().getItemInMainHand().setItemMeta(meta);
				player.getInventory().setItemInMainHand(null);
				event.setCancelled(true);
			}
		}
	}
}
