package informationmodel;

import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;

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

	public static void main(String[] args) throws UnexpectedException {

		Double dBasicTemp;
		Double dTotalTemp;

		DynamicsFunctions dyn = new DynamicsFunctions();
		DeffuantModelDynamics def = new DeffuantModelDynamics();

		for (int dist = 1; dist <= distEnd; dist++) {
			System.out
					.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ D I S T A N C E _ _ _ _ _ _ _ _ _ _ _ _ _");
			System.out.println("Distance = " + dist);
			for (int steep = 0; steep <= 4; steep++) {

				HashMap<Integer, Integer> numberOfOccurences = new HashMap<>();
				HashMap<Integer, Integer> numberOfIterations = new HashMap<>();

				System.out.println("-----------------------------------------");
				System.out.println("Steepness = " + steep);
				for (int iter = 0; iter < ITER; iter++) {

					// System.out.println("Net number " + iter);
					// NetBA net = new NetBA(25, 4)
					NetCayley net = new NetCayley(distEnd);
					net.configureInformationModel(prob, steep * 1.0);
					net.setTIdistBM(dist);
					while (net.TI == -100) {
						// net = new NetBA(25, 4);
						System.out.println("BLADDDDDDDD");
						net.configureInformationModel(prob, steep * 1.0);
						net.setTIdistBM(dist);
					}

					// Double dBasic = 10000.0;
					Double dTotal = 10000.0;
					ArrayList<Double> synchro_parameter = new ArrayList<Double>();
					dTotalTemp = (double) lambda + 1;
					int i = 0;
					while (dTotalTemp > lambda) {

						// System.out.println("	step = " + i);
						if (model.equals("INF")) {
							dyn.updateOpinions_InformationModel(net,
									def.takeRandomNeighbors(net));
						} else if (model.equals("DEF")) {
							def.updateOpinions_DeffuantModel(net,
									def.takeRandomNeighbors(net), 180);
						} else if (model.equals("BASIC")) {
							dyn.updateOpinions_BasicModel(def
									.takeRandomNeighbors(net));
						}

						// dBasicTemp = dyn.countBasicTotalSynchrony(net);
						dTotalTemp = dyn.countTotalSynchrony(net);
						synchro_parameter.add(dTotalTemp);

						if (synchro_parameter.size() > 1000) {
							synchro_parameter = (ArrayList<Double>) synchro_parameter
									.subList(synchro_parameter.size() - 1001,
											synchro_parameter.size() - 1);
							double stddev = def
									.synchronStdDev(synchro_parameter);
							if (stddev > 0.5) {
								if (numberOfOccurences.containsKey(dist)) {
									numberOfOccurences.replace(dist,
											numberOfOccurences.get(dist) + 1);
								} else {
									numberOfOccurences.put(dist, 1);
								}

								if (numberOfIterations.containsKey(dist)) {
									numberOfIterations.replace(dist,
											numberOfIterations.get(dist) + i);
								} else {
									numberOfIterations.put(dist, i);
								}

								break;
							}

							dTotal = dTotalTemp;
						}
						i++;
					}

					System.out.println("steep = " + steep + " distance: "
							+ dist);
					for (Map.Entry<Integer, Integer> entry : numberOfOccurences
							.entrySet()) {
						Integer key = entry.getKey();
						System.out.print(key + "\t"
								+ numberOfOccurences.get(key));

					}
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
