package mgr;

import java.util.Collection;
import java.util.Random;

import javax.swing.JFrame;

import org.math.plot.Plot2DPanel;

public class Main {

	static int ITER = 2000;
	static int TIME = 200;

	public static void main(String[] args) {

		System.out.println("Start!");
		Plot2DPanel plot = new Plot2DPanel();

		double[] ct = new double[TIME];
		ct[0] = 0;
		double[] t = new double[TIME];
		int n = 6;
		int k = 3;
		
		while (n <= 20) {
			for (int run = 1; run <= ITER; run++) {
				Net net = new Net(n, k);
				int i = 0;

				while (i < TIME) {

					updateOpinions(takeRandomNeighbors(net));
					ct[i] = ct[i] + countBasicTotalSynchrony(net) / ITER;
					t[i] = i;
					i++;
				}
			}
			plot.addLinePlot("c(t) for n = "+n+", k = "+k, t, ct);
			n = n + 2;
		}

		JFrame frame = new JFrame("a plot panel");
		frame.setContentPane(plot);
		frame.setVisible(true);
		frame.setSize(500, 500);
		System.out.println("End!");

	}

	public static Agent takeRandomAgent(Net net) {
		Random r = new Random();
		return net.agentsVertices.get(r.nextInt(net.numVertices) + 1);
	}

	public static Agent[] takeRandomNeighbors(Net net) {
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

	public static void updateOpinions(Agent[] agents) {

		Agent agent1 = agents[0];
		Agent agent2 = agents[1];
		// System.out.println("Agent1 opinion: " + agent1.opinion);
		// System.out.println("Agent2 opinion: " + agent2.opinion);
		double f;
		double diff = Math.abs(agent1.opinion - agent2.opinion);
		if (diff <= 180)
			f = (agent1.opinion + agent2.opinion) / 2;
		else
			f = ((agent1.opinion + agent2.opinion) / 2 ) - 180;

		if (f >= 0) {
			agent1.opinion = f;
			agent2.opinion = f;
		} else {
			agent1.opinion = f + 360;
			agent2.opinion = f + 360;
		}
		// System.out.println("updating...");
		// System.out.println("Agent1 opinion: " + agent1.opinion);
		// System.out.println("Agent2 opinion: " + agent2.opinion);
	}

	public static double countBasicTotalSynchrony(Net net) {

		double ct = 0;
		for (int i = 1; i < net.numVertices; i++) {
			double iOp = net.agentsVertices.get(i).opinion;
			for (int j = i + 1; j <= net.numVertices; j++) {
				double jOp = net.agentsVertices.get(j).opinion;
				ct = ct
						+ Math.min(Math.abs(iOp - jOp),
								Math.abs(iOp - jOp - 360));
			}
		}
		ct = ct / (net.numVertices * (net.numVertices - 1));
		return ct;
	}
}
