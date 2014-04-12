#pragma once
#include <string>
#include <set>
#include <BWAPI/Race.h>
#include "Type.h"
namespace BWAPI
{
  class UnitType;
  class UpgradeType : public Type
  {
  public:
    UpgradeType();
    UpgradeType(int id);

    /** Returns the name for the upgrade type. */
    const std::string &getName() const;
    const char *c_str() const;

    /** Returns the race the upgrade is for. For example, UpgradeTypes::Terran_Infantry_Armor.getRace()
     * will return Races::Terran. */
    Race getRace() const;

    /** Returns the mineral price for the first upgrade. */
    int mineralPrice(int level = 1) const;

    /** Returns the amount that the mineral price increases for each additional upgrade. */
    int mineralPriceFactor() const;

    /** Returns the vespene gas price for the first upgrade. */
    int gasPrice(int level = 1) const;

    /** Returns the amount that the vespene gas price increases for each additional upgrade. */
    int gasPriceFactor() const;

    /** Returns the number of frames needed to research the first upgrade. */
    int upgradeTime(int level = 1) const;

    /** Returns the number of frames that the upgrade time increases for each additional upgrade. */
    int upgradeTimeFactor() const;

    /** Returns the maximum number of times the upgrade can be researched. */
    int maxRepeats() const;

    /** Returns the type of unit that researches the upgrade. */
    UnitType whatUpgrades() const;

    /** Returns the type of unit that is additionally required for the upgrade. */
    UnitType whatsRequired(int level = 1) const;

    /** Returns the set of units that are affected by this upgrade. */
    const std::set<UnitType>& whatUses() const;
  };
  namespace UpgradeTypes
  {
    /** Given a string, this will return the upgrade type. */
    UpgradeType getUpgradeType(std::string name);

    /** Returns the set of all the UpgradeTypes. */
    const std::set<UpgradeType>& allUpgradeTypes();
    void init();
    extern const UpgradeType Terran_Infantry_Armor;
    extern const UpgradeType Terran_Vehicle_Plating;
    extern const UpgradeType Terran_Ship_Plating;
    extern const UpgradeType Zerg_Carapace;
    extern const UpgradeType Zerg_Flyer_Carapace;
    extern const UpgradeType Protoss_Ground_Armor;
    extern const UpgradeType Protoss_Air_Armor;
    extern const UpgradeType Terran_Infantry_Weapons;
    extern const UpgradeType Terran_Vehicle_Weapons;
    extern const UpgradeType Terran_Ship_Weapons;
    extern const UpgradeType Zerg_Melee_Attacks;
    extern const UpgradeType Zerg_Missile_Attacks;
    extern const UpgradeType Zerg_Flyer_Attacks;
    extern const UpgradeType Protoss_Ground_Weapons;
    extern const UpgradeType Protoss_Air_Weapons;
    extern const UpgradeType Protoss_Plasma_Shields;
    extern const UpgradeType U_238_Shells;
    extern const UpgradeType Ion_Thrusters;
    extern const UpgradeType Titan_Reactor;
    extern const UpgradeType Ocular_Implants;
    extern const UpgradeType Moebius_Reactor;
    extern const UpgradeType Apollo_Reactor;
    extern const UpgradeType Colossus_Reactor;
    extern const UpgradeType Ventral_Sacs;
    extern const UpgradeType Antennae;
    extern const UpgradeType Pneumatized_Carapace;
    extern const UpgradeType Metabolic_Boost;
    extern const UpgradeType Adrenal_Glands;
    extern const UpgradeType Muscular_Augments;
    extern const UpgradeType Grooved_Spines;
    extern const UpgradeType Gamete_Meiosis;
    extern const UpgradeType Metasynaptic_Node;
    extern const UpgradeType Singularity_Charge;
    extern const UpgradeType Leg_Enhancements;
    extern const UpgradeType Scarab_Damage;
    extern const UpgradeType Reaver_Capacity;
    extern const UpgradeType Gravitic_Drive;
    extern const UpgradeType Sensor_Array;
    extern const UpgradeType Gravitic_Boosters;
    extern const UpgradeType Khaydarin_Amulet;
    extern const UpgradeType Apial_Sensors;
    extern const UpgradeType Gravitic_Thrusters;
    extern const UpgradeType Carrier_Capacity;
    extern const UpgradeType Khaydarin_Core;
    extern const UpgradeType Argus_Jewel;
    extern const UpgradeType Argus_Talisman;
    extern const UpgradeType Caduceus_Reactor;
    extern const UpgradeType Chitinous_Plating;
    extern const UpgradeType Anabolic_Synthesis;
    extern const UpgradeType Charon_Boosters;
    extern const UpgradeType None;
    extern const UpgradeType Unknown;
  }
}
