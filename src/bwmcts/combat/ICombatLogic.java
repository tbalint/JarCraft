package bwmcts.combat;

import javabot.JNIBWAPI;

public interface ICombatLogic {
	
	/**
	 * Performs combat logic using the specific time.
	 * @param bwapi
	 * 		The BWAPI representing the game state
	 * @param time
	 * 		The time in ms. to perform the logic.
	 */
	void act(JNIBWAPI bwapi, int time);

}
