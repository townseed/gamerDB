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
		int returnedVal = -1;
		
		try {
			CallableStatement statement = this.dbService.getConnection().prepareCall(query);
//			statement.registerOutParameter(1, returnedVal);
			statement.setString(1, gamerInfo.name);
			statement.setString(2, gamerInfo.username);
			statement.setString(3, gamerInfo.email);
			statement.setString(4, gamerInfo.dob);
			statement.execute();
			//returnedVal = statement.getInt(1);
			if(returnedVal == -1 || returnedVal == 0) {
				System.out.println("Add completed successfully!");
			}
			else {
				System.out.println("from gamePieceService: Error in SQL execution");
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
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

}
