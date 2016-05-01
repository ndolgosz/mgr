package mgr;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D;
import java.rmi.UnexpectedException;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.naming.directory.InvalidAttributesException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument.Content;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.generators.EvolvingGraphGenerator;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.graph.Forest;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.renderers.Renderer.Vertex;
import edu.uci.ics.jung.visualization.transform.shape.GraphicsDecorator;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationViewer;

public class VisualizeNetPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Layout<Integer, String> mVisualizer;
	private VisualizationViewer<Integer, String> mVizViewer;
	//public static Net net;
	//public static Net net;
	public static NetCayley net;
	
	public static String interaction = "INF"; // "INF", "BASIC"

	public VisualizeNetPanel() {
		restart();
	}

	public void repaintPanel() {

		mVizViewer.getRenderer().setVertexRenderer(new MyRenderer());
		mVisualizer.reset();
		mVizViewer.revalidate();
		mVizViewer.repaint();
	}

	protected void restart() {

		/* -------------- SETTINGS 1 ----------------------- */
		// net = new NetBA(20);
		net = new NetCayley(3,4);
		//net = new Net(20,4);
		
	

		/* -------------- SETTINGS 2 ----------------------- */

		if(!interaction.equals("BASIC")){
		net.configureInformationModel(0.0, 1.0);
		
		net.setBMtoCenter();


		//try {
		//	net.setTIdistBM(2);
		//} catch (UnexpectedException e) {
			
		//	e.printStackTrace();
		//}
		}
		for (Integer node : net.net.getVertices()) {
			System.out.println(node);
		}

		mVisualizer = new KKLayout(net.net);
		//mVisualizer = new CircleLayout(net.net);
		mVizViewer = new VisualizationViewer<Integer, String>(mVisualizer);
		mVizViewer.setBackground(Color.WHITE);
		Transformer<Integer, String> transformer = new Transformer<Integer, String>() {

			@Override
			public String transform(Integer arg0) {
				return arg0.toString();
			}
		};
		mVizViewer.getRenderContext().setVertexLabelTransformer(transformer);
		mVizViewer.getRenderer().setVertexRenderer(new MyRenderer());
		add(mVizViewer);
		repaintPanel();
	}

	static class MyRenderer implements Vertex<Integer, String> {

		@Override
		public void paintVertex(RenderContext<Integer, String> rc,
				Layout<Integer, String> layout, Integer vertex) {

			GraphicsDecorator graphicsContext = rc.getGraphicsContext();
			Point2D center = layout.transform(vertex);
			Shape shape;
			if (net.BM != -1 && vertex.intValue() == net.BM) {
				shape = new Ellipse2D.Double(center.getX() - 20,
						center.getY() - 20, 40, 40);
			} else if (net.TI != -1 && vertex.intValue() == net.TI) {
				shape = new Rectangle2D.Double(center.getX() - 15,
						center.getY() - 15, 30, 30);
			} else {
				shape = new Ellipse2D.Double(center.getX() - 10,
						center.getY() - 10, 20, 20);
			}
			Color color = Color.getHSBColor(
					(float) net.agentsVertices.get(vertex.intValue())
							.getOpinion() / 360, (float) 0.9, (float) 0.9);
			graphicsContext.setPaint(color);
			graphicsContext.fill(shape);
		}
	}

	public static void main(String[] args) {

		JFrame jf = new JFrame("network");
		Container pane = jf.getContentPane();

		final VisualizeNetPanel panel = new VisualizeNetPanel();
		final JTextField meetings = new JTextField(String.valueOf(
				evolution(panel, 0)).substring(0, 5));
		JButton ev20 = new JButton("20 meetings");
		ev20.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				meetings.setText(String.valueOf(evolution(panel, 20))
						.substring(0, 5));
			}
		});

		JButton ev1 = new JButton("1 meeting");
		ev1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				meetings.setText(String.valueOf(evolution(panel, 1)).substring(
						0, 5));
			}
		});

		JButton reset = new JButton("reset");
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.restart();
				meetings.setText(String.valueOf(evolution(panel, 0)).substring(
						0, 5));
			}
		});

		JButton ev1000 = new JButton("1000 meetings");
		ev1000.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				meetings.setText(String.valueOf(evolution(panel, 1000))
						.substring(0, 2));
			}
		});

		JPanel panelSouth = new JPanel();
		pane.add(panelSouth, BorderLayout.SOUTH);
		panelSouth.add(reset);
		panelSouth.add(ev1);
		panelSouth.add(ev20);
		panelSouth.add(ev1000);
		panelSouth.add(new JTextArea("Synchronization parameter: "));
		panelSouth.add(meetings);
		jf.add(panel, BorderLayout.CENTER);

		jf.setSize(700, 500);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.pack();
		jf.setVisible(true);

	}

	public static double evolution(VisualizeNetPanel panel, int iter) {
		DynamicsFunctions dyn = new DynamicsFunctions();
		DeffuantModelDynamics def = new DeffuantModelDynamics();
		double synchrony = 0.0;
		for (int i = 0; i < iter; i++) {
			Agent[] agents = def.takeRandomNeighbors(net);
			if (net.TI == -1 && interaction.equals("BASIC")) {
				dyn.updateOpinions_BasicModel(agents);
			} 
			else if(net.TI > 0 && interaction.equals("DEF")){
				
				def.updateOpinions_DeffuantModel(net, agents, 180);
			}
			else if(net.TI > 0 && interaction.equals("INF")){			
				dyn.updateOpinions_InformationModel(net,agents);
			}	
			else {
				try {
					throw new InvalidAttributesException();
				} catch (InvalidAttributesException e) {
					e.printStackTrace();
				}
			}
		}
		if (net.TI == -1) {
			synchrony = dyn.countBasicTotalSynchrony(net);
		} else {
			synchrony = def.countTotalSynchrony(net);
		}
		panel.repaint();
		return synchrony;
	}

}