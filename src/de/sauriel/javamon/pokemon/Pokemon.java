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
	
	private int hp;
	private int attack;
	private int defense;
	private int specialAttack;
	private int specialDefense;
	private int speed;
	
	private int accuracy = 100;
	private int evasion = 100;
	
	private final int individualValueHP;
	private final int individualValueAttack;
	private final int individualValueDefense;
	private final int individualValueSpecialAttack;
	private final int individualValueSpecialDefense;
	private final int individualValueInitiative;
	private final int individualValueSpeed;
	
	private int baseExpYield;
	
	private int effortValueHP;
	private int effortValueAttack;
	private int effortValueDefense;
	private int effortValueSpecialAttack;
	private int effortValueSpecialDefense;
	private int effortValueSpeed;					// INIT
	
	public Pokemon(int pokedexID, int level, boolean isWild, String item, String status, int actualHP) {
		
		this.level = level;
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
		individualValueInitiative = new Random().nextInt(32);
		individualValueSpeed = new Random().nextInt(32);
	}


	private void levelUp() {
		level++;
		
		
		// TODO http://wiki.pokemon-online.eu/wiki/Stat_formula
		System.out.println("LEVEL UP");
	}
	
	public void receiveExp(int exp) {
		if (level < 100) {
			this.exp += exp;
			switch (expType) {
			case "erratic":
				if (this.exp >= LevelUpSystem.getErraticEXP(level)) {
					levelUp();
				}
				break;
			case "fast":
				if (this.exp >= LevelUpSystem.getFastEXP(level)) {
					levelUp();
				}
				break;
			case "midfast":
				if (this.exp >= LevelUpSystem.getMidFastEXP(level)) {
					levelUp();
				}
				break;
			case "slow":
				if (this.exp >= LevelUpSystem.getSlowEXP(level)) {
					levelUp();
				}
				break;
			case "fluctuating":
				if (this.exp >= LevelUpSystem.getFluctuatingEXP(level)) {
					levelUp();
				}
				break;
			case "midslow":
			default:
				if (this.exp >= LevelUpSystem.getMidSlowEXP(level)) {
					levelUp();
				}
				break;
			}
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


	@Override
	public String toString() {
		return "Pokemon [name=" + name + ", customName=" + customName
				+ ", sex=" + sex + ", id=" + id + ", pokedexID=" + pokedexID
				+ ", isWild=" + isWild + ", isTraded=" + isTraded + ", typ1="
				+ typ1 + ", typ2=" + typ2 + ", damageNormal="
				+ Arrays.toString(damagedNormal) + ", damageDouble="
				+ Arrays.toString(damagedDouble) + ", damageImmun="
				+ Arrays.toString(damagedImmune) + ", damageHalf="
				+ Arrays.toString(damagedHalf) + ", expType=" + expType
				+ ", exp=" + exp + ", level=" + level + ", actualHP="
				+ actualHP + ", status=" + status + ", item=" + item
				+ ", moves=" + moves + ", learnableMoves=" + learnableMoves
				+ ", evolveLevel=" + evolveLevel + ", hp=" + hp + ", attack="
				+ attack + ", defense=" + defense + ", specialAttack="
				+ specialAttack + ", specialDefense=" + specialDefense
				+ ", speed=" + speed + ", accuracy=" + accuracy + ", evasion="
				+ evasion + ", individualValueHP=" + individualValueHP
				+ ", individualValueAttack=" + individualValueAttack
				+ ", individualValueDefense=" + individualValueDefense
				+ ", individualValueSpecialAttack="
				+ individualValueSpecialAttack
				+ ", individualValueSpecialDefense="
				+ individualValueSpecialDefense
				+ ", individualValueInitiative=" + individualValueInitiative
				+ ", individualValueSpeed=" + individualValueSpeed
				+ ", baseExpYield=" + baseExpYield + ", effortValueHP="
				+ effortValueHP + ", effortValueAttack=" + effortValueAttack
				+ ", effortValueDefense=" + effortValueDefense
				+ ", effortValueSpecialAttack=" + effortValueSpecialAttack
				+ ", effortValueSpecialDefense=" + effortValueSpecialDefense
				+ ", effortValueSpeed=" + effortValueSpeed + "]";
	}
	
	
	
	
	// TODO Methoden
}