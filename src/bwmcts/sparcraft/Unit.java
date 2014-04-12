/**
* This file is based on and translated from the open source project: Sparcraft
* https://code.google.com/p/sparcraft/
* author of the source: David Churchill
**/
package bwmcts.sparcraft;

import javabot.JNIBWAPI;
import javabot.types.UnitCommandType.UnitCommandTypes;
import javabot.types.UnitType;
import javabot.types.UnitType.UnitTypes;
import javabot.types.WeaponType;

public class Unit implements Comparable<Unit> {
    UnitType     _unitType;				// the BWAPI unit type that we are mimicing
    int        _range;
	
	Position            _position;				// current location in a possibly infinite space
	
	int              _unitID;				// unique unit ID to the state it's contained in
    int              _playerID;				// the player who controls the unit
	
	public int          _currentHP;				// current HP of the unit
	int          _currentEnergy;

	int            _timeCanMove;			// time the unit can next move
	int            _timeCanAttack;			// time the unit can next attack

	UnitAction          _previousAction=new UnitAction();		// the previous move that the unit performed
	int            _previousActionTime;	// the time the previous move was performed
	Position            _previousPosition;

    int    _prevCurrentPosTime;
     Position    _prevCurrentPos;
     int moveCoolDown;
     int attackCoolDown;
     public int damage;
     public float dpf;


	public Unit(UnitType unitType, int playerID, Position pos){
		_unitType  =unitType;
	    _range    =      new      PlayerWeapon(PlayerProperties.Get(playerID), WeaponProperties.Get(_unitType.getGroundWeaponID()).type).GetMaxRange() + Constants.Range_Addition;
	    _unitID               =0;
	    _playerID             =playerID;
	    _currentHP            =unitType.getMaxHitPoints()+unitType.getMaxShields();
	    _currentEnergy        =unitType.getMaxEnergy()>0?50:0;
	    _timeCanMove          =0;
	    _timeCanAttack        =0;
	    _previousActionTime   =0;
	    _prevCurrentPosTime   =0;
	    _position=pos;
	    _prevCurrentPos=pos.clone();
	    _previousPosition=pos.clone();
	    moveCoolDown=moveCooldown();
	    attackCoolDown=attackCooldown();
	    damage=damage();
	    dpf=dpf();
	    _range=_range*_range;
	}
	//Unit(BWAPI::Unit * unit, BWAPI::Game * game, const IDType & playerID, const TimeType & gameTime);
	public Unit(UnitTypes unitType, int playerID, Position pos){
		
		
	}
	public Unit(UnitType unitType,  Position pos, int unitID, int playerID, int hp, int energy, int tm, int ta){
	
		_unitType             =unitType;
	    _range                =new PlayerWeapon(PlayerProperties.Get(playerID), WeaponProperties.Get(_unitType.getGroundWeaponID()).type).GetMaxRange()+ Constants.Range_Addition;
	    //, _range                (unitType.groundWeapon().maxRange() + Constants::Range_Addition)
	    _position             =pos;
	    _unitID               =unitID;
	    _playerID             =playerID;
	    _currentHP            =hp;
	    _currentEnergy        =energy;
	    _timeCanMove          =tm;
	    _timeCanAttack        =ta;
	    _previousActionTime   =0;
	    _prevCurrentPosTime   =0;
	    _prevCurrentPos=pos.clone();
	    _previousPosition=pos.clone();
	    moveCoolDown=moveCooldown();
	    attackCoolDown=attackCooldown();
	    damage=damage();
	    dpf=dpf();
	    _range=_range*_range;
    }

	
	

