package src;


import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import MySQL.Kills;
import MySQL.Deaths;
import MySQL.Points;
import commands.SG;
import cooldowns.Waiting;
import events.SettingSpawns;
import events.Spectate_Items;
import events.Tag;
import events.Chest_open;
import events.Death;
import events.Join;
import events.Quit;
import events.ServerPing;
import events.Vote;
import state.Gstate;
import utils.GameInfo;
import utils.GameState;
import utils.MapLocationApi;
import utils.LocationAPI;
import utils.Options;

public class main extends JavaPlugin {
	
	public static boolean stoped = false;
	public static boolean forcestart = false;

	private static main instance;
	public static String serverName = "&6&lEagleMc";
	public static String serverIP = " &e&lEagleMc.net";
	public static String Prefix =color("&8❘ &3SurvivalGames &8❘ ");
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
			Points.createTable();
			Kills.createTable();
			Deaths.createTable();
		}
		getLogger().info("This Plugin was coded by SrKammounYT");
		getLogger().info("Github: https://github.com/ahmedSrKammounYT");
		setState(Gstate.waiting);
		checkSpawns();
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		Bukkit.getPluginManager().registerEvents(new  Join(), this);
		Bukkit.getPluginManager().registerEvents(new  Quit(), this);
		if(getState(Gstate.noSpawns)) {
		Bukkit.getPluginManager().registerEvents(new  SettingSpawns(), this);
		}
		Bukkit.getPluginManager().registerEvents(new  Options(), this);
		Bukkit.getPluginManager().registerEvents(new  ServerPing(), this);
		Bukkit.getPluginManager().registerEvents(new  Chest_open(), this);
		Bukkit.getPluginManager().registerEvents(new  Death(), this);
		Bukkit.getPluginManager().registerEvents(new Vote(), this);
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
		Waiting wt = new Waiting();
		wt.runTaskTimer(this, 0, 20);
		Chest_open.addChestItems();
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
				LocationAPI.getLocation("d"+i);
			} catch (Exception e) {
				Bukkit.getConsoleSender().sendMessage(main.color("&cSpawn DeathMatch &e" +i +" &cis not set!"));
				setState(Gstate.noSpawns);
			}
		}
		try {
			LocationAPI.getLocation("lobby");
			
		} catch (Exception e) {
			Bukkit.getConsoleSender().sendMessage(main.color("&cLobby &cis not set!"));
			setState(Gstate.noSpawns);
		}
		try {
			LocationAPI.getLocation("specdm");
			
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
