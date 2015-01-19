package mgr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import edu.uci.ics.jung.algorithms.cluster.WeakComponentClusterer;
import edu.uci.ics.jung.algorithms.shortestpath.UnweightedShortestPath;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class Net {

    int numVertices;
    int numEdges;
    public int BM;
    public int TI;
    public double steepness;
    HashMap<Integer, Agent> agentsVertices;
    Map<Integer, Number> distanceBM;
    Graph<Integer, String> net;

    public Net(int n, int k) {
        this.steepness = 1.0;
        agentsVertices = new HashMap<Integer, Agent>();
        this.numVertices = n;
        this.numEdges = k;
        updateGraph();
        while (new WeakComponentClusterer<Integer, String>().transform(net)
                .size() != 1) {
            updateGraph();
        }

    }

    public Net(int n, int k, double steep, double prob) {

        this.steepness = steep;
        agentsVertices = new HashMap<Integer, Agent>();
        this.numVertices = n;
        this.numEdges = k;
        updateGraph();
        while (new WeakComponentClusterer<Integer, String>().transform(net)
                .size() != 1) {
            updateGraph();
        }
        chooseBM();
        UnweightedShortestPath<Integer, String> path = new UnweightedShortestPath<>(
                net);
        distanceBM = path.getDistanceMap(BM);
        chooseTI(prob);
        setWeightsToEveryAgent();

    }

    private void updateGraph() {

        net = new UndirectedSparseGraph<Integer, String>();

        for (int i = 1; i <= numVertices; i++) {
            net.addVertex(i);
            agentsVertices.put(i, new Agent(i));
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
    }

    public static void printMatrix(int[][] connections) {
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
        for (int i = 0; i < numVertices; i++) {
            sumRow = sumRow + table[columnNumber][i];
        }
        return sumRow;

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
            int tmp = BM;
            while (tmp == BM) {
                tmp = r.nextInt(numVertices) + 1;
            }
            TI = tmp;
        }
    }

    private void chooseBM() {
        Random r = new Random();
        BM = r.nextInt(numVertices) + 1;
    }
}
