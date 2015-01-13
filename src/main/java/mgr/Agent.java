package mgr;

import java.util.Random;

import edu.uci.ics.jung.algorithms.shortestpath.UnweightedShortestPath;

public class Agent {

    private double opinion;
    private int vertex;
    private double weight;

    public Agent(int vertex) {
        Random gen = new Random();
        this.opinion = gen.nextDouble() * 360;
        if(this.opinion>360 || this.opinion < 0){
        	throw new IllegalArgumentException();
        }
        this.vertex = vertex;
    }

    public double getOpinion() {
        return opinion;
    }

    public void setOpinion(double op) {
        opinion = op;
    }

    public void countWeight(
            Net net) {
        weight = Math.pow((1.0 / (net.distanceBM.get(vertex).doubleValue() + 1.0)), net.steepness);
    }
  
    public int getVertex() {
        return vertex;
    }
    
    public double getWeight() {
        return weight;
    }

}
