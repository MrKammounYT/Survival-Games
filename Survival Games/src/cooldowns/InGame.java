package cooldowns;



import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import MySQL.Points;
import events.Chest_open;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import src.main;
import state.Gstate;
import utils.GameState;
import utils.LocationAPI;

public class InGame extends BukkitRunnable{
	
	int timer = 0;
	int dtimer= 60;
	GameState gm = new GameState();

	@Override
	public void run() {
		
		
		for(Player pls: Bukkit.getOnlinePlayers()) {
			scoreBoard(pls);
		}
		
		if(timer> 0 && (timer%540)==0) {
			 Bukkit.broadcastMessage(main.Prefix + main.color("§fAll chest Has been refiled"));
			 Chest_open.clc.clear();		 
		}
		if(main.stoped == true) {
			cancel();
		}
		
		//deathmatch

		if(GameState.GamePlayer.size() <= gm.getDeathMatchPlayers()) {
			if(dtimer==4||dtimer == 30||dtimer==20||dtimer==50||dtimer==60||dtimer==10||dtimer==5||dtimer==1||dtimer==2||dtimer==3) {
				Bukkit.broadcastMessage(main.Prefix+main.color("&fDeathMatch Starting in &a" + dtimer));
				for(Player pls: Bukkit.getOnlinePlayers()) {
					pls.playSound(pls.getLocation(), Sound.CLICK, 4.0f,5.0f);
				}
			}
			dtimer--;
		}
		//deathmatch
		if(dtimer ==0) {
			
			World w = Bukkit.getWorld(LocationAPI.getLocation("d"+1).getWorld().getName());
			w.setTime(100);
			w.setDifficulty(Difficulty.EASY);
			w.setFullTime(100);
			w.setAutoSave(false);
			w.setThunderDuration(0);
			w.setThundering(false);
			w.setStorm(false);
			if(GameState.spec.size() > 0) {
			for(int i=0;i<GameState.spec.size();i++) {
				Player p = Bukkit.getPlayer(GameState.spec.get(i));
				p.teleport(LocationAPI.getLocation("specdm"));
				BPlayerBoard board = Netherboard.instance().getBoard(p);
				board.clear();

				}
			}
			for(int i=0;i<GameState.GamePlayer.size();i++) {
				Player p = Bukkit.getPlayer(GameState.GamePlayer.get(i));
				p.teleport(LocationAPI.getLocation("d"+(i+1)));
				BPlayerBoard board = Netherboard.instance().getBoard(p);
				board.clear();
			}
			main.setState(Gstate.deathMatch);
			Deathmatch m = new Deathmatch();
			m.runTaskTimer(main.getinstance(), 0,20);
			cancel();
			}
		//deathmatch
		
		//win
		if(GameState.GamePlayer.size() == 1 ) {
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
		//win
		
		
		//draw
		if(timer == 3600 || GameState.GamePlayer.size() == 0) {
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
		//draw
		
		
		
		
		timer++;
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
	board.set(main.color("&7&l "),14);
	board.set(main.color("&e&lTime Elapsed"),13);
	board.set(main.color("&e│ &f" + convert(timer)),12);
	board.set(main.color("&6&l  "),11);
	board.set(main.color("&e&lPlayers Left:"),10);
	board.set(main.color("&e│ &f"+GameState.GamePlayer.size()),9);
	board.set(main.color(""),8);
	board.set(main.color("&e&lGame Info"),7);
	board.set(main.color("&e│ &fKills: &a"+ gm.GetGamePlayerinfo().get(p.getName()).getkills()),6);
	board.set(main.color("&e│ &fPoints: &a"+ gm.GetGamePlayerinfo().get(p.getName()).getpoints()),5);
	board.set(main.color("&e│ &fCrates: &a"+ gm.GetGamePlayerinfo().get(p.getName()).getcrates()),4);
	board.set(main.color("&a  "),3);
	board.set(main.color(main.serverIP +" &7│ &7" + formatter.format(date)),2);
	board.set(main.color("&7&m------------------------"),1);
	}

	
}
