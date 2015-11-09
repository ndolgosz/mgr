package mgr;

import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import edu.uci.ics.jung.algorithms.cluster.WeakComponentClusterer;
import edu.uci.ics.jung.algorithms.generators.random.BarabasiAlbertGenerator;
import edu.uci.ics.jung.algorithms.shortestpath.UnweightedShortestPath;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class NetCayley {

	public int numVertices;
	public int numEdges;
	public int deep;
	public int BM = -1;
	public int TI = -1;
	public double steepness;
	public HashMap<Integer, Agent> agentsVertices;
	public Map<Integer, Number> distanceBM;
	public Graph<Integer, Integer> net;
	public Map<Integer, Double> copyOfAgents;

	public NetCayley(int deep, int k) {
		this.deep = deep;

		numVertices = retrieveNumberOfVertices(deep);
		numEdges = k;
		this.steepness = 1.0;
		agentsVertices = new HashMap<Integer, Agent>();
		
		updateGraph(deep,numEdges);

		while (new WeakComponentClusterer<Integer, Integer>().transform(net)
				.size() != 1) {
			updateGraph(deep,numEdges);
		}
		numVertices = getMaxIdFromLeafs();
		
		this.copyOfAgents = getMapOfOpinions();

	}
	
	public NetCayley(int deep) {
		this.deep = deep;

		
		numEdges = 3;
		this.steepness = 1.0;
		agentsVertices = new HashMap<Integer, Agent>();

		updateGraph(deep,numEdges);

		while (new WeakComponentClusterer<Integer, Integer>().transform(net)
				.size() != 1) {
			updateGraph(deep,numEdges);
		}
		numVertices = getMaxIdFromLeafs();
		this.copyOfAgents = getMapOfOpinions();

	}

	private int retrieveNumberOfVertices(int deep) {

		int i = 0;
		int tmp = 1;
		while (i < deep) {
			tmp = tmp * 2 + 2;
			i++;
		}
		
		return tmp;
	}

	private Map<Integer, Double> getMapOfOpinions() {
		Map<Integer, Double> map = new HashMap<>();
		for (int i = 1; i <= numVertices; i++) {
			map.put(i, agentsVertices.get(i).getOpinion());
		}
		return map;
	}

	public void configureInformationModel(double prob, double steep) {
		this.steepness = steep;
		resetAgentsOpinion();
		chooseBM();
		UnweightedShortestPath<Integer, Integer> path = new UnweightedShortestPath<>(
				net);
		distanceBM = path.getDistanceMap(BM);
		chooseTI(prob);
		setWeightsToEveryAgent();
	}

	private void resetAgentsOpinion() {
		for (int i = 1; i <= numVertices; i++) {
			agentsVertices.get(i).setOpinion(copyOfAgents.get(i));
		}
	}

	private void updateGraph(int deep, int k) {

		net = new UndirectedSparseGraph<Integer, Integer>();

		//for (int i = 1; i <= numVertices; i++) {
			
			net.addVertex(1);
			agentsVertices.put(1, new Agent(1));
		//}
		int kk = 1;
		for (kk = 1; kk <= k; kk++){
			net.addVertex(kk+1);
			agentsVertices.put(kk+1, new Agent(kk+1));
			net.addEdge(kk, 1, kk+1);
		}
		int edge = kk;
		for (int deepInx = 2; deepInx <= deep; deepInx++) {
			
			for (Integer node : getLeafs()) {
				
				Integer maxId = getMaxIdFromLeafs();
				for(int kkk = 1; kkk < k; kkk++){
					
					net.addVertex(maxId + kkk);
					agentsVertices.put(maxId + kkk, new Agent(maxId + kkk));
					net.addEdge(edge, node, maxId + kkk);
					edge++;
				}

			}
		}

	}

	private List<Integer> getLeafs() {
		List<Integer> leafs = new ArrayList<Integer>();
		for (Integer i : net.getVertices()) {
			if (net.degree(i) == 1) {
				leafs.add(i);
			}
		}
		return leafs;
	}
	private Integer getMaxIdFromLeafs() {
		Integer leaf = 0;
		for (Integer i : net.getVertices()) {
			if (net.degree(i) == 1 && i > leaf) {
				leaf = i;
			}
		}
		return leaf;
	}

	private void setWeightsToEveryAgent() {
		for (int j = 1; j <= numVertices; j++) {
			agentsVertices.get(j).countWeight(this);
		}
	}

	private void chooseTI(double probability) {
		Random r = new Random();
		double d = r.nextDouble();
		if (d < probability) {
			TI = BM;
		} else {
			int tmp = r.nextInt(numVertices) + 1;
			
			while (tmp == BM) {
				tmp = r.nextInt(numVertices) + 1;
			}
			TI = tmp;
		}
	}

	private void chooseBM() {
		BM = 1;
	}

	public void setBMtoCenter() {

		BM = 1;
	}

	public void setTIdistBM(int distance) throws UnexpectedException {

		if (distance > deep) {
			throw new UnexpectedException("WRIOG DATA! DISTANCE NOT AVAILABLE");
		}
		for (int i = 1; i <= numVertices; i++) {
			if (distanceBM.get(i).equals(distance)) {
				TI = i;
				return;
			}
		}
		TI = -100;
	}

}
