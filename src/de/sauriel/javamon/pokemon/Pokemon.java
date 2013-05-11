package de.sauriel.javamon.pokemon;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;


import de.sauriel.javamon.moves.Move;
import de.sauriel.javamon.system.LevelUpSystem;
import de.sauriel.javamon.system.SQLiteConnector;


public class Pokemon {
	
	private String name;
	private String customName;
	private String sex;
	private int id;
	
	private int pokedexID;
	private boolean isWild;
	private boolean isTraded = false;
	
	private String typ1;
	private String typ2;
	
	private String[] damagedNormal;
	private String[] damagedDouble;
	private String[] damagedImmune;
	private String[] damagedHalf;
	
	private String expType;
	private int exp = 0;
	private int level;
	
	private int actualHP;
	private String status;
	
	private String item;
	
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
	private int effortValueSpeed;					// aka. INIT
	
	public Pokemon(int pokedexID, int level, boolean isWild, String item, String status, int actualHP) {
		
		this.level = 1;
		this.isWild = isWild;
		this.item = item;
		this.status = status;
		this.actualHP = actualHP;
		
		Random rnd = new Random();
		
		this.id = rnd.nextInt(2147483647);
		
		if (rnd.nextInt(2) == 1) {
			this.sex = "male";
		} else {
			this.sex = "female";
		}
		
		ResultSet pokemon = SQLiteConnector.getPokemonInfo(pokedexID);
		
		try {
			while(pokemon.next()) {
				
				this.pokedexID = pokemon.getInt("pokedexID");
				this.name = pokemon.getString("name");
				this.customName = pokemon.getString("name");
				
				this.typ1 = pokemon.getString("typ1");
				this.typ2 = pokemon.getString("typ2");

				damagedNormal = pokemon.getString("damagedNormal").split(";");
				damagedDouble = pokemon.getString("damagedDouble").split(";");
				damagedImmune = pokemon.getString("damagedImmune").split(";");
				damagedHalf = pokemon.getString("damagedHalf").split(";");
				
				this.expType = pokemon.getString("expType");
				
				this.moves = new ArrayList<>();
				String[] baseMovesSingle = pokemon.getString("baseMoves").split(";");
				for (String baseMoves : baseMovesSingle) {
					this.moves.add(new Move(baseMoves));
				}
				
				this.learnableMoves = new HashMap<>();
				String[] learnableMovesSingle = pokemon.getString("learnableMoves").split(";");
				for (String learnableMovesSplit : learnableMovesSingle) {
					String[] movesMap = learnableMovesSplit.split(",");
					this.learnableMoves.put(Integer.parseInt(movesMap[0]), movesMap[1]);
				}
				
				this.evolveLevel = pokemon.getInt("evolveLevel");
				this.evolveToID = pokemon.getInt("evolveToID");
				
				this.baseHP = pokemon.getInt("hp");
				this.baseAttack = pokemon.getInt("attack");
				this.baseDefense = pokemon.getInt("defense");
				this.baseSpecialAttack = pokemon.getInt("specialAttack");
				this.baseSpecialDefense = pokemon.getInt("specialDefense");
				this.baseSpeed = pokemon.getInt("speed");
				
				this.hp = pokemon.getInt("hp");
				this.attack = pokemon.getInt("attack");
				this.defense = pokemon.getInt("defense");
				this.specialAttack = pokemon.getInt("specialAttack");
				this.specialDefense = pokemon.getInt("specialDefense");
				this.speed = pokemon.getInt("speed");
				
				this.baseExpYield = pokemon.getInt("baseExpYield");
				
				this.effortValueHP = pokemon.getInt("effortValueHP");
				this.effortValueAttack = pokemon.getInt("effortValueAttack");
				this.effortValueDefense = pokemon.getInt("effortValueDefense");
				this.effortValueSpecialAttack = pokemon.getInt("effortValueSpecialAttack");
				this.effortValueSpecialDefense = pokemon.getInt("effortValueSpecialDefense");
				this.effortValueSpeed = pokemon.getInt("effortValueSpeed");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		individualValueHP = new Random().nextInt(32);
		individualValueAttack = new Random().nextInt(32);
		individualValueDefense = new Random().nextInt(32);
		individualValueSpecialAttack = new Random().nextInt(32);
		individualValueSpecialDefense = new Random().nextInt(32);
		individualValueSpeed = new Random().nextInt(32);
		
		if (level > 1) {
			switch (expType) {
			case "erratic":
				receiveExp(LevelUpSystem.getErraticEXP(level - 1));
				break;
			case "fast":
				receiveExp(LevelUpSystem.getFastEXP(level - 1));
				break;
			case "midfast":
				receiveExp(LevelUpSystem.getMidFastEXP(level - 1));
				break;
			case "slow":
				receiveExp(LevelUpSystem.getSlowEXP(level - 1));
				break;
			case "fluctuating":
				receiveExp(LevelUpSystem.getFluctuatingEXP(level - 1));
				break;
			case "midslow":
			default:
				receiveExp(LevelUpSystem.getMidSlowEXP(level - 1));
				break;
			}
		}
	}


	private void levelUp() {
		
		hp = LevelUpSystem.getLevelUpHP(individualValueHP, baseHP, effortValueHP, level);
		attack = LevelUpSystem.getLevelUpStats(individualValueAttack, baseAttack, effortValueAttack, level);
		defense = LevelUpSystem.getLevelUpStats(individualValueDefense, baseDefense, effortValueDefense, level);
		specialAttack = LevelUpSystem.getLevelUpStats(individualValueSpecialAttack, baseSpecialAttack, effortValueSpecialAttack, level);
		specialDefense = LevelUpSystem.getLevelUpStats(individualValueSpecialDefense, baseSpecialDefense, effortValueSpecialDefense, level);
		speed = LevelUpSystem.getLevelUpStats(individualValueSpeed, baseSpeed, effortValueSpeed, level);
		actualHP = hp;
		level++;
		
		System.out.println(customName + " has reached level " + level + ".");
		
		checkLevelUpEvent(level);
	}
	
	private void checkLevelUpEvent(int level) {
		if (learnableMoves.containsKey(level)) {
			learnNewMove(level);
		} else if (evolveLevel == level) {
			evolve(evolveToID);
		}
	}
	
	private void learnNewMove(int level) {
		moves.add(new Move(learnableMoves.get(level)));
		System.out.println(customName + " learned " + learnableMoves.get(level) + ".");
		learnableMoves.remove(level);
		// TODO modify the system, so that only four moves can be learned
	}

	private void evolve(int evolveToID) {
		System.out.println(customName + " evolved to " + SQLiteConnector.getPokemonName(evolveToID) + ".");
		// TODO write evolve method
		// change custom name if it wasn't a custom name
	}

	public void receiveExp(int exp) {
		if (level < 100) {
			this.exp += exp;
			boolean loop = true;
			do {
				switch (expType) {
				case "erratic":
					if (this.exp >= LevelUpSystem.getErraticEXP(level)) {
						levelUp();
					} else {
						loop = false;
					}
					break;
				case "fast":
					if (this.exp >= LevelUpSystem.getFastEXP(level)) {
						levelUp();
					} else {
						loop = false;
					}
					break;
				case "midfast":
					if (this.exp >= LevelUpSystem.getMidFastEXP(level)) {
						levelUp();
					} else {
						loop = false;
					}
					break;
				case "slow":
					if (this.exp >= LevelUpSystem.getSlowEXP(level)) {
						levelUp();
					} else {
						loop = false;
					}
					break;
				case "fluctuating":
					if (this.exp >= LevelUpSystem.getFluctuatingEXP(level)) {
						levelUp();
					} else {
						loop = false;
					}
					break;
				case "midslow":
				default:
					if (this.exp >= LevelUpSystem.getMidSlowEXP(level)) {
						levelUp();
					} else {
						loop = false;
					}
					break;
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
}