package informationmodel;

import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;

import mgr.Agent;
import mgr.DeffuantModelDynamics;
import mgr.DynamicsFunctions;
import mgr.Net;
import mgr.NetBA;
import mgr.NetCayley;

public class SynchronizationLevel {

	static int ITER = 10000;
	static int lambda = 1;
	// static double prob = 0.0;
	static String model = "DEF"; // INF, DEF, BASIC
	final static int distEnd = 1;

	private static double countNumberOfSynchronizedAgents(Net net) {

		double opTI = net.agentsVertices.get(net.TI).getOpinion();
		int sumSynch = 0;
		for (Map.Entry<Integer, Agent> entry : net.agentsVertices.entrySet()) {
			double agOp = entry.getValue().getOpinion();
			// System.out.println(opTI);
			if (Math.abs(agOp - opTI) < 5) {
				sumSynch++;
			}
		}
		//System.out.println(sumSynch * 1.0 / (1.0 * net.numVertices));
		return sumSynch * 1.0 / (1.0 * net.numVertices);
	}

	private static double countNumberOfSynchronizedAgents(NetBA net) {

		double opTI = net.agentsVertices.get(net.TI).getOpinion();
		int sumSynch = 0;
		for (Map.Entry<Integer, Agent> entry : net.agentsVertices.entrySet()) {
			double agOp = entry.getValue().getOpinion();
			// System.out.println(opTI);
			if (Math.abs(agOp - opTI) < 3) {
				sumSynch++;
			}
		}
		System.out.println(sumSynch * 1.0 / (1.0 * net.numVertices));
		return sumSynch * 1.0 / (1.0 * net.numVertices);
	}

	private static void printAgentsOpinions(HashMap<Integer, Agent> map) {

		System.out.println("\nAgents opionions");
		for (Map.Entry<Integer, Agent> entry : map.entrySet()) {
			System.out.print(entry.getValue().getOpinion() + " ");
		}
		System.out.println();
	}

	private static void printSynchroParam(ArrayList<Double> list) {

		System.out.println("\nSynchro param");
		for (Double elem : list) {
			System.out.print(String.valueOf(elem) + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) throws UnexpectedException {

		Double dTotalTemp;

		DynamicsFunctions dyn = new DynamicsFunctions();
		DeffuantModelDynamics def = new DeffuantModelDynamics();
		System.out.println("steep\tdist\tnumberOfNotSynchronized\t");

		for (double prob = 0.0; prob <= 0.5; prob += 0.25) {
			
			for (int steep = 6; steep <= 6; steep++) {

				HashMap<Double, Double> averageSynchronizationLevel = new HashMap<>();
				double averageLevel = 0.0;

				for (int iter = 0; iter < ITER; iter++) {

					//NetBA net = new NetBA(25);
					Net net = new Net(20,4);
					net.configureInformationModel(prob, steep * 1.0);
					// net.setTIdistBM(dist);

					ArrayList<Double> synchro_parameter = new ArrayList<Double>();
					dTotalTemp = (double) lambda + 1;

					while (dTotalTemp > lambda) {

						if (model.equals("INF")) {
							dyn.updateOpinions_InformationModel(net, def.takeRandomNeighbors(net));
						} else if (model.equals("DEF")) {
							def.updateOpinions_DeffuantModel(net, def.takeRandomNeighbors(net), 180); // prob
																										// =
																										// 3/4
						} else if (model.equals("BASIC")) {
							dyn.updateOpinions_BasicModel(def.takeRandomNeighbors(net));
						}

						dTotalTemp = dyn.countTotalSynchrony(net);
						//System.out.println(dTotalTemp);
						synchro_parameter.add(dTotalTemp);

						if (synchro_parameter.size() > 1000) {
							synchro_parameter.remove(0);

							double stddev = def.synchronStdDev(synchro_parameter);
							if (stddev < 0.000001) {
								break;
							}
						}
					}

					averageLevel = averageLevel + countNumberOfSynchronizedAgents(net);

				}
				
	
				averageSynchronizationLevel.put(prob, averageLevel/ITER);
				

				for (Map.Entry<Double, Double> entry : averageSynchronizationLevel.entrySet()) {
					Double key = entry.getKey();
					System.out.print("\n" + steep + "\t" + key + "\t" + averageSynchronizationLevel.get(key));
					System.out.println();

				}

			}
		}
	}
}
