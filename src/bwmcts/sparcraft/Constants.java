
/**
* This file is based on and translated from the open source project: Sparcraft
* https://code.google.com/p/sparcraft/
* author of the source: David Churchill
**/
package bwmcts.sparcraft;

public class Constants {

		// number of players in the game
	
		static int Num_Players				= 2;
		static int  TILE_SIZE  =32;
		// maximum number of units a player can have
		public static int Max_Units					= 128;

		// max depth the search can ever handle
		static int Max_Search_Depth			= 50;

		// number of directions that units can move
		public static int Num_Directions				= 4;

		// max number of ordered moves in a search depth
		static int Max_Ordered_Moves			= 10;

		// distance moved for a 'move' command
		static int Move_Distance				= 16;

        // add between a move and attack as penalty
		static int Move_Penalty             = 4;

        // add range to units because of bounding boxes
		static int Range_Addition       = 32;

		// maximum number of moves possible for any unit
		public static int Max_Moves					= Max_Units + Num_Directions + 1;
		static boolean   Use_Unit_Bounding			= false;
		static int Pass_Move_Duration			= 20;
		static float  Min_Unit_DPF				= 0.1f;
		static int Starting_Energy		= 50;
        

		// directions of movement
		public static int Move_Dir[][] = new int[][]{{-1,0}, {1,0}, {0,1}, {0,-1} };
		public static int[] Move_DirX=new int[]{-1,1,0,0};
		public static int[] Move_DirY=new int[]{0,0,1,-1};
		
		
}
