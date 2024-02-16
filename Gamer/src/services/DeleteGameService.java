package services;

import java.sql.CallableStatement;
import java.sql.SQLException;

import services.DatabaseConnectionService;

public class DeleteGameService {
	
	//	French the DBA will be able to insert games and game pieces with their attributes 	and will be prompted error when inserting invalid inputs.  
	private DatabaseConnectionService dbService = null;

	public DeleteGameService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
	
	public boolean deleteGamer(String userName) {
		String query = "{? = call DeleteGame(?)}";
		int returnedVal = -1;
		
		try {
			CallableStatement statement = this.dbService.getConnection().prepareCall(query);
			statement.registerOutParameter(1, java.sql.Types.INTEGER);
			statement.setString(2, userName);
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
