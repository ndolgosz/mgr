package mgr;

import java.lang.invoke.WrongMethodTypeException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Timer;

import EDU.oswego.cs.dl.util.concurrent.ClockDaemon;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class Net {

	int numVertices;
	int numEdges;
	HashMap<Integer, Agent> agentsVertices;
	Graph<Integer, String> net;

	public Net(int n, int k) {
		if (n % 2 != 0) {
			System.out.println("tylko liczby parzyste!");
			throw new WrongMethodTypeException();
		}
		agentsVertices = new HashMap<Integer, Agent>();
		this.numVertices = n;
		this.numEdges = k;
		updateGraph();
	}

	private void updateGraph() {

		net = new UndirectedSparseGraph<Integer, String>();
		for (int i = 1; i <= numVertices; i++) {
			net.addVertex(i);
			agentsVertices.put(i, new Agent());
		}
		if (net.getVertexCount() == numVertices)
			System.out.println("Vertex adding - completed!");
		List<List<Integer>> connections = randomConnections();
		System.out.println("Connections established!");
		for (List<Integer> con : connections) {
			net.addEdge(con.get(0).toString() + "-" + con.get(1).toString(),
					con.get(0), con.get(1));

		}
		System.out.println("Graph is OK: " + isNumOfEdgesConst());

	}

	private List<List<Integer>> randomConnections() {

		HashMap<Integer, List<Integer>> totalCon = new HashMap<>();
		while (numOfFullNodes(totalCon) != numVertices) {
			totalCon = buildEmptyMap();
			Random generator = new Random();

			for (int i = 1; i <= numVertices; i++) {
				if (numOfFullNodes(totalCon) >= numVertices - 1
						|| isNodeThatIsNotFullContained(totalCon, i))
					break;
				while (totalCon.get(i).size() < numEdges) {

					int j = generator.nextInt((numVertices - i) + 1) + i;
					int iter = 0;
					while ((j == i || totalCon.get(i).contains(j) || (totalCon
							.containsKey(j) && totalCon.get(j).size() == numEdges))
							&& iter < 1000) {

						iter++;
						j = generator.nextInt((numVertices - i) + 1) + i;
						if (numOfFullNodes(totalCon) >= numVertices - 1
								|| isNodeThatIsNotFullContained(totalCon, i))
							break;
					}
					if (numOfFullNodes(totalCon) >= numVertices - 1
							|| isNodeThatIsNotFullContained(totalCon, i)
							|| iter >= 1000)
						break;

					totalCon.get(i).add(j);
					totalCon.get(j).add(i);

				}
			}
		}
		return mapToArray(totalCon);
	}

	public void updateOpinions(Agent agent1, Agent agent2) {
		double f;
		double diff = Math.abs(agent1.opinion - agent2.opinion);
		if (diff <= 180)
			f = (agent1.opinion + agent2.opinion) / 2;
		else
			f = (agent1.opinion + agent2.opinion) / 2 - 180;

		if (f >= 0) {
			agent1.opinion = f;
			agent2.opinion = f;
		} else {
			agent1.opinion = f + 360;
			agent2.opinion = f + 360;
		}
	}

	public void printConnections(List<List<Integer>> connections) {
		for (List<Integer> nCon : connections) {
			System.out.println();
			for (Integer item : nCon)
				System.out.print(item + " ");
		}
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

	public boolean isNumOfEdgesConst() {

		for (Integer n : net.getVertices())
			if (getDegree(n) != numEdges)
				return false;
		return true;
	}

	private int numOfFullNodes(HashMap<Integer, List<Integer>> totalCon) {

		int num = 0;
		for (int node : totalCon.keySet()) {
			if (totalCon.get(node).size() == numEdges)
				num++;
		}
		return num;
	}

	private boolean isNodeThatIsNotFullContained(
			HashMap<Integer, List<Integer>> totalCon, int node) {

		if (numOfFullNodes(totalCon) == numVertices - 2) {
			for (int n : totalCon.keySet()) {
				if (n > node && totalCon.get(n).size() < numEdges) {
					if (totalCon.get(node).contains(n))
						return true;
				}

			}
		}
		return false;
	}

	private HashMap<Integer, List<Integer>> buildEmptyMap() {
		HashMap<Integer, List<Integer>> tot = new HashMap<Integer, List<Integer>>();

		for (int i = 1; i <= numVertices; i++) {
			List<Integer> val = new ArrayList<Integer>();
			tot.put(i, val);
		}
		return tot;

	}
}
