#pragma once
#include <BWAPI.h>
#include "PlayerData.h"
#include <set>
#include <string>

namespace BWAPI
{
  class Unit;
  class Force;
  class PlayerImpl : public Player
  {
    private:
      int id;
    public:
      PlayerData* self;
      std::set<Unit*> units;
      void clear();
      PlayerImpl(int id);
      virtual int getID() const;
      virtual std::string getName() const;
      virtual const std::set<Unit*>& getUnits() const;
      virtual Race getRace() const;
      virtual PlayerType getType() const;
      virtual Force* getForce() const;
      virtual bool isAlly(Player* player) const;
      virtual bool isEnemy(Player* player) const;
      virtual bool isNeutral() const;
      virtual TilePosition getStartLocation() const;
      virtual bool isVictorious() const;
      virtual bool isDefeated() const;
      virtual bool leftGame() const;

      virtual int minerals() const;
      virtual int gas() const;
      virtual int gatheredMinerals() const;
      virtual int gatheredGas() const;
      virtual int repairedMinerals() const;
      virtual int repairedGas() const;
      virtual int refundedMinerals() const;
      virtual int refundedGas() const;
      virtual int spentMinerals() const;
      virtual int spentGas() const;

      virtual int supplyTotal() const;
      virtual int supplyUsed() const;
      virtual int supplyTotal(Race race) const;
      virtual int supplyUsed(Race race) const;

      virtual int allUnitCount(UnitType unit) const;
      virtual int visibleUnitCount(UnitType unit) const;
      virtual int completedUnitCount(UnitType unit) const;
      virtual int incompleteUnitCount(UnitType unit) const;
      virtual int deadUnitCount(UnitType unit) const;
      virtual int killedUnitCount(UnitType unit) const;

      virtual int  getUpgradeLevel(UpgradeType upgrade) const;
      virtual bool hasResearched(TechType tech) const;
      virtual bool isResearching(TechType tech) const;
      virtual bool isUpgrading(UpgradeType upgrade) const;

      virtual BWAPI::Color getColor() const;
      virtual int getTextColor() const;

      virtual int maxEnergy(UnitType unit) const;
      virtual double topSpeed(UnitType unit) const;
      virtual int groundWeaponMaxRange(UnitType unit) const;
      virtual int airWeaponMaxRange(UnitType unit) const;
      virtual int weaponMaxRange(WeaponType weapon) const;
      virtual int sightRange(UnitType unit) const;
      virtual int groundWeaponDamageCooldown(UnitType unit) const;
      virtual int armor(UnitType unit) const;

      virtual int getUnitScore() const;
      virtual int getKillScore() const;
      virtual int getBuildingScore() const;
      virtual int getRazingScore() const;
      virtual int getCustomScore() const;

      virtual bool isObserver() const;

      virtual int  getMaxUpgradeLevel(UpgradeType upgrade) const;
      virtual bool isResearchAvailable(TechType tech) const;
      virtual bool isUnitAvailable(UnitType unit) const;
  };
};
