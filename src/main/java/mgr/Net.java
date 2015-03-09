package mgr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import edu.uci.ics.jung.algorithms.cluster.WeakComponentClusterer;
import edu.uci.ics.jung.algorithms.shortestpath.UnweightedShortestPath;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class Net {

    int numVertices;
    int numEdges;
    public int BM = -1;
    public int TI = -1;
    public double steepness;
    HashMap<Integer, Agent> agentsVertices;
    Map<Integer, Number> distanceBM;
    Graph<Integer, String> net;
    Map<Integer,Double> copyOfAgents;

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
        this.copyOfAgents = getMapOfOpinions();

    }
    
    private Map<Integer, Double> getMapOfOpinions() {
		Map<Integer,Double> map = new HashMap<>();
		for(int i = 1; i <= numVertices; i++){
			map.put(i, agentsVertices.get(i).getOpinion());
		}
		return map;
	}

	public void configureInformationModel(double prob, double steep){
    	this.steepness = steep;
    	resetAgentsOpinion();
    	chooseBM();
        UnweightedShortestPath<Integer, String> path = new UnweightedShortestPath<>(
                net);
        distanceBM = path.getDistanceMap(BM);
        chooseTI(prob);
        setWeightsToEveryAgent();
    }
    
    private void resetAgentsOpinion(){
    	for(int i = 1; i <= numVertices; i++){
    		agentsVertices.get(i).setOpinion(copyOfAgents.get(i));
    	}
    }
	private void updateGraph() {

        net = new UndirectedSparseGraph<Integer, String>();

        for (int i = 1; i <= numVertices; i++) {
            net.addVertex(i);
            agentsVertices.put(i, new Agent(i));
        }
        
        Integer[][] connections = randomConnectionsMatrix();
        while(!doesEveryAgentHasKNeighbours(connections)){
        	connections = randomConnectionsMatrix();
        }
        
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
       
        Integer totalCon[][] = createZerosMatrix();
        for (int i = 0; i < numVertices; i++) {
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

    private boolean doesEveryAgentHasKNeighbours(Integer[][] conn){
    	
    	int size = conn.length;
    	
    	for(int i = size - 1; i >= 0; i--){
    		int sum = 0;
    		for(int j = 0; j < conn[i].length ; j++){
    			sum = sum + conn[i][j];
    		}
    		if(sum != numEdges) return false;
    	}  	
    	return true;
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
            int tmp = r.nextInt(numVertices) + 1;;
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
