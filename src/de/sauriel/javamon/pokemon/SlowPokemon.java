package de.sauriel.javamon.pokemon;

import de.sauriel.javamon.system.PokemonSet;

public class SlowPokemon extends Pokemon {

	public SlowPokemon(PokemonSet pokemonInfo, int level) {
		super(pokemonInfo, level);
		this.expType = "slow";
	}

	@Override
	protected int getNeededEXP(int level) {
		int exp = (int) ((5 * Math.pow(((double) level + 1), 3)) / 4);
		return exp;	
	}

}
