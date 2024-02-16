package services;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import services.DatabaseConnectionService;

public class UpdateGamepieceService {
	
	//	French the DBA will be able to insert games and game pieces with their attributes 	and will be prompted error when inserting invalid inputs.  
	private DatabaseConnectionService dbService = null;

	public UpdateGamepieceService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
	public int getGamer(String name) {
		try {
			Statement stmt = this.dbService.getConnection().createStatement();
			String query = "select * from gamepiece join gamepieceHas on gamepiece.ID = gamepieceHas.gamepieceID join attribute on gamepieceHas.attributeID = attribute.ID  where gamepiece.name = '" + name + "'\n";
			// later replace it with sp.
			ResultSet rs = stmt.executeQuery(query);
			int nameIndex = rs.findColumn("attributeID");
			while (rs.next()) {
				if(rs.getInt(nameIndex) > 0) {
					return rs.getInt(nameIndex);
				}
			}
			return 0;
		}
		catch (SQLException ex) {
			ex.printStackTrace();
			return -1;
		}
		
	}
	public boolean insertOneGamePiece(String gamePieceName, int ID) {
		if (gamePieceName.length() > 20) {
			System.out.println("Service error: gamePieceName length greater than 20");
			return false;
		}
		if(gamePieceName == null || gamePieceName.length() < 1) {
			return false;
		}
		String query = "update dbo.attribute set name = '" + gamePieceName + "' where ID = " + ID;
		
		try {
			CallableStatement statement = this.dbService.getConnection().prepareCall(query);
			statement.execute();
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
