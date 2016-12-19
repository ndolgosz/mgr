package mgr;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.awt.geom.Point2D;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.naming.directory.InvalidAttributesException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.apache.commons.collections15.Transformer;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.renderers.Renderer.Vertex;
import edu.uci.ics.jung.visualization.transform.shape.GraphicsDecorator;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationViewer;

public class VisualizeNetPanel extends JPanel {

	/**
	 * Java Swing aplet used for net visualization
	 */
	private static final long serialVersionUID = 1L;

	private Layout<Integer, String> layout;
	private VisualizationViewer<Integer, String> mVizViewer;
	private static double steepness = 1.0;
	private static double epsilon = 180;

	public static String[] topologies = { "ER", "BA", "TREE" };
	public static String[] interactions = { "INF", "DEF" };
	public static NetCayley netTree;
	public static NetBA netBA;
	public static Net net;
	public static HashMap<Integer, Agent> agents;
	public static String netType; // ER, BA, TREE
	public static int BM = -1;
	public static int TI = -1;
	public static int N = 20;
	public static int k = 4;
	public static int depth = 3;

	public static Plot plot;
	public static XYSeriesCollection dataset;
	public static XYSeries series;
	public static JFreeChart xylineChart;

	public static String interaction = "INF";

	public VisualizeNetPanel() {
		series = new XYSeries("Synchronization");
		XYSeriesCollection dataset = new XYSeriesCollection(series);
		xylineChart = ChartFactory.createXYLineChart("", "t", "c(t)", dataset, PlotOrientation.VERTICAL, false, false,
				false);

	}

	private static void addLabelTextRows(JLabel[] labels, JComponent[] textFields, GridBagLayout gridbag,
			Container container) {
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.PAGE_START;
		int numLabels = labels.length;

		for (int i = 0; i < numLabels; i++) {
			c.gridwidth = GridBagConstraints.REMAINDER; // next-to-last
			c.fill = GridBagConstraints.CENTER; // reset to default
			c.weightx = 3.0;
			c.ipady = 5;// reset to default

			container.add(labels[i], c);

			c.gridwidth = GridBagConstraints.REMAINDER;
			c.gridheight = 1;
			// c.ipady = 20;// end row
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1.0;

			container.add(textFields[i], c);
		}
	}

	public void repaintPanel() {

		layout.reset();

		mVizViewer.revalidate();
		mVizViewer.repaint();
		mVizViewer.updateUI();
	}

	protected void restart() {

		if ("BA".equals(netType)) {
			netBA = new NetBA(N);
			agents = new HashMap<>();
			netBA.configureInformationModel(0.0, steepness);
			layout = new KKLayout(netBA.net);
			agents = netBA.agentsVertices;
			TI = netBA.TI;
			BM = netBA.BM;

		} else if ("ER".equals(netType)) {
			net = new Net(N, k);
			agents = new HashMap<>();
			net.configureInformationModel(0.0, steepness);
			layout = new KKLayout(net.net);
			agents = net.agentsVertices;

			TI = net.TI;
			BM = net.BM;
		} else {
			netTree = new NetCayley(depth);
			netTree.configureInformationModel(0.0, steepness);
			layout = new KKLayout(netTree.net);
			agents = netTree.agentsVertices;
			TI = netTree.TI;
			BM = netTree.BM;
		}

		series.clear();

		removeAll();
		mVizViewer = new VisualizationViewer<Integer, String>(layout);
		mVizViewer.setGraphLayout(layout);

		mVizViewer.setBackground(Color.WHITE);
		Transformer<Integer, String> transformer = new Transformer<Integer, String>() {

			@Override
			public String transform(Integer arg0) {
				return String.valueOf(agents.get(arg0).getWeight()).substring(0, 3);
			}
		};
		mVizViewer.getRenderContext().setVertexLabelTransformer(transformer);
		mVizViewer.getRenderer().setVertexRenderer(new MyRenderer());
		add(mVizViewer);
		repaintPanel();
	}

	static class MyRenderer implements Vertex<Integer, String> {

		@Override
		public void paintVertex(RenderContext<Integer, String> rc, Layout<Integer, String> layout, Integer vertex) {

			GraphicsDecorator graphicsContext = rc.getGraphicsContext();
			Point2D center = layout.transform(vertex);
			int size = (int) Math.round(agents.get(vertex).getWeight() * 50);
			Shape shape;
			if (BM != -1 && vertex.intValue() == BM) {
				shape = new Ellipse2D.Double(center.getX() - 20, center.getY() - 20, size, size);
			} else if (TI != -1 && vertex.intValue() == TI) {
				shape = new Rectangle2D.Double(center.getX() - 15, center.getY() - 15, 40, 40);
			} else {
				shape = new Ellipse2D.Double(center.getX() - size, center.getY() - size, size + 5, size + 5);
			}
			Color color = Color.getHSBColor((float) agents.get(vertex.intValue()).getOpinion() / 360, (float) 0.9,
					(float) 0.9);
			graphicsContext.setPaint(color);
			graphicsContext.fill(shape);
		}
	}

