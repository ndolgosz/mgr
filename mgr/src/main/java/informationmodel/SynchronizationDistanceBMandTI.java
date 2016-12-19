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

	static int ITER = 10;
	static int lambda = 1;
	//static double prob = 0.0;
	static String model = "DEF"; // INF, DEF, BASIC
	final static int distEnd = 1;

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
		System.out.println("steep\tdist\tnumberOfNotSynchronized\t") ;
		double prob = 0.0;
		for (int dist = 1; dist <= 3; dist++) {
	
			for (int steep = 0; steep <= 4; steep++) {

				HashMap<Integer, Integer> numberOfOccurences = new HashMap<>();

	
				for (int iter = 0; iter < ITER; iter++) {

					NetCayley net = new NetCayley(3);
					//Net net = new Net(20,4);
					net.configureInformationModel(0.0, steep * 1.0);
					net.setTIdistBM(dist);

					ArrayList<Double> synchro_parameter = new ArrayList<Double>();
					dTotalTemp = (double) lambda + 1;

					while (dTotalTemp > lambda) {

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

				
						dTotalTemp = dyn.countTotalSynchrony(net);
						synchro_parameter.add(dTotalTemp);

						if (synchro_parameter.size() > 1000 && dTotalTemp > lambda) {
							synchro_parameter.remove(0);

							double stddev = def
									.synchronStdDev(synchro_parameter);
							if (stddev < 0.000000001) {
								

								if (numberOfOccurences.containsKey(dist)) {
									numberOfOccurences.replace(dist,
											numberOfOccurences.get(dist) + 1);
								} else {
									numberOfOccurences.put(dist, 1);
									printAgentsOpinions(net.agentsVertices);
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

			}
			prob = prob + 0.25;
			
		}
	}
}
