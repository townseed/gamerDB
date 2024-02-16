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
			JOptionPane.showMessageDialog(null, "Failed to retrieve game names.");
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
		String query = "select * from gamer";
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
					"An error ocurred while retrieving data. See printed stack trace.");
			ex.printStackTrace();
			return new ArrayList<FetchGamers>();
		}

	}
	public ArrayList<String> getAllGameNames(){
		ArrayList<String> gameNames = new ArrayList<String>();
		try {
			Statement stmt = this.dbService.getConnection().createStatement();
			String query = "select [name] from game"; 
			// later replace it with sp.
			ResultSet rs = stmt.executeQuery(query);
			int nameIndex = rs.findColumn("name");
			while (rs.next()) {
				gameNames.add(rs.getString(nameIndex));
			}
			System.out.println(gameNames);
			return gameNames;
		}
		catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Failed to retrieve game names.");
			ex.printStackTrace();
			return new ArrayList<String>();
		}
	}
	public TwinStringList getAllGamePieces(){
		TwinStringList results = new TwinStringList();
		try {
			Statement stmt = this.dbService.getConnection().createStatement();
			String query = "select p.[name] as [Gamepiece Name], g.[name] as [Game Name] from gamepiece p join game g on g.id = p.gameID\n"; 
			// later replace it with sp.
			ResultSet rs = stmt.executeQuery(query);
			int GPnameIndex = rs.findColumn("gamepiece name");
			int GnameIndex = rs.findColumn("game name");
			while (rs.next()) {
				results.first.add(rs.getString(GPnameIndex));
				results.second.add(rs.getString(GnameIndex));
			}
			//System.out.println(gamerNames);
			return results;
		}
		catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Failed to retrieve gamepiece names.");
			ex.printStackTrace();
			return new TwinStringList();
		}
	}
	public PentaStringList getUserMatchRecords(String username){
		PentaStringList results = new PentaStringList();
		try {
			Statement stmt = this.dbService.getConnection().createStatement();
			String query = "exec getUserMatchRecords '" + username + "'"; 
			// later replace it with sp.
			ResultSet rs = stmt.executeQuery(query);
			int gameIndex = rs.findColumn("Game Name");
			int pieceIndex = rs.findColumn("Gamepiece Name");
			int dateIndex = rs.findColumn("date");
			int resultIndex = rs.findColumn("result");
			int scoreIndex = rs.findColumn("score");
			while (rs.next()) {
				results.first.add(rs.getString(gameIndex));
				results.second.add(rs.getString(pieceIndex));
				results.third.add(rs.getString(dateIndex));
				results.fourth.add(rs.getString(resultIndex));
				results.fith.add(rs.getString(scoreIndex));
			}
//			System.out.print(gamerNames);
			return results;
		}
		catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Failed to retrieve gamer results.");
			ex.printStackTrace();
			return new PentaStringList();
		}
	}
	public PentaStringList getLeaderboard(){
		PentaStringList results = new PentaStringList();
		try {
			Statement stmt = this.dbService.getConnection().createStatement();
			String query = "select date, result, score, game.name as [Game Name], gamepiece.name as [Game Piece] from match join playedIn on match.id = playedIn.matchID join game on match.gameID = game.ID join gamepiece on playedIn.gamepieceID = gamepiece.ID\r\n"
					+ "order by score desc"; 
			// later replace it with sp.
			ResultSet rs = stmt.executeQuery(query);
			int gameIndex = rs.findColumn("Game Name");
			int pieceIndex = rs.findColumn("Game Piece");
			int dateIndex = rs.findColumn("date");
			int resultIndex = rs.findColumn("result");
			int scoreIndex = rs.findColumn("score");
			while (rs.next()) {
				results.first.add(rs.getString(gameIndex));
				results.second.add(rs.getString(pieceIndex));
				results.third.add(rs.getString(dateIndex));
				results.fourth.add(rs.getString(resultIndex));
				results.fith.add(rs.getString(scoreIndex));
			}
//			System.out.print(gamerNames);
			return results;
		}
		catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Failed to retrieve gamer results.");
			ex.printStackTrace();
			return new PentaStringList();
		}
	}
	
}
