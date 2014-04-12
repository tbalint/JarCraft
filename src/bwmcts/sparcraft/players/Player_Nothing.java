/**
* This file is based on and translated from the open source project: Sparcraft
* https://code.google.com/p/sparcraft/
* author of the source: David Churchill
**/
package bwmcts.sparcraft.players;

import java.util.HashMap;
import java.util.List;

import bwmcts.sparcraft.GameState;
import bwmcts.sparcraft.Unit;
import bwmcts.sparcraft.UnitAction;
import bwmcts.sparcraft.UnitActionTypes;

public class Player_Nothing extends Player {
	
	private int _id=0;
	
	public Player_Nothing(int playerID) {
		_id=playerID;
		setID(playerID);
	}

	public void getMoves(GameState  state, HashMap<Integer,List<UnitAction>> moves, List<UnitAction>  moveVec)
	{
		
	    moveVec.clear();
		for (int u=0; u< moves.size(); u++)
		{
			
			Unit ourUnit = state.getUnit(_id, u);
			if (ourUnit == null){
				continue;
			}
			moveVec.add(new UnitAction(u, _id, UnitActionTypes.PASS, 0,ourUnit.pos()));
			
		}
		
	}
	
	public String toString(){
		return "No_AI";
	}
}
