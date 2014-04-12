package bwmcts.combat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import bwmcts.sparcraft.Position;
import javabot.JNIBWAPI;
import javabot.model.Unit;

public class NoOverKillAttackValueLogic implements ICombatLogic {

	Map<Integer, Integer> attackingUnits = new HashMap<Integer, Integer>();
	AttackClosestLogic closestLogic = new AttackClosestLogic();
	Map<Integer, Float> attackedUnits = new HashMap<Integer, Float>();
	Set<Unit> dyingUnits = new HashSet<Unit>(); 
	
	@Override
	public void act(JNIBWAPI bwapi, int time) {
		
		//System.out.println("--------------- " + bwapi.getMyUnits().size() + " units.");
	
		dyingUnits.clear();
		attackedUnits.clear();
		
		List<Unit> myUnits = bwapi.getMyUnits();
		
		for(Unit unit : myUnits){
			
			actUnit(bwapi, unit);
			
		}
	}

	private void actUnit(JNIBWAPI bwapi, Unit unit) {
		//System.out.println(bwapi.getUnitType(unit.getTypeID()).getName());
		
		List<Unit> enemyUnits = bwapi.getEnemyUnits();
		
		//bwapi.drawCircle(unit.getX(), unit.getY(), 6, BWColor.YELLOW, false, false);
		
		Position position = new Position(unit.getX(), unit.getY());
		
		// Find closest units enemy
		int groundRange = 0;
		int airRange = 0;
		
		if (bwapi.getUnitType(unit.getTypeID()).isCanAttackGround()){
			int weapon = bwapi.getUnitType(unit.getTypeID()).getGroundWeaponID();
			groundRange += bwapi.getWeaponType(weapon).getMaxRange();
			groundRange += 32;
		}
		if (bwapi.getUnitType(unit.getTypeID()).isCanAttackAir()){
			int weapon = bwapi.getUnitType(unit.getTypeID()).getAirWeaponID();
			airRange += bwapi.getWeaponType(weapon).getMaxRange();
			airRange += 32;
		}
		
		//System.out.println("Ground range = " + groundRange);
		//System.out.println("Air range = " + airRange);
		
		float highestValue = Float.MIN_VALUE;
		Unit bestEnemy = null;
		for(Unit enemy : enemyUnits){
			
			//Position enemyPosition = new Position(enemy.getX(), enemy.getY());
			
			//int distance = Util.distance(position, enemyPosition);
			int distance = position.getDistance(enemy.getX(), enemy.getY());
			//System.out.println("d = " + distance);
			//System.out.println("enemy = " + bwapi.getUnitType(enemy.getTypeID()).getName());

			if (inRange(bwapi, enemy, distance, groundRange, airRange)){

				float damagePerFrame = mostDamageCooldown(bwapi, enemy);
				//System.out.println("Damage per frame = " + damagePerFrame);
				
				if ( damagePerFrame / enemy.getHitPoints() > highestValue){
					if (attackedUnits.containsKey(enemy.getID()) && 
							attackedUnits.get(enemy.getID()) >= enemy.getHitPoints() + enemy.getShield()){
						continue;
					} else {
						highestValue = damagePerFrame / enemy.getHitPoints();
						bestEnemy = enemy;
					}
				}
				
			}
			
		}
		
		boolean outOfRange = false;
		if (bestEnemy == null){
			//System.out.println("No enemy in range found");
			closestLogic.actUnit(bwapi, unit);
			return;
		}
		
		//System.out.println("Best enemy found");
		float damage = getWeaponDamage(bwapi, unit, bestEnemy);
		
		Unit lastTarget = null;
		if (attackingUnits.containsKey(unit.getID()))
			lastTarget = bwapi.getUnit(attackingUnits.get(unit.getID()));
			
		if (lastTarget == null || bestEnemy.getID() != lastTarget.getID()){
			bwapi.rightClick(unit.getID(), bestEnemy.getID());
			attackingUnits.put(unit.getID(), bestEnemy.getID());
		}
		
		if (attackingUnits.containsKey(unit.getID())){
			Unit newTarget = bwapi.getUnit(attackingUnits.get(unit.getID()));
			if (outOfRange){
				//bwapi.drawLine(unit.getX(), unit.getY(), newTarget.getX(), newTarget.getY(), BWColor.GREEN, false);
			}else{
				//bwapi.drawLine(unit.getX(), unit.getY(), newTarget.getX(), newTarget.getY(), BWColor.RED, false);
				attackedUnits.put(newTarget.getID(), damage);
			}
		}
		
	}

