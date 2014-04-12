/**
* This file is based on and translated from the open source project: Sparcraft
* https://code.google.com/p/sparcraft/
* author of the source: David Churchill
**/
package bwmcts.uct.uctcd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import bwmcts.uct.NodeType;
import bwmcts.sparcraft.GameState;
import bwmcts.sparcraft.Players;
import bwmcts.sparcraft.UnitAction;
import bwmcts.sparcraft.UnitActionTypes;
import bwmcts.sparcraft.players.Player_Kite;
import bwmcts.sparcraft.players.Player_NoOverKillAttackValue;
import bwmcts.uct.UCT;
import bwmcts.uct.UctConfig;
import bwmcts.uct.UctNode;
import bwmcts.uct.UctStats;

public class UCTCD extends UCT {
	
	private static final String name = "UCTCD";
	
	private HashMap<Integer, Integer> currentMoves;

	public UCTCD(UctConfig config) {
		super(config);
	}

	@Override
	public List<UnitAction> search(GameState state, long timeBudget){
		
		// Start timer
		long timer = System.currentTimeMillis();
		
		// Only search for moves if this players turn
		if (config.getMaxPlayerIndex() == 0 && state.whoCanMove() == Players.Player_Two){
			System.out.println("Exit without computing");
			return new ArrayList<UnitAction>(); 
		} else if (config.getMaxPlayerIndex() == 1 && state.whoCanMove() == Players.Player_One){
			System.out.println("Exit without computing");
			return new ArrayList<UnitAction>();
		}
		
		// Create root node
		UctNode root = new UctNode(null, NodeType.ROOT, new ArrayList<UnitAction>(), config.getMaxPlayerIndex(), "ROOT");
		root.setVisits(1);
		
		// Reset stats if new game
		if (state.getTime()==0)
			stats.reset();
		
		// Search
		int t=0;
		while(new Date().getTime() <= timer + timeBudget){
			
			traverse(root, state.clone());
			t++;
			
		}
		
		stats.getIterations().add(t);
		
		// Select best action
		//UctNode best = mostVisitedChildOf(root);
		UctNode best = bestValueChildOf(root);
		
		if (best == null){
			System.out.println("UCTCD: NULL MOVE!");
			return new ArrayList<UnitAction>();
		}
		
		if (stats.getSelectedActions().containsKey(best.getLabel()))
			stats.getSelectedActions().put(best.getLabel(), stats.getSelectedActions().get(best.getLabel()));
		else
			stats.getSelectedActions().put(best.getLabel(), 1);
		
		if (config.isDebug())
			writeToFile(root.print(0), "tree.xml");
		
		return best.getMove();
		
	}
	
	private float traverse(UctNode node, GameState state) {
		
		float score = 0f;
		if (node.getVisits() == 0){
			updateState(node, state, true);
			score = evaluate(state.clone());
		} else {
			updateState(node, state, false);
			if (state.isTerminal()){
				score = evaluate(state.clone());
			} else {
				if (node.getChildren().isEmpty())
					generateChildren(node, state);
				score = traverse(selectNode(node), state);
			}
		}
		node.setVisits(node.getVisits() + 1);
		
		if (config.isLTD2()){
			node.setTotalScore(node.getTotalScore() + score);
		} else {
			if (score > 0)
				node.setTotalScore(node.getTotalScore() + 1);
			else if (score == 0)
				node.setTotalScore(node.getTotalScore() + 0.5f);
		}

		return score;
	}

	private void generateChildren(UctNode node, GameState state) {
		
		// Figure out who is next to move
		int playerToMove = getPlayerToMove(node, state);
		
		// Generate possible moves
		HashMap<Integer, List<UnitAction>> map = new HashMap<Integer, List<UnitAction>>();
		currentMoves=new HashMap<Integer, Integer>();
		try {
			state.generateMoves(map, playerToMove);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Move ordering
		shuffleMoveOrders(map);
		
		NodeType childType = getChildNodeType(node, state);
		
		// Add script moves
		List<UnitAction> moveNok = new ArrayList<UnitAction>();
		new Player_NoOverKillAttackValue(playerToMove).getMoves(state, map, moveNok);
		UctNode childNok = new UctNode(node, childType, moveNok, playerToMove, "NOK-AV");
		node.getChildren().add(childNok);
		
		// If modelling NOK-AV skip rest of moves if enemy turn to move
		if (config.isNokModelling() && playerToMove != config.getMaxPlayerIndex())
			return;
		
		List<UnitAction> moveKite = new ArrayList<UnitAction>();
		new Player_Kite(playerToMove).getMoves(state, map, moveKite);
		UctNode childKite = new UctNode(node, childType, moveKite, playerToMove, "KITE");
		node.getChildren().add(childKite);
		
		// Add random moves
		while(node.getChildren().size() < config.getMaxChildren()){
			List<UnitAction> moveRandom = new ArrayList<UnitAction>();
			moveRandom = getNextMove(playerToMove, state, map); // Possible moves?
			UctNode childRandom = new UctNode(node, childType, moveRandom, playerToMove, "RANDOM");
			node.getChildren().add(childRandom);
		}
		
	}

	private void shuffleMoveOrders(HashMap<Integer, List<UnitAction>> map) {
		
		// Foreach unit
		for(Integer u : map.keySet()){
			
			int moveEnd = -1;
	        int moveBegin = -1;
	        
	        int numMoves = map.get(u).size();

	        // reverse through the list of actions for this unit
	        for(int a = numMoves-1; a >= 0; --a){
				
	        	UnitActionTypes moveType = map.get(u).get(a)._moveType;
	        	
	            // mark the end of the move actions
	            if (moveEnd == -1 && (moveType == UnitActionTypes.MOVE))
	            {
	                moveEnd = a;
	            }
	            // mark the beginning of the MOVE unit actions
	            else if ((moveEnd != -1) && (moveBegin == -1) && (moveType != UnitActionTypes.MOVE))
	            {
	                moveBegin = a;
	            }
	            else if (moveBegin != -1)
	            {
	                break;
	            }
	        	
			}
	        
	     	// if we found the end but didn't find the beginning
	        if (moveEnd != -1 && moveBegin == -1)
	        {
	            // then the move actions begin at the beginning of the array
	            moveBegin = 0;
	        }

	        // shuffle the movement actions for this unit
	        if (moveEnd != -1 && moveBegin != -1 && moveEnd != moveBegin)
	        {
	        	List<UnitAction> moveActions = map.get(u).subList(moveBegin+1, moveEnd+1);
	        	
	        	Collections.shuffle(moveActions);
	        }
			
		}
		
	}
	
	private List<UnitAction> getNextMove(int playerToMove, GameState state, HashMap<Integer, List<UnitAction>> map) {
		
		ArrayList<UnitAction> move = new ArrayList<UnitAction>();
		
		for(Integer i: map.keySet()){
			if (currentMoves.containsKey(i)){
				int a=(currentMoves.get(i)+1) % map.get(i).size(); 
				currentMoves.put(i, a);
				move.add(map.get(i).get(a));
			} else {
				currentMoves.put(i, 0);
				move.add(map.get(i).get(0));
			}
			
		}
		
		return move;
		
	}
	
}