    public Unit() {
		// TODO Auto-generated constructor stub
	}
	// action functions
	public void setPreviousAction(UnitAction m, int previousMoveTime){
		 _previousAction = m;
		 _previousActionTime = previousMoveTime; 
		
	}
	public void updateAttackActionTime(int newTime){
		 _timeCanAttack = newTime; 
	}
	public void updateMoveActionTime(int newTime){
		 _timeCanMove = newTime;
	}
	public void attack(UnitAction move, int gameTime){
		
		if (_previousAction._moveType == UnitActionTypes.ATTACK || _previousAction._moveType == UnitActionTypes.RELOAD)
	    {
			
	        // add the repeat attack animation duration
	        // can't attack again until attack cooldown is up
			_timeCanMove=gameTime + attackRepeatFrameTime();
			_timeCanAttack=gameTime + attackCoolDown;
	    }
	    // if there previous action was a MOVE action, add the move penalty
	    else if (_previousAction._moveType == UnitActionTypes.MOVE)
	    {
	    	_timeCanMove=gameTime + attackInitFrameTime() + 2;
	    	_timeCanAttack=gameTime + attackCoolDown + Constants.Move_Penalty;
	    }
	    else
	    {
	        // add the initial attack animation duration
	    	_timeCanMove=gameTime + attackInitFrameTime() + 2;
	    	_timeCanAttack=gameTime + attackCoolDown;
	    }
	    setPreviousAction(move, gameTime);
	}
	public void heal(UnitAction move, Unit target, int gameTime){
		 _currentEnergy -= healCost();

	    // can't attack again until attack cooldown is up
	    updateAttackActionTime        (gameTime + healCooldown());
	    updateMoveActionTime          (gameTime + healCooldown());

	    if (currentEnergy() < healCost())
	    {
	        updateAttackActionTime(1000000);
	    }

	    setPreviousAction(move, gameTime);
	
	}
	public void move(UnitAction move,int gameTime) {
		
		 _previousPosition = pos();

		    // get the distance to the move action destination
		   // int dist = move.pos().getDistance(pos());
		    
		    // how long will this move take?
		    //int moveDuration = (int)((double)move.pos().getDistance(pos()) / speed());

		    // update the next time we can move, make sure a move always takes 1 time step

		 _timeCanMove=gameTime + Math.max( (int)((double)move.pos().getDistance(pos()) / _unitType.getTopSpeed()), 1);

		    // assume we need 4 frames to turn around after moving
		 _timeCanAttack=Math.max(_timeCanAttack, _timeCanMove);

		    // update the position
		    //_position.addPosition(dist * dir.x(), dist * dir.y());
	    _position=move.pos().clone();
	    
	    setPreviousAction(move, gameTime);
	}
	public void waitUntilAttack(UnitAction move, int gameTime){
		_timeCanMove=_timeCanAttack;
	    setPreviousAction(move, gameTime);
	}
	public void pass(UnitAction move, int gameTime){
		_timeCanMove=gameTime + Constants.Pass_Move_Duration;
		_timeCanAttack=gameTime + Constants.Pass_Move_Duration;
	    setPreviousAction(move, gameTime);
	}
	public void takeAttack(Unit attacker){
		PlayerWeapon weapon=attacker.getWeapon(this);
	    //int      damage=attacker.getWeapon(this).GetDamageBase();

	    //damage =Math.max((int)((attacker.getWeapon(this).GetDamageBase()-getArmor()) * attacker.getWeapon(this).GetDamageMultiplier(getSize())), 2);
	    
	    //std::cout << (int)attacker.player() << " " << damage << "\n";

	   // updateCurrentHP(_currentHP - Math.max((int)((attacker.getWeapon(this).GetDamageBase()-getArmor()) * attacker.getWeapon(this).GetDamageMultiplier(getSize())), 2));
		_currentHP -= Math.max((int)((weapon.GetDamageBase()-getArmor()) * weapon.GetDamageMultiplier(getSize())), 2);
	}
	public void takeHeal(Unit healer){
		updateCurrentHP(_currentHP + healer.healAmount());
	}

