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
import bwmcts.sparcraft.Unit;
import bwmcts.sparcraft.UnitAction;
import bwmcts.sparcraft.UnitActionTypes;

public class Player_Kite extends Player {
	
	private int _id=0;
	private int enemy;
	
	public Player_Kite(int playerID) {
		_id=playerID;
		setID(playerID);
		enemy=GameState.getEnemy(_id);
	}

	public void getMoves(GameState  state, HashMap<Integer,List<UnitAction>> moves, List<UnitAction>  moveVec)
	{
		moveVec.clear();
		boolean foundUnitAction					= false;
		int actionMoveIndex						= 0;
		int furthestMoveIndex					= 0;
		int furthestMoveDist					= 0;
		int closestMoveIndex					= 0;
		int actionDistance						= Integer.MAX_VALUE;
		int closestMoveDist		  			= Integer.MAX_VALUE;
		Unit ourUnit, closestUnit=null;
		List<UnitAction> actions;
		int dist=0, bestMoveIndex = 0;
		UnitAction move;
		for (Integer u : moves.keySet())
		{
			foundUnitAction		= false;
			actionMoveIndex		= 0;
			furthestMoveIndex	= 0;
			furthestMoveDist	= 0;
			closestMoveIndex	= 0;
			actionDistance		= Integer.MAX_VALUE;
			closestMoveDist		= Integer.MAX_VALUE;

			ourUnit				= (state.getUnit(_id, u));
			
			actions=moves.get(u);
			for (int m = 0; m <actions.size(); ++m)
			{
				move						= actions.get(m);
					
				if (move.type() == UnitActionTypes.ATTACK)
				{
					dist			= ourUnit.getDistanceSqToUnit(state.getUnit(enemy, move._moveIndex), state.getTime());

					if (dist < actionDistance)
					{
						actionDistance = dist;
						actionMoveIndex = m;
						foundUnitAction = true;
					}
				}
				else if (move.type() == UnitActionTypes.HEAL)
				{

					dist				= ourUnit.getDistanceSqToUnit(state.getUnit(move.player(), move._moveIndex), state.getTime());

					if (dist < actionDistance)
					{
						actionDistance = dist;
						actionMoveIndex = m;
						foundUnitAction = true;
					}
				}
				else if (move.type() == UnitActionTypes.MOVE)
				{
					//Position ourDest = new Position(ourUnit.pos().getX() + Constants.Move_Dir[move._moveIndex][0], 
					//								 ourUnit.pos().getY() + Constants.Move_Dir[move._moveIndex][1]);
					closestUnit			= (ourUnit.canHeal() ? state.getClosestOurUnit(_id, u) : state.getClosestEnemyUnit(ourUnit.currentPosition(state._currentTime),enemy,Integer.MAX_VALUE,0,0));
					dist = closestUnit.getDistanceSqToPosition(ourUnit.pos().getX() + Constants.Move_DirX[move._moveIndex],ourUnit.pos().getY() + Constants.Move_DirY[move._moveIndex], state.getTime());

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
			bestMoveIndex = 0;

			// if we have an attack move we will use that one
			if (foundUnitAction)
			{
				bestMoveIndex = actionMoveIndex;
			}
			// otherwise use the closest move to the opponent
			else
			{
				 //if we are in attack range of the unit, back up
				if (ourUnit.canAttackTarget(closestUnit, state.getTime()))
				{
				bestMoveIndex = furthestMoveIndex;
				}
				//otherwise get back into the fight
				else
				{
					bestMoveIndex = closestMoveIndex;
				}
			}
			
			moveVec.add(actions.get(bestMoveIndex));
		}
	}
	
	public String toString(){
		return "Kite";
	}
}
