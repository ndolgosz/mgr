package tables;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.interpolation.*;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.math.plot.Plot3DPanel;

public class TableInterpolation {

	public static double[][] fitPlot2D(double[] x, double[] y, double[][] z) {
		Map<Double, double[]> mapX = new HashMap<>();
		Map<Double, double[]> mapY = new HashMap<>();
		double[][] resultMap = new double[x.length][y.length];

		for (int k = 0; k < y.length; k++) {
			WeightedObservedPoints points = new WeightedObservedPoints();
			for (int i = 0; i < x.length; i++) {
				points.add(x[i], z[i][k]);
			}
			mapY.put(y[k], PolynomialCurveFitter.create(3).fit(points.toList()));
		}

		for (int i = 0; i < x.length; i++) {
			WeightedObservedPoints points = new WeightedObservedPoints();
			for (int k = 0; k < y.length; k++) {
				points.add(y[k], z[i][k]);
			}
			mapX.put(x[i], PolynomialCurveFitter.create(3).fit(points.toList()));
		}

		for (int i = 0; i < x.length; i++) {
			for (int j = 0; j < y.length; j++) {
				resultMap[i][j] = (getValue(mapX.get(x[i]), y[j]) + getValue(
						mapY.get(y[j]), x[i])) / 2;
			}
		}
		return resultMap;
	}

	public static PolynomialFunction fitPolynomial1D(double[] x, double[] y) {
		WeightedObservedPoints points = new WeightedObservedPoints();
		for (int i = 0; i < x.length; i++) {
			points.add(x[i], y[i]);
		}

		double[] coeff = PolynomialCurveFitter.create(3).fit(points.toList());

		PolynomialFunction fun = new PolynomialFunction(coeff);
		return fun;
	}

	public static double[] getValues(PolynomialFunction fun, double[] x) {
		double[] fitted = new double[x.length];
		for (int i = 0; i < x.length; i++) {
			fitted[i] = fun.value(x[i]);
		}
		return fitted;
	}

	public static double[] getValues(double[] coeff, double[] x) {
		double[] fitted = new double[x.length];
		for (int i = 0; i < x.length; i++) {
			fitted[i] = 0;
			for (int j = 0; j < coeff.length; j++) {
				fitted[i] = fitted[i] + coeff[j] * Math.pow(x[i], j);
			}
		}
		return fitted;
	}

	private static double getValue(double[] coeff, double x) {
		double fitted = 0;
		for (int j = 0; j < coeff.length; j++) {
			fitted = fitted + coeff[j] * Math.pow(x, j);
		}
		return fitted;
	}

	public static double[] interpolatePlot1D(double[] x, double[] y) {

		UnivariateInterpolator interpolator = new SplineInterpolator();
		UnivariateFunction function = interpolator.interpolate(x, y);
		double[] intY = new double[x.length];
		for (int i = 0; i < x.length; i++) {
			intY[i] = function.value(x[i]);
		}

		return intY;
	}

	public static double[][] interpolatePlot2D(double[] x, double[] y,BicubicSplineInterpolatingFunction function) {

		
		double[][] interpolatedData = new double[x.length][y.length];

		for (int i = 0; i < x.length; i++) {
			for (int j = 0; j < y.length; j++) {
				interpolatedData[i][j] = function.value(x[i], y[j]);
			}
		}
		return interpolatedData;
	}
	
	public static BicubicSplineInterpolatingFunction getInterpolatedFunction(double[] x, double[] y, double[][] z){
		SmoothingPolynomialBicubicSplineInterpolator interpolator = new  SmoothingPolynomialBicubicSplineInterpolator();
		return interpolator.interpolate(
				x, y, z);
	}


}
