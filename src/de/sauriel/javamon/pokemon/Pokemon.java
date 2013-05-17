package de.sauriel.javamon.pokemon;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;


import de.sauriel.javamon.moves.Move;
import de.sauriel.javamon.system.PokemonSet;
import de.sauriel.javamon.system.SQLiteConnector;


public abstract class Pokemon {
	
	private String name;
	private String customName;
	private String sex;
	private int id;
	
	private int pokedexID;
	private boolean isWild = false;
	private boolean isTraded = false;
	
	private String typ1;
	private String typ2;
	
	private String[] damagedNormal;
	private String[] damagedDouble;
	private String[] damagedImmune;
	private String[] damagedHalf;
	
	protected String expType;
	private int exp = 0;
	private int level;
	
	private int actualHP;
	private String status;
	
	private String item = "none";
	
	private ArrayList<Move> moves;
	
	private HashMap<Integer, String> learnableMoves; 
	
	private int evolveLevel;
	private int evolveToID;
	
	private int hp;
	private int attack;
	private int defense;
	private int specialAttack;
	private int specialDefense;
	private int speed;
	
	private int baseHP;
	private int baseAttack;
	private int baseDefense;
	private int baseSpecialAttack;
	private int baseSpecialDefense;
	private int baseSpeed;
	
	private int accuracy = 100;
	private int evasion = 100;
	
	private final int individualValueHP;
	private final int individualValueAttack;
	private final int individualValueDefense;
	private final int individualValueSpecialAttack;
	private final int individualValueSpecialDefense;
	private final int individualValueSpeed;
	
	private int baseExpYield;
	
	private int effortValueHP;
	private int effortValueAttack;
	private int effortValueDefense;
	private int effortValueSpecialAttack;
	private int effortValueSpecialDefense;
	private int effortValueSpeed;					// aka INIT
	
	public Pokemon(PokemonSet pokemonInfo, int level) {
		
		this.level = 1;
		
		Random rnd = new Random();
		
		this.id = rnd.nextInt(2147483647);
		
		if (rnd.nextInt(2) == 1) {
			this.sex = "male";
		} else {
			this.sex = "female";
		}
		
		this.pokedexID = pokemonInfo.getInt("pokedexID");
		this.name = pokemonInfo.getString("name");
		this.customName = pokemonInfo.getString("name");
				
		this.typ1 = pokemonInfo.getString("typ1");
		this.typ2 = pokemonInfo.getString("typ2");

		damagedNormal = pokemonInfo.getStringArray("damagedNormal");
		damagedDouble = pokemonInfo.getStringArray("damagedDouble");
		damagedImmune = pokemonInfo.getStringArray("damagedImmune");
		damagedHalf = pokemonInfo.getStringArray("damagedHalf");
			
		//this.expType = pokemon.getString("expType");
				
		this.moves = pokemonInfo.getArrayList("baseMoves");
		
		this.learnableMoves = pokemonInfo.getHashMap("learnableMoves");
				
		this.evolveLevel = pokemonInfo.getInt("evolveLevel");
		this.evolveToID = pokemonInfo.getInt("evolveToID");
		
		this.baseHP = pokemonInfo.getInt("hp");
		this.baseAttack = pokemonInfo.getInt("attack");
		this.baseDefense = pokemonInfo.getInt("defense");
		this.baseSpecialAttack = pokemonInfo.getInt("specialAttack");
		this.baseSpecialDefense = pokemonInfo.getInt("specialDefense");
		this.baseSpeed = pokemonInfo.getInt("speed");
				
		this.hp = pokemonInfo.getInt("hp");
		this.attack = pokemonInfo.getInt("attack");
		this.defense = pokemonInfo.getInt("defense");
		this.specialAttack = pokemonInfo.getInt("specialAttack");
		this.specialDefense = pokemonInfo.getInt("specialDefense");
		this.speed = pokemonInfo.getInt("speed");
		
		this.baseExpYield = pokemonInfo.getInt("baseExpYield");
				
		this.effortValueHP = pokemonInfo.getInt("effortValueHP");
		this.effortValueAttack = pokemonInfo.getInt("effortValueAttack");
		this.effortValueDefense = pokemonInfo.getInt("effortValueDefense");
		this.effortValueSpecialAttack = pokemonInfo.getInt("effortValueSpecialAttack");
		this.effortValueSpecialDefense = pokemonInfo.getInt("effortValueSpecialDefense");
		this.effortValueSpeed = pokemonInfo.getInt("effortValueSpeed");
		
		this.actualHP = hp;
		
		individualValueHP = new Random().nextInt(32);
		individualValueAttack = new Random().nextInt(32);
		individualValueDefense = new Random().nextInt(32);
		individualValueSpecialAttack = new Random().nextInt(32);
		individualValueSpecialDefense = new Random().nextInt(32);
		individualValueSpeed = new Random().nextInt(32);
		
		if (level > 1) {
			receiveExp(getNeededEXP(level - 1));
		}
	}
	
