/**
* This file is based on and translated from the open source project: Sparcraft
* https://code.google.com/p/sparcraft/
* author of the source: David Churchill
**/
package bwmcts.sparcraft;

public class Position {

	int x;
	int y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
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
		Position other = (Position) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	public int getDistanceSq(Position p) {

		return (x-p.x)*(x-p.x) + (y-p.y)*(y-p.y);
	}

	public int getDistanceSq(int x1, int y1) {

		return (x-x1)*(x-x1) + (y-y1)*(y-y1);
	}
	
	public void subtractPosition(Position pos) {
		x -= pos.x;
		y -= pos.y;
	}

	public void scalePosition(float f) {
		x = (int)(f * x);
        y = (int)(f * y);
		
	}

	public void addPosition(Position pos) {
		x += pos.x;
		y += pos.y;
	}

	public void moveTo(Position pos) {
		x=pos.getX();
		y=pos.getY();
		
	}
	
	public void moveTo(int i, int j) {
		x=i;
		y=j;
		
	}
	
	public int getManhattanDistance(Position p){
		return Math.abs(x-p.getX()) + Math.abs(y-p.getY());	 
	}
	
	public int getManhattanDistance(int p, int q){
		return Math.abs(x-p) + Math.abs(y-q);	 
	}

	public int getDistance(Position pos) {
		int dX = x - pos.x;
        int dY = y - pos.y;
        
        if (dX == 0)
        {
            return Math.abs(dY);
        }
        else if (dY == 0)
        {
            return Math.abs(dX);
        }
        else
        {
            return (int)Math.sqrt((dX*dX - dY*dY));
        }
	}
	
	public int getDistance(int p, int q) {
		int dX = x - p;
        int dY = y - q;

        if (dX == 0)
        {
            return Math.abs(dY);
        }
        else if (dY == 0)
        {
            return Math.abs(dX);
        }
        else
        {
            return (int)Math.sqrt((dX*dX - dY*dY));
        }
	}
	
	public String toString(){
		return "("+x+" : "+y+")";
	}
	
	public Position clone(){
		return new Position(x,y);
	}
}
