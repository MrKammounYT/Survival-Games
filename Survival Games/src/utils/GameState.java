package utils;

import java.util.ArrayList;
import java.util.HashMap;



public class GameState {
	
	private static int minPlayers= 2;
	private static int maxPlayers= 24;
	private static int deathmatchPlayers= 4;
	private static HashMap<String, GameInfo> gmi = new HashMap<>();
	private static HashMap<String, PlayerInfo> gpi = new HashMap<>();

	public static ArrayList<String> GamePlayer = new ArrayList<>();
	public static ArrayList<String> spec = new ArrayList<>();
	
	
	public HashMap<String, GameInfo> GetGameMapInfo(){
		return gmi;
	}
	public HashMap<String, PlayerInfo> GetGamePlayerinfo(){
		return gpi;
	}
	
	public int getMinPlayers() {
		return minPlayers;
	}
	public int getMaxPlayers() {
		return maxPlayers;
	}
	public int getDeathMatchPlayers() {
		return deathmatchPlayers;
	}
	

}
