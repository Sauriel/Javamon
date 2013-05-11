package de.sauriel.javamon.moves;

public class Move {
	
	private String moveName;
	
	public Move(String moveName) {
		this.moveName = moveName;
	}
	
	@Override
	public String toString() {
		return "Move: " + moveName;
	}

}
