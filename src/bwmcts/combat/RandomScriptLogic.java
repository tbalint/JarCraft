package bwmcts.combat;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import bwmcts.sparcraft.AnimationFrameData;
import bwmcts.sparcraft.GameState;
import bwmcts.sparcraft.PlayerProperties;
import bwmcts.sparcraft.Unit;
import bwmcts.sparcraft.UnitAction;
import bwmcts.sparcraft.UnitActionTypes;
import bwmcts.sparcraft.UnitProperties;
import bwmcts.sparcraft.WeaponProperties;
import bwmcts.sparcraft.players.Player;
import bwmcts.sparcraft.players.Player_Kite;
import bwmcts.sparcraft.players.Player_NoOverKillAttackValue;
import bwmcts.uct.UCT;
import bwmcts.uct.UnitState;
import bwmcts.uct.UnitStateTypes;
import bwmcts.uct.flatguctcd.FlatGUCTCD;
import bwmcts.uct.guctcd.GUCTCD;
import javabot.JNIBWAPI;
import javabot.util.BWColor;

public class RandomScriptLogic extends Player implements ICombatLogic {
	
	public RandomScriptLogic(int id){
		this._id = id;
	}
	
	@Override
	public void getMoves(GameState state, HashMap<Integer,List<UnitAction>> moves, List<UnitAction> moveVec){
		
		List<UnitState> states = getRandomStates(state);
		List<UnitAction> move = statesToActions(states, state);
		
		for(UnitAction action : move){
			if (action == null){
				System.out.println("hmm");
			} else {
				moveVec.add(action.clone());
			}
		}
		
	}
	
	private List<UnitState> getRandomStates(GameState state) {
		
		HashMap<Integer, List<UnitAction>> map;

		map = new HashMap<Integer, List<UnitAction>>();
		try {
			state.generateMoves(map, this._id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return getRandomMove(this._id, map);
	}
	
	private List<UnitState> getRandomMove(int playerToMove, HashMap<Integer, List<UnitAction>> map) {
		
		ArrayList<UnitState> move = new ArrayList<UnitState>();
		
		for(Integer i : map.keySet()){
			
			// Skip empty actions
			List<UnitAction> actions = map.get(i);
			if (actions.isEmpty())
				continue;
			
			// Random state
			UnitStateTypes type = UnitStateTypes.ATTACK;
			if (Math.random() >= 0.5f)
				type = UnitStateTypes.KITE;
			
			UnitState unitState = new UnitState(type, i, playerToMove);
			
			// Add random possible action
			move.add(unitState);
			
		}
		
		return move;
		
	}

	private List<UnitAction> statesToActions(List<UnitState> move, GameState state) {
		
		int player = 0;
		
		if (move == null || move.isEmpty() || move.get(0) == null)
			return new ArrayList<UnitAction>();
		else
			player = move.get(0).player;
		
		Player attack = new Player_NoOverKillAttackValue(player);
		Player kite = new Player_Kite(player);
		
		HashMap<Integer, List<UnitAction>> map = new HashMap<Integer, List<UnitAction>>();
		
		try {
			state.generateMoves(map, player);
		} catch (Exception e) {e.printStackTrace();}
		
		List<Integer> attackingUnits = new ArrayList<Integer>();
		List<Integer> kitingUnits = new ArrayList<Integer>();
		
		// Divide units into two groups
		for(UnitState unitState : move){
			
			if (unitState.type == UnitStateTypes.ATTACK)
				attackingUnits.add(unitState.unit);
			else if (unitState.type == UnitStateTypes.KITE)
				kitingUnits.add(unitState.unit);
			
		}
		
		List<UnitAction> allActions = new ArrayList<UnitAction>();
		HashMap<Integer, List<UnitAction>> attackingMap = new HashMap<Integer, List<UnitAction>>();
		HashMap<Integer, List<UnitAction>> kitingMap = new HashMap<Integer, List<UnitAction>>();

		for(Integer i : attackingUnits)
			if (map.get(i) != null)
				attackingMap.put(i, map.get(i));
			
		
		for(Integer i : kitingUnits)
			if (map.get(i) != null)
				kitingMap.put(i, map.get(i));
		
		// Add attack actions
		List<UnitAction> attackActions = new ArrayList<UnitAction>();
		attack.getMoves(state, attackingMap, attackActions);
		allActions.addAll(attackActions);
		
		// Add kite actions
		List<UnitAction> kiteActions = new ArrayList<UnitAction>();
		kite.getMoves(state, kitingMap, kiteActions);
		allActions.addAll(kiteActions);
		
		return allActions;
	}

	@Override
	public void act(JNIBWAPI bwapi, int time) {
		// TODO Auto-generated method stub
		
	}

	
}
