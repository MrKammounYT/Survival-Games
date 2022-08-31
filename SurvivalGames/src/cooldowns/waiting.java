package cooldowns;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import src.main;
import state.Gstate;
import utils.GameState;
import utils.Titles;

public class waiting extends BukkitRunnable {

	
	int timer = 300;
	@Override
	public void run() {

		
		for(Player pls :Bukkit.getOnlinePlayers()) {
			pls.setLevel(timer);
			Titles.sendActionBar(pls, main.color("&ePlayers Waiting: &b"+ GameState.GamePlayer.size()));
			
		}
		if(main.stoped == true) {
			cancel();
		}
		


		GameState gm = new GameState();

		if(GameState.GamePlayer.size() >= gm.getMinPlayers()) {
			Bukkit.broadcastMessage(main.Prefix + main.color("&eGame Starting in &b30 &eSeconds"));
			main.setState(Gstate.starting);
			starting st = new starting();
			st.runTaskTimer(main.getinstance(), 0, 20);
			cancel();
		}
		if(timer ==0 && GameState.GamePlayer.size() < gm.getMinPlayers()) {
			Bukkit.broadcastMessage(main.Prefix + main.color("&fNot enough Players to Start The Game"));
			timer = 300;
		}
		
		if(GameState.GamePlayer.size() > 0) {
		timer--;
		}
	}

}
