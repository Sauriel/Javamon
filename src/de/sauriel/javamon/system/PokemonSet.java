package de.sauriel.javamon.system;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import de.sauriel.javamon.moves.Move;

public class PokemonSet {
	
	private HashMap<String, Object> pokemonSet;
	
	public PokemonSet(ResultSet result) throws SQLException {
		this.pokemonSet = new HashMap<>();
		
		pokemonSet.put("pokedexID", result.getInt("pokedexID"));
		pokemonSet.put("name", result.getString("name"));
		
		pokemonSet.put("typ1", result.getString("typ1"));
		pokemonSet.put("typ2", result.getString("typ2"));

		pokemonSet.put("damagedNormal", result.getString("damagedNormal").split(";"));
		pokemonSet.put("damagedDouble", result.getString("damagedDouble").split(";"));
		pokemonSet.put("damagedImmune", result.getString("damagedImmune").split(";"));
		pokemonSet.put("damagedHalf", result.getString("damagedHalf").split(";"));
		
		pokemonSet.put("expType", result.getString("expType"));
		
		ArrayList<Move> moves = new ArrayList<>();
		String[] baseMovesSingle = result.getString("baseMoves").split(";");
		for (String baseMoves : baseMovesSingle) {
			moves.add(new Move(baseMoves));
		}
		pokemonSet.put("baseMoves", moves);
		
		HashMap<Integer, String> learnableMoves = new HashMap<>();
		String[] learnableMovesSingle = result.getString("learnableMoves").split(";");
		for (String learnableMovesSplit : learnableMovesSingle) {
			String[] movesMap = learnableMovesSplit.split(",");
			learnableMoves.put(Integer.parseInt(movesMap[0]), movesMap[1]);
		}
		pokemonSet.put("learnableMoves", learnableMoves);
		
		pokemonSet.put("evolveLevel", result.getInt("evolveLevel"));
		pokemonSet.put("evolveToID", result.getInt("evolveToID"));
		
		pokemonSet.put("baseHP", result.getInt("hp"));
		pokemonSet.put("baseAttack", result.getInt("attack"));
		pokemonSet.put("baseDefense", result.getInt("defense"));
		pokemonSet.put("baseSpecialAttack", result.getInt("specialAttack"));
		pokemonSet.put("baseSpecialDefense", result.getInt("specialDefense"));
		pokemonSet.put("baseSpeed", result.getInt("speed"));
		
		pokemonSet.put("hp", result.getInt("hp"));
		pokemonSet.put("attack", result.getInt("attack"));
		pokemonSet.put("defense", result.getInt("defense"));
		pokemonSet.put("specialAttack", result.getInt("specialAttack"));
		pokemonSet.put("specialDefense", result.getInt("specialDefense"));
		pokemonSet.put("speed", result.getInt("speed"));
		
		pokemonSet.put("baseExpYield", result.getInt("baseExpYield"));
		
		pokemonSet.put("effortValueHP", result.getInt("effortValueHP"));
		pokemonSet.put("effortValueAttack", result.getInt("effortValueAttack"));
		pokemonSet.put("effortValueDefense", result.getInt("effortValueDefense"));
		pokemonSet.put("effortValueSpecialAttack", result.getInt("effortValueSpecialAttack"));
		pokemonSet.put("effortValueSpecialDefense", result.getInt("effortValueSpecialDefense"));
		pokemonSet.put("effortValueSpeed", result.getInt("effortValueSpeed"));
	}
	
	public String getString(String key) {
		return (String) pokemonSet.get(key);
	}
	
	public int getInt(String key) {
		return (int) pokemonSet.get(key);
	}
	
	public String[] getStringArray(String key) {
		return (String[]) pokemonSet.get(key);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Move> getArrayList(String key) {
		return (ArrayList<Move>) pokemonSet.get(key);
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<Integer, String> getHashMap(String key) {
		return (HashMap<Integer, String>) pokemonSet.get(key);
	}
}
