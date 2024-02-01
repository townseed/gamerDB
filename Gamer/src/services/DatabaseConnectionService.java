package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

//import sodabase.ui.SodaByRestaurant;

public class DatabaseConnectionService {

	//DO NOT EDIT THIS STRING, YOU WILL RECEIVE NO CREDIT FOR THIS TASK IF THIS STRING IS EDITED
	private final String SampleURL = "jdbc:sqlserver://${dbServer};databaseName=${dbName};user=${user};password={${pass}}";
	//this string is WRONG. encryption is turned off, which requires additional arguments.
	private Connection connection = null;

	private String databaseName;
	private String serverName;

	public DatabaseConnectionService(String serverName, String databaseName) {
		//DO NOT CHANGE THIS METHOD
		this.serverName = serverName;
		this.databaseName = databaseName;
	}

	public boolean connect(String user, String pass) {
		//done: Task 1
		//BUILD YOUR CONNECTION STRING HERE USING THE SAMPLE URL 
		String password = "I l0ve cat$";
		String serverName = "golem.csse.rose-hulman.edu";
		String dbName = "s1g7";
		String username = "Townseed";
				
		String fullURL = SampleURL.replace("${dbServer}", this.serverName)
				.replace("${dbName}", this.databaseName)
				.replace("${user}", user)
				.replace("${pass}", pass) + ";encrypt=false;trustServerCertificate=true;";
		final String SampleURL = "jdbc:sqlserver://golem.csse.rose-hulman.edu;databaseName=s1g7;user=soiefeam;password=~;encrypt=false;";
		try {
			this.connection = DriverManager.getConnection(SampleURL);			
			System.out.println("connection success.");
			return true;
		}
		catch(SQLException e) {
			System.err.println("Database connection failed");
			e.printStackTrace();
		}finally {
			
		}
		return false;
	}
	

	public Connection getConnection() {
		return this.connection;
	}
	
	public void closeConnection() {
		try {
			if(connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			//rip in peace
		}
	}

}
