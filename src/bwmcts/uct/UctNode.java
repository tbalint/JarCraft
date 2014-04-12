/**
* This file is based on and translated from the open source project: Sparcraft
* https://code.google.com/p/sparcraft/
* author of the source: David Churchill
**/
package bwmcts.uct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bwmcts.uct.NodeType;
import bwmcts.sparcraft.UnitAction;

public class UctNode {

	protected int visits;
	protected float totalScore;
	protected float uctValue;
	
	protected List<UnitAction> move;
	protected int movingPlayerIndex;
	protected NodeType type;
	
	protected List<UctNode> children;
	protected UctNode parent;
	protected HashMap<Integer, List<UnitAction>> possibleMoves;
	protected String label;
	
	public UctNode(UctNode parent, NodeType type, List<UnitAction> move, int movingPlayerIndex, String label) {
		
		this.visits = 0;
		this.totalScore = 0;
		this.uctValue = 0;
		this.movingPlayerIndex = movingPlayerIndex;
		this.parent = parent;
		this.children = new ArrayList<UctNode>();
		this.type = type;
		this.label = label;
		this.move = move;
		
	}

	public UctNode mostVisitedChild(){
		UctNode mostVisited = null;
		for(UctNode child : children){
			if (mostVisited == null || child.getVisits() > mostVisited.getVisits())
				mostVisited = child;
		}
		return mostVisited;
	}
	
	public List<UctNode> getChildren() {
		return children;
	}

	public void setChildren(List<UctNode> children) {
		this.children = children;
	}

	public UctNode getParent() {
		return parent;
	}

	public void setParent(UctNode parent) {
		this.parent = parent;
	}

	public int getVisits() {
		return visits;
	}

	public void setVisits(int visits) {
		this.visits = visits;
	}

	public float getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(float totalScore) {
		this.totalScore = totalScore;
	}

	public NodeType getType() {
		return type;
	}

	public void setType(NodeType type) {
		this.type = type;
	}

	public float getUctValue() {
		return uctValue;
	}

	public void setUctValue(float uctValue) {
		this.uctValue = uctValue;
	}

	public int getMovingPlayerIndex() {
		return movingPlayerIndex;
	}

	public void setMovingPlayerIndex(int movingPlayerIndex) {
		this.movingPlayerIndex = movingPlayerIndex;
	}
	
	public String moveString(){
		String moves = "";
		for(UnitAction a : move){
			moves += a.moveString() + "(" + a.pos().getX() + "," + a.pos().getY() + ");";
		}
		return moves;
	}

	public String print(int level) {
		
		String out = "";
		for(int n = 0; n < level; n++){
			out += "\t";
		}
		out += "<node label="+label+" score=" + totalScore / visits + " visited=" + visits + " type="+type+ " playerToMove=" + movingPlayerIndex + " moves=" + moveString();
		
		if (children.isEmpty()){
			out += "/>\n";
		} else {
			out += ">\n";
		}
		
		int next = level+1;
		for(UctNode child : children){
			
			out += child.print(next);
			
		}
		
		if (!children.isEmpty()){
			for(int n = 0; n < level; n++){
				out += "\t";
			}
			out += "</node>\n";
		}
		
		return out;
		
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public HashMap<Integer, List<UnitAction>> getPossibleMoves() {
		return possibleMoves;
	}

	public void setPossibleMoves(HashMap<Integer, List<UnitAction>> possibleMoves) {
		this.possibleMoves = possibleMoves;
	}

	public List<UnitAction> getMove() {
		return move;
	}

	public void setMove(List<UnitAction> move) {
		this.move = move;
	}
	
}
