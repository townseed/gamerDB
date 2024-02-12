package services;

import java.sql.CallableStatement;
import java.sql.SQLException;

import services.DatabaseConnectionService;

public class InsertGamerService {
	
	//	French the DBA will be able to insert games and game pieces with their attributes 	and will be prompted error when inserting invalid inputs.  
	private DatabaseConnectionService dbService = null;

	public InsertGamerService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
	
	public boolean insertGamer(FetchGamers gamerInfo) {
		String query = "insert into gamer([name], username, email, dateOfBirth) values(?,?,?,?)";
		
		try {
			CallableStatement statement = this.dbService.getConnection().prepareCall(query);
			statement.setString(1, gamerInfo.name);
			statement.setString(2, gamerInfo.username);
			statement.setString(3, gamerInfo.email);
			statement.setString(4, gamerInfo.dob);
			statement.execute();
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
