package mgr;

import java.util.HashMap;
import java.util.Random;

public class Main {

	public static void main(String[] args) {

		Net net = new Net(20, 4);
		System.out.println("---------------------------");
		updateOpinions(takeTwoRandomAgents(net));

	}

	public static Agent takeRandomAgent(Net net) {
		Random r = new Random();
		return net.agentsVertices.get(r.nextInt(net.numVertices) + 1);
	}

	public static Agent[] takeTwoRandomAgents(Net net) {
		Random r = new Random();
		int i = r.nextInt(net.numVertices) + 1;
		int j = r.nextInt(net.numVertices) + 1;
		while (i == j)
			j = r.nextInt(net.numVertices) + 1;
		Agent[] agents = { net.agentsVertices.get(i), net.agentsVertices.get(j) };
		return agents;
	}

	public static void updateOpinions(Agent[] agents) {

		Agent agent1 = agents[0];
		Agent agent2 = agents[1];
		System.out.println("Agent1 opinion: " + agent1.opinion);
		System.out.println("Agent2 opinion: " + agent2.opinion);
		double f;
		double diff = Math.abs(agent1.opinion - agent2.opinion);
		if (diff <= 180)
			f = (agent1.opinion + agent2.opinion) / 2;
		else
			f = (agent1.opinion + agent2.opinion) / 2 - 180;

		if (f >= 0) {
			agent1.opinion = f;
			agent2.opinion = f;
		} else {
			agent1.opinion = f + 360;
			agent2.opinion = f + 360;
		}
		System.out.println("updating...");
		System.out.println("Agent1 opinion: " + agent1.opinion);
		System.out.println("Agent2 opinion: " + agent2.opinion);
	}
}
