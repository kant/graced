package gui.controller;

import gui.application.FileLocation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;




/**
 * Controller class for the IntroPanel.fxml panel.
 * @author Ing. Melina C. Vidoni - 2014
 *
 */
public class IntroPanelController extends Controller {
	@FXML
	private Button nextButton;
	
	
	
    /**
     * The default constructor of the class.
     * The constructor is called before the initialize() method.
     */
	public IntroPanelController() { 
		super();
	}
	

	    
    
    /**
     * Called when the user clicks on the exit button.
     */    
    @FXML
    private void nextPanel() {
    	// Change to the next panel
    	mainApp.changePanel( FileLocation.BASE_INFO_PANEL );
    }
	
}
