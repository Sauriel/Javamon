package de.sauriel.javamon.pokemon;

import de.sauriel.javamon.system.PokemonSet;

public class ErraticPokemon extends Pokemon {

	public ErraticPokemon(PokemonSet pokemonInfo, int level) {
		super(pokemonInfo, level);
		this.expType = "erratic";
	}

	@Override
	protected int getNeededEXP(int level) {
		int exp;
		if (level <= 50) {
			exp = (int) (Math.pow(((double) level + 1), 3) * ((100 - ((double) level + 1)) / 50));
		} else if (level <= 68) {
			exp = (int) (Math.pow(((double) level + 1), 3) * ((150 - ((double) level + 1)) / 100));
		} else if (level <= 98) {
			exp = (int) (Math.pow(((double) level + 1), 3) * ((1911 - (10 * ((double) level + 1))) / 1500));
		} else {
			exp = (int) (Math.pow(((double) level + 1), 3) * ((160 - ((double) level + 1)) / 100));
		}
		return exp;	
	}

}
