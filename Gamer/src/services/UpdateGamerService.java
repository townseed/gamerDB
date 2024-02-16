package services;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import services.DatabaseConnectionService;

public class UpdateGamerService {
	
	//	French the DBA will be able to insert games and game pieces with their attributes 	and will be prompted error when inserting invalid inputs.  
	private DatabaseConnectionService dbService = null;

	public UpdateGamerService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
	public int getGamer(String name) {
		try {
			Statement stmt = this.dbService.getConnection().createStatement();
			String query = "select * from gamer where name = '" + name + "'\n";
			System.out.println(query);
			// later replace it with sp.
			ResultSet rs = stmt.executeQuery(query);
			int nameIndex = rs.findColumn("name");
			while (rs.next()) {
				if(rs.getString(nameIndex) != null) {
					return 1;
				}
			}
			return 0;
		}
		catch (SQLException ex) {
			ex.printStackTrace();
			return -1;
		}
		
	}
	public boolean insertGamer(FetchGamers gamerInfo) {
		try {
			if(gamerInfo.username != null && gamerInfo.username.length() >= 1) {
				String query = "update dbo.gamer set Username = '" + gamerInfo.username + "' where name = '" + gamerInfo.name + "'";
				CallableStatement statement = this.dbService.getConnection().prepareCall(query);
				statement.execute();
			}
			if(gamerInfo.email != null && gamerInfo.email.length() >= 1) {
				String query = "update dbo.gamer set email = '" + gamerInfo.email + "' where name = '" + gamerInfo.name + "'";
				CallableStatement statement = this.dbService.getConnection().prepareCall(query);
				statement.execute();
			}
			if(gamerInfo.dob != null && gamerInfo.dob.length() >= 1) {
				String query = "update dbo.gamer set DateOfBirth = '" + gamerInfo.dob + "' where name = '" + gamerInfo.name + "'";
				CallableStatement statement = this.dbService.getConnection().prepareCall(query);
				statement.execute();
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
