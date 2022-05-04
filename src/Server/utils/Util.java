package Server.utils;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import Server.equipments.ArcherWarriorKit;
import Server.equipments.ProWarriorKit;
import Server.equipments.PvpWarriorKit;
import Server.equipments.WarriorKit;
import unit4.collectionsLib.Node;

public class Util {

	public static World world = Bukkit.getWorld("world");
	private static final Location SPAWN = new Location(world, 0, 65, 0);

	private static WarriorKit[] kits = null;

	public static void initialize() {
		world.setSpawnLocation(SPAWN);
		ItemStack sword = new ItemStack(Material.IRON_SWORD, 1);
		kits = new WarriorKit[3];
		PotionEffectType type = PotionEffectType.NIGHT_VISION;
		PotionEffect effect = new PotionEffect(type, 999999999, 200);
		kits[0] = new PvpWarriorKit(sword, Material.APPLE, 3, effect);
		kits[1] = new ProWarriorKit(sword, Material.APPLE, 6, Enchantment.DAMAGE_ALL, 1, effect);
		kits[2] = new ArcherWarriorKit(sword, Material.APPLE, 4, 10, effect);
	}

	public static boolean kitPlayer(Player player, String warriorLevel) {
		if(kits == null)
			return false;
		int len = kits.length, i;
		A: for(i = 0; i < len; i++) {
			if(kits[i].getWarriorLevel().equalsIgnoreCase(warriorLevel))
				break A;
		}
		if(i == len) {
			player.sendMessage(ChatColor.RED + "the kit '" + warriorLevel + "' does not exist.");
			return false;
		}
		kits[i].kitPlayer(player);
		return true;
	}

	public static ChatColor getColour(Player player) {
		if (player.getName().equalsIgnoreCase("JonJonTV")) {
			return ChatColor.AQUA;
		}

		if (player.isOp()) {
			return ChatColor.GOLD;
		}

		if (player.hasPermission("Server.unique")) {
			Random rnd = new Random();
			ChatColor[] colors = { ChatColor.BLUE, ChatColor.YELLOW, ChatColor.GREEN, ChatColor.RED };
			return colors[rnd.nextInt(colors.length)];
		}
		return ChatColor.GRAY;
	}

	public static boolean inSpawn(Location loc) {
		return inSpawn(loc, 2);
	}

	public static boolean inSpawn(Location loc, int radius) {
		if (loc == null)
			return false;
		int x = Math.abs(loc.getBlockX() - SPAWN.getBlockX());
		int z = Math.abs(loc.getBlockZ() - SPAWN.getBlockZ());
		return (x <= radius) && (z <= radius);
	}

	public static Location getSpawn() {
		return SPAWN;
	}
}
