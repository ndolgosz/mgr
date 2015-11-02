package mgr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import org.apache.commons.math3.util.Pair;
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
	
	public Agent[] takeRandomNeighbors(NetBA net) {
		
		
		Random r = new Random();
		
		Collection<Integer> edges = net.net.getEdges();	
		int i = r.nextInt(edges.size());
		edu.uci.ics.jung.graph.util.Pair<Integer> pair = net.net.getEndpoints((int) edges.toArray()[i]);
		
		Agent[] agents = { net.agentsVertices.get(pair.getFirst()), net.agentsVertices.get(pair.getSecond()) };
		return agents;
	}
	
	public Agent[] takeRandomNeighbors(NetCayley net) {
		
		
		Random r = new Random();
		
		Collection<Integer> edges = net.net.getEdges();	
		int i = r.nextInt(edges.size());
		edu.uci.ics.jung.graph.util.Pair<Integer> pair = net.net.getEndpoints((int) edges.toArray()[i]);
		
		Agent[] agents = { net.agentsVertices.get(pair.getFirst()), net.agentsVertices.get(pair.getSecond()) };
		return agents;
	}


	public int randomAgent(Net net) {
		Random r = new Random();
		int i = r.nextInt(net.numVertices) + 1;

		return i;
	}

	public void updateOpinions_DeffuantModel(Net net, Agent[] agents, double threshold) {

		int TI = net.TI;
		
		Agent agent1 = agents[0];
		Agent agent2 = agents[1];
		
		double ag1s = agent1.getWeight() / (agent1.getWeight() + agent2.getWeight());
		double ag2s = agent2.getWeight() / (agent1.getWeight() + agent2.getWeight());
		
		if(Math.abs(agent1.getOpinion() - agent2.getOpinion()) < threshold){
			
			if(agent1.getVertex() != TI && agent2.getVertex() != TI){
				agent1.setOpinion(agent1.getOpinion() + ag2s * (agent2.getOpinion() - agent1.getOpinion()));
				agent2.setOpinion(agent2.getOpinion() + ag1s * (agent1.getOpinion() - agent2.getOpinion()));
			}
			else if(agent2.getVertex() == TI){
				agent1.setOpinion(agent2.getOpinion());
			}
			else if(agent1.getVertex() == TI){
				agent2.setOpinion(agent1.getOpinion());
			}
		}

	}
	
	public void updateOpinions_DeffuantModel(NetBA net, Agent[] agents, double threshold) {

		int TI = net.TI;
		
		Agent agent1 = agents[0];
		Agent agent2 = agents[1];
		
		double ag1s = agent1.getWeight() / (agent1.getWeight() + agent2.getWeight());
		double ag2s = agent2.getWeight() / (agent1.getWeight() + agent2.getWeight());
		
		if(Math.abs(agent1.getOpinion() - agent2.getOpinion()) < threshold){
			
			if(agent1.getVertex() != TI && agent2.getVertex() != TI){
				agent1.setOpinion(agent1.getOpinion() + ag2s * (agent2.getOpinion() - agent1.getOpinion()));
				agent2.setOpinion(agent2.getOpinion() + ag1s * (agent1.getOpinion() - agent2.getOpinion()));
			}
			else if(agent2.getVertex() == TI){
				agent1.setOpinion(agent2.getOpinion());
			}
			else if(agent1.getVertex() == TI){
				agent2.setOpinion(agent1.getOpinion());
			}
		}

	}
	
	public void updateOpinions_DeffuantModel(NetCayley net, Agent[] agents, double threshold) {

		int TI = net.TI;
		
		Agent agent1 = agents[0];
		Agent agent2 = agents[1];
		
		double ag1s = agent1.getWeight() / (agent1.getWeight() + agent2.getWeight());
		double ag2s = agent2.getWeight() / (agent1.getWeight() + agent2.getWeight());
		
		if(Math.abs(agent1.getOpinion() - agent2.getOpinion()) < threshold){
			
			if(agent1.getVertex() != TI && agent2.getVertex() != TI){
				agent1.setOpinion(agent1.getOpinion() + ag2s * (agent2.getOpinion() - agent1.getOpinion()));
				agent2.setOpinion(agent2.getOpinion() + ag1s * (agent1.getOpinion() - agent2.getOpinion()));
			}
			else if(agent2.getVertex() == TI){
				agent1.setOpinion(agent2.getOpinion());
			}
			else if(agent1.getVertex() == TI){
				agent2.setOpinion(agent1.getOpinion());
			}
		}

	}
	
	public double synchronStdDev(ArrayList<Double> vec){
		
		double sum=0.0;	
		double sum2=0.0;	
		for(double v : vec)
			sum=sum+v;
		
		double averageOp = sum/vec.size();
		
		for(double v : vec)
			sum2=sum2+(v-averageOp)*(v-averageOp);	
		return Math.sqrt(sum2/vec.size());
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
	
	public double countTotalSynchrony(NetCayley net) {
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
