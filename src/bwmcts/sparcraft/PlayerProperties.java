/**
* This file is based on and translated from the open source project: Sparcraft
* https://code.google.com/p/sparcraft/
* author of the source: David Churchill
**/
package bwmcts.sparcraft;

import javabot.model.Player;
import javabot.types.TechType;
import javabot.types.TechType.TechTypes;
import javabot.types.UpgradeType;
import javabot.types.UpgradeType.UpgradeTypes;
import javabot.types.WeaponType;

public class PlayerProperties {
	
	
	
	int NUM_UPGRADES	= 63, NUM_TECHS	= 47 ;

	int[] upgradeLevel=new int[NUM_UPGRADES];
	boolean[] hasResearched=new boolean[NUM_TECHS];

    static      PlayerProperties[]    props=new PlayerProperties[2];
	
	
	
    public static void Init(){
    	props[0]=new PlayerProperties();
    	props[1]=new PlayerProperties();
    }
    
	public PlayerProperties()
	{
		Reset();
	}

	public PlayerProperties(Player player) 
	{ 
		Capture(player); 
	}

	public static PlayerProperties Get(int playerID)
	{ 
	    return props[playerID]; 
	}

	public void Reset()
	{
		for(int i=0; i<NUM_UPGRADES; i++)
		{
			upgradeLevel[i] = 0;
		}

		for(int i=0; i<NUM_TECHS; i++)
		{
			hasResearched[i] = false;
		}
	}

	public void SetUpgradeLevel(UpgradeType upgrade, int level)
	{
		assert(upgrade.getID() != UpgradeTypes.None.ordinal());
		assert(upgrade.getID() !=UpgradeTypes.Unknown.ordinal());
		assert(level >= 0 && level <= upgrade.getMaxRepeats());
		upgradeLevel[upgrade.getID()] = level;
	}

	public void SetResearched(TechType tech, boolean researched)
	{
		assert(tech.getID() != TechTypes.None.ordinal()); 
		assert(tech.getID() != TechTypes.Unknown.ordinal()); 
		hasResearched[tech.getID()] = researched;
	}

	public void Capture(Player player)
	{
		for(int i=0; i<NUM_UPGRADES; i++)
		{
			upgradeLevel[i] = player.upgradeLevel(i);
		}

		for(int i=0; i<NUM_TECHS; i++)
		{
			hasResearched[i] = player.hasResearched(i);
		}
	}

	public int GetUpgradeLevel(UpgradeType upgrade)
	{ 
	    return upgradeLevel[upgrade.getID()]; 
	}

	public int GetUpgradeLevel(WeaponType upgrade)
	{ 
	    return upgradeLevel[upgrade.getUpgradeTypeID()]; 
	}
	public int GetUpgradeLevel(int upgrade)
	{ 
	    return upgradeLevel[upgrade]; 
	}
	public boolean HasUpgrade(UpgradeType upgrade) 
	{ 
	    return upgradeLevel[upgrade.getID()] > 0; 
	}

	public boolean HasResearched(TechType tech) 
	{ 
	    return hasResearched[tech.getID()]; 
	}

}
