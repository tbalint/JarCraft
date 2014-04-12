package bwmcts.uct;

public class UctConfig {

	private double K = 1.6f;
	private int maxChildren = 20;
	private int maxPlayerIndex = 0;
	private boolean debug = false;
	private int simulationSteps = Integer.MAX_VALUE;
	private boolean nokModelling = true;
	private boolean LTD2 = true;
	
	public UctConfig(int maxPlayerIndex){
		this.maxPlayerIndex = maxPlayerIndex;
	}
	
	public UctConfig(int maxPlayerIndex, boolean debug){
		this.maxPlayerIndex = maxPlayerIndex;
		this.debug = debug;
	}
	
	public UctConfig(
							double k, 
							int maxChildren, 
							int maxPlayerIndex, 
							boolean debug, 
							int simulationSteps,
							boolean nokModelling, 
							boolean LTD2) {
		super();
		K = k;
		this.maxChildren = maxChildren;
		this.maxPlayerIndex = maxPlayerIndex;
		this.debug = debug;
		this.simulationSteps = simulationSteps;
		this.nokModelling = nokModelling;
		this.LTD2 = LTD2;
	}
	
	public boolean isLTD2() {
		return LTD2;
	}

	public void setLTD2(boolean lTD2) {
		LTD2 = lTD2;
	}

	public double getK() {
		return K;
	}

	public void setK(double k) {
		K = k;
	}

	public int getMaxChildren() {
		return maxChildren;
	}

	public void setMaxChildren(int maxChildren) {
		this.maxChildren = maxChildren;
	}

	public int getMaxPlayerIndex() {
		return maxPlayerIndex;
	}

	public void setMaxPlayerIndex(int maxPlayerIndex) {
		this.maxPlayerIndex = maxPlayerIndex;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public int getSimulationSteps() {
		return simulationSteps;
	}

	public void setSimulationSteps(int simulationSteps) {
		this.simulationSteps = simulationSteps;
	}

	public boolean isNokModelling() {
		return nokModelling;
	}

	public void setNokModelling(boolean nokModelling) {
		this.nokModelling = nokModelling;
	}
	
	public String toString(){
		return "K: "+ K+ "\tmaxChildren: "+maxChildren+"\tmaxPlayerIndex: "+maxPlayerIndex+"\tdebug: "+debug+
				"\tsimulationSteps: "+simulationSteps+"\tnokModelling: "+nokModelling+"\tLTD2: "+LTD2;

	}

}