	// conditional functions
	public boolean isMobile() {
		 return _unitType.isCanMove(); 
	}
	public boolean isOrganic()  {
		return _unitType.isOrganic(); 
	}
	public boolean isAlive()   {
		return _currentHP > 0;
	}
	public boolean canAttackNow()    {
		return _unitType.getID() != UnitTypes.Terran_Medic.ordinal() && _timeCanAttack <= _timeCanMove;
	}
	public boolean canMoveNow()     {
		//return _unitType.isCanMove() && _timeCanMove <= _timeCanAttack;
		return _timeCanMove <= _timeCanAttack;
	}
	public boolean canHealNow()  {
		return canHeal() && (currentEnergy() >= healCost()) && (_timeCanAttack <= _timeCanMove); 
	}
	public boolean canKite()      {
		return _timeCanMove < _timeCanAttack; 
	}
	public boolean canHeal()      { 
		return _unitType.getID() == UnitTypes.Terran_Medic.ordinal(); 
	}

	public boolean canAttackTarget(Unit unit,int gameTime){

		//WeaponType weapon =WeaponProperties.props[ unit.type().isFlyer() ? type().getAirWeaponID() : type().getGroundWeaponID()].type;

	    if (WeaponProperties.props[ unit._unitType.isFlyer() ? _unitType.getAirWeaponID() : _unitType.getGroundWeaponID()].type.getDamageAmount() == 0)
	    //if (!(unit._unitType.isFlyer() ? _unitType.isCanAttackAir() : _unitType.isCanAttackGround()))
		{
	        return false;
	    }

	    // range of this unit attacking
	    //int r = range();

	    // return whether the target unit is in range
	    return _range >= getDistanceSqToPosition(unit.currentPosition(gameTime), gameTime);
    }
	public boolean canHealTarget(Unit unit, int gameTime) { 
		if (!canHeal() || !unit.isOrganic() || !(unit.player() == player()) || (unit.currentHP() == unit.maxHP()))
	    {
	        // then it can't heal the target
	        return false;
	    }

	    // range of this unit attacking
	    int r = healRange();

	    // return whether the target unit is in range
	    return (r * r) >= getDistanceSqToUnit(unit, gameTime);
    }

    // id related
	public void setUnitID(int id){
		_unitID=id;
		}
	public int  getId(){
		return _unitID;
		}
	public int  player(){
		return _playerID;
		}

    // position related functions
	public Position pos() {
		return _position;
	}
	int  x()  {
		return _position.getX();
	}
	int  y() {
		return _position.getY();
	}
	public int range()  {
		return _range; 
	}
	public int healRange()  {
		return canHeal() ? 96 : 0; 
	}
	public int getDistanceSqToUnit(Unit u, int gameTime) {
		return getDistanceSqToPosition(u.currentPosition(gameTime), gameTime); 
	}
	public int getDistanceSqToPosition(Position p, int gameTime) {
		return currentPosition(gameTime).getDistanceSq(p);
	}
	
	public int getDistanceSqToPosition(int x, int y, int gameTime) {
		return currentPosition(gameTime).getDistanceSq(x,y);
	}
    public Position currentPosition(int gameTime)  {
    	if (_previousAction._moveType == UnitActionTypes.MOVE)
        {
            // if gameTime is equal to previous move time then we haven't moved yet
    		if (gameTime == _previousActionTime)
            {

                return _previousPosition;
            }
    		
    		else if (gameTime >= _timeCanMove)
	        {
	         	
	             return _position;
	        }
    		
            // else if game time is >= time we can move, then we have arrived at the destination
           
            // otherwise we are still moving, so calculate the current position
            else if (gameTime == _prevCurrentPosTime)
            {
            	
                return _prevCurrentPos;
            }
            else
            {

                _prevCurrentPosTime = gameTime;

                // calculate the new current position
        		
                _prevCurrentPos.moveTo(_position.x-_previousPosition.x,_position.y-_previousPosition.y);

                _prevCurrentPos.scalePosition((float)(gameTime - _previousActionTime) / (_timeCanMove - _previousActionTime));
                _prevCurrentPos.addPosition(_previousPosition);

                return _prevCurrentPos;
            }
        }
        // if it wasn't a MOVE, then we just return the Unit position
        else
        {
            return _position;
        }
    }

