package services;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import services.DatabaseConnectionService;

public class UpdateGamepieceService {
	
	//	French the DBA will be able to insert games and game pieces with their attributes 	and will be prompted error when inserting invalid inputs.  
	private DatabaseConnectionService dbService = null;

	public UpdateGamepieceService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
	
	public ArrayList<String> getAdjectives(String gameName){
		ArrayList<String> results = new ArrayList<String>();
		try {
			String query = "select a.[name] as [descriptor] from attribute a join game g on g.id = a.gameID where g.[name] = ?\n";
			PreparedStatement stmt = this.dbService.getConnection().prepareStatement(query);
			stmt.setString(1, gameName);
			ResultSet rs = stmt.executeQuery();
			int GPnameIndex = rs.findColumn("descriptor");
			while (rs.next()) {
				results.add(rs.getString(GPnameIndex));
			}
			return results;
		}
		catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Failed to retrieve descriptions.");
			ex.printStackTrace();
			return new ArrayList<String>();
		}
	}
//	public boolean insertOneGamePiece(String gamePieceName, int ID) {
//		if (gamePieceName.length() > 20) {
//			System.out.println("Service error: gamePieceName length greater than 20");
//			return false;
//		}
//		if(gamePieceName == null || gamePieceName.length() < 1) {
//			return false;
//		}
//		String query = "update dbo.attribute set name = '" + gamePieceName + "' where ID = " + ID;
//		
//		try {
//			CallableStatement statement = this.dbService.getConnection().prepareCall(query);
//			statement.execute();
//		} catch (SQLException e) {
//			
//			e.printStackTrace();
//			return false;
//		}
//		return true;
//	}
	public ArrayList<String> discribe(String gameName, String pieceName) {
		ArrayList<String> results = new ArrayList<String>();
		try {
			String query = "select gph.[value] as value from gamepieceHas gph join attribute a on a.id = gph.attributeID join game g on g.id = a.gameID join gamepiece p on p.id = gph.gamepieceID where g.name = ? and p.[name] = ?";
			PreparedStatement stmt = this.dbService.getConnection().prepareStatement(query);
			stmt.setString(1, gameName);
			stmt.setString(2, pieceName);
			ResultSet rs = stmt.executeQuery();
			int GPnameIndex = rs.findColumn("value");
			while (rs.next()) {
				results.add(rs.getString(GPnameIndex));
			}
			return results;
		}
		catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Failed to retrieve descriptions.");
			ex.printStackTrace();
			return new ArrayList<String>();
		}
	}

	public boolean setDescription(String gameName, String pieceName, String attributeName, String value){
		try {
			String query = "{? = call discribeGamePiece(?, ?, ?, ?)}";
			
			CallableStatement statement = this.dbService.getConnection().prepareCall(query);
			statement.registerOutParameter(1, java.sql.Types.INTEGER);
			statement.setString(2, pieceName);
			statement.setString(3, gameName);
			statement.setString(4, attributeName);
			statement.setString(5, value);
			return statement.execute();
		}
		catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Failed to retrieve descriptions.");
			ex.printStackTrace();
			return false;
		}
	}

}
