package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import de.sauriel.javamon.system.PokemonSet;
import de.sauriel.javamon.system.SQLiteConnector;

public class SQLPokemonTest {
	
	@Test
	public void nameTest() {
		String name = SQLiteConnector.getPokemonName(1);
		assertTrue(name.equals("Bulbasaur"));
	}

	@Test
	public void test() {
		
		PokemonSet pokemon = SQLiteConnector.getPokemonInfo(1);
		
		pokemon.toString();
		
		try {
			assertTrue(pokemon.getInt("pokedexID") == 1);
			assertTrue(pokemon.getString("name").equals("Bulbasaur"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
