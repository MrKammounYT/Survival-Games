package utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;


public class MapLocationApi {
	
	


	static File file = new File("plugins/HiveMc-SG", "maps-locations.yml");
	  
	  static FileConfiguration cfg = (FileConfiguration)YamlConfiguration.loadConfiguration(file);
	  
	
	  public static int getMapId(String mapName) {
		  for(int i=1;i<6;i++) {
			  if(getMapByID(i).equals(mapName)) {
				  return i;
				  } 
			  }
		  return 0;
	  }
	  
	  public static int getnextId() {
		  for(int i=1;i<5;i++) {
			  if(!cfg.contains(""+i)) {
				  return i;
			  }
			  
		  }
		  return 0;
	  }
	
	  
	  
	  public static void setmap(int id,String name) {
		  cfg.set(String.valueOf(id), name);
		  try {
		      cfg.save(file);
		    } catch (IOException e) {
		      e.printStackTrace();
		    } 
	  }
	  
	  
	  public static boolean checkmap(String name) {
		  for(int i=1;i<6;i++) {
		  String n = (String) cfg.get(getMapByID(i)+".", name);
		  if(n.equals(name)) {
			  return true;
		  }
		  }
		  return false;
	  }
	  
	  
	  
	  public static void setLocation(int id,Location loc, String name) {
	    String world = loc.getWorld().getName();
	    double x = loc.getX();
	    double y = loc.getY();
	    double z = loc.getZ();
	    double yaw = loc.getYaw();
	    double pitch = loc.getPitch();
	    cfg.set(id+"."+world +"." +String.valueOf(name) + ".world", world);
	    cfg.set(id+"."+world +"." + String.valueOf(name) + ".x", Double.valueOf(x));
	    cfg.set(id+"."+world +"." +String.valueOf(name) + ".y", Double.valueOf(y));
	    cfg.set(id+"."+world +"." +String.valueOf(name) + ".z", Double.valueOf(z));
	    cfg.set(id+"."+world +"." +String.valueOf(name) + ".yaw", Double.valueOf(yaw));
	    cfg.set(id+"."+world +"." +String.valueOf(name) + ".pitch", Double.valueOf(pitch));
	    try {
	      cfg.save(file);
	    } catch (IOException e) {
	      e.printStackTrace();
	    } 
	  }
	  
	  public static String getMapByID(int id) {
		  for(String key: cfg.getConfigurationSection(String.valueOf(id)).getKeys(false)) {
				return key;
		  }
		  return null;
	  }
	  
	  
	  
	  public static Location getLocation(int id,String map,String name) {
	    String world = cfg.getString(id +"."+ map +"." +String.valueOf(name) + ".world");
	    double x = cfg.getDouble(id +"."+ map +"." +String.valueOf(name) + ".x");
	    double y = cfg.getDouble(id +"."+ map +"." +String.valueOf(name) + ".y");
	    double z = cfg.getDouble(id +"."+ map+"." +String.valueOf(name) + ".z");
	    double yaw = cfg.getDouble(id +"."+ map +"." +String.valueOf(name) + ".yaw");
	    double pitch = cfg.getDouble(id +"."+ map +"." +String.valueOf(name) + ".pitch");
	    Location loc = new Location(Bukkit.getWorld(world), x, y, z);
	    loc.setYaw((float)yaw);
	    loc.setPitch((float)pitch);
	    return loc;
	  }
	

}
