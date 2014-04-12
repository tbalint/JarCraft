/**
* This file is based on and translated from the open source project: Sparcraft
* https://code.google.com/p/sparcraft/
* author of the source: David Churchill
**/
package bwmcts.sparcraft.players;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import bwmcts.sparcraft.GameState;
import bwmcts.sparcraft.UnitAction;

public class Player_Random extends Player {
	
	private Random r = new Random();
	
	public Player_Random(int playerID) {
		_id=playerID;
		setID(playerID);
	}

	public void getMoves(GameState  state, HashMap<Integer,List<UnitAction>> moves, List<UnitAction>  moveVec)
	{
		
	    moveVec.clear();
		for (int u=0; u< moves.size(); u++)
		{
			int a = (int) Math.floor((r.nextDouble() * (double)moves.get(u).size()));
			
			moveVec.add(moves.get(u).get(a));
			
		}
	}
	
	public String toString(){
		return "Random";
	}
}
