package tests;

import static org.junit.Assert.*;

import java.sql.ResultSet;

import org.junit.Test;

import de.sauriel.javamon.system.SQLiteConnector;

public class SQLPokemonTest {

	@Test
	public void test() {
		
		ResultSet pokemon = SQLiteConnector.getPokemonInfo(1);
		
		try {
			
			while(pokemon.next()) {
				assertTrue(pokemon.getInt("pokedexID") == 1);
				assertTrue(pokemon.getString("name").equals("Bulbasaur"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(SQLiteConnector.closeConnection());
	}

}
