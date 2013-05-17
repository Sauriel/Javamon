package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import de.sauriel.javamon.pokemon.Pokemon;
import de.sauriel.javamon.pokemon.PokemonCreator;

public class PokemonTest {

	@Test
	public void test() {
		Pokemon bulbasaur = PokemonCreator.createNewPokemon(1, 30);
		bulbasaur.listDetails();
		assertFalse(bulbasaur.toString().isEmpty());
		assertFalse(bulbasaur.toString().contains("null"));
	}

}
