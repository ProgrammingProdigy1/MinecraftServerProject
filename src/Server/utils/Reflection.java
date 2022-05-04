package Server.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

public class Reflection {
	
	//parameter classname shall follow the template: {nms}.className*
	//*className - the desired class
	public static Class<?> getClass(String classname) {
		try {
			String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
			String path = classname.replace("{nms}", "net.minecraft.server." + version)
					.replace("{nm}", "net.minecraft." + version).replace("{cb}", "org.bukkit.craftbukkit." + version);
			return Class.forName(path);
		} catch (Throwable t) {
			t.printStackTrace();
			return null;
		}
	}

	public static Object getNmsPlayer(Player p) throws Exception {
		Method getHandle = p.getClass().getMethod("getHandle");
		return getHandle.invoke(p);
	}

	public static Object getNmsScoreboard(Scoreboard s) throws Exception {
		Method getHandle = s.getClass().getMethod("getHandle");
		return getHandle.invoke(s);
	}

	public static Object getFieldValue(Object instance, String fieldName) throws Exception {
		Field field = instance.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		return field.get(instance);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getFieldValue(Field field, Object obj) {
		try {
			return (T) field.get(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Field getField(Class<?> clazz, String fieldName) throws Exception {
		Field field = clazz.getField(fieldName);
		field.setAccessible(true);
		return field;
	}

	public static void setValue(Object instance, String field, Object value) {
		try {
			Field f = instance.getClass().getField(field);
			f.setAccessible(true);
			f.set(instance, value);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public static void sendAllPacket(Object packet) throws Exception {
		for (Player p : Bukkit.getOnlinePlayers()) {
			Object nmsPlayer = getNmsPlayer(p);
			Object connection = nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);
			connection.getClass().getMethod("sendPacket", Reflection.getClass("{nms}.Packet")).invoke(connection,
					packet);
		}
	}

	public static void sendListPacket(List<String> players, Object packet) {
		try {
			for (String name : players) {
				Object nmsPlayer = getNmsPlayer(Bukkit.getPlayer(name));
				Object connection = nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);
				connection.getClass().getMethod("sendPacket", Reflection.getClass("{nms}.Packet")).invoke(connection,
						packet);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public static void sendPlayerPacket(Player p, Object packet) throws Exception {
		Object nmsPlayer = getNmsPlayer(p);
		Object connection = nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);
		connection.getClass().getMethod("sendPacket", Reflection.getClass("{nms}.Packet")).invoke(connection, packet);
	}
	
	public static Object getMethodValue(Object instance, String methodName, Class<?>[] params, Object... args) throws Exception {
		Method method = instance.getClass().getMethod(methodName, params);
		method.setAccessible(true);
		return method.invoke(instance, args);
	}
	
	public static <T> T getMethodValue2(Method method, Object obj, Object... args) {
		try {
			return (T) method.invoke(obj, args);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Method getMethod(Class<?> clazz, String methodName, Class<?>... args) throws Exception {
		Method method = clazz.getMethod(methodName, args);
		method.setAccessible(true);
		return method;
	}
	
	/**
	 * 
	 * @param path
	 * @param valueTypes
	 * @param values
	 * @return
	 * @throws Exception
	 */
	public static Object construct(String path, Class<?>[] valueTypes, Object... values) throws Exception {
		Class<?> clazz = Reflection.getClass(path);
		Constructor<?> constructor = clazz.getConstructor(valueTypes);
		return constructor.newInstance(values);
	}

	/**
	 * 
	 * @param constructor
	 * @param values
	 * @return
	 * @throws Exception
	 */
	public static Object construct(Constructor<?> constructor, Object... values) throws Exception {
		return constructor.newInstance(values);
	}
	
	public static Object getEnumObject(Class<?> Enum, String value) throws Exception {
		Method method = getMethod(Enum, "valueOf", String.class);
		return method.invoke(null, value);
	}
}
