package importGamers;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
public class Main {

	public static void main(String[] args) {
		ArrayList<String> queries = manageScanner();
		manageConnection(queries);
		
	}
	public static ArrayList<String> manageScanner() {
		// Starting source for scanner code = https://www.javatpoint.com/how-to-read-csv-file-in-java
		Scanner scanner = null;
		String firstLine = null;
		ArrayList<String> columnHeaders = new ArrayList<String>();
		ArrayList<String> columnHeadersGamer = new ArrayList<String>();
		ArrayList<String> columnHeadersMatch = new ArrayList<String>();
		ArrayList<String> columnHeadersGamepiece = new ArrayList<String>();
		ArrayList<String> columnHeadersGamepieceHas = new ArrayList<String>();
		ArrayList<String> columnHeadersGamePieceAttribute = new ArrayList<String>();
		ArrayList<String> columnHeadersGameAttribute = new ArrayList<String>();
		ArrayList<String> currentRow = new ArrayList<String>();
		ArrayList<String> Gamer = new ArrayList<String>();
		ArrayList<String> Match = new ArrayList<String>();
		ArrayList<String> Gamepiece = new ArrayList<String>();
		ArrayList<String> GamepieceHas = new ArrayList<String>();
		ArrayList<String> Attribute = new ArrayList<String>();
		ArrayList<String> GameAttribute = new ArrayList<String>();
		ArrayList<ArrayList<String>> GamerList = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> MatchList = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> GamepieceList = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> GamepieceHasList = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> AttributeList = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> GameAttributeList = new ArrayList<ArrayList<String>>();
 		int[] spacing = new int[] {4, 9, 11, 12, 13, 14};
		
		ArrayList<String> queries = new ArrayList<String>();
		try {
			scanner = new Scanner(new File("src/sample_Data.csv"));
			scanner.useDelimiter(",");  //delimiter pattern for .csv files
			if(scanner.hasNextLine()) {
				firstLine = scanner.nextLine();
				StringBuilder partialString = new StringBuilder();
				for(char c : firstLine.toCharArray()) {
					if(c == ',') {
						columnHeaders.add(partialString.toString());
						partialString = new StringBuilder();
					}
					else {
						partialString.append(c);
					}
				}
				columnHeaders.add(partialString.toString());
				for(String i : columnHeaders) {
					if(i.startsWith("gamer.")) {
						columnHeadersGamer.add(i.replace("gamer.", ""));
					}
					else if(i.startsWith("match.")) {
						columnHeadersMatch.add(i.replace("match.", ""));
					}
					else if(i.startsWith("gamepiece.")) {
						columnHeadersGamepiece.add(i.replace("gamepiece.", ""));
					}else if(i.startsWith("gamepiecehas.") && !i.startsWith("gamepiecehas.attribute.")) {
						columnHeadersGamepieceHas.add(i.replace("gamepiecehas.", ""));
					}
					else if(i.startsWith("gamepiecehas.attribute.")) {
						columnHeadersGamePieceAttribute.add(i.replace("gamepiecehas.attribute.", ""));
					}
					else if(i.startsWith("gamehas.attribute.")) {
						columnHeadersGameAttribute.add(i.replace("gamehas.attribute.", ""));
					}
					else {
						System.out.println(i);
					}
				}
			}
			else {
				System.out.println("Could not get column names or create a table");
			}
			int count = 0;
			String currentItem = "";
			while (scanner.hasNextLine())  
			{  
				StringBuilder partialString = new StringBuilder();
				count = 0;
				Gamer = new ArrayList<String>();
				Match = new ArrayList<String>();
				Gamepiece = new ArrayList<String>();
				GamepieceHas = new ArrayList<String>();
				Attribute = new ArrayList<String>();
				GameAttribute = new ArrayList<String>();
				//String nextLine = scanner.nextLine();
//				for(char c : nextLine.toCharArray()) {
//					if(c == ',') {
//						currentRow.add(partialString.toString());
//						partialString = new StringBuilder();
//					}
//					else {
//						partialString.append(c);
//					}
//				}
//				currentRow.add(partialString.toString());
				currentRow.add(scanner.next());
				while(count <= spacing[5]) {
					currentItem = currentRow.remove(0);
					if(currentItem.length() == 0) {
						break;
					}
					if(count <= spacing[0]) {
						Gamer.add(currentItem);
					}
					else if(count <= spacing[1]) {
						Match.add(currentItem);
					}
					else if(count <= spacing[2]) {
						Gamepiece.add(currentItem);
					}
					else if(count <= spacing[3]) {
						GamepieceHas.add(currentItem);
					}
					else if(count <= spacing[4]) {
						Attribute.add(currentItem);
					}
					else {
						GameAttribute.add(currentItem);
					}
					count++;
				}
				if(Gamer.size() >= 1) {
					GamerList.add(Gamer);
				}
				if(Match.size() >= 1) {
					MatchList.add(Match);
				}
				if(Gamepiece.size() >= 1) {
					GamepieceList.add(Gamepiece);
				}
				if(GamepieceHas.size() >= 1) {
					GamepieceHasList.add(GamepieceHas);
				}
				if(Attribute.size() >= 1) {
					AttributeList.add(Attribute);
				}
				if(GameAttribute.size() >= 1) {
					GameAttributeList.add(GameAttribute);
				}
			}   
			scanner.close();  
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  

		queries.add("drop table gamers");
		queries.add("drop table matches");
		queries.add("drop table gamepieces");
		queries.add("drop table gamepieceshave");
		queries.add("drop table gamepieceattributes");
		queries.add("drop table gameattributess");
		queries.add(tableQueryCreator("gamers", columnHeadersGamer));
		queries.add(tableQueryCreator("matches", columnHeadersMatch));
		queries.add(tableQueryCreator("gamepieces", columnHeadersGamepiece));
		queries.add(tableQueryCreator("gamepieceshave", columnHeadersGamepieceHas));
		queries.add(tableQueryCreator("gamepieceattributes", columnHeadersGamePieceAttribute));
		queries.add(tableQueryCreator("gameattributess", columnHeadersGameAttribute));
		for(ArrayList<String> a : GamerList) {
			queries.add(tupleQueryCreator("gamers", columnHeadersGamer, a));
		}
		for(ArrayList<String> b : MatchList) {
			queries.add(tupleQueryCreator("match", columnHeadersMatch, b));
		}
		for(ArrayList<String> c : GamepieceList) {
			queries.add(tupleQueryCreator("gamepiece", columnHeadersGamepiece, c));
		}
		for(ArrayList<String> d : GamepieceHasList) {
			queries.add(tupleQueryCreator("gamepieceshave", columnHeadersGamepieceHas, d));
		}
		for(ArrayList<String> e : AttributeList) {
			queries.add(tupleQueryCreator("attribute", columnHeadersGamePieceAttribute, e));
		}
		for(ArrayList<String> f : GameAttributeList) {
			queries.add(tupleQueryCreator("gameattributess", columnHeadersGameAttribute, f));
		}
		return queries;
	}
	public static String tableQueryCreator(String tableName, ArrayList<String> tableSpecs) {
		//https://stackoverflow.com/questions/6520999/create-table-if-not-exists-equivalent-in-sql-server
		//https://stackoverflow.com/questions/10991894/auto-increment-primary-key-in-sql-server-management-studio-2012
		String returnString = "if not exists (select * from sysobjects where name='" + tableName + "' and xtype='U')"
				+ "    create table " + tableName + " ("; //" ( ID INT NOT NULL IDENTITY(1, 1) PRIMARY KEY, ";
		for(String i : tableSpecs) {
			returnString = returnString + i + " nvarchar(64) not null, ";
		}
		returnString = returnString.substring(0, returnString.length() - 2);
		returnString = returnString + ")";
		return returnString;
	}
	public static String tupleQueryCreator(String tableName, ArrayList<String> columnNames, ArrayList<String> values) {
		StringBuilder returnString = new StringBuilder();
		returnString.append("INSERT INTO ");
		returnString.append(tableName);
		returnString.append(" (");
		for(String i : columnNames) {
			returnString.append("[" + i + "]");
			returnString.append(", ");
		}
		returnString.deleteCharAt(returnString.length() - 1);
		returnString.deleteCharAt(returnString.length() - 1);
		returnString.append(") VALUES (");
		for(String j : values) {
			returnString.append("'" + j + "'");
			returnString.append(", ");
		}
		returnString.deleteCharAt(returnString.length() - 1);
		returnString.deleteCharAt(returnString.length() - 1);
		returnString.append(");");
		return returnString.toString();
	}
	
	public static void manageConnection(ArrayList<String> queries) {
		Connection connection = null;
		final String SampleURL = "jdbc:sqlserver://golem.csse.rose-hulman.edu;databaseName=s1g7;user=soiefeam;password=~;encrypt=false;";
		try {
			connection = DriverManager.getConnection(SampleURL);
			System.out.println("Connection open!");
			Statement stmt;
			for(String query : queries) {
				stmt = connection.createStatement();
				stmt.executeUpdate(query);
				stmt.close();
			}
			try {
				connection.close();
				System.out.println("Connection closed!");
			} catch (SQLException e) {
				e.printStackTrace();
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}