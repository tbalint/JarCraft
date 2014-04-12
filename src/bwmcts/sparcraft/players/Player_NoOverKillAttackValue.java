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

public class Player_NoOverKillAttackValue extends Player {
	
	private int _id=0;
	private int enemy;
	private int[] hpRemaining = new int[Constants.Max_Units];
	
	public Player_NoOverKillAttackValue(int playerID) {
		_id=playerID;
		setID(playerID);
		enemy=GameState.getEnemy(_id);
	}

	public long timeOnHpCopying=0;
	public void getMoves(GameState  state, HashMap<Integer,List<UnitAction>> moves, List<UnitAction>  moveVec)
	{
		
		moveVec.clear();

		for(int u = 0; u < state.numUnits(enemy); u++){
			
			hpRemaining[u] = state.getUnit(enemy,u)._currentHP;
			
		}
		
		
		//for (int u = 0; u<moves.size(); ++u){
		//boolean foundUnitAction = false;
		//int actionMoveIndex	= 0;
		float actionHighestDPS	= 0;
		//int closestMoveIndex = 0;
		int closestMoveDist	= Integer.MAX_VALUE;
		UnitAction move;
		Unit ourUnit;
		int dist=0;
		float dpsHPValue=0;
		Unit closestUnit ;
		
		int m=0;
		List<UnitAction> movesForU;
		for (Integer u : moves.keySet()){
			closestUnit= null;
			//foundUnitAction=false;
			//actionMoveIndex=0;
			actionHighestDPS=0;
			//closestMoveIndex =0;
			closestMoveDist=Integer.MAX_VALUE;
			ourUnit = state.getUnit(_id, u);
			//Unit ourUnit = state.getUnitByID(u);
			
			if (ourUnit == null || moves.get(u) == null){
				//state.print();
				System.out.println(ourUnit + " " + _id + " " + u);
			}
			movesForU=moves.get(u);
			UnitAction actionMove=null;
			UnitAction passiveMove=null;
			
			for (m=0; m<movesForU.size(); m++)
			{
				//long g=System.nanoTime();
				move	= movesForU.get(m);
				
				//timeOnHpCopying+=System.nanoTime()-g;	
				if ((move._moveType == UnitActionTypes.ATTACK) && (hpRemaining[move.index()] > 0))
				{
					
					dpsHPValue 	= (state.getUnit(enemy, move.index()).dpf / hpRemaining[move.index()]);

					if (dpsHPValue > actionHighestDPS)
					{
						actionHighestDPS = dpsHPValue;
						//actionMoveIndex = m;
						actionMove=move;
						//foundUnitAction = true;
					}

				} 
				else if (move._moveType == UnitActionTypes.HEAL){
					
					dpsHPValue =	(state.getUnit(move.player(), move.index()).dpf / hpRemaining[move.index()]);

					if (dpsHPValue > actionHighestDPS)
					{
						actionHighestDPS = dpsHPValue;
						//actionMoveIndex = m;
						actionMove=move;
						//foundUnitAction = true;
					}
				}
				if (actionMove==null){
				//if (!foundUnitAction){
					if (closestUnit==null){
						
						closestUnit= ourUnit.canHeal() ? state.getClosestOurUnit(_id, u) : state.getClosestEnemyUnit(ourUnit.currentPosition(state._currentTime),enemy,Integer.MAX_VALUE,0,0);
						
					}
					
					if (move._moveType== UnitActionTypes.RELOAD)
					{
						if (ourUnit.canAttackTarget(closestUnit, state._currentTime))
						{
							//closestMoveIndex = m;
							passiveMove=move;
							break;
						}
					}
					else if (move._moveType == UnitActionTypes.MOVE)
					{
						
						dist = closestUnit.getDistanceSqToPosition(ourUnit.pos().getX() + Constants.Move_DirX[move.index()], 
											ourUnit.pos().getY() + Constants.Move_DirY[move.index()], state.getTime());
	
						if (dist < closestMoveDist)
						{
							closestMoveDist = dist;
							//closestMoveIndex = m;
							passiveMove=move;
						}
					}
				}
			}

			if (actionMove!=null){
			//if (foundUnitAction){
				//actionMove = movesForU.get(actionMoveIndex);
				if (actionMove._moveType == UnitActionTypes.ATTACK)
				{
					//hpRemaining[theMove.index()] -= state.getUnit(_id, theMove.unit()).damage();
					hpRemaining[actionMove.index()] -= ourUnit.damage;
				}
				moveVec.add(actionMove);
			} else {
				//moveVec.add(movesForU.get(closestMoveIndex));
				moveVec.add(passiveMove);
			}
			
		}
	}
	
	public String toString(){
		return "NOK-AV";
	}
}
