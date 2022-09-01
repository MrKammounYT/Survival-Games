package cooldowns;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import src.main;
import state.Gstate;
import utils.GameState;
import utils.MapLocationApi;
import utils.Titles;

public class Starting extends BukkitRunnable {

	int timer = 45;
	
	public static ArrayList<Block> cages = new ArrayList<>();
	HashMap<String, Integer> map = new HashMap<>();
	GameState gm = new GameState();
	public static String winner;
	@Override
	public void run() {
		for(Player pls :Bukkit.getOnlinePlayers()) {
			pls.setLevel(timer);
			
		}
		if(main.stoped == true) {
			cancel();
		}
		
		if(timer == 15) {
			for(Player pls :Bukkit.getOnlinePlayers()) {
				pls.getInventory().clear();
				pls.closeInventory();
			}
			winner = getmapwinner();
			if(noVotes(winner)) {
				winner = getmapwinner2();
			}
			Bukkit.broadcastMessage(main.Prefix + main.color("&b"+winner + " &eHas won the voting with &b" + gm.GetGameMapInfo().get(winner).getVote() + " &eVotes!"));
		}
		
		if(timer == 20 || timer == 10 || timer == 5 || timer == 3 || timer == 2 || timer == 1) {
			Bukkit.broadcastMessage(main.Prefix + main.color("&eGame Starting in &b"+timer+ " &eSeconds"));
			for(Player pls :Bukkit.getOnlinePlayers()) {
				Titles.sendTitle(pls, main.color("&fGame Start in"), main.color("&a"+timer) ,10);
				pls.playSound(pls.getLocation(), Sound.CLICK, 3.0f, 2.0f);
			}
		}
		
	
		if(GameState.GamePlayer.size() < gm.getMinPlayers()) {
			cancel();
			Bukkit.broadcastMessage(main.Prefix + main.color("&6Not enough Players to Start The Game"));
			Waiting wt = new Waiting();
			wt.runTaskTimer(main.getinstance(), 0, 20);
		}
		
		if(timer == 1) {
			main.setState(Gstate.waittogame);

		}
		
		if(timer == 0) {
			//start  game
			Bukkit.broadcastMessage(main.Prefix + main.color("&eTeleporting...."));
			World w = Bukkit.getWorld(winner);
			w.setTime(100);
			w.setDifficulty(Difficulty.EASY);
			w.setFullTime(100);
			w.setAutoSave(false);
			w.setThunderDuration(0);
			w.setThundering(false);
			w.setStorm(false);
			for(int i=0;i<GameState.GamePlayer.size();i++) {
				String ps = GameState.GamePlayer.get(i);
				Player p = Bukkit.getPlayer(ps);
				BPlayerBoard board = Netherboard.instance().getBoard(p);
				board.clear();
				p.teleport(MapLocationApi.getLocation(MapLocationApi.getMapId(winner),winner,"s"+(i+1)));
				Location cagelc = MapLocationApi.getLocation(MapLocationApi.getMapId(winner),winner,"s"+(i+1));
				Location lc = cagelc.clone().add(0,-2,0);
				if(p.hasPermission("eaglemc.eagle")) {
					cage(lc,Material.getMaterial(95),11);

					}
					else if(p.hasPermission("eaglemc.diamond")){
						cage(lc,Material.getMaterial(95),3);

					}
					else if(p.hasPermission("eaglemc.gold")) {
						cage(lc,Material.getMaterial(95),4);

					}else {
						cage(lc,Material.GLASS,0);

					}
				
			}
			PreGame pr = new PreGame();
			pr.runTaskTimer(main.getinstance(), 0, 20);
			cancel();
			// teleport players
		}
		
		timer--;
	}


	
	public void cage(Location f,Material mt,int data) {
		Location lc = f.clone().add(0,0,0);
      Location upper = lc.clone().add(0,2,0);//the block above the Player
      if(upper.getBlock().getType() == Material.AIR) {
       upper.getBlock().setType(mt);
       upper.getBlock().setData((byte)data);
       cages.add(upper.getBlock());
      }
       
       for(int i=0;i<2;i++) {     //3 is the height of the cage
           Location x = lc.clone().add(1,i,0);//get the block in front of the player
           Location x2 = lc.clone().add(-1,i,0);//get the block behind the Player
           if(x.getBlock().getType() == Material.AIR) {
           x.getBlock().setType(mt);
           x.getBlock().setData((byte)data);
           cages.add(x.getBlock());

           }
           if(x2.getBlock().getType() == Material.AIR) {
           x2.getBlock().setType(mt);
           x2.getBlock().setData((byte)data);
           cages.add(x2.getBlock());

           }
           Location z = lc.clone().add(0,i,1);//get the block next to the player(left/right)
           Location z2 = lc.clone().add(0,i,-1);//get the block next to the player (left/right)
           if(z.getBlock().getType() == Material.AIR) {
           z.getBlock().setType(mt);
           z.getBlock().setData((byte)data);
           cages.add(z.getBlock());

           }
           if(z2.getBlock().getType() == Material.AIR) {
           z2.getBlock().setType(mt);
           z2.getBlock().setData((byte)data);
           cages.add(z2.getBlock());
           }


       }
	}
	
	public boolean noVotes(String s) {
		if(map.get(winner) == 0) {
			return true;
		}
		return false;
	}
	
	public String getmapwinner2() {
		int x = new Random().nextInt(5) + 1;
		String name = gm.GetGameMapInfo().get(MapLocationApi.getMapByID(x)).getMapName();
		return name;
		
	}
	public String getmapwinner() {
		for(int i=1;i<6;i++) {
			String name = gm.GetGameMapInfo().get(MapLocationApi.getMapByID(i)).getMapName();
			map.put(name, gm.GetGameMapInfo().get(name).getVote());
		}
		 int maxValueInMap=(Collections.max(map.values()));  // This will return max value in the Hashmap
	        for (Entry<String, Integer> entry : map.entrySet()) {  // Itrate through hashmap
	            if (entry.getValue()==maxValueInMap) {
	                return entry.getKey();     
	            }
	        }
	        return null;
	}
	
}
