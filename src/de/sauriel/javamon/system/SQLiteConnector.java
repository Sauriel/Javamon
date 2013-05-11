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
	
	public static ResultSet getPokemonInfo(int pokedexID) {
		
		connectToDB();
		
		ResultSet results = null;
		
		try {
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:sqlite/Javamon.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.

			results = statement.executeQuery("SELECT * FROM Stats WHERE pokedexID=" + pokedexID);
			
//			statement.executeUpdate("INSERT INTO Stats values(1, 'Test')");

		} catch(SQLException e) {
			System.out.println("Database file could not be found.");
			System.err.println(e.getMessage());
		}
		
		// ToDo Exception for empty result
		
		return results;
	}
	
	public static String getPokemonName(int pokedexID) {
		
		connectToDB();
		
		String pokemonName = null;
		
		try {
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:sqlite/Javamon.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.

			ResultSet results = statement.executeQuery("SELECT name FROM Stats WHERE pokedexID=" + pokedexID);
			
			pokemonName = results.getString("name");

		} catch(SQLException e) {
			System.out.println("Database file could not be found.");
			System.err.println(e.getMessage());
		}
		
		// ToDo Exception for empty result
		
		return pokemonName;
	}
}
