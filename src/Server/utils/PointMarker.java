package Server.utils;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PointMarker {

	private int n, len;
	private String category;

	public PointMarker(int len, String category) {
		this.len = len;
		List<Object> names = SQL.queryInfo("pointmarker", "serial", "category", category);
		n = names.isEmpty() ? 0 : (int) names.get(names.size() - 1);
		this.category = category;
	}

	public boolean insertPoint(Location loc) {
		SQL.openConnection();
		boolean contains = (SQL.tableContains(loc.getBlockX(), "pointmarker", "x")
				&& SQL.tableContains(loc.getBlockY(), "pointmarker", "y")
				&& SQL.tableContains(loc.getBlockZ(), "pointmarker", "z"));
		SQL.closeConnection();
		if (!contains) {
			this.n++;
			SQL.insertInfoRowBeta("pointmarker", "category", "serial", "x", "y", "z", "pitch", "yaw", category, n,
					loc.getX(), loc.getY(), loc.getZ(), loc.getPitch(), loc.getYaw());
			return true;
		}
		return false;
	}

	public void spread(Player[] players) {
		if (players.length <= 0)
			return;
		List<Object> lst = SQL.queryInfo("pointmarker", "serial", "category", category);
		int amount = (int) lst.get(lst.size() - 1);
		if (players.length > amount || len < amount)
			return;
		double x, y, z;
		float pitch, yaw;
		Location[] locs = new Location[amount];
		int times = locs.length;
		for (int i = 1; i <= amount; i++) {
			x = (double) SQL.queryInfo("pointmarker", "x", "category", category, "serial", i).get(0);
			y = (double) SQL.queryInfo("pointmarker", "y", "category", category, "serial", i).get(0);
			z = (double) SQL.queryInfo("pointmarker", "z", "category", category, "serial", i).get(0);
			pitch = (float) SQL.queryInfo("pointmarker", "pitch", "category", category, "serial", i).get(0);
			yaw = (float) SQL.queryInfo("pointmarker", "yaw", "category", category, "serial", i).get(0);
			locs[i - 1] = new Location(Bukkit.getWorld("world"), x, y, z, yaw, pitch);
		}
		Random rnd = new Random();
		int n = times, index = -1;
		Location loc = null;
		for (int i = 0; i < times; i++) {
			index = rnd.nextInt(n);
			loc = locs[index];
			locs[index] = locs[n - 1].clone();
			locs[n - 1] = loc.clone();
			n--;
		}
		int length = players.length;
		for (int i = 0; i < length; i++)
			players[i].teleport(locs[i]);
	}

	public int getN() {
		return n;
	}

	public String getCategory() {
		return category;
	}
}
