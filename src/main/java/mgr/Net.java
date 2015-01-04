package mgr;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class Net {

	int numVertices;
	int numEdges;
	HashMap<Integer, Agent> agentsVertices;
	Graph<Integer, String> net;

	public Net(int n, int k) {

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
		// if (net.getVertexCount() == numVertices)
		// System.out.println("Vertex adding - completed!");
		Integer[][] connections = randomConnectionsMatrix();
		int edge = 0;
		for (int i = 0; i < numVertices; i++) {
			for (int j = 0; j < numVertices; j++) {
				if (connections[i][j] == 1) {
					net.addEdge(String.valueOf(edge), i + 1, j + 1);
				}
				edge++;
			}

		}
		// List<List<Integer>> connections = randomConnections();
		// System.out.println("Connections established!");
		/*
		 * for (List<Integer> con : connections) {
		 * net.addEdge(con.get(0).toString() + "-" + con.get(1).toString(),
		 * con.get(0), con.get(1));
		 * 
		 * }
		 */
		// System.out.println("Graph is OK: " + isNumOfEdgesConst());

	}

	private void printConnectionsMatrix(Integer[][] connections) {
		for (int i = 0; i < connections.length; i++) {
			System.out.println();
			System.out.print(i + "    ");
			for (int j = 0; j < connections[i].length; j++) {
				System.out.print(connections[i][j] + " ");
			}
		}
		System.out.println();
	}

	private int countSumOfRow(Integer[][] table, int columnNumber) {
		int sumRow = 0;
		int sumColumn = 0;

		for (int i = 0; i < numVertices; i++) {
			sumColumn = sumColumn + table[i][columnNumber];
			sumRow = sumRow + table[columnNumber][i];
		}
		if (sumColumn == sumRow) {
			return sumRow;
		} else
			throw new IndexOutOfBoundsException();
	}

	private Integer[][] createZerosMatrix() {

		Integer totalCon[][] = new Integer[numVertices][numVertices];

		for (int i = 0; i < numVertices; i++) {
			for (int j = 0; j < numVertices; j++) {
				totalCon[i][j] = 0;
			}
		}
		return totalCon;
	}

	private List<Integer> createIntegersVector(int start, int repeats) {

		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < repeats; i++) {
			for (int j = 0; j <= numVertices - start; j++) {
				list.add(start + j);
			}
		}
		return list;

	}

	private Integer[][] randomConnectionsMatrix() {
		// List<Integer> listAll = createIntegersVector(1, numEdges);
		Integer totalCon[][] = createZerosMatrix();
		for (int i = 0; i < numVertices; i++) {
			// printMatrixConnection(totalCon);
			List<Integer> list = createIntegersVector(i + 2, 1);
			while (countSumOfRow(totalCon, i) < numEdges && list.size() > 0) {
				int idx = new Random().nextInt(list.size());
				Integer random = list.get(idx);
				list.remove(random);
				if (countSumOfRow(totalCon, random - 1) < numEdges) {
					totalCon[i][random - 1] = 1;
					totalCon[random - 1][i] = 1;
				}
			}
		}
		return totalCon;
	}

	public void printConnections(List<List<Integer>> connections) {
		for (List<Integer> nCon : connections) {
			System.out.println();
			for (Integer item : nCon)
				System.out.print(item + " ");
		}
	}
}
