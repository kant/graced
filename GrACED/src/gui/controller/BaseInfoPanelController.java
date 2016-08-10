package gui.controller;

import gui.application.FileLocation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import org.controlsfx.dialog.DialogStyle;
import org.controlsfx.dialog.Dialogs;

import database.DatabaseException;
import database.ErpDB;
import database.enums.TypesDB;




/**
 * Controller class for the BaseInfoPanel.fxml panel.
 * @author Ing. Melina C. Vidoni - 2014
 *
 */
public class BaseInfoPanelController extends Controller {
	@FXML
	private Button nextButton;

	@FXML
	private TextField dbName;
	
	@FXML
	private TextField ipLocation;
	
	@FXML
	private TextField ipPort;
	
	@FXML
	private TextField loginName;
	
	@FXML
	private PasswordField loginPass;
	
	@FXML
	private ComboBox<TypesDB> dbType;


	
    /**
     * The default constructor of the class.
     * The constructor is called before the initialize() method.
     */
	public BaseInfoPanelController() { 
		super();
	}
	
	
	
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    void initialize() {
        // Initialize the logic
    	dbType.getItems().clear();
    	dbType.getItems().addAll( TypesDB.getAll() );
    	dbType.setValue(TypesDB.POSTGRESQL);
   }
    
    
    
    
    
    @FXML
	public void actionNext() {	
  	
		// Lets check if the fields are filled
		if ( (dbName.getText().isEmpty() || dbName.getText() == "")
			 || (ipLocation.getText().isEmpty() || ipLocation.getText() == "")	
			 || (ipPort.getText().isEmpty() || ipPort.getText() == "")
			 || (loginName.getText().isEmpty() || loginName.getText() == "")
			 || (loginPass.getText().isEmpty() || loginPass.getText() == "")
		   ) {

			System.out.println("Should popup an dialog");

	    	// Show a dialog
	    	Dialogs.create()
	    			.style(DialogStyle.NATIVE)
	    			.owner( mainApp.getPrimaryStage() )
	    			.title("Missing Data")
	    			.masthead(null)
	    			.message("The fields cannot remain empty.\nPlease fill all the fields with the required data.")
	    			.showWarning();
		}

		// If everything is OK
		else {
			// Prepare the connection
			ErpDB erpDB = ErpDB.getInstance();
			erpDB.setLocation( ipLocation.getText() );
			erpDB.setPort( ipPort.getText() );
			erpDB.setDB( dbName.getText() );
			erpDB.setUser( loginName.getText() );
			erpDB.setPassword( loginPass.getText() );
			erpDB.setType( dbType.getValue().toString() );
			erpDB.setServer();
			
			
			try {
				// Now try to connect
				erpDB.connect();
				erpDB.close();
				
				// Change the panel
				mainApp.changePanel(FileLocation.WORD_SEPARATION_PANEL);
				
			} 
			catch (DatabaseException e) {
				// If it is here, the data isn't working
				// Show a message
		    	Dialogs.create()
    				.owner( mainApp.getPrimaryStage() )
    				.style(DialogStyle.NATIVE)
    				.title("Failed to Connect")
    				.masthead(null)
    				.message("GrACED couldn't stablish a connection with the proposed data.\nPlease control the input and try again.")
    				.showError();
			}
		}
		

	}	

	
	
}
