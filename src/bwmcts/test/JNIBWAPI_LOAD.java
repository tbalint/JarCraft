package bwmcts.test;

import javabot.BWAPIEventListener;
import javabot.JNIBWAPI;
import javabot.model.*;
import javabot.types.*;
import javabot.types.DamageType.DamageTypes;
import javabot.types.ExplosionType.ExplosionTypes;
import javabot.types.TechType.TechTypes;
import javabot.types.UnitType.UnitTypes;
import javabot.types.UpgradeType.UpgradeTypes;
import javabot.types.WeaponType.WeaponTypes;

import java.util.Collection;
import java.util.HashMap;

/**
 * JNI interface for the Brood War API.
 * <p/>
 * This focus of this interface is to provide the callback and game state query
 * functionality in BWAPI. Utility functions such as can buildHere have not
 * yet been implemented.
 * <p/>
 * Note: for thread safety and game state sanity, all native calls should be invoked from the callback methods.
 * <p/>
 * For BWAPI documentation see: http://code.google.com/p/bwapi/
 * <p/>
 * API Pages
 * Game: http://code.google.com/p/bwapi/wiki/Game
 * Unit: http://code.google.com/p/bwapi/wiki/Unit
 */
public class JNIBWAPI_LOAD extends JNIBWAPI {

	public JNIBWAPI_LOAD(BWAPIEventListener listener) {
		super(listener);

	}

