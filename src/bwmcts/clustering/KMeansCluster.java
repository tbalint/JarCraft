package bwmcts.clustering;

import java.util.List;

import bwmcts.sparcraft.Position;
import bwmcts.sparcraft.Unit;

public class KMeansCluster {

	private List<Unit> units;
	private Position mean;
	
	public KMeansCluster(List<Unit> units, Position mean) {
		super();
		this.units = units;
		this.mean = mean;
	}

	public KMeansCluster(List<Unit> units) {
		super();
		this.units = units;
		this.mean = KMeansCluster.calcMean(this);
	}
	
	public static Position calcMean(KMeansCluster cluster) {

		Position mean = new Position(0, 0);
		double x = 0;
		double y = 0;
		for(Unit unit : cluster.getUnits()){
			x += unit.pos().getX();
			y += unit.pos().getY();
		}

		mean.setX((int)(x / (double)cluster.getUnits().size()));
		mean.setY((int)(y / (double)cluster.getUnits().size()));
		return mean;
	}

	public List<Unit> getUnits() {
		return units;
	}

	public void setUnits(List<Unit> units) {
		this.units = units;
	}

	public Position getMean() {
		return mean;
	}

	public void setMean(Position mean) {
		this.mean = mean;
	}
	
}
