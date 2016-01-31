package mgr;

import java.util.Random;


public class Agent {

    private double opinion;
    private int vertex;
    private double weight;

    public Agent(int vertex) {
        Random gen = new Random();
        this.opinion = gen.nextDouble() * 360;
        if(this.opinion >= 360 || this.opinion < 0){
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

	public void countWeight(NetCayley netCayley) {
		 this.weight = Math.pow((1.0 / (netCayley.distanceBM.get(vertex).doubleValue() + 1.0)), netCayley.steepness);
		
	}
	
	public void setWeight(double w) {
		 this.weight = w;
		
	}

}
