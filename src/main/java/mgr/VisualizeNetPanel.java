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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.text.AbstractDocument.Content;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.generators.EvolvingGraphGenerator;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
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
	private Graph<Integer, String> m_graph;
	private Layout<Integer, String> mVisualizer;
	private VisualizationViewer<Integer, String> mVizViewer;
	public static Net net;
	
	public VisualizeNetPanel(Net net) {
		VisualizeNetPanel.net = net;
		m_graph = net.net;
		mVisualizer = new FRLayout<Integer, String>(m_graph);

		mVizViewer = new VisualizationViewer<Integer, String>(mVisualizer);
		mVizViewer.setBackground(Color.WHITE);
		Transformer<Integer, String> transformer = new Transformer<Integer, String>() {

			@Override
			public String transform(Integer arg0) {	
				return arg0.toString();
			}};
		    mVizViewer.getRenderContext().setVertexLabelTransformer(transformer);
		mVizViewer.getRenderer().setVertexRenderer(new MyRenderer());
		add(mVizViewer);
		mVisualizer.reset();
		mVizViewer.revalidate();
		mVizViewer.repaint();
		
	}
	
	public void repaintPanel(){
		mVizViewer.getRenderer().setVertexRenderer(new MyRenderer());
		mVisualizer.reset();
		mVizViewer.revalidate();
		mVizViewer.repaint();
	}
	
	static class MyRenderer implements Vertex<Integer, String> {
	   
		@Override
		public void paintVertex(RenderContext<Integer, String> rc,
				Layout<Integer, String> layout, Integer vertex) {

		      GraphicsDecorator graphicsContext = rc.getGraphicsContext();
		      Point2D center = layout.transform(vertex);
		      Shape shape;
		      if(net.BM != -1 && vertex.intValue() == net.BM){
		    	  shape = new Ellipse2D.Double(center.getX()-10, center.getY()-10, 40, 40);
		      }
		      else if (net.TI != -1 && vertex.intValue() == net.TI) {
		    	  shape = new Rectangle2D.Double(center.getX()-10, center.getY()-10, 30, 30);
		      }
		      else {
		    	  shape = new Ellipse2D.Double(center.getX()-10, center.getY()-10, 20, 20);
		      }
		      Color color = Color.getHSBColor((float) net.agentsVertices.get(vertex.intValue()).getOpinion()/360,(float) 0.9, (float) 0.9);
		      graphicsContext.setPaint(color);
		      graphicsContext.fill(shape);
		}	
	  }
	public static void main(String[] args) {
		
		JFrame jf = new JFrame("network");
		Container pane = jf.getContentPane();
		
		Net net = new Net(20, 4,2,0);
		final VisualizeNetPanel panel = new VisualizeNetPanel(net);
		JButton start = new JButton("20 meetings");
		start.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				evolution(panel, 20);		
			}
		});
		
		pane.add(start,BorderLayout.SOUTH);
		jf.add(panel, BorderLayout.CENTER);
	
		jf.setSize(700, 500);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.pack();
		jf.setVisible(true);

	}
	
	public static void evolution(VisualizeNetPanel panel, int iter){
		DynamicsFunctions dyn = new DynamicsFunctions();
		for(int i = 0; i < iter; i++){
			if(net.TI == -1){
				dyn.updateOpinions_BasicModel(dyn.takeRandomNeighbors(net));
			}
			else {
				dyn.updateOpinions_InformationModel(net,dyn.takeRandomNeighbors(net));
			}
		}
		panel.repaintPanel();
	}

}