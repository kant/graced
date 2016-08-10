package gui.view.model;


import javafx.animation.TranslateTransitionBuilder;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;




/**
 * Handler for the animation that shows up when the mouse enters on a slice
 * of a certain piechart where this is added up.
 * Credits: http://tomsondev.bestsolution.at/2012/11/21/animating-the-javafx-piechart-a-bit/
 * @author Tom Schindl, 2012.
 *
 */
@SuppressWarnings("deprecation")
public class MouseHoverAnimation implements EventHandler<MouseEvent> {
	  static final Duration ANIMATION_DURATION = new Duration(500);
	  static final double ANIMATION_DISTANCE = 0.15;
	  private double cos;
	  private double sin;
	  private PieChart chart;
	 
	  
	  /**
	   * Default constructor of the class.
	   * @param d The slice of the chart where the user will hover.
	   * @param chart The complete chart.
	   */
	  public MouseHoverAnimation(PieChart.Data d, PieChart chart) {
		  this.chart = chart;
		  double start = 0;
		  double angle = calcAngle(d);
		  for( PieChart.Data tmp : chart.getData() ) {
			  if( tmp == d ) {
				  break;
			  }
			  start += calcAngle(tmp);
		  }
	 
		  cos = Math.cos(Math.toRadians(0 - start - angle / 2));
		  sin = -Math.sin(Math.toRadians(0 - start - angle / 2));
	  }
	 
	  
	  
	  /**
	   * Invoked when the mouseEntered event gets fired up.
	   */
	  @Override
	  public void handle(MouseEvent arg0) {
		  Node n = (Node) arg0.getSource();
		 
		  double minX = Double.MAX_VALUE;
		  double maxX = Double.MAX_VALUE * -1;

		             
		  for( PieChart.Data d : chart.getData() ) {
			  minX = Math.min(minX, d.getNode().getBoundsInParent().getMinX());
		      maxX = Math.max(maxX, d.getNode().getBoundsInParent().getMaxX());
		  }
		 
		  double radius = maxX - minX;
		  TranslateTransitionBuilder.create().toX((radius *  ANIMATION_DISTANCE) * cos)
		  		.toY((radius *  ANIMATION_DISTANCE) * sin).duration(ANIMATION_DURATION).node(n).build().play();
	  }
	  
	  
	  
	  
	  /**
	   * Method that calculates the angle of movement of the slice
	   * @param d The slice that will be moving.
	   * @return The angle of movement.
	   */
	  private static double calcAngle(PieChart.Data d) {
		  double total = 0;
		  for( PieChart.Data tmp : d.getChart().getData() ) {
			  total += tmp.getPieValue();
		  }
	             
		  return 360 * (d.getPieValue() / total);
	  }
	  
	  
}