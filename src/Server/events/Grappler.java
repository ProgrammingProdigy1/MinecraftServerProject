package Server.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.util.Vector;

public class Grappler implements Listener {

	@EventHandler
	public void onGrappleThrow(ProjectileLaunchEvent event) {
		if(event.getEntityType().equals(EntityType.FISHING_HOOK) && event.getEntity().getShooter() instanceof Player) {
			
			Player player = (Player)event.getEntity().getShooter();
			Location target = null;
			
			for(Block block : player.getLineOfSight(null, 100)) {
				if(!(block.getType() == Material.AIR)) {
					target = block.getLocation();
					break;
				}
			}
			
			if(target != null) {
				player.teleport(player.getLocation().add(0, 0.5, 0));
				Vector v = getVectorForPoints(player.getLocation(), target);
				event.getEntity().setVelocity(v);
				player.setVelocity(v);
			}
		}
	}
	
	public static Vector getVectorForPoints(Location l1, Location l2) {
        double g = -0.08;
        double d = l2.distance(l1);
        double t = d;
        double vX = (1.0+0.07*t) * (l2.getX() - l1.getX())/t;
        double vY = (1.0+0.03*t) * (l2.getY() - l1.getY())/t - 0.5*g*t;
        double vZ = (1.0+0.07*t) * (l2.getZ() - l1.getZ())/t;
        return new Vector(vX, vY, vZ);
    }
}
