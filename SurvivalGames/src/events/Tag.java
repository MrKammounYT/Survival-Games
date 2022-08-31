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
import MySQL.deaths;
import MySQL.points;
import src.main;
import state.Gstate;
import utils.GameState;

public class Tag implements Listener  {
	
	GameState GameState = new GameState();

	public static HashMap<String, String> tag = new HashMap<>();
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void ontag(EntityDamageByEntityEvent e) {
		if(main.getState(Gstate.inGame) || main.getState(Gstate.deathMatch)) {
		if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			Player k = (Player) e.getDamager();
			if(utils.GameState.spec.contains(k.getName()) || utils.GameState.spec.contains(p.getName())) {
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
	    			int pt =(points.getpoints(p.getUniqueId()) *5)/100;
	    			if(pt <= 0) {
	    				pt = 5;
	    			}
	    			p.sendMessage(main.Prefix + main.color("&fYou Lost &e" + pt+ " &aGlobal Points &fFor Being killed by" + k.getDisplayName()));
	    			p.sendMessage(main.Prefix + main.color("&cYou Died, &eGood Luck Next time."));
	    			p.sendMessage(main.Prefix + main.color("&c&lMatch Summary &fkills: &a"+GameState.GetGamePlayerinfo().get(p.getName()).getkills()+ " &fTotal Points: &a"+ (GameState.GetGamePlayerinfo().get(p.getName()).getpoints() - pt)) + main.color(" &fDeaths: &a1  &fVictories: &a0"));
	    			k.sendMessage(main.Prefix + main.color("&fYou gained &e"+pt +" &aGlobal Points &fForm killing "+ p.getPlayerListName()));
	    			Kills.addkills(k.getUniqueId(), 1);
	    			points.addpoints(k.getUniqueId(), pt);
	    			utils.GameState.GamePlayer.remove(p.getName());
	    			GameState.GetGamePlayerinfo().get(k.getName()).addkills(1);
	    			GameState.GetGamePlayerinfo().get(k.getName()).addpoints(pt);
	    			points.removepoints(p.getUniqueId(), pt);
	    			deaths.adddeaths(p.getUniqueId(), 1);
	    			Bukkit.broadcastMessage(main.Prefix + p.getPlayerListName() + main.color(" &fwas Slain by " +k.getDisplayName()));
	    			Bukkit.broadcastMessage(main.Prefix + main.color("&a"+utils.GameState.GamePlayer.size()) + main.color(" &fPlayer Remaining."));

	        	}
	        	
	        }

		
	
	

}
