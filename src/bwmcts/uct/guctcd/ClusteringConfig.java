package bwmcts.uct.guctcd;

import bwmcts.clustering.ClusteringAlgorithm;

public class ClusteringConfig {

	private double hpMulitplier;
	private int clusters;
	private ClusteringAlgorithm clusterAlg;
	
	public ClusteringConfig(double hpMulitplier, int clusters, ClusteringAlgorithm clusterAlg) {
		super();
		this.hpMulitplier = hpMulitplier;
		this.clusters = clusters;
		this.clusterAlg = clusterAlg;
	}
	
	public ClusteringAlgorithm getClusterAlg() {
		return clusterAlg;
	}

	public void setClusterAlg(ClusteringAlgorithm clusterAlg) {
		this.clusterAlg = clusterAlg;
	}
	
	public double getHpMulitplier() {
		return hpMulitplier;
	}

	public void setHpMulitplier(double hpMulitplier) {
		this.hpMulitplier = hpMulitplier;
	}

	public int getClusters() {
		return clusters;
	}

	public void setClusters(int clusters) {
		this.clusters = clusters;
	}
	
	public String toString(){
		return clusterAlg.toString()+" - hpMulitplier: "+hpMulitplier+"\tclusters: "+clusters;
		
	}
	
}
