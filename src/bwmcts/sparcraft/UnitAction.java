/**
* This file is based on and translated from the open source project: Sparcraft
* https://code.google.com/p/sparcraft/
* author of the source: David Churchill
**/
package bwmcts.sparcraft;


public class UnitAction {

	public int	_unit;
	public int _player;
	public int _moveIndex;
	public UnitActionTypes _moveType;
	public Position        _p;

	public UnitAction(){
		_unit=255;
		_player=255;
		_moveType=UnitActionTypes.NONE;
		_moveIndex=255;

	}

	public UnitAction( int unitIndex, int player, UnitActionTypes type, int moveIndex, Position dest){
		_unit=unitIndex;
		_player=player;
		_moveType=type;
		_moveIndex=moveIndex;
		_p=dest;
	}

	public UnitAction( int unitIndex, int player, UnitActionTypes type, int moveIndex){
		_unit=unitIndex;
		_player=player;
		_moveType=type;
		_moveIndex=moveIndex;
	}

	
	
	public int unit(){ return _unit; }
	public int player()	{ return _player; }
	public int index()	{ return _moveIndex; }
    public Position pos()    { return _p; }
	public UnitActionTypes type(){return _moveType;}

	public String moveString(){
		if (_moveType == UnitActionTypes.ATTACK) 
		{
			return "ATTACK";
		}
		else if (_moveType == UnitActionTypes.MOVE)
		{
			return "MOVE";
		}
		else if (_moveType == UnitActionTypes.RELOAD)
		{
			return "RELOAD";
		}
		else if (_moveType == UnitActionTypes.PASS)
		{
			return "PASS";
		}
		else if (_moveType == UnitActionTypes.HEAL)
		{
			return "HEAL";
		}

		return "NONE";
	}

	public Position getDir(){
		assert(_moveType == UnitActionTypes.MOVE);

		return new Position(Constants.Move_Dir[_moveIndex][0], Constants.Move_Dir[_moveIndex][1]);
	}
	
	public String toString(){
		return this._moveIndex+","+this._player+","+this._unit+","+this.moveString()+","+this.pos();
		
	}
	
	public UnitAction clone(){
		UnitAction a=new UnitAction();
		
		a._unit=this._unit;
		a._player=this._player;
		a._moveIndex=this._moveIndex;
		a._moveType=this._moveType;
		if (this._p!=null)
			a._p=new Position(this._p.x,this._p.y);
	
		return a;
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _moveIndex;
		result = prime * result
				+ ((_moveType == null) ? 0 : _moveType.hashCode());
		result = prime * result + ((_p == null) ? 0 : _p.hashCode());
		result = prime * result + _player;
		result = prime * result + _unit;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UnitAction other = (UnitAction) obj;
		if (_moveIndex != other._moveIndex)
			return false;
		if (_moveType != other._moveType)
			return false;
		if (_p == null) {
			if (other._p != null)
				return false;
		} else if (!_p.equals(other._p))
			return false;
		if (_player != other._player)
			return false;
		if (_unit != other._unit)
			return false;
		return true;
	}
	
	
}
