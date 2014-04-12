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
import bwmcts.uct.UCT;
import bwmcts.uct.flatguctcd.FlatGUCTCD;
import bwmcts.uct.guctcd.GUCTCD;
import bwmcts.uct.rguctcd.RGUCTCD;
import javabot.JNIBWAPI;
import javabot.util.BWColor;

public class UctLogic extends Player implements ICombatLogic {

	private UCT uct;
	//private HashMap<Integer,UnitAction> actions=new HashMap<Integer,UnitAction>();
	private int timeBudget;
	private List<List<Unit>> clusters;
	
	public UctLogic(JNIBWAPI bwapi, UCT uct, int timeBudget){
		
		this.uct = uct;
		
		bwapi.loadTypeData();
		AnimationFrameData.Init();
		PlayerProperties.Init();
		WeaponProperties.Init(bwapi);
		UnitProperties.Init(bwapi);
		this.timeBudget = timeBudget;

	}

	@Override	
	public void act(JNIBWAPI bwapi, int time) {
		
		try{
			GameState state = new GameState(bwapi);
			//state.print();
			List<UnitAction> move=new ArrayList<UnitAction>();
			move = uct.search(state.clone(), timeBudget);
			if (uct instanceof GUCTCD){
				clusters = ((GUCTCD)uct).getClusters();
			}
			System.out.println();
			executeActions(bwapi,state,move);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void drawClusters(JNIBWAPI bwapi, HashMap<Integer, List<Unit>> clusters) {
		
		if (clusters == null)
			return;
		
		for(Integer i : clusters.keySet()){
			
			for (Unit u : clusters.get(i)){
				
				bwapi.drawCircle(u.pos().getX(), u.pos().getY(), 6, getColor(i), false, false);
				
			}
			
		}
		
	}
	

	private int getColor(Integer i) {
		switch (i){
		case 0:
			return BWColor.CYAN;
		case 1:
			return BWColor.GREEN;
		case 2:
			return BWColor.WHITE;
		case 3:
			return BWColor.PURPLE;
		case 4:
			return BWColor.ORANGE;
		case 5:
			return BWColor.YELLOW;
		case 6:
			return BWColor.TEAL;
		case 7:
			return BWColor.RED;
		case 8:
			return BWColor.BLUE;
		default:
			return BWColor.BLACK;
	}
	}


	private HashMap<Integer,UnitAction> firstAttack=new HashMap<Integer,UnitAction>();
	
	private void executeActions(JNIBWAPI bwapi, GameState state, List<UnitAction> moves) {
		if (moves!=null && !moves.isEmpty()){
			for (UnitAction move : moves){
				
				Unit ourUnit		= state.getUnit(move._player, move._unit);
		    	int player		= ourUnit.player();
		    	int enemyPlayer  = GameState.getEnemy(player);
		    	if (firstAttack.get(ourUnit.getId())!=null){
		    		if (bwapi.getUnit(firstAttack.get(ourUnit.getId())._moveIndex) == null){
		    			firstAttack.remove(ourUnit.getId());
		    		}
		    		if (bwapi.getUnit(ourUnit.getId()).isAttackFrame()){
		    			firstAttack.remove(ourUnit.getId());
		    		}
		    		if (bwapi.getUnit(ourUnit.getId()).getLastCommandFrame()+10<bwapi.getFrameCount()){
		    			firstAttack.remove(ourUnit.getId());
		    		}
		    		
		    	}
		    	if (firstAttack.get(ourUnit.getId())!=null){continue;}
		    	
		    	if (bwapi.getUnit(ourUnit.getId()).isAttackFrame()){continue;}
		    	
		    	if (move._moveType == UnitActionTypes.ATTACK && bwapi.getUnit(ourUnit.getId()).getGroundWeaponCooldown()==0)
		    	{
		    		Unit enemyUnit=state.getUnit(enemyPlayer,move._moveIndex);
		    		
		    		bwapi.attack(ourUnit.getId(), enemyUnit.getId());
		    		firstAttack.put(ourUnit.getId(), move.clone());

		    	}
		    	else if (move._moveType == UnitActionTypes.MOVE)
		    	{
		    		bwapi.move(ourUnit.getId(), move.pos().getX(), move.pos().getY());
		    	}
		    	else if (move._moveType == UnitActionTypes.HEAL)
		    	{
		    		Unit ourOtherUnit=state.getUnit(player,move._moveIndex);

		    		bwapi.rightClick(ourUnit.getId(), ourOtherUnit.getId());	

		    	}
		    	else if (move._moveType == UnitActionTypes.RELOAD)
		    	{
		    	}
		    	else if (move._moveType == UnitActionTypes.PASS)
		    	{
		    	}
			}
		} else {
			System.out.println("---------------------NO MOVES----------------------------");
			
		}
	}

	public void getMoves(GameState state, HashMap<Integer,List<UnitAction>> moves, List<UnitAction>  moveVec)
	{
		
		moveVec.clear();
		List<UnitAction> move=new ArrayList<UnitAction>();
		move = uct.search(state.clone(), timeBudget);
		if (uct instanceof GUCTCD){
			clusters = ((GUCTCD)uct).getClusters();
		}
		if (uct instanceof RGUCTCD){
			clusters = ((RGUCTCD)uct).getClusters();
		}
		if (uct instanceof FlatGUCTCD){
			clusters = ((FlatGUCTCD)uct).getClusters();
		}
		/*
		if (guctcd!=null){
			
			try{
				long start = System.currentTimeMillis();
				UPGMA upgmaPlayerA = new UPGMA(state.getAllUnit()[ID()], guctcd.getHpMulitplier(), 1);
				UPGMA upgmaPlayerB = new UPGMA(state.getAllUnit()[state.getEnemy(ID())], guctcd.getHpMulitplier(), 1);
				long end = System.currentTimeMillis();
				//move = guctcd.search(state, upgmaPlayerA, upgmaPlayerB, timeBudget - (end-start));
				move = guctcd.search(state, upgmaPlayerA, upgmaPlayerB, timeBudget);
			} catch (Exception e){
				
			}
		}
		*/
		for(UnitAction action : move)
			moveVec.add(action.clone());
	
	}
	
	public void drawUnitOneInfo(JNIBWAPI bwapi){
		javabot.model.Unit my=bwapi.getUnit(0);
		
		bwapi.drawText(0, 0, "isMoving: "+my.isMoving(), false);
		bwapi.drawText(0, 20, "isattacking: "+my.isAttacking(), false);
		bwapi.drawText(0, 40, "isattackframe: "+my.isAttackFrame(), false);
		bwapi.drawText(0, 60, "isacc: "+my.isAccelerating(), false);
		bwapi.drawText(0, 80, "isIdle: "+my.isIdle(), false);
		bwapi.drawText(0, 100, "isStartingAttack: "+my.isStartingAttack(), false);
	}

	public UCT getUct() {
		return uct;
	}

	public void setUct(UCT uct) {
		this.uct = uct;
	}

	public List<List<Unit>> getClusters() {
		return clusters;
	}

	public void setClusters(List<List<Unit>> clusters) {
		this.clusters = clusters;
	}
	
	public String toString(){
		
		return uct.toString();
	}
	
}
