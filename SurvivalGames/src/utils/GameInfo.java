package utils;

public class GameInfo {
	
	private  String map;
	private  int vote;
	
	
	
	
	
	public GameInfo(String name,int vote) {
		this.map = name;
		this.vote = vote;;
		
	}
	
	
	public  int getVote() {
		return vote;
	}	
	
	public  void addVotes(int n) {
		vote += n;	
	}
	
	public  void removeVotes(int n) {
		vote -=n;
	}
	
	public  String getMapName() {
		return map;
	}
	
	
	
	
	
	
	
	
	
	
	
	


	

}
