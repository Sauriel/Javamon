package de.sauriel.javamon.pokemon;

import de.sauriel.javamon.system.PokemonSet;
import de.sauriel.javamon.system.SQLiteConnector;

public class PokemonCreator {
	
	public static Pokemon createNewPokemon(int pokedexID, int level) {
		
		PokemonSet pokemonInfo = SQLiteConnector.getPokemonInfo(pokedexID);
		
		Pokemon pokemon;
		
		switch (pokemonInfo.getString("expType")) {
		case "erratic":
			pokemon = new ErraticPokemon(pokemonInfo, level);
			break;
		case "fast":
			pokemon = new FastPokemon(pokemonInfo, level);
			break;
		case "midfast":
			pokemon = new MidFastPokemon(pokemonInfo, level);
			break;
		case "midslow":
			pokemon = new MidSlowPokemon(pokemonInfo, level);
			break;
		case "slow":
			pokemon = new SlowPokemon(pokemonInfo, level);
			break;
		case "fluctuating":
		default:
			pokemon = new FluctuatingPokemon(pokemonInfo, level);
			break;
		}
		
		pokemon.setStatus("normal");
		
		return pokemon;
	}
	
	public static Pokemon createNewWildPokemon(int pokedexID, int level) {
		Pokemon pokemon = createNewPokemon(pokedexID, level);
		pokemon.setIsWild(true);
		return pokemon;
	}
	
	public static Pokemon createNewTradedPokemon(int pokedexID, int level) {
		Pokemon pokemon = createNewPokemon(pokedexID, level);
		pokemon.setIsTraded(true);
		return pokemon;
	}

}
