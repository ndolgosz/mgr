package mgr;

import java.util.Random;

import edu.uci.ics.jung.graph.Graph;

public class Agent {

	private double opinion;
	private int weight;
	private int vertex;

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
//TODO
	public void countWeight(int BM, Graph<Integer, String> net) {
		if (BM == vertex) {
			weight = 0;
			System.out.println(weight);
			return;
		} else if (net.getNeighbors(BM).contains(vertex)) {
			weight = 1;
			System.out.println(weight);
			return;
		}

		for (Integer in : net.getNeighbors(BM)) {
			if (net.getNeighbors(in).contains(vertex)) {
				weight = 2;
				System.out.println(weight);
				return;
			}
		}
		weight = 3;

		System.out.println(weight);
	}

	public int getWeight() {
		return weight;
	}

}
