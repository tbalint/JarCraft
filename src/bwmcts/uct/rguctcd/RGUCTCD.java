/**
* This file is an extension to code based on and translated from the open source project: Sparcraft
* https://code.google.com/p/sparcraft/
* author of the source: David Churchill
**/
package bwmcts.uct.rguctcd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bwmcts.clustering.UPGMA;
import bwmcts.uct.UctNode;
import bwmcts.uct.UnitState;
import bwmcts.uct.UnitStateTypes;
import bwmcts.uct.NodeType;
import bwmcts.sparcraft.EvaluationMethods;
import bwmcts.sparcraft.Game;
import bwmcts.sparcraft.GameState;
import bwmcts.sparcraft.Players;
import bwmcts.sparcraft.StateEvalScore;
import bwmcts.sparcraft.Unit;
import bwmcts.sparcraft.UnitAction;
import bwmcts.sparcraft.players.Player;
import bwmcts.sparcraft.players.Player_AttackClosest;
import bwmcts.sparcraft.players.Player_Defense;
import bwmcts.sparcraft.players.Player_Kite;
import bwmcts.sparcraft.players.Player_KiteDPS;
import bwmcts.sparcraft.players.Player_NoOverKillAttackValue;
import bwmcts.uct.UCT;
import bwmcts.uct.UctConfig;
import bwmcts.uct.UctStats;
import bwmcts.uct.guctcd.ClusteringConfig;
import bwmcts.uct.iuctcd.IuctNode;

public class RGUCTCD extends UCT {

	private ClusteringConfig guctConfig;
	
	private List<List<Unit>> clustersA;
	private List<List<Unit>> clustersB;

	private List<List<Unit>> clusters;
	
	public RGUCTCD(UctConfig uctConfig, ClusteringConfig guctConfig){
		super(uctConfig);
		this.guctConfig = guctConfig;
	}

	public List<UnitAction> search(GameState state, long timeBudget){
		
		//System.out.println("Search called");
		
		if (config.getMaxPlayerIndex() == 0 && state.whoCanMove() == Players.Player_Two){
			return new ArrayList<UnitAction>(); 
		} else if (config.getMaxPlayerIndex() == 1 && state.whoCanMove() == Players.Player_One){
			return new ArrayList<UnitAction>(); 
		}
		
		long start = System.currentTimeMillis();
		long startNs = System.nanoTime();
		
		// Get clusters
		clustersA = guctConfig.getClusterAlg().getClusters(state.getAllUnit()[0], 6, guctConfig.getHpMulitplier());
		clustersB = guctConfig.getClusterAlg().getClusters(state.getAllUnit()[1], 6, guctConfig.getHpMulitplier());
		
		if(config.getMaxPlayerIndex() == 0)
			clusters = clustersA;
		else
			clusters = clustersB;
		
		//System.out.println("Nano time: " + (System.nanoTime() - startNs));
		
		UctNode root = new RGuctNode(null, NodeType.ROOT, new ArrayList<UnitState>(), config.getMaxPlayerIndex(), "ROOT");
		root.setVisits(1);
		if(config.getMaxPlayerIndex() == 0)
			((RGuctNode)root).setClusters(cleanClusters(state, clustersA));
		else
			((RGuctNode)root).setClusters(cleanClusters(state, clustersB));
		
		// Reset stats if new game
		if (state.getTime()==0)
			stats.reset();
		
		int t = 0;
		while(System.currentTimeMillis() <= start + timeBudget){
			
			traverse(root, state.clone());
			t++;
			
		}
		
		stats.getIterations().add(t);
		//System.out.println("GUCTCD: " + t);
		
		//UctNode best = mostVisitedChildOf(root);
		UctNode best = bestValueChildOf(root);
		if (((RGuctNode)best).getAbstractMove().size() != clusters.size()){
			System.out.println("Scripts: " + ((RGuctNode)best).getAbstractMove().size() + ", clusters: " + clusters.size());
		}
		
		if (config.isDebug())
			writeToFile(root.print(0), "tree.xml");
		
		if (best == null)
			return new ArrayList<UnitAction>();
		
		List<UnitAction> actions = best.getMove();
		
		return actions;
		
	}

	private float traverse(UctNode node, GameState state) {
		
		float score = 0;
		int playerToMove = getPlayerToMove(node, state);
		if (node.getVisits() == 0){
			if (((RGuctNode)node).getClusters() == null){
				if (playerToMove == 0)
					((RGuctNode)node).setClusters(cleanClusters(state, clustersA));
				else
					((RGuctNode)node).setClusters(cleanClusters(state, clustersB));
			}
			if (node.getMove() == null)
				node.setMove(statesToActions(((RGuctNode)node).getAbstractMove(), ((RGuctNode)node).getClusters(), state));
			updateState(node, state, true);
			score = evaluate(state.clone());
		} else {
			updateState(node, state, false);
			if (state.isTerminal()){
				score = evaluate(state.clone());
			} else {
				if (expandable(node, playerToMove))
					generateChildren(node, state, playerToMove);
				score = traverse(selectNode(node), state);
			}
		}
		
		if (config.isLTD2()){
			node.setTotalScore(node.getTotalScore() + score);
		} else {
			if (score > 0)
				node.setTotalScore(node.getTotalScore() + 1);
			else if (score == 0)
				node.setTotalScore(node.getTotalScore() + 0.5f);
		}
		
		node.setVisits(node.getVisits() + 1);
		
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			node.setPossibleMoves(map);
			
		}
		
		if (node.getType() == NodeType.ROOT)
			clusters = ((RGuctNode)node).getClusters();
				