	public static void main(String[] args) {

		JFrame jf = new JFrame("network");
		Container pane = jf.getContentPane();
		jf.setSize(400, 600);

		final VisualizeNetPanel panel = new VisualizeNetPanel();
		panel.setSize(400, 600);
		JPanel westPanel = new JPanel();
		pane.add(westPanel, BorderLayout.WEST);
		final JTextField meetings = new JTextField("new net");
		meetings.setEditable(false);

		JTextField steepnessArea = new JTextField(String.valueOf(steepness));
		steepnessArea.setEditable(true);
		JTextField eps = new JTextField(String.valueOf(epsilon));
		eps.setEditable(true);
		JComboBox<String> topology = new JComboBox<String>(topologies);
		topology.setSelectedItem(1);
		JComboBox<String> dynamics = new JComboBox<String>(interactions);
		dynamics.setSelectedItem(1);
		JTextField fieldN = new JTextField(String.valueOf(N));
		JTextField fieldK = new JTextField(String.valueOf(k));
		JTextField fieldD = new JTextField(String.valueOf(depth));
		JLabel nodesLabel = new JLabel("Number of nodes:");
		JLabel kLabel = new JLabel("Node's degree:");
		JLabel rLabel = new JLabel("Tree's radius:");
		interaction = (String) dynamics.getSelectedItem();
		netType = (String) topology.getSelectedItem();

		JButton reset = new JButton("RESTART");
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				steepness = Double.valueOf(steepnessArea.getText());
				netType = (String) topology.getSelectedItem();
				interaction = (String) dynamics.getSelectedItem();
				epsilon = Double.valueOf(eps.getText());
				if (fieldK.getParent() == westPanel && netType.equals("ER")) {
					k = (Integer) Integer.valueOf(fieldK.getText());
					if (k <= 1 || k > 100) {
						fieldK.setText("Wrong arguments");
						return;
					}

				}
				if (fieldN.getParent() == westPanel && !netType.equals("TREE")) {
					N = (Integer) Integer.valueOf(fieldN.getText());
					if (N <= 1 || N < k) {
						fieldN.setText("Wrong arguments");
						return;
					}
				}
				if (fieldD.getParent() == westPanel && netType.equals("TREE")) {
					depth = (Integer) Integer.valueOf(fieldD.getText());
					if (depth < 1) {
						fieldD.setText("Wrong arguments");
						return;
					}
				}
				panel.restart();
				meetings.setText(String.valueOf(evolution(panel, 1)).substring(0, 3));

			}
		});

		dynamics.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (dynamics.getSelectedItem().equals("DEF"))
					eps.setEnabled(true);
				else
					eps.setEnabled(false);

			}
		});

		topology.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GridBagConstraints c = new GridBagConstraints();
				c.anchor = GridBagConstraints.CENTER;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridwidth = GridBagConstraints.REMAINDER;
				c.gridx = 0;
				c.ipadx = 1;

				if (topology.getSelectedItem().equals("ER")) {
					westPanel.remove(rLabel);
					westPanel.remove(fieldD);
					if (fieldN.getParent() != westPanel) {
						westPanel.add(nodesLabel, c);
						westPanel.add(fieldN, c);
					}
					if (fieldK.getParent() != westPanel) {
						westPanel.add(kLabel, c);
						westPanel.add(fieldK, c);
					}
					westPanel.repaint();
					westPanel.revalidate();
					westPanel.updateUI();
				} else if (topology.getSelectedItem().equals("BA")) {
					westPanel.remove(fieldK);
					westPanel.remove(rLabel);
					westPanel.remove(fieldD);
					westPanel.remove(kLabel);

					if (fieldN.getParent() != westPanel) {
						westPanel.add(nodesLabel, c);
						westPanel.add(fieldN, c);
					}

					westPanel.repaint();
					westPanel.revalidate();
					westPanel.updateUI();

				} else if (topology.getSelectedItem().equals("TREE")) {

					westPanel.remove(fieldK);
					westPanel.remove(kLabel);
					westPanel.remove(nodesLabel);
					westPanel.remove(fieldN);

					if (fieldD.getParent() != westPanel) {
						westPanel.add(rLabel, c);
						westPanel.add(fieldD, c);
					}
					westPanel.repaint();
					westPanel.revalidate();
					westPanel.updateUI();

				}

			}
		});

		GridBagLayout gridbag = new GridBagLayout();
		westPanel.setLayout(gridbag);

		JPanel northPanel = new JPanel();
		pane.add(northPanel, BorderLayout.NORTH);

		JLabel resetLabel = new JLabel("Start simulations:");
		resetLabel.setLabelFor(steepnessArea);

		JLabel steepLabel = new JLabel("Steepness: ");
		steepLabel.setLabelFor(steepnessArea);

		JLabel topologyLabel = new JLabel("Topology: ");
		topologyLabel.setLabelFor(topology);

		JLabel interactionsLabel = new JLabel("Interactions: ");
		interactionsLabel.setLabelFor(dynamics);

		JLabel epsLabel = new JLabel("Opinion threshold 0-360: ");
		epsLabel.setLabelFor(dynamics);
		eps.setEnabled(false);

		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.PAGE_START;
		c.fill = GridBagConstraints.CENTER;
		c.ipady = 2;
		c.gridwidth = GridBagConstraints.REMAINDER;

		westPanel.add(resetLabel, c);
		c.ipady = 40;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 30, 0);
		reset.setBackground(Color.PINK);
		westPanel.add(reset, c);
		JLabel[] labels = { steepLabel, topologyLabel, interactionsLabel, epsLabel, new JLabel(" "),
				new JLabel("Synchronization: "), nodesLabel, kLabel };
		JComponent[] textFields = { steepnessArea, topology, dynamics, eps, new JLabel(" "), meetings, fieldN, fieldK };
		addLabelTextRows(labels, textFields, gridbag, westPanel);
		westPanel.repaint();
		westPanel.revalidate();

		JPanel southPanel = new JPanel();
		pane.add(southPanel, BorderLayout.SOUTH);

		ChartPanel chartPanel = new ChartPanel(xylineChart);

		chartPanel.setPreferredSize(new Dimension(750, 150));
		southPanel.add(chartPanel);

		jf.add(panel, BorderLayout.CENTER);

		JButton ev1 = new JButton("1 meeting");
		ev1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				meetings.setText(String.valueOf(evolution(panel, 1)).substring(0, 3));
				chartPanel.repaint();
			}
		});
		JButton ev20 = new JButton("20 meetings");
		ev20.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				meetings.setText(String.valueOf(evolution(panel, 20)).substring(0, 3));
				chartPanel.repaint();
			}
		});
		JButton ev500 = new JButton("500 meetings");
		ev500.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				meetings.setText(String.valueOf(evolution(panel, 500)).substring(0, 3));
				chartPanel.repaint();
			}
		});
		JButton ev1000 = new JButton("1000 meetings");
		ev1000.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				meetings.setText(String.valueOf(evolution(panel, 1000)).substring(0, 3));
				chartPanel.repaint();
			}
		});

		JButton ev2000 = new JButton("2000 meetings");
		ev2000.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				meetings.setText(String.valueOf(evolution(panel, 2000)).substring(0, 3));
				chartPanel.repaint();
			}
		});
		northPanel.setLayout(new FlowLayout(2));
		northPanel.add(ev1);
		northPanel.add(ev20);
		northPanel.add(ev500);
		northPanel.add(ev1000);
		northPanel.add(ev2000);
		northPanel.add(new JLabel("         "));
		jf.setSize(800, 800);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		jf.setVisible(true);
		westPanel.setBackground(Color.WHITE);
		panel.setBackground(Color.WHITE);
		northPanel.setBackground(Color.lightGray);
		southPanel.setBackground(Color.lightGray);

	}

	public static double evolution(VisualizeNetPanel panel, int iter) {
		DynamicsFunctions dyn = new DynamicsFunctions();
		DeffuantModelDynamics def = new DeffuantModelDynamics();
		double synchrony = 0.0;
		for (int i = 0; i < iter; i++) {
			Agent[] agents = null;
			if (netType.equals("BA"))
				agents = def.takeRandomNeighbors(netBA);
			else if (netType.equals("ER"))
				agents = def.takeRandomNeighbors(net);
			else
				agents = def.takeRandomNeighbors(netTree);

			if (TI > 0 && interaction.equals("DEF")) {

				if (netType.equals("BA"))
					def.updateOpinions_DeffuantModel(netBA, agents, epsilon);
				else if (netType.equals("ER"))
					def.updateOpinions_DeffuantModel(net, agents, epsilon);
				else
					def.updateOpinions_DeffuantModel(netTree, agents, epsilon);
			} else if (TI > 0 && interaction.equals("INF")) {
				if (netType.equals("BA"))
					dyn.updateOpinions_InformationModel(netBA, agents);
				else if (netType.equals("ER"))
					dyn.updateOpinions_InformationModel(net, agents);
				else
					dyn.updateOpinions_InformationModel(netTree, agents);

			} else {
				try {
					throw new InvalidAttributesException();
				} catch (InvalidAttributesException e) {
					e.printStackTrace();
				}
			}

			if (netType.equals("BA"))
				synchrony = def.countTotalSynchrony(netBA);
			else if (netType.equals("ER"))
				synchrony = def.countTotalSynchrony(net);
			else
				synchrony = def.countTotalSynchrony(netTree);

			series.add(i + series.getItemCount(), synchrony);

		}

		panel.repaint();
		return synchrony;
	}

}