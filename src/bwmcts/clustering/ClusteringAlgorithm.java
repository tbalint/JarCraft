package bwmcts.clustering;

import java.util.List;

import bwmcts.sparcraft.Unit;

public interface ClusteringAlgorithm {

	List<List<Unit>> getClusters(Unit[] units, int h, double hp);

	public String toString();
}