		String label = "";
		if (node.getChildren().isEmpty()){
			move.addAll(getAllMove(UnitStateTypes.ATTACK, ((RGuctNode)node).getClusters()));
			label = "NOK-AV";
		} else if (node.getChildren().size() == 1 && playerToMove == config.getMaxPlayerIndex()){
			move.addAll(getAllMove(UnitStateTypes.KITE, ((RGuctNode)node).getClusters()));
			label = "KITE";
		} else if (playerToMove == config.getMaxPlayerIndex()){
			move = getRandomMove(playerToMove, ((RGuctNode)node).getClusters()); // Possible moves?
			label = "RANDOM";
		}
		
		if (move == null)
			return;
	
		if (uniqueMove(move, node)){
			if (label.equals("RANDOM") && move.size() == 1){
				System.out.println("sd");
				boolean a = uniqueMove(move, node);
			}
			RGuctNode child = new RGuctNode((RGuctNode)node, getChildNodeType(node, state), move, playerToMove, label);
			node.getChildren().add(child);
		}
		
	}
	
	private List<List<Unit>> cleanClusters(GameState state, List<List<Unit>> clusters) {
		
		List<List<Unit>> readyClusters = new ArrayList<List<Unit>>();
		
		for(List<Unit> cluster : clusters){
			List<Unit> readyCluster = new ArrayList<Unit>();
			for(Unit unit : cluster){
				if (unit.firstTimeFree() == state.getTime())
					readyCluster.add(unit);
			}
			if (!readyCluster.isEmpty())
				readyClusters.add(readyCluster);
		}
		//System.out.println(clusters.size() + " : " + readyClusters.size());
		return readyClusters;
	}

	private List<UnitState> getAllMove(UnitStateTypes type, List<List<Unit>> clusters) {

		List<UnitState> states = new ArrayList<UnitState>();
		
		int i = 0;
		for(List<Unit> units : clusters){
			
			UnitState state = new UnitState(type, i, units.get(0).player());
			states.add(state);
			i++;
			
		}
		
		return states;
	}

	private boolean uniqueMove(List<UnitState> move, UctNode node) {

		if(node.getChildren().isEmpty())
			return true;
		
		for (UctNode child : node.getChildren()){
			boolean identical = true;
			for(int i = 0; i < move.size(); i++){
				if (((RGuctNode)child).getAbstractMove().get(i).type != move.get(i).type){
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

	private NodeType getChildNodeType(RGuctNode parent, GameState prevState) {
		
		if(!prevState.bothCanMove()){
			
			return NodeType.SOLO;
			
		} else { 
			
			if (parent.getType() == NodeType.ROOT)
		
				return NodeType.FIRST;
			
			if (parent.getType() == NodeType.SOLO)
				
				return NodeType.FIRST;
			
			if (parent.getType() == NodeType.SECOND)
				
				return NodeType.FIRST;
			
			if (parent.getType() == NodeType.FIRST)
				
				return NodeType.SECOND;
		}
			
		return NodeType.DEFAULT;
	}

	private List<UnitState> getRandomMove(int playerToMove, List<List<Unit>> clusters) {
		
		List<UnitState> states = new ArrayList<UnitState>();
		
		int i = 0;
		for(List<Unit> units : clusters){
			
			// Random state
			UnitStateTypes type = UnitStateTypes.ATTACK;
			if (Math.random() >= 0.5f)
				type = UnitStateTypes.KITE;
			
			UnitState state = new UnitState(type, i, units.get(0).player());
			states.add(state);
			i++;
			
		}
		
		return states;
		
	}

	private List<UnitAction> statesToActions(List<UnitState> move, List<List<Unit>> clus, GameState state) {
		
		if (move == null || move.isEmpty() || move.get(0) == null)
			return new ArrayList<UnitAction>();
		
		int player = move.get(0).player;
		
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
			
			if (clus.size() <= unitState.unit){
				break;
			}
			// Add units in cluster
			for(Unit u : clus.get(unitState.unit)){
				
				if (u.isAlive() && (u.canAttackNow() || u.canMoveNow())){
				
					if (unitState.type == UnitStateTypes.ATTACK && u.isAlive())
						attackingUnits.add(u.getId());
					else if (unitState.type == UnitStateTypes.KITE && u.isAlive())
						kitingUnits.add(u.getId());
					
				}
				
			}
			
		}
		
		List<UnitAction> allActions = new ArrayList<UnitAction>();
		HashMap<Integer, List<UnitAction>> attackingMap = new HashMap<Integer, List<UnitAction>>();
		HashMap<Integer, List<UnitAction>> kitingMap = new HashMap<Integer, List<UnitAction>>();
		
		// Loop through the map
		for(Integer i : map.keySet()){
			int u = map.get(i).get(0)._unit;
			int unitId = state.getUnit(player, u).getId();
			if (attackingUnits.contains(unitId))
				attackingMap.put(i, map.get(i)); 
			if (kitingUnits.contains(unitId))
				kitingMap.put(i, map.get(i));
		}
		
		// Add attack actions
		List<UnitAction> attackActions = new ArrayList<UnitAction>();
		attack.getMoves(state, attackingMap, attackActions);
		allActions.addAll(attackActions);
		
		// Add defend actions
		List<UnitAction> defendActions = new ArrayList<UnitAction>();
		kite.getMoves(state, kitingMap, defendActions);
		allActions.addAll(defendActions);
		
		return allActions;
	}

	public List<List<Unit>> getClusters() {
		return clusters;
	}
	
}