	protected abstract int getNeededEXP(int level);


	private void levelUp() {
		
		hp = getLevelUpHP();
		attack = getLevelUpStats(individualValueAttack, baseAttack, effortValueAttack);
		defense = getLevelUpStats(individualValueDefense, baseDefense, effortValueDefense);
		specialAttack = getLevelUpStats(individualValueSpecialAttack, baseSpecialAttack, effortValueSpecialAttack);
		specialDefense = getLevelUpStats(individualValueSpecialDefense, baseSpecialDefense, effortValueSpecialDefense);
		speed = getLevelUpStats(individualValueSpeed, baseSpeed, effortValueSpeed);
		actualHP = hp;
		level++;
		
		System.out.println(customName + " has reached level " + level + ".");
		
		checkLevelUpEvent();
	}

	private int getLevelUpHP() {
		int newHP = (int) ((((double) individualValueHP + (double) baseHP + ((Math.sqrt((double) effortValueHP)) / 8) + 50) * (double) level) / 50) + 10;
		return newHP;
	}

	private int getLevelUpStats(int individualValueStat, int baseStat, int effortValueStat) {
		int newStat = (int) ((((double) individualValueStat + (double) baseStat + ((Math.sqrt((double) effortValueStat)) / 8)) * (double) level) / 50) + 5;
		return newStat;
	}
	
	private void checkLevelUpEvent() {
		if (learnableMoves.containsKey(level)) {
			learnNewMove();
		} else if (evolveLevel == level) {
			evolve();
		}
	}
	
	private void learnNewMove() {
		moves.add(new Move(learnableMoves.get(level)));
		System.out.println(customName + " learned " + learnableMoves.get(level) + ".");
		learnableMoves.remove(level);
		// TODO modify the system, so that only four moves can be learned
	}

	private void evolve() {
		System.out.println(customName + " evolved to " + SQLiteConnector.getPokemonName(evolveToID) + ".");
		// TODO write evolve method
		// change custom name if it wasn't a custom name
	}

	public void receiveExp(int exp) {
		if (level < 100) {
			this.exp += exp;
			boolean loop = true;
			do {
				if (this.exp >= getNeededEXP(level)) {
					levelUp();
				} else {
					loop = false;
				}
			} while (loop);
		}
	}
	
	public int yieldExp(int levelWinner, boolean winnerIsTraded, String winnerItem, int partcipatingPokemon) {
		double wildModifier;
		double ownerModifier;
		double luckyEggModifier;
		double expPowerModifier = 1;
		
		if (isWild) {
			wildModifier = 1;
		} else {
			wildModifier = 1.5;
		}
		
		if (winnerIsTraded) {
			ownerModifier = 1.5;
		} else {
			ownerModifier = 1;
		}
		
		if (winnerItem == "Lucky Egg") {
			luckyEggModifier = 1.5;
		} else {
			luckyEggModifier = 1;
		}
		
		double yieldedExp = (((wildModifier * (double) baseExpYield * (double) level) / (5 * (double) partcipatingPokemon)) * 
				(Math.pow((2 * (double) level + 10), 2.5) / Math.pow(((double) level + (double) levelWinner + 10), 2.5)) + 1) * 
				ownerModifier * luckyEggModifier * expPowerModifier;
		
		return (int) yieldedExp;
	}
	
