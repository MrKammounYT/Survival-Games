package events;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import MySQL.Kills;
import MySQL.MySQL;
import MySQL.Deaths;
import MySQL.Points;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import src.main;
import state.Gstate;
import utils.GameState;
import utils.PlayerInfo;
import utils.Titles;
import utils.LocationAPI;

public class Join implements Listener {

	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(MySQL.checkConnection() == false) {
			p.kickPlayer("§cServer Is Restarting");
			Bukkit.getServer().shutdown();
		}
		
		GameState gm = new GameState();
		p.getInventory().clear();
		e.setJoinMessage(null);
		p.getInventory().setHelmet(null);
		p.getInventory().setChestplate(null);
		p.getInventory().setLeggings(null);
		p.getInventory().setBoots(null);
		Netherboard.instance().createBoard(p, "board");
		p.setLevel(0);
		if(main.getState(Gstate.waittogame)) {
			p.sendMessage(main.Prefix+ main.color("&cGame Has Alerday Started!"));
			p.teleport(LocationAPI.getLocation("lobby"));
			GameState.spec.add(p.getName());
			gm.GetGamePlayerinfo().put(p.getName(), new PlayerInfo(0, 0, 0,false));
			p.setAllowFlight(true);
			p.getInventory().clear();
			p.setHealth(20);
			p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000000, 1));
			p.setFoodLevel(20);
			p.getInventory().setItem(0, navigateCompass());
			p.getInventory().setItem(8, leave());
			p.setGameMode(GameMode.SURVIVAL);
			p.spigot().setCollidesWithEntities(false);
			for(Player pls :Bukkit.getOnlinePlayers()) {
					pls.hidePlayer(p);
			}
			return;
		}
		if ((main.getState(Gstate.waiting)|| main.getState(Gstate.starting))) {
			if(GameState.GamePlayer.size() == gm.getMaxPlayers()) {
				if(p.hasPermission("eaglemc.gold")) {
				for(String f : GameState.GamePlayer) {
					Player ps = Bukkit.getPlayer(f);
					if(!ps.hasPermission("eaglemc.gold")){
						ps.kickPlayer(main.Prefix + main.color("&cYou Have Been Kicked By a Vip Join"));
					}
				}
			}else {
				p.kickPlayer("§cGame Is Full!");
				return;

				}
			}
			p.setAllowFlight(false);

			Bukkit.broadcastMessage(main.Prefix + main.color("&r" +Titles.getPlayerName(p) +" &eJoined the game"));
			p.getInventory().clear();
			p.setLevel(0);
			p.getInventory().setItem(0, vote());
			GameState.GamePlayer.add(p.getName());
			gm.GetGamePlayerinfo().put(p.getName(), new PlayerInfo(0, 0, 0,false));
			p.setGameMode(GameMode.SURVIVAL);
			p.setHealth(20);
			for(PotionEffect effect:p.getActivePotionEffects()){
			p.removePotionEffect(effect.getType());
			}
			p.setFoodLevel(20);
			Bukkit.getScheduler().runTaskLater(main.getinstance(), new BukkitRunnable() {
				@Override
				public void run() {
					p.teleport(LocationAPI.getLocation("lobby"));
					Points.createPlayer(p);
					Kills.createPlayer(p);
					Deaths.createPlayer(p);
					Board(p);

				}
			}, 5L);
			for(Player pls:Bukkit.getOnlinePlayers()) {
				pls.showPlayer(p);
				p.showPlayer(pls);
				Board(pls);
			}


			
		} else if(main.getState(Gstate.noSpawns)) {
			if(!p.hasPermission("sg.admin")) {
				p.kickPlayer("Game is not Ready");
			}

		}
		else if(main.getState(Gstate.finish)) {
			p.kickPlayer("§cServer is restarting");

		}
		else {
			p.sendMessage(main.Prefix+ main.color("&cGame Has Alerday Started!"));
			p.teleport(LocationAPI.getLocation("lobby"));
			GameState.spec.add(p.getName());
			gm.GetGamePlayerinfo().put(p.getName(), new PlayerInfo(0, 0, 0,false));
			p.setAllowFlight(true);
			p.getInventory().clear();
			p.setHealth(20);
			p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000000, 1));
			p.setFoodLevel(20);
			p.getInventory().setItem(0, navigateCompass());
			p.getInventory().setItem(8, leave());
			p.setGameMode(GameMode.SURVIVAL);
			p.spigot().setCollidesWithEntities(false);
			for(Player pls :Bukkit.getOnlinePlayers()) {
					pls.hidePlayer(p);
			}
		}
		
	}
	public ItemStack vote() {
		ItemStack item = new ItemStack(Material.NETHER_STAR);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(main.color("&e&lVote Menu"));
		ArrayList<String> lore = new ArrayList<>();
		lore.add("§7Right a click To ");
		lore.add("§7Open map voting menu");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
		}
	public ItemStack CustomotionMenu() {
		ItemStack item = new ItemStack(Material.NETHER_STAR);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(main.color("&6&lCustomisation Menu"));
		ArrayList<String> lore = new ArrayList<>();
		lore.add("§7Right a click To ");
		lore.add("§7Open map Customisation menu menu");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
		}
	public ItemStack navigateCompass() {
		ItemStack item = new ItemStack(Material.COMPASS);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§ePlayer Selecter");
		ArrayList<String> lore = new ArrayList<>();
		lore.add("§7right a click To ");
		lore.add("§7Spectate Players!");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
		}
	public ItemStack leave() {
		ItemStack item = new ItemStack(Material.BED);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§cBack To Lobby");
		ArrayList<String> lore = new ArrayList<>();
		lore.add("§7right a click To");
		lore.add("§7Leave");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
		}
	
	public static void Board(Player p) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
	    Date date = new Date();  
		BPlayerBoard board = Netherboard.instance().getBoard(p);
		board.setName(main.color("&e&lSurvival Games"));
		board.set(main.color("&7&l "),12);
		board.set(main.color("&e&lPlayers"), 11);
		board.set(main.color("&e│ &f"+GameState.GamePlayer.size()+"&f/24"), 10);
		board.set(main.color("&c  "), 9);
		board.set(main.color("&e&lPoints"), 8);
		board.set(main.color("&e│ &f" + Points.getpoints(p.getUniqueId())), 7);
		board.set(main.color("&a   "), 6);
		board.set(main.color("&e&lKills"), 5);
		board.set(main.color("&e│ &f" + Kills.getKills(p.getUniqueId())), 4);
		board.set(main.color("&d   "), 3);
		board.set(main.color(main.serverIP +" &7│ &7" + formatter.format(date)), 2);
		board.set(main.color("&7&m------------------------"), 1);
	}
	
}
