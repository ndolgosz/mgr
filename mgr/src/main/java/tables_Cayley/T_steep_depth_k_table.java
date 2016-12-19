package tables_Cayley;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Arrays;

import mgr.DynamicsFunctions;
import mgr.Net;
import mgr.NetBA;
import mgr.NetCayley;

import org.apache.commons.math3.analysis.interpolation.BicubicSplineInterpolatingFunction;
import org.math.plot.Plot3DPanel;

import tables.TableInterpolation;

public class T_steep_depth_k_table {

	private static final int ITER = 10000;
	private double prob;
	private double lambda;
	// private final static int avK = 3;
	private double begSteep = 0.0;
	private double endSteep = 5.0;
	private int begD = 1;
	private double diff = 0.2;
	private int endD = 4;
	private int begK = 2;
	private int endK = 8;

	public double[] steep_axis = new double[(int) ((endSteep - begSteep) / diff + 1)];
	public double[][] dk_axis = new double[(endD - begD + 1)
			* (endK - begK + 1)][2];
	public double[][] T_fun = new double[dk_axis.length][(int) ((endSteep - begSteep)
			/ diff + 1)];
	public double[] divdk_axis = new double[dk_axis.length];

	public BicubicSplineInterpolatingFunction functionInterpolated;

	public T_steep_depth_k_table(double prob, int lambda) {
		this.lambda = lambda;
		this.prob = prob;
		setNKaxes();
	}

	public T_steep_depth_k_table(double prob) {
		this.lambda = 20;
		this.prob = prob;
		setNKaxes();
	}

	public void countTsteepkMatrix() {
		DynamicsFunctions dynamics = new DynamicsFunctions();

		System.out.println("Building T(steep,n) plot: ITER=" + ITER
				+ " lambda: " + lambda);
		int d = begD;
		int dk_iter = 0;
		while (d <= endD) {
			
			int k = begK;
			while (k <= endK) {
				System.out.println("\tFor d = " + d);

				double steep = begSteep;
				int steep_iter = 0;
				while (steep_iter <= (int) endSteep / diff) {
					// System.out.println("\t\tFor steepness = " + steep);

					double T = 0;
					// usrednianie
					NetCayley net = new NetCayley(d, k);
					if(net.numVertices < 40){
						
					for (int run = 1; run <= ITER; run++) {

						net = new NetCayley(d, k);
						
						net.configureInformationModel(prob, steep);
						
						
						double ct = lambda + 1;
						int i = 0;
						while (ct > lambda) {
							dynamics.updateOpinions_InformationModel(net,
									dynamics.takeRandomNeighbors(net));
							ct = dynamics.countTotalSynchrony(net);
							i++;
							if( i >= 10000){
								break;
							}
						}
						T = T + i - 1;
					}
					}
					else {
						T=100000000;
					}
					T_fun[dk_iter][steep_iter] = T / ITER;
					steep = steep + diff;
					steep_iter++;
				}
				dk_iter++;
				k++;
			}
			d++;
		}

		// interpolateGraph();
	}

	private void interpolateGraph() {
		System.out.println(dk_axis.length);
		System.out.println(steep_axis.length);

		System.out.println(T_fun[0].length);
		System.out.println(T_fun.length);

	}

	public Plot3DPanel createPlot() {

		Plot3DPanel plot = new Plot3DPanel();
		for (int i = 0; i < dk_axis.length; i++)
			divdk_axis[i] = (double) dk_axis[i][0] / dk_axis[i][1];
		plot.addGridPlot("T(steepness,n)", steep_axis, divdk_axis, T_fun);

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
		int i = 0;
		for (int d = begD; d <= endD; d++) {
			for (int k = begK; k <= endK; k++) {
				//System.out.println(i + " " + d + " " + k);
				dk_axis[i] = new double[2];
				dk_axis[i][0] = (double) d;
				dk_axis[i][1] = (double) k;
				i++;
			}
			
		}

	}

	public void saveTMatrixToFile() throws FileNotFoundException {

		PrintWriter writer = null;

		writer = new PrintWriter(
				"C:\\Users\\natdol\\workspace\\mgr\\mgr\\src\\main\\resources\\tables_Cayley\\Tsteep_d_k_L"
						+ String.valueOf((int) lambda)
						+ "_"
						+ new DecimalFormat("#.#").format(prob).replace(",", ".") + ".txt");

		for (int i = 0; i < steep_axis.length; i++) {
			if (i != steep_axis.length - 1) {
				writer.print(new DecimalFormat("#.##").format(steep_axis[i]).replace(",", ".") + " ");
			} else {
				writer.print(new DecimalFormat("#.##").format(steep_axis[i]).replace(",", ".") + "\n");
			}
		}
		for (int i = 0; i < dk_axis.length; i++) {
			if (i != dk_axis.length - 1) {
				writer.print((int) dk_axis[i][0] + ";" + (int) dk_axis[i][1] + " ");
			} else {
				writer.print((int) dk_axis[i][0] + ";" + (int) dk_axis[i][1] + "\n");
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
				"Tsteep_d_k_L" + String.valueOf(lambda) + "_"
						+ new DecimalFormat("#.#").format(prob).replace(",", ".") + ".txt");

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
			
			begD =  Integer.valueOf(kaa[0].split(";")[0]);
			endD =  Integer.valueOf(kaa[kaa.length - 1].split(";")[0]);
			begK =  Integer.valueOf(kaa[0].split(";")[1]);
			endK =  Integer.valueOf(kaa[kaa.length - 1].split(";")[1]);
			
			dk_axis = new double[(endD - begD + 1)*(endK - begK + 1)][2];
			T_fun = new double[dk_axis.length][(int) ((endSteep - begSteep)
					/ diff + 1)];

			for (String na : kaa) {
				String[] na2 = na.split(";");
				dk_axis[i] = new double[2];
				dk_axis[i][0] = Double.valueOf(na2[0]);
				dk_axis[i][1] = Double.valueOf(na2[1]);
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
		// interpolateGraph();
	}
}
