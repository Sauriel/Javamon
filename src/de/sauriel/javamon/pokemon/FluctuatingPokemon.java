package de.sauriel.javamon.pokemon;

import de.sauriel.javamon.system.PokemonSet;

public class FluctuatingPokemon extends Pokemon {

	public FluctuatingPokemon(PokemonSet pokemonInfo, int level) {
		super(pokemonInfo, level);
		this.expType = "fluctuating";
	}

	@Override
	protected int getNeededEXP(int level) {
		int exp;
		if (level <= 15) {
			exp = (int) (Math.pow(((double) level + 1), 3) * ((24 + Math.floor(((double) level + 2) / 3)) / 50));
		} else if (level <= 36) {
			exp = (int) (Math.pow(((double) level + 1), 3) * ((14 + ((double) level + 1)) / 50));
			exp = level;
		} else {
			exp = (int) (Math.pow(((double) level + 1), 3) * ((32 + Math.floor(((double) level + 1) / 2)) / 50));
		}
		return exp;	
	}

}
