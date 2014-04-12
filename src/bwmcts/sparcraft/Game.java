/**
* This file is based on and translated from the open source project: Sparcraft
* https://code.google.com/p/sparcraft/
* author of the source: David Churchill
**/
package bwmcts.sparcraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bwmcts.sparcraft.players.Player;

public class Game {

	/**
	 * @param args
	 */
	
	private GameState state;
	
	private Player[]	_players=new Player[2];
	private int				rounds;
	public int				moveLimit;
	
	private boolean display=false;
	public SparcraftUI ui;
		
	
	public Game(GameState initialState, Player p1, Player p2, int limit){
		state=initialState;
		_players[0]=p1;
		_players[1]=p2;
		this.moveLimit=limit;
		this.rounds=0;
	}

	public Game(GameState initialState, Player p1, Player p2, int limit, boolean display){
		state=initialState;
		_players[0]=p1;
		_players[1]=p2;
		this.moveLimit=limit;
		this.rounds=0;
		this.display=display;
		if (display){

	    	ui = SparcraftUI.getUI(state, p1, p2);
	        
	    }
	}

	
// play the game until there is a winner
	public void play(){

		ArrayList<UnitAction>scriptMoves_A = new ArrayList<UnitAction>();
		ArrayList<UnitAction>scriptMoves_B = new ArrayList<UnitAction>();
		Player toMove;
		Player enemy;
		HashMap<Integer,List<UnitAction>> moves_A=new HashMap<Integer,List<UnitAction>>();
        HashMap<Integer,List<UnitAction>> moves_B=new HashMap<Integer,List<UnitAction>>();
        int playerToMove=-1;

	    while (!this.gameOver()){
	    	
	        if (rounds >= moveLimit)
	        {
	            break;
	        }
	    	
	        scriptMoves_A.clear();
	        scriptMoves_B.clear();
	
	        // the player that will move next
	        playerToMove=getPlayerToMove();
	        toMove = _players[playerToMove];
	        enemy = _players[GameState.getEnemy(playerToMove)];

	        // generate the moves possible from this state
	        moves_A.clear();
	        moves_B.clear();

			state.generateMoves(moves_A, toMove.ID());

	        
	        // if both players can move, generate the other player's moves
	        if (state.bothCanMove())
	        {

	        	state.generateMoves(moves_B, enemy.ID());

				enemy.getMoves(state, moves_B, scriptMoves_B);

	            state.makeMoves(scriptMoves_B);

	        }
	        
	        // the tuple of moves he wishes to make
	        toMove.getMoves(state, moves_A, scriptMoves_A);
	        // make the moves
			state.makeMoves(scriptMoves_A);

	        if (display)
	        {
	        	GameState copy=state.clone();
	        	copy.finishedMoving();
	        	
	        	int nextTime=Math.min(copy.getUnit(0,0).firstTimeFree(), copy.getUnit(1,0).firstTimeFree());
	        	int time=state.getTime();
	        	if (time<nextTime){
		        	while (time<nextTime){
		        		copy.setTime(time);
				        ui.setGameState(copy);
				        ui.repaint();
			        	try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
			        	time++;
		        	}
	        	} else {
	        		ui.setGameState(copy);
			        ui.repaint();
		        	try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	        	}
	        }
	        
	        state.finishedMoving();
		    
	        rounds++;
	        
	    }
	}


	public int getRounds(){
		return rounds;
	}
	

// returns whether or not the game is over
	public boolean gameOver()
	{
	    return state.isTerminal(); 
	}

	public GameState getState()
	{
	    return state;
	}

// determine the player to move
	public int getPlayerToMove()
	{
	   Players whoCanMove=state.whoCanMove();
	
	   Players random = Math.random() >= 0.5 ? Players.Player_One : Players.Player_Two;
	   
	   return whoCanMove==Players.Player_Both ? random.ordinal(): whoCanMove.ordinal();
	}

}