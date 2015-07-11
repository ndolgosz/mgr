package mgr;

import java.util.Collection;
import java.util.Random;

import org.math.plot.Plot2DPanel;

public class AxelrodModelDynamics {

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
	
	public Agent[] takeRandomNeighbors(NetBA net) {
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

	public void updateOpinions_AxelrodModel(Net net, AgentAxelrod[] agents, double threshold) {

		int match = 0;
		for(int i = 0; i < agents[0].getOpinion().length; i++){
			if(agents[0].getOpinion()[i] == agents[1].getOpinion()[i]){
				match++;
			}
		}
		
		if(match/ agents[0].getOpinion().length*1.0 > threshold){
		int TI = net.TI;
		
		AgentAxelrod agent1 = agents[0];
		AgentAxelrod agent2 = agents[1];
		
		double ag1s = agent1.getWeight() / (agent1.getWeight() + agent2.getWeight());
		double ag2s = agent2.getWeight() / (agent1.getWeight() + agent2.getWeight());
		
		Random r = new Random();
		
		//wymienia tyle cech - proporcjonalnie stosunku wag? - z wiekszym prawdopodobientwsem cechê przeka¿e silniejszy?
		int feature = r.nextInt(agent1.getOpinion().length) + 1;
		if(agent1.getWeight() > agent2.getWeight()){
			agent2.setOpinion(feature, agent1.getOpinion()[feature]);
		}
		else if(agent1.getWeight() < agent2.getWeight()){
			agent1.setOpinion(feature, agent2.getOpinion()[feature]);
		}
					
		}
		
	}
	
	public void updateOpinions_AxelrodModel(NetBA net, Agent[] agents, double threshold) {

		int TI = net.TI;
		
		Agent agent1 = agents[0];
		Agent agent2 = agents[1];
		
		double ag1s = agent1.getWeight() / (agent1.getWeight() + agent2.getWeight());
		double ag2s = agent2.getWeight() / (agent1.getWeight() + agent2.getWeight());
		
		if(Math.abs(agent1.getOpinion() - agent2.getOpinion()) < threshold){
			if(agent1.getVertex() != TI){
				agent1.setOpinion(agent1.getOpinion() + ag2s * (agent2.getOpinion() - agent1.getOpinion()));
			}
			if(agent2.getVertex() != TI){
				agent2.setOpinion(agent2.getOpinion() + ag1s * (agent1.getOpinion() - agent2.getOpinion()));
			}
		}

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
	
	public double countTotalSynchrony(NetBA net) {
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
