package bwmcts.uct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UctStats {

	private List<Integer> iterations;
	private Map<String, Integer> selectedActions;
	
	public UctStats() {
		super();
		this.iterations = new ArrayList<Integer>();
		this.selectedActions = new HashMap<String, Integer>();;
	}
	
	public void reset() {
		this.iterations.clear();
		this.selectedActions = new HashMap<String, Integer>();
	}

	public List<Integer> getIterations() {
		return iterations;
	}

	public void setIterations(List<Integer> iterations) {
		this.iterations = iterations;
	}

	public Map<String, Integer> getSelectedActions() {
		return selectedActions;
	}

	public void setSelectedActions(Map<String, Integer> selectedActions) {
		this.selectedActions = selectedActions;
	}


}
