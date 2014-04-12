/**
* This file is based on and translated from the open source project: Sparcraft
* https://code.google.com/p/sparcraft/
* author of the source: David Churchill
**/
package bwmcts.sparcraft;

import javabot.types.UnitType;
import javabot.types.UnitType.UnitTypes;

public class AnimationFrameData {
	public static int[][] attackFrameData=new int[UnitTypes.values().length][2];

	public static void Init()
	{
	    // allocate the vector according to UnitType size
	    for (int i=0; i<UnitTypes.values().length;i++){
	    	attackFrameData[i]=new int[]{0,0};
	    }

	    // Protoss Units
	    attackFrameData[UnitTypes.Protoss_Probe.ordinal()]				= new int[]{2, 2};
	    attackFrameData[UnitTypes.Protoss_Zealot.ordinal()]				= new int[]{8, 7};
	    attackFrameData[UnitTypes.Protoss_Dragoon.ordinal()]				= new int[]{7, 3};
	    attackFrameData[UnitTypes.Protoss_Dark_Templar.ordinal()]			= new int[]{9, 9};
	    attackFrameData[UnitTypes.Protoss_Scout.ordinal()]				= new int[]{2, 2};
	    attackFrameData[UnitTypes.Protoss_Corsair.ordinal()]				= new int[]{8, 8};
	    attackFrameData[UnitTypes.Protoss_Arbiter.ordinal()]				= new int[]{2, 2};

	    // Terran Units
	    attackFrameData[UnitTypes.Terran_SCV.ordinal()]					= new int[]{2, 2};
	    attackFrameData[UnitTypes.Terran_Marine.ordinal()]				= new int[]{8, 6};
	    attackFrameData[UnitTypes.Terran_Firebat.ordinal()]				= new int[]{8, 8};
	    attackFrameData[UnitTypes.Terran_Ghost.ordinal()]					= new int[]{3, 2};
	    attackFrameData[UnitTypes.Terran_Vulture.ordinal()]				= new int[]{1, 1};
	    attackFrameData[UnitTypes.Terran_Goliath.ordinal()]				= new int[]{1, 1};
	    attackFrameData[UnitTypes.Terran_Siege_Tank_Tank_Mode.ordinal()]	= new int[]{1, 1};
	    attackFrameData[UnitTypes.Terran_Siege_Tank_Siege_Mode.ordinal()]	= new int[]{1, 1};
	    attackFrameData[UnitTypes.Terran_Wraith.ordinal()]				= new int[]{2, 2};
	    attackFrameData[UnitTypes.Terran_Battlecruiser.ordinal()]			= new int[]{2, 2};
	    attackFrameData[UnitTypes.Terran_Valkyrie.ordinal()]				= new int[]{40, 40};

	    // Zerg Units
	    attackFrameData[UnitTypes.Zerg_Drone.ordinal()]					= new int[]{2, 2};
	    attackFrameData[UnitTypes.Zerg_Zergling.ordinal()]				= new int[]{5, 5};
	    attackFrameData[UnitTypes.Zerg_Hydralisk.ordinal()]				=new int[]{3, 2};
	    attackFrameData[UnitTypes.Zerg_Lurker.ordinal()]					= new int[]{2, 2};
	    attackFrameData[UnitTypes.Zerg_Ultralisk.ordinal()]				= new int[]{14, 14};
	    attackFrameData[UnitTypes.Zerg_Mutalisk.ordinal()]				= new int[]{1, 1};
	    attackFrameData[UnitTypes.Zerg_Devourer.ordinal()]				= new int[]{9, 9};
	}
	
	public static int[] getAttackFrames(UnitType type)
	{
	    return attackFrameData[type.getID()];
	}
}


