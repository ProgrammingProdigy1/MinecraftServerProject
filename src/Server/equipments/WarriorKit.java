package Server.equipments;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

public abstract class WarriorKit {

	private ItemStack sword, foodType;
	private int foodAmount;
	private String warriorLevel;
	private PotionMeta pm;

	public WarriorKit(ItemStack sword, Material foodType, int foodAmount, PotionEffect effect) {
		this.sword = sword;
		this.foodAmount = foodAmount;
		this.foodType = new ItemStack(foodType, this.foodAmount);
		this.warriorLevel = "";
		this.pm = (PotionMeta) new ItemStack(Material.POTION, 1).getItemMeta();
		this.pm.addCustomEffect(effect, true);
		String s = effect.getType().getName();
		s = s.toLowerCase();
		String[] Strs = s.split("_");
		s = "";
		for (String str : Strs) {
			char ch = str.charAt(0);
			s += str.replaceFirst(String.valueOf(ch), String.valueOf(ch).toUpperCase()) + " ";
		}
		s = s.substring(0, s.length() - 1);
		this.pm.setDisplayName("§rPotion of " + s);
	}

	public ItemStack getSword() {
		return new ItemStack(this.sword);
	}

	public ItemStack getFoodType() {
		return new ItemStack(this.foodType);
	}

	public int getFoodAmount() {
		return foodAmount;
	}

	protected void setSword(ItemStack sword) {
		this.sword = new ItemStack(sword);
	}

	protected void setFoodType(Material foodType, int foodAmount) {
		this.foodType = new ItemStack(foodType, foodAmount);
	}

	protected void setFoodAmount(int foodAmount) {
		this.foodAmount = foodAmount;
	}

	public void kitPlayer(Player player) {
		player.getInventory().clear();
		player.getInventory().addItem(getSword());
		player.getInventory().addItem(getFoodType());
		ItemStack stack = new ItemStack(Material.POTION, 1);
		stack.setItemMeta(pm);
		player.getInventory().addItem(stack);
	}

	public String getWarriorLevel() {
		return warriorLevel;
	}

	protected void setWarriorLevel(String warriorLevel) {
		this.warriorLevel = warriorLevel;
	}

	public PotionMeta getPm() {
		return pm;
	}

	public void setPm(PotionMeta pm) {
		this.pm = pm;
	}

}
