package Server.equipments;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class ProWarriorKit extends WarriorKit {

	private Enchantment magic;
	private int magicLevel;

	public ProWarriorKit(ItemStack sword, Material foodType, int foodAmount, Enchantment magic, int magicLevel, PotionEffect effect) {
		super(sword, foodType, foodAmount, effect);
		ItemStack betterSword = this.getSword();
		betterSword.addEnchantment(magic, magicLevel);
		this.setSword(betterSword);
		setWarriorLevel("Pro");
	}

	public Enchantment getMagic() {
		return magic;
	}

	public int getMagicLevel() {
		return magicLevel;
	}

	public void setMagic(Enchantment magic) {
		this.magic = magic;
	}

	public void setMagicLevel(int magicLevel) {
		this.magicLevel = magicLevel;
	}
	
	public void kitPlayer(Player player) {
		super.kitPlayer(player);
		player.sendMessage(ChatColor.DARK_GREEN + "You've been equiped with the pro kit");
	}
}
