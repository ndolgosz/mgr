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

public class SynchronizationDistanceBMandTI {

	static int ITER = 10000;
	static int lambda = 10;
	static double prob = 0.0;
	static String model = "DEF"; // INF, DEF, BASIC
	final static int distEnd = 3;

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
		System.out.println("steep\tdist\tnumberOfNotSynchronized\t prob = "+prob) ;
		for (int dist = 1; dist <= distEnd; dist++) {
			//System.out
					//.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ D I S T A N C E _ _ _ _ _ _ _ _ _ _ _ _ _");
			//System.out.println("Distance = " + dist);
			for (int steep = 4; steep <= 6; steep++) {

				HashMap<Integer, Integer> numberOfOccurences = new HashMap<>();

				//System.out.println("-----------------------------------------");
				//System.out.println("Steepness = " + steep);
				for (int iter = 0; iter < ITER; iter++) {

					// NetBA net = new NetBA(25, 4)
					NetCayley net = new NetCayley(distEnd);
					net.configureInformationModel(prob, steep * 1.0);
					net.setTIdistBM(dist);
					// while (net.TI == -100) {
					// // net = new NetBA(25, 4);
					// System.out.println("BLADDDDDDDD");
					// net.configureInformationModel(prob, steep * 1.0);
					// net.setTIdistBM(dist);
					// }

					ArrayList<Double> synchro_parameter = new ArrayList<Double>();
					dTotalTemp = (double) lambda + 1;

					while (dTotalTemp > lambda) {

						// System.out.println("	step = " + i);
						if (model.equals("INF")) {
							dyn.updateOpinions_InformationModel(net,
									def.takeRandomNeighbors(net));
						} else if (model.equals("DEF")) {
							def.updateOpinions_DeffuantModel(net,
									def.takeRandomNeighbors(net), 180); // prob
																		// = 3/4
						} else if (model.equals("BASIC")) {
							dyn.updateOpinions_BasicModel(def
									.takeRandomNeighbors(net));
						}

						// dBasicTemp = dyn.countBasicTotalSynchrony(net);
						dTotalTemp = dyn.countTotalSynchrony(net);
						synchro_parameter.add(dTotalTemp);

						if (synchro_parameter.size() > 1000) {
							synchro_parameter.remove(0);

							double stddev = def
									.synchronStdDev(synchro_parameter);
							if (stddev < 0.00000001) {
								
								//printAgentsOpinions(net.agentsVertices);
								//printSynchroParam(synchro_parameter);

								if (numberOfOccurences.containsKey(dist)) {
									numberOfOccurences.replace(dist,
											numberOfOccurences.get(dist) + 1);
								} else {
									numberOfOccurences.put(dist, 1);
								}
								break;
							}
						}
					}



					}

				for (Map.Entry<Integer, Integer> entry : numberOfOccurences
						.entrySet()) {
					Integer key = entry.getKey();
					System.out.print("\n" + steep + "\t" + key + "\t"
							+ numberOfOccurences.get(key));
				}

				/*
				 * System.out.println("Iterations: "); for (Map.Entry<Integer,
				 * Integer> entry : numberOfIterations.entrySet()) { Integer key
				 * = entry.getKey(); Integer value = entry.getValue();
				 * System.out.println("distance: " + key + " iterations: " +
				 * value/numberOfOccurences.get(key)); }
				 */
			}
		}
	}
}
