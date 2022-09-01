package cooldowns;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Finish extends BukkitRunnable {
	
	
	int timer = 15;

	@Override
	public void run() {

		if(timer == 4) {
			for(Player pls : Bukkit.getOnlinePlayers()){
				pls.kickPlayer("§cServer Is restarting");
			}
		}
		if(timer == 0) {
			Bukkit.getServer().shutdown();
			cancel();
		}
		
		
		
		 timer--;
	}
	

}