	private float getWeaponDamage(JNIBWAPI bwapi, Unit unit, Unit enemy) {
		
		int enemyArmor = bwapi.getUnitType(unit.getTypeID()).getArmor();
		String enemySize = bwapi.getUnitSizeType(bwapi.getUnitType(unit.getTypeID()).getSizeID()).getName();
		
		// Find closest units enemy
		float groundDamage = 0;
		float airDamage = 0;
		
		if (bwapi.getUnitType(unit.getTypeID()).isCanAttackGround()){
			int weapon = bwapi.getUnitType(unit.getTypeID()).getGroundWeaponID();
			int type = bwapi.getWeaponType(weapon).getDamageTypeID();
			String damageType = bwapi.getDamageType(type).getName();
			groundDamage = calcDamage(bwapi, weapon, enemyArmor, enemySize, damageType);
		}
		if (bwapi.getUnitType(unit.getTypeID()).isCanAttackAir()){
			int weapon = bwapi.getUnitType(unit.getTypeID()).getAirWeaponID();
			int type = bwapi.getWeaponType(weapon).getDamageTypeID();
			String damageType = bwapi.getDamageType(type).getName();
			airDamage = calcDamage(bwapi, weapon, enemyArmor, enemySize, damageType);
		}
		
		// Is in air?
		if(bwapi.getUnitType(unit.getTypeID()).isFlyer() || unit.isLifted())
			return airDamage;
		
		return groundDamage;
	}

	private float calcDamage(JNIBWAPI bwapi, int weapon, int enemyArmor, String enemySize, String damageType) {
		
		float groundDamage = bwapi.getWeaponType(weapon).getDamageAmount();
		
		if (!damageType.equals("Ignore_Armor"))
			groundDamage -= enemyArmor;
		if (damageType.equals("Concussive")){
			if (enemySize.equals("Medium"))
				groundDamage = groundDamage * 0.5f;
			if (enemySize.equals("Large"))
				groundDamage = groundDamage * 0.25f;
		}
		if (damageType.equals("Explosive")){
			if (enemySize.equals("Medium"))
				groundDamage = groundDamage * 0.5f;
			if (enemySize.equals("Large"))
				groundDamage = groundDamage * 0.25f;
		}
		return Math.max(0.5f, groundDamage);
		
	}

	private float mostDamageCooldown(JNIBWAPI bwapi, Unit unit) {
		
		float damage = 0;
		if (bwapi.getUnitType(unit.getTypeID()).isCanAttackAir()){
			int air = bwapi.getUnitType(unit.getTypeID()).getAirWeaponID();
			float airDamage = (float)(bwapi.getWeaponType(air).getDamageAmount()) / (float)(bwapi.getWeaponType(air).getDamageCooldown());
			if (airDamage > damage){
				damage = airDamage;
			}
		}
		if (bwapi.getUnitType(unit.getTypeID()).isCanAttackGround()){
			int ground = bwapi.getUnitType(unit.getTypeID()).getGroundWeaponID();
			float groundDamage = (float)(bwapi.getWeaponType(ground).getDamageAmount()) / (float)(bwapi.getWeaponType(ground).getDamageCooldown());
			if (groundDamage > damage){
				damage = groundDamage;
			}
		}
		return damage;
		
	}

	private boolean inRange(JNIBWAPI bwapi, Unit unit, int distance, int groundRange, int airRange) {
		
		// Is in air?
		if(bwapi.getUnitType(unit.getTypeID()).isFlyer() || unit.isLifted())
			return distance < airRange;
		
		return distance < groundRange;
		
	}
}
