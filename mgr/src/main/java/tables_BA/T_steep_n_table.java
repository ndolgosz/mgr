package tables_BA;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;

import mgr.DynamicsFunctions;
import mgr.Net;
import mgr.NetBA;

import org.apache.commons.math3.analysis.interpolation.BicubicSplineInterpolatingFunction;
import org.math.plot.Plot3DPanel;

import tables.TableInterpolation;

public class T_steep_n_table {

	private static final int ITER = 50000;
	private double prob;
	private double lambda;
	//private final static int avK = 3;
	private double begSteep = 0.0;
	private double endSteep = 5.0;
	private int begN = 20;
	private double diff = 0.05;
	private int endN = 20;

	public double[] steep_axis = new double[(int) ((endSteep - begSteep) / diff + 1)];
	public double[] n_axis = new double[endN - begN + 1];
	public double[][] T_fun = new double[endN - begN + 1][(int) ((endSteep - begSteep)
			/ diff + 1)];
	public BicubicSplineInterpolatingFunction functionInterpolated;

	public T_steep_n_table(double prob, int lambda) {
		this.lambda = lambda;
		this.prob = prob;
		setNKaxes();
	}

	public T_steep_n_table(double prob) {
		this.lambda = 20;
		this.prob = prob;
		setNKaxes();
	}

	public void countTsteepkMatrix() {
		DynamicsFunctions dynamics = new DynamicsFunctions();

		System.out.println("Building T(steep,n) plot: ITER=" + ITER
				+ " lambda: " + lambda);
		int n = begN;

		while (n <= endN) {
			System.out.println("\tFor n = " + n);

			double steep = begSteep;
			int steep_iter = 0;
			while (steep_iter <= (int) endSteep / diff) {
				//System.out.println("\t\tFor steepness = " + steep);
				
				double T = 0;
				// usrednianie
				for (int run = 1; run <= ITER; run++) {
					//Net net = new Net(n,4);
					NetBA net = new NetBA(n);
					net.configureInformationModel(prob, steep);
					net.setBMtoCenter();
					double ct = lambda + 1;

					int i = 0;
					while (ct > lambda) {
						dynamics.updateOpinions_InformationModel(net,
								dynamics.takeRandomNeighbors(net));
						ct = dynamics.countTotalSynchrony(net);
						
						i++;
					}
					T = T + i - 1;
					
				}

				T_fun[n - begN][steep_iter] = T / ITER;
				steep = steep + diff;
				steep_iter++;
			}
			n++;
		}

		//interpolateGraph();
	}

	private void interpolateGraph() {
		System.out.println(n_axis.length);
		System.out.println(steep_axis.length);
		
		System.out.println(T_fun[0].length);
		System.out.println(T_fun.length);
		
		functionInterpolated = TableInterpolation.getInterpolatedFunction(
				n_axis, steep_axis, T_fun);

	}

	public Plot3DPanel createPlot() {

		Plot3DPanel plot = new Plot3DPanel();
		plot.addGridPlot("T(steepness,n)", steep_axis, n_axis, T_fun);
		plot.addGridPlot("interpolation", steep_axis, n_axis, TableInterpolation
				.interpolatePlot2D(n_axis, steep_axis, functionInterpolated));
		plot.setAxisLabels("steepness", "n", "T(steepness,n)");
		plot.setFixedBounds(2, 100, 1000);
		
		return plot;
	}

	public void printMatrix(double[][] connections) {
		for (int i = 0; i < connections.length; i++) {
			System.out.println();
			System.out.print(i + "    ");
			for (int j = 0; j < connections[i].length; j++) {
				System.out.print(connections[i][j] + " ");
			}
		}
		System.out.println();
	}

	private void setNKaxes() {
		steep_axis[0] = begSteep;
		for (int i = 1; i < steep_axis.length; i++) {
			steep_axis[i] = steep_axis[i - 1] + diff;
		}
		for (int i = 0; i < n_axis.length; i++) {
			n_axis[i] = begN + i;
		}
	}

	public void saveTMatrixToFile() throws FileNotFoundException {

		PrintWriter writer = null;

		writer = new PrintWriter(
				"C:\\Users\\natdol\\workspace\\mgr\\mgr\\src\\main\\resources\\tables_BA\\Tsteep_L"
						+ String.valueOf((int) lambda) + "_"
						+ String.valueOf(prob) + ".txt");

		for (int i = 0; i < steep_axis.length; i++) {
			if (i != steep_axis.length - 1) {
				writer.print(steep_axis[i] + " ");
			} else {
				writer.print(steep_axis[i] + "\n");
			}
		}
		for (int i = 0; i < n_axis.length; i++) {
			if (i != n_axis.length - 1) {
				writer.print((int) n_axis[i] + " ");
			} else {
				writer.print((int) n_axis[i] + "\n");
			}
		}
		writer.println();

		for (int i = 0; i < T_fun.length; i++) {
			for (int j = 0; j < T_fun[0].length; j++) {
				if (j != T_fun[0].length - 1) {
					writer.print(T_fun[i][j] + " ");
				} else {
					writer.print(T_fun[i][j]);
				}
			}
			if (i < T_fun.length - 1) {
				writer.println();
			}
		}
		writer.close();

	}

	public void readTnkFromFile(int lambda, double prob) {
		InputStream in = getClass().getResourceAsStream(
				"Tsteep_L" + String.valueOf(lambda) + "_"
						+ String.valueOf(prob).substring(0, 3) + ".txt");
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		int i = 0;
		try {
			String[] naa = reader.readLine().split(" ");
			begSteep = Double.valueOf(naa[0]);
			endSteep = Double.valueOf(naa[naa.length - 1]);
			steep_axis = new double[(int) ((endSteep - begSteep) / diff + 1)];
			for (String na : naa) {
				steep_axis[i] = Double.valueOf(na);
				i++;
			}
			i = 0;
			String[] kaa = reader.readLine().split(" ");
			begN = Integer.valueOf(kaa[0]);
			endN = Integer.valueOf(kaa[kaa.length - 1]);
			n_axis = new double[endN - begN + 1];
			T_fun = new double[endN - begN + 1][(int) ((endSteep - begSteep)
					/ diff + 1)];

			for (String na : kaa) {
				n_axis[i] = Double.valueOf(na);
				i++;
			}
			reader.readLine();
			int k = 0;

			String lineS;
			while ((lineS = reader.readLine()) != null) {
				int steep = 0;
				String[] line = lineS.split(" ");
				if (line.length < 2)
					break;
				for (String num : line) {
					T_fun[k][steep] = Double.valueOf(num);
					steep++;
				}
				k++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//interpolateGraph();
	}
}
