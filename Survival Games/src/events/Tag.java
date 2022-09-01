package events;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import MySQL.Kills;
import MySQL.Deaths;
import MySQL.Points;
import src.main;
import state.Gstate;
import utils.GameState;
import utils.Titles;

public class Tag extends GameState implements Listener   {
	

	public static HashMap<String, String> tag = new HashMap<>();
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void ontag(EntityDamageByEntityEvent e) {
		if(main.getState(Gstate.inGame) || main.getState(Gstate.deathMatch)) {
		if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			Player k = (Player) e.getDamager();
			if(spec.contains(k.getName()) || spec.contains(p.getName())) {
				e.setCancelled(true);
				return;
			}
			tag.put(p.getName(), k.getName());
		}
		}
	}
	
	@EventHandler
	public void onQuitTaged(PlayerQuitEvent e) {
		Player p = e.getPlayer();
			if(tag.containsKey(p.getName())) {
	        		Player k = Bukkit.getPlayer(tag.get(p.getName()));
	    			int pt =(Points.getpoints(p.getUniqueId()) *5)/100;
	    			if(pt <= 0) {
	    				pt = 5;
	    			}
	    			p.sendMessage(main.Prefix + main.color("&fYou Lost &e" + pt+ " &aGlobal Points &fFor Being killed by" + Titles.getPlayerName(k)));
	    			p.sendMessage(main.Prefix + main.color("&c&lMatch Summary &fkills: &a"+GetGamePlayerinfo().get(p.getName()).getkills()+ " &fTotal Points: &a"+ (GetGamePlayerinfo().get(p.getName()).getpoints() - pt)) + main.color(" &fDeaths: &a1  &fVictories: &a0"));
	    			k.sendMessage(main.Prefix + main.color("&fYou gained &e"+pt +" &aGlobal Points &fForm killing "+ Titles.getPlayerName(p)));
	    			Bukkit.broadcastMessage(main.Prefix + main.color(Titles.getPlayerName(p) + " &fwas Slain by " +Titles.getPlayerName(k)));
	    			Bukkit.broadcastMessage(main.Prefix + main.color("&a"+GamePlayer.size()) + main.color(" &fPlayer Remaining."));
	    			Kills.addkills(k.getUniqueId(), 1);
	    			Points.addpoints(k.getUniqueId(), pt);
	    			GamePlayer.remove(p.getName());
	    			GetGamePlayerinfo().get(k.getName()).addkills(1);
	    			GetGamePlayerinfo().get(k.getName()).addpoints(pt);
	    			Points.removepoints(p.getUniqueId(), pt);
	    			Deaths.adddeaths(p.getUniqueId(), 1);
	    			
	        	}
	        	
	        }

		
	
	

}
