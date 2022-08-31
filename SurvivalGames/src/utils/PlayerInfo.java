package utils;

public class PlayerInfo {
	
	private int kills,points,crates;
	
	
	private String VotedMap;
	private boolean voted;
	
	public PlayerInfo(int kills,int points,int crates,boolean vote) {
		this.kills = kills;
		this.points = points;
		this.crates = crates;
		this.voted = vote;
	}
	
	public void setvote(boolean n) {
		this.voted = n;
	}
	public boolean hasvoted() {
		return this.voted;
	}
	
	public void setVotedMap(String n) {
		this.VotedMap = n;
	}
	public String GetVotedMap() {
		return VotedMap;
	}
	
	
	
	public void addkills(int amount) {
		this.kills += amount;
	}
	public void addpoints(int amount) {
		this.points += amount;
	}
	public void addcrates(int amount) {
		this.crates += amount;
	}
	
	
	public int getkills() {
		return this.kills;
	}
	public int getpoints() {
		return this.points;
	}
	public int getcrates() {
		return this.crates;
	}
	
	
	

}