    // health and damage related functions
	public int damage()  {
		 return _unitType.getID() == UnitTypes.Protoss_Zealot.ordinal() ? 
			        2 * WeaponProperties.Get(_unitType.getGroundWeaponID()).type.getDamageAmount() : 
			    WeaponProperties.Get(_unitType.getGroundWeaponID()).type.getDamageAmount(); 
	}
	
	public int damageGround()  {
		 return _unitType.getID() == UnitTypes.Protoss_Zealot.ordinal() ? 
			        2 * WeaponProperties.Get(_unitType.getGroundWeaponID()).type.getDamageAmount() : 
			    WeaponProperties.Get(_unitType.getGroundWeaponID()).type.getDamageAmount(); 
	}
	
	public int damageAir(){
		return WeaponProperties.Get(_unitType.getAirWeaponID()).type.getDamageAmount();
	}
	
	public int healAmount() {
		 return canHeal() ? 6 : 0;
	}
	public int maxHP() {
		return _unitType.getMaxHitPoints() + _unitType.getMaxShields(); 
	}
	public int currentHP() {
		return _currentHP; 
	}
	public int currentEnergy() {
		return _currentEnergy;
	}
	public int maxEnergy() {
		return _unitType.getMaxEnergy(); 
	}
	public int healCost() {
		return 3; 
	}
	public int getArmor() {
		  return UnitProperties.Get(type()).GetArmor(PlayerProperties.Get(player())); 
	}
	
	public float dpf()  {
		return Math.max(Constants.Min_Unit_DPF, (float)damage / (attackCoolDown + 1)); 
	}
	public void updateCurrentHP(int newHP){
		 _currentHP =Math.min(maxHP(), newHP); 
	}
	public int getSize() {
		 return _unitType.getSizeID();
	}
	public WeaponType getWeapon(UnitType target) {
		return target.isFlyer() ? WeaponProperties.Get(_unitType.getAirWeaponID()).type : WeaponProperties.Get(_unitType.getGroundWeaponID()).type;
	}
	public PlayerWeapon getWeapon(Unit target){
		 return new PlayerWeapon(PlayerProperties.Get(player()), target.type().isFlyer() ? WeaponProperties.Get(_unitType.getAirWeaponID()).type : WeaponProperties.Get(_unitType.getGroundWeaponID()).type);
	}

    // time and cooldown related functions
	public int moveCooldown() {
		return (int)((float)Constants.Move_Distance / _unitType.getTopSpeed()); 
	}
	public int attackCooldown()  {
		return WeaponProperties.Get(_unitType.getGroundWeaponID()).GetCooldown(PlayerProperties.Get(_playerID)); 
	}
	public int healCooldown(){
		return 8;
	}
	public int nextAttackActionTime(){
		return _timeCanAttack; 
	}
	public int nextMoveActionTime(){
		return _timeCanMove;
	}
	public int previousActionTime() {
		return _previousActionTime; 
	}
	public int firstTimeFree(){
		return _timeCanAttack <= _timeCanMove ? _timeCanAttack : _timeCanMove; 
	}
	public int attackInitFrameTime() {
		return AnimationFrameData.getAttackFrames(_unitType)[0]; 
	}
	public int attackRepeatFrameTime(){
		return AnimationFrameData.getAttackFrames(_unitType)[1]; 
	}
	public void setCooldown(int attack, int move){
		 _timeCanAttack = attack; _timeCanMove = move; 
	}

