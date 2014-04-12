/**
 * OIRGINAL CODE FROM http://www.itu.dk/~sestoft/bsa.html, http://www.itu.dk/~sestoft/bsa/Match2.java
 */
package bwmcts.clustering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import bwmcts.sparcraft.Unit;

public class UPGMA implements ClusteringAlgorithm {
	int K;			// The number of clusters created so far
	UPCluster[] cluster;		// The nodes (clusters) of the resulting tree
	List<Unit> input;
	private double hpMultiplier;
	private double distMultiplier;
	
	@Override
	public List<List<Unit>> getClusters(Unit[] units, int numClusters, double hpMultiplier) {
		
		this.hpMultiplier = hpMultiplier;
		
		init(units);
		
		return getClusters(numClusters);
		
	}
	
	@Override
	public String toString(){
		return "UPGMA";
	}

	private void init(Unit[] in){

		input = new ArrayList<Unit>();
		for (int i=0; i<in.length;i++){
			if (in[i]!=null && in[i].isAlive())
				input.add(in[i]);
		}
		double[][] ds = createDistanceMatrix(input);
	    int N = ds.length;
	    K = N;
	    if (K==0)
	    	return;
	    cluster = new UPCluster[2*N-1];
	    for (int i=0; i<N; i++) 
	    	cluster[i] = new UPCluster(i, ds[i]);
	    
	    while (K < 2*N-1)
	    	findAndJoin();
	}
	
	public UPCluster getRoot()
	{ return cluster[K-1]; }
	  
	public double d(int i, int j) 
	{ return cluster[Math.max(i, j)].dmat[Math.min(i, j)]; }
		
	void findAndJoin() { // Find closest two live clusters and join them
		int mini = -1, minj = -1;
	    double mind = Double.POSITIVE_INFINITY;
	    for (int i=0; i<K; i++) 
	    	if (cluster[i].live())
		for (int j=0; j<i; j++) 
			if (cluster[j].live()) {
				double d = d(i, j);
				if (d < mind) {
					mind = d;
			      	mini = i;
			      	minj = j;
			    }
			}
	    join(mini, minj);
	}
	
	public void join(int i, int j) { // Join i and j to form node K
		//  System.out.println("Joining " + (i+1) + " and " + (j+1) + " to form "    + (K+1) + " at height " + (int)(d(i, j) * 50)/100.0);
		double[] dmat = new double[K];
		for (int m=0; m<K; m++)
		  if (cluster[m].live() && m != i && m != j) 
		dmat[m] = (d(i, m) * cluster[i].card + d(j, m) * cluster[j].card)
		          / (cluster[i].card + cluster[j].card);
		cluster[K] = new UPCluster(K, cluster[i], cluster[j], d(i, j) / 2, dmat);
		cluster[i].kill(); 
		cluster[j].kill();
		K++;
	}
	
	public List<List<Unit>> getClusters(int clusters){
		if (K==0)
			return null;
		List<UPCluster> up = cutTree(this.getRoot(), clusters);
		List<List<Unit>> result = new ArrayList<List<Unit>>();
		for (int i=0; i < up.size(); i++){
			List<Unit> tmp = new ArrayList<Unit>();
			for (int p: getLeafs(up.get(i))){
				tmp.add(this.input.get(p-1));
			}
			
			result.add(tmp);
		}
	
		return result;
	}
	
	public List<Integer> getLeafs(UPCluster c){
		List<Integer> ids = new ArrayList<Integer>();
		if (c.left != null){
			ids.addAll(getLeafs(c.left));
		}
		if (c.right != null){
			ids.addAll(getLeafs(c.right));
		}
		if (c.left == null && c.right == null){
			ids.add(c.lab);
		}
		return ids;
	}
	
	
	public List<UPCluster> cutTree(UPCluster c, int clusterLimit){
		List<UPCluster> up=new ArrayList<UPCluster>();
		Queue<UPCluster> q=new LinkedList<UPCluster>();
		if (c.card > 0 && c.left != null && c.right != null){
			q.add(c);
			up.add(c);
			while (up.size()<clusterLimit && !q.isEmpty()){
				UPCluster current =q.poll();
				if (current==null){break;}
				if (current.card > 0 && current.left != null && current.right != null){
					up.remove(current);
					up.add(current.left);
					up.add(current.right);
					if (current.left.height>current.right.height){
						q.add(current.left);
						q.add(current.right);
					} else{
						q.add(current.right);
						q.add(current.left);
					}
				}
			}
			
		} else {
			up.add(c);
		}
		return up;
	}
	
	public double[][] createDistanceMatrix(List<Unit> strings){
		double[][] ds = new double[strings.size()][];
		
		if (strings.size() > 0){
			ds[0] = new double[]{};
			for (int i=1; i < strings.size();i++){
				double[] tmp = new double[i];
				for (int j=0;j<i;j++){
					//This implementation follows from Algorithms on Strings, Trees and Sequences by Dan Gusfield and Chas Emerick's implementation of the Levenshtein distance algorithm from http://www.merriampark.com/ld.htm					
					tmp[j] =getDistance(strings.get(i), strings.get(j));
				}
				ds[i] = tmp;
			}
		}
		return ds;
	}
	
	private double getDistance(Unit a, Unit b){
		double distance=0;
		if (a.typeID()!=b.typeID()){
			distance=1000000;
		}
		distance+=a.getDistanceSqToUnit(b, b.firstTimeFree())*distMultiplier;//*1000
		distance+=Math.abs(a.currentHP()-b.currentHP())*hpMultiplier;
		
		return distance;
	}
		
	public void printMatrix(double[][] m){
		for (double[] d : m){
			for (double i : d){
				System.out.print(i+" ");
			}
			System.out.println();
		}
	}


}

// UPGMA clusters or trees, built by the UPGMA algorithm
class UPCluster {
	int lab;			// Cluster identifier
	int card;			// The number of sequences in the cluster
	double height;		// The height of the node
	UPCluster left, right;	// Left and right children, or null
	double[] dmat;		// Distances to lower-numbered nodes, or null

	public UPCluster(int lab, double[] dmat) {	// Leaves = single sequences
		this.lab = lab + 1; 
    	card = 1;
    	this.dmat = dmat;
	}

	public UPCluster(int lab, UPCluster left, UPCluster right, double height, 
			double[] dmat) { 
		this.lab = lab + 1; 
	    this.left = left;
	    this.right = right;
	    card = left.card + right.card;
	    this.height = height;
	    this.dmat = dmat;
	}

	public boolean live()
	{ return dmat != null; }

	public void kill() 
	{ dmat = null; }

}
