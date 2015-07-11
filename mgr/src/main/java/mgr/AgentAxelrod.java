package mgr;

import java.util.Random;


public class AgentAxelrod {

    private int[] opinion;
    private int vertex;
    private double weight;

    public AgentAxelrod(int vertex) {
        Random gen = new Random();
        opinion = new int[5];
        for(int i = 0; i < opinion.length; i++){
        	opinion[i] = (int) gen.nextDouble();
        }
        this.vertex = vertex;
    }

    public int[] getOpinion() {
        return opinion;
    }

    public void setOpinion(int featureNo, int op) {
        opinion[featureNo] = op;
    }

    public void countWeight(
            NetBA netBA) {
        weight = Math.pow((1.0 / (netBA.distanceBM.get(vertex).doubleValue() + 1.0)), netBA.steepness);
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
