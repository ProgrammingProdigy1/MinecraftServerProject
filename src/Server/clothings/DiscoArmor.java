package Server.clothings;

import java.util.Collection;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import Server.main.ServerMain;

public class DiscoArmor implements Listener {

	private Material material;
	private Color color;

	public DiscoArmor() {
		functionality();
		Bukkit.getScheduler().scheduleSyncRepeatingTask(ServerMain.getPlugin(ServerMain.class), new Runnable() {
			
			@Override
			public void run() {
				functionality();
			}
		}, 0, 5);
	}

	public DiscoArmor(Material material) {
		this.material = material;
	}

	public ItemStack toItemSack() {
		ItemStack armor = new ItemStack(material, 1);
		LeatherArmorMeta meta = (LeatherArmorMeta) armor.getItemMeta();
		meta.setColor(color);
		armor.setItemMeta(meta);
		return armor;
	}

	private static void functionality() {
		Collection<? extends Player> players = Bukkit.getOnlinePlayers();
		for (Player p : players) {
			if (p.getInventory().getChestplate() == null
					|| p.getInventory().getChestplate().getType() != Material.LEATHER_CHESTPLATE)
				continue;
			DiscoArmor armor = new DiscoArmor(Material.LEATHER_CHESTPLATE);
			Random rnd = new Random();
			int r, g, b;
			r = rnd.nextInt(255);
			g = rnd.nextInt(255);
			b = rnd.nextInt(255);
			armor.color = Color.fromBGR(b, g, r);
			p.getInventory().setChestplate(armor.toItemSack());
		}
	}
}
