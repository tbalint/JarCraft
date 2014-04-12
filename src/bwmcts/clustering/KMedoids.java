package bwmcts.clustering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import bwmcts.sparcraft.Unit;

public class KMedoids {
	
	public static int q = 20;

	public HashMap<Integer, List<Unit>> getClusters(Unit[] units, int k, double hpMultiplier) {
	
		List<Unit> unitList = Arrays.asList(units);
		
		List<Unit> medoids = selectRandomUnits(k, unitList);
		
		List<KMedoidCluster> clusters = cluster(k, medoids, unitList);
		
		double utility = utility(clusters);
		
		int i = 0;
		while(i < q){
			
			List<Unit> newMedoids = newMedoids(medoids, unitList);
			List<KMedoidCluster> newClusters = cluster(k, newMedoids, unitList);
			double newUtility = utility(newClusters);
			
			if (newUtility > utility){
				utility = newUtility;
				medoids = newMedoids;
				clusters = newClusters;
				i=0;
			} else {
				i++;
			}
		}
		
		System.out.println("Utility: " + utility);
		
		return toClusterMap(clusters);
		
	}
	
	private HashMap<Integer, List<Unit>> toClusterMap(List<KMedoidCluster> clusters) {
		
		HashMap<Integer, List<Unit>> map = new HashMap<Integer, List<Unit>>();
		
		int i = 0;
		for(KMedoidCluster cluster : clusters)
			map.put(i++, cluster.ClusterMembers);
		
		return map;
	}

	private double utility(List<KMedoidCluster> clusters) {
		
		double distance = 0;
		
		for(KMedoidCluster cluster : clusters){
			for (Unit unit : cluster.ClusterMembers){
				distance += distance(cluster.Medoid, unit);
			}
		}
		
		return -distance;
	}

	private List<Unit> newMedoids(List<Unit> seeds, List<Unit> units) {
		
		// Clone seeds
		List<Unit> newMedoids = new ArrayList<Unit>();
		newMedoids.addAll(seeds);
		
		// Remove random seed
		int rs = (int) Math.floor(Math.random() * seeds.size());
		Unit randomSeed = seeds.get(rs);
		newMedoids.remove(randomSeed);
		
		// Add random non-seed
		Unit randomAnswer = null;
		while(true){
			int ri = (int) Math.floor(Math.random() * units.size());
			randomAnswer = units.get(ri);
			if (!seeds.contains(randomAnswer))
				break;
		}
		newMedoids.add(randomAnswer);
		
		return newMedoids;
	}

	private List<KMedoidCluster> cluster(int k, List<Unit> seeds, List<Unit> units) {
		
		// Make cluster map
		List<KMedoidCluster> clusters = new ArrayList<KMedoidCluster>();
		
		// Add seeds
		for (Unit seed : seeds)
			clusters.add(new KMedoidCluster(seed));
		
		// Add units to clusters
		for(Unit unit : units){
			KMedoidCluster closestCluster = null;
			double closestDistance = Double.MAX_VALUE;
			for (KMedoidCluster cluster : clusters){
				double distance = distance(unit, cluster.Medoid);
				if (distance < closestDistance){
					closestDistance = distance;
					closestCluster = cluster;
				}
			}
			closestCluster.ClusterMembers.add(unit);
		}
		
		return clusters;
	}
	
	private static double distance(Unit a, Unit b) {
		
		// TODO : Implement
		
		return 0.0;
		
	}

	@Override
	public String toString(){
		return "KMedoids";
	}
	
	private static List<Unit> selectRandomUnits(int k, List<Unit> units) {
		
		List<Unit> selected = new ArrayList<Unit>();
		
		while(selected.size() < k){
			int rand = (int) Math.floor( Math.random() * units.size() );
			Unit random = units.get(rand);
			if(!selected.contains(random))
				selected.add(random);
		}
		
		return selected;
	}
	
}
