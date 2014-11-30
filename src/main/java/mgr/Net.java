package mgr;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;

public class Net {

	int numVertices;
	int numEdges;
	Graph<Integer, String> net;

	public Net(int n, int k) {
		this.numVertices = n;
		this.numEdges = k;
		updateGraph();
	}

	private void updateGraph() {
		net = new SparseMultigraph<Integer, String>();
		for (int i = 1; i <= numVertices; i++) {
			net.addVertex(i);
			System.out.println("vertex nr " + i + " added");
		}
		List<List<Integer>> connections = randomConnections();
		System.out.println("connections established");
		System.out.println(connections);
		for (List<Integer> con : connections) {
			System.out.println("Edge-1: " + con.get(0) + "  " + con.get(1));
			net.addEdge(con.get(0).toString() + "-" + con.get(1).toString(),
					con.get(0), con.get(1));

		}

	}

	private List<List<Integer>> randomConnections() {

		HashMap<Integer, List<Integer>> totalCon = new HashMap<>();
		Random generator = new Random();

		for (int i = 1; i <= numVertices; i++) {

			if (!totalCon.containsKey(i)) {
				List<Integer> l = new ArrayList<>();
				totalCon.put(i, l);
			}
			System.out
					.println("Node: " + i + " size " + totalCon.get(i).size());
			while (totalCon.get(i).size() < numEdges) {
				int j = generator.nextInt((numVertices - i)+1) + i;
				while (j == i || totalCon.get(i).contains(j)
						|| (totalCon.containsKey(j) && totalCon.get(j).size() >= numEdges)) {

					j = generator.nextInt((numVertices - i)+1) + i;//TODO
				}
				totalCon.get(i).add(j);
				if (totalCon.containsKey(j))
					totalCon.get(j).add(i);
				else {
					List<Integer> l = new ArrayList<>();
					l.add(i);
					totalCon.put(j, l);
				}
				System.out.println(totalCon.get(i));
			}
		}
		return mapToArray(totalCon);
	}

	public void printConnections(List<List<Integer>> connections) {
		for (List<Integer> nCon : connections) {
			System.out.println();
			for (Integer item : nCon)
				System.out.print(item + " ");
		}
	}

	private List<List<Integer>> generateListOfNodes() {

		List<List<Integer>> list = new ArrayList<List<Integer>>();
		for (int j = 1; j <= numVertices; j++) {
			List<Integer> i = new ArrayList<Integer>();
			for (int jj = 1; jj <= numEdges; jj++) {
				i.add(j);
			}
			list.add(i);
		}
		return list;
	}

	private int howManyEgdesLeft(HashMap<Integer, List<Integer>> map, int num) {

		int counts = 0;
		if (map.containsKey(num))
			counts = map.get(num).size();
		return numEdges - counts;

	}

	private List<List<Integer>> mapToArray(HashMap<Integer, List<Integer>> map) {

		List<List<Integer>> result = new ArrayList<>();
		for (Entry<Integer, List<Integer>> entry : map.entrySet()) {
			List<Integer> temp = new ArrayList<>();
			temp.add(entry.getKey());
			temp.addAll(entry.getValue());
			for (List<Integer> tmp : conPairs(temp)) {
				if (!result.contains(tmp))
					result.add(tmp);
			}
		}

		return result;
	}

	private List<List<Integer>> conPairs(List<Integer> con) {
		List<List<Integer>> list = new ArrayList<>();
		for (int i = 1; i < con.size(); i++) {
			List<Integer> t = new ArrayList<>();
			if (con.get(0) < con.get(i)) {
				t.add(con.get(0));
				t.add(con.get(i));
			} else {
				t.add(con.get(i));
				t.add(con.get(0));
			}

			list.add(t);

		}
		return list;
	}

	private int getDegree(int n) {
		return net.degree(n);
	}

	public HashMap<Integer, Integer> howManyEdges() {
		HashMap<Integer, Integer> degrees = new HashMap<>();
		for (Integer n : net.getVertices()) {
			degrees.put(n, getDegree(n));
			System.out.println("Vertex: " + n + " --> " + getDegree(n));
		}

		return degrees;
	}
	
 
}
