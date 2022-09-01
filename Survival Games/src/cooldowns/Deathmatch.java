package cooldowns;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


import MySQL.Points;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import src.main;
import state.Gstate;
import utils.GameState;

public class Deathmatch extends BukkitRunnable {


	int timer = 180;
	@Override
	public void run() {
		
		for(Player pls: Bukkit.getOnlinePlayers()) {
			scoreBoard(pls);
		}
		
		if(GameState.GamePlayer.size() == 1) {
			Player p = Bukkit.getPlayer(GameState.GamePlayer.get(0));
			for(Player pls: Bukkit.getOnlinePlayers()) {
				pls.playSound(pls.getLocation(), Sound.LEVEL_UP, 6.0f, 5.0f);
			}
			p.sendMessage(main.Prefix + main.color("&fYou Gained &e100 &2global points &ffor winning."));
			Points.addpoints(p.getUniqueId(), 100);
			Bukkit.broadcastMessage("§6§m------------------------------------");
			Bukkit.broadcastMessage("§c          " + main.Prefix + "         ");
			Bukkit.broadcastMessage("§c                                      ");
			Bukkit.broadcastMessage("         §fWinner: " + p.getDisplayName());
			Bukkit.broadcastMessage("§d                                      ");
			Bukkit.broadcastMessage("§6§m------------------------------------");
			cancel();
			main.setState(Gstate.finish);
			Finish f = new Finish();
			f.runTaskTimer(main.getinstance(), 0, 20);
		}
		if(timer == 0 || GameState.GamePlayer.size() == 0) {
			for(Player pls: Bukkit.getOnlinePlayers()) {
				pls.playSound(pls.getLocation(), Sound.LEVEL_UP, 6.0f, 5.0f);
			}
			Bukkit.broadcastMessage("§6§m------------------------------------");
			Bukkit.broadcastMessage("§c          " + main.Prefix + "         ");
			Bukkit.broadcastMessage("§c                                      ");
			Bukkit.broadcastMessage("                 §fDraw                 ");
			Bukkit.broadcastMessage("§d                                      ");
			Bukkit.broadcastMessage("§6§m------------------------------------");
			cancel();
			main.setState(Gstate.finish);
			Finish f = new Finish();
			f.runTaskTimer(main.getinstance(), 0, 20);
			}


		timer--;
	}
	public String convert(int secs) {
        int h = secs / 3600, i = secs - h * 3600, m = i / 60, s = i - m * 60;
        String timeF = "";
        if (m < 10) {
            timeF = timeF + "0";
        }
        timeF = timeF + m + ":";
        if (s < 10) {
            timeF = timeF + "0";
        }
        timeF = timeF + s;

        return timeF;
    }
public void scoreBoard(Player p) {
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
    Date date = new Date();  
	BPlayerBoard board = Netherboard.instance().getBoard(p);
		board.setName(main.color("&e&lSurvival Games"));
		board.set(main.color("&7&l "),9);
		board.set(main.color("&e&lTime Remaining"),8);
		board.set( main.color("&e│ &f" + convert(timer)),7);
		board.set(main.color("&6  "),6);
		board.set(main.color("&e&lPlayers Left:"),5);
		board.set(main.color("&e│ &f"+GameState.GamePlayer.size()),4);
		board.set(main.color("&c&l "),3);
		board.set(main.color(main.serverIP +" &7│ &7" + formatter.format(date)),2);
		board.set(main.color("&7&m------------------------"),1);
}

}
