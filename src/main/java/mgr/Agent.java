package mgr;

import java.util.Random;

public class Agent {

    public double opinion;

    public Agent() {
    	Random gen = new Random();
        this.opinion = gen.nextDouble()*360;
    }
    public void printOpinion() {    
        System.out.print("opinion: "+opinion);
    }
}
