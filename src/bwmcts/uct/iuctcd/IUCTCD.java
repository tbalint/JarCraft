package bwmcts.uct.iuctcd;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import bwmcts.uct.UnitState;
import bwmcts.uct.NodeType;
import bwmcts.uct.UnitStateTypes;
import bwmcts.uct.iuctcd.IuctNode;
import bwmcts.uct.rguctcd.RGuctNode;
import bwmcts.sparcraft.GameState;
import bwmcts.sparcraft.Players;
import bwmcts.sparcraft.UnitAction;
import bwmcts.sparcraft.players.Player;
import bwmcts.sparcraft.players.Player_Kite;
import bwmcts.sparcraft.players.Player_NoOverKillAttackValue;
import bwmcts.sparcraft.players.Player_Pass;
import bwmcts.uct.UCT;
import bwmcts.uct.UctConfig;
import bwmcts.uct.UctNode;
import bwmcts.uct.UctStats;

public class IUCTCD extends UCT {
	
	public IUCTCD(UctConfig config) {
		super(config);
	}
	
	@Override
	public List<UnitAction> search(GameState state, long timeBudget){
		
		if (config.getMaxPlayerIndex() == 0 && state.whoCanMove() == Players.Player_Two){
			return new ArrayList<UnitAction>(); 
		} else if (config.getMaxPlayerIndex() == 1 && state.whoCanMove() == Players.Player_One){
			return new ArrayList<UnitAction>(); 
		}
		
		Date start = new Date();
		
		UctNode root = new IuctNode(null, NodeType.ROOT, new ArrayList<UnitState>(), config.getMaxPlayerIndex(), "ROOT");
		root.setVisits(1);
		
		// Reset stats if new game
		if (state.getTime()==0)
			stats.reset();
		
		int t = 0;
		while(new Date().getTime() <= start.getTime() + timeBudget){
			
			traverse(root, state.clone());
			t++;
			
		}
		
		stats.getIterations().add(t);
		//System.out.println("IUCTCD: " + t);
		
		//UctNode best = mostWinningChildOf(root);
		//UctNode best = mostVisitedChildOf(root);
		UctNode best = bestValueChildOf(root);
		//System.out.println(((IuctNode)best).getAbstractMove().size());
		
		if (best == null){
			System.out.println("IUCTCD: NULL MOVE!");
			return new ArrayList<UnitAction>();
		}
		
		if (config.isDebug())
			writeToFile(root.print(0), "tree.xml");
		
		List<UnitAction> actions = statesToActions(((IuctNode)best).getAbstractMove(), state.clone());

		return actions;
		
	}

	private float traverse(UctNode node, GameState state) {
		
		float score = 0;
		if (node.getVisits() == 0){
			node.setMove(statesToActions(((IuctNode)node).getAbstractMove(), state));
			updateState(node, state, true);
			score = evaluate(state.clone());
		} else {
			updateState(node, state, false);
			if (state.isTerminal()){
				score = evaluate(state.clone());
			} else {
				int playerToMove = getPlayerToMove(node, state);
				if (expandable(node, playerToMove))
					generateChildren(node, state, playerToMove);
				score = traverse(selectNode(node), state);
			}
		}
		node.setVisits(node.getVisits() + 1);
		node.setTotalScore(node.getTotalScore() + score);
		return score;
	}

	private boolean expandable(UctNode node, int playerToMove) {
		
		boolean us = playerToMove == config.getMaxPlayerIndex();
		if (!us && config.isNokModelling() && !node.getChildren().isEmpty())
			return false;
		
		if (node.getVisits() > config.getMaxChildren())
			return false;

		return true;
	}

	private void generateChildren(UctNode node, GameState state, int playerToMove) {
		
		List<UnitState> move = new ArrayList<UnitState>();
		
		HashMap<Integer, List<UnitAction>> map;
		if (node.getPossibleMoves() == null){

			map = new HashMap<Integer, List<UnitAction>>();
			try {
				state.generateMoves(map, playerToMove);
			} catch (Exception e) {
				e.printStackTrace();
			}
			node.setPossibleMoves(map);
			
		}
		
		boolean onlyNok = config.isNokModelling() && playerToMove != config.getMaxPlayerIndex();
		
		String label = "";
		if (node.getChildren().isEmpty()){
			move.addAll(getAllMove(UnitStateTypes.ATTACK, node.getPossibleMoves()));
			label = "NOK-AV";
		} else if (!onlyNok && node.getChildren().size() == 1){
			move.addAll(getAllMove(UnitStateTypes.KITE, node.getPossibleMoves()));
			label = "KITE";
		} else if (!onlyNok){
			move = getRandomMove(playerToMove, node.getPossibleMoves()); // Possible moves?
			label = "RANDOM";
		}
			
		if (move == null)
			return;
	
		if (uniqueMove(move, (IuctNode)node)){
			IuctNode child = new IuctNode((IuctNode)node, getChildNodeType(node, state), move, playerToMove, label);
			node.getChildren().add(child);
		}
		
	}
	
	private boolean uniqueMove(List<UnitState> move, UctNode node) {

		if(node.getChildren().isEmpty())
			return true;
		
		for (UctNode child : node.getChildren()){
			boolean identical = true;
			for(int i = 0; i < move.size(); i++){
				if (((IuctNode)child).getAbstractMove().get(i).type != move.get(i).type){
					identical = false;
					break;
				}
			}
			if (identical){
				return false;
			}
		}
		
		return true;
		
	}

	private List<UnitState> getAllMove(UnitStateTypes type, HashMap<Integer, List<UnitAction>> map) {

		List<UnitState> states = new ArrayList<UnitState>();
		
		for(Integer i : map.keySet()){
			
			List<UnitAction> actions = map.get(i);
			if (actions.isEmpty())
				continue;
			
			UnitState state = new UnitState(type, actions.get(0)._unit, actions.get(0)._player);
			states.add(state);
			
		}
		
		return states;
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
	
	public String toString(){
		return "IUCTCD - "+this.config.toString();
	}
	
}