	public void listDetails() {
		System.out.println("name:\t\t\t\t" + name);
		System.out.println("customName:\t\t\t" + customName);
		System.out.println("sex:\t\t\t\t" + sex);
		System.out.println("id:\t\t\t\t" + id);
		System.out.println("pokedexID:\t\t\t" + pokedexID);
		System.out.println("isWild:\t\t\t\t" + isWild);
		System.out.println("isTraded:\t\t\t" + isTraded);
		System.out.println("typ1:\t\t\t\t" + typ1);
		System.out.println("typ2:\t\t\t\t" + typ2);
		System.out.println("damagedNormal:\t\t\t" + Arrays.toString(damagedNormal));
		System.out.println("damagedDouble:\t\t\t" + Arrays.toString(damagedDouble));
		System.out.println("damagedImmune:\t\t\t" + Arrays.toString(damagedImmune));
		System.out.println("damagedHalf:\t\t\t"	+ Arrays.toString(damagedHalf));
		System.out.println("expType:\t\t\t" + expType);
		System.out.println("exp:\t\t\t\t" + exp);
		System.out.println("level:\t\t\t\t" + level);
		System.out.println("actualHP:\t\t\t" + actualHP);
		System.out.println("status:\t\t\t\t" + status);
		System.out.println("item:\t\t\t\t" + item);
		System.out.println("moves:\t\t\t\t" + moves);
		System.out.println("learnableMoves:\t\t\t" + learnableMoves);
		System.out.println("evolveLevel:\t\t\t" + evolveLevel);
		System.out.println("evolveToID:\t\t\t" + evolveToID);
		System.out.println("hp:\t\t\t\t" + hp);
		System.out.println("attack:\t\t\t\t" + attack);
		System.out.println("defense:\t\t\t" + defense);
		System.out.println("specialAttack:\t\t\t" + specialAttack);
		System.out.println("specialDefense:\t\t\t" + specialDefense);
		System.out.println("speed:\t\t\t\t" + speed);
		System.out.println("baseHP:\t\t\t\t" + baseHP);
		System.out.println("baseAttack:\t\t\t" + baseAttack);
		System.out.println("baseDefense:\t\t\t" + baseDefense);
		System.out.println("baseSpecialAttack:\t\t" + baseSpecialAttack);
		System.out.println("baseSpecialDefense:\t\t" + baseSpecialDefense);
		System.out.println("baseSpeed:\t\t\t" + baseSpeed);
		System.out.println("accuracy:\t\t\t" + accuracy);
		System.out.println("evasion:\t\t\t" + evasion);
		System.out.println("individualValueHP:\t\t" + individualValueHP);
		System.out.println("individualValueAttack:\t\t" + individualValueAttack);
		System.out.println("individualValueDefense:\t\t" + individualValueDefense);
		System.out.println("individualValueSpecialAttack:\t" + individualValueSpecialAttack);
		System.out.println("individualValueSpecialDefense:\t" + individualValueSpecialDefense);
		System.out.println("individualValueSpeed:\t\t" + individualValueSpeed);
		System.out.println("baseExpYield:\t\t\t" + baseExpYield);
		System.out.println("effortValueHP:\t\t\t" + effortValueHP);
		System.out.println("effortValueAttack:\t\t" + effortValueAttack);
		System.out.println("effortValueDefense:\t\t" + effortValueDefense);
		System.out.println("effortValueSpecialAttack:\t" + effortValueSpecialAttack);
		System.out.println("effortValueSpecialDefense:\t" + effortValueSpecialDefense);
		System.out.println("effortValueSpeed:\t\t" + effortValueSpeed);
	}

	@Override
	public String toString() {
		return "Pokemon [name=" + name + ", customName=" + customName
				+ ", sex=" + sex + ", id=" + id + ", pokedexID=" + pokedexID
				+ ", isWild=" + isWild + ", isTraded=" + isTraded + ", typ1="
				+ typ1 + ", typ2=" + typ2 + ", damagedNormal="
				+ Arrays.toString(damagedNormal) + ", damagedDouble="
				+ Arrays.toString(damagedDouble) + ", damagedImmune="
				+ Arrays.toString(damagedImmune) + ", damagedHalf="
				+ Arrays.toString(damagedHalf) + ", expType=" + expType
				+ ", exp=" + exp + ", level=" + level + ", actualHP="
				+ actualHP + ", status=" + status + ", item=" + item
				+ ", moves=" + moves + ", learnableMoves=" + learnableMoves
				+ ", evolveLevel=" + evolveLevel + ", evolveToID=" + evolveToID
				+ ", hp=" + hp + ", attack=" + attack + ", defense=" + defense
				+ ", specialAttack=" + specialAttack + ", specialDefense="
				+ specialDefense + ", speed=" + speed + ", baseHP=" + baseHP
				+ ", baseAttack=" + baseAttack + ", baseDefense=" + baseDefense
				+ ", baseSpecialAttack=" + baseSpecialAttack
				+ ", baseSpecialDefense=" + baseSpecialDefense + ", baseSpeed="
				+ baseSpeed + ", accuracy=" + accuracy + ", evasion=" + evasion
				+ ", individualValueHP=" + individualValueHP
				+ ", individualValueAttack=" + individualValueAttack
				+ ", individualValueDefense=" + individualValueDefense
				+ ", individualValueSpecialAttack="
				+ individualValueSpecialAttack
				+ ", individualValueSpecialDefense="
				+ individualValueSpecialDefense + ", individualValueSpeed="
				+ individualValueSpeed + ", baseExpYield=" + baseExpYield
				+ ", effortValueHP=" + effortValueHP + ", effortValueAttack="
				+ effortValueAttack + ", effortValueDefense="
				+ effortValueDefense + ", effortValueSpecialAttack="
				+ effortValueSpecialAttack + ", effortValueSpecialDefense="
				+ effortValueSpecialDefense + ", effortValueSpeed="
				+ effortValueSpeed + "]";
	}
	
	public void setIsWild(boolean isWild) {
		this.isWild = isWild;
	}
	
	public void setIsTraded(boolean isTraded) {
		this.isTraded = isTraded;
	}
	
	public void setItem(String item) {
		this.item = item;
	}
	
	public void setActualHP(int actualHP) {
		this.actualHP = actualHP;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<Move> getMoves() {
		return moves;
	}
}