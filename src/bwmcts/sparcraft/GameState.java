/**
* This file is based on and translated from the open source project: Sparcraft
* https://code.google.com/p/sparcraft/
* author of the source: David Churchill
**/
package bwmcts.sparcraft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javabot.JNIBWAPI;
import javabot.types.UnitType.UnitTypes;
import javabot.types.UnitType;



public class GameState {

	private Map   _map;               
	        
	Unit[][] _units=new Unit[Constants.Num_Players][Constants.Max_Moves];
	      
	int[][] _unitIndex=new int[Constants.Num_Players][Constants.Max_Moves];
	List<Unit> _neutralUnits;
	
	public int[]  _numUnits=new int[Constants.Num_Players];
	int[]  _prevNumUnits=new int[Constants.Num_Players];
	
	float[]  _totalLTD=new float[Constants.Num_Players];
	float[] _totalSumSQRT=new float[Constants.Num_Players];
	
	
	int[][] _closestMoveIndex=new int[Constants.Num_Players][Constants.Max_Moves];
	public int  _currentTime;


	    // checks to see if the unit array is full before adding a unit to the state
    private boolean checkFull(int player){return true;}
    private boolean checkUniqueUnitIDs(){return true;}


	
	public GameState(){
		_map=null;
		_currentTime=0;

		for (int u=0; u<Constants.Max_Moves; u++)
		{
	        _unitIndex[0][u] = u;
			_unitIndex[1][u] = u;
		}
	}
	
	public GameState(String filename){}

	public GameState(JNIBWAPI bwapi) {
	
		//Update PlayerProperties
		PlayerProperties.props[0]=new PlayerProperties(bwapi.getPlayer(0));
		PlayerProperties.props[1]=new PlayerProperties(bwapi.getPlayer(1));
		
		_units=new Unit[Constants.Num_Players][Constants.Max_Moves];
		int i=0;
		for (javabot.model.Unit u: bwapi.getMyUnits()){
			//System.out.println(bwapi.getFrameCount()+" - "+u.getLastCommandFrame()+": "+u.getGroundWeaponCooldown()+": "+u.getAirWeaponCooldown());
			_units[bwapi.getSelf().getID()][i]=new Unit(UnitProperties.Get(u.getTypeID()).type,new Position(u.getX(),u.getY()), u.getID(), u.getPlayerID(), u.getHitPoints()+u.getShield(), u.getEnergy(),bwapi.getFrameCount(),bwapi.getFrameCount());
			_units[bwapi.getSelf().getID()][i].setUnitCooldown(bwapi, u);
			
			i++;
		}
		i=0;
		for (javabot.model.Unit u: bwapi.getEnemyUnits()){

			//TODO 
			//System.out.println(bwapi.getFrameCount()+" - "+u.getLastCommandFrame()+": "+u.getGroundWeaponCooldown()+": "+u.getAirWeaponCooldown());
			
			_units[bwapi.getEnemies().get(0).getID()][i]=new Unit(UnitProperties.Get(u.getTypeID()).type,new Position(u.getX(),u.getY()), u.getID(), u.getPlayerID(), u.getHitPoints()+u.getShield(), u.getEnergy(),bwapi.getFrameCount(),bwapi.getFrameCount()+u.getGroundWeaponCooldown());
			
			i++;
		}
		
		//_maxUnits=Constants.Max_Moves; 
		 _unitIndex=new int[Constants.Num_Players][Constants.Max_Moves];
		 //TODO check!!!!
		 //for (int u=0; u<_maxUnits; u++)
		 for (int u=0; u<Constants.Max_Moves; u++)
			{
		        _unitIndex[0][u] = u;
				_unitIndex[1][u] = u;
			}
		 _neutralUnits=new ArrayList<Unit>();

		 _numUnits=new int[2];
		 _numUnits[bwapi.getEnemies().get(0).getID()] = bwapi.getEnemyUnits().size();
		 _numUnits[bwapi.getSelf().getID()] = bwapi.getMyUnits().size();
		 //_numUnits=new int[]{bwapi.getMyUnits().size(),bwapi.getEnemyUnits().size()};
		 _prevNumUnits=new int[]{_numUnits[0],_numUnits[1]};

		 _totalLTD=new float[Constants.Num_Players];
		 _totalSumSQRT=new float[Constants.Num_Players];

		 //_numMovements=new int[]{0,0};
		 //_prevHPSum=new int[]{0,0};//TODO
			
	    _currentTime=bwapi.getFrameCount();
	    
	    //_sameHPFrames=0;

		
		calculateStartingHealth();
		this._map=new Map(bwapi.getMap());
		sortUnits();
	}
	
	// misc functions
	public void finishedMoving(){
		//sort units
		for (int p=0; p<Constants.Num_Players; p++)
		{
			if (_prevNumUnits[p] <= 1)
			{
				_prevNumUnits[p]= _numUnits[p];
				continue;
			}
			else
			{
				Arrays.sort(_units[p],0,_prevNumUnits[p]);
				_prevNumUnits[p]= _numUnits[p];
			}
		}

		//Update game time
		_currentTime=Math.min(getUnit(0,0).firstTimeFree(), getUnit(1,0).firstTimeFree());

	}
	
	
	public boolean playerDead(int player){
		if (numUnits(player) <= 0)
		{
			return true;
		}

		for (int u=0; u<_numUnits[player]; u++)
		{
			if (getUnit(player, u).damage > 0)
			{
				return false;
			}
		}

		return true;
	}
	public boolean isTerminal(){
		if (playerDead(Players.Player_One.ordinal()) || playerDead(Players.Player_Two.ordinal()))
	    {
	        return true;
	    }


		for (int p=0; p<Constants.Num_Players; p++)
		{
			for (int u=0; u<numUnits(p); u++)
			{
				// if any unit on any team is a mobile attacker
				if (getUnit(p, u).isMobile() && !getUnit(p, u).canHeal())
				{
					// there is no deadlock, so return false
					return false;
				}
			}
		}

		// at this point we know everyone must be immobile, so check for attack deadlock
		Unit unit1, unit2;
		for (int u1=0; u1<numUnits(Players.Player_One.ordinal()); u1++)
		{
			unit1=getUnit(Players.Player_One.ordinal(), u1);

			for (int u2=0; u2<numUnits(Players.Player_Two.ordinal());u2++)
			{
				unit2=getUnit(Players.Player_Two.ordinal(), u2);

				// if anyone can attack anyone else
				if (unit1.canAttackTarget(unit2, _currentTime) || unit2.canAttackTarget(unit1, _currentTime))
				{
					// then there is no deadlock
					return false;
				}
			}
		}
		
		// if everyone is immobile and nobody can attack, then there is a deadlock
		return true;
	}

	    // unit data functions
	public int numUnits(int player){
		return _numUnits[player];
	}
	public int prevNumUnits(int player) {
		return _prevNumUnits[player];
	}
	public int numNeutralUnits(){
		return _neutralUnits.size();
	}
	public int closestEnemyUnitDistance(Unit unit){
		int enemyPlayer=getEnemy(unit.player());

		int closestDist=0;

		for (int u=0; u<numUnits(enemyPlayer); u++)
		{
	        int dist=unit.getDistanceSqToUnit(getUnit(enemyPlayer, u), _currentTime);

			if (dist > closestDist)
			{
				closestDist = dist;
			}
		}

		return closestDist;
	}

    // Unit functions
	public void sortUnits(){
		for (int p=0; p<Constants.Num_Players; p++)
		{
			if (_prevNumUnits[p] <= 1)
			{
				_prevNumUnits[p]= _numUnits[p];
				continue;
			}
			else
			{
				Arrays.sort(_units[p],0,_prevNumUnits[p]);

				_prevNumUnits[p]= _numUnits[p];
			}
		}	
	}
	public void addUnit(Unit u) throws Exception{
		checkFull(u.player());
	    //System::checkSupportedUnitType(u.type());

	    // Calculate the unitID for this unit
	    // This will just be the current total number of units in the state
	    int unitID = _numUnits[Players.Player_One.ordinal()] + _numUnits[Players.Player_Two.ordinal()];

	    // Set the unit and it's unitID
	    u.setUnitID(unitID);
	    
	    _units[u.player()][_numUnits[u.player()]]=u;

	    // Increment the number of units this player has
		_numUnits[u.player()]++;
		_prevNumUnits[u.player()]++;

	    // And do the clean-up
		finishedMoving();
		calculateStartingHealth();

	    if (!checkUniqueUnitIDs())
	    {
	        throw new Exception("GameState has non-unique Unit ID values");
	    }
		
	}
	public void addUnit(UnitType unitType, int playerID, Position pos){
		checkFull(playerID);
	    //System::checkSupportedUnitType(type);

	    // Calculate the unitID for this unit
	    // This will just be the current total number of units in the state
	    int unitID = _numUnits[Players.Player_One.ordinal()] + _numUnits[Players.Player_Two.ordinal()];

	    // Set the unit and it's unitID
	    _units[playerID][_numUnits[playerID]] = new Unit(unitType, playerID, pos);
	    _units[playerID][_numUnits[playerID]].setUnitID(unitID);
	    // Increment the number of units this player has
		_numUnits[playerID]++;
		_prevNumUnits[playerID]++;

	    // And do the clean-up
		try{
		finishedMoving();
		calculateStartingHealth();
		} catch(Exception e){}
	}
	public void addUnitWithID(Unit u){
		 checkFull(u.player());
		  //  System::checkSupportedUnitType(u.type());

		    // Simply add the unit to the array
		 _units[u.player()][_numUnits[u.player()]]=u;

		    // Increment the number of units this player has
			_numUnits[u.player()]++;
			_prevNumUnits[u.player()]++;

		    // And do the clean-up
			finishedMoving();
			calculateStartingHealth();
	}
	public void addNeutralUnit(Unit unit){
		_neutralUnits.add(unit);
	}
	public Unit getUnit(int player, int unitIndex){
		 return _units[player][_unitIndex[player][unitIndex]];
	}
	public Unit getUnitByID(int unitID) {
		for (int p=0; p<Constants.Num_Players; p++)
		{
			for (int u=0; u<numUnits(p); u++)
			{
				if (getUnit(p, u).getId() == unitID)
				{
					return getUnit(p, u);
				}
			}
		}

		System.out.println("GameState Error: getUnitByID() Unit not found");
		return null;
	}
	//public Unit getUnit(int player, int unitIndex){return null;}
	public Unit getUnitByID(int player, int unitID) {
		for (int u=0; u<numUnits(player); u++)
		{
			if (getUnit(player, u).getId() == unitID)
			{
				return getUnit(player, u);
			}
		}

		System.out.println("GameState Error: getUnitByID() Unit not found");
		return null;
	}

	public Unit getClosestEnemyUnit(int player, int unitIndex){
		int enemyPlayer=getEnemy(player);
		Position myUnitPosition=getUnit(player,unitIndex).currentPosition(_currentTime);

		int minDist=Integer.MAX_VALUE;
		int minUnitInd=0;
	    //int minUnitID=255;

		//Position currentPos = myUnit.currentPosition(_currentTime);
		int distSq=0;
		for (int u=0; u<_numUnits[enemyPlayer]; u++)
		{
	        distSq = myUnitPosition.getDistanceSq(getUnit(enemyPlayer, u).currentPosition(_currentTime));
			if ((distSq < minDist))
			{
				minDist = distSq;
				minUnitInd = u;

			}

		}

		return getUnit(enemyPlayer, minUnitInd);
		//return getUnit(getEnemy(player),_closestMoveIndex[player][unitIndex]);
	}
	public Unit getClosestEnemyUnit(Position myUnitPosition,int enemyPlayer,int minDist,int minUnitInd,int distSq){


		for (int u=0; u<_numUnits[enemyPlayer]; u++)
		{
			distSq = myUnitPosition.getDistanceSq(getUnit(enemyPlayer, u).currentPosition(_currentTime));
			if (distSq < minDist)
			{
				minDist = distSq;
				minUnitInd = u;
			}
		}

		return getUnit(enemyPlayer, minUnitInd);
	}
	public Unit getClosestOurUnit(int player, int unitIndex){
		Unit myUnit=getUnit(player,unitIndex);

		int minDist=Integer.MAX_VALUE;
		int minUnitInd=0;

		Position currentPos = myUnit.currentPosition(_currentTime);

		for (int u=0; u<_numUnits[player]; u++)
		{
			if (u == unitIndex || getUnit(player, u).canHeal())
			{
				continue;
			}

			//size_t distSq(myUnit.distSq(getUnit(enemyPlayer,u)));
			int distSq=currentPos.getDistanceSq(getUnit(player, u).currentPosition(_currentTime));

			if (distSq < minDist)
			{
				minDist = distSq;
				minUnitInd = u;
			}
		}

		return getUnit(player, minUnitInd);
	}
	public Unit getUnitDirect(int player, int unit){
		return _units[player][unit];
	}
	public Unit getNeutralUnit(int u){
		return _neutralUnits.get(u);
	}
	    
	    // game time functions
	public void setTime(int time){
		_currentTime = time;
	}
	public int getTime(){
		return _currentTime;
	}

	    // evaluation functions
	public StateEvalScore eval( int player, EvaluationMethods evalMethod)  {
		StateEvalScore score=new StateEvalScore(0, 0);
		int enemyPlayer=getEnemy(player);

		// if both players are dead, return 0
		if (playerDead(enemyPlayer) && playerDead(player))
		{
			return new StateEvalScore(0, 0);
		}

		if (evalMethod == EvaluationMethods.LTD)
		{
			score = new StateEvalScore(evalLTD(player), 0);
		}
		else if (evalMethod == EvaluationMethods.LTD2)
		{
			score = new StateEvalScore(evalLTD2(player), 0);
		}

		if (score._val == 0)
		{
			return score;
		}

		/*int winBonus=0;

		if (playerDead(enemyPlayer) && !playerDead(player))
		{
			//winBonus = 500-getTime();
		}
		else if (playerDead(player) && !playerDead(enemyPlayer))
		{
			//winBonus = -100000;
		}*/

		return new StateEvalScore(score._val /*+ winBonus*/, score._numMoves);
	}
	
	// evaluate the state for _playerToMove
	private int evalLTD(int player) 
	{
		return LTD(player) - LTD(getEnemy(player));
	}

	// evaluate the state for _playerToMove
	private int evalLTD2(int player)
	{
		return LTD2(player) - LTD2(getEnemy(player));
	}
	
	public int LTD(int player)
	{
		if (numUnits(player) == 0)
		{
			return 0;
		}

		float sum = 0;
		Unit unit;
		for (int u = 0; u<_numUnits[player]; ++u)
		{
			 unit= getUnit(player, u);

			sum += unit.currentHP() * unit.dpf;
		}

		return (int) (1000 * sum / _totalLTD[player]);
	}
	
	public int LTD2(int player)
	{
		if (numUnits(player) == 0)
		{
			return 0;
		}

		float sum = 0;

		for (int u=0; u<numUnits(player); ++u)
		{
			Unit unit = getUnit(player, u);

			sum += Math.sqrt(unit.currentHP()) * unit.dpf;
		}

		//int ret = (int)(1000 * sum / _totalSumSQRT[player]);
		//return ret;
		return (int)(1000 * sum / _totalSumSQRT[player]);
	}
	
	
	public static int getEnemy(int player){
		return (player + 1) % 2;
	}

	    // unit hitpoint calculations, needed for LTD2 evaluation
	public void calculateStartingHealth(){
		Unit unit;
		for (int p=0; p<Constants.Num_Players; p++)
		{
			float totalHP=0;
			float totalSQRT=0;

			for (int u=0; u<_numUnits[p]; u++)
			{
				/*
				if (getUnit(p, u) == null){
					System.out.println("p="+p+" u="+u);
					continue;
				}
				*/
				unit=getUnit(p, u);
				totalHP += unit.maxHP() * unit.dpf;
				totalSQRT += Math.sqrt(unit.maxHP()) * unit.dpf;
			}

			_totalLTD[p] = totalHP;
			_totalSumSQRT[p] = totalSQRT;
		}
	}
	public void setTotalLTD(float p1,float p2){}
	public void setTotalLTD2(float p1,float p2){}
	public float getTotalLTD(int player){return 0;}  
	public float getTotalLTD2(int player){return 0;}    

	    // move related functions
	

	public void generateMoves(HashMap<Integer,List<UnitAction>> moves, int playerIndex) {
		moves.clear();

	    // which is the enemy player
		int enemyPlayer  = getEnemy(playerIndex);

	    // make sure this player can move right now
	    //int canMove=whoCanMove().ordinal();
	    /*if (whoCanMove().ordinal() == enemyPlayer)
	    {
	    	System.out.println("GameState Error - Called generateMoves() for a player that cannot currently move");
	        return;//throw new Exception("GameState Error - Called generateMoves() for a player that cannot currently move");
	    }*/

		// we are interested in all simultaneous moves
		// so return all units which can move at the same time as the first
		int firstUnitMoveTime = getUnit(playerIndex, 0).firstTimeFree();
		Unit unit;	
		Unit enemyUnit;
		int moveDistance=0;
		double timeUntilAttack=0;
		for (int unitIndex=0; unitIndex < _numUnits[playerIndex]; unitIndex++)
		{
			// unit reference
			unit=getUnit(playerIndex,unitIndex);
			if (unit==null || unit.firstTimeFree() != firstUnitMoveTime){break;}
			// if this unit can't move at the same time as the first
			/*if (unit.firstTimeFree() != firstUnitMoveTime)
			{
				// stop checking
				break;
			}*/
/*
			if (unit.previousActionTime() == _currentTime && _currentTime != 0)
			{
	            System.out.println("Previous Move Took 0 Time: " + unit.previousAction().moveString());
	            return;
			}
			*/
			ArrayList<UnitAction> actionTemp=new ArrayList<UnitAction>();
			

			// generate attack moves
			if (unit.canAttackNow())
			{
				
				for (int u=0; u<_numUnits[enemyPlayer]; u++)
				{
					enemyUnit=getUnit(enemyPlayer, u);

					if (unit.canAttackTarget(enemyUnit, _currentTime))
					{
						actionTemp.add(new UnitAction(unitIndex, playerIndex, UnitActionTypes.ATTACK, u, enemyUnit.pos()));
	                    //moves.add(UnitAction(unitIndex, playerIndex, UnitActionTypes::ATTACK, unit.ID()));
					}
				}
			}
			else if (unit.canHealNow())
			{
				for (int u=0; u<_numUnits[playerIndex]; u++)
				{
					// units cannot heal themselves in broodwar
					if (u == unitIndex)
					{
						continue;
					}

					Unit ourUnit=getUnit(playerIndex, u);
					if (ourUnit!=null && ourUnit.isAlive()){
						if (unit.canHealTarget(ourUnit, _currentTime))
						{
							actionTemp.add(new UnitAction(unitIndex, playerIndex, UnitActionTypes.HEAL, u,unit.pos()));
		                    //moves.add(UnitAction(unitIndex, playerIndex, UnitActionTypes::HEAL, unit.ID()));
						}
					} else {
						break;
					}
				}
			}
			// generate the wait move if it can't attack yet
			else if (unit._unitType.getID() != UnitTypes.Terran_Medic.ordinal())
			{
				actionTemp.add(new UnitAction(unitIndex, playerIndex, UnitActionTypes.RELOAD, 0,unit.pos()));
			}
			
			// generate movement moves
			if (unit.isMobile())
			{
	            // In order to not move when we could be shooting, we want to move for the minimum of:
	            // 1) default move distance move time
	            // 2) time until unit can attack, or if it can attack, the next cooldown
	            timeUntilAttack         = unit.nextAttackActionTime() - _currentTime;
	           // timeUntilAttack                 = ;

	            // the default move duration
	           // double defaultMoveDuration      = (double)Constants.Move_Distance / unit.speed();

	            // if we can currently attack
	            //double chosenTime  = Math.min(timeUntilAttack, defaultMoveDuration);

	            // the chosen movement distance
	            moveDistance      = (int) (Math.min(timeUntilAttack == 0 ? unit.attackCoolDown : timeUntilAttack, (double)Constants.Move_Distance / unit.speed()) * unit.speed());
	            //moveDistance      = (int) (Math.min(timeUntilAttack == 0 ? unit.attackCoolDown : timeUntilAttack, unit.moveCoolDown) * unit.speed());
	            // DEBUG: If chosen move distance is ever 0, something is wrong
	            if (moveDistance == 0)
	            {
	            	System.out.println("Move Action with distance 0 generated");
	            	continue;
	            }

	            // we are only generating moves in the cardinal direction specified in common.h
				for (int d=0; d<Constants.Num_Directions; d++)
				{			
	                // the direction of this movement
	              	//Position dir= new Position(Constants.Move_Dir[d][0], Constants.Move_Dir[d][1]);
	            
	                /*if (moveDistance == 0)
	                {
	                    System.out.printf("%lf %lf %lf\n", timeUntilAttack, defaultMoveDuration, chosenTime);
	                }*/

	                // the final destination position of the unit
	                Position dest = new Position(unit.pos().x+moveDistance*Constants.Move_DirX[d],unit.pos().y+ moveDistance*Constants.Move_DirY[d]);
	                
	                // if that poisition on the map is walkable
	                if (isWalkable(dest) || (unit.type().isFlyer() && isFlyable(dest)))
					{
	                    // add the move to the MoveArray
						actionTemp.add(new UnitAction(unitIndex, playerIndex, UnitActionTypes.MOVE, d, dest));
					}
				}
			}

			// if no moves were generated for this unit, it must be issued a 'PASS' move
			if (actionTemp.isEmpty())
			{
				actionTemp.add(new UnitAction(unitIndex, playerIndex, UnitActionTypes.PASS, 0,unit.pos()));
			}
			moves.put(unitIndex, actionTemp);
		}
		
	}
	public void makeMoves(List<UnitAction> moves){
		 if (moves.size() > 0) {
			//if (getUnit(moves.get(0)._player,moves.get(0)._unit).firstTimeFree()!=_currentTime)
	        if (whoCanMove().ordinal() == getEnemy(moves.get(0).player()))
	        {
	            //throw new Exception("GameState Error - Called makeMove() for a player that cannot currently move");
	            //System.out.print(" GameState Error - Called makeMove() for a player that cannot currently move ");
	        	return;
	        }
	    }
		UnitAction move;
		Unit ourUnit,enemyUnit;
		HashMap<Unit,Boolean> moved=new HashMap<Unit,Boolean>();
	    for (int m=0; m<moves.size(); m++)
	    {
	    	//performUnitAction(moves.get(m));
	    	move=moves.get(m);
	    	ourUnit		= getUnit(move._player, move._unit);
	    	//int player		= ourUnit.player();
	    	if (moved.containsKey(ourUnit)){
	    		continue;
	    	} else {
	    		moved.put(ourUnit, true);
	    	}

	    	if (move._moveType == UnitActionTypes.ATTACK)
	    	{
	    		//int enemyPlayer  =;
	    		enemyUnit=getUnit( getEnemy(move._player),move._moveIndex);
	            //Unit & enemyUnit(getUnitByID(enemyPlayer ,move._moveIndex));
	    			
	    		// attack the unit
	    		ourUnit.attack(move, _currentTime);
	    			
	    		// enemy unit takes damage if it is alive
	    		if (enemyUnit.isAlive())
	    		{				
	    			enemyUnit.takeAttack(ourUnit);

	    			// check to see if enemy unit died
	    			if (!enemyUnit.isAlive())
	    			{
	    				// if it died, remove it
	    				_numUnits[enemyUnit.player()]--;
	    			}
	    		}			
	    	}
	    	else if (move._moveType == UnitActionTypes.MOVE)
	    	{
	    		//_numMovements[move._player]++;

	    		ourUnit.move(move, _currentTime);
	    	}
	    	else if (move._moveType == UnitActionTypes.RELOAD)
	    	{
	    		ourUnit.waitUntilAttack(move, _currentTime);
	    	}
	    	else if (move._moveType == UnitActionTypes.HEAL)
	    	{
	    		Unit ourOtherUnit=getUnit(move._player,move._moveIndex);
	    			
	    		// attack the unit
	    		ourUnit.heal(move, ourOtherUnit, _currentTime);
	    			
	    		if (ourOtherUnit.isAlive())
	    		{
	    			ourOtherUnit.takeHeal(ourUnit);
	    		}
	    	}
	    	else if (move._moveType == UnitActionTypes.PASS)
	    	{
	    		ourUnit.pass(move, _currentTime);
	    	}
	        
	    }
	}

	public Players whoCanMove(){
		if(getUnit(0,0)==null || getUnit(1,0) == null)
			return Players.Player_None;
		
		int p1Time=getUnit(0,0).firstTimeFree();
		int p2Time=getUnit(1,0).firstTimeFree();

		// if player one is to move first
		if (p1Time < p2Time)
		{
			return Players.Player_One;
		}
		// if player two is to move first
		else if (p1Time > p2Time)
		{
			return Players.Player_Two;
		}
		else
		{
			return Players.Player_Both;
		}
	}
	public boolean bothCanMove(){
		return getUnit(0, 0).firstTimeFree() == getUnit(1, 0).firstTimeFree();
	}
			  
	    // map-related functions
	public void setMap(Map map) throws Exception{
		_map = map;

	    // check to see if all units are on walkable tiles
	    for (int p=0; p<Constants.Num_Players; p++)
	    {
	        for (int u=0; u<numUnits(p); u++)
	        {
	            Position pos=getUnit(p, u).pos();

	            if (!isWalkable(pos))
	            {
	            	throw new Exception("Unit is on non-walkable map tile: "+pos.toString());
	            }
	        }
	    }
		
	}
	
	public Map getMap() {
		return _map;
	} 
	
	public boolean isWalkable(Position pos){
		if (_map != null)
		{
			return _map.isWalkable(pos);
		}

		// if there is no map, then return true
		return true;
	}
	public boolean isFlyable(Position pos){
		if (_map != null)
		{
			return _map.isFlyable(pos);
		}
	
		// if there is no map, then return true
		return true;
	}            
	
	public Unit[][] getAllUnit(){
		return _units;
	}

	// state i/o functions
	public void print(){
		
		
		System.out.printf("State - Time: %d\n", _currentTime);

		for (int p=0; p<Constants.Num_Players; p++)
		{
			for (int u=0; u<_numUnits[p]; u++)
			{
				Unit unit=getUnit(p, u);

				System.out.printf("  P%d %5d %5d    (%3d, %3d)     %s_%d\n", unit.player(), unit.currentHP(), unit.firstTimeFree(), unit.x(), unit.y(), unit.name(),unit._unitID);
			}
		}
	}
	
	public GameState clone(){
		GameState s=new GameState();
		 s._map=this._map.clone();               
          
		 s._units=new Unit[Constants.Num_Players][Constants.Max_Moves];
        
		 s._unitIndex=new int[Constants.Num_Players][Constants.Max_Moves];
		 s._neutralUnits=new ArrayList<Unit>();

		 s._numUnits=new int[Constants.Num_Players];
		 s._prevNumUnits=new int[Constants.Num_Players];

		 s._totalLTD=new float[Constants.Num_Players];
		 s._totalSumSQRT=new float[Constants.Num_Players];

		// s._numMovements=new int[Constants.Num_Players];
		 //s._prevHPSum=new int[Constants.Num_Players];
			
		 s._currentTime=this._currentTime;
		 //s._maxUnits=this._maxUnits;
		 //s._sameHPFrames=this._sameHPFrames;

		for (int i=0; i<Constants.Num_Players;i++){
			for (int j=0; j<Constants.Max_Moves;j++){
				if (this._units[i][j]!=null)
					s._units[i][j]=this._units[i][j].clone();
				s._unitIndex[i][j]=this._unitIndex[i][j];
			}
			s._numUnits[i]=this._numUnits[i];
			s._prevNumUnits[i]=this._prevNumUnits[i];
			s._totalLTD[i]=this._totalLTD[i];
			s._totalSumSQRT[i]=this._totalSumSQRT[i];
			//s._numMovements[i]=this._numMovements[i];
			//s._prevHPSum[i]=this._prevHPSum[i];
			
		}
		if (this._neutralUnits!=null && !this._neutralUnits.isEmpty())
		for (Unit u:this._neutralUnits){
			if (u!=null)
				s._neutralUnits.add(u.clone());
		}
		
		return s;
	}
	public int aliveUnits() {
		// TODO Auto-generated method stub
		return _numUnits[0] + _numUnits[1];
	}

}
