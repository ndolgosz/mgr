package mgr;

import java.util.Random;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;

public class Agent {

    private double opinion;
    private int vertex;
    private double weight;

    public Agent(int vertex) {
        Random gen = new Random();
        this.opinion = gen.nextDouble() * 360;
        this.vertex = vertex;
    }

    public double getOpinion() {
        return opinion;
    }

    public void setOpinion(double op) {
        opinion = op;
    }

    public void countWeight(int BM,
            Net net) {
        DijkstraShortestPath<Integer, String> path = new DijkstraShortestPath<>(
                net.net);
        double d = (double) path.getDistance(BM, vertex);
        weight = Math.pow((1.0 / (d + 1.0)), net.steepness);
    }
  
    public int getVertex() {
        return vertex;
    }
    
    public double getWeight() {
        return weight;
    }

}
