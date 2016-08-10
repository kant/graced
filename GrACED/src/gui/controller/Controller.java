package gui.controller;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.DialogStyle;
import org.controlsfx.dialog.Dialogs;

import gui.application.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;




/**
 * A class that starts the controller hierarchy on the system.
 * @author Ing. Melina C. Vidoni - 2014
 *
 */
public abstract class Controller {
	protected MainApp mainApp;
	
	@FXML
	private Button exitButton;
	
	
	
	
    /**
     * The default constructor of the class.
     * The constructor is called before the initialize() method.
     */
	public Controller() { }
	
	
	
	
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() { }


    
    /**
     * Is called by the main application to give a reference back to itself.
     * @param ma
     */
    public void setMainApp(MainApp ma) {
        mainApp = ma;
    }
	
    
    
    
    
    /**
     * Called when the user clicks on the exit button.
     */
    @FXML
    private void exitSystem() {
    	// Show a dialog
    	Action response = Dialogs.create()
    			.owner( mainApp.getPrimaryStage() )
    			.style(DialogStyle.NATIVE)
	            .title("Exiting GrACED")
	            .masthead(null)
	            .message("Are you sure you want to exit GrACE?\nAll progress will be lost.")
	            .showConfirm();

    	// Now evaluate the response
    	if (response == Dialog.Actions.YES) {
    		// Bye bye~
    	    System.exit(0);
    	} 
    	else {
    		// Nothing happens here...
    	}
    	

    }
}
