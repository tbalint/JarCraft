/**
* This file is based on and translated from the open source project: Sparcraft
* https://code.google.com/p/sparcraft/
* author of the source: David Churchill
**/
package bwmcts.sparcraft;

import javabot.JNIBWAPI;

public class Map {
	
	
	
	
	int					_walkTileWidth;
	int					_walkTileHeight;
	int					_buildTileWidth;
	int					_buildTileHeight;
	boolean[][]						_mapData;	            // true if walk tile [x][y] is walkable

	boolean[][]						_unitData;	            // true if unit on build tile [x][y]
	boolean[][]						_buildingData;          // true if building on build tile [x][y]

	public Position getWalkPosition(Position pixelPosition)
	{
		return new Position(pixelPosition.getX() / 8, pixelPosition.getY() / 8);
	}

    void resetVectors()
    {
        _mapData =          new boolean[_walkTileWidth][_walkTileHeight];
		_unitData =         new boolean[_buildTileWidth][_buildTileHeight];
		_buildingData =     new boolean[_buildTileWidth][_buildTileHeight];
		for (int i=0; i<_walkTileWidth;i++){
			for (int j=0; j<_walkTileHeight;j++){
				_mapData[i][j]=true;
			}
		}
		for (int i=0; i<_buildTileWidth;i++){
			for (int j=0; j<_buildTileHeight;j++){
				_buildingData[i][j]=false;
				_unitData[i][j]=false;
			}
		}
    }

    public	Map(){ 
        _walkTileWidth=0;
		_walkTileHeight=0;
		_buildTileWidth=0;
		_buildTileHeight=0;
    
    }

    // constructor which sets a completely walkable map
    public Map(int bottomRightBuildTileX, int bottomRightBuildTileY){
        _walkTileWidth=bottomRightBuildTileX * 4;
		_walkTileHeight=bottomRightBuildTileY * 4;
		_buildTileWidth=bottomRightBuildTileX;
		_buildTileHeight=bottomRightBuildTileY;
        resetVectors();
    }

	public Map(javabot.model.Map map){ 
        _walkTileWidth=map.getWalkWidth();
		_walkTileHeight=map.getWalkHeight();
		_buildTileWidth=map.getWidth();
		_buildTileHeight=map.getHeight();
	
		resetVectors();

		for (int x=0; x<_walkTileWidth; x++)
		{
			for (int y=0; y<_walkTileHeight; y++)
			{
				setMapData(x, y, map.isWalkable(x, y));
			}
		}
	}
	
	public int getPixelWidth()
    {
        return getWalkTileWidth() * 4;
    }

    public int getPixelHeight()
    {
        return getWalkTileHeight() * 4;
    }

	public int getWalkTileWidth()
	{
		return _walkTileWidth;
	}

	public int getWalkTileHeight()
	{
		return _walkTileHeight;
	}

	public int getBuildTileWidth()
	{
		return _buildTileWidth;
	}

	public int getBuildTileHeight()
	{
		return _buildTileHeight;
	}

	public boolean isWalkable(Position pixelPosition)
	{
		//Position wp=getWalkPosition(pixelPosition);
		if (pixelPosition.x<0 || pixelPosition.y<0){
			return false;
		}
		return	isWalkable(pixelPosition.x/8, pixelPosition.y/8);
	}
    
    public boolean isFlyable(Position pixelPosition)
	{
		//Position wp=getWalkPosition(pixelPosition);
    	if (pixelPosition.x<0 || pixelPosition.y<0){
			return false;
		}
    	return	isWalkable(pixelPosition.x/8, pixelPosition.y/8);
	}

	public boolean isWalkable(int walkTileX, int walkTileY) {
		return	walkTileX >= 0 && walkTileX < getWalkTileWidth() && 
				walkTileY >= 0 && walkTileY < getWalkTileHeight() &&
				getMapData(walkTileX, walkTileY);
	}

    public boolean isFlyable(int walkTileX, int walkTileY)
	{
		return	walkTileX >= 0 && walkTileX < getWalkTileWidth() && 
				walkTileY >= 0 && walkTileY < getWalkTileHeight();
	}

