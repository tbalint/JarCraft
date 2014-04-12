///**
//* This file is based on and translated from the open source project: Sparcraft
//* https://code.google.com/p/sparcraft/
//* author of the source: David Churchill
//**/
//package bwmcts.combat;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import javabot.JNIBWAPI;
//import bwmcts.clustering.UPGMA;
//import bwmcts.mcts.UnitState;
//import bwmcts.mcts.UnitStateTypes;
//import bwmcts.sparcraft.AnimationFrameData;
//import bwmcts.sparcraft.Constants;
//import bwmcts.sparcraft.EvaluationMethods;
//import bwmcts.sparcraft.Game;
//import bwmcts.sparcraft.GameState;
//import bwmcts.sparcraft.PlayerProperties;
//import bwmcts.sparcraft.StateEvalScore;
//import bwmcts.sparcraft.Timer;
//import bwmcts.sparcraft.Unit;
//import bwmcts.sparcraft.UnitAction;
//import bwmcts.sparcraft.UnitActionTypes;
//import bwmcts.sparcraft.UnitProperties;
//import bwmcts.sparcraft.WeaponProperties;
//import bwmcts.sparcraft.players.Player;
//import bwmcts.sparcraft.players.Player_ClusteredUnitStateToUnitAction;
//import bwmcts.sparcraft.players.Player_Kite;
//import bwmcts.sparcraft.players.Player_NoOverKillAttackValue;
//
//public class GPortfolioGreedyLogic extends Player implements ICombatLogic {
//
//
//	int _iterations;
//    int _responses;
//    int _timeLimit;
//	int _numberOfClusters=0;
//	int _simulationLimit;
//	
//	public GPortfolioGreedyLogic(JNIBWAPI bwapi, int iter, int responses, int simulationLimit, int timeLimit, int numberOfClusters){
//		
//		bwapi.loadTypeData();
//		AnimationFrameData.Init();
//		PlayerProperties.Init();
//		WeaponProperties.Init(bwapi);
//		UnitProperties.Init(bwapi);
//		_iterations=iter;
//		_responses=responses;
//		_timeLimit=timeLimit;
//		_numberOfClusters=numberOfClusters;
//		_simulationLimit=simulationLimit;
//
//	}
//	
//	
//	@Override
//	public void act(JNIBWAPI bwapi, int time) {
//		GameState state = new GameState(bwapi);
//
//		try{
//
//			HashMap<Integer,List<UnitAction>> possibleMoves=new HashMap<Integer, List<UnitAction>>();
//			state.generateMoves(possibleMoves, bwapi.getSelf().getID());
//			
//			executeActions(bwapi,state,search(bwapi.getSelf().getID(), possibleMoves, state.clone()));
//			
//		} catch(Exception e){
//			//e.printStackTrace();
//		}
//	}
//	
//	private HashMap<Integer,UnitAction> firstAttack=new HashMap<Integer,UnitAction>();
//	
//	private void executeActions(JNIBWAPI bwapi, GameState state, List<UnitAction> moves) {
//		if (moves!=null && !moves.isEmpty()){
//			for (UnitAction move : moves){
//				
//				Unit ourUnit		= state.getUnit(move._player, move._unit);
//		    	int player		= ourUnit.player();
//		    	int enemyPlayer  = state.getEnemy(player);
//		    	if (firstAttack.get(ourUnit.getId())!=null){
//		    		if (!bwapi.getUnit(firstAttack.get(ourUnit.getId())._moveIndex).isExists()){
//		    			firstAttack.remove(ourUnit.getId());
//		    		}
//		    		if (bwapi.getUnit(ourUnit.getId()).isAttackFrame()){
//		    			firstAttack.remove(ourUnit.getId());
//		    		}
//		    		if (bwapi.getUnit(ourUnit.getId()).getLastCommandFrame()+10<bwapi.getFrameCount()){
//		    			firstAttack.remove(ourUnit.getId());
//		    		}
//		    		
//		    	}
//		    	if (firstAttack.get(ourUnit.getId())!=null){continue;}
//		    	
//		    	if (bwapi.getUnit(ourUnit.getId()).isAttackFrame()){continue;}
//		    	
//		    	if (move._moveType == UnitActionTypes.ATTACK && bwapi.getUnit(ourUnit.getId()).getGroundWeaponCooldown()==0)
//		    	{
//		    		Unit enemyUnit=state.getUnit(enemyPlayer,move._moveIndex);
//		    		
//		    		bwapi.attack(ourUnit.getId(), enemyUnit.getId());
//		    		firstAttack.put(ourUnit.getId(), move.clone());
//
//		    	}
//		    	else if (move._moveType == UnitActionTypes.MOVE)
//		    	{
//		    		bwapi.move(ourUnit.getId(), move.pos().getX(), move.pos().getY());
//		    	}
//		    	else if (move._moveType == UnitActionTypes.HEAL)
//		    	{
//		    		Unit ourOtherUnit=state.getUnit(player,move._moveIndex);
//
//		    		bwapi.rightClick(ourUnit.getId(), ourOtherUnit.getId());	
//
//		    	}
//		    	else if (move._moveType == UnitActionTypes.RELOAD)
//		    	{
//		    	}
//		    	else if (move._moveType == UnitActionTypes.PASS)
//		    	{
//		    	}
//			}
//		} else {
//			System.out.println("---------------------NO MOVES----------------------------");
//			
//		}
//	}
//	
//	public void getMoves(GameState state, HashMap<Integer,List<UnitAction>> moves, List<UnitAction>  moveVec)
//	{
//		
//		GameState clone = state.clone();
//		moveVec.clear();
//		long s=System.currentTimeMillis();
//		moveVec.addAll(search(ID(),moves,clone));
//		//System.out.println(System.currentTimeMillis()-s);
//		//System.out.println("          --------------------------------      "+moveVec.size());
//
//	}
//	
//	
//	Timer time=new Timer();
//	int _totalEvals=0;
//	UnitStateTypes _enemyScript=UnitStateTypes.ATTACK;
//	UnitStateTypes[] _playerScriptPortfolio=new UnitStateTypes[]{UnitStateTypes.ATTACK, UnitStateTypes.KITE};
//	UPGMA clustersPlayer;
//	UPGMA clustersEnemy;
//	
//	private List<UnitAction> search(int player,HashMap<Integer,List<UnitAction>> moves, GameState state)
//	{
//		time=new Timer();
//		time.start();
//	    int enemyPlayer=state.getEnemy(player);
//	    clustersPlayer=new UPGMA(state.getAllUnit()[player],1,1);
//	    clustersEnemy=new UPGMA(state.getAllUnit()[enemyPlayer],1,1);
//	    // calculate the seed scripts for each player
//	    // they will be used to seed the initial root search
//	    UnitStateTypes seedScript = calculateInitialSeed(player, state);
//	    //UnitStateTypes enemySeedScript = calculateInitialSeed(enemyPlayer, state);
//	
//	    // set up the root script data
//	    HashMap<Integer,UnitStateTypes> originalScriptDataA = new HashMap<Integer,UnitStateTypes>();
//	    HashMap<Integer,UnitStateTypes> originalScriptDataB = new HashMap<Integer,UnitStateTypes>();
//	    setAllScripts(player, state, originalScriptDataA, seedScript);
//	    setAllScripts(enemyPlayer, state, originalScriptDataB, _enemyScript);
//	    _totalEvals = 0;
//	    //printf("\nFirst Part %lf ms\n", ms);
//	
//	    // do the initial root portfolio search for our player
//	    doPortfolioSearch(player, state, originalScriptDataA,originalScriptDataB);
//	
//	    // iterate as many times as required
//	    for (int i=0; i<_responses; ++i)
//	    {
//	        // do the portfolio search to improve the enemy's scripts
//	        //doPortfolioSearch(enemyPlayer, state.clone(), originalScriptDataB,originalScriptDataA);
//	
//	        // then do portfolio search again for us to improve vs. enemy's update
//	        doPortfolioSearch(player, state, originalScriptDataA,originalScriptDataB);
//	    }
//	
//	    // convert the script vector into a move vector and return it
//	    boolean set = false;
//	    for(UnitStateTypes t : originalScriptDataA.values()){
//	    	if(t != null)
//	    		set = true;
//	    }
//	
//	    if (!set){
//	    	 for(Integer i : originalScriptDataA.keySet()){
//	    		 originalScriptDataA.put(i, UnitStateTypes.ATTACK);
//	    	 }
//	    }
//	    	
//	    //System.out.println(_totalEvals);
//	    Player_ClusteredUnitStateToUnitAction playerA =new Player_ClusteredUnitStateToUnitAction(player);
//	    playerA.setScripts(originalScriptDataA);
//	    playerA.setID(player);
//	    //System.out.println(originalScriptDataA);
//	    playerA.setClusters(clustersPlayer.getClusters(_numberOfClusters));
//	    List<UnitAction> list=new ArrayList<UnitAction>();
//	    playerA.getMoves(state, moves, list);
//	    return list;
//	}
//	
//	private void doPortfolioSearch(int player, GameState state, HashMap<Integer,UnitStateTypes> currentScriptDataA, HashMap<Integer,UnitStateTypes> currentScriptDataB)
//	{
//
//	    // the enemy of this player
//		
//	    for (int i=0; i<_iterations; ++i)
//	    {
//	        // set up data for best scripts
//	    	UnitStateTypes[]  bestScriptVec=new UnitStateTypes[Constants.Max_Units];
//		    StateEvalScore[]  bestScoreVec=new StateEvalScore[Constants.Max_Units];
//	        // for each unit that can move
//	        for (int unitIndex=0; unitIndex<_numberOfClusters; ++unitIndex)
//	        {
//
//	            // iterate over each script move that it can execute
//	            for (int sIndex=0; sIndex<_playerScriptPortfolio.length; ++sIndex)
//	            {
//	            	
//	            	if (_timeLimit > 0 && time.getElapsedTimeInMilliSec() > _timeLimit)
//	 	            {
//	 	                break;
//	 	            }
//	                // set the current script for this unit
//	                currentScriptDataA.put(unitIndex,_playerScriptPortfolio[sIndex]);
//	
//	                // evaluate the current state given a playout with these unit scripts
//	                StateEvalScore score = eval(player, state.clone(), currentScriptDataA, currentScriptDataB);
//	
//	                // if we have a better score, set it
//	                if (sIndex == 0 || score._val > bestScoreVec[unitIndex]._val)
//	                {
//	                    bestScriptVec[unitIndex] = _playerScriptPortfolio[sIndex];
//	                    bestScoreVec[unitIndex]  = score;
//	                }
//	            }
//	            if (_timeLimit > 0 && time.getElapsedTimeInMilliSec() > _timeLimit)
// 	            {
// 	                break;
// 	            }
//	
//	            // set the current vector to the best move for use in future simulations
//	            currentScriptDataA.put(unitIndex,bestScriptVec[unitIndex]);
//	        }
//	    }   
//	}
//	
//	private UnitStateTypes calculateInitialSeed(int player, GameState state)
//	{
//		UnitStateTypes bestScript=null;
//	    StateEvalScore bestScriptScore=new StateEvalScore(0, 0);
//
//	    // try each script in the portfolio for each unit as an initial seed
//	    for (int sIndex=0; sIndex<_playerScriptPortfolio.length; ++sIndex)
//	    {
//	    	
//	        // evaluate the current state given a playout with these unit scripts
//	        StateEvalScore score = eval(player, state.clone(), _playerScriptPortfolio[sIndex],_enemyScript);
//	
//	        if (sIndex == 0 || score._val > bestScriptScore._val)
//	        {
//	            bestScriptScore = score;
//	            bestScript = _playerScriptPortfolio[sIndex];
//	        }
//	    }
//	
//	    return bestScript;
//	}
//	
//	private StateEvalScore eval(int player, GameState state, UnitStateTypes playerScript, UnitStateTypes enemyScript){
//		Player playerA;
//		Player playerB;
//		int enemyPlayer=GameState.getEnemy(player);
//		if (playerScript == UnitStateTypes.ATTACK){
//			playerA=new Player_NoOverKillAttackValue(player);
//		} else {
//			playerA=new Player_Kite(player);
//		}
//		if (enemyScript==UnitStateTypes.ATTACK){
//			playerB=new Player_NoOverKillAttackValue(enemyPlayer);
//		} else {
//			playerB=new Player_Kite(enemyPlayer);
//		}
//		
//		
//		Game g=new Game(state, playerA, playerB, 100, false);
//	    if (player==1){
//	    	 g=new Game(state, playerB,playerA, 100, false);
//	    }
//
//	    g.play();
//	
//	    _totalEvals++;
//	    
//		return g.getState().eval(player, EvaluationMethods.LTD2);
//	}
//	
//	
//	private StateEvalScore eval(int player, GameState state, HashMap<Integer,UnitStateTypes> playerScriptsChosen, HashMap<Integer,UnitStateTypes> enemyScriptsChosen)
//	{
//	    int enemyPlayer=state.getEnemy(player);
//	
//	    Player_ClusteredUnitStateToUnitAction playerA =new Player_ClusteredUnitStateToUnitAction(player);
//	    Player_ClusteredUnitStateToUnitAction playerB =new Player_ClusteredUnitStateToUnitAction(enemyPlayer);
//	    playerA.setScripts(playerScriptsChosen);
//	    playerB.setScripts(enemyScriptsChosen);
//	    playerA.setID(player);
//	    playerB.setID(enemyPlayer);
//	    if (ID()==player){
//	    	playerA.setClusters(clustersPlayer.getClusters(_numberOfClusters));
//	    	playerB.setClusters(clustersEnemy.getClusters(_numberOfClusters));
//	    } else {
//	    	playerB.setClusters(clustersPlayer.getClusters(_numberOfClusters));
//	    	playerA.setClusters(clustersEnemy.getClusters(_numberOfClusters));
//	    }
//	    Game g=new Game(state, playerA, playerB, _simulationLimit, false);
//	    if (player==1){
//	    	 g=new Game(state, playerB,playerA, _simulationLimit, false);
//	    }
//
//	    g.play();
//	
//	    _totalEvals++;
//	    
//		return g.getState().eval(player, EvaluationMethods.LTD2);
//	}
//	
//	private void  setAllScripts(int player, GameState state, HashMap<Integer,UnitStateTypes> data, UnitStateTypes script)
//	{
//	    for (int c=0; c < _numberOfClusters; c++)
//	    {
//	    	data.put(c,script);
//	    }
//	}
//	
//
//}
//
