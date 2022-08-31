package events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import MySQL.Kills;
import MySQL.deaths;
import MySQL.points;
import src.main;
import state.Gstate;
import utils.GameState;
import utils.Titles;
import utils.locationAPI;

public class death implements Listener {
	
	GameState GameState = new GameState();

	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void ondeath(PlayerDeathEvent e) {
		if(main.getState(Gstate.noSpawns))return;
		e.setDeathMessage(null);
		Player p = e.getEntity();
		utils.GameState.GamePlayer.remove(p.getName());
		for(ItemStack items : e.getDrops()) {
		p.getWorld().dropItem(p.getLocation(), items);
		}
		for(Player pls: Bukkit.getOnlinePlayers()) {
			pls.playSound(pls.getLocation(), Sound.AMBIENCE_THUNDER, 4.0f, 5.0f);
		}
		
		if(p.getKiller() != null) {
			Player k = p.getKiller();
			Tag.tag.remove(p.getName());
			int pt =(points.getpoints(p.getUniqueId()) *5)/100;
			if(pt <= 0) {
				pt = 5;
			}
			p.sendMessage(main.Prefix + main.color("&fYou Lost &e" + pt+ " &aGlobal Points &fFor Being killed by" + Titles.getPlayerName(k)));
			p.sendMessage(main.Prefix + main.color("&cYou Died, &eGood Luck Next time."));
			p.sendMessage(main.Prefix + main.color("&c&lMatch Summary &fkills: &a"+GameState.GetGamePlayerinfo().get(p.getName()).getkills()+ " &fTotal Points: &a"+ (GameState.GetGamePlayerinfo().get(p.getName()).getpoints() - pt)) + main.color(" &fDeaths: &a1  &fVictories: &a0"));
			k.sendMessage(main.Prefix + main.color("&fYou gained &e"+pt +" &aGlobal Points &fForm killing "+ Titles.getPlayerName(p)));
			Bukkit.getScheduler().runTaskLater(main.getinstance(), new BukkitRunnable() {
			@Override
			public void run() {
				p.spigot().respawn();
				
			}
		}, 5L);
			Kills.addkills(k.getUniqueId(), 1);
			points.addpoints(k.getUniqueId(), pt);
			utils.GameState.GamePlayer.remove(p.getName());
			GameState.GetGamePlayerinfo().get(k.getName()).addkills(1);
			GameState.GetGamePlayerinfo().get(k.getName()).addpoints(pt);
			points.removepoints(p.getUniqueId(), pt);
			deaths.adddeaths(p.getUniqueId(), 1);
			Bukkit.broadcastMessage(main.Prefix + Titles.getPlayerName(p) + main.color(" &fwas Slain by " +Titles.getPlayerName(k)));
			Bukkit.broadcastMessage(main.Prefix + main.color("&a"+utils.GameState.GamePlayer.size()) + main.color(" &fPlayer Remaining."));
			
		}else {
			if(Tag.tag.containsKey(p.getName())) {
        		Player k = Bukkit.getPlayer(Tag.tag.get(p.getName()));
    			int pt =(points.getpoints(p.getUniqueId()) *5)/100;
    			if(pt <= 0) {
    				pt = 5;
    			}
    			p.sendMessage(main.Prefix + main.color("&fYou Lost &e" + pt+ " &aGlobal Points &fFor Being killed by" + Titles.getPlayerName(k)));
    			p.sendMessage(main.Prefix + main.color("&cYou Died, &eGood Luck Next time."));
    			p.sendMessage(main.Prefix + main.color("&c&lMatch Summary &fkills: &a"+GameState.GetGamePlayerinfo().get(p.getName()).getkills()+ " &fTotal Points: &a"+ (GameState.GetGamePlayerinfo().get(p.getName()).getpoints() - pt)) + main.color(" &fDeaths: &a1  &fVictories: &a0"));
    			k.sendMessage(main.Prefix + main.color("&fYou gained &e"+pt +" &aGlobal Points &fForm killing "+ Titles.getPlayerName(p)));
    			Bukkit.getScheduler().runTaskLater(main.getinstance(), new BukkitRunnable() {
    				@Override
    				public void run() {
    					p.spigot().respawn();
    					
    				}
    			}, 5L);
    			Kills.addkills(k.getUniqueId(), 1);
    			points.addpoints(k.getUniqueId(), pt);
    			utils.GameState.GamePlayer.remove(p.getName());
    			GameState.GetGamePlayerinfo().get(k.getName()).addkills(1);
    			GameState.GetGamePlayerinfo().get(k.getName()).addpoints(pt);
    			points.removepoints(p.getUniqueId(), pt);
    			deaths.adddeaths(p.getUniqueId(), 1);
    			Bukkit.broadcastMessage(main.Prefix + Titles.getPlayerName(p) + main.color(" &fwas Slain by " +Titles.getPlayerName(k)));
    			Bukkit.broadcastMessage(main.Prefix + main.color("&a"+utils.GameState.GamePlayer.size()) + main.color(" &fPlayer Remaining."));
    			return;
        	}
			p.sendMessage(main.Prefix + main.color("&cYou Died, &eGood Luck Next time."));
			Bukkit.broadcastMessage(main.Prefix + Titles.getPlayerName(p) + main.color(" &fDied"));
			Bukkit.broadcastMessage(main.Prefix + main.color("&a"+utils.GameState.GamePlayer.size()) + main.color(" &fPlayer Remaining."));

			
		}
	
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void playerrespawn(PlayerRespawnEvent e) {
		if(main.getState(Gstate.noSpawns))return;
		e.setRespawnLocation(locationAPI.getLocation("lobby"));
		Player p = e.getPlayer();
		Bukkit.getScheduler().runTaskLater(main.getinstance(), new BukkitRunnable() {
			@Override
			public void run() {
				Titles.sendTitle(p, "§eGG", "", 10);
				utils.GameState.spec.add(p.getName());
				p.setGameMode(GameMode.SURVIVAL);
				p.getInventory().clear();
				p.getInventory().setItem(0, navigateCompass());
				p.getInventory().setItem(8, leave());
				p.setHealth(20);
				p.setFoodLevel(20);
				p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000000, 1));
				p.spigot().setCollidesWithEntities(false);
				p.setAllowFlight(true);
				for(Player pls :Bukkit.getOnlinePlayers()) {
						pls.hidePlayer(p);
				}				
			}
		}, 5L);
		


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
	
	
	
	
	
	
	

}
