package de.sauriel.javamon.pokemon;

import de.sauriel.javamon.system.PokemonSet;

public class MidSlowPokemon extends Pokemon {

	public MidSlowPokemon(PokemonSet pokemonInfo, int level) {
		super(pokemonInfo, level);
		this.expType = "midslow";
	}

	@Override
	protected int getNeededEXP(int level) {
		int exp = (int) (1.2 * (Math.pow(((double) level + 1), 3)) - (15 * Math.pow(((double) level + 1), 2)) + (100 * ((double) level + 1)) - 140);
		return exp;	
	}

}
