package utils;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;

public class Titles implements Listener {
	
	
  public static void sendTitle(Player player, String msgTitle, String msgSubTitle, int ticks) {
    IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + msgTitle + "\"}");
    IChatBaseComponent chatSubTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + msgSubTitle + "\"}");
    PacketPlayOutTitle p = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, chatTitle);
    PacketPlayOutTitle p2 = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, chatSubTitle);
    (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet<?>)p);
    (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet<?>)p2);
    sendTime(player, ticks);
  }
  
  private static void sendTime(Player player, int ticks) {
    PacketPlayOutTitle p = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, 20, ticks, 20);
    (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet<?>)p);
  }
  
  public static void sendActionBar(Player player, String message) {
    IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
    PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte)2);
    (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet<?>)ppoc);
  }
  
  public static String getPlayerName(Player p) {
	    LuckPerms lp = LuckPermsProvider.get();
	    User user = lp.getPlayerAdapter(Player.class).getUser(p);
	    
	    if (user.getCachedData().getMetaData().getPrefix() == null)
	      return p.getName(); 
	    
	    String str1 = user.getCachedData().getMetaData().getPrefix();
	    String f = str1 + "" + p.getName();
	    return f;
	  }
}