    // other functions
	public int typeID(){
		return _unitType.getID(); 
	}
	public double  speed(){
		 return _unitType.getTopSpeed(); 
	}
	public UnitType   type(){
		 return _unitType; 
	}
	public UnitAction  previousAction() {
		 return _previousAction; 
	}
	public String name() {
		return _unitType.getName().replaceAll(" ", "_"); 
	}
	public void print() {
		 System.out.printf("%s %5d [%5d %5d] (%5d, %5d)\n", _unitType.getName(), currentHP(), nextAttackActionTime(), nextMoveActionTime(), x(), y());
	}
	
	public String toString(){
		return String.format("%s %5d [%5d %5d] (%5d, %5d)\n", _unitType.getName(), currentHP(), nextAttackActionTime(), nextMoveActionTime(), x(), y());
	}
	
	public int compareTo(Unit u) {
		if (!isAlive() && !u.isAlive()){
			return 0;
		}
		
		if (!isAlive())
	    {
			return 1;
	    }
	    else if (!u.isAlive())
	    {
	    	return -1;
	    }

	    if (firstTimeFree() == u.firstTimeFree())
	    {
	    	return getId() >= u.getId()? 1:-1;
	    }
	    else
	    {
	    	return firstTimeFree() >= u.firstTimeFree()? 1:-1;
	    }
		
	}
    
	public Unit clone(){
		Unit u=new Unit();
		u._unitType=this._unitType;				// the BWAPI unit type that we are mimicing
	    u._range=this._range;
		
	    if (this._position!=null)
	    	u._position=new Position(this._position.x,this._position.y);				// current location in a possibly infinite space
		
		u._unitID=this._unitID;				// unique unit ID to the state it's contained in
	    u._playerID=this._playerID;				// the player who controls the unit
		
		u._currentHP=this._currentHP;				// current HP of the unit
		u._currentEnergy=this._currentEnergy;

		u._timeCanMove=this._timeCanMove;			// time the unit can next move
		u._timeCanAttack=this._timeCanAttack;			// time the unit can next attack

		if (this._previousAction!=null)
			u._previousAction=this._previousAction.clone();;		// the previous move that the unit performed
		u._previousActionTime=this._previousActionTime;	// the time the previous move was performed
		if (this._previousPosition!=null)
			u._previousPosition=new Position(this._previousPosition.x,this._previousPosition.y);

	    u._prevCurrentPosTime=this._prevCurrentPosTime;
	    if (this._prevCurrentPos!=null)
	    	u._prevCurrentPos=new Position(this._prevCurrentPos.x,this._prevCurrentPos.y);
	    u.moveCoolDown=this.moveCoolDown;
	    u.attackCoolDown=this.attackCoolDown;
	    u.damage=this.damage;
	    u.dpf=this.dpf;

		return u;
	}
	
	//Code from UalbertaBot
	public void setUnitCooldown(JNIBWAPI bwapi,javabot.model.Unit unit){
	        int attackCooldown=0;
	        int moveCooldown=0;

	        UnitCommandTypes lastCommand = UnitCommandTypes.values()[unit.getLastCommandID()];
	        int lastCommandFrame = unit.getLastCommandFrame();
	        int currentFrame = bwapi.getFrameCount();
	        int framesSinceCommand = currentFrame - lastCommandFrame;
  
	        attackCooldown = currentFrame + Math.max(0, unit.getGroundWeaponCooldown()-2);
	        // if the last attack was an attack command
	        if (lastCommand == UnitCommandTypes.Attack_Unit)
	        {
	            moveCooldown = currentFrame +Math.max(0, attackInitFrameTime() - framesSinceCommand);

	        }
	        // if the last command was a move command
	        else if (lastCommand == UnitCommandTypes.Move)
	        {
        		
                moveCooldown = currentFrame + Math.max(0, moveCoolDown - framesSinceCommand);

	        }

	        if (moveCooldown - currentFrame < 4 || unit.isMoving())
	        {
	        	
	                moveCooldown = currentFrame;
	        }

	        moveCooldown = Math.min(moveCooldown, attackCooldown);

	        this._timeCanMove=moveCooldown;
	        this._timeCanAttack=attackCooldown;
	}

}
