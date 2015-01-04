package mgr;

import java.util.Collection;
import java.util.Random;

public class DynamicsFunctions {
	
	public Agent takeRandomAgent(Net net) {
        Random r = new Random();
        return net.agentsVertices.get(r.nextInt(net.numVertices) + 1);
    }

    public Agent[] takeRandomNeighbors(Net net) {
        Random r = new Random();
        int i = r.nextInt(net.numVertices) + 1;
        int j;
        Collection<Integer> jj = net.net.getNeighbors(i);
        if (jj.size() > 0) {
            j = (int) jj.toArray()[r.nextInt(jj.size())];
        } else
            j = i;
        Agent[] agents = { net.agentsVertices.get(i), net.agentsVertices.get(j) };
        return agents;
    }

    public void updateOpinions(Agent[] agents) {

        Agent agent1 = agents[0];
        Agent agent2 = agents[1];
        double f;
        double diff = Math.abs(agent1.opinion - agent2.opinion);
        if (diff <= 180)
            f = (agent1.opinion + agent2.opinion) / 2;
        else
            f = ((agent1.opinion + agent2.opinion) / 2) - 180;

        if (f >= 0) {
            agent1.opinion = f;
            agent2.opinion = f;
        } else {
            agent1.opinion = f + 360;
            agent2.opinion = f + 360;
        }
    }

    public double countBasicTotalSynchrony(Net net) {

        double ct = 0;
        for (int i = 1; i <= net.numVertices; i++) {
            double iOp = net.agentsVertices.get(i).opinion;
            for (int j = 1; j <= net.numVertices; j++) {
                double jOp = net.agentsVertices.get(j).opinion;

                ct = ct
                        + Math.min(Math.abs(iOp - jOp),
                                Math.abs(Math.abs(iOp - jOp) - 360)); 
            }
        }

        ct = ct / ((net.numVertices) * (net.numVertices - 1));
        return ct;
    }
}
