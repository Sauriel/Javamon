package de.sauriel.javamon.pokemon;

import de.sauriel.javamon.system.PokemonSet;

public class MidFastPokemon extends Pokemon {

	public MidFastPokemon(PokemonSet pokemonInfo, int level) {
		super(pokemonInfo, level);
		this.expType = "midfast";
	}

	@Override
	protected int getNeededEXP(int level) {
		int exp = (int) Math.pow(((double) level + 1), 3);
		return exp;	
	}

}
