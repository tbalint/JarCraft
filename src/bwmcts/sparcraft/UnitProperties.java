/**
* This file is based on and translated from the open source project: Sparcraft
* https://code.google.com/p/sparcraft/
* author of the source: David Churchill
**/
package bwmcts.sparcraft;

import javabot.JNIBWAPI;
import javabot.types.UnitType;
import javabot.types.UnitType.UnitTypes;
import javabot.types.UpgradeType;
import javabot.types.UpgradeType.UpgradeTypes;

public class UnitProperties {
	static UnitProperties[]	props=new UnitProperties[256];

	UnitType				type;
	int pixelShift=10;
	UpgradeType			capacityUpgrade;
	UpgradeType			extraArmorUpgrade;
	UpgradeType			maxEnergyUpgrade;
	UpgradeType			sightUpgrade;
	UpgradeType			speedUpgrade;

	int[] capacity=new int[2];
	int[] extraArmor=new int[2];
	int[] maxEnergy=new int[2];
	int[] sightRange=new int[2];
	int[] speed=new int[2];
	
	
	
	
	public UnitProperties(JNIBWAPI bwapi){ 
		capacityUpgrade=bwapi.getUpgradeType(UpgradeTypes.None.ordinal());
		maxEnergyUpgrade=bwapi.getUpgradeType(UpgradeTypes.None.ordinal());
		sightUpgrade=bwapi.getUpgradeType(UpgradeTypes.None.ordinal());
		extraArmorUpgrade=bwapi.getUpgradeType(UpgradeTypes.None.ordinal());
		speedUpgrade=bwapi.getUpgradeType(UpgradeTypes.None.ordinal());
		capacity[0]		= capacity[1]		= 0;
	}

	public void SetType(UnitType type)
	{
		if (type!=null){
			this.type		= type;
			
			maxEnergy[0]	= maxEnergy[1]		= type.getMaxEnergy();
			sightRange[0]	= sightRange[1]		= type.getSightRange() << pixelShift;
			extraArmor[0]	= extraArmor[1]		= 0;
			speed[0]		= speed[1]			= (int)((1 << pixelShift) * type.getTopSpeed());
		}
	}

	public void SetSpeedUpgrade(UpgradeType upgrade, double rate)
	{
		if (upgrade!=null){
			speedUpgrade				= upgrade;
			speed[1]					= (int)((1 << pixelShift) * rate);
		}
	}

	public void SetCapacityUpgrade(UpgradeType upgrade, int capacity0, int capacity1)
	{
		if (upgrade!=null){
			capacityUpgrade				= upgrade;
			capacity[0]					= capacity0;
			capacity[1]					= capacity1;
		}
	}

	public void SetEnergyUpgrade(UpgradeType upgrade)
	{
		if (upgrade!=null){
			maxEnergyUpgrade			= upgrade;
			maxEnergy[1]				= 250;
		}
	}

	public void SetSightUpgrade(UpgradeType upgrade, int range)
	{ 
		if (upgrade!=null){
			sightUpgrade				= upgrade;
			sightRange[1]				= (range << 5) << pixelShift;
		}
	}

	public void SetExtraArmorUpgrade(UpgradeType upgrade, int amount)
	{
		if (upgrade!=null){
			extraArmorUpgrade			= upgrade;
			extraArmor[1]				= amount;
		}
	}
	
