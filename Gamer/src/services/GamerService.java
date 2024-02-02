package services;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

//import sodabase.ui.SodaByRestaurant;

public class GamerService {
	private DatabaseConnectionService dbService = null;
	
	public GamerService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
	
	public ArrayList<String> getGamerNames(){
		ArrayList<String> gamerNames = new ArrayList<String>();
		try {
			Statement stmt = this.dbService.getConnection().createStatement();
			String query = "call getAllGamers\n"; 
			// later replace it with sp.
			ResultSet rs = stmt.executeQuery(query);
			int nameIndex = rs.findColumn("name");
			while (rs.next()) {
				gamerNames.add(rs.getString(nameIndex));
			}
			return gamerNames;
		}
		catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Failed to retrieve gamer names.");
			ex.printStackTrace();
			return new ArrayList<String>();
		}
	}
	
	public boolean insertOneGamer(String username, String name, String email, String dob) {
		String query = "{? = call [dbo].[insert_Gamer](?,?,?,?)}";
		int returnedVal = -1;
		
		try {
			CallableStatement statement = this.dbService.getConnection().prepareCall(query);
			statement.registerOutParameter(1, java.sql.Types.INTEGER);
			statement.setString(2, username);
			statement.setString(3, name);
			statement.setString(4, email);
			statement.setString(5, dob);
			statement.execute();
			returnedVal = statement.getInt(1);
			if(returnedVal == -1 || returnedVal == 0) {
				System.out.println("Add completed successfully!");
			}
			else {
				System.out.println("Add has experienced an unidentified error. Please attempt to rerun the program.");
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		if(returnedVal == 0) return true;
//		if(returnedVal == 1) {
//			JOptionPane.showMessageDialog(null, "Failed to add new soda: name cannot be empty");
//			return false;
//		}
//		if(returnedVal == 2) {
//			JOptionPane.showMessageDialog(null, "Failed to add new soda: soda already exists");
//			return false;
//		}
		return true;
	}
	
	public ArrayList<FetchGamers> getGamersTable(){
		String query = "SELECT * FROM Gamer";
		try {
			Statement stmt = this.dbService.getConnection().createStatement();
			return parseResults(stmt.executeQuery(query));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "default find all case failed");
			e.printStackTrace();
		}return new ArrayList<FetchGamers>();
	}
	
	private ArrayList<FetchGamers> parseResults(ResultSet rs) {
		try {
			ArrayList<FetchGamers> fetchGamers = new ArrayList<FetchGamers>();
			int usernameIndex = rs.findColumn("Username");
			int nameIndex = rs.findColumn("name");
			int emailIndex = rs.findColumn("email");
			int dobIndex = rs.findColumn("dateofbirth");
			while (rs.next()) {
				fetchGamers.add(new FetchGamers(rs.getString(usernameIndex), 
						rs.getString(nameIndex),
						rs.getString(emailIndex), 
						rs.getString(dobIndex)
						)
						);
			}
//			System.out.println(fetchGamers.size());
			return fetchGamers;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null,
					"An error ocurred while retrieving sodas by restaurants. See printed stack trace.");
			ex.printStackTrace();
			return new ArrayList<FetchGamers>();
		}

	}
	public ArrayList<String> getAllGameNames(){
		ArrayList<String> gamerNames = new ArrayList<String>();
		try {
			Statement stmt = this.dbService.getConnection().createStatement();
			String query = "exec getAllGameNames\n"; 
			// later replace it with sp.
			ResultSet rs = stmt.executeQuery(query);
			int nameIndex = rs.findColumn("name");
			while (rs.next()) {
				gamerNames.add(rs.getString(nameIndex));
			}
			System.out.println(gamerNames);
			return gamerNames;
		}
		catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Failed to retrieve gamer names.");
			ex.printStackTrace();
			return new ArrayList<String>();
		}
	}
	public ArrayList<String> getAllGamePieces(){
		ArrayList<String> gamerNames = new ArrayList<String>();
		try {
			Statement stmt = this.dbService.getConnection().createStatement();
			String query = "exec getAllGamePieces\n"; 
			// later replace it with sp.
			ResultSet rs = stmt.executeQuery(query);
			int nameIndex = rs.findColumn("name");
			while (rs.next()) {
				gamerNames.add(rs.getString(nameIndex));
			}
			System.out.println(gamerNames);
			return gamerNames;
		}
		catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Failed to retrieve gamer names.");
			ex.printStackTrace();
			return new ArrayList<String>();
		}
	}
	public ArrayList<String> getAllMatchRecords(String date, String game, String result){
		ArrayList<String> gamerNames = new ArrayList<String>();
		try {
			Statement stmt = this.dbService.getConnection().createStatement();
			String query = "exec getAllMatchRecords '" + date + "', '" + game + "', '" + result + "'\n"; 
			// later replace it with sp.
			ResultSet rs = stmt.executeQuery(query);
			int nameIndex = rs.findColumn("ID");
			while (rs.next()) {
				gamerNames.add(rs.getString(nameIndex));
			}
			System.out.print(gamerNames);
			return gamerNames;
		}
		catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Failed to retrieve gamer names.");
			ex.printStackTrace();
			return new ArrayList<String>();
		}
	}
	
}
