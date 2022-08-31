package events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import src.main;
import state.Gstate;
import utils.GameState;

public class quit implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		e.setQuitMessage(null);
		if(GameState.spec.contains(e.getPlayer().getName())) {
			GameState.spec.remove(e.getPlayer().getName());
			
		}
		if(GameState.GamePlayer.contains(e.getPlayer().getName())) {
			GameState.GamePlayer.remove(e.getPlayer().getName());

		}
		if(main.getState(Gstate.waiting) || main.getState(Gstate.starting)) {
			for(Player pls:Bukkit.getOnlinePlayers()) {
				join.Board(pls);
			}
		}
		//leave in pvp
	
		//leave in pvp
		
	}
	
	
}