	private HashMap<Integer, UnitType> unitTypes = new HashMap<Integer, UnitType>();
    private HashMap<Integer, TechType> techTypes = new HashMap<Integer, TechType>();
    private HashMap<Integer, UpgradeType> upgradeTypes = new HashMap<Integer, UpgradeType>();
    private HashMap<Integer, WeaponType> weaponTypes = new HashMap<Integer, WeaponType>();
    private HashMap<Integer, UnitSizeType> unitSizeTypes = new HashMap<Integer, UnitSizeType>();
    private HashMap<Integer, BulletType> bulletTypes = new HashMap<Integer, BulletType>();
    private HashMap<Integer, DamageType> damageTypes = new HashMap<Integer, DamageType>();
    private HashMap<Integer, ExplosionType> explosionTypes = new HashMap<Integer, ExplosionType>();
    private HashMap<Integer, UnitCommandType> unitCommandTypes = new HashMap<Integer, UnitCommandType>();
    private HashMap<Integer, OrderType> orderTypes = new HashMap<Integer, OrderType>();
    
    
    public int[] getUnitTypes(){
    	
    	
    	int[] data = new int[]{
    			0, Race.TERRAN.getID(), UnitTypes.Terran_Barracks.ordinal(), UpgradeTypes.Terran_Infantry_Armor.ordinal(),40,0,0,0,50,0,360,2,0,1,0,50,100, UnitSizeType.UnitSizes.Small.ordinal(),1,1,8,9,8,10,128,224, WeaponTypes.Gauss_Rifle.ordinal(),1, WeaponTypes.Gauss_Rifle.ordinal(),1,400,1,1,40,0,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			20, Race.TERRAN.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Terran_Infantry_Armor.ordinal(),200,0,0,3,50,0,1,0,0,1,0,0,200, UnitSizeType.UnitSizes.Small.ordinal(),1,1,8,9,8,10,128,224, WeaponTypes.Gauss_Rifle_Jim_Raynor.ordinal(),1, WeaponTypes.Gauss_Rifle_Jim_Raynor.ordinal(),1,400,1,1,40,0,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			1, Race.TERRAN.getID(), UnitTypes.Terran_Barracks.ordinal(), UpgradeTypes.Terran_Infantry_Armor.ordinal(),45,0,200,0,25,75,750,2,0,1,0,175,350, UnitSizeType.UnitSizes.Small.ordinal(),1,1,7,10,7,11,224,288, WeaponTypes.C_10_Canister_Rifle.ordinal(),1, WeaponTypes.C_10_Canister_Rifle.ordinal(),1,400,1,1,40,0,1,1,0,0,1,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,   0,0,
    			16, Race.TERRAN.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Terran_Infantry_Armor.ordinal(),250,0,250,3,50,150,1500,0,0,1,0,0,700, UnitSizeType.UnitSizes.Small.ordinal(),1,1,7,10,7,11,224,352, WeaponTypes.C_10_Canister_Rifle_Sarah_Kerrigan.ordinal(),1, WeaponTypes.C_10_Canister_Rifle_Sarah_Kerrigan.ordinal(),1,400,1,1,40,0,1,1,0,0,1,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,   0,0,
    			99, Race.TERRAN.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Terran_Infantry_Armor.ordinal(),200,0,250,2,200,75,1500,0,0,1,0,0,700, UnitSizeType.UnitSizes.Small.ordinal(),1,1,7,10,7,11,224,320, WeaponTypes.C_10_Canister_Rifle_Samir_Duran.ordinal(),1, WeaponTypes.C_10_Canister_Rifle_Samir_Duran.ordinal(),1,400,1,1,40,0,1,1,0,0,1,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,   0,0,
    			104, Race.ZERG.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Zerg_Carapace.ordinal(),300,0,250,3,200,75,1500,0,0,1,0,0,700, UnitSizeType.UnitSizes.Small.ordinal(),1,1,7,10,7,11,224,352, WeaponTypes.C_10_Canister_Rifle_Infested_Duran.ordinal(),1, WeaponTypes.C_10_Canister_Rifle_Infested_Duran.ordinal(),1,400,1,1,40,0,1,1,0,1,1,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,   0,0,
    			100, Race.TERRAN.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Terran_Infantry_Armor.ordinal(),250,0,250,3,200,75,1500,0,0,1,0,0,700, UnitSizeType.UnitSizes.Small.ordinal(),1,1,7,10,7,11,224,352, WeaponTypes.C_10_Canister_Rifle_Alexei_Stukov.ordinal(),1, WeaponTypes.C_10_Canister_Rifle_Alexei_Stukov.ordinal(),1,400,1,1,40,0,1,1,0,0,1,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,   0,0,
    			2, Race.TERRAN.getID(), UnitTypes.Terran_Factory.ordinal(), UpgradeTypes.Terran_Vehicle_Plating.ordinal(),80,0,0,0,75,0,450,4,0,2,0,75,150, UnitSizeType.UnitSizes.Medium.ordinal(),1,1,16,16,15,15,160,256, WeaponTypes.Fragmentation_Grenade.ordinal(),1, WeaponTypes.None.ordinal(),0,640,100,14569,40,0,1,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			19, Race.TERRAN.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Terran_Vehicle_Plating.ordinal(),300,0,0,3,150,0,900,0,0,2,0,0,300, UnitSizeType.UnitSizes.Medium.ordinal(),1,1,16,16,15,15,160,256, WeaponTypes.Fragmentation_Grenade_Jim_Raynor.ordinal(),1, WeaponTypes.None.ordinal(),0,640,100,14569,40,0,1,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			3, Race.TERRAN.getID(), UnitTypes.Terran_Factory.ordinal(), UpgradeTypes.Terran_Vehicle_Plating.ordinal(),125,0,0,1,100,50,600,4,0,2,0,200,400, UnitSizeType.UnitSizes.Large.ordinal(),1,1,16,16,15,15,192,256, WeaponTypes.Twin_Autocannons.ordinal(),1, WeaponTypes.Hellfire_Missile_Pack.ordinal(),1,457,1,1,17,0,1,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			17, Race.TERRAN.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Terran_Vehicle_Plating.ordinal(),300,0,0,3,200,100,1200,0,0,2,0,0,800, UnitSizeType.UnitSizes.Large.ordinal(),1,1,16,16,15,15,192,256, WeaponTypes.Twin_Autocannons_Alan_Schezar.ordinal(),1, WeaponTypes.Hellfire_Missile_Pack_Alan_Schezar.ordinal(),1,457,1,1,17,0,1,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			5, Race.TERRAN.getID(), UnitTypes.Terran_Factory.ordinal(), UpgradeTypes.Terran_Vehicle_Plating.ordinal(),150,0,0,1,150,100,750,4,0,4,0,350,700, UnitSizeType.UnitSizes.Large.ordinal(),1,1,16,16,15,15,256,320, WeaponTypes.Arclite_Cannon.ordinal(),1, WeaponTypes.None.ordinal(),   0,400,1,1,13,0,1,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			23, Race.TERRAN.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Terran_Vehicle_Plating.ordinal(),400,0,0,3,300,200,1500,0,0,4,0,0,1400, UnitSizeType.UnitSizes.Large.ordinal(),1,1,16,16,15,15,256,320, WeaponTypes.Arclite_Cannon_Edmund_Duke.ordinal(),1, WeaponTypes.None.ordinal(),0,400,1,1,13,0,1,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			7, Race.TERRAN.getID(), UnitTypes.Terran_Command_Center.ordinal(), UpgradeTypes.Terran_Infantry_Armor.ordinal(),60,0,0,0,50,0,300,2,0,1,0,50,100, UnitSizeType.UnitSizes.Small.ordinal(),1,1,11,11,11,11,32,224, WeaponTypes.Fusion_Cutter.ordinal(),1, WeaponTypes.None.ordinal(),0,492,67,12227,40,0,1,1,0,0,0,0,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,   0,0,
    			8, Race.TERRAN.getID(), UnitTypes.Terran_Starport.ordinal(), UpgradeTypes.Terran_Ship_Plating.ordinal(),120,0,200,0,150,100,900,4,0,255,0,400,800, UnitSizeType.UnitSizes.Large.ordinal(),1,1,19,15,18,14,160,224, WeaponTypes.Burst_Lasers.ordinal(),1, WeaponTypes.Gemini_Missiles.ordinal(),1,667,67,21745,40,0,1,1,1,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,   0,0,
    			21, Race.TERRAN.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Terran_Ship_Plating.ordinal(),500,0,250,4,400,200,1800,0,0,255,0,0,1600, UnitSizeType.UnitSizes.Large.ordinal(),1,1,19,15,18,14,160,224, WeaponTypes.Burst_Lasers_Tom_Kazansky.ordinal(),1, WeaponTypes.Gemini_Missiles_Tom_Kazansky.ordinal(),1,667,67,21745,40,0,1,1,1,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,   0,0,
    			9, Race.TERRAN.getID(), UnitTypes.Terran_Starport.ordinal(), UpgradeTypes.Terran_Ship_Plating.ordinal(),200,0,200,1,100,225,1200,4,0,255,0,625,1250, UnitSizeType.UnitSizes.Large.ordinal(),2,2,32,33,32,16,0,320, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,500,50,5120,40,0,0,1,1,0,1,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			22, Race.TERRAN.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Terran_Ship_Plating.ordinal(),800,0,250,4,50,600,2400,0,0,255,0,0,2500, UnitSizeType.UnitSizes.Large.ordinal(),2,2,32,33,32,16,0,320, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,500,50,5120,40,0,0,1,1,0,1,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			11, Race.TERRAN.getID(), UnitTypes.Terran_Starport.ordinal(), UpgradeTypes.Terran_Ship_Plating.ordinal(),150,0,0,1,100,100,750,4,0,255,8,300,600, UnitSizeType.UnitSizes.Large.ordinal(),2,2,24,16,24,20,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,547,17,37756,20,0,0,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			12, Race.TERRAN.getID(), UnitTypes.Terran_Starport.ordinal(), UpgradeTypes.Terran_Ship_Plating.ordinal(),500,0,200,3,400,300,2000,12,0,255,0,1200,2400, UnitSizeType.UnitSizes.Large.ordinal(),2,2,37,29,37,29,192,352, WeaponTypes.ATS_Laser_Battery.ordinal(),1, WeaponTypes.ATA_Laser_Battery.ordinal(),1,250,27,7585,20,0,1,1,1,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			27, Race.TERRAN.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Terran_Ship_Plating.ordinal(),1000,0,250,4,800,600,4800,0,0,255,0,0,4800, UnitSizeType.UnitSizes.Large.ordinal(),2,2,37,29,37,29,192,256, WeaponTypes.ATS_Laser_Battery_Hero.ordinal(),1, WeaponTypes.ATA_Laser_Battery_Hero.ordinal(),1,250,27,7585,20,0,1,1,1,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			28, Race.TERRAN.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Terran_Ship_Plating.ordinal(),850,0,250,4,800,600,2400,0,0,255,0,0,4800, UnitSizeType.UnitSizes.Large.ordinal(),2,2,37,29,37,29,192,352, WeaponTypes.ATS_Laser_Battery_Hyperion.ordinal(),1, WeaponTypes.ATA_Laser_Battery_Hyperion.ordinal(),1,250,27,7585,20,0,1,1,1,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			29, Race.TERRAN.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Terran_Ship_Plating.ordinal(),700,0,250,4,800,600,4800,0,0,255,0,0,4800, UnitSizeType.UnitSizes.Large.ordinal(),2,2,37,29,37,29,192,352, WeaponTypes.ATS_Laser_Battery_Hero.ordinal(),1, WeaponTypes.ATA_Laser_Battery_Hero.ordinal(),1,250,27,7585,20,0,1,1,1,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			102, Race.TERRAN.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Terran_Ship_Plating.ordinal(),700,0,250,4,800,600,4800,0,0,255,0,0,4800, UnitSizeType.UnitSizes.Large.ordinal(),2,2,37,29,37,29,192,352, WeaponTypes.ATS_Laser_Battery_Hero.ordinal(),1, WeaponTypes.ATA_Laser_Battery_Hero.ordinal(),1,250,27,7585,20,0,1,1,1,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			13, Race.TERRAN.getID(), UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),20,0,0,0,1,0,1,0,0,255,0,0,25, UnitSizeType.UnitSizes.Small.ordinal(),1,1,7,7,7,7,96,96, WeaponTypes.Spider_Mines.ordinal(),1, WeaponTypes.None.ordinal(),0,160,1,1,127,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			14, Race.TERRAN.getID(), UnitTypes.Terran_Nuclear_Silo.ordinal(), UpgradeTypes.None.ordinal(),100,0,0,0,200,200,1500,16,0,255,0,800,0,  UnitSizeType.UnitSizes.Independent.ordinal(),1,1,7,14,7,14,0,96, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,3333,33,1103213,127,0,0,1,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			30, Race.TERRAN.getID(), UnitTypes.Terran_Factory.ordinal(), UpgradeTypes.Terran_Vehicle_Plating.ordinal(),150,0,0,1,150,100,750,4,0,255,0,0,700, UnitSizeType.UnitSizes.Large.ordinal(),1,1,16,16,15,15,384,320, WeaponTypes.Arclite_Shock_Cannon.ordinal(),1, WeaponTypes.None.ordinal(),0,0,1,1,40,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			25, Race.TERRAN.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Terran_Vehicle_Plating.ordinal(),400,0,0,3,300,200,1500,0,0,255,0,0,1400, UnitSizeType.UnitSizes.Large.ordinal(),1,1,16,16,15,15,384,320, WeaponTypes.Arclite_Shock_Cannon_Edmund_Duke.ordinal(),1, WeaponTypes.None.ordinal(),0,0,1,1,40,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			32, Race.TERRAN.getID(), UnitTypes.Terran_Barracks.ordinal(), UpgradeTypes.Terran_Infantry_Armor.ordinal(),50,0,0,1,50,25,360,2,0,1,0,100,200, UnitSizeType.UnitSizes.Small.ordinal(),1,1,11,7,11,14,96,224, WeaponTypes.Flame_Thrower.ordinal(),3, WeaponTypes.None.ordinal(),0,4,1,1,40,0,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			10, Race.TERRAN.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Terran_Infantry_Armor.ordinal(),160,0,0,3,100,50,720,0,0,1,0,0,400, UnitSizeType.UnitSizes.Small.ordinal(),1,1,11,7,11,14,96,224, WeaponTypes.Flame_Thrower_Gui_Montag.ordinal(),3, WeaponTypes.None.ordinal(),0,4,1,1,40,0,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			33, Race.TERRAN.getID(), UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),0,0,0,0,0,0,1,0,0,255,0,0,0,  UnitSizeType.UnitSizes.Independent.ordinal(),1,1,13,13,13,17,0,320, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,   0,0,
    			34, Race.TERRAN.getID(), UnitTypes.Terran_Barracks.ordinal(), UpgradeTypes.Terran_Infantry_Armor.ordinal(),60,0,200,1,50,25,450,2,0,1,0,125,250, UnitSizeType.UnitSizes.Small.ordinal(),1,1,8,9,8,10,288,288, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,400,1,1,40,0,0,1,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			15, Race.TERRAN.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Terran_Infantry_Armor.ordinal(),40,0,0,0,0,0,1,0,0,1,0,0,10, UnitSizeType.UnitSizes.Small.ordinal(),1,1,8,9,8,10,0,224, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,400,1,1,40,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			35, Race.ZERG.getID(), UnitTypes.Zerg_Hatchery.ordinal(), UpgradeTypes.Zerg_Carapace.ordinal(),25,0,0,10,1,1,1,0,0,255,0,0,10, UnitSizeType.UnitSizes.Small.ordinal(),1,1,8,8,7,7,0,128, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,1,1,20,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			36, Race.ZERG.getID(), UnitTypes.Zerg_Larva.ordinal(), UpgradeTypes.Zerg_Carapace.ordinal(),200,0,0,10,1,1,1,0,0,255,0,0,25, UnitSizeType.UnitSizes.Medium.ordinal(),1,1,16,16,15,15,0,128, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			37, Race.ZERG.getID(), UnitTypes.Zerg_Larva.ordinal(), UpgradeTypes.Zerg_Carapace.ordinal(),35,0,0,0,50,0,420,1,0,1,0,25,50, UnitSizeType.UnitSizes.Small.ordinal(),1,1,8,4,7,11,96,160, WeaponTypes.Claws.ordinal(),1, WeaponTypes.None.ordinal(),0,549,1,1,27,0,1,1,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,   0,0,
    			54, Race.ZERG.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Zerg_Carapace.ordinal(),120,0,0,3,100,0,840,0,0,1,0,0,100, UnitSizeType.UnitSizes.Small.ordinal(),1,1,8,4,7,11,96,160, WeaponTypes.Claws_Devouring_One.ordinal(),1, WeaponTypes.None.ordinal(),0,549,1,1,27,0,1,1,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,   0,0,
    			51, Race.ZERG.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Zerg_Carapace.ordinal(),400,0,250,2,200,300,1500,0,0,1,0,0,4000, UnitSizeType.UnitSizes.Small.ordinal(),1,1,7,10,7,11,96,288, WeaponTypes.Claws_Infested_Kerrigan.ordinal(),1, WeaponTypes.None.ordinal(),0,400,1,1,40,0,1,1,0,1,1,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,   0,0,
    			38, Race.ZERG.getID(), UnitTypes.Zerg_Larva.ordinal(), UpgradeTypes.Zerg_Carapace.ordinal(),80,0,0,0,75,25,420,2,0,2,0,125,350, UnitSizeType.UnitSizes.Medium.ordinal(),1,1,10,10,10,12,128,192, WeaponTypes.Needle_Spines.ordinal(),1, WeaponTypes.Needle_Spines.ordinal(),1,366,1,1,27,0,1,1,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,   0,0,
    			53, Race.ZERG.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Zerg_Carapace.ordinal(),160,0,0,2,150,50,780,0,0,2,0,0,500, UnitSizeType.UnitSizes.Medium.ordinal(),1,1,10,10,10,12,128,256, WeaponTypes.Needle_Spines_Hunter_Killer.ordinal(),1, WeaponTypes.Needle_Spines_Hunter_Killer.ordinal(),1,366,1,1,27,0,1,1,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,   0,0,
    			39, Race.ZERG.getID(), UnitTypes.Zerg_Larva.ordinal(), UpgradeTypes.Zerg_Carapace.ordinal(),400,0,0,1,200,200,900,8,0,4,0,650,1300, UnitSizeType.UnitSizes.Large.ordinal(),2,2,19,16,18,15,96,224, WeaponTypes.Kaiser_Blades.ordinal(),1, WeaponTypes.None.ordinal(),0,512,1,1,40,0,1,1,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			48, Race.ZERG.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Zerg_Carapace.ordinal(),800,0,0,4,400,400,1800,0,0,4,0,0,2600, UnitSizeType.UnitSizes.Large.ordinal(),2,2,19,16,18,15,96,224, WeaponTypes.Kaiser_Blades_Torrasque.ordinal(),1, WeaponTypes.None.ordinal(),0,512,1,1,40,0,1,1,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			40, Race.ZERG.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Zerg_Carapace.ordinal(),30,0,0,0,1,1,1,0,0,1,0,0,25, UnitSizeType.UnitSizes.Small.ordinal(),1,1,9,9,9,9,96,160, WeaponTypes.Toxic_Spores.ordinal(),1, WeaponTypes.None.ordinal(),0,600,1,1,27,0,1,1,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			41, Race.ZERG.getID(), UnitTypes.Zerg_Larva.ordinal(), UpgradeTypes.Zerg_Carapace.ordinal(),40,0,0,0,50,0,300,2,0,1,0,50,100, UnitSizeType.UnitSizes.Small.ordinal(),1,1,11,11,11,11,32,224, WeaponTypes.Spines.ordinal(),1, WeaponTypes.None.ordinal(),0,492,67,12227,40,0,1,1,0,1,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,   0,0,
    			42, Race.ZERG.getID(), UnitTypes.Zerg_Larva.ordinal(), UpgradeTypes.Zerg_Flyer_Carapace.ordinal(),200,0,0,0,100,0,600,0,16,255,8,100,200, UnitSizeType.UnitSizes.Large.ordinal(),2,2,25,25,24,24,0,288, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,83,27,840,20,0,0,1,1,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			57, Race.ZERG.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Zerg_Flyer_Carapace.ordinal(),1000,0,0,4,200,0,1200,0,60,255,8,0,400, UnitSizeType.UnitSizes.Large.ordinal(),2,2,25,25,24,24,0,352, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,83,27,840,20,0,0,1,1,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			43, Race.ZERG.getID(), UnitTypes.Zerg_Larva.ordinal(), UpgradeTypes.Zerg_Flyer_Carapace.ordinal(),120,0,0,0,100,100,600,4,0,255,0,300,600, UnitSizeType.UnitSizes.Small.ordinal(),2,2,22,22,21,21,96,224, WeaponTypes.Glave_Wurm.ordinal(),1, WeaponTypes.Glave_Wurm.ordinal(),1,667,67,21745,40,0,1,1,1,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			55, Race.ZERG.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Zerg_Flyer_Carapace.ordinal(),300,0,0,3,200,200,1200,0,0,255,0,0,1200, UnitSizeType.UnitSizes.Small.ordinal(),2,2,22,22,21,21,96,224, WeaponTypes.Glave_Wurm_Kukulza.ordinal(),1, WeaponTypes.Glave_Wurm_Kukulza.ordinal(),1,667,67,21745,40,0,1,1,1,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			44, Race.ZERG.getID(), UnitTypes.Zerg_Mutalisk.ordinal(), UpgradeTypes.Zerg_Flyer_Carapace.ordinal(),150,0,0,2,50,100,600,4,0,255,0,550,1100, UnitSizeType.UnitSizes.Large.ordinal(),2,2,22,22,21,21,256,352, WeaponTypes.Acid_Spore.ordinal(),1, WeaponTypes.None.ordinal(),0,250,27,7585,20,0,1,1,1,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			56, Race.ZERG.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Zerg_Flyer_Carapace.ordinal(),400,0,0,4,100,200,1200,0,0,255,0,0,2200, UnitSizeType.UnitSizes.Large.ordinal(),2,2,22,22,21,21,256,352, WeaponTypes.Acid_Spore_Kukulza.ordinal(),1, WeaponTypes.None.ordinal(),0,250,27,7585,20,0,1,1,1,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			45, Race.ZERG.getID(), UnitTypes.Zerg_Larva.ordinal(), UpgradeTypes.Zerg_Flyer_Carapace.ordinal(),120,0,200,0,100,100,750,4,0,255,0,400,800, UnitSizeType.UnitSizes.Medium.ordinal(),2,2,24,24,23,23,256,320, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,667,67,21745,40,0,0,1,1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			49, Race.ZERG.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Zerg_Flyer_Carapace.ordinal(),300,0,250,3,200,300,1500,0,0,255,0,0,1600, UnitSizeType.UnitSizes.Medium.ordinal(),2,2,24,24,23,23,256,320, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,667,67,21745,40,0,0,1,1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			46, Race.ZERG.getID(), UnitTypes.Zerg_Larva.ordinal(), UpgradeTypes.Zerg_Carapace.ordinal(),80,0,200,1,50,150,750,4,0,2,0,225,450, UnitSizeType.UnitSizes.Medium.ordinal(),1,1,13,12,13,12,0,320, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,400,1,1,27,0,0,1,0,1,1,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,   0,0,
    			52, Race.ZERG.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Zerg_Carapace.ordinal(),250,0,250,3,50,200,1500,0,0,2,0,0,900, UnitSizeType.UnitSizes.Medium.ordinal(),1,1,13,12,13,12,0,320, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,400,1,1,27,0,0,1,0,1,1,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,   0,0,
    			47, Race.ZERG.getID(), UnitTypes.Zerg_Larva.ordinal(), UpgradeTypes.Zerg_Flyer_Carapace.ordinal(),25,0,0,0,25,75,450,1,0,255,0,100,200, UnitSizeType.UnitSizes.Small.ordinal(),1,1,12,12,11,11,96,160, WeaponTypes.None.ordinal(),0, WeaponTypes.Suicide_Scourge.ordinal(),1,667,107,13616,40,0,1,1,1,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			50, Race.ZERG.getID(), UnitTypes.Zerg_Infested_Command_Center.ordinal(), UpgradeTypes.Zerg_Carapace.ordinal(),60,0,0,0,100,50,600,2,0,1,0,200,400, UnitSizeType.UnitSizes.Small.ordinal(),1,1,8,9,8,10,96,160, WeaponTypes.Suicide_Infested_Terran.ordinal(),1, WeaponTypes.None.ordinal(),0,582,1,1,40,0,1,1,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,   0,0,
    			58, Race.TERRAN.getID(), UnitTypes.Terran_Starport.ordinal(), UpgradeTypes.Terran_Ship_Plating.ordinal(),200,0,0,2,250,125,750,6,0,255,0,400,800, UnitSizeType.UnitSizes.Large.ordinal(),2,2,24,16,24,20,192,256, WeaponTypes.None.ordinal(),0, WeaponTypes.Halo_Rockets.ordinal(),4,660,65,21901,30,0,1,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			59, Race.ZERG.getID(), UnitTypes.Zerg_Mutalisk.ordinal(), UpgradeTypes.Zerg_Carapace.ordinal(),200,0,0,0,1,1,1,0,0,255,0,0,1100, UnitSizeType.UnitSizes.Large.ordinal(),1,1,16,16,15,15,0,128, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			60, Race.PROTOSS.getID(), UnitTypes.Protoss_Stargate.ordinal(), UpgradeTypes.Protoss_Air_Armor.ordinal(),100,80,200,1,150,100,600,4,0,255,0,350,700, UnitSizeType.UnitSizes.Medium.ordinal(),1,1,18,16,17,15,288,288, WeaponTypes.None.ordinal(),0, WeaponTypes.Neutron_Flare.ordinal(),1,667,67,17067,30,0,1,1,1,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			98, Race.PROTOSS.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Protoss_Air_Armor.ordinal(),100,60,250,0,150,100,750,0,0,255,0,0,1300, UnitSizeType.UnitSizes.Medium.ordinal(),1,1,18,16,17,15,288,288, WeaponTypes.None.ordinal(),0, WeaponTypes.Neutron_Flare.ordinal(),1,667,67,17067,30,0,1,1,1,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			61, Race.PROTOSS.getID(), UnitTypes.Protoss_Gateway.ordinal(), UpgradeTypes.Protoss_Ground_Armor.ordinal(),80,40,0,1,125,100,750,4,0,2,0,325,650, UnitSizeType.UnitSizes.Small.ordinal(),1,1,12,6,11,19,96,224, WeaponTypes.Warp_Blades.ordinal(),1, WeaponTypes.None.ordinal(),0,492,27,13474,40,0,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			74, Race.PROTOSS.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Protoss_Ground_Armor.ordinal(),40,80,0,0,150,150,750,1,0,2,0,0,400, UnitSizeType.UnitSizes.Small.ordinal(),1,1,12,6,11,19,96,224, WeaponTypes.Warp_Blades_Hero.ordinal(),1, WeaponTypes.None.ordinal(),0,492,27,13474,40,0,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			75, Race.PROTOSS.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Protoss_Ground_Armor.ordinal(),60,400,0,0,100,300,1500,0,0,2,0,0,800, UnitSizeType.UnitSizes.Small.ordinal(),1,1,12,6,11,19,96,224, WeaponTypes.Warp_Blades_Zeratul.ordinal(),1, WeaponTypes.None.ordinal(),0,492,27,13474,40,0,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			62, Race.ZERG.getID(), UnitTypes.Zerg_Mutalisk.ordinal(), UpgradeTypes.Zerg_Flyer_Carapace.ordinal(),250,0,0,2,150,50,600,4,0,255,0,550,1100, UnitSizeType.UnitSizes.Large.ordinal(),2,2,22,22,21,21,224,320, WeaponTypes.None.ordinal(),0, WeaponTypes.Corrosive_Acid.ordinal(),1,500,48,17067,30,0,1,1,1,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			63, Race.PROTOSS.getID(), UnitTypes.Protoss_Dark_Templar.ordinal(), UpgradeTypes.Protoss_Ground_Armor.ordinal(),25,200,200,1,0,0,300,8,0,4,0,650,1300, UnitSizeType.UnitSizes.Large.ordinal(),1,1,16,16,15,15,224,320, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,492,160,5120,40,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			64, Race.PROTOSS.getID(), UnitTypes.Protoss_Nexus.ordinal(), UpgradeTypes.Protoss_Ground_Armor.ordinal(),20,20,0,0,50,0,300,2,0,1,0,50,100, UnitSizeType.UnitSizes.Small.ordinal(),1,1,11,11,11,11,32,256, WeaponTypes.Particle_Beam.ordinal(),1, WeaponTypes.None.ordinal(),0,492,67,12227,40,0,1,1,0,0,0,0,0,1,1,0,0,0,1,0,0,0,0,0,0,0,0,   0,0,
    			65, Race.PROTOSS.getID(), UnitTypes.Protoss_Gateway.ordinal(), UpgradeTypes.Protoss_Ground_Armor.ordinal(),100,60,0,1,100,0,600,4,0,2,0,100,200, UnitSizeType.UnitSizes.Small.ordinal(),1,1,11,5,11,13,96,224, WeaponTypes.Psi_Blades.ordinal(),2, WeaponTypes.None.ordinal(),0,400,1,1,40,0,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			77, Race.PROTOSS.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Protoss_Ground_Armor.ordinal(),240,240,0,2,200,0,1200,0,0,2,0,0,400, UnitSizeType.UnitSizes.Small.ordinal(),1,1,11,5,11,13,96,224, WeaponTypes.Psi_Blades_Fenix.ordinal(),2, WeaponTypes.None.ordinal(),0,400,1,1,40,0,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			66, Race.PROTOSS.getID(), UnitTypes.Protoss_Gateway.ordinal(), UpgradeTypes.Protoss_Ground_Armor.ordinal(),100,80,0,1,125,50,750,4,0,4,0,250,500, UnitSizeType.UnitSizes.Large.ordinal(),1,1,15,15,16,16,128,256, WeaponTypes.Phase_Disruptor.ordinal(),1, WeaponTypes.Phase_Disruptor.ordinal(),1,500,1,1,40,0,1,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			78, Race.PROTOSS.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Protoss_Ground_Armor.ordinal(),240,240,0,3,300,100,1500,0,0,4,0,0,1000, UnitSizeType.UnitSizes.Large.ordinal(),1,1,15,15,16,16,128,256, WeaponTypes.Phase_Disruptor_Fenix.ordinal(),1, WeaponTypes.Phase_Disruptor_Fenix.ordinal(),1,500,1,1,40,0,1,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			67, Race.PROTOSS.getID(), UnitTypes.Protoss_Gateway.ordinal(), UpgradeTypes.Protoss_Ground_Armor.ordinal(),40,40,200,0,50,150,750,4,0,2,0,350,700, UnitSizeType.UnitSizes.Small.ordinal(),1,1,12,10,11,13,96,224, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,320,27,13474,40,0,0,1,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			79, Race.PROTOSS.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Protoss_Ground_Armor.ordinal(),80,300,250,2,100,300,1500,0,0,2,0,0,1400, UnitSizeType.UnitSizes.Small.ordinal(),1,1,12,10,11,13,96,224, WeaponTypes.Psi_Assault.ordinal(),1, WeaponTypes.None.ordinal(),0,320,27,13474,40,0,1,1,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			87, Race.PROTOSS.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Protoss_Ground_Armor.ordinal(),80,300,250,2,100,300,1500,0,0,2,0,0,1400, UnitSizeType.UnitSizes.Small.ordinal(),1,1,12,10,11,13,96,224, WeaponTypes.Psi_Assault.ordinal(),1, WeaponTypes.None.ordinal(),0,320,27,13474,40,0,1,1,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			68, Race.PROTOSS.getID(), UnitTypes.Protoss_High_Templar.ordinal(), UpgradeTypes.Protoss_Ground_Armor.ordinal(),10,350,0,0,0,0,300,8,0,4,0,700,1400, UnitSizeType.UnitSizes.Large.ordinal(),1,1,16,16,15,15,96,256, WeaponTypes.Psionic_Shockwave.ordinal(),1, WeaponTypes.Psionic_Shockwave.ordinal(),1,492,160,5120,40,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			76, Race.PROTOSS.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Protoss_Ground_Armor.ordinal(),100,800,0,3,0,0,600,0,0,4,0,0,2800, UnitSizeType.UnitSizes.Large.ordinal(),1,1,16,16,15,15,96,256, WeaponTypes.Psionic_Shockwave_Tassadar_Zeratul_Archon.ordinal(),1, WeaponTypes.Psionic_Shockwave_Tassadar_Zeratul_Archon.ordinal(),1,492,160,5120,40,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			69, Race.PROTOSS.getID(), UnitTypes.Protoss_Robotics_Facility.ordinal(), UpgradeTypes.Protoss_Air_Armor.ordinal(),80,60,0,1,200,0,900,4,0,255,8,200,400, UnitSizeType.UnitSizes.Large.ordinal(),2,1,20,16,19,15,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,443,17,37756,20,0,0,1,1,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			70, Race.PROTOSS.getID(), UnitTypes.Protoss_Stargate.ordinal(), UpgradeTypes.Protoss_Air_Armor.ordinal(),150,100,0,0,275,125,1200,6,0,255,0,650,1300, UnitSizeType.UnitSizes.Large.ordinal(),2,1,18,16,17,15,128,256, WeaponTypes.Dual_Photon_Blasters.ordinal(),1, WeaponTypes.Anti_Matter_Missiles.ordinal(),1,500,48,17067,30,0,1,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			80, Race.PROTOSS.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Protoss_Air_Armor.ordinal(),400,400,0,3,600,300,2400,0,0,255,0,0,2600, UnitSizeType.UnitSizes.Large.ordinal(),2,1,18,16,17,15,128,320, WeaponTypes.Dual_Photon_Blasters_Mojo.ordinal(),1, WeaponTypes.Anti_Matter_Missiles_Mojo.ordinal(),1,500,48,17067,30,0,1,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			88, Race.PROTOSS.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Protoss_Air_Armor.ordinal(),250,250,0,3,600,300,2400,0,0,255,0,0,2400, UnitSizeType.UnitSizes.Large.ordinal(),2,1,18,16,17,15,128,320, WeaponTypes.Dual_Photon_Blasters_Artanis.ordinal(),1, WeaponTypes.Anti_Matter_Missiles_Artanis.ordinal(),1,500,48,17067,30,0,1,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			71, Race.PROTOSS.getID(), UnitTypes.Protoss_Stargate.ordinal(), UpgradeTypes.Protoss_Air_Armor.ordinal(),200,150,200,1,100,350,2400,8,0,255,0,1025,2050, UnitSizeType.UnitSizes.Large.ordinal(),2,2,22,22,21,21,160,288, WeaponTypes.Phase_Disruptor_Cannon.ordinal(),1, WeaponTypes.Phase_Disruptor_Cannon.ordinal(),1,500,33,24824,40,0,1,1,1,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			86, Race.PROTOSS.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Protoss_Air_Armor.ordinal(),600,500,250,3,50,1000,4800,0,0,255,0,0,4100, UnitSizeType.UnitSizes.Large.ordinal(),2,2,22,22,21,21,160,288, WeaponTypes.Phase_Disruptor_Cannon_Danimoth.ordinal(),1, WeaponTypes.Phase_Disruptor_Cannon_Danimoth.ordinal(),1,500,33,24824,40,0,1,1,1,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			72, Race.PROTOSS.getID(), UnitTypes.Protoss_Stargate.ordinal(), UpgradeTypes.Protoss_Air_Armor.ordinal(),300,150,0,4,350,250,2100,12,0,255,0,950,1900, UnitSizeType.UnitSizes.Large.ordinal(),2,2,32,32,31,31,256,352, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,333,27,13474,20,1,0,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			82, Race.PROTOSS.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Protoss_Air_Armor.ordinal(),800,500,0,4,700,600,4200,0,0,255,0,0,3800, UnitSizeType.UnitSizes.Large.ordinal(),2,2,32,32,31,31,256,288, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,333,27,13474,20,1,0,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			73, Race.PROTOSS.getID(), UnitTypes.Protoss_Carrier.ordinal(), UpgradeTypes.Protoss_Air_Armor.ordinal(),40,40,0,0,25,0,300,0,0,255,0,30,60, UnitSizeType.UnitSizes.Small.ordinal(),1,1,8,8,7,7,128,192, WeaponTypes.Pulse_Cannon.ordinal(),1, WeaponTypes.Pulse_Cannon.ordinal(),1,1333,427,13640,40,0,1,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			83, Race.PROTOSS.getID(), UnitTypes.Protoss_Robotics_Facility.ordinal(), UpgradeTypes.Protoss_Ground_Armor.ordinal(),100,80,0,0,200,100,1050,8,0,4,0,400,800, UnitSizeType.UnitSizes.Large.ordinal(),1,1,16,16,15,15,256,320, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,178,1,1,20,1,0,1,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			81, Race.PROTOSS.getID(), UnitTypes.None.ordinal(), UpgradeTypes.Protoss_Ground_Armor.ordinal(),200,400,0,3,400,200,1800,0,0,4,0,0,1600, UnitSizeType.UnitSizes.Large.ordinal(),1,1,16,16,15,15,256,320, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,178,1,1,20,1,0,1,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			84, Race.PROTOSS.getID(), UnitTypes.Protoss_Robotics_Facility.ordinal(), UpgradeTypes.Protoss_Air_Armor.ordinal(),40,20,0,0,25,75,600,2,0,255,0,225,450, UnitSizeType.UnitSizes.Small.ordinal(),1,1,16,16,15,15,0,288, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,333,27,13474,20,0,0,1,1,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			85, Race.PROTOSS.getID(), UnitTypes.Protoss_Reaver.ordinal(), UpgradeTypes.Protoss_Ground_Armor.ordinal(),20,10,0,0,15,0,105,0,0,255,0,0,0, UnitSizeType.UnitSizes.Small.ordinal(),1,1,2,2,2,2,128,160, WeaponTypes.Scarab.ordinal(),1, WeaponTypes.None.ordinal(),0,1600,1,1,27,0,1,1,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			89, 0, UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),60,0,0,0,1,1,1,0,0,255,0,0,10, UnitSizeType.UnitSizes.Small.ordinal(),1,1,16,16,15,15,0,224, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,400,1,1,27,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			90, 0, UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),60,0,0,0,1,1,1,0,0,255,0,0,10, UnitSizeType.UnitSizes.Small.ordinal(),1,1,16,16,15,15,0,224, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,400,1,1,27,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			93, 0, UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),60,0,0,0,1,1,1,0,0,255,0,0,10, UnitSizeType.UnitSizes.Small.ordinal(),1,1,16,16,15,15,0,224, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,400,1,1,27,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			94, 0, UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),60,0,0,0,1,1,1,0,0,255,0,0,10, UnitSizeType.UnitSizes.Small.ordinal(),1,1,16,16,15,15,0,224, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,500,16,51200,14,0,0,1,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			95, 0, UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),60,0,0,0,1,1,1,0,0,255,0,0,10, UnitSizeType.UnitSizes.Small.ordinal(),1,1,16,16,15,15,0,224, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,400,1,1,27,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			96, 0, UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),60,0,0,0,1,1,1,0,0,255,0,0,10, UnitSizeType.UnitSizes.Small.ordinal(),1,1,16,16,15,15,0,224, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,400,1,1,27,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			97, Race.ZERG.getID(), UnitTypes.Zerg_Hydralisk.ordinal(), UpgradeTypes.Zerg_Carapace.ordinal(),200,0,0,10,1,1,1,0,0,255,0,0,500, UnitSizeType.UnitSizes.Medium.ordinal(),1,1,16,16,15,15,0,128, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			103, Race.ZERG.getID(), UnitTypes.Zerg_Hydralisk.ordinal(), UpgradeTypes.Zerg_Carapace.ordinal(),125,0,0,1,50,100,600,4,0,4,0,250,500, UnitSizeType.UnitSizes.Medium.ordinal(),1,1,15,15,16,16,192,256, WeaponTypes.Subterranean_Spines.ordinal(),1, WeaponTypes.None.ordinal(),0,582,1,1,40,0,1,1,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,   0,0,
    			105, Race.PROTOSS.getID(), UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),800,0,0,0,250,250,2400,0,0,255,0,0,0,  UnitSizeType.UnitSizes.Independent.ordinal(),4,3,60,40,59,39,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,   0,0,
    			106, Race.TERRAN.getID(), UnitTypes.Terran_SCV.ordinal(), UpgradeTypes.None.ordinal(),1500,0,0,1,400,0,1800,0,20,255,0,400,1200, UnitSizeType.UnitSizes.Large.ordinal(),4,3,58,41,58,41,0,320, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,100,33,2763,27,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,1,0,   0,0,
    			107, Race.TERRAN.getID(), UnitTypes.Terran_Command_Center.ordinal(), UpgradeTypes.None.ordinal(),500,0,200,1,50,50,600,0,0,255,0,75,225, UnitSizeType.UnitSizes.Large.ordinal(),2,2,37,16,31,25,0,320, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,1,1,0,0,   0,0,
    			108, Race.TERRAN.getID(), UnitTypes.Terran_Command_Center.ordinal(), UpgradeTypes.None.ordinal(),600,0,0,1,100,100,1200,0,0,255,0,75,225, UnitSizeType.UnitSizes.Large.ordinal(),2,2,37,16,31,25,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,1,0,0,   0,0,
    			109, Race.TERRAN.getID(), UnitTypes.Terran_SCV.ordinal(), UpgradeTypes.None.ordinal(),500,0,0,1,100,0,600,0,16,255,0,50,150, UnitSizeType.UnitSizes.Large.ordinal(),3,2,38,22,38,26,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,   0,0,
    			110, Race.TERRAN.getID(), UnitTypes.Terran_SCV.ordinal(), UpgradeTypes.None.ordinal(),750,0,0,1,100,0,600,0,0,255,0,50,150, UnitSizeType.UnitSizes.Large.ordinal(),4,2,56,32,56,31,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,33,2763,27,0,0,0,0,0,0,0,0,1,0,0,1,1,0,0,0,0,0,1,0,0,0,   0,0,
    			111, Race.TERRAN.getID(), UnitTypes.Terran_SCV.ordinal(), UpgradeTypes.None.ordinal(),1000,0,0,1,150,0,1200,0,0,255,0,75,225, UnitSizeType.UnitSizes.Large.ordinal(),4,3,48,40,56,32,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,100,33,2763,27,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,1,0,   0,0,
    			112, Race.TERRAN.getID(), UnitTypes.Terran_SCV.ordinal(), UpgradeTypes.None.ordinal(),600,0,0,1,150,0,1200,0,0,255,0,100,300, UnitSizeType.UnitSizes.Large.ordinal(),3,2,40,32,44,24,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,   0,0,
    			113, Race.TERRAN.getID(), UnitTypes.Terran_SCV.ordinal(), UpgradeTypes.None.ordinal(),1250,0,0,1,200,100,1200,0,0,255,0,200,600, UnitSizeType.UnitSizes.Large.ordinal(),4,3,56,40,56,40,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,100,33,2763,27,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,1,0,   0,0,
    			114, Race.TERRAN.getID(), UnitTypes.Terran_SCV.ordinal(), UpgradeTypes.None.ordinal(),1300,0,0,1,150,100,1050,0,0,255,0,200,600, UnitSizeType.UnitSizes.Large.ordinal(),4,3,48,40,48,38,0,320, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,100,33,2763,27,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,1,0,   0,0,
    			115, Race.TERRAN.getID(), UnitTypes.Terran_Starport.ordinal(), UpgradeTypes.None.ordinal(),500,0,0,1,50,50,600,0,0,255,0,100,300, UnitSizeType.UnitSizes.Large.ordinal(),2,2,47,24,28,22,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,1,0,0,   0,0,
    			116, Race.TERRAN.getID(), UnitTypes.Terran_SCV.ordinal(), UpgradeTypes.None.ordinal(),850,0,0,1,100,150,900,0,0,255,0,275,825, UnitSizeType.UnitSizes.Large.ordinal(),4,3,48,38,48,38,0,320, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,100,33,2763,27,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,1,0,   0,0,
    			117, Race.TERRAN.getID(), UnitTypes.Terran_Science_Facility.ordinal(), UpgradeTypes.None.ordinal(),750,0,0,1,50,50,600,0,0,255,0,75,225, UnitSizeType.UnitSizes.Large.ordinal(),2,2,47,24,28,22,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,1,0,0,   0,0,
    			118, Race.TERRAN.getID(), UnitTypes.Terran_Science_Facility.ordinal(), UpgradeTypes.None.ordinal(),600,0,0,1,50,50,600,0,0,255,0,75,225, UnitSizeType.UnitSizes.Large.ordinal(),2,2,47,24,28,22,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,1,0,0,   0,0,
    			120, Race.TERRAN.getID(), UnitTypes.Terran_Factory.ordinal(), UpgradeTypes.None.ordinal(),750,0,0,1,50,50,600,0,0,255,0,75,225, UnitSizeType.UnitSizes.Large.ordinal(),2,2,39,24,31,24,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,1,0,0,   0,0,
    			122, Race.TERRAN.getID(), UnitTypes.Terran_SCV.ordinal(), UpgradeTypes.None.ordinal(),850,0,0,1,125,0,900,0,0,255,0,65,195, UnitSizeType.UnitSizes.Large.ordinal(),4,3,48,32,48,28,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,100,33,2763,27,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,1,0,   0,0,
    			123, Race.TERRAN.getID(), UnitTypes.Terran_SCV.ordinal(), UpgradeTypes.None.ordinal(),750,0,0,1,100,50,1200,0,0,255,0,100,300, UnitSizeType.UnitSizes.Large.ordinal(),3,2,48,32,47,22,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,   0,0,
    			124, Race.TERRAN.getID(), UnitTypes.Terran_SCV.ordinal(), UpgradeTypes.None.ordinal(),200,0,0,0,75,0,450,0,0,255,0,50,150, UnitSizeType.UnitSizes.Large.ordinal(),2,2,16,32,16,16,224,352, WeaponTypes.None.ordinal(),0, WeaponTypes.Longbolt_Missile.ordinal(),1,0,0,0,40,0,1,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,1,0,0,0,   0,0,
    			125, Race.TERRAN.getID(), UnitTypes.Terran_SCV.ordinal(), UpgradeTypes.None.ordinal(),350,0,0,1,100,0,450,0,0,255,4,50,150, UnitSizeType.UnitSizes.Large.ordinal(),3,2,32,24,32,16,0,320, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,33,2763,27,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,   0,0,
    			126, Race.TERRAN.getID(), UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),700,0,0,1,800,600,4800,0,0,255,0,0,5000, UnitSizeType.UnitSizes.Large.ordinal(),3,2,48,32,47,31,0,320, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,   0,0,
    			127, Race.TERRAN.getID(), UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),2000,0,0,1,200,0,900,0,0,255,0,0,5000, UnitSizeType.UnitSizes.Large.ordinal(),3,2,48,32,47,31,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,   0,0,
    			130, Race.ZERG.getID(), UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),1500,0,0,1,1,1,1800,0,0,255,0,300,900, UnitSizeType.UnitSizes.Large.ordinal(),4,3,58,41,58,41,0,320, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,100,33,2763,27,1,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,1,0,   0,0,
    			131, Race.ZERG.getID(), UnitTypes.Zerg_Drone.ordinal(), UpgradeTypes.None.ordinal(),1250,0,0,1,300,0,1800,0,2,255,0,300,900, UnitSizeType.UnitSizes.Large.ordinal(),4,3,49,32,49,32,0,288, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,1,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,   0,0,
    			132, Race.ZERG.getID(), UnitTypes.Zerg_Hatchery.ordinal(), UpgradeTypes.None.ordinal(),1800,0,0,1,150,100,1500,0,2,255,0,100,1200, UnitSizeType.UnitSizes.Large.ordinal(),4,3,49,32,49,32,0,320, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,1,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,   0,0,
    			133, Race.ZERG.getID(), UnitTypes.Zerg_Lair.ordinal(), UpgradeTypes.None.ordinal(),2500,0,0,1,200,150,1800,0,2,255,0,100,1500, UnitSizeType.UnitSizes.Large.ordinal(),4,3,49,32,49,32,0,352, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,1,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,   0,0,
    			134, Race.ZERG.getID(), UnitTypes.Zerg_Drone.ordinal(), UpgradeTypes.None.ordinal(),250,0,0,1,150,0,600,0,0,255,0,75,225, UnitSizeType.UnitSizes.Large.ordinal(),2,2,32,32,31,31,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,1,0,0,1,0,0,0,   0,0,
    			135, Race.ZERG.getID(), UnitTypes.Zerg_Drone.ordinal(), UpgradeTypes.None.ordinal(),850,0,0,1,100,50,600,0,0,255,0,100,300, UnitSizeType.UnitSizes.Large.ordinal(),3,2,40,32,40,24,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,1,0,0,1,0,0,0,   0,0,
    			136, Race.ZERG.getID(), UnitTypes.Zerg_Drone.ordinal(), UpgradeTypes.None.ordinal(),850,0,0,1,100,100,900,0,0,255,0,150,450, UnitSizeType.UnitSizes.Large.ordinal(),4,2,48,32,48,4,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,1,0,0,1,0,0,0,   0,0,
    			137, Race.ZERG.getID(), UnitTypes.Zerg_Spire.ordinal(), UpgradeTypes.None.ordinal(),1000,0,0,1,100,150,1800,0,0,255,0,200,1350, UnitSizeType.UnitSizes.Large.ordinal(),2,2,28,32,28,24,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,1,0,0,1,0,0,0,   0,0,
    			138, Race.ZERG.getID(), UnitTypes.Zerg_Drone.ordinal(), UpgradeTypes.None.ordinal(),850,0,0,1,150,100,900,0,0,255,0,175,525, UnitSizeType.UnitSizes.Large.ordinal(),3,2,38,28,32,28,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,1,0,0,1,0,0,0,   0,0,
    			139, Race.ZERG.getID(), UnitTypes.Zerg_Drone.ordinal(), UpgradeTypes.None.ordinal(),750,0,0,1,75,0,600,0,0,255,0,40,120, UnitSizeType.UnitSizes.Large.ordinal(),3,2,44,32,32,20,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,1,0,0,1,0,0,0,   0,0,
    			140, Race.ZERG.getID(), UnitTypes.Zerg_Drone.ordinal(), UpgradeTypes.None.ordinal(),600,0,0,1,150,200,1200,0,0,255,0,275,825, UnitSizeType.UnitSizes.Large.ordinal(),3,2,40,32,32,31,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,1,0,0,1,0,0,0,   0,0,
    			141, Race.ZERG.getID(), UnitTypes.Zerg_Drone.ordinal(), UpgradeTypes.None.ordinal(),600,0,0,1,200,150,1800,0,0,255,0,250,750, UnitSizeType.UnitSizes.Large.ordinal(),2,2,28,32,28,24,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,1,0,0,1,0,0,0,   0,0,
    			142, Race.ZERG.getID(), UnitTypes.Zerg_Drone.ordinal(), UpgradeTypes.None.ordinal(),750,0,0,1,200,0,1200,0,0,255,0,75,225, UnitSizeType.UnitSizes.Large.ordinal(),3,2,36,28,40,18,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,1,0,0,1,0,0,0,   0,0,
    			143, Race.ZERG.getID(), UnitTypes.Zerg_Drone.ordinal(), UpgradeTypes.None.ordinal(),400,0,0,0,75,0,300,0,0,255,0,40,120, UnitSizeType.UnitSizes.Large.ordinal(),2,2,24,24,23,23,0,320, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,1,0,0,1,0,0,0,   0,0,
    			144, Race.ZERG.getID(), UnitTypes.Zerg_Creep_Colony.ordinal(), UpgradeTypes.None.ordinal(),400,0,0,0,50,0,300,0,0,255,0,25,195, UnitSizeType.UnitSizes.Large.ordinal(),2,2,24,24,23,23,224,320, WeaponTypes.None.ordinal(),0, WeaponTypes.Seeker_Spores.ordinal(),1,0,0,0,0,0,1,0,0,1,0,0,1,0,0,1,0,0,0,0,1,0,0,1,0,0,0,   0,0,
    			146, Race.ZERG.getID(), UnitTypes.Zerg_Creep_Colony.ordinal(), UpgradeTypes.None.ordinal(),300,0,0,2,50,0,300,0,0,255,0,40,240, UnitSizeType.UnitSizes.Large.ordinal(),2,2,24,24,23,23,224,320, WeaponTypes.Subterranean_Tentacle.ordinal(),1, WeaponTypes.None.ordinal(),0,0,0,0,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,1,0,0,1,0,0,0,   0,0,
    			147, Race.ZERG.getID(), UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),5000,0,0,1,1,1,1,0,0,255,0,0,10000, UnitSizeType.UnitSizes.Large.ordinal(),5,3,80,32,79,40,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,1,0,0,1,0,0,0,   0,0,
    			148, Race.ZERG.getID(), UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),2500,0,0,1,1,1,1,0,0,255,0,0,10000, UnitSizeType.UnitSizes.Large.ordinal(),5,3,80,32,79,40,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,1,0,0,1,0,0,0,   0,0,
    			149, Race.ZERG.getID(), UnitTypes.Zerg_Drone.ordinal(), UpgradeTypes.None.ordinal(),750,0,0,1,50,0,600,0,0,255,0,25,75, UnitSizeType.UnitSizes.Large.ordinal(),4,2,64,32,63,31,0,224, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,1,1,0,0,0,0,0,1,0,0,0,   0,0,
    			150, Race.ZERG.getID(), UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),250,0,0,1,0,0,0,0,0,255,0,0,5000, UnitSizeType.UnitSizes.Large.ordinal(),2,2,32,32,31,31,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,1,0,0,1,0,0,0,   0,0,
    			151, Race.ZERG.getID(), UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),1500,0,0,1,0,0,0,0,0,255,0,0,2500, UnitSizeType.UnitSizes.Large.ordinal(),3,2,40,32,32,31,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,1,0,0,1,0,0,0,   0,0,
    			152, Race.ZERG.getID(), UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),1500,0,0,1,0,0,0,0,0,255,0,0,2500, UnitSizeType.UnitSizes.Large.ordinal(),3,2,40,32,32,31,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,1,0,0,1,0,0,0,   0,0,
    			154, Race.PROTOSS.getID(), UnitTypes.Protoss_Probe.ordinal(), UpgradeTypes.None.ordinal(),750,750,0,1,400,0,1800,0,18,255,0,400,1200, UnitSizeType.UnitSizes.Large.ordinal(),4,3,56,39,56,39,0,352, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,   0,0,
    			155, Race.PROTOSS.getID(), UnitTypes.Protoss_Probe.ordinal(), UpgradeTypes.None.ordinal(),500,500,0,1,200,200,1200,0,0,255,0,300,900, UnitSizeType.UnitSizes.Large.ordinal(),3,2,36,16,40,20,0,320, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,   0,0,
    			156, Race.PROTOSS.getID(), UnitTypes.Protoss_Probe.ordinal(), UpgradeTypes.None.ordinal(),300,300,0,0,100,0,450,0,16,255,0,50,150, UnitSizeType.UnitSizes.Large.ordinal(),2,2,16,12,16,20,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,   0,0,
    			157, Race.PROTOSS.getID(), UnitTypes.Protoss_Probe.ordinal(), UpgradeTypes.None.ordinal(),450,450,0,1,100,0,600,0,0,255,0,50,150, UnitSizeType.UnitSizes.Large.ordinal(),4,2,48,32,48,24,0,320, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,1,0,0,0,0,0,1,0,0,0,   0,0,
    			159, Race.PROTOSS.getID(), UnitTypes.Protoss_Probe.ordinal(), UpgradeTypes.None.ordinal(),250,250,0,1,50,100,450,0,0,255,0,175,525, UnitSizeType.UnitSizes.Large.ordinal(),3,2,44,16,44,28,0,320, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,   0,0,
    			160, Race.PROTOSS.getID(), UnitTypes.Protoss_Probe.ordinal(), UpgradeTypes.None.ordinal(),500,500,0,1,150,0,900,0,0,255,0,75,225, UnitSizeType.UnitSizes.Large.ordinal(),4,3,48,32,48,40,0,320, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,   0,0,
    			162, Race.PROTOSS.getID(), UnitTypes.Protoss_Probe.ordinal(), UpgradeTypes.None.ordinal(),100,100,0,0,150,0,750,0,0,255,0,100,300, UnitSizeType.UnitSizes.Large.ordinal(),2,2,20,16,20,16,224,352, WeaponTypes.STS_Photon_Cannon.ordinal(),1, WeaponTypes.STA_Photon_Cannon.ordinal(),1,0,0,0,0,0,1,0,0,0,0,0,0,1,0,1,0,0,0,1,0,0,0,1,0,0,0,   0,0,
    			163, Race.PROTOSS.getID(), UnitTypes.Protoss_Probe.ordinal(), UpgradeTypes.None.ordinal(),450,450,0,1,150,100,900,0,0,255,0,200,600, UnitSizeType.UnitSizes.Large.ordinal(),3,2,24,24,40,24,0,320, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,   0,0,
    			164, Race.PROTOSS.getID(), UnitTypes.Protoss_Probe.ordinal(), UpgradeTypes.None.ordinal(),500,500,0,1,200,0,900,0,0,255,0,100,300, UnitSizeType.UnitSizes.Large.ordinal(),3,2,40,24,40,24,0,320, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,   0,0,
    			165, Race.PROTOSS.getID(), UnitTypes.Protoss_Probe.ordinal(), UpgradeTypes.None.ordinal(),500,500,0,1,150,200,900,0,0,255,0,250,750, UnitSizeType.UnitSizes.Large.ordinal(),3,2,32,24,32,24,0,320, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,   0,0,
    			166, Race.PROTOSS.getID(), UnitTypes.Protoss_Probe.ordinal(), UpgradeTypes.None.ordinal(),550,550,0,1,150,0,600,0,0,255,0,100,300, UnitSizeType.UnitSizes.Large.ordinal(),3,2,36,24,36,20,0,320, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,   0,0,
    			167, Race.PROTOSS.getID(), UnitTypes.Protoss_Probe.ordinal(), UpgradeTypes.None.ordinal(),600,600,0,1,150,150,1050,0,0,255,0,300,900, UnitSizeType.UnitSizes.Large.ordinal(),4,3,48,40,48,32,0,320, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,   0,0,
    			168, Race.PROTOSS.getID(), UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),2000,0,0,1,150,0,1,0,0,255,0,0,5000, UnitSizeType.UnitSizes.Large.ordinal(),4,3,64,48,63,47,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,   0,0,
    			169, Race.PROTOSS.getID(), UnitTypes.Protoss_Probe.ordinal(), UpgradeTypes.None.ordinal(),500,500,0,1,300,200,900,0,0,255,0,350,1050, UnitSizeType.UnitSizes.Large.ordinal(),3,2,40,32,47,24,0,320, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,   0,0,
    			170, Race.PROTOSS.getID(), UnitTypes.Protoss_Probe.ordinal(), UpgradeTypes.None.ordinal(),500,500,0,1,200,150,900,0,0,255,0,450,1350, UnitSizeType.UnitSizes.Large.ordinal(),3,2,44,28,44,28,0,320, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,   0,0,
    			171, Race.PROTOSS.getID(), UnitTypes.Protoss_Probe.ordinal(), UpgradeTypes.None.ordinal(),450,450,0,1,150,100,450,0,0,255,0,125,375, UnitSizeType.UnitSizes.Large.ordinal(),3,2,32,32,32,20,0,320, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,   0,0,
    			172, Race.PROTOSS.getID(), UnitTypes.Protoss_Probe.ordinal(), UpgradeTypes.None.ordinal(),200,200,200,1,100,0,450,0,0,255,0,50,150, UnitSizeType.UnitSizes.Large.ordinal(),3,2,32,16,32,16,0,320, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,   0,0,
    			173, Race.PROTOSS.getID(), UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),100000,0,0,1,250,0,1,0,0,255,0,0,2500, UnitSizeType.UnitSizes.Large.ordinal(),4,3,64,48,63,47,0,320, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,   0,0,
    			174, Race.PROTOSS.getID(), UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),1500,0,0,1,250,0,1,0,0,255,0,0,5000, UnitSizeType.UnitSizes.Large.ordinal(),7,3,112,48,111,47,0,320, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,   0,0,
    			175, Race.PROTOSS.getID(), UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),5000,0,0,1,1500,500,4800,0,0,255,0,0,5000, UnitSizeType.UnitSizes.Large.ordinal(),5,4,80,34,79,63,0,320, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,   0,0,
    			176, 0, UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),100000,0,0,0,1,1,1,0,0,255,0,10,10,  UnitSizeType.UnitSizes.Independent.ordinal(),2,1,32,16,31,15,0,288, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,   0,0,
    			188, 0, UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),100000,0,0,0,1,1,1,0,0,255,0,10,10,  UnitSizeType.UnitSizes.Independent.ordinal(),4,2,64,32,63,31,0,288, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,   0,0,
    			189, Race.PROTOSS.getID(), UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),700,0,0,1,600,200,2400,0,0,255,0,0,2000, UnitSizeType.UnitSizes.Large.ordinal(),3,2,48,32,47,31,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,   0,0,
    			190, Race.TERRAN.getID(), UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),2000,0,0,1,1000,400,4800,0,0,255,0,0,3600, UnitSizeType.UnitSizes.Large.ordinal(),5,3,80,38,69,47,0,320, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,   0,0,
    			200, Race.TERRAN.getID(), UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),800,0,0,1,200,50,2400,0,0,255,0,0,600, UnitSizeType.UnitSizes.Large.ordinal(),4,3,56,28,63,43,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,   0,0,
    			201, Race.ZERG.getID(), UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),2500,0,0,1,1000,500,2400,0,0,255,0,0,4000, UnitSizeType.UnitSizes.Large.ordinal(),3,2,48,32,47,31,0,320, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,1,0,0,1,0,0,0,   0,0,
    			194, Race.ZERG.getID(), UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),100000,0,0,0,250,0,1,0,0,255,0,0,0,  UnitSizeType.UnitSizes.Independent.ordinal(),3,2,48,32,47,31,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,   0,0,
    			195, Race.TERRAN.getID(), UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),100000,0,0,0,50,50,1,0,0,255,0,0,0,  UnitSizeType.UnitSizes.Independent.ordinal(),3,2,48,32,47,31,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,   0,0,
    			196, Race.PROTOSS.getID(),UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),100000,0,0,0,100,100,1,0,0,255,0,0,0,  UnitSizeType.UnitSizes.Independent.ordinal(),3,2,48,32,47,31,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,   0,0,
    			197, Race.ZERG.getID(), UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),100000,0,0,0,250,0,1,0,0,255,0,0,0,  UnitSizeType.UnitSizes.Independent.ordinal(),3,2,48,32,47,31,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,   0,0,
    			198, Race.TERRAN.getID(), UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),100000,0,0,0,50,50,1,0,0,255,0,0,0,  UnitSizeType.UnitSizes.Independent.ordinal(),3,2,48,32,47,31,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,   0,0,
    			199, Race.PROTOSS.getID(), UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),100000,0,0,0,100,100,1,0,0,255,0,0,0,  UnitSizeType.UnitSizes.Independent.ordinal(),3,2,48,32,47,31,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,   0,0,
    			202, Race.ZERG.getID(), UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),800,0,0,0,250,200,2400,0,0,255,0,0,0,  UnitSizeType.UnitSizes.Independent.ordinal(),5,5,80,80,79,79,0,256, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,   0,0,
    			128, 0, UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),10000,0,0,0,1,1,1,0,0,255,0,0,0,  UnitSizeType.UnitSizes.Independent.ordinal(),1,1,16,16,15,15,0,160, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			129, 0, UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),10000,0,0,0,1,1,1,0,0,255,0,0,0,  UnitSizeType.UnitSizes.Independent.ordinal(),1,1,16,16,15,15,0,160, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			215, 0, UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),10000,0,0,0,1,1,1,0,0,255,0,0,0,  UnitSizeType.UnitSizes.Independent.ordinal(),1,1,16,16,15,15,0,160, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			216, 0, UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),800,0,0,0,1,1,1,0,0,255,0,0,0,  UnitSizeType.UnitSizes.Independent.ordinal(),1,1,16,16,15,15,0,160, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			217, 0, UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),800,0,0,0,1,1,1,0,0,255,0,0,0,  UnitSizeType.UnitSizes.Independent.ordinal(),1,1,16,16,15,15,0,160, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			218, 0, UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),800,0,0,0,1,1,1,0,0,255,0,0,0,  UnitSizeType.UnitSizes.Independent.ordinal(),1,1,16,16,15,15,0,160, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			219, 0, UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),800,0,0,0,1,1,1,0,0,255,0,0,0,  UnitSizeType.UnitSizes.Independent.ordinal(),1,1,16,16,15,15,0,160, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			228, 0, UnitTypes.None.ordinal(), UpgradeTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,255,0,0,0,  UnitSizeType.UnitSizes.None.ordinal(),0,0,0,0,0,0,0,0, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,   0,0,
    			229, 0, UnitTypes.Unknown.ordinal(), UpgradeTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,255,0,0,0,  UnitSizeType.UnitSizes.None.ordinal(),0,0,0,0,0,0,0,0, WeaponTypes.None.ordinal(),0, WeaponTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0   ,0,0

    			
		
    	};
    	return data;
    }
    
    public int[] getTechTypes(){
    	
    	int[] data =new int []{0, Race.TERRAN.getID(),100,100,1200,0,UnitTypes.Terran_Academy.ordinal(),WeaponTypes.None.ordinal(),0,0,
    						1, Race.TERRAN.getID(),200,200,1500,100,UnitTypes.Terran_Covert_Ops.ordinal(),WeaponTypes.Lockdown.ordinal(),0,0,
    						2, Race.TERRAN.getID(),200,200,1800,100,UnitTypes.Terran_Science_Facility.ordinal(),WeaponTypes.EMP_Shockwave.ordinal(),0,0,
    						3, Race.TERRAN.getID(),100,100,1200,0  ,UnitTypes.Terran_Machine_Shop.ordinal(),WeaponTypes.Spider_Mines.ordinal(),0,0,
    						4, Race.TERRAN.getID(),0  ,0  ,0   ,50 ,UnitTypes.None.ordinal(),WeaponTypes.None.ordinal(),0,0,
    						5, Race.TERRAN.getID(),150,150,1200,0  ,UnitTypes.Terran_Machine_Shop.ordinal(),WeaponTypes.None.ordinal(),0,0,
    						6, Race.TERRAN.getID(),0  ,0  ,0   ,100,UnitTypes.None.ordinal(),WeaponTypes.None.ordinal(),0,0,
    						7, Race.TERRAN.getID(),200,200,1200,75 ,UnitTypes.Terran_Science_Facility.ordinal(),WeaponTypes.Irradiate.ordinal(),0,0,
    						8, Race.TERRAN.getID(),100,100,1800,150,UnitTypes.Terran_Physics_Lab.ordinal(),WeaponTypes.Yamato_Gun.ordinal(),0,0,
    						9, Race.TERRAN.getID(),150,150,1500,25 ,UnitTypes.Terran_Control_Tower.ordinal(),WeaponTypes.None.ordinal(),0,0,
    					   10, Race.TERRAN.getID(),100,100,1200,25 ,UnitTypes.Terran_Covert_Ops.ordinal(),WeaponTypes.None.ordinal(),0,0, 
    					   11, Race.ZERG.getID(),100,100,1200,0  ,UnitTypes.Zerg_Hatchery.ordinal(),WeaponTypes.None.ordinal(),0,0, 
    					   12, Race.ZERG.getID(),0  ,0  ,0   ,0  ,UnitTypes.None.ordinal(),WeaponTypes.None.ordinal(),0,0,
    					   13, Race.ZERG.getID(),100,100,1200,150,UnitTypes.Zerg_Queens_Nest.ordinal(),WeaponTypes.Spawn_Broodlings.ordinal(),0,0,
    					   14, Race.ZERG.getID(),0  ,0  ,0   ,100,UnitTypes.None.ordinal(),WeaponTypes.Dark_Swarm.ordinal(),0,0,
    					   15, Race.ZERG.getID(),200,200,1500,150,UnitTypes.Zerg_Defiler_Mound.ordinal(),WeaponTypes.Plague.ordinal(),0,0,
    					   16, Race.ZERG.getID(),100,100,1500,0  ,UnitTypes.Zerg_Defiler_Mound.ordinal(),WeaponTypes.Consume.ordinal(),0,0,
    					   17, Race.ZERG.getID(),100,100,1200,75 ,UnitTypes.Zerg_Queens_Nest.ordinal(),WeaponTypes.Ensnare.ordinal(),0,0,
    					   18, Race.ZERG.getID(),0  ,0  ,0   ,75 ,UnitTypes.None.ordinal(),WeaponTypes.Parasite.ordinal(),0,0,
    					   19, Race.PROTOSS.getID(),200,200,1800,75 ,UnitTypes.Protoss_Templar_Archives.ordinal(),WeaponTypes.Psionic_Storm.ordinal(),0,0,
    					   20, Race.PROTOSS.getID(),150,150,1200,100,UnitTypes.Protoss_Templar_Archives.ordinal(),WeaponTypes.None.ordinal()            ,0,0,
    					   21, Race.PROTOSS.getID(),150,150,1800,150,UnitTypes.Protoss_Arbiter_Tribunal.ordinal(),WeaponTypes.None.ordinal()            ,0,0,
    					   22, Race.PROTOSS.getID(),150,150,1500,100,UnitTypes.Protoss_Arbiter_Tribunal.ordinal(),WeaponTypes.Stasis_Field.ordinal()    ,0,0,
    					   23, Race.PROTOSS.getID(),0  ,0  ,0   ,0  ,UnitTypes.None.ordinal()                    ,WeaponTypes.None.ordinal()            ,0,0,
    					   24, Race.TERRAN.getID(),100,100,1200,50 ,UnitTypes.Terran_Academy.ordinal(),WeaponTypes.Restoration.ordinal(),0,0,
    					   25, Race.PROTOSS.getID(),200,200,1200,125,UnitTypes.Protoss_Fleet_Beacon.ordinal(),WeaponTypes.Disruption_Web.ordinal(),0,0,
    					   26,0,0,0,0,0,0,0,0,0,
    					   27, Race.PROTOSS.getID(),200,200,1800,150,UnitTypes.Protoss_Templar_Archives.ordinal(),WeaponTypes.Mind_Control.ordinal(),0,0,
    					   28, Race.PROTOSS.getID(),0  ,0  ,0   ,0  ,UnitTypes.None.ordinal(),WeaponTypes.None.ordinal(),0,0,
    					   29, Race.PROTOSS.getID(),0  ,0  ,0   ,50 ,UnitTypes.None.ordinal(),WeaponTypes.Feedback.ordinal(),0,0,
    					   30, Race.TERRAN.getID(),100,100,1800,75 ,UnitTypes.Terran_Academy.ordinal(),WeaponTypes.Optical_Flare.ordinal(),0,0,
    					   31, Race.PROTOSS.getID(),100,100,1500,100,UnitTypes.Protoss_Templar_Archives.ordinal(),WeaponTypes.Maelstrom.ordinal(),0,0,
    					   32, Race.ZERG.getID(),200,200,1800,0  ,UnitTypes.Zerg_Hydralisk_Den.ordinal()  ,WeaponTypes.None.ordinal(),0,0,
    					   33,0,0,0,0,0,0,0,0,0,
    					   34, Race.TERRAN.getID(),0  ,0  ,0   ,1  ,UnitTypes.None.ordinal() ,WeaponTypes.None.ordinal(),0,0,
    					   35,0,0,0,0,0,0,0,0,0,
    					   36,0,0,0,0,0,0,0,0,0,
    					   37,0,0,0,0,0,0,0,0,0,
    					   38,0,0,0,0,0,0,0,0,0,
    					   39,0,0,0,0,0,0,0,0,0,
    					   40,0,0,0,0,0,0,0,0,0,
    					   41,0,0,0,0,0,0,0,0,0,
    					   42,0,0,0,0,0,0,0,0,0,
    					   43,0,0,0,0,0,0,0,0,0,
    					   44,0,0  ,0  ,0   ,0  ,UnitTypes.None.ordinal(),WeaponTypes.None.ordinal(),0,0,
    					   45,0,0  ,0  ,0   ,0  ,UnitTypes.None.ordinal(),WeaponTypes.None.ordinal(),0,0,
    					   46,Race.TERRAN.getID(),0  ,0  ,0   ,0  ,UnitTypes.None.ordinal(),WeaponTypes.Nuclear_Strike.ordinal(),0,0
    	};
    	
    	
    	
    	return data;
    }
    
    public int[] getUpgradeTypes(){

    	int[] data = new int[]{
    			0,Race.TERRAN.getID(), 100, 75 , 100, 75 , 4000, 480,3, UnitTypes.Terran_Engineering_Bay.ordinal(),
    			1,Race.TERRAN.getID(), 100, 75 , 100, 75 , 4000, 480,3, UnitTypes.Terran_Armory.ordinal(),
    			2,Race.TERRAN.getID(), 150, 75 , 150, 75 , 4000, 480,3, UnitTypes.Terran_Armory.ordinal(),
    			3,Race.ZERG.getID(), 150, 75 , 150, 75 , 4000, 480,3, UnitTypes.Zerg_Evolution_Chamber.ordinal(),
    			4,Race.ZERG.getID(), 150, 75 , 150, 75 , 4000, 480,3, UnitTypes.Zerg_Spire.ordinal(),
    			5,Race.PROTOSS.getID(), 100, 75 , 100, 75 , 4000, 480,3, UnitTypes.Protoss_Forge.ordinal(),
    			6,Race.PROTOSS.getID(), 150, 75 , 150, 75 , 4000, 480,3, UnitTypes.Protoss_Cybernetics_Core.ordinal() ,
    			7,Race.TERRAN.getID(), 100, 75 , 100, 75 , 4000, 480,3, UnitTypes.Terran_Engineering_Bay.ordinal(),
    			8,Race.TERRAN.getID(), 100, 75 , 100, 75 , 4000, 480,3, UnitTypes.Terran_Armory.ordinal(),
    			9,Race.TERRAN.getID(), 100, 50 , 100, 50 , 4000, 480,3, UnitTypes.Terran_Armory.ordinal(),
    		   10,Race.ZERG.getID(), 100, 50 , 100, 50 , 4000, 480,3, UnitTypes.Zerg_Evolution_Chamber.ordinal(),
    		   11,Race.ZERG.getID(), 100, 50 , 100, 50 , 4000, 480,3, UnitTypes.Zerg_Evolution_Chamber.ordinal(),
    		   12,Race.ZERG.getID(), 100, 75 , 100, 75 , 4000, 480,3, UnitTypes.Zerg_Spire.ordinal() ,
    		   13,Race.PROTOSS.getID(), 100, 50 , 100, 50 , 4000, 480,3, UnitTypes.Protoss_Forge.ordinal(),
    		   14,Race.PROTOSS.getID(), 100, 75 , 100, 75 , 4000, 480,3, UnitTypes.Protoss_Cybernetics_Core.ordinal(),
    		   15,Race.PROTOSS.getID(), 200, 100, 200, 100, 4000, 480,3, UnitTypes.Protoss_Forge.ordinal(),
    		   16,Race.TERRAN.getID(), 150, 0  , 150, 0  , 1500, 0,1  , UnitTypes.Terran_Academy.ordinal(),
    		   17,Race.TERRAN.getID(), 100, 0  , 100, 0  , 1500, 0,1  , UnitTypes.Terran_Machine_Shop.ordinal(),
    		   18,0,0,0,0,0,0,0,0,0,
    		   19,Race.TERRAN.getID(), 150, 0  , 150, 0  , 2500, 0,1  , UnitTypes.Terran_Science_Facility.ordinal(),
    		   20,Race.TERRAN.getID(), 100, 0  , 100, 0  , 2500, 0,1  , UnitTypes.Terran_Covert_Ops.ordinal(),
    		   21,Race.TERRAN.getID(), 150, 0  , 150, 0  , 2500, 0,1  , UnitTypes.Terran_Covert_Ops.ordinal(),
    		   22,Race.TERRAN.getID(), 200, 0  , 200, 0  , 2500, 0,1  , UnitTypes.Terran_Control_Tower.ordinal(),
    		   23,Race.TERRAN.getID(), 150, 0  , 150, 0  , 2500, 0,1  , UnitTypes.Terran_Physics_Lab.ordinal(),
    		   24,Race.ZERG.getID(), 200, 0  , 200, 0  , 2400, 0,1  , UnitTypes.Zerg_Lair.ordinal(),
    		   25,Race.ZERG.getID(), 150, 0  , 150, 0  , 2000, 0,1  , UnitTypes.Zerg_Lair.ordinal(),
    		   26,Race.ZERG.getID(), 150, 0  , 150, 0  , 2000, 0,1  , UnitTypes.Zerg_Lair.ordinal(),
    		   27,Race.ZERG.getID(), 100, 0  , 100, 0  , 1500, 0,1  , UnitTypes.Zerg_Spawning_Pool.ordinal(),
    		   28,Race.ZERG.getID(), 200, 0  , 200, 0  , 1500, 0,1  , UnitTypes.Zerg_Spawning_Pool.ordinal(),
    		   29,Race.ZERG.getID(), 150, 0  , 150, 0  , 1500, 0,1  , UnitTypes.Zerg_Hydralisk_Den.ordinal(),
    		   30,Race.ZERG.getID(), 150, 0  , 150, 0  , 1500, 0,1  , UnitTypes.Zerg_Hydralisk_Den.ordinal(),
    		   31,Race.ZERG.getID(), 150, 0  , 150, 0  , 2500, 0,1  , UnitTypes.Zerg_Queens_Nest.ordinal(),
    		   32,Race.ZERG.getID(), 150, 0  , 150, 0  , 2500, 0,1  , UnitTypes.Zerg_Defiler_Mound.ordinal(),
    		   33,Race.PROTOSS.getID(), 150, 0  , 150, 0  , 2500, 0,1  , UnitTypes.Protoss_Cybernetics_Core.ordinal(),
    		   34,Race.PROTOSS.getID(), 150, 0  , 150, 0  , 2000, 0,1  , UnitTypes.Protoss_Citadel_of_Adun.ordinal(),
    		   35,Race.PROTOSS.getID(), 200, 0  , 200, 0  , 2500, 0,1  , UnitTypes.Protoss_Robotics_Support_Bay.ordinal(),
    		   36,Race.PROTOSS.getID(), 200, 0  , 200, 0  , 2500, 0,1  , UnitTypes.Protoss_Robotics_Support_Bay.ordinal(),
    		   37,Race.PROTOSS.getID(), 200, 0  , 200, 0  , 2500, 0,1  , UnitTypes.Protoss_Robotics_Support_Bay.ordinal(),
    		   38,Race.PROTOSS.getID(), 150, 0  , 150, 0  , 2000, 0,1  , UnitTypes.Protoss_Observatory.ordinal()         ,
    		   39,Race.PROTOSS.getID(), 150, 0  , 150, 0  , 2000, 0,1  , UnitTypes.Protoss_Observatory.ordinal()         ,
    		   40,Race.PROTOSS.getID(), 150, 0  , 150, 0  , 2500, 0,1  , UnitTypes.Protoss_Templar_Archives.ordinal()    ,
    		   41,Race.PROTOSS.getID(), 100, 0  , 100, 0  , 2500, 0,1  , UnitTypes.Protoss_Fleet_Beacon.ordinal()        ,
    		   42,Race.PROTOSS.getID(), 200, 0  , 200, 0  , 2500, 0,1  , UnitTypes.Protoss_Fleet_Beacon.ordinal()        ,
    		   43,Race.PROTOSS.getID(), 100, 0  , 100, 0  , 1500, 0,1  , UnitTypes.Protoss_Fleet_Beacon.ordinal()        ,
    		   44,Race.PROTOSS.getID(), 150, 0  , 150, 0  , 2500, 0,1  , UnitTypes.Protoss_Arbiter_Tribunal.ordinal()    ,
    		   45,0,0,0,0,0,0,0,0,0,
    		   46,0,0,0,0,0,0,0,0,0,
    		   47,Race.PROTOSS.getID(), 100, 0  , 100, 0  , 2500, 0,1  , UnitTypes.Protoss_Fleet_Beacon.ordinal()    ,
    		   48,0,0,0,0,0,0,0,0,0,
    		   49,Race.PROTOSS.getID(), 150, 0  , 150, 0  , 2500, 0,1  , UnitTypes.Protoss_Templar_Archives.ordinal()    ,
    		   50,0,0,0,0,0,0,0,0,0,
    		   51,Race.TERRAN.getID(), 150, 0  , 150, 0  , 2500, 0,1  , UnitTypes.Terran_Academy.ordinal()    ,
    		   52,Race.ZERG.getID(), 150, 0  , 150, 0  , 2000, 0,1  , UnitTypes.Zerg_Ultralisk_Cavern.ordinal()    ,
    		   53,Race.ZERG.getID(), 200, 0  , 200, 0  , 2000, 0,1  , UnitTypes.Zerg_Ultralisk_Cavern.ordinal()    ,
    		   54,Race.TERRAN.getID(), 100, 0  , 100, 0  , 2000, 0,1  , UnitTypes.Terran_Machine_Shop.ordinal()    ,
    		   55,0,0,0,0,0,0,0,0,0,
    		   56,0,0,0,0,0,0,0,0,0,
    		   57,0,0,0,0,0,0,0,0,0,
    		   58,0,0,0,0,0,0,0,0,0,
    		   59,0,0,0,0,0,0,0,0,0,
    		   60,0,0,0,0,0,0,0,0,0,
    		   61,0, 0  , 0  , 0  , 0  , 0   , 0  ,0, UnitTypes.None.ordinal()    ,
    		   62,0, 0  , 0  , 0  , 0  , 0   , 0  ,0, UnitTypes.None.ordinal()    

    			
    			
    			
    	};
    	
    	
    	
    	return data;
    }
    
    public int[] getWeaponTypes(){
    	
    	
    	int[] data = new int[]{
    			0, TechTypes.None.ordinal(), UnitTypes.Terran_Marine.ordinal(),6,1,15,1, UpgradeTypes.Terran_Infantry_Weapons.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,128,0,0,0,1,1,0,0,0,0,0,0,0,
    			1, TechTypes.None.ordinal(), UnitTypes.Hero_Jim_Raynor_Marine.ordinal(),18,1,15,1, UpgradeTypes.Terran_Infantry_Weapons.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,160,0,0,0,1,1,0,0,0,0,0,0,0,
    			2, TechTypes.None.ordinal(), UnitTypes.Terran_Ghost.ordinal(),10,1,22,1, UpgradeTypes.Terran_Infantry_Weapons.ordinal(), DamageTypes.Concussive.ordinal(), ExplosionTypes.Normal.ordinal(),0,224,0,0,0,1,1,0,0,0,0,0,0,0,
    			3, TechTypes.None.ordinal(), UnitTypes.Hero_Sarah_Kerrigan.ordinal(),30,1,22,1, UpgradeTypes.Terran_Infantry_Weapons.ordinal(), DamageTypes.Concussive.ordinal(), ExplosionTypes.Normal.ordinal(),0,192,0,0,0,1,1,0,0,0,0,0,0,0,
    			112, TechTypes.None.ordinal(), UnitTypes.Hero_Samir_Duran.ordinal(),25,1,22,1, UpgradeTypes.Terran_Infantry_Weapons.ordinal(), DamageTypes.Concussive.ordinal(), ExplosionTypes.Normal.ordinal(),0,192,0,0,0,1,1,0,0,0,0,0,0,0,
    			113, TechTypes.None.ordinal(), UnitTypes.Hero_Infested_Duran.ordinal(),25,1,22,1, UpgradeTypes.Terran_Infantry_Weapons.ordinal(), DamageTypes.Concussive.ordinal(), ExplosionTypes.Normal.ordinal(),0,192,0,0,0,1,1,0,0,0,0,0,0,0,
    			116, TechTypes.None.ordinal(), UnitTypes.Hero_Alexei_Stukov.ordinal(),30,1,22,1, UpgradeTypes.Terran_Infantry_Weapons.ordinal(), DamageTypes.Concussive.ordinal(), ExplosionTypes.Normal.ordinal(),0,192,0,0,0,1,1,0,0,0,0,0,0,0,
    			4, TechTypes.None.ordinal(), UnitTypes.Terran_Vulture.ordinal(),20,2,30,1, UpgradeTypes.Terran_Vehicle_Weapons.ordinal(), DamageTypes.Concussive.ordinal(), ExplosionTypes.Normal.ordinal(),0,160,0,0,0,0,1,0,0,0,0,0,0,0,
    			5, TechTypes.None.ordinal(), UnitTypes.Hero_Jim_Raynor_Vulture.ordinal(),30,2,22,1, UpgradeTypes.Terran_Vehicle_Weapons.ordinal(), DamageTypes.Concussive.ordinal(), ExplosionTypes.Normal.ordinal(),0,160,0,0,0,0,1,0,0,0,0,0,0,0,
    			6, TechTypes.Spider_Mines.ordinal(), UnitTypes.Terran_Vulture_Spider_Mine.ordinal(),125,0,22,1, UpgradeTypes.None.ordinal(), DamageTypes.Explosive.ordinal(), ExplosionTypes.Radial_Splash.ordinal(),0,10,50,75,100,0,1,0,0,1,0,0,0,0,
    			7, TechTypes.None.ordinal(), UnitTypes.Terran_Goliath.ordinal(),12,1,22,1, UpgradeTypes.Terran_Vehicle_Weapons.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,192,0,0,0,0,1,0,0,0,0,0,0,0,
    			9, TechTypes.None.ordinal(), UnitTypes.Hero_Alan_Schezar.ordinal(),24,1,22,1, UpgradeTypes.Terran_Vehicle_Weapons.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,160,0,0,0,0,1,0,0,0,0,0,0,0,
    			8, TechTypes.None.ordinal(), UnitTypes.Terran_Goliath.ordinal(),10,2,22,2, UpgradeTypes.Terran_Vehicle_Weapons.ordinal(), DamageTypes.Explosive.ordinal(), ExplosionTypes.Normal.ordinal(),0,160,0,0,0,1,0,0,0,0,0,0,0,0,
    			10, TechTypes.None.ordinal(), UnitTypes.Hero_Alan_Schezar.ordinal(),20,1,22,2, UpgradeTypes.Terran_Vehicle_Weapons.ordinal(), DamageTypes.Explosive.ordinal(), ExplosionTypes.Normal.ordinal(),0,160,0,0,0,1,0,0,0,0,0,0,0,0,
    			11, TechTypes.None.ordinal(), UnitTypes.Terran_Siege_Tank_Tank_Mode.ordinal(),30,3,37,1, UpgradeTypes.Terran_Vehicle_Weapons.ordinal(), DamageTypes.Explosive.ordinal(), ExplosionTypes.Normal.ordinal(),0,224,0,0,0,0,1,0,0,0,0,0,0,0,
    			12, TechTypes.None.ordinal(), UnitTypes.Hero_Edmund_Duke_Tank_Mode.ordinal(),70,3,37,1, UpgradeTypes.Terran_Vehicle_Weapons.ordinal(), DamageTypes.Explosive.ordinal(), ExplosionTypes.Normal.ordinal(),0,224,0,0,0,0,1,0,0,0,0,0,0,0,
    			13, TechTypes.None.ordinal(), UnitTypes.Terran_SCV.ordinal(),5,1,15,1, UpgradeTypes.None.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,10,0,0,0,0,1,0,0,0,0,0,0,0,
    			15, TechTypes.None.ordinal(), UnitTypes.Terran_Wraith.ordinal(),20,2,22,1, UpgradeTypes.Terran_Ship_Weapons.ordinal(), DamageTypes.Explosive.ordinal(), ExplosionTypes.Normal.ordinal(),0,160,0,0,0,1,0,0,0,0,0,0,0,0,
    			17, TechTypes.None.ordinal(), UnitTypes.Hero_Tom_Kazansky.ordinal(),40,2,22,1, UpgradeTypes.Terran_Ship_Weapons.ordinal(), DamageTypes.Explosive.ordinal(), ExplosionTypes.Normal.ordinal(),0,160,0,0,0,1,0,0,0,0,0,0,0,0,
    			16, TechTypes.None.ordinal(), UnitTypes.Terran_Wraith.ordinal(),8,1,30,1, UpgradeTypes.Terran_Ship_Weapons.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,160,0,0,0,0,1,0,0,0,0,0,0,0,
    			18, TechTypes.None.ordinal(), UnitTypes.Hero_Tom_Kazansky.ordinal(),16,1,30,1, UpgradeTypes.Terran_Ship_Weapons.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,160,0,0,0,0,1,0,0,0,0,0,0,0,
    			19, TechTypes.None.ordinal(), UnitTypes.Terran_Battlecruiser.ordinal(),25,3,30,1, UpgradeTypes.Terran_Ship_Weapons.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,192,0,0,0,0,1,0,0,0,0,0,0,0,
    			21, TechTypes.None.ordinal(), UnitTypes.Hero_Norad_II.ordinal(),50,3,30,1, UpgradeTypes.Terran_Ship_Weapons.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,192,0,0,0,0,1,0,0,0,0,0,0,0,
    			23, TechTypes.None.ordinal(), UnitTypes.Hero_Hyperion.ordinal(),30,3,22,1, UpgradeTypes.Terran_Ship_Weapons.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,192,0,0,0,0,1,0,0,0,0,0,0,0,
    			20, TechTypes.None.ordinal(), UnitTypes.Terran_Battlecruiser.ordinal(),25,3,30,1, UpgradeTypes.Terran_Ship_Weapons.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,192,0,0,0,1,0,0,0,0,0,0,0,0,
    			22, TechTypes.None.ordinal(), UnitTypes.Hero_Norad_II.ordinal(),50,3,30,1, UpgradeTypes.Terran_Ship_Weapons.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,192,0,0,0,1,0,0,0,0,0,0,0,0,
    			24, TechTypes.None.ordinal(), UnitTypes.Hero_Hyperion.ordinal(),30,3,22,1, UpgradeTypes.Terran_Ship_Weapons.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,192,0,0,0,1,0,0,0,0,0,0,0,0,
    			25, TechTypes.None.ordinal(), UnitTypes.Terran_Firebat.ordinal(),8,1,22,1, UpgradeTypes.Terran_Infantry_Weapons.ordinal(), DamageTypes.Concussive.ordinal(), ExplosionTypes.Enemy_Splash.ordinal(),0,32,15,20,25,0,1,0,0,0,0,0,0,0,
    			26, TechTypes.None.ordinal(), UnitTypes.Terran_Firebat.ordinal(),16,1,22,1, UpgradeTypes.Terran_Infantry_Weapons.ordinal(), DamageTypes.Concussive.ordinal(), ExplosionTypes.Enemy_Splash.ordinal(),0,32,15,20,25,0,1,0,0,0,0,0,0,0,
    			27, TechTypes.None.ordinal(), UnitTypes.Terran_Siege_Tank_Siege_Mode.ordinal(),70,5,75,1, UpgradeTypes.Terran_Vehicle_Weapons.ordinal(), DamageTypes.Explosive.ordinal(), ExplosionTypes.Radial_Splash.ordinal(),64,384,10,25,40,0,1,0,0,0,0,0,0,0,
    			28, TechTypes.None.ordinal(), UnitTypes.Hero_Edmund_Duke_Siege_Mode.ordinal(),150,5,75,1, UpgradeTypes.Terran_Vehicle_Weapons.ordinal(), DamageTypes.Explosive.ordinal(), ExplosionTypes.Radial_Splash.ordinal(),64,384,10,25,40,0,1,0,0,0,0,0,0,0,
    			29, TechTypes.None.ordinal(), UnitTypes.Terran_Missile_Turret.ordinal(),20,0,15,1, UpgradeTypes.None.ordinal(), DamageTypes.Explosive.ordinal(), ExplosionTypes.Normal.ordinal(),0,224,0,0,0,1,0,0,0,0,0,0,0,0,
    			30, TechTypes.Yamato_Gun.ordinal(), UnitTypes.Terran_Battlecruiser.ordinal(),260,0,15,1, UpgradeTypes.None.ordinal(), DamageTypes.Explosive.ordinal(), ExplosionTypes.Yamato_Gun.ordinal(),0,320,0,0,0,1,1,0,0,0,0,0,0,0,
    			31, TechTypes.Nuclear_Strike.ordinal(), UnitTypes.Terran_Ghost.ordinal(),600,0,1,1, UpgradeTypes.None.ordinal(), DamageTypes.Explosive.ordinal(), ExplosionTypes.Nuclear_Missile.ordinal(),0,3,128,192,256,1,1,0,0,0,0,0,0,0,
    			32, TechTypes.Lockdown.ordinal(), UnitTypes.Terran_Ghost.ordinal(),0,0,1,1, UpgradeTypes.None.ordinal(), DamageTypes.Concussive.ordinal(), ExplosionTypes.Lockdown.ordinal(),0,256,0,0,0,1,1,1,0,1,0,0,0,0,
    			33, TechTypes.EMP_Shockwave.ordinal(), UnitTypes.Terran_Science_Vessel.ordinal(),0,0,1,1, UpgradeTypes.None.ordinal(), DamageTypes.Concussive.ordinal(), ExplosionTypes.EMP_Shockwave.ordinal(),0,256,64,64,64,1,1,0,0,0,0,1,0,0,
    			34, TechTypes.Irradiate.ordinal(), UnitTypes.Terran_Science_Vessel.ordinal(),250,0,75,1, UpgradeTypes.None.ordinal(), DamageTypes.Ignore_Armor.ordinal(), ExplosionTypes.Irradiate.ordinal(),0,288,0,0,0,1,1,0,0,0,0,1,0,0,
    			35, TechTypes.None.ordinal(), UnitTypes.Zerg_Zergling.ordinal(),5,1,8,1, UpgradeTypes.Zerg_Melee_Attacks.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,15,0,0,0,0,1,0,0,0,0,0,0,0,
    			36, TechTypes.None.ordinal(), UnitTypes.Hero_Devouring_One.ordinal(),10,1,8,1, UpgradeTypes.Zerg_Melee_Attacks.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,15,0,0,0,0,1,0,0,0,0,0,0,0,
    			37, TechTypes.None.ordinal(), UnitTypes.Hero_Infested_Kerrigan.ordinal(),50,1,15,1, UpgradeTypes.Zerg_Melee_Attacks.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,15,0,0,0,0,1,0,0,0,0,0,0,0,
    			38, TechTypes.None.ordinal(), UnitTypes.Zerg_Hydralisk.ordinal(),10,1,15,1, UpgradeTypes.Zerg_Missile_Attacks.ordinal(), DamageTypes.Explosive.ordinal(), ExplosionTypes.Normal.ordinal(),0,128,0,0,0,1,1,0,0,0,0,0,0,0,
    			39, TechTypes.None.ordinal(), UnitTypes.Hero_Hunter_Killer.ordinal(),20,1,15,1, UpgradeTypes.Zerg_Missile_Attacks.ordinal(), DamageTypes.Explosive.ordinal(), ExplosionTypes.Normal.ordinal(),0,160,0,0,0,1,1,0,0,0,0,0,0,0,
    			40, TechTypes.None.ordinal(), UnitTypes.Zerg_Ultralisk.ordinal(),20,3,15,1, UpgradeTypes.Zerg_Melee_Attacks.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,25,0,0,0,0,1,0,0,0,0,0,0,0,
    			41, TechTypes.None.ordinal(), UnitTypes.Hero_Torrasque.ordinal(),50,3,15,1, UpgradeTypes.Zerg_Melee_Attacks.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,25,0,0,0,0,1,0,0,0,0,0,0,0,
    			42, TechTypes.None.ordinal(), UnitTypes.Zerg_Broodling.ordinal(),4,1,15,1, UpgradeTypes.Zerg_Melee_Attacks.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,2,0,0,0,0,1,0,0,0,0,0,0,0,
    			43, TechTypes.None.ordinal(), UnitTypes.Zerg_Drone.ordinal(),5,0,22,1, UpgradeTypes.None.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,32,0,0,0,0,1,0,0,0,0,0,0,0,
    			46, TechTypes.None.ordinal(), UnitTypes.Zerg_Guardian.ordinal(),20,2,30,1, UpgradeTypes.Zerg_Flyer_Attacks.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,256,0,0,0,0,1,0,0,0,0,0,0,0,
    			47, TechTypes.None.ordinal(), UnitTypes.Hero_Kukulza_Guardian.ordinal(),40,2,30,1, UpgradeTypes.Zerg_Flyer_Attacks.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,256,0,0,0,0,1,0,0,0,0,0,0,0,
    			48, TechTypes.None.ordinal(), UnitTypes.Zerg_Mutalisk.ordinal(),9,1,30,1, UpgradeTypes.Zerg_Flyer_Attacks.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,96,0,0,0,1,1,0,0,0,0,0,0,0,
    			49, TechTypes.None.ordinal(), UnitTypes.Hero_Kukulza_Mutalisk.ordinal(),18,1,30,1, UpgradeTypes.Zerg_Flyer_Attacks.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,96,0,0,0,1,1,0,0,0,0,0,0,0,
    			52, TechTypes.None.ordinal(), UnitTypes.Zerg_Spore_Colony.ordinal(),15,0,15,1, UpgradeTypes.None.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,224,0,0,0,1,0,0,0,0,0,0,0,0,
    			53, TechTypes.None.ordinal(), UnitTypes.Zerg_Sunken_Colony.ordinal(),40,0,32,1, UpgradeTypes.None.ordinal(), DamageTypes.Explosive.ordinal(), ExplosionTypes.Normal.ordinal(),0,224,0,0,0,0,1,0,0,0,0,0,0,0,
    			54, TechTypes.None.ordinal(), UnitTypes.Zerg_Infested_Terran.ordinal(),500,0,1,1, UpgradeTypes.None.ordinal(), DamageTypes.Explosive.ordinal(), ExplosionTypes.Radial_Splash.ordinal(),0,3,20,40,60,0,1,0,0,0,0,0,0,0,
    			55, TechTypes.None.ordinal(), UnitTypes.Zerg_Scourge.ordinal(),110,0,1,1, UpgradeTypes.None.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,3,0,0,0,1,0,0,0,0,0,0,0,0,
    			56, TechTypes.Parasite.ordinal(), UnitTypes.Zerg_Queen.ordinal(),0,0,1,1, UpgradeTypes.None.ordinal(), DamageTypes.Independent.ordinal(), ExplosionTypes.Parasite.ordinal(),0,384,0,0,0,1,1,0,0,1,0,0,0,0,
    			57, TechTypes.Spawn_Broodlings.ordinal(), UnitTypes.Zerg_Queen.ordinal(),0,0,1,1, UpgradeTypes.None.ordinal(), DamageTypes.Independent.ordinal(), ExplosionTypes.Broodlings.ordinal(),0,288,0,0,0,0,1,0,0,1,1,0,1,0,
    			58, TechTypes.Ensnare.ordinal(), UnitTypes.Zerg_Queen.ordinal(),0,0,1,1, UpgradeTypes.None.ordinal(), DamageTypes.Independent.ordinal(), ExplosionTypes.Ensnare.ordinal(),0,288,0,0,0,1,1,0,0,0,0,1,0,0,
    			59, TechTypes.Dark_Swarm.ordinal(), UnitTypes.Zerg_Defiler.ordinal(),0,0,1,1, UpgradeTypes.None.ordinal(), DamageTypes.Independent.ordinal(), ExplosionTypes.Dark_Swarm.ordinal(),0,288,0,0,0,1,1,0,0,0,0,0,0,0,
    			60, TechTypes.Plague.ordinal(), UnitTypes.Zerg_Defiler.ordinal(),300,0,1,1, UpgradeTypes.None.ordinal(), DamageTypes.Independent.ordinal(), ExplosionTypes.Plague.ordinal(),0,288,0,0,0,1,1,0,0,0,0,1,0,0,
    			61, TechTypes.Consume.ordinal(), UnitTypes.Zerg_Defiler.ordinal(),0,0,1,1, UpgradeTypes.None.ordinal(), DamageTypes.Independent.ordinal(), ExplosionTypes.Consume.ordinal(),0,16,0,0,0,1,1,0,1,1,0,0,0,1,
    			62, TechTypes.None.ordinal(), UnitTypes.Protoss_Probe.ordinal(),5,0,22,1, UpgradeTypes.None.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,32,0,0,0,0,1,0,0,0,0,0,0,0,
    			64, TechTypes.None.ordinal(), UnitTypes.Protoss_Zealot.ordinal(),8,1,22,1, UpgradeTypes.Protoss_Ground_Weapons.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,15,0,0,0,0,1,0,0,0,0,0,0,0,
    			65, TechTypes.None.ordinal(), UnitTypes.Hero_Fenix_Zealot.ordinal(),20,1,22,1, UpgradeTypes.Protoss_Ground_Weapons.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,15,0,0,0,0,1,0,0,0,0,0,0,0,
    			66, TechTypes.None.ordinal(), UnitTypes.Protoss_Dragoon.ordinal(),20,2,30,1, UpgradeTypes.Protoss_Ground_Weapons.ordinal(), DamageTypes.Explosive.ordinal(), ExplosionTypes.Normal.ordinal(),0,128,0,0,0,1,1,0,0,0,0,0,0,0,
    			67, TechTypes.None.ordinal(), UnitTypes.Hero_Fenix_Dragoon.ordinal(),45,2,22,1, UpgradeTypes.Protoss_Ground_Weapons.ordinal(), DamageTypes.Explosive.ordinal(), ExplosionTypes.Normal.ordinal(),0,128,0,0,0,1,1,0,0,0,0,0,0,0,
    			69, TechTypes.None.ordinal(), UnitTypes.Hero_Tassadar.ordinal(),20,1,22,1, UpgradeTypes.Protoss_Ground_Weapons.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,96,0,0,0,0,1,0,0,0,0,0,0,0,
    			70, TechTypes.None.ordinal(), UnitTypes.Protoss_Archon.ordinal(),30,3,20,1, UpgradeTypes.Protoss_Ground_Weapons.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Enemy_Splash.ordinal(),0,64,3,15,30,1,1,0,0,0,0,0,0,0,
    			71, TechTypes.None.ordinal(), UnitTypes.Hero_Tassadar_Zeratul_Archon.ordinal(),60,3,20,1, UpgradeTypes.Protoss_Ground_Weapons.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Enemy_Splash.ordinal(),0,64,3,15,30,1,1,0,0,0,0,0,0,0,
    			73, TechTypes.None.ordinal(), UnitTypes.Protoss_Scout.ordinal(),8,1,30,1, UpgradeTypes.Protoss_Air_Weapons.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,128,0,0,0,0,1,0,0,0,0,0,0,0,
    			75, TechTypes.None.ordinal(), UnitTypes.Hero_Mojo.ordinal(),20,1,30,1, UpgradeTypes.Protoss_Air_Weapons.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,128,0,0,0,0,1,0,0,0,0,0,0,0,
    			114, TechTypes.None.ordinal(), UnitTypes.Hero_Artanis.ordinal(),20,1,30,1, UpgradeTypes.Protoss_Air_Weapons.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,128,0,0,0,0,1,0,0,0,0,0,0,0,
    			74, TechTypes.None.ordinal(), UnitTypes.Protoss_Scout.ordinal(),14,1,22,2, UpgradeTypes.Protoss_Air_Weapons.ordinal(), DamageTypes.Explosive.ordinal(), ExplosionTypes.Normal.ordinal(),0,128,0,0,0,1,0,0,0,0,0,0,0,0,
    			76, TechTypes.None.ordinal(), UnitTypes.Hero_Mojo.ordinal(),28,1,22,2, UpgradeTypes.Protoss_Air_Weapons.ordinal(), DamageTypes.Explosive.ordinal(), ExplosionTypes.Normal.ordinal(),0,128,0,0,0,1,0,0,0,0,0,0,0,0,
    			115, TechTypes.None.ordinal(), UnitTypes.Hero_Artanis.ordinal(),28,1,22,2, UpgradeTypes.Protoss_Air_Weapons.ordinal(), DamageTypes.Explosive.ordinal(), ExplosionTypes.Normal.ordinal(),0,128,0,0,0,1,0,0,0,0,0,0,0,0,
    			77, TechTypes.None.ordinal(), UnitTypes.Protoss_Arbiter.ordinal(),10,1,45,1, UpgradeTypes.Protoss_Air_Weapons.ordinal(), DamageTypes.Explosive.ordinal(), ExplosionTypes.Normal.ordinal(),0,160,0,0,0,1,1,0,0,0,0,0,0,0,
    			78, TechTypes.None.ordinal(), UnitTypes.Hero_Danimoth.ordinal(),20,1,45,1, UpgradeTypes.Protoss_Air_Weapons.ordinal(), DamageTypes.Explosive.ordinal(), ExplosionTypes.Normal.ordinal(),0,160,0,0,0,1,1,0,0,0,0,0,0,0,
    			79, TechTypes.None.ordinal(), UnitTypes.Protoss_Interceptor.ordinal(),6,1,1,1, UpgradeTypes.Protoss_Air_Weapons.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,128,0,0,0,1,1,0,0,0,0,0,0,0,
    			80, TechTypes.None.ordinal(), UnitTypes.Protoss_Photon_Cannon.ordinal(),20,0,22,1, UpgradeTypes.None.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,224,0,0,0,0,1,0,0,0,0,0,0,0,
    			81, TechTypes.None.ordinal(), UnitTypes.Protoss_Photon_Cannon.ordinal(),20,0,22,1, UpgradeTypes.None.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,224,0,0,0,1,0,0,0,0,0,0,0,0,
    			82, TechTypes.None.ordinal(), UnitTypes.Protoss_Scarab.ordinal(),100,25,1,1, UpgradeTypes.Scarab_Damage.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Enemy_Splash.ordinal(),0,128,20,40,60,0,1,0,0,0,0,0,0,0,
    			83, TechTypes.Stasis_Field.ordinal(), UnitTypes.Protoss_Arbiter.ordinal(),0,1,1,1, UpgradeTypes.None.ordinal(), DamageTypes.Independent.ordinal(), ExplosionTypes.Stasis_Field.ordinal(),0,288,0,0,0,1,1,0,0,0,0,1,0,0,
    			84, TechTypes.Psionic_Storm.ordinal(), UnitTypes.Protoss_High_Templar.ordinal(),14,1,45,1, UpgradeTypes.None.ordinal(), DamageTypes.Ignore_Armor.ordinal(), ExplosionTypes.Radial_Splash.ordinal(),0,288,48,48,48,1,1,0,0,1,0,1,0,0,
    			100, TechTypes.None.ordinal(), UnitTypes.Protoss_Corsair.ordinal(),5,1,8,1, UpgradeTypes.Protoss_Air_Weapons.ordinal(), DamageTypes.Explosive.ordinal(), ExplosionTypes.Air_Splash.ordinal(),0,160,5,50,100,1,0,0,0,0,0,0,0,0,
    			101, TechTypes.Disruption_Web.ordinal(), UnitTypes.Protoss_Corsair.ordinal(),0,0,22,1, UpgradeTypes.None.ordinal(), DamageTypes.Ignore_Armor.ordinal(), ExplosionTypes.Disruption_Web.ordinal(),0,288,0,0,0,0,1,0,0,0,0,0,0,0,
    			102, TechTypes.Restoration.ordinal(), UnitTypes.Terran_Medic.ordinal(),20,0,22,1, UpgradeTypes.None.ordinal(), DamageTypes.Ignore_Armor.ordinal(), ExplosionTypes.Restoration.ordinal(),0,192,0,0,0,1,1,0,0,0,0,1,0,0,
    			103, TechTypes.None.ordinal(), UnitTypes.Terran_Valkyrie.ordinal(),6,1,64,2, UpgradeTypes.Terran_Ship_Weapons.ordinal(), DamageTypes.Explosive.ordinal(), ExplosionTypes.Air_Splash.ordinal(),0,192,5,50,100,1,0,0,0,0,0,0,0,0,
    			104, TechTypes.None.ordinal(), UnitTypes.Zerg_Devourer.ordinal(),25,2,100,1, UpgradeTypes.Zerg_Flyer_Attacks.ordinal(), DamageTypes.Explosive.ordinal(), ExplosionTypes.Corrosive_Acid.ordinal(),0,192,0,0,0,1,0,0,0,0,0,0,0,0,
    			105, TechTypes.Mind_Control.ordinal(), UnitTypes.Protoss_Dark_Archon.ordinal(),8,1,22,1, UpgradeTypes.None.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Mind_Control.ordinal(),0,256,0,0,0,1,1,0,0,0,0,1,0,0,
    			106, TechTypes.Feedback.ordinal(), UnitTypes.Protoss_Dark_Archon.ordinal(),8,1,22,1, UpgradeTypes.None.ordinal(), DamageTypes.Ignore_Armor.ordinal(), ExplosionTypes.Feedback.ordinal(),0,320,0,0,0,1,1,0,0,0,0,1,0,0,
    			107, TechTypes.Optical_Flare.ordinal(), UnitTypes.Terran_Medic.ordinal(),8,1,22,1, UpgradeTypes.None.ordinal(), DamageTypes.Independent.ordinal(), ExplosionTypes.Optical_Flare.ordinal(),0,288,0,0,0,0,1,0,0,0,0,0,0,0,
    			108, TechTypes.Maelstrom.ordinal(), UnitTypes.Protoss_Dark_Archon.ordinal(),0,1,1,1, UpgradeTypes.None.ordinal(), DamageTypes.Independent.ordinal(), ExplosionTypes.Maelstrom.ordinal(),0,320,0,0,0,1,1,0,0,0,0,1,0,0,
    			109, TechTypes.None.ordinal(), UnitTypes.Zerg_Lurker.ordinal(),20,2,37,1, UpgradeTypes.Zerg_Missile_Attacks.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Enemy_Splash.ordinal(),0,192,20,20,20,0,1,0,0,0,0,0,0,0,
    			111, TechTypes.None.ordinal(), UnitTypes.Protoss_Dark_Templar.ordinal(),40,3,30,1, UpgradeTypes.Protoss_Ground_Weapons.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,15,0,0,0,0,1,0,0,0,0,0,0,0,
    			86, TechTypes.None.ordinal(), UnitTypes.Hero_Dark_Templar.ordinal(),45,1,30,1, UpgradeTypes.Protoss_Ground_Weapons.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,15,0,0,0,0,1,0,0,0,0,0,0,0,
    			85, TechTypes.None.ordinal(), UnitTypes.Hero_Zeratul.ordinal(),100,1,22,1, UpgradeTypes.Protoss_Ground_Weapons.ordinal(), DamageTypes.Normal.ordinal(), ExplosionTypes.Normal.ordinal(),0,15,0,0,0,0,1,0,0,0,0,0,0,0,
    			130, TechTypes.None.ordinal(), UnitTypes.None.ordinal(),0,0,0,0, UpgradeTypes.None.ordinal(), DamageTypes.None.ordinal(), ExplosionTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    			131, TechTypes.None.ordinal(), UnitTypes.None.ordinal(),0,0,0,0, UpgradeTypes.None.ordinal(), DamageTypes.None.ordinal(), ExplosionTypes.None.ordinal(),0,0,0,0,0,0,0,0,0,0,0,0,0,0

    		
    		
    		
    	};
    	return data;
    }

    public int[] getUnitSizeTypes(){
    	int[] data = new int[]{0,1,3,4,5};
		return data;
	}

	public int[] getBulletTypes(){
		int[] data = new int[211];
		for (int i=0; i<211;i++){
			data[i]=i;
		}
		return data;
	}

	public int[] getDamageTypes(){
		int[] data = new int[]{0,1,3,4,5,6};
		return data;
	}

	public int[] getExplosionTypes(){
		int[] data = new int[26];
		for (int i=0; i<26;i++){
			data[i]=i;
		}
		return data;
	}
	
	public int[] getUnitCommandTypes(){
		int[] data = new int[45];
		for (int i=0; i<45;i++){
			data[i]=i;
		}
		return data;
	}

	public int[] getOrderTypes(){
		int[] data = new int[]{0,1,2,3,4,5,6,10,12,13,14,15,16,17,18,20,23,24,27,29,30,32,33,34,36,37,38,39,40,41,42,43, 
			44,46,47,49,50,51,55,58,63,64,65,66,67,68,69,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,
			91,92,93,94,95,96,97,98,99,101,102,103,104,105,106,107,109,110,111,112,113,115,116,117,118,119,120,121,122,123,124,
			125,127,128,129,131,132,133,137,138,139,140,141,142,143,144,145,146,147,148,149,150,152,153,154,155,156,157,158,159,
			160,161,162,163,165,166,167,168,169,170,171,172,173,174,175,176,177,179,180,181,182,183,184,185,186,187,188,189,190
		};

		return data;
	}

    
    public void loadTypeData() {
        // unit types
        int[] unitTypeData = getUnitTypes();
        for (int index = 0; index < unitTypeData.length; index += UnitType.numAttributes) {
            UnitType type = new UnitType(unitTypeData, index);
            type.setName(UnitType.UnitTypes.values()[type.getID()].name());
            unitTypes.put(type.getID(), type);
        }

        // tech types
        int[] techTypeData = getTechTypes();
        for (int index = 0; index < techTypeData.length; index += TechType.numAttributes) {
            TechType type = new TechType(techTypeData, index);
            type.setName(TechType.TechTypes.values()[type.getID()].name());
            techTypes.put(type.getID(), type);
        }

        // upgrade types
        int[] upgradeTypeData = getUpgradeTypes();
        for (int index = 0; index < upgradeTypeData.length; index += UpgradeType.numAttributes) {
            UpgradeType type = new UpgradeType(upgradeTypeData, index);
            type.setName(UpgradeType.UpgradeTypes.values()[type.getID()].name());
            upgradeTypes.put(type.getID(), type);
        }

        // weapon types
        int[] weaponTypeData = getWeaponTypes();
        for (int index = 0; index < weaponTypeData.length; index += WeaponType.numAttributes) {
            WeaponType type = new WeaponType(weaponTypeData, index);
            type.setName(WeaponType.WeaponTypes.values()[type.getID()].name());
            weaponTypes.put(type.getID(), type);
        }

        // unit size types
        int[] unitSizeTypeData = getUnitSizeTypes();
        for (int index = 0; index < unitSizeTypeData.length; index += UnitSizeType.numAttributes) {
            UnitSizeType type = new UnitSizeType(unitSizeTypeData, index);
            type.setName(UnitSizeType.UnitSizes.values()[type.getID()].name());
            unitSizeTypes.put(type.getID(), type);
        }

        // bullet types
        int[] bulletTypeData = getBulletTypes();
        for (int index = 0; index < bulletTypeData.length; index += BulletType.numAttributes) {
            BulletType type = new BulletType(bulletTypeData, index);
            
            type.setName(BulletType.BulletTypes.values()[type.getID()].name());
            bulletTypes.put(type.getID(), type);
        }

        // damage types
        int[] damageTypeData = getDamageTypes();
        for (int index = 0; index < damageTypeData.length; index += DamageType.numAttributes) {
            DamageType type = new DamageType(damageTypeData, index);
            type.setName(DamageType.DamageTypes.values()[index].name());
            damageTypes.put(type.getID(), type);
        }

        // explosion types
        int[] explosionTypeData = getExplosionTypes();
        for (int index = 0; index < explosionTypeData.length; index += ExplosionType.numAttributes) {
            ExplosionType type = new ExplosionType(explosionTypeData, index);
            type.setName(ExplosionType.ExplosionTypes.values()[type.getID()].name());
            explosionTypes.put(type.getID(), type);
        }

        // unitCommand types
        int[] unitCommandTypeData = getUnitCommandTypes();
        for (int index = 0; index < unitCommandTypeData.length; index += UnitCommandType.numAttributes) {
            UnitCommandType type = new UnitCommandType(unitCommandTypeData, index);
            type.setName(UnitCommandType.UnitCommandTypes.values()[type.getID()].name());
            unitCommandTypes.put(type.getID(), type);
        }

        // order types
        int[] orderTypeData = getOrderTypes();
        for (int index = 0; index < orderTypeData.length; index += OrderType.numAttributes) {
            OrderType type = new OrderType(orderTypeData, index);
            type.setName(OrderType.OrderTypeTypes.values()[index].name());
//			System.out.println("ID: "+ type.getID()+" Name: "+ type.getName());
            orderTypes.put(type.getID(), type);
        }
    }
 // type data accessors
    public UnitType getUnitType(int unitID) {
        return unitTypes.get(unitID);
    }

    public TechType getTechType(int techID) {
        return techTypes.get(techID);
    }

    public UpgradeType getUpgradeType(int upgradeID) {
        return upgradeTypes.get(upgradeID);
    }

    public WeaponType getWeaponType(int weaponID) {
        return weaponTypes.get(weaponID);
    }

    public UnitSizeType getUnitSizeType(int sizeID) {
        return unitSizeTypes.get(sizeID);
    }

    public BulletType getBulletType(int bulletID) {
        return bulletTypes.get(bulletID);
    }

    public DamageType getDamageType(int damageID) {
        return damageTypes.get(damageID);
    }

    public ExplosionType getExplosionType(int explosionID) {
        return explosionTypes.get(explosionID);
    }

    public UnitCommandType getUnitCommandType(int unitCommandID) {
        return unitCommandTypes.get(unitCommandID);
    }

    public OrderType getOrderType(int orderID) {
        return orderTypes.get(orderID);
    }

    public Collection<UnitType> unitTypes() {
        return unitTypes.values();
    }

    public Collection<TechType> techTypes() {
        return techTypes.values();
    }

    public Collection<UpgradeType> upgradeTypes() {
        return upgradeTypes.values();
    }

    public Collection<WeaponType> weaponTypes() {
        return weaponTypes.values();
    }

    public Collection<UnitSizeType> unitSizeTypes() {
        return unitSizeTypes.values();
    }

    public Collection<BulletType> bulletTypes() {
        return bulletTypes.values();
    }

    public Collection<DamageType> damageTypes() {
        return damageTypes.values();
    }

    public Collection<ExplosionType> explosionTypes() {
        return explosionTypes.values();
    }

    public Collection<UnitCommandType> unitCommandTypes() {
        return unitCommandTypes.values();
    }

    public Collection<OrderType> orderTypes() {
        return orderTypes.values();
    }

}
