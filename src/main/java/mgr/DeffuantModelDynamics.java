package mgr;

import java.util.Collection;
import java.util.Random;

import org.math.plot.Plot2DPanel;

public class DeffuantModelDynamics {

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

	public int randomAgent(Net net) {
		Random r = new Random();
		int i = r.nextInt(net.numVertices) + 1;

		return i;
	}

	public void updateOpinions_VoterModel(Net net, Agent[] agents) {

		int TI = net.TI;

		Agent agent1 = agents[0];
		Agent agent2 = agents[1];
		double s1 = agent1.getWeight();
		double s2 = agent2.getWeight();
		double f;
		double diff = agent1.getOpinion() - agent2.getOpinion();
		
		if (agent1.getVertex() == TI || agent2.getVertex() == TI) {
			f = net.agentsVertices.get(TI).getOpinion();
		} else if (diff <= 180 && diff >= 0) {
			f = agent1.getOpinion()
					- (s2 / (s1 + s2)) * diff;
		} else if (diff > 180) {
			f = agent1.getOpinion()
					- (s2 / (s1 + s2)) * (diff - 360);
		} else if (diff >= -180 && diff <= 0) {
			f = agent1.getOpinion()
					- (s2 / (s1 + s2) * diff);
		} else {
			f = agent1.getOpinion()
					- (s2 / (s1 + s2) * (diff + 360));
		}

		if (f < 0) {
			f = f + 360;
		}
		else if (f >= 360) {
			f = f - 360;
		}
		
		agent1.setOpinion(f);
		agent2.setOpinion(f);

	}

	public double countTotalSynchrony(Net net) {
		double tiOp = net.agentsVertices.get(net.TI).getOpinion();
		double ct = 0;
		for (int i = 1; i <= net.numVertices; i++) {
			double iOp = net.agentsVertices.get(i).getOpinion();
			ct = ct + Math.abs(iOp - tiOp);
		}
		ct = ct / (net.numVertices);
		return ct;
	}
}