	public static void Init(JNIBWAPI bwapi)
	{
		for (UnitTypes type : UnitTypes.values())
		{
			props[type.ordinal()]=new UnitProperties(bwapi);
			props[type.ordinal()].SetType(bwapi.getUnitType(type.ordinal()));
		}

		double standardSpeed=bwapi.getUnitType(UnitTypes.Terran_SCV.ordinal()).getTopSpeed();

		props[UnitTypes.Terran_Ghost.ordinal()            ].SetEnergyUpgrade(bwapi.getUpgradeType(UpgradeTypes.Moebius_Reactor.ordinal()));
		props[UnitTypes.Terran_Ghost.ordinal()            ].SetSightUpgrade(bwapi.getUpgradeType(UpgradeTypes.Ocular_Implants.ordinal()), 11);

		props[UnitTypes.Terran_Medic.ordinal()          ].SetEnergyUpgrade(bwapi.getUpgradeType(UpgradeTypes.Caduceus_Reactor.ordinal()));

		props[UnitTypes.Terran_Vulture.ordinal()          ].SetSpeedUpgrade(bwapi.getUpgradeType(UpgradeTypes.Ion_Thrusters.ordinal()),            standardSpeed * 1.881);

		props[UnitTypes.Terran_Wraith.ordinal()           ].SetEnergyUpgrade(bwapi.getUpgradeType(UpgradeTypes.Apollo_Reactor.ordinal()));

		props[UnitTypes.Terran_Battlecruiser.ordinal()    ].SetEnergyUpgrade(bwapi.getUpgradeType(UpgradeTypes.Colossus_Reactor.ordinal()));
		props[UnitTypes.Terran_Science_Vessel.ordinal()   ].SetEnergyUpgrade(bwapi.getUpgradeType(UpgradeTypes.Titan_Reactor.ordinal()));



		props[UnitTypes.Zerg_Zergling.ordinal()       ].SetSpeedUpgrade(bwapi.getUpgradeType(UpgradeTypes.Metabolic_Boost.ordinal()),			    standardSpeed * 1.615);

		props[UnitTypes.Zerg_Hydralisk.ordinal()    ].SetSpeedUpgrade(bwapi.getUpgradeType(UpgradeTypes.Muscular_Augments.ordinal()),		    standardSpeed * 1.105);

		props[UnitTypes.Zerg_Ultralisk.ordinal()      ].SetExtraArmorUpgrade(bwapi.getUpgradeType(UpgradeTypes.Chitinous_Plating.ordinal()),	    2);
		props[UnitTypes.Zerg_Ultralisk.ordinal()      ].SetSpeedUpgrade(bwapi.getUpgradeType(UpgradeTypes.Anabolic_Synthesis.ordinal()),		    standardSpeed * 1.556);

		props[UnitTypes.Zerg_Defiler.ordinal()      ].SetEnergyUpgrade(bwapi.getUpgradeType(UpgradeTypes.Metasynaptic_Node.ordinal()));

		props[UnitTypes.Zerg_Overlord.ordinal()       ].SetSightUpgrade(bwapi.getUpgradeType(UpgradeTypes.Antennae.ordinal()),					    11);
		props[UnitTypes.Zerg_Overlord.ordinal()     ].SetSpeedUpgrade(bwapi.getUpgradeType(UpgradeTypes.Pneumatized_Carapace.ordinal()),		    bwapi.getUnitType(UnitTypes.Protoss_Carrier.ordinal()).getTopSpeed());

		props[UnitTypes.Zerg_Queen.ordinal()          ].SetEnergyUpgrade(bwapi.getUpgradeType(UpgradeTypes.Gamete_Meiosis.ordinal()));



		props[UnitTypes.Protoss_Zealot.ordinal()      ].SetSpeedUpgrade(bwapi.getUpgradeType(UpgradeTypes.Leg_Enhancements.ordinal()),			    standardSpeed * 1.167);

		props[UnitTypes.Protoss_High_Templar.ordinal()].SetEnergyUpgrade(bwapi.getUpgradeType(UpgradeTypes.Khaydarin_Amulet.ordinal()));

		props[UnitTypes.Protoss_Reaver.ordinal()      ].SetCapacityUpgrade(bwapi.getUpgradeType(UpgradeTypes.Reaver_Capacity.ordinal()),		    5, 10);

		props[UnitTypes.Protoss_Dark_Archon.ordinal() ].SetEnergyUpgrade(bwapi.getUpgradeType(UpgradeTypes.Argus_Talisman.ordinal()));

		props[UnitTypes.Protoss_Observer.ordinal()    ].SetSightUpgrade(bwapi.getUpgradeType(UpgradeTypes.Sensor_Array.ordinal()),				    11);
		props[UnitTypes.Protoss_Observer.ordinal()    ].SetSpeedUpgrade(bwapi.getUpgradeType(UpgradeTypes.Gravitic_Boosters.ordinal()),		    bwapi.getUnitType(UnitTypes.Protoss_Corsair.ordinal()).getTopSpeed());

		props[UnitTypes.Protoss_Shuttle.ordinal()     ].SetSpeedUpgrade(bwapi.getUpgradeType(UpgradeTypes.Gravitic_Drive.ordinal()),			    bwapi.getUnitType(UnitTypes.Protoss_Corsair.ordinal()).getTopSpeed());

		props[UnitTypes.Protoss_Scout.ordinal()       ].SetSightUpgrade(bwapi.getUpgradeType(UpgradeTypes.Apial_Sensors.ordinal()),			    10);
		props[UnitTypes.Protoss_Scout.ordinal()       ].SetSpeedUpgrade(bwapi.getUpgradeType(UpgradeTypes.Gravitic_Thrusters.ordinal()),		    bwapi.getUnitType(UnitTypes.Protoss_Corsair.ordinal()).getTopSpeed());

		props[UnitTypes.Protoss_Corsair.ordinal()     ].SetEnergyUpgrade(bwapi.getUpgradeType(UpgradeTypes.Argus_Jewel.ordinal()));

	    props[UnitTypes.Protoss_Carrier.ordinal()     ].SetCapacityUpgrade(bwapi.getUpgradeType(UpgradeTypes.Carrier_Capacity.ordinal()),		    4, 8);

		props[UnitTypes.Protoss_Arbiter.ordinal()     ].SetEnergyUpgrade(bwapi.getUpgradeType(UpgradeTypes.Khaydarin_Core.ordinal()));
	}

	public static UnitProperties Get(UnitType type2) {
		// TODO Auto-generated method stub
		return props[type2.getID()];
	}
	
	public static UnitProperties Get(int unitTypeID) {
		return props[unitTypeID];
	}
	
	public int	GetArmor(PlayerProperties player){ return type.getArmor() + player.GetUpgradeLevel(type.getArmorUpgradeID()) + extraArmor[player.GetUpgradeLevel(extraArmorUpgrade)]; }
	public int	GetCapacity(PlayerProperties player) { return capacity[player.GetUpgradeLevel(capacityUpgrade)]; }
	public int	GetMaxEnergy(PlayerProperties player) { return maxEnergy[player.GetUpgradeLevel(maxEnergyUpgrade)]; }
	public int	GetSight(PlayerProperties player) { return sightRange[player.GetUpgradeLevel(sightUpgrade)]; }
	public int	GetSpeed(PlayerProperties player) { return speed[player.GetUpgradeLevel(speedUpgrade)]; }

	public WeaponProperties GetGroundWeapon() { return WeaponProperties.Get(type.getGroundWeaponID()); }
	public WeaponProperties GetAirWeapon() { return WeaponProperties.Get(type.getAirWeaponID()); }
}
