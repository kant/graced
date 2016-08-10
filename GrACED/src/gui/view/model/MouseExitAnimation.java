package gui.view.model;



import javafx.animation.TranslateTransitionBuilder;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;



/**
 * Handler for the animation that shows up when the mouse gets out of the zone of a slice
 * of a certain piechart where this is added up.
 * Credits: http://tomsondev.bestsolution.at/2012/11/21/animating-the-javafx-piechart-a-bit/
 * @author Tom Schindl, 2012.
 *
 */
@SuppressWarnings("deprecation")
public class MouseExitAnimation implements EventHandler<MouseEvent> {
	
	
	/**
	 * Invoked when the mouseExited event gets fired up on the slice
	 * of a certain piechart.
	 */
	@Override
	public void handle(MouseEvent event) {
		TranslateTransitionBuilder.create().toX(0).toY(0)
			.duration(new Duration(500)).node((Node) event.getSource()).build().play();
	}
	
	
}