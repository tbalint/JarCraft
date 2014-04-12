/**
* This file is based on and translated from the open source project: Sparcraft
* https://code.google.com/p/sparcraft/
* author of the source: David Churchill
**/
package bwmcts.uct;


public class UnitState {

	public UnitStateTypes type;
	public int unit;
	public int player;
	
	public UnitState(UnitStateTypes type, int unit, int player) {
		super();
		this.type = type;
		this.unit = unit;
		this.player = player;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + unit;
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
		UnitState other = (UnitState) obj;
		if (type != other.type)
			return false;
		if (unit != other.unit)
			return false;
		return true;
	}
	
	
	
}
