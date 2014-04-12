/**
* This file is based on and translated from the open source project: Sparcraft
* https://code.google.com/p/sparcraft/
* author of the source: David Churchill
**/
package bwmcts.sparcraft;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javabot.JNIBWAPI;
import javabot.types.TechType;
import javabot.types.UnitType;
import javabot.types.UnitType.UnitTypes;
import javabot.types.UpgradeType;
import bwmcts.sparcraft.players.*;
import bwmcts.test.JNIBWAPI_LOAD;

public class SearchExperiment {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		SearchExperiment se=new SearchExperiment(args[0]);

		try {
			se.runExperiment();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	HashMap<Integer,List<Player>> players=new HashMap<Integer,List<Player>>();
	HashMap<Integer,List<String>> playerStrings=new HashMap<Integer,List<String>>();
    List<GameState> states=new ArrayList<GameState>();
    Map map;
    boolean  showDisplay= false;

    String  resultsFile;
    boolean                        appendTimeStamp=true;
    String                 timeString;
    String                 configFileFull;
    String                 configFileSmall;
    String                 imageDir;

	Player[] resultsPlayers=new Player[2];
	//int                        resultsStateNumber;
	//int                        resultsNumUnits;
	//int                        resultsEval;
	//int                        resultsRounds;
	//int                        resultsTime;
    int[][]                         numGames;
	int[][]                         numWins;
    int[][]                         numLosses;
	int[][]                         numDraws;
	
	
	JNIBWAPI bwapi;
	Random					rand = new Random();

	public SearchExperiment(String configFile){
		bwapi = new JNIBWAPI_LOAD(null);
		bwapi.loadTypeData();
		AnimationFrameData.Init();
		PlayerProperties.Init();
		WeaponProperties.Init(bwapi);
		UnitProperties.Init(bwapi);	
		configFileSmall = getBaseFilename(configFile);
		map = new Map(40,40);
		setCurrentDateTime();
		parseConfigFile(configFile);
		try {
			writeConfig(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		setupResults();
		
	}

	public void setupResults(){
	    int np1 = players.get(0).size();
	    int np2 = players.get(1).size();
	
	    //resultsStateNumber  = new int[np1][np2];
		//resultsNumUnits     = ivvv(np1, ivv(np2, iv()));
		//resultsEval         = ivvv(np1, ivv(np2, iv()));
		//resultsRounds       = ivvv(np1, ivv(np2, iv()));
		//resultsTime         = dvvv(np1, dvv(np2, dv()));
	    numGames            = new int[np1][np2];
	    numWins             = new int[np1][np2];
	    numLosses           = new int[np1][np2];
	    numDraws            = new int[np1][np2];
	}
	
	public void writeConfig(String configfile) throws IOException
	{
		File f= new File(getConfigOutFileName());

		
		List<String> lines=getLines(configfile);
		BufferedWriter out = new BufferedWriter(new FileWriter(f));
		for (String s : lines){
			out.write(s);
			out.newLine();
		}
		

        out.close();

	}
	
	public void writeResultsSummary() throws IOException
	{
		File f= new File(getResultsSummaryFileName());

		
		BufferedWriter out = new BufferedWriter(new FileWriter(f));


        
		
		for (int p1=0; p1 < players.get(0).size(); ++p1)
		{
	        for (int p2=0; p2 < players.get(1).size(); ++p2)
		    {
	            double score = 0;
	            if (numGames[p1][p2] > 0)
	            {
	                score = ((double)numWins[p1][p2] / (double)(numGames[p1][p2])) + 0.5*((double)numDraws[p1][p2] / (double)numGames[p1][p2]);
	            }
	            out.write(String.valueOf(score));
	        }
	
	        out.newLine();
		}
	
	    out.close();
	}
	
	public void padString(String str, int length)
	{
	    while (str.length() < length)
	    {
	        str = str + " ";
	    }
	}
	
	public String getResultsSummaryFileName()
	{
	    String res = resultsFile;
	    
	    if (appendTimeStamp)
	    {
	        res += "_" + getDateTimeString();
	    }
	
	    res += "_results_summary.txt";
	    return res;
	}
	
	public String getResultsOutFileName()
	{
	    String res = resultsFile;
	    
	    if (appendTimeStamp)
	    {
	        res += "_" + getDateTimeString();
	    }
	
	    res += "_results_raw.txt";
	    return res;
	}
	
	public String getConfigOutFileName()
	{
	    String conf = resultsFile;
	    
	    if (appendTimeStamp)
	    {
	        conf += "_" + getDateTimeString();
	    }
	
	    conf += "_config.txt";
	    return conf;
	}
	
	public String getDateTimeString()
	{
	    return timeString;
	}
	
	public void setCurrentDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd__HH_mm_ss");
		Calendar cal = Calendar.getInstance();
		
		timeString = dateFormat.format(cal.getTime());
	}
	
	public List<String> getLines(String filename) throws IOException
	{
		File f= new File(filename);
		if (!f.exists()){
			System.out.println("Problem Opening File: " + filename);
		}

		BufferedReader br = new BufferedReader(new FileReader(f));
		List<String> lines=new ArrayList<String>();
	    // each line of the file will be a new player to add
		String s;
	    while ((s=br.readLine()) !=null)
	    {
	    	
	    	lines.add(s);
	       
	    }
	
	    br.close();
	    return lines;
	}
	
	public void parseConfigFile(String filename)
	{

	    List<String> lines;
		try {
			lines = getLines(filename);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	
	    for (String line : lines)
	    {
	    	if (line.startsWith("#") || line.length()<4){
	    		continue;
	    	}
	    	String[] data = line.split(" ");
	    	int i=0;
	        String option=data[i++];
	
	        if (option.equals("Player"))
	        {
	            addPlayer(line);
	        }
	        else if (option.equals("State"))
	        {
	            addState(line);
	        }
	        else if (option.equals("MapFile"))
	        {
	            String fileString= data[i++];
	            
	            map = new Map(50,50);
	            //TODO
	            //map.load(fileString);
	        }
	        else if (option.equals("Display"))
	        {
	            option=data[i++];
	            
	            if (option.equals("true"))
	            {
	                showDisplay = true;
	                
	            }
	        }
	        else if (option.equals("ResultsFile"))
	        {
	            String fileString=data[i++];
	            String append=data[i++];
	            
	            resultsFile = fileString;
	
	            appendTimeStamp = Boolean.parseBoolean(append);
	        }
	        else if (option.equals("PlayerUpgrade"))
	        {
	            int playerID=Integer.parseInt(data[i++]);
	            String upgradeName=data[i++];
	            int upgradeLevel=Integer.parseInt(data[i++]);
	
	            PlayerProperties.Get(playerID).SetUpgradeLevel(bwapi.getUpgradeType(UpgradeType.UpgradeTypes.valueOf(upgradeName).ordinal()), upgradeLevel);
	        }
	        else if (option.equals("PlayerTech"))
	        {
	            int playerID=Integer.parseInt(data[i++]);
	            String techName=data[i++];

	
	            PlayerProperties.Get(playerID).SetResearched(bwapi.getTechType(TechType.TechTypes.valueOf(techName).ordinal()), true);
	        }
	        else
	        {
	            System.out.println("Invalid Option in Configuration File: " + option);
	            return;
	        }
	    }
	}
	
	public void addState(String line)
	{
		
		String[] data = line.split(" ");
		int i=1;
	    // the first number is the playerID
	    //String state= data[i++];
	    String stateType= data[i++];
	    int numStates= Integer.parseInt(data[i++]);

	
	    if (stateType.equals("StateSymmetric"))
	    { 
	        int xLimit= Integer.parseInt(data[i++]);
	        int yLimit= Integer.parseInt(data[i++]);
	
	        List<String>unitVec=new ArrayList<String>();
	        List<Integer> numUnitVec=new ArrayList<Integer>();
	
	        while (i<data.length)
	        {
	        	
	            unitVec.add(data[i++]);
	            numUnitVec.add(Integer.parseInt(data[i++]));
	        }
	
	        for (int s=0; s<numStates; ++s)
	        {
	            states.add(getSymmetricState(bwapi,unitVec, numUnitVec, xLimit, yLimit));
	        }
	    }
	    else if (stateType.equals("StateRawDataFile"))
	    {
	        String filename=data[i++];

	        for (int s=0; s<numStates; ++s)
	        {
	            states.add(new GameState(filename));
	        }
	    }
	    else if (stateType.equals("StateDescriptionFile"))
	    {
	        String filename= data[i++];

	        for (int s=0; s<numStates; ++s)
	        {
	            parseStateDescriptionFile(bwapi,filename);
	        }
	    }
	    else if (stateType.equals("SeparatedState"))
	    {
	        int xLimit= Integer.parseInt(data[i++]);
	        int yLimit= Integer.parseInt(data[i++]);
	        int cx1= Integer.parseInt(data[i++]); 
	        int cy1= Integer.parseInt(data[i++]); 
	        int cx2= Integer.parseInt(data[i++]); 
	        int cy2= Integer.parseInt(data[i++]);

	
	        List<String>unitVec=new ArrayList<String>();
	        List<Integer> numUnitVec=new ArrayList<Integer>();
	        while (i<data.length)
	        {
	        	
	            unitVec.add(data[i++]);
	            numUnitVec.add(Integer.parseInt(data[i++]));
	        }
	        System.out.println(cx1+","+ cy1+","+ cx2+","+ cy2+","+ xLimit+","+ yLimit);
	        for (int s=0; s<numStates/2; ++s)
	        {
	        	
	            addSeparatedState(bwapi,unitVec, numUnitVec, cx1, cy1, cx2, cy2, xLimit, yLimit);
	        }
	    }
	    else
	    {
	        System.out.println("Invalid State Type in Configuration File: " + stateType);
	        return;
	    } 
	}
	
	public void parseStateDescriptionFile(JNIBWAPI bwapi, String fileName)
	{
	    List<String> lines;
		try {
			lines = getLines(fileName);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	    
	    GameState currentState=new GameState();
	
	    for (String s : lines)
	    {
	    	String[] t=s.split(" ");
	    	if (t.length==4){
		        String unitType=t[0];
		        int playerID=Integer.parseInt(t[1]);
		        int x=Integer.parseInt(t[2]);
		        int y=Integer.parseInt(t[3]);
		        currentState.addUnit(getUnitType(bwapi,unitType), playerID, new Position(x, y));
	    	} else {
	    		System.out.println("Wrong string: " + s);
	    	}
	
	        
	    }
	
	    states.add(currentState);
	}
	//BWAPI unittype
	public UnitType getUnitType(JNIBWAPI bwapi,String unitTypeString)
	{
	    UnitType type= bwapi.getUnitType(UnitTypes.valueOf(unitTypeString).ordinal());
	
	    //System::checkSupportedUnitType(type);
	
	    return type;
	}
	
	public void addGameState(GameState state) throws Exception
	{
	    if (states.size() >= 10000)
	    {
	        throw new Exception("Search Experiment cannot contain more than 10,000 states.");
	    }
	}
	
	public void addPlayer(String line)
	{
	    String[] data=line.split(" ");
	    // Regular expressions for line validation (if I ever want to use them)
	    //std::regex ScriptRegex("[a-zA-Z]+[ ]+[0-1][ ]+[a-zA-Z]+[ ]*");
	    //std::regex AlphaBetaRegex("[a-zA-Z]+[ ]+[0-1][ ]+[a-zA-Z]+[ ]+[0-9]+[ ]+[0-9]+[ ]+[a-zA-Z]+[ ]+[a-zA-Z]+[ ]+[a-zA-Z]+[ ]+[a-zA-Z]+[ ]+[a-zA-Z]+[ ]+[a-zA-Z]+[ ]*");
	    //std::regex UCTRegex("[a-zA-Z]+[ ]+[0-1][ ]+[a-zA-Z]+[ ]+[0-9]+[ ]+[0-9.]+[ ]+[0-9]+[ ]+[0-9]+[ ]+[a-zA-Z]+[ ]+[a-zA-Z]+[ ]+[a-zA-Z]+[ ]+[a-zA-Z]+[ ]+[a-zA-Z]+[ ]+[a-zA-Z]+[ ]*");
	    //std::regex PortfolioRegex("[a-zA-Z]+[ ]+[0-1][ ]+[a-zA-Z]+[ ]+[0-9]+[ ]+[a-zA-Z]+[ ]+[0-9]+[ ][0-9]+[ ]*");
	
	    // the first number is the playerID
	    int i=1;
	    //String player;
	    int playerID=Integer.parseInt(data[i++]);
	    int playerModelID=0;
	    String playerModelString=data[i++];
	    

	    
	    //TODO
	    //playerStrings[playerID].add(playerModelString);
	    List<Player>player=new ArrayList<Player>();
	    if (players.containsKey(playerID)){
	    	player=players.get(playerID);
	    };


	   	if (playerModelString.equals("AttackClosest"))		
	    { 
	        player.add( new Player_AttackClosest(playerID)); 
	    }
		else if (playerModelString.equals("Kite"))					
	    { 
			player.add(new Player_Kite(playerID)); 
	    }
		else if (playerModelString.equals("KiteDPS"))				
	    { 
			player.add(new Player_KiteDPS(playerID)); 
	    }
		else if (playerModelString.equals("NOKDPS"))		
	    { 
			player.add(new Player_NoOverKillAttackValue(playerID)); 
	    }
		else if (playerModelString.equals("Random"))					
	    { 
			player.add(new Player_Random(playerID)); 
	    }
	    /*else if (playerModelID == PlayerModels.PortfolioGreedySearch)				
	    { 
	        String enemyPlayerModel;
	        int timeLimit=0;
	        int iterations=1;
	        int responses=0;
	
	        //iss >> timeLimit;
	        //iss >> enemyPlayerModel;
	        //iss >> iterations;
	        //iss >> responses;
	
	        players[playerID].push_back(PlayerPtr(new Player_PortfolioGreedySearch(playerID, PlayerModels::getID(enemyPlayerModel), iterations, responses, timeLimit))); 
	    }
	    else if (playerModelID == PlayerModels.AlphaBeta)
	    {
	        int             timeLimitMS;
	        int             maxChildren;
	        String     moveOrdering;
	        String     evalMethod;
	        String     playoutScript1;
	        String     playoutScript2;
	        String     playerToMoveMethod;
	        String     opponentModelScript;
	
	        // read in the values
	        //iss >> timeLimitMS;
	        //iss >> maxChildren;
	        //iss >> moveOrdering;
	        //iss >> evalMethod;
	        //iss >> playoutScript1;
	        //iss >> playoutScript2;
	        //iss >> playerToMoveMethod;
	        //iss >> opponentModelScript;
	
	        // convert them to the proper enum types
	        //int moveOrderingID      = MoveOrderMethod::getID(moveOrdering);
	        //int evalMethodID        = EvaluationMethods::getID(evalMethod);
	        //int playoutScriptID1    = PlayerModels::getID(playoutScript1);
	        //int playoutScriptID2    = PlayerModels::getID(playoutScript2);
	        //int playerToMoveID      = PlayerToMove::getID(playerToMoveMethod);
	        //int opponentModelID     = PlayerModels::getID(opponentModelScript);
	
	        // construct the parameter object
	        AlphaBetaSearchParameters params;
	
	        // give the default parameters we can't set via options
		    params.setMaxDepth(50);
	        params.setSearchMethod(SearchMethods::IDAlphaBeta);
	
	        // set the parameters from the options in the file
		    params.setMaxPlayer(playerID);
		    params.setTimeLimit(timeLimitMS);
	        params.setMaxChildren(maxChildren);
	        params.setMoveOrderingMethod(moveOrderingID);
	        params.setEvalMethod(evalMethodID);
	        params.setSimScripts(playoutScriptID1, playoutScriptID2);
	        params.setPlayerToMoveMethod(playerToMoveID);
		
	        // add scripts for move ordering
	        if (moveOrderingID == MoveOrderMethod::ScriptFirst)
	        {
	            params.addOrderedMoveScript(PlayerModels::NOKDPS);
	            params.addOrderedMoveScript(PlayerModels::KiterDPS);
	            //params.addOrderedMoveScript(PlayerModels::Cluster);
	            //params.addOrderedMoveScript(PlayerModels::AttackWeakest);
	        }
	
	        // set opponent modeling if it's not none
	        if (opponentModelID != PlayerModels::None)
	        {
	            if (playerID == 0)
	            {
	                params.setSimScripts(playoutScriptID1, opponentModelID);
	                params.setPlayerModel(1, playoutScriptID2);
	            }
	            else
	            {
	                params.setSimScripts(opponentModelID, playoutScriptID2);
	                params.setPlayerModel(0, playoutScriptID1);
	            }
	        }
	
	        PlayerPtr abPlayer(new Player_AlphaBeta(playerID, params, TTPtr((TranspositionTable *)NULL)));
	        players[playerID].push_back(abPlayer); 
	    }
	    else if (playerModelID == PlayerModels.UCT)
	    {
	        int             timeLimitMS;
	        double          cValue;
	        int             maxTraversals;
	        int             maxChildren;
	        std::string     moveOrdering;
	        std::string     evalMethod;
	        std::string     playoutScript1;
	        std::string     playoutScript2;
	        std::string     playerToMoveMethod;
	        std::string     opponentModelScript;
	
	        // read in the values
	        iss >> timeLimitMS;
	        iss >> cValue;
	        iss >> maxTraversals;
	        iss >> maxChildren;
	        iss >> moveOrdering;
	        iss >> evalMethod;
	        iss >> playoutScript1;
	        iss >> playoutScript2;
	        iss >> playerToMoveMethod;
	        iss >> opponentModelScript;
	
	        // convert them to the proper enum types
	        int moveOrderingID      = MoveOrderMethod::getID(moveOrdering);
	        int evalMethodID        = EvaluationMethods::getID(evalMethod);
	        int playoutScriptID1    = PlayerModels::getID(playoutScript1);
	        int playoutScriptID2    = PlayerModels::getID(playoutScript2);
	        int playerToMoveID      = PlayerToMove::getID(playerToMoveMethod);
	        int opponentModelID     = PlayerModels::getID(opponentModelScript);
	
	        // construct the parameter object
	        UCTSearchParameters params;
	
	        // set the parameters from the options in the file
		    params.setTimeLimit(timeLimitMS);
	        params.setCValue(cValue);
		    params.setMaxPlayer(playerID);
	        params.setMaxTraversals(maxTraversals);
	        params.setMaxChildren(maxChildren);
	        params.setMoveOrderingMethod(moveOrderingID);
	        params.setEvalMethod(evalMethodID);
	        params.setSimScripts(playoutScriptID1, playoutScriptID2);
	        params.setPlayerToMoveMethod(playerToMoveID);
	        //params.setGraphVizFilename("__uct.txt");
	
	        // add scripts for move ordering
	        if (moveOrderingID == MoveOrderMethod::ScriptFirst)
	        {
	            params.addOrderedMoveScript(PlayerModels::NOKDPS);
	            params.addOrderedMoveScript(PlayerModels::KiterDPS);
	            //params.addOrderedMoveScript(PlayerModels::Cluster);
	        }
		
	        // set opponent modeling if it's not none
	        if (opponentModelID != PlayerModels::None)
	        {
	            if (playerID == 0)
	            {
	                params.setSimScripts(playoutScriptID1, opponentModelID);
	                params.setPlayerModel(1, playoutScriptID2);
	            }
	            else
	            {
	                params.setSimScripts(opponentModelID, playoutScriptID2);
	                params.setPlayerModel(0, playoutScriptID1);
	            }
	        }
	
	        PlayerPtr uctPlayer(new Player_UCT(playerID, params));
	        players[playerID].push_back(uctPlayer); 
	    }*/
		else
	    {
	        System.out.println("Invalid Player Type in Configuration File: " + playerModelString);
	    }
	   	players.put(playerID, player);
	}
	
	public Position getRandomPosition(int xlimit, int ylimit)
	{
		int x = xlimit - (rand.nextInt() % (2*xlimit));
		int y = ylimit - (rand.nextInt() % (2*ylimit));
	
		return new Position(x, y);
	}
	
	public GameState getSymmetricState(JNIBWAPI bwapi, List<String> unitTypes, List<Integer> numUnits, int xLimit, int yLimit)
	{
		GameState state=new GameState();
	
	    Position mid= new Position(640, 360);
	
	    // for each unit type to add
	    for (int i=0; i<unitTypes.size(); i++)
	    {
	        UnitType type = bwapi.getUnitType(UnitTypes.valueOf(unitTypes.get(i)).ordinal());
	
	        // add the symmetric unit for each count in the numUnits Vector
	        for (int u=0; u<numUnits.get(i); u++)
		    {
	            Position r= new Position((rand.nextInt() % (2*xLimit)) - xLimit, (rand.nextInt() % (2*yLimit)) - yLimit);
	            Position u1= new Position(mid.getX() + r.getX(), mid.getY() + r.getY());
	            Position u2= new Position(mid.getX() - r.getX(), mid.getY() - r.getY());
	
	            state.addUnit(type, Players.Player_One.ordinal(), u1);
	            state.addUnit(type, Players.Player_Two.ordinal(), u2);
		    }
	    }
	    
		state.finishedMoving();
		return state;
	}
	
	public void addSeparatedState(JNIBWAPI bwapi,  List<String> unitTypes, List<Integer> numUnits,
	                                                int cx1, int cy1, 
	                                                int cx2, int cy2,
									                int xLimit, int yLimit)
	{
		GameState state=new GameState();
	    GameState state2=new GameState();
	
	    // for each unit type to add
	    for (int i=0; i<unitTypes.size(); i++)
	    {
	    	
	        UnitType type = bwapi.getUnitType(UnitTypes.valueOf(unitTypes.get(i)).ordinal());
	
	        // add the symmetric unit for each count in the numUnits Vector
	        for (int u=0; u<numUnits.get(i); u++)
		    {
	            Position r=new Position((rand.nextInt() % (2*xLimit)) - xLimit, (rand.nextInt() % (2*yLimit)) - yLimit);
	            Position u1=new Position(Math.abs(cx1 + r.getX()), Math.abs(cy1 + r.getY()));
	            Position u2=new Position(Math.abs(cx2 - r.getX()), Math.abs(cy2 - r.getY()));
	            state.addUnit(type, Players.Player_One.ordinal(), u1);
	            state.addUnit(type, Players.Player_Two.ordinal(), u2);
	            state2.addUnit(type, Players.Player_One.ordinal(), u2);
	            state2.addUnit(type, Players.Player_Two.ordinal(), u1);
		    }
	    }
	    
		state.finishedMoving();
		state2.finishedMoving();
		
		states.add(state);
	    states.add(state2);
	}
	
	public String[][] getExpDescription(int p1Ind, int p2Ind, int state)
	{/*
	    // 2-column description vector
	    svv desc(2, sv());
	
	    std::stringstream ss;
	
	    desc[0].push_back("Player 1:");
	    desc[0].push_back("Player 2:");
	    desc[0].push_back("State #:");
	    desc[0].push_back("Units:");
	
	    for (size_t p1(0); p1 < players[0].size(); ++p1)
		{
	        std::stringstream ss;
	        ss << "P1 " << p1 << ":";
	        desc[0].push_back(ss.str());
	    }
	
	    for (size_t p2(0); p2 < players[1].size(); ++p2)
		{
	        std::stringstream ss;
	        ss << "P2 " << p2 << ":";
	        desc[0].push_back(ss.str());
	    }
	
	    for (size_t p1(0); p1 < players[0].size(); ++p1)
		{
	        for (size_t p2(0); p2 < players[1].size(); ++p2)
		    {
	            std::stringstream ps;    
	            ps << p1 << " vs " << p2;
	            desc[0].push_back(ps.str());
	        }
	    }
	
	    ss << PlayerModels::getName(players[0][p1Ind]->getType());        desc[1].push_back(ss.str()); ss.str(std::string());
	    ss << PlayerModels::getName(players[1][p2Ind]->getType());        desc[1].push_back(ss.str()); ss.str(std::string());
	    ss << state << " of " << states.size();                         desc[1].push_back(ss.str()); ss.str(std::string());
	    ss << states[state].numUnits(0);                                desc[1].push_back(ss.str()); ss.str(std::string());
	
	    for (size_t p1(0); p1 < players[0].size(); ++p1)
		{
	        desc[1].push_back(PlayerModels::getName(players[0][p1]->getType()));
	    }
	
	    for (size_t p2(0); p2 < players[1].size(); ++p2)
		{
	        desc[1].push_back(PlayerModels::getName(players[1][p2]->getType()));
	    }
	
	    char buf[30];
		for (size_t p1(0); p1 < players[0].size(); ++p1)
		{
	        for (size_t p2(0); p2 < players[1].size(); ++p2)
		    {
	            double score = 0;
	            if (numGames[p1][p2] > 0)
	            {
	                score = ((double)numWins[p1][p2] / (double)(numGames[p1][p2])) + 0.5*((double)numDraws[p1][p2] / (double)numGames[p1][p2]);
	            }
	
	            sprintf(buf, "%.7lf", score);
			    desc[1].push_back(std::string(buf));
	        }
		}
	
	    return desc;*/
	    return null;
	}
	
	public String getBaseFilename(String filename)
	{
	    for (int i=filename.length()-1; i>=0; i--)
	    {
	        if (filename.charAt(i) == '/' || filename.charAt(i) == '\\')
	        {
	            return filename.substring(i+1,filename.length());
	        }
	    }
	
	    return filename;
	}
	
	public void runExperiment() throws Exception
	{
		
		File f= new File(getResultsOutFileName());

		
		BufferedWriter out = new BufferedWriter(new FileWriter(f));
	    
	    // set the map file for all states
	    for (int state=0; state < states.size(); ++state)
		{
	        states.get(state).setMap(map);
	    }
	

	
		out.write("   P1    P2    ST  UNIT       EVAL    RND           MS | UnitType PlayerID CurrentHP XPos YPos");
	    out.newLine();
		// for each player one player
		for (int p1Player=0; p1Player < players.get(0).size(); p1Player++)
		{
			// for each player two player
			for (int p2Player=0; p2Player < players.get(1).size(); p2Player++)
			{
				// for each state we care about
				for (int state=0; state < states.size(); ++state)
				{
					
	                //fprintf(stderr, "%s  ", configFileSmall.c_str());
					//fprintf(stderr, "%5d %5d %5d %5d", (int)p1Player, (int)p2Player, (int)state, (int)states[state].numUnits(Players::Player_One));
					//sprintf(buf, "%5d %5d %5d %5d", (int)p1Player, (int)p2Player, (int)state, (int)states[state].numUnits(Players::Player_One));
	                //results << buf;
	
					//resultsPlayers[0].push_back(p1Player);
					//resultsPlayers[1].push_back(p2Player);
					//resultsStateNumber[p1Player][p2Player].push_back(state);
					//resultsNumUnits[p1Player][p2Player].push_back(states[state].numUnits(Players::Player_One));
					
					// get player one
					Player playerOne =players.get(0).get(p1Player);
	

					// get player two
					Player playerTwo =players.get(1).get(p2Player);

	
					// construct the game
					Game g= new Game(states.get(state), playerOne, playerTwo, 20000,showDisplay);

	
					// play the game to the end
					float time=System.currentTimeMillis();
					g.play();
					time=System.currentTimeMillis()-time;
					
					int gameEval = g.getState().eval(0, EvaluationMethods.LTD2)._val;
	
	                numGames[p1Player][p2Player]++;
	                if (gameEval > 0)
	                {
	                    numWins[p1Player][p2Player]++;
	                }
	                else if (gameEval < 0)
	                {
	                    numLosses[p1Player][p2Player]++;
	                }
	                else if (gameEval == 0)
	                {
	                    numDraws[p1Player][p2Player]++;
	                }
	
	                //out.write(String.format( " %10d %6d %12.2lf", gameEval, g.getRounds(), time));


					//resultsEval[p1Player][p2Player].push_back(gameEval);
					//resultsRounds[p1Player][p2Player].push_back(g.getRounds());
					//resultsTime[p1Player][p2Player].push_back(ms);
	                
	                out.write(printStateUnits( g.getState()));
	                
	                out.newLine();
	                
	                writeResultsSummary();
				}
			}
		}
	    
	    out.close();

	}
	
	
	public String printStateUnits( GameState state)
	{
		StringBuffer results=new StringBuffer();
	    for (int p=0; p<Constants.Num_Players; p++)
	    {
	        for (int u=0; u<state.numUnits(p); u++)
	        {
	            Unit unit=state.getUnit(p,u);
	            unit.currentPosition(state.getTime());
	                        
	            results.append(unit.toString());
	        }
	    }
	    return results.toString();
	}
}
