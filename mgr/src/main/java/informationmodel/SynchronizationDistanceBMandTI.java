package informationmodel;

import java.rmi.UnexpectedException;
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
	static double prob = 0.0;
	static String model = "DEF"; // INF, DEF, BASIC
	final static int distEnd = 3;

	public static void main(String[] args) throws UnexpectedException {

		Double dBasicTemp;
		Double dTotalTemp;

		DynamicsFunctions dyn = new DynamicsFunctions();
		DeffuantModelDynamics def = new DeffuantModelDynamics();

		for (int dist = 1; dist <= distEnd; dist++) {
			System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ D I S T A N C E _ _ _ _ _ _ _ _ _ _ _ _ _");
			System.out.println("Distance = " + dist);
			for (int steep = 0; steep <= 4; steep++) {

				HashMap<Integer, Integer> numberOfSameDistanceNets = new HashMap<>();
				HashMap<Integer, Integer> numberOfOccurences = new HashMap<>();
				HashMap<Integer, Integer> numberOfIterations = new HashMap<>();

				System.out.println("-----------------------------------------");
				System.out.println("Steepness = " + steep);
				for (int iter = 0; iter < ITER; iter++) {

					// System.out.println("Net number " + iter);
					//NetBA net = new NetBA(25, 4)
					NetCayley net = new NetCayley(distEnd);
					net.configureInformationModel(prob, steep * 1.0);
					net.setTIdistBM(dist);
					while (net.TI == -100) {
						//net = new NetBA(25, 4);
						System.out.println("BLADDDDDDDD");
						net.configureInformationModel(prob, steep * 1.0);
						net.setTIdistBM(dist);
					}

					// counting same configuration nets
					if (numberOfSameDistanceNets.containsKey(dist)) {
						numberOfSameDistanceNets.replace(dist,
								numberOfSameDistanceNets.get(dist) + 1);
					} else {
						numberOfSameDistanceNets.put(dist, 1);
					}

					Double dBasic = 10000.0;
					Double dTotal = 10000.0;

					for (int i = 1; i < ITER; i++) {

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

						dBasicTemp = dyn.countBasicTotalSynchrony(net);
						dTotalTemp = dyn.countTotalSynchrony(net);

						if (dTotal - dTotalTemp < 0.1 && dTotalTemp < 5) {
							// System.out.println("	Iterations for basic: " +
							// i);
							// System.out.println("	Distance BM - TI : " +
							// dist);

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
						/*
						 * if (dTotal - dTotalTemp < 0.1) {
						 * System.out.println("	Iterations for total: " + i);
						 * System.out.println("	Distance BM - TI : " +
						 * net.distanceBM.get(net.TI)); break; }
						 */

						dBasic = dBasicTemp;
						dTotal = dTotalTemp;
					}

				}

				System.out.println("steep = " + steep + " distance: " + dist);
				for (Map.Entry<Integer, Integer> entry : numberOfOccurences
						.entrySet()) {
					Integer key = entry.getKey();
					System.out.print(key + "\t" + numberOfOccurences.get(key));
					System.out.print("\t" + numberOfSameDistanceNets.get(key)
							+ "\n");

				}
			}

			/*
			 * System.out.println("Iterations: "); for (Map.Entry<Integer,
			 * Integer> entry : numberOfIterations.entrySet()) { Integer key =
			 * entry.getKey(); Integer value = entry.getValue();
			 * System.out.println("distance: " + key + " iterations: " +
			 * value/numberOfOccurences.get(key)); }
			 */
		}
	}

}
