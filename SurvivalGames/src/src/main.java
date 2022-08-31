package src;


import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import MySQL.Kills;
import MySQL.deaths;
import MySQL.points;
import commands.SG;
import cooldowns.waiting;
import events.SettingSpawns;
import events.Spectate_Items;
import events.Tag;
import events.chest_open;
import events.death;
import events.join;
import events.quit;
import events.serverPing;
import events.vote;
import state.Gstate;
import utils.GameInfo;
import utils.GameState;
import utils.MapLocationApi;
import utils.locationAPI;
import utils.options;

public class main extends JavaPlugin {
	
	public static boolean stoped = false;
	private static main instance;
	public static String serverName = "&6&lEagleMc";
	public static String serverIP = " &e&lEagleMc.net";
	public static String Prefix =color("&8│ &3SurvivalGames &8│ ");
	private static Gstate state;
	@Override
	public void onEnable() {
		instance = this;
		this.saveDefaultConfig();
		try {
			MySQL.MySQL.connect();
		} catch (ClassNotFoundException | SQLException e) {
			Bukkit.getConsoleSender().sendMessage(Prefix+"§cData Base not connected!");
			Bukkit.getConsoleSender().sendMessage("§cServer will stop!");

		}
		if(MySQL.MySQL.isConnected()) {
			Bukkit.getConsoleSender().sendMessage(Prefix+"§aData Base Is connected");
			points.createTable();
			Kills.createTable();
			deaths.createTable();
		}
		getLogger().info("This Plugin was coded by SrKammounYT");
		getLogger().info("Github: https://github.com/ahmedSrKammounYT");
		setState(Gstate.waiting);
		checkSpawns();
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		Bukkit.getPluginManager().registerEvents(new  join(), this);
		Bukkit.getPluginManager().registerEvents(new  quit(), this);
		if(getState(Gstate.noSpawns)) {
		Bukkit.getPluginManager().registerEvents(new  SettingSpawns(), this);
		}
		Bukkit.getPluginManager().registerEvents(new  options(), this);
		Bukkit.getPluginManager().registerEvents(new  serverPing(), this);
		Bukkit.getPluginManager().registerEvents(new  chest_open(), this);
		Bukkit.getPluginManager().registerEvents(new  death(), this);
		Bukkit.getPluginManager().registerEvents(new vote(), this);
		Bukkit.getPluginManager().registerEvents(new  Spectate_Items(), this);
		Bukkit.getPluginManager().registerEvents(new  Tag(), this);
		getCommand("sg").setExecutor(new SG());
		if(!getState(Gstate.noSpawns)) {
		for(World w : Bukkit.getWorlds()) {
			w.setAutoSave(false);
			w.setWeatherDuration(0);
			w.setTime(100);
			w.setThundering(false);
			w.setDifficulty(Difficulty.EASY);
		}
		waiting wt = new waiting();
		wt.runTaskTimer(this, 0, 20);
		chest_open.addChestItems();
		}
		
	}
	
	public void onDisable() {
		for(Player pls: Bukkit.getOnlinePlayers()) {
			pls.kickPlayer("§cServer is restarting");
		}
	}
	
	
	public void checkSpawns() {
		for(int i=1;i<6;i++) {
			GameState gm = new GameState();
			try {
			gm.GetGameMapInfo().put(MapLocationApi.getMapByID(i), new GameInfo(MapLocationApi.getMapByID(i), 0));
			} catch (Exception e) {
				Bukkit.getConsoleSender().sendMessage(main.color("&cMap Number &e" +i +" &cis not set!"));
				setState(Gstate.noSpawns);

			}
		}
		for(int i=1;i<5;i++) {
			try {
				locationAPI.getLocation("d"+i);
			} catch (Exception e) {
				Bukkit.getConsoleSender().sendMessage(main.color("&cSpawn DeathMatch &e" +i +" &cis not set!"));
				setState(Gstate.noSpawns);
			}
		}
		try {
			locationAPI.getLocation("lobby");
			
		} catch (Exception e) {
			Bukkit.getConsoleSender().sendMessage(main.color("&cLobby &cis not set!"));
			setState(Gstate.noSpawns);
		}
		try {
			locationAPI.getLocation("specdm");
			
		} catch (Exception e) {
			Bukkit.getConsoleSender().sendMessage(main.color("&cspectate deathmatch &cis not set!"));
			setState(Gstate.noSpawns);
		}
	}
	
	
	public static main getinstance() {
		return instance;
	}
	
	public static boolean getState(Gstate stat) {
		if(state == stat) {
		return true;
		}else {
			return false;
		}
	}
	public static void setState(Gstate states) {
		state = states;
	}
	
	public static String color(String toColor) {
		return ChatColor.translateAlternateColorCodes('&', toColor);
	}

	
	

	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
