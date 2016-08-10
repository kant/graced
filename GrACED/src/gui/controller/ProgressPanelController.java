package gui.controller;

import org.controlsfx.control.Notifications;

import gui.application.FileLocation;
import gui.concurrent.SimulatorTask;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;



/**
 * Controller class for the ProgressPanel.fxml panel.
 * @author Ing. Melina C. Vidoni - 2014
 *
 */
public class ProgressPanelController extends Controller {
	private SimpleStringProperty stepNumber;

	@FXML private ProgressBar progressBar;
	
	@FXML private Label doingLabel;
	
	@FXML
	private Label stepTitleLabel;
	
	@FXML
	private Label stepInfoLabel;
	
	@FXML
	private HBox stepsBar;
	
	@FXML
	private ToggleGroup buttonGroup;
	
	@FXML
	private ToggleButton buttonStep1;
	
	@FXML
	private ToggleButton buttonStep2;
	
	@FXML
	private ToggleButton buttonStep3;
	
	@FXML
	private ToggleButton buttonStep4;
	


	
	
    /**
     * The default constructor of the class.
     * The constructor is called before the initialize() method.
     */
	public ProgressPanelController() {
		// Call parent constructor
		super();
		
		// Initialize the property
		stepNumber = new SimpleStringProperty();
	}
	
	
	
	
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
		// Add a listener to the property
		stepNumber.addListener(new ChangeListener<String>() {
			 
			   @Override
			   public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
				   // Change the information on the panel
				   if( !newValue.equals("") )
					   changeStep( Integer.valueOf(newValue) );
			   }
			   
		});
    	
    	
    	// Link the progress bar
    	SimulatorTask st = new SimulatorTask();
    	progressBar.progressProperty().bind(st.progressProperty());
    	stepNumber.bind( st.titleProperty() );
    	doingLabel.textProperty().bind( st.messageProperty() );

    	
    	// Run the long task
        Thread th = new Thread(st);
        th.setDaemon(true);
        th.start();
    }
	

	
    
    
    
    
    /**
     * A method that changes the step information and the button that should be
     * pressed on the HBox panel.
     * @param step the number of the step we are in.
     */
    public void changeStep(int step) {
    	// Use a switch to evaluate the step
    	switch(step) {
    		case 1: // First step
    				buttonStep1.setSelected(true);
    				stepTitleLabel.setText("STEP 1 - Starting GrACED");
    				stepInfoLabel.setText("This step starts the intelligent agent GrACED and the environment, "
    						+ "in order to prepare everything for the further classification. "
    						+ "This step should be faster, and simpler depending on the connection with the database.");
    				
    				break;
    				
    		case 2: // Second step
    				buttonStep2.setSelected(true);
    				stepTitleLabel.setText("STEP 2 - Classifying Tables");
    				stepInfoLabel.setText("This step is the main process of GrACED."
    						+ " GrACED is classifying all the tables of the database in the classes "
    						+ "provided by the standard ANSI/ISA-95, that were added to its knowledge base, and that"
    						+ "were also marked as usable in the process of classification.");
    				break;
    				
    		case 3: // Third Step
    				buttonStep3.setSelected(true);
    				stepTitleLabel.setText("STEP 3 - Propagating Percents");
    				stepInfoLabel.setText("This step is the second more important one in the full process of classification."
    						+ "By extending the belonging percentage obtained in the previous step, GrACED "
    						+ "propagates it upwards the ANSI/ISA-95 categories hierarchy in order to obtain information"
    						+ "on how much the database will adecuate to the standard.");
    				break;
    				
    		case 4: // Fourth Step
    				buttonStep4.setSelected(true);
    				stepTitleLabel.setText("STEP 4 - Generating Charts");
    				stepInfoLabel.setText("The classification and propagation is already finished, but GrACED is still"
    						+ " configuring the charts in order to present the information in a more friendly way. Please wait"
    						+ " while the agent finalizes the process.");
    				break;
    				
    		case 5: // Fifth Step
    				Notifications.create().title("Classification Finished")
    									  .text("GrACED has finished the categorization of information,"
    											  	+ "\nand has also finished the distribution of information")
    									  .showInformation();
    				mainApp.changePanel( FileLocation.CHARTS_PANEL );
    				break;
    	}
    }
	
	    
	

	
}
