package services;

import java.sql.CallableStatement;
import java.sql.SQLException;

import services.DatabaseConnectionService;

public class UpdateGamepieceService {
	
	//	French the DBA will be able to insert games and game pieces with their attributes 	and will be prompted error when inserting invalid inputs.  
	private DatabaseConnectionService dbService = null;

	public UpdateGamepieceService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
	
	public boolean insertOneGamePiece(String gamePieceName) {
		if (gamePieceName.length() > 20) {
			System.out.println("Service error: gamePieceName length greater than 20");
			return false;
		}
		
		String query = "insert into gamepiece([name]) values(?)";
		
		try {
			CallableStatement statement = this.dbService.getConnection().prepareCall(query);
			statement.setString(1, gamePieceName);
			statement.execute();
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
