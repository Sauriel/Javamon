package de.sauriel.javamon.pokemon;

import de.sauriel.javamon.system.PokemonSet;

public class FastPokemon extends Pokemon {

	public FastPokemon(PokemonSet pokemonInfo, int level) {
		super(pokemonInfo, level);
		this.expType = "fast";
	}

	@Override
	protected int getNeededEXP(int level) {
		int exp = (int) ((4 * Math.pow(((double) level + 1), 3)) / 5);
		return exp;	
	}

}
