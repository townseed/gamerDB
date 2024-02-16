package services;

import java.sql.CallableStatement;
import java.sql.SQLException;

import services.DatabaseConnectionService;

public class DeleteMatchService {
	
	//	French the DBA will be able to insert games and game pieces with their attributes 	and will be prompted error when inserting invalid inputs.  
	private DatabaseConnectionService dbService = null;

	public DeleteMatchService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
	
	public boolean deleteGamer(String date, String location) {
		String query = "{? = call DeleteMatch(?, ?)}";
		int returnedVal = -1;
		
		try {
			CallableStatement statement = this.dbService.getConnection().prepareCall(query);
			statement.registerOutParameter(1, java.sql.Types.INTEGER);
			statement.setString(2, date);
			statement.setString(3, location);
			statement.execute();
			returnedVal = statement.getInt(1);
			if(returnedVal == 0) {
				System.out.println("delete completed successfully!");
				return true;
			}else if (returnedVal == 1) {
				System.out.println("delete not executed");
				return false;
			}
			else {
				System.out.println("from gamePieceService: Error in SQL execution");
				return false;
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}
	}

}
