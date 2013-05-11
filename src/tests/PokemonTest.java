package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import de.sauriel.javamon.pokemon.Pokemon;

public class PokemonTest {

	@Test
	public void test() {
		Pokemon bulbasaur = new Pokemon(1, 1, true, "Bitter Berry", "normal", 45);
		System.out.println(bulbasaur.toString());
		assertFalse(bulbasaur.toString().isEmpty());
		assertFalse(bulbasaur.toString().contains("null"));
	}

}
