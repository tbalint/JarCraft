package bwmcts.sparcraft.players;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bwmcts.uct.UnitStateTypes;
import bwmcts.sparcraft.GameState;
import bwmcts.sparcraft.Unit;
import bwmcts.sparcraft.UnitAction;

public class Player_ClusteredUnitStateToUnitAction extends Player {
	
	HashMap<Integer,List<Unit>> _clusters;
	HashMap<Integer,UnitStateTypes> _scripts;
	int id=0;
	List<UnitAction> defendActions = new ArrayList<UnitAction>();
	List<UnitAction> attackActions = new ArrayList<UnitAction>();
	Player attack,kite;

	List<Integer> attackingUnits = new ArrayList<Integer>();
	List<Integer> kitingUnits = new ArrayList<Integer>();
	HashMap<Integer, List<UnitAction>> attackingMap = new HashMap<Integer, List<UnitAction>>();
	HashMap<Integer, List<UnitAction>> kitingMap = new HashMap<Integer, List<UnitAction>>();
	
	
	public Player_ClusteredUnitStateToUnitAction(int id){
		this.id=id;
		setID(id);
		attack = new Player_NoOverKillAttackValue(id);
		kite = new Player_Kite(id);
	}
	
	public void getMoves(GameState  state, HashMap<Integer,List<UnitAction>> moves, List<UnitAction>  moveVec)
	{
		if (_scripts == null || _scripts.isEmpty())
			return;
		
		
		//int player = id;
		
		defendActions.clear();
		attackActions.clear();
		attackingUnits.clear();
		kitingUnits.clear();
		attackingMap.clear();
		kitingMap.clear();
		
		
		// Divide units into two groups
		if (_clusters==null){
			for (int unitIndex=0; unitIndex<state.numUnits(id); ++unitIndex)
	        {
	
	            Unit unit=state.getUnit(id, unitIndex);
	            if (unit==null || _scripts.get(unit.getId())==null){break;}
	            if (_scripts.get(unit.getId()) == UnitStateTypes.ATTACK && unit.isAlive())
					attackingUnits.add(unit.getId());
				else if (_scripts.get(unit.getId()) == UnitStateTypes.KITE && unit.isAlive())
					kitingUnits.add(unit.getId());
	        }
			
			
		} else {
			int i=0;
			UnitStateTypes type;
			for(List<Unit> units : _clusters.values()){
				type=_scripts.get(i);
				if (type!=null){
					for (Unit u:units){
						if (type == UnitStateTypes.ATTACK && u.isAlive())
							attackingUnits.add(u.getId());
						else if (type == UnitStateTypes.KITE && u.isAlive())
							kitingUnits.add(u.getId());
					}
				}
				
				i++;
				// Which cluster?
				
				
				// Add units in cluster
				
				/*	
				for(Unit u : _clusters.get(unitState.unit)){
					
					if (unitState.type == UnitStateTypes.ATTACK && u.isAlive())
						attackingUnits.add(u.getId());
					else if (unitState.type == UnitStateTypes.KITE && u.isAlive())
						kitingUnits.add(u.getId());
					
				}*/
				
			}
		}
		//List<UnitAction> allActions = new ArrayList<UnitAction>();
		
		
		// TODO: Loop through the map instead
		for(Integer i : moves.keySet()){
			int u = moves.get(i).get(0)._unit;
			int unitId = state.getUnit(id, u).getId();
			if (attackingUnits.contains(unitId))
				attackingMap.put(i, moves.get(i)); 
			if (kitingUnits.contains(unitId))
				kitingMap.put(i, moves.get(i));
		}
		
		// Add attack actions
		
		attack.getMoves(state, attackingMap, attackActions);
		moveVec.addAll(attackActions);
		
		// Add defend actions
		
		kite.getMoves(state, kitingMap, defendActions);
		moveVec.addAll(defendActions);
		
		
		
	}
	
	public void setClusters(HashMap<Integer,List<Unit>> clusters){
		_clusters=clusters;
	}
	
	public void setScripts(HashMap<Integer,UnitStateTypes> scripts){
		_scripts=scripts;
	}
	
	public String toString(){
		return "ClusteredUnits";
	}
}
