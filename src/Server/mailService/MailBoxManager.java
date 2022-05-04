package Server.mailService;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class MailBoxManager {

	private MailBoxManager() {
	}

	static MailBoxManager instance = new MailBoxManager();

	public static MailBoxManager getInstance() {
		return instance;
	}

	Plugin p;

	FileConfiguration data;
	File dfile;

	public void setup(Plugin p) {

		this.p = p;
		if (!p.getDataFolder().exists()) {
			p.getDataFolder().mkdir();
		}

		dfile = new File(p.getDataFolder(), "mailBoxData.yml");

		if (!dfile.exists()) {
			try {
				dfile.createNewFile();
			} catch (IOException e) {
				Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create mailBoxData.yml!");
			}
		}

		data = YamlConfiguration.loadConfiguration(dfile);
	}

	public FileConfiguration getData() {
		return data;
	}

	public void saveData() {
		try {
			data.save(dfile);
		} catch (IOException e) {
			Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save mailBoxData.yml!");
		}
	}

	public void reloadData() {
		data = YamlConfiguration.loadConfiguration(dfile);
	}

	public PluginDescriptionFile getDesc() {
		return p.getDescription();
	}
}
