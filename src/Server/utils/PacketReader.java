package Server.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import Server.events.RightClickNpcEvent;
import Server.main.ServerMain;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.PacketPlayInUseEntity;

public class PacketReader {

	Channel channel;
	public static Map<UUID, Channel> channels = new HashMap<UUID, Channel>();

	public void inject(Player player) throws Exception {
		Object sec = Reflection.getClass("{cb}.entity.CraftPlayer").cast(player);
		sec = Reflection.getMethodValue(sec, "getHandle", null);
		sec = Reflection.getFieldValue(sec, "playerConnection");
		sec = Reflection.getFieldValue(sec, "networkManager");
		channel = (Channel) Reflection.getFieldValue(sec, "channel");
		channels.put(player.getUniqueId(), channel);

		if (channel.pipeline().get("PacketInjector") != null)
			return;
		channel.pipeline().addAfter("decoder", "PacketInjector", new MessageToMessageDecoder<Object>() {

			@Override
			protected void decode(ChannelHandlerContext channel, Object packet, List<Object> arg) throws Exception {
				arg.add(packet);
				readPacket(player, packet);
			}

		});
	}

	public void uninject(Player player) {
		channel = channels.get(player.getUniqueId());
		if (channel.pipeline().get("PacketInjector") != null)
			channel.pipeline().remove("PacketInjector");
	}

	public void readPacket(Player player, Object packet) throws Exception {
		if (packet.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInUseEntity")) {
			if (Reflection.getFieldValue(packet, "action").toString().equalsIgnoreCase("ATTACK"))
				return;
			if (Reflection.getFieldValue(packet, "d").toString().equalsIgnoreCase("OFF_HAND"))
				return;
			if (Reflection.getFieldValue(packet, "action").toString().equalsIgnoreCase("INTERACT_AT"))
				return;
			if (Reflection.getFieldValue(packet, "action").toString().equalsIgnoreCase("INTERACT")) {
				int id = (int) Reflection.getFieldValue(packet, "a");
					Bukkit.getScheduler().scheduleSyncDelayedTask(ServerMain.getPlugin(ServerMain.class),
							new Runnable() {
//								UUID npcUUID = NpcLauncher.getNpcIds().get(id);
								@Override
								public void run() {
									try {
//										Bukkit.getPluginManager().callEvent(new RightClickNpcEvent(player, NpcLauncher.getNpcs().get(npcUUID)));
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}, 0);
			}
		}
	}
}