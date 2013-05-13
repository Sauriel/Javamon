package de.sauriel.javamon.system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteConnector {
	
	
	
	static Connection connection = null;
	
	
	
	private static void connectToDB() {

		//load SQLite JDBC Driver
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println("SQLite JDBC Driver could not be found.");
			System.err.println(e.getMessage());
		}
		
	}
	
	
	
	public static boolean closeConnection() {
		try {
			if(connection != null) {
				connection.close();
				return true;
			} else {
				return true;
			}
		} catch(SQLException e) {
			System.out.println("Connection couldn't be closed.");
			System.err.println(e);
			return false;
		}
	}
	
	
	
	private static ResultSet getResult(String query) {
		
		connectToDB();
		
		ResultSet results = null;
		
		try {
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:sqlite/Javamon.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.

			results = statement.executeQuery(query);
			
//			statement.executeUpdate("INSERT INTO Stats values(1, 'Test')");

		} catch(SQLException e) {
			System.out.println("Database file could not be found.");
			System.err.println(e.getMessage());
		}
		
		// ToDo Exception for empty result
		
		return results;
	}
	
	
	
	public static PokemonSet getPokemonInfo(int pokedexID) {
		
		String query = "SELECT * FROM Stats WHERE pokedexID=" + pokedexID;
		PokemonSet result = null;
		
		try {
			result = new PokemonSet(getResult(query));
			closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	public static String getPokemonName(int pokedexID) {
		
		String query = "SELECT name FROM Stats WHERE pokedexID=" + pokedexID;
		String result = null;
		
		try {
			ResultSet pokemon = getResult(query);
			result = pokemon.getString("name");
			closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
