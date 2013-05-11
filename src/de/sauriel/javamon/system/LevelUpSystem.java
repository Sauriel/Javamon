package de.sauriel.javamon.system;
public class LevelUpSystem {

	public static int getErraticEXP(int level) {
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

	public static int getFastEXP(int level) {
		int exp = (int) ((4 * Math.pow(((double) level + 1), 3)) / 5);
		return exp;		
	}

	public static int getMidFastEXP(int level) {
		int exp = (int) Math.pow(((double) level + 1), 3);
		return exp;		
	}

	public static int getMidSlowEXP(int level) {
		int exp = (int) (1.2 * (Math.pow(((double) level + 1), 3)) - (15 * Math.pow(((double) level + 1), 2)) + (100 * ((double) level + 1)) - 140);
		return exp;		
	}

	public static int getSlowEXP(int level) {
		int exp = (int) ((5 * Math.pow(((double) level + 1), 3)) / 4);
		return exp;		
	}

	public static int getFluctuatingEXP(int level) {
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
