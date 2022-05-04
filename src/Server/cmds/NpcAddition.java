package Server.cmds;

import org.bukkit.Color;
import org.bukkit.Particle.DustOptions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class NpcAddition implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length != 0) {
			sender.sendMessage(ChatColor.RED + "The command is invalid!");
			return false;
		}
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players are allowed to create NPC characters!");
			return false;
		}
		Player player = (Player)sender;
		DustOptions dust = new DustOptions(Color.fromRGB(255, 0, 0), 1);
//		PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
//				CraftParticle.toNMS(Particle.REDSTONE, dust), 
//				true, 
//				player.getLocation().getX(),
//				player.getLocation().getY(),
//				player.getLocation().getZ(),
//				10f, 10f, 10f, 
//				5f, 
//				1000);
//		((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
		
		return true;
	}
	

}
