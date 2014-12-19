package me.chasertw123.villagedefense.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * 
 * @author hamzaxx
 */
public class ActionBarAPI {

    private static String version = "";

    private static Object packet;

    private static Method getHandle;
    private static Method sendPacket;
    private static Field playerConnection;
    private static Class<?> nmsChatSerializer;
    private static Class<?> nmsIChatBaseComponent;
    private static Class<?> packetType;

    static {
        try {
            version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

            packetType = Class.forName(getPacketPlayOutChat());

            Class<?> typeCraftPlayer = Class.forName(getCraftPlayerClasspath());

            Class<?> typeNMSPlayer = Class.forName(getNMSPlayerClasspath());

            Class<?> typePlayerConnection = Class.forName(getPlayerConnectionClasspath());

            nmsChatSerializer = Class.forName(getChatSerializerClasspath());

            nmsIChatBaseComponent = Class.forName(getIChatBaseComponentClasspath());

            getHandle = typeCraftPlayer.getMethod("getHandle");

            playerConnection = typeNMSPlayer.getField("playerConnection");

            sendPacket = typePlayerConnection.getMethod("sendPacket", Class.forName(getPacketClasspath()));

        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | NoSuchFieldException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Error {0}", ex);
        }
    }

    public static void send(Player receivingPacket, String msg) {
        try {
            Object serialized = getMethod(nmsChatSerializer, "a", String.class).invoke(null, "{\"text\": \"" + msg + "\"}");

            if (!version.contains("1_7")) {
                packet = packetType.getConstructor(nmsIChatBaseComponent, byte.class).newInstance(serialized, (byte) 2);
            } else {
                packet = packetType.getConstructor(nmsIChatBaseComponent, int.class).newInstance(serialized, 2);
            }

            Object player = getHandle.invoke(receivingPacket);

            Object connection = playerConnection.get(player);

            sendPacket.invoke(connection, packet);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Error {0}", ex);
        }
    }

    private static Method getMethod(Class<?> classs, String name, Class<?>... args) {
        for (Method m : classs.getMethods()) {
            if (m.getName().equals(name) && (args.length == 0 || ClassListEqual(args, m.getParameterTypes()))) {
                m.setAccessible(true);
                return m;
            }
        }
        return null;
    }

    private static boolean ClassListEqual(Class<?>[] l1, Class<?>[] l2) {
        boolean equal = true;
        if (l1.length != l2.length) {
            return false;
        }
        for (int i = 0; i < l1.length; i++) {
            if (l1[i] != l2[i]) {
                equal = false;
                break;
            }
        }
        return equal;
    }

    private static String getCraftPlayerClasspath() {
        return "org.bukkit.craftbukkit." + version + ".entity.CraftPlayer";
    }

    private static String getPlayerConnectionClasspath() {
        return "net.minecraft.server." + version + ".PlayerConnection";
    }

    private static String getNMSPlayerClasspath() {
        return "net.minecraft.server." + version + ".EntityPlayer";
    }

    private static String getPacketClasspath() {
        return "net.minecraft.server." + version + ".Packet";
    }

    private static String getIChatBaseComponentClasspath() {
        return "net.minecraft.server." + version + ".IChatBaseComponent";
    }

    private static String getChatSerializerClasspath() {
        return "net.minecraft.server." + version + ".ChatSerializer";
    }

    private static String getPacketPlayOutChat() {
        return "net.minecraft.server." + version + ".PacketPlayOutChat";
    }
}
