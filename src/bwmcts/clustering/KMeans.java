package bwmcts.clustering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bwmcts.sparcraft.Position;
import bwmcts.sparcraft.Unit;

public class KMeans implements ClusteringAlgorithm {
	
	public List<List<Unit>> getClusters(Unit[] uarr, int k, double hp) {
		List<Unit> units = new ArrayList<Unit>();
		for(Unit u : uarr){
			if (u==null)
				break;
			if (u._currentHP<=0)
				continue;
			units.add(u);
		}
		return getClusters(units, k, hp);
	}
	

	public List<List<Unit>> getClusters(List<Unit> units, int k, double hp) {

		List<Position> seeds = selectRandomUnitPosition(k, units);

		while(true){
			
			// Assign to clusters
			List<KMeansCluster> clusters = assignToClusters(k, seeds, units);

			// Update cluster means
			boolean change = updateClusterMeans(clusters);
			
			// If no change return clusters
			if (!change){
				List<List<Unit>> finalClusters = new ArrayList<List<Unit>>();
				for(KMeansCluster cluster : clusters)
					finalClusters.add(cluster.getUnits());
				return finalClusters;
			}
			
			// Update seeds
			seeds.clear();
			for(KMeansCluster cluster : clusters)
				seeds.add(cluster.getMean());

		}

	}
	
	

	private double distance(Position a, Position b) {

		double disX = a.getX() - b.getX();
		double disY = a.getY() - b.getY();
		
		double distance = (double) Math.sqrt(disX*disX + disY*disY);

		return distance;
	}

	private boolean updateClusterMeans(List<KMeansCluster> clusters) {

		boolean change = false;
		for(KMeansCluster cluster : clusters){
			if (cluster.getUnits().isEmpty())
				continue;
			Position mean = KMeansCluster.calcMean(cluster);
			if (!mean.equals(cluster.getMean())){
				cluster.setMean(mean);
				change = true;
			}
		}

		return change;
		
	}

	private List<KMeansCluster> assignToClusters(int k, List<Position> seeds, List<Unit> units) {

		// Make cluster map
		List<KMeansCluster> clusters = new ArrayList<KMeansCluster>();
		for(Position seed : seeds)
			clusters.add(new KMeansCluster(new ArrayList<Unit>(), seed));
		
		// Add data to clusters
		for(Unit unit : units){

			KMeansCluster closestCluster = null;
			double minDistance = Double.MAX_VALUE;

			for(KMeansCluster cluster : clusters){
				double distance = distance(unit.pos(), cluster.getMean());
				if (distance < minDistance){
					minDistance = distance;
					closestCluster = cluster;
				}
			}

			closestCluster.getUnits().add(unit);

		}
		
		return clusters;
	}

	private List<Position> selectRandomUnitPosition(int k, List<Unit> units) {

		List<Position> selected = new ArrayList<Position>();

		k = (int) Math.min(k, units.size());
		while(selected.size() < k ){
			int rand = (int) Math.floor( Math.random() * units.size() );
			Position random = units.get(rand).pos();
			if(!selected.contains(random))
				selected.add(random);
		}

		return selected;
	}
	@Override
	public String toString(){
		return "Kmeans";
	}
}
