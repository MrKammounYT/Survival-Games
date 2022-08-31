package MySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.entity.Player;

public class points {

	

	public static void createTable() {
		try {
			PreparedStatement	ps = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS points "
					+"(NAME VARCHAR(100),UUID VARCHAR(100),POINTS INT(100),PRIMARY KEY (NAME))");
		ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void createPlayer(Player p) {
		try {
			UUID uuid = p.getUniqueId();
		if(!Playerexists(uuid)) {
			PreparedStatement ps2 = MySQL.getConnection().prepareStatement("INSERT INTO points"
		+ " (NAME,UUID) VALUES (?,?)");
		ps2.setString(1, p.getName());
		ps2.setString(2, uuid.toString());
		ps2.executeUpdate();
		return;
		}
		
		} catch (SQLException e) {
e.printStackTrace();
}
	}
	public static void deleteplayer(Player p) {
		try {
			UUID uuid = p.getUniqueId();
			PreparedStatement ps = MySQL.getConnection().prepareStatement("DELETE FROM points WHERE UUID=" + uuid);
			ps.executeUpdate();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
	

	public static boolean Playerexists(UUID uuid) {
		try {
		PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM points WHERE UUID=?");
		ps.setString(1, uuid.toString());
		ResultSet results = ps.executeQuery();
		if(results.next()) {
			return true;
		}
		else {
			return false;
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public static void addpoints(UUID uuid,int points)
	{
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE points SET POINTS=? WHERE UUID=?");
			ps.setInt(1,getpoints(uuid) + points);
			ps.setString(2, uuid.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
e.printStackTrace();		}
		
		
	}
	
	public static void removepoints(UUID uuid,int points)
	{
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE points SET POINTS=? WHERE UUID=?");
			ps.setInt(1,getpoints(uuid) - points);
			ps.setString(2, uuid.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
e.printStackTrace();		}
		
		
	}
	
	
	public static int getpoints(UUID uuid) {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT POINTS FROM points WHERE UUID=?");
			ps.setString(1, uuid.toString());
			ResultSet rs = ps.executeQuery();
			int points = 0;
			if(rs.next()) {
				points = rs.getInt("points");
				return points;
			}
		} catch (SQLException e) {
e.printStackTrace();	
}
		return 0;

	}
}