	public boolean getMapData(int walkTileX, int walkTileY)
	{
		return _mapData[walkTileX][walkTileY];
	}

	public boolean getUnitData(int buildTileX, int buildTileY)
	{
		return _unitData[buildTileX][buildTileY];
	}

	public void setMapData(int walkTileX, int walkTileY, boolean val)
	{
		_mapData[walkTileX][walkTileY] = val;
	}

	public void setUnitData(JNIBWAPI game)
	{
		_unitData = new boolean[getBuildTileWidth()][getBuildTileHeight()];//true

		for (javabot.model.Unit unit :  game.getAllUnits())
		{
			
			if (!game.getUnitType(unit.getID()).isBuilding())
			{
				addUnit(new Unit(game.getUnitType(unit.getTypeID()),unit.getPlayerID(),new Position(unit.getX(), unit.getY())));
			}
		}
	}

	public boolean canBuildHere(Position pos)
	{
		return _unitData[pos.getX()][pos.getY()] && _buildingData[pos.getX()][pos.getY()];
	}

	public void setBuildingData(JNIBWAPI game)
	{
		_buildingData = new boolean[getBuildTileWidth()][getBuildTileHeight()];// true));

		for(javabot.model.Unit unit: game.getAllUnits())
		{
			if (game.getUnitType(unit.getID()).isBuilding())
			{
				addUnit(new Unit(game.getUnitType(unit.getTypeID()),unit.getPlayerID(),new Position(unit.getX(), unit.getY())));
			}
		}
	}

	public void addUnit(Unit unit)
	{
		if (unit.type().isBuilding())
		{
			int tx = unit.pos().getX() / Constants.TILE_SIZE;
			int ty = unit.pos().getY() / Constants.TILE_SIZE;
			int sx = unit.type().getTileWidth(); 
			int sy = unit.type().getTileHeight();
			for(int x = tx; x < tx + sx && x < (int)getBuildTileWidth(); ++x)
			{
				for(int y = ty; y < ty + sy && y < (int)getBuildTileHeight(); ++y)
				{
					_buildingData[x][y] = true;
				}
			}
		}
		else
		{
			int startX = (unit.pos().getX() - unit.type().getDimensionLeft()) / Constants.TILE_SIZE;
			int endX   = (unit.pos().getX() + unit.type().getDimensionRight() + Constants.TILE_SIZE - 1) / Constants.TILE_SIZE; // Division - round up
			int startY = (unit.pos().getY() - unit.type().getDimensionUp()) / Constants.TILE_SIZE;
			int endY   = (unit.pos().getY() + unit.type().getDimensionDown() + Constants.TILE_SIZE - 1) / Constants.TILE_SIZE;
			for (int x = startX; x < endX && x < (int)getBuildTileWidth(); ++x)
			{
				for (int y = startY; y < endY && y < (int)getBuildTileHeight(); ++y)
				{
					_unitData[x][y] = true;
				}
			}
		}
	}

	public Map clone(){

		Map m = new Map();
		m._walkTileWidth=this._walkTileWidth;
		m._walkTileHeight=this._walkTileHeight;
		m._buildTileWidth=this._buildTileWidth;
		m._buildTileHeight=this._buildTileHeight;
		m._mapData =          new boolean[_walkTileWidth][_walkTileHeight];
		m._unitData =         new boolean[_buildTileWidth][_buildTileHeight];
		m._buildingData =     new boolean[_buildTileWidth][_buildTileHeight];
		for (int i=0; i<_walkTileWidth;i++){
			for (int j=0; j<_walkTileHeight;j++){
				m._mapData[i][j]=this._mapData[i][j];
			}
		}
		
		for (int i=0; i<_buildTileWidth;i++){
			for (int j=0; j<_buildTileHeight;j++){
				m._buildingData[i][j]=this._buildingData[i][j];
				m._unitData[i][j]=this._unitData[i][j];
			}
		}
		return m;
	}
	

}
