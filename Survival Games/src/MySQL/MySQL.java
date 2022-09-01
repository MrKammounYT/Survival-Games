package MySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import src.main;

public class MySQL {


	static String host = main.getinstance().getConfig().getString("host");
	static String port =main.getinstance().getConfig().getString("port");
	static String database = main.getinstance().getConfig().getString("database");
	static String username = main.getinstance().getConfig().getString("username");
	static String password = main.getinstance().getConfig().getString("password");
	static Connection connection;
	
	
	public static boolean checkConnection() {  
		try {
			if (connection.isClosed()) {
				return false;
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
		    
	}
	
	
	public static boolean isConnected() {
		return (connection == null ? false:true);
	}
	
	public static void connect() throws ClassNotFoundException,SQLException{
		 if(!isConnected()) {
			 connection = (Connection) DriverManager.getConnection("jdbc:mysql://" +
				     host + ":" + port + "/" + database + "?useSSL=false",
				     username, password);
		 }
	}
	
	public static void disconnect() {
		 if(isConnected()) {
			 try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		 }

	}
	
	public static Connection getConnection() {
		return connection;
	}
	

}
