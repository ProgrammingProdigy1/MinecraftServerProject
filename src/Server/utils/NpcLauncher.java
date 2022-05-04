//package Server.utils;
//
//import java.lang.reflect.Array;
//import java.lang.reflect.Method;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Objects;
//import java.util.UUID;
//
//import org.bukkit.Bukkit;
//import org.bukkit.Location;
//import org.bukkit.entity.Player;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//import org.bukkit.event.inventory.InventoryClickEvent;
//import org.bukkit.event.inventory.InventoryOpenEvent;
//import org.bukkit.event.inventory.InventoryType;
//import org.bukkit.event.player.PlayerJoinEvent;
//import org.bukkit.event.player.PlayerQuitEvent;
//import org.bukkit.inventory.Inventory;
//
//import com.mojang.authlib.GameProfile;
//import com.mojang.authlib.properties.Property;
//
//import Server.events.RightClickNpcEvent;
//import net.minecraft.server.v1_16_R3.Entity;
//
//
//public class NpcLauncher implements Listener {
//	final Location[] STAND_SPAWN;
//	static PacketReader reader;
//	static Map<UUID, Object> npcs;
//	static Map<Integer, UUID> npcIds;
//	static NpcDataContainer[] npcData;
//	
//	public NpcLauncher() throws Exception {
//		STAND_SPAWN = new Location[4];
//		STAND_SPAWN[0] = new Location(Bukkit.getWorld("world"), 27.607, 176, 73.905, 120, 0);
//		
//		reader = new PacketReader();
//		npcs = new HashMap<UUID, Object>();
//		npcIds = new HashMap<Integer, UUID>();
//		npcData = new NpcDataContainer[4];
//		
//		npcData[0] = new NpcDataContainer(
//				"Ice Minigame",
//				STAND_SPAWN[0].getX(), 
//				STAND_SPAWN[0].getY(), 
//				STAND_SPAWN[0].getZ(), 
//				STAND_SPAWN[0].getPitch(), 
//				STAND_SPAWN[0].getYaw(), 
//				"ewogICJ0aW1lc3RhbXAiIDogMTYwMzkyMTAwODM2NCwKICAicHJvZmlsZUlkIiA6ICJiMGQ0YjI4YmMxZDc0ODg5YWYwZTg2NjFjZWU5NmFhYiIsCiAgInByb2ZpbGVOYW1lIiA6ICJNaW5lU2tpbl9vcmciLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTg5YjIwYzkxMGJjNGQxMDU0ZDhmNWJkOWNlZmNlMTgyNjJjOTAyOTc1ZDllODllODQ3ZDg1MzY1YmI2ZmYzZCIKICAgIH0KICB9Cn0=", 
//				"W74G+dEnRbVmaD3uDshwZNFwRz+fPmogj7wThnS55JWiUaSRZOKsSODcs2/hZec+NukylAIKZyJL1I9J//ct5ZTpSsiKoc/0DFZ2CN0pZZy3P3qo9iWQKhc/ZCrmd+AWkeddxlh17Q9s4ydUIZYcbGlVyr1Lh6Bhk9tUfSWJZSZb4q9JVSV3fzQC97Xbcu6xcv1ReHTKOUEjShrfEXHwZNosxWlhD+E3Fe+e8K3PMq9qMKCq3gZ6U82I97v7sjx1f+rS47f1Q3W8aj21a2u/pUpqDvjlQXzJ8zxU7XCynuOhX8nkeEJA9LRbCC5S2wNQFcfIkbrQ5QlSLPVH2mKifSgsSS3FO1UvlvOUDtkFe6gXYqyIW4dzb47ZrQasaRPKnESfxpTaRgsIlvCl2VUU3bA+qD1tqvn0tSXfo8GwlRPPOECDfmlbrwouLd3cIi7da1ERq/cC/YOMxIkwmIo57IcfCtn1PM5fnSbOhMqCc68g7hHjL+4/Y5L/ps0oYobX3MH0/rxrzYZ+Jwibd97/9zNMN7mpnU0gCP8hV+iFIE1tAzuoOdYhuaO4Y/m4u+8HJNdBO6aBVVyvE95uQIvI7xTS8wRNG/y4T3Xae2fL4+vVaMEWuhKIwsI2Bz0wYKW6yL5AHsaHFx0AsnTmqWEOg5CDIXxiezldFPNzhaRRM9M="
//				);
//		Object craftServer = Reflection.getClass("{cb}.CraftServer").cast(Bukkit.getServer()),
//				server = Reflection.getMethodValue(craftServer, "getServer", null),
//				craftWorld = Reflection.getClass("{cb}.CraftWorld").cast(Bukkit.getWorld("world")),
//				world = Reflection.getMethodValue(craftWorld, "getHandle", null);
//		Object pim = Reflection.construct("{nms}.PlayerInteractManager",
//				new Class<?>[] { Reflection.getClass("{nms}.WorldServer") }, world);
//		
//		npcObjects = new Object[4];
//		
//		for(int i = 0; i < 1; i++) {
//		}
//	}
//	
////	MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
////	WorldServer world = ((CraftWorld) player.getWorld()).getHandle();
////	GameProfile profile = new GameProfile(UUID.randomUUID(), "Ice Minigame");
////	String texture = "ewogICJ0aW1lc3RhbXAiIDogMTYwMzk3MTM1MDUzMCwKICAicHJvZmlsZUlkIiA6ICJiMGQ0YjI4YmMxZDc0ODg5YWYwZTg2NjFjZWU5NmFhYiIsCiAgInByb2ZpbGVOYW1lIiA6ICJNaW5lU2tpbl9vcmciLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGRiZDIxNTZkZDQ1OWUzMmMzY2RiNTI4OWFkYTliMTE2MGQ3MTI2ZmQzYzcwM2ZkOThlZjY5OWIzYjVmZjQyMiIKICAgIH0KICB9Cn0=";
////	String signature = "OZwlW6176+E8ghiFSya5KJlqrPi8u2T2FBrwxDdouuvBuxtOIFNLKIiI6t9+ydyKpjoapSqvmx9aAVLG/6emabrYwqHDVM/XiZqlWodLm0F0mAx8eGssXMYKd0XhmhJrI9X4MzpB7Y89U02ZmY9OmE4U7TX0ohs4vqI/J1d7FHcpNr/MncVOwpWH0smL96+E3AnyZw5f/QAqiLRkLfFaK3H44J+tIep6WWYjjOXjE9v2JSQ6sJWM9+OILap30GjlHh1NnF/Nsl+Zq1N4pqXwai82fiVq9uFE9M4JSZMDuWWkWjKzm580j9Jp/5yJjMhpQKxSTztKYhDrsqP3ruIZQw9fg1WM1y0sixmlLmMCzlGUO+n0q7V2AJnywzKmtz1GyfpVR1GKkO2MX/XnarsRkrcTMKgguo/wNdijYgnLHiDzImLU0AziUYsLsV4/C3W3PC+QStJOLbgbRZUNkm6wBzelTRKb4T4UXiPfUhLm6FKM2NGErkF7UsRpvSyt+8hqfRSLocoT4IBsZARQSbP6Knq+HjjuoWnMTsTTVvmyOAGGG2Sq7MUmHqb0DI53SW1tlJkq7qcNPhRl1XKaqp+EoDe8Y10e6uPj/X81Rpew/mKswYzEKp3druvZM7JO30Z1DqUtfu3+VoaTiTv1RVUzwFfOs5qhuX5CdLgMkkT45QM=";
////	profile.getProperties().put("textures", new Property("textures", texture, signature));
////	npc = new EntityPlayer(server, world, profile, new PlayerInteractManager(world));
////	npc.setLocation(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 0, 0);
////	npc.listName = ChatSerializer.a("{\"text\":\"\"}");
////	PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
////	connection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, npc));
////	connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
////	DataWatcher watcher = npc.getDataWatcher();
////	byte b = 0x01 | 0x02 | 0x04 | 0x08 | 0x10 | 0x20 | 0x40;
////	watcher.set(DataWatcherRegistry.a.a(16), b);
////	connection.sendPacket(new PacketPlayOutEntityMetadata(npc.getId(), npc.getDataWatcher(), false));
//	
//	@EventHandler
//	public void onPlayerJoin(PlayerJoinEvent event) throws Exception {
//		Player player = event.getPlayer();
//		reader.inject(player);
//		for(int i = 0; i < 1; i++) {
//		addNpc(
//				player, 
//				i,
//				npcData[i].getName(), 
//				STAND_SPAWN[i].getX(), 
//				STAND_SPAWN[i].getY(), 
//				STAND_SPAWN[i].getZ(), 
//				STAND_SPAWN[i].getPitch(), 
//				STAND_SPAWN[i].getYaw(), 
//				npcData[i].getTexture(),
//				npcData[i].getSignature()
//				);
//		}
//	}
//	
//	@EventHandler
//	public void onPlayerQuit(PlayerQuitEvent event) throws Exception {
//		Player player = event.getPlayer();
//		reader.uninject(player);
//	}
//	
//	public static void addNpc(Player player, String npcName, double x, double y, double z, float pitch, float yaw, String texture, String signature) throws Exception {
//		Object npc;
//		GameProfile profile = new GameProfile(UUID.randomUUID(), npcData[i].getName());
//		profile.getProperties().put("textures", new Property("textures", npcData[i].getTexture(), npcData[i].getSignature()));
//		npcObjects[i] = Reflection.construct("{nms}.EntityPlayer",
//				new Class<?>[] { Reflection.getClass("{nms}.MinecraftServer"), Reflection.getClass("{nms}.WorldServer"),
//						GameProfile.class, Reflection.getClass("{nms}.PlayerInteractManager") },
//				server, world, profile, pim);
//		Reflection.getMethodValue(npcObjects[i], "setLocation",
//				new Class<?>[] { double.class, double.class, double.class, float.class, float.class },
//				npcData[i].getX(), npcData[i].getY(), npcData[i].getZ(), ((npcData[i].getYaw() * 256.0F) / 360.0F), npcData[i].getPitch());
//		Method method = Reflection.getMethod(Reflection.getClass("{nms}.IChatBaseComponent$ChatSerializer"), "a",
//				new Class<?>[] { String.class });
//		Object baseComponent = Reflection.getMethodValue2(method, null, String.format("{\"text\":\"NPC - %s\"}", npcData[i].getName()));
//		Reflection.setValue(npcObjects[i], "listName", baseComponent);
//		npcs.put(profile.getId(), npcObjects[i]);
//		npcIds.put((Integer) Reflection.getMethodValue(npcObjects[i], "getId", null), profile.getId());
//		Object Enum = Reflection.getEnumObject(
//				Reflection.getClass("{nms}.PacketPlayOutPlayerInfo$EnumPlayerInfoAction"),
//				"ADD_PLAYER");
//		Object arr = Array.newInstance(Reflection.getClass("{nms}.EntityPlayer"), 1);
//		Array.set(arr, 0, npcObjects[npcSerial]);
//		Object packetPlayerInfo = Reflection.construct(
//			"{nms}.PacketPlayOutPlayerInfo", 
//			new Class<?>[] {
//				Reflection.getClass("{nms}.PacketPlayOutPlayerInfo$EnumPlayerInfoAction"),
//				getArrayClassFromType(Reflection.getClass("{nms}.EntityPlayer"))
//			}, 
//			Reflection.getClass("{nms}.PacketPlayOutPlayerInfo$EnumPlayerInfoAction").cast(Enum),
//			arr
//			);
//		Reflection.sendPlayerPacket(player, packetPlayerInfo);
//		Object packetNamedEntitySpawn = Reflection.construct("{nms}.PacketPlayOutNamedEntitySpawn",
//				new Class<?>[] { Reflection.getClass("{nms}.EntityHuman") }, npcObjects[npcSerial]);
//		Reflection.sendPlayerPacket(player, packetNamedEntitySpawn);
//		Object dataWatcher = Reflection.getMethodValue(npcObjects[npcSerial], "getDataWatcher", null);
//		byte b = 0x01 | 0x02 | 0x04 | 0x08 | 0x10 | 0x20 | 0x40;
//		Object dataWatcherSerializer = Reflection.getField(Reflection.getClass("{nms}.DataWatcherRegistry"), "a").get(null);
//		Object dataWatcherObject = Reflection.getMethodValue(dataWatcherSerializer, "a", new Class<?>[] { int.class },
//				16);
//		Reflection.getMethodValue(dataWatcher, "set",
//				new Class<?>[] { Reflection.getClass("{nms}.DataWatcherObject"), Object.class}, dataWatcherObject, b);
//		Object packetEntityMetadata = Reflection.construct("{nms}.PacketPlayOutEntityMetadata",
//				new Class<?>[] { int.class, Reflection.getClass("{nms}.DataWatcher"), boolean.class },
//				Reflection.getMethodValue(npcObjects[npcSerial], "getId", null), dataWatcher, false);
//		Reflection.sendPlayerPacket(player, packetEntityMetadata);
//		Object packetHeadRotation = Reflection.construct(
//				"{nms}.PacketPlayOutEntityHeadRotation", 
//				new Class<?>[] {
//					Reflection.getClass("{nms}.Entity"),
//					byte.class
//				}, npcObjects[npcSerial], (byte)((yaw * 256.0F) / 360.0F));
//		Reflection.sendPlayerPacket(player, packetHeadRotation);
//		Object packetEntityLook = Reflection.construct(
//				"{nms}.PacketPlayOutEntity$PacketPlayOutEntityLook", 
//				new Class<?>[] {
//					int.class,
//					byte.class,
//					byte.class,
//					boolean.class
//				}, (int)Reflection.getMethodValue(npcObjects[npcSerial], "getId", null),
//				(byte)((yaw * 256.0F) / 360.0F),
//				(byte)pitch, true);
//		Reflection.sendPlayerPacket(player, packetEntityLook);
//	}
//	
//	public static void addNpcByList(Collection<? extends Player> collection, String npcName, double x, double y, double z, float pitch, float yaw, String texture, String signature) throws Exception {
//		for(Player player : collection) {
//			for(int i = 0; i < 1; i++)
//				addNpc(player, i, npcName, x, y, z, pitch, yaw, texture, signature);
//		}
//	}
//	
//	public static void addServerNpc(String npcName, double x, double y, double z, float pitch, float yaw, String texture, String signature) throws Exception {
//		addNpcByList(Bukkit.getServer().getOnlinePlayers(), npcName, x, y, z, pitch, yaw, texture, signature);
//	}
//	
//	public static String getJVMName(Class<?> clazz) {
//        if(clazz == null) {
//            return null;
//        }
//        //For arrays, .getName() is fine.
//        if(clazz.isArray()) {
//            return clazz.getName().replace('.', '/');
//        }
//        if(clazz == boolean.class) {
//            return "Z";
//        } else if(clazz == byte.class) {
//            return "B";
//        } else if(clazz == short.class) {
//            return "S";
//        } else if(clazz == int.class) {
//            return "I";
//        } else if(clazz == long.class) {
//            return "J";
//        } else if(clazz == float.class) {
//            return "F";
//        } else if(clazz == double.class) {
//            return "D";
//        } else if(clazz == char.class) {
//            return "C";
//        } else {
//            return "L" + clazz.getName().replace('.', '/') + ";";
//        }
//    }
//
//    /**
//     * Generically and dynamically returns the array class type for the given class type. The dynamic equivalent of
//     * sending {@code String.class} and getting {@code String[].class}. Works with array types as well.
//     * @param clazz The class to convert to an array type.
//     * @return The array type of the input class.
//     */
//    public static Class<?> getArrayClassFromType(Class<?> clazz) {
//        Objects.requireNonNull(clazz);
//        try {
//            return Class.forName("[" + getJVMName(clazz).replace('/', '.'));
//        } catch(ClassNotFoundException ex) {
//            // This cannot naturally happen, as we are simply creating an array type for a real type that has
//            // clearly already been loaded.
//            throw new NoClassDefFoundError(ex.getMessage());
//        }
//    }
//    
//    public static PacketReader packetReader() {
//    	return reader;
//    }
//
//	/**
//	 * @return the npcIds
//	 */
//	@EventHandler
//	public void onRightClickNpc(RightClickNpcEvent event) throws Exception {
//		System.out.println(event.getNPC());
//		Player player = event.getPlayer();
//		Inventory inv = Bukkit.createInventory(player, InventoryType.CHEST, "Minigame Modes");
//		player.openInventory(inv);
//	}
//	public static Map<UUID, Object> getNpcs() {
//		return npcs;
//	}
//
//	public static Map<Integer, UUID> getNpcIds() {
//		return npcIds;
//	}
//	
//	@EventHandler
//	public void onInventoryClick(InventoryClickEvent event) {
//		
//	}
//
//	@EventHandler
//	public void onInventoryOpen(InventoryOpenEvent event) {
//
//	}
//	
//	public static NpcDataContainer[] getNpcData() {
//		return npcData;
//	}
//}
