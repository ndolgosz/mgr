package mgr;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JFrame;

import org.math.plot.Plot2DPanel;

public class AgentsOpinionsInTime {

	static int ITER = 2000;
	static double prob = 0.0;
	static double steep = 1.0;
	static String model = "INF"; // INF, DEF, BASIC
	
	public static void main(String[] args) {

		Net net = new Net(20, 4);
		net.configureInformationModel(prob, steep);
		DynamicsFunctions dyn = new DynamicsFunctions();
		DeffuantModelDynamics def = new DeffuantModelDynamics();
		HashMap<Integer, double[]> mapOpinions = new HashMap<>();
		double[] time = new double[ITER];

		for (Integer i : net.agentsVertices.keySet()) {
			mapOpinions.put(i, new double[ITER]);
			mapOpinions.get(i)[0] = net.agentsVertices.get(i).getOpinion();
		}

		time[0] = 0;
		for (int i = 1; i < ITER; i++) {
			time[i] = i;
			
			if(model.equals("INF")){
				dyn.updateOpinions_InformationModel(net,dyn.takeRandomNeighbors(net));
			}
			else if(model.equals("DEF")){
				def.updateOpinions_DeffuantModel(net, dyn.takeRandomNeighbors(net),180);
			}
			else if(model.equals("BASIC")){
				dyn.updateOpinions_BasicModel(dyn.takeRandomNeighbors(net));
			}

			for (int j : net.agentsVertices.keySet()) {
				mapOpinions.get(j)[i] = net.agentsVertices.get(j).getOpinion();
			}

		}
		
		
		JFrame frame = new JFrame("a plot panel");
		frame.setContentPane(createPlot(mapOpinions, time, net));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(1000, 500);
		
	}
public static Plot2DPanel createPlot(HashMap<Integer, double[]> mapOpinions, double[] time, Net net) {
		
		Plot2DPanel plot = new Plot2DPanel();
		plot.setAxisLabels("t", "opinion");
		
		for (Entry<Integer, double[]> entry : mapOpinions.entrySet()) {
			Color color = Color.gray;
			if(entry.getKey() == net.BM)
				color = Color.red;
			else if(entry.getKey() == net.TI)
				color = Color.blue;
			plot.addLinePlot(entry.getKey().toString(), color, time, entry.getValue());
		}
		plot.addLegend("SOUTH");
		return plot;
	}
}
