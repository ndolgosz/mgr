package informationmodel;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import org.apache.commons.collections4.map.HashedMap;
import org.math.plot.Plot2DPanel;

import basicmodel.PlotCt_N;
import sun.font.CreatedFontTracker;
import tables.TableInterpolation;
import mgr.Agent;
import mgr.DeffuantModelDynamics;
import mgr.DynamicsFunctions;
import mgr.Net;
import mgr.NetBA;
import mgr.NetCayley;

public class SynchroPercentageInTime {

	static int ITER = 100000;
	static int TIME = 8000;
	static int lambda = 5;
	static double prob = 0.0;
	static String model = "INF"; // INF, DEF, BASIC
	final static int distEnd = 3;
	final static int steep = 2;

	private static void printAgentsOpinions(HashMap<Integer, Agent> map) {

		System.out.println("\nAgents opionions");
		for (Map.Entry<Integer, Agent> entry : map.entrySet()) {
			System.out.print(entry.getValue().getOpinion() + " ");
		}
		System.out.println();
	}
	
	private static void printDistance(HashMap<Number, Integer> distances) {

		System.out.println("\nAgents opionions");
		for (Map.Entry<Number, Integer> entry : distances.entrySet()) {
			System.out.print(entry.getKey()+ " " +entry.getValue() + " ");
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

	private static double countNumberOfSynchronizedAgents(NetCayley net) {

		double opTI = net.agentsVertices.get(net.TI).getOpinion();
		int sumSynch = 0;
		for (Map.Entry<Integer, Agent> entry : net.agentsVertices.entrySet()) {
			double agOp = entry.getValue().getOpinion();
			// System.out.println(opTI);
			if (Math.abs(agOp - opTI) < 0.01) {
				sumSynch++;
			}
		}
		return sumSynch * 1.0 / (1.0 * net.numVertices);
	}
	
	private static double countNumberOfSynchronizedAgents(Net net) {

		double opTI = net.agentsVertices.get(net.TI).getOpinion();
		int sumSynch = 0;
		for (Map.Entry<Integer, Agent> entry : net.agentsVertices.entrySet()) {
			double agOp = entry.getValue().getOpinion();
			// System.out.println(opTI);
			if (Math.abs(agOp - opTI) < 0.01) {
				sumSynch++;
			}
		}
		return sumSynch * 1.0 / (1.0 * net.numVertices);
	}
	


	private static double countNumberOfSynchronizedAgents(NetBA net) {

		double opTI = net.agentsVertices.get(net.TI).getOpinion();
		int sumSynch = 0;

		for (Map.Entry<Integer, Agent> entry : net.agentsVertices.entrySet()) {
			double agOp = entry.getValue().getOpinion();
			// System.out.println(opTI);
			if (Math.abs(agOp - opTI) < 1) {
				sumSynch++;
			}
		}
		return sumSynch / net.numVertices;
	}

	public static Plot2DPanel createPlot(double[] time_axis,
			double[] number_axis) {

		Plot2DPanel plot = new Plot2DPanel();
		plot.addLinePlot("iteration", time_axis, number_axis);

		// plot.addLinePlot(
		// "interpolation",time_axis,TableInterpolation.getValues(
		// TableInterpolation.fitPolynomial1D(time_axis, number_axis),
		// time_axis));
		return plot;
	}

	public static Plot2DPanel addLine(Plot2DPanel plot, double[] time_axis,
			double[] number_axis, String name) {

		plot.addLinePlot(name, time_axis, number_axis);
		return plot;
	}

	public static void main(String[] args) throws UnexpectedException {

		double[] time = new double[TIME];
		double[] number_of_followers_TI = new double[TIME];
		Plot2DPanel plot = new Plot2DPanel();

		DynamicsFunctions dyn = new DynamicsFunctions();
		DeffuantModelDynamics def = new DeffuantModelDynamics();
		System.out.println("steep\tdist\tnumberOfNotSynchronized\t prob = "
				+ prob);
		
		PrintWriter writer = null;

		try {
			writer = new PrintWriter(
					"C:\\Users\\natdol\\workspace\\mgr\\mgr\\src\\main\\resources\\results\\synchro_"
							+ String.valueOf((int) lambda) + "_"
							+ String.valueOf(steep) + ".txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//int dist = 3;
		int number_of_no_fours = 0;
		HashMap<Number, Integer> distances = new HashMap<>();
		for (int steep = 0; steep <= 5; steep++) {
			number_of_followers_TI = null;
			number_of_followers_TI = new double[TIME];
			
			for (int iter = 0; iter < ITER; iter++) {

				Net net = new Net(20,4);
				net.configureInformationModel(prob, steep * 1.0);
				/*for(Number val : net.distanceBM.values()){
					if(!distances.containsKey(val))
						distances.put(val, 1);
					distances.replace(val, distances.get(val)+1);
				}
				
				while(!net.distanceBM.values().contains(4)){
					number_of_no_fours++;
					net = new Net(20,4);
					net.configureInformationModel(prob, steep * 1.0);
				}*/
				//NetCayley net = new NetCayley(distEnd, 4);
				
				//net.setBMtoCenter();
				//net.setTIdistBM(dist);
				

				int i = 0;
				//System.out.println(iter);		
				while (i < TIME) {
					
					if (iter == 0) {
						number_of_followers_TI[i] = countNumberOfSynchronizedAgents(net)/ITER;
						time[i] = i;
					} else {
						number_of_followers_TI[i] = number_of_followers_TI[i]
								+ countNumberOfSynchronizedAgents(net)/ITER;
					}

					if (model.equals("INF")) {
						dyn.updateOpinions_InformationModel(net,
								def.takeRandomNeighbors(net));
					} else if (model.equals("DEF")) {
						def.updateOpinions_DeffuantModel(net,
								def.takeRandomNeighbors(net), 180); // prob =
																	// 3/4
					} else if (model.equals("BASIC")) {
						dyn.updateOpinions_BasicModel(def
								.takeRandomNeighbors(net));
					}



					i++;
				}

			}
			
			printDistance(distances);
			
			
			System.out.println(number_of_no_fours);
			System.out.println(ITER);
			//printToFile(writer, number_of_followers_TI);
			plot = addLine(plot, time, number_of_followers_TI,Integer.toString(steep));
			plot.addLegend("NORTH");
		}
		printToFileTime(writer,time);
		writer.close();

		JFrame frame = new JFrame("a plot panel");
		// frame.setLayout(new GridLayout(1, 2));
		frame.add(plot);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(1000, 500);

	}

	public static void printToFile(PrintWriter writer, double[] axis) {



		//writer.println();
		for (int i = 0; i < axis.length; i++) {
			if (i != axis.length - 1) {
				writer.print(String.valueOf(axis[i]).substring(0, 5) + " ");
				
			} else {
				writer.print(String.valueOf(axis[i]).substring(0, 5) + "\n");
				
			}
		}		
		


	}
	
	public static void printToFileTime(PrintWriter writer, double[] axis) {



		//writer.println();
		for (int i = 0; i < axis.length; i++) {
			if (i != axis.length - 1) {
				writer.print(String.valueOf(axis[i]) + " ");
				
			} else {
				writer.print(String.valueOf(axis[i]) + "\n");
				
			}
		}		
		


	}
}
