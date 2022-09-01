package cooldowns;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import src.main;
import state.Gstate;
import utils.Titles;

public class PreGame extends BukkitRunnable {

	
	int timer =12;
	@Override
	public void run() {
		
		for(Player pls :Bukkit.getOnlinePlayers()) {
			pls.setLevel(timer);
			
		}
		if(timer == 10 || timer == 5  || timer == 4 || timer == 3 || timer == 2 || timer == 1    ) {
			for(Player pls :Bukkit.getOnlinePlayers()) {
				Titles.sendTitle(pls, main.color("&f&lGame Start in"), main.color("&a&l"+timer) ,10);
				pls.playSound(pls.getLocation(), Sound.CLICK, 3.0f, 2.0f);
			}
		}
		if(main.stoped == true) {
			cancel();
		}
		if(timer == 0) {
			for(Block blocks :Starting.cages) {
					blocks.setType(Material.AIR);
			}
			
			for(Player pls :Bukkit.getOnlinePlayers()) {
				Titles.sendTitle(pls, main.color("&cGo!"), "", 10);
				pls.playSound(pls.getLocation(), Sound.ENDERDRAGON_GROWL, 3.0f, 2.0f);
			}
			InGame ing = new InGame();
			ing.runTaskTimer(main.getinstance(), 0, 20);
			main.setState(Gstate.inGame);
			cancel();
		}
		
		timer--;
	}

}
