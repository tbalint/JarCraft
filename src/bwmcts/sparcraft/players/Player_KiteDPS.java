/**
* This file is based on and translated from the open source project: Sparcraft
* https://code.google.com/p/sparcraft/
* author of the source: David Churchill
**/
package bwmcts.sparcraft.players;

import java.util.HashMap;
import java.util.List;

import bwmcts.sparcraft.Constants;
import bwmcts.sparcraft.GameState;
import bwmcts.sparcraft.Position;
import bwmcts.sparcraft.Unit;
import bwmcts.sparcraft.UnitAction;
import bwmcts.sparcraft.UnitActionTypes;

public class Player_KiteDPS extends Player {
	
	private int _id=0;
	
	public Player_KiteDPS(int playerID) {
		_id=playerID;
		setID(playerID);
	}

	public void getMoves(GameState  state, HashMap<Integer,List<UnitAction>> moves, List<UnitAction>  moveVec)
	{
		moveVec.clear();
		for (Integer u : moves.keySet())
		{
			boolean foundUnitAction					= false;
			int actionMoveIndex						= 0;
			int furthestMoveIndex					= 0;
			int furthestMoveDist					= 0;
			int closestMoveIndex					= 0;
			double actionHighestDPS					= 0;
			long closestMoveDist		  			= Long.MAX_VALUE;

			Unit ourUnit							= (state.getUnit(_id, u));
			Unit closestUnit						= (ourUnit.canHeal() ? state.getClosestOurUnit(_id, u) : state.getClosestEnemyUnit(_id, u));

			for (int m = 0; m < moves.get(u).size(); ++m)
			{
				UnitAction move						= moves.get(u).get(m);
					
				if (move.type() == UnitActionTypes.ATTACK)
				{
					Unit target						= (state.getUnit(GameState.getEnemy(move.player()), move._moveIndex));
					double dpsHPValue				= (target.dpf() / target.currentHP());

					if (dpsHPValue > actionHighestDPS)
					{
						actionHighestDPS = dpsHPValue;
						actionMoveIndex = m;
						foundUnitAction = true;
					}
				}
				else if (move.type() == UnitActionTypes.HEAL)
				{
					Unit target				= (state.getUnit(move.player(), move._moveIndex));
					double dpsHPValue 		= (target.dpf() / target.currentHP());

					if (dpsHPValue > actionHighestDPS)
					{
						actionHighestDPS = dpsHPValue;
						actionMoveIndex = m;
						foundUnitAction = true;
					}
				}
				else if (move.type() == UnitActionTypes.MOVE)
				{
					Position ourDest = new Position(ourUnit.pos().getX() + Constants.Move_Dir[move._moveIndex][0], 
													 ourUnit.pos().getY() + Constants.Move_Dir[move._moveIndex][1]);
					int dist = (closestUnit.getDistanceSqToPosition(ourDest, state.getTime()));

					if (dist > furthestMoveDist)
					{
						furthestMoveDist = dist;
						furthestMoveIndex = m;
					}

					if (dist < closestMoveDist)
					{
						closestMoveDist = dist;
						closestMoveIndex = m;
					}
				}
			}

			// the move we will be returning
			int bestMoveIndex = 0;

			// if we have an attack move we will use that one
			if (foundUnitAction)
			{
				bestMoveIndex = actionMoveIndex;
			}
			// otherwise use the closest move to the opponent
			else
			{
				// if we are in attack range of the unit, back up
				if (ourUnit.canAttackTarget(closestUnit, state.getTime()))
				{
					bestMoveIndex = furthestMoveIndex;
				}
				// otherwise get back into the fight
				else
				{
					bestMoveIndex = closestMoveIndex;
				}
			}
			
			moveVec.add(moves.get(u).get(bestMoveIndex));
		}
	}
	
	public String toString(){
		return "KiteDPS";
	}
}
