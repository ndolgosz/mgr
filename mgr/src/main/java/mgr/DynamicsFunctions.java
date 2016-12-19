package mgr;

import java.util.Collection;
import java.util.Random;

import org.math.plot.Plot2DPanel;

import tables.T_n_k_table;
import tables.T_steep_k_table;
import tables_BA.T_steep_n_table;
import tables_Cayley.T_steep_depth_k_table;

public class DynamicsFunctions {


/*	public Agent[] takeRandomNeighbors(Net net) {
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
	}*/
	
public Agent[] takeRandomNeighbors(Net net) {
		
		
		Random r = new Random();
		
		Collection<Integer> edges = net.net.getEdges();	
		int i = r.nextInt(edges.size());
		edu.uci.ics.jung.graph.util.Pair<Integer> pair = net.net.getEndpoints((int) edges.toArray()[i]);
		
		Agent[] agents = { net.agentsVertices.get(pair.getFirst()), net.agentsVertices.get(pair.getSecond()) };
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



	private int randomAgent(Net net) {
		Random r = new Random();
		int i = r.nextInt(net.numVertices) + 1;

		return i;
	}

	public void updateOpinions_BasicModel(Agent[] agents) {

		Agent agent1 = agents[0];
		Agent agent2 = agents[1];
		double f;
		double diff = Math.abs(agent1.getOpinion() - agent2.getOpinion());
		if (diff <= 180)
			f = (agent1.getOpinion() + agent2.getOpinion()) / 2;
		else
			f = ((agent1.getOpinion() + agent2.getOpinion()) / 2) - 180;

		if (f >= 0) {
			agent1.setOpinion(f);
			agent2.setOpinion(f);
		} else {
			agent1.setOpinion(f + 360);
			agent2.setOpinion(f + 360);
		}

		if (agent1.getOpinion() < 0 || agent1.getOpinion() > 360) {
			throw new IllegalArgumentException();
		}
		System.out.println(agent1.getOpinion());
	}

	public void updateOpinions_InformationModel(Net net, Agent[] agents) {

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
	
	public void updateOpinions_InformationModel(NetBA net, Agent[] agents) {

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

	public void updateOpinions_InformationModel(NetCayley net, Agent[] agents) {

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
	
	
	public double countBasicTotalSynchrony(Net net) {

		double ct = 0;
		for (int i = 1; i <= net.numVertices; i++) {
			double iOp = net.agentsVertices.get(i).getOpinion();
			for (int j = 1; j <= net.numVertices; j++) {
				double jOp = net.agentsVertices.get(j).getOpinion();

				ct = ct
						+ Math.min(Math.abs(iOp - jOp),
								Math.abs(Math.abs(iOp - jOp) - 360));
			}
		}

		ct = ct / ((net.numVertices) * (net.numVertices - 1));
		return ct;
	}
	
	public double countBasicTotalSynchrony(NetBA net) {

		double ct = 0;
		for (int i = 1; i <= net.numVertices; i++) {
			double iOp = net.agentsVertices.get(i).getOpinion();
			for (int j = 1; j <= net.numVertices; j++) {
				double jOp = net.agentsVertices.get(j).getOpinion();

				ct = ct
						+ Math.min(Math.abs(iOp - jOp),
								Math.abs(Math.abs(iOp - jOp) - 360));
			}
		}

		ct = ct / ((net.numVertices) * (net.numVertices - 1));
		return ct;
	}
	
	public double countBasicTotalSynchrony(NetCayley net) {

		double ct = 0;
		for (int i = 1; i <= net.numVertices; i++) {
			double iOp = net.agentsVertices.get(i).getOpinion();
			for (int j = 1; j <= net.numVertices; j++) {
				double jOp = net.agentsVertices.get(j).getOpinion();

				ct = ct
						+ Math.min(Math.abs(iOp - jOp),
								Math.abs(Math.abs(iOp - jOp) - 360));
			}
		}

		ct = ct / ((net.numVertices) * (net.numVertices - 1));
		return ct;
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
	

	private double T_nk(double n, int k, double[][] Tnk, double[] n_axis,
			double[] k_axis) {
		int nInd = -1;
		int kInd = -1;

		for (int i = 0; i < n_axis.length; i++) {
			
			if (n_axis[i] == n) {
				nInd = i;
				break;
			}
		}

		for (int i = 0; i < k_axis.length; i++) {

			if (k_axis[i] == k * 1.0) {
				kInd = i;
				
				break;
			}
		}

		return Tnk[kInd][nInd];
	}
	
	private double T_nk(double k, double n, double[][] Tnk,double[] k_axis, double[] n_axis
			) {
		int nInd = -1;
		int kInd = -1;

		for (int i = 0; i < n_axis.length; i++) {
			//System.out.println(n+" "+n_axis[i]);
			if (n_axis[i] == n) {
				nInd = i;
				break;
			}
		}

		for (int i = 0; i < k_axis.length; i++) {
			//System.out.println(k+" "+k_axis[i]);
			if (k_axis[i] == k) {
				kInd = i;
				
				break;
			}
		}
		
		return Tnk[kInd][nInd];
	}
	
	private double T_dksteep(double[] dk, double steep, double[][] Tnk,double[][] dk_axis, double[] steep_axis
			) {
		int nInd = -1;
		int kInd = -1;
		double d = dk[0];
		double k = dk[1];

		for (int i = 0; i < steep_axis.length; i++) {
			//System.out.println(n+" "+n_axis[i]);
			if (steep_axis[i] == steep) {
				nInd = i;
				break;
			}
		}

		for (int i = 0; i < dk_axis.length; i++) {
			//System.out.println(k+" "+k_axis[i]);
			if (dk_axis[i][0] == d && dk_axis[i][1]== k) {
				//System.out.println(d+" "+k+" "+i);
				kInd = i;			
				break;
			}
		}
		
		return Tnk[kInd][nInd];
	}

	private double H_nk(double n) {
		double alpha = 0.5;
		double beta = 15;

		return 1.0 / (1.0 + Math.exp(alpha * beta - alpha * n));
	}

	private double B_nk(double n, int k, double[][] Tnk, double[] n_axis,
			double[] k_axis, double kappa, double tau) {
		return (H_nk(n) / n) - (kappa * k)
				- (tau * T_nk(n, k, Tnk, n_axis, k_axis) / n);
	}

	private double B_steepk(int n, double steep, int k, double[][] Tnk,
			double[] steep_axis, double[] k_axis, double kappa, double tau) {
		return (H_nk(n) / n) - (kappa * k)
				- (tau * T_nk(steep, k, Tnk, steep_axis, k_axis) / n);
	}
	
	
	
	private double B_costk_function(int n, double k, double steep, double cost, T_steep_k_table T,
			double kappa, double tau) {
		
		return (H_nk(n) / n) - (kappa * k)
				- Math.pow((tau * T.functionInterpolated.value(k, steep) / n), cost);
	}

	public int[] optimalGroup_n_k(T_n_k_table table, double kappa, double tau) {

		double[][] Tnk = table.T_fun;
		double[] n_axis = table.n_axis;
		double[] k_axis = table.k_axis;
		
		double maxB = B_nk(n_axis[0], (int) k_axis[0], Tnk,
				n_axis, k_axis, kappa, tau);
		int[] optimum = new int[2];
		for (int i = 0; i < Tnk.length; i++) {
			for (int j = 0; j < k_axis.length; j++) {
				if (Tnk[i][j] != 0) {
					double bnk = B_nk(n_axis[i], (int) k_axis[j], Tnk,
							n_axis, k_axis, kappa, tau);
					if (maxB < bnk) {
						maxB = bnk;
						optimum[0] = (int) n_axis[i];
						optimum[1] = (int) k_axis[j];
					}
				}
			}
		}
		return optimum;
	}

	public double[] optimalGroup_steep_k(T_steep_k_table table, double kappa, double tau, double cost) {

		int n_opt = 20;
		
		double[] steep_axis = table.steep_axis;
		double[] k_axis = table.k_axis;
		
		double maxB = B_costk_function(n_opt, k_axis[0], steep_axis[0], cost, table, kappa, tau);
		double[] optimum = new double[2];
		for (double steep = steep_axis[0]; steep <= steep_axis[steep_axis.length - 1]; steep+=0.05) {
			for (double k = k_axis[0]; k <= k_axis[steep_axis.length - 1]; k+=0.2) {

				double bnk = B_costk_function(n_opt, k, steep, cost, table, kappa, tau);
				if (maxB < bnk) {
					maxB = bnk;
					optimum[0] = steep;
					optimum[1] = k;
				}
			}
		}
		return optimum;
	}

	public double optimalGroup_steep_cost(T_steep_k_table table, double kappa, double tau, int cost) {

		int n_const = 20;
		int k_const = 5;
		
		double[] steep_axis = table.steep_axis;
		
		double maxB = B_costk_function(n_const, k_const, steep_axis[0], cost, table, kappa, tau);
		double optimum =  steep_axis[0];
		for (double steep = steep_axis[0]; steep <= steep_axis[steep_axis.length - 1]; steep+=0.005) {
				double bnk = B_costk_function(n_const, k_const, steep, cost, table, kappa, tau);
				if (maxB < bnk) {
					maxB = bnk;
					optimum = steep;				
				}		
		}
		return optimum;
	}
	
	public Plot2DPanel[] optimalGroup_kappa_tau(T_n_k_table table) {

		double[] n1 = new double[100];
		double[] k1 = new double[100];
		double[] kappa_vec = new double[100];
		int i = 0;
		for (double kappa = 0; kappa <= 0.0005; kappa = kappa + 0.000005) {
			int[] pair = optimalGroup_n_k(table, kappa,
					0.00001);
			n1[i] = (double) pair[0];
			k1[i] = (double) pair[1];
			kappa_vec[i] = kappa;
			i++;
		}
		double[] n2 = new double[100];
		double[] k2 = new double[100];
		double[] tau_vec = new double[100];
		i = 0;
		for (double tau = 0; tau <= 0.00004; tau = tau + 0.000005) {
			int[] pair = optimalGroup_n_k(table, 0.0001, tau);
			n2[i] = (double) pair[0];
			k2[i] = (double) pair[1];
			tau_vec[i] = tau;
			i++;
		}

		Plot2DPanel plot1 = new Plot2DPanel();
		plot1.addLinePlot("n*(kappa)", kappa_vec, n1);
		plot1.addLinePlot("k*(kappa)", kappa_vec, k1);

		plot1.setAxisLabels("kappa", "n*k*(kappa) for tau = 0.00001");

		Plot2DPanel plot2 = new Plot2DPanel();
		plot2.addLinePlot("n*(tau)", tau_vec, n2);
		plot2.addLinePlot("k*(tau)", tau_vec, k2);

		plot2.setAxisLabels("tau", "n*k*(tau) for kappa = 0.0001");

		return new Plot2DPanel[] { plot1, plot2 };
	}
	
	public double averageK(NetBA net){
		
		int k=0;
		for(int i = 1; i<=net.numVertices; i++){
			
			k=k+net.net.getNeighborCount(i);
		}
		
		return (k/(double) net.numVertices);
	}
	public double averageK(NetCayley net){
		
		int k=0;
		for(int i = 1; i<=net.numVertices; i++){
			
			k=k+net.net.getNeighborCount(i);
		}
		
		return (k/(double) net.numVertices);
	}

	public double B_n_steep(double n, double steep, double avK, double[][] Tnsteep, double[] n_axis,
			double[] steep_axis, double kappa, double tau) {
		return (H_nk(n) / n) - (kappa * avK)
				- (tau * T_nk(n, steep, Tnsteep, n_axis,steep_axis) / n);
	}
	public double B_dk_steep(double[] dk, double steep, double avK, double[][] Tnsteep, double[][] dk_axis,
			double[] steep_axis, double kappa, double tau) {
		int n = new NetCayley((int)dk[0], (int)dk[1]).numVertices;
		return (H_nk(n) / n) - (kappa * avK)
				- (tau * T_dksteep(dk, steep, Tnsteep, dk_axis,steep_axis) / n);
	}
	
	private double countAverageKBA(int n){			
			NetBA net = new NetBA(n);
			return averageK(net);
	}

	private double countAverageKCayley(int d, int k){
		

			NetCayley net = new NetCayley(d,k);
			return averageK(net);
		
	}

	public double[] optimalGroup_steep_n(T_steep_n_table table, double kappa, double tau, String netName) {

		double[][] tab = table.T_fun;
		double[] steep_axis = table.steep_axis;
		double[] n_axis = table.n_axis;
		
		double maxB = B_n_steep((int) n_axis[0], steep_axis[0], countAverageKBA((int) n_axis[0]), tab, n_axis,steep_axis, kappa, tau);
		double[] optimum = new double[2];
		for (double steep : steep_axis) {
			//System.out.println();
			//System.out.println("STEEP : "+steep);
			for (double n : n_axis) {
				//System.out.println("N : "+n);
				
				double bnk = B_n_steep(n, steep, countAverageKBA((int) n), tab,n_axis,steep_axis, kappa, tau);
				//System.out.println(bnk);
				if (maxB < bnk) {
					maxB = bnk;
					optimum[0] = (double) steep;
					optimum[1] = (double) n;
				}
			}
		}
		return optimum;
	}
	
	
	public double[] optimalGroup_steep_dk(T_steep_depth_k_table table, double kappa, double tau) {

		double[][] tab = table.T_fun;
		double[] steep_axis = table.steep_axis;
		double[][] dk_axis = table.dk_axis;
		
		double maxB = B_dk_steep(dk_axis[0], steep_axis[0], countAverageKCayley((int) dk_axis[0][0],(int) dk_axis[0][1]), tab, dk_axis,steep_axis, kappa, tau);
		double[] optimum = new double[3];
		for (double steep : steep_axis) {
			//System.out.println();
			//System.out.println("STEEP : "+steep);
			for (double[] dk : dk_axis) {
				//System.out.println("N : "+n);
				
				double bnk = B_dk_steep(dk, steep, countAverageKCayley((int)dk[0], (int) dk[1]), tab, dk_axis,steep_axis, kappa, tau);
				//System.out.println(bnk);
				if (maxB < bnk) {
					maxB = bnk;
					optimum[0] = (double) steep;
					optimum[1] = (double) dk[0];
					optimum[2] = (double) dk[1];
				}
			}
		}
		return optimum;
	}
	

}
