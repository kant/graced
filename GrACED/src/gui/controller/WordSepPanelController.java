package gui.controller;

import gui.application.FileLocation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import database.Separation;
import database.enums.SeparationChars;
import database.enums.WordSeparation;





/**
 * Controller class for the WordSeparationPanel.fxml panel.
 * @author Ing. Melina C. Vidoni - 2014
 *
 */
public class WordSepPanelController extends Controller {
	final ToggleGroup group = new ToggleGroup();
	
	@FXML
	private Button nextButton;
	
	@FXML
	private RadioButton pascalRB;
	
	@FXML
	private RadioButton camelRB;
	
	@FXML
	private RadioButton specialRB;
	
	@FXML
	private RadioButton mixedRB;
	
	@FXML
	private Label pascalExample;
	
	@FXML
	private Label camelExample;
	
	@FXML
	private Label specialExample;
	
	@FXML
	private Label mixedExample;
	
	@FXML
	private ComboBox<SeparationChars> specialCharC;
	
	@FXML
	private CheckBox removePrefixCB;
	
	@FXML
	private TextField prefixTF;
	
	@FXML
	private ComboBox<SeparationChars> postfixSepC;
	
	@FXML
	private ComboBox<WordSeparation> casingSepC;

	

	
	
    /**
     * The default constructor of the class.
     * The constructor is called before the initialize() method.
     */
	public WordSepPanelController() { 
		super();
	}
	
	
	
	
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	// Set Pascal
    	pascalRB.setText( WordSeparation.PASCAL_CASE.getName() );
    	pascalRB.setToggleGroup(group);
    	pascalExample.setText( WordSeparation.PASCAL_CASE.getExample() );
    	
    	// Set Camel
    	camelRB.setText( WordSeparation.CAMEL_CASE.getName() );
    	camelRB.setToggleGroup(group);
    	camelExample.setText( WordSeparation.CAMEL_CASE.getExample() );
    	
    	// Set special
    	specialRB.setText( WordSeparation.SPECIAL_CHARACTER.getName() );
    	specialRB.setToggleGroup(group);
    	specialExample.setText( WordSeparation.SPECIAL_CHARACTER.getExample() );

	    	// Set special Combo
	    	specialCharC.getItems().clear();
	    	specialCharC.getItems().addAll( SeparationChars.getAll() );
	    	specialCharC.setValue(SeparationChars.DASH);
	    	specialCharC.setDisable( true );
    	
    	
    	// Set Mixed
    	mixedRB.setText( WordSeparation.PREFIX_CASING.getName() );
    	mixedRB.setToggleGroup(group);
    	mixedExample.setText( WordSeparation.PREFIX_CASING.getExample() );
    	    	
	    	// Set Mixed Special Combo
	    	postfixSepC.getItems().clear();
	    	postfixSepC.getItems().addAll( SeparationChars.getAll() );
	    	postfixSepC.setValue(SeparationChars.DASH);
	    	postfixSepC.setDisable( true );
	    	    	
	    	// Set Casing Special Combo
	    	casingSepC.getItems().clear();
	    	casingSepC.getItems().addAll( WordSeparation.getAll() );
	    	casingSepC.setValue(WordSeparation.PASCAL_CASE);
	    	casingSepC.setDisable( true );
	    	
	    	// Other Mixed Combo
	    	removePrefixCB.setDisable( true );
	    	prefixTF.setDisable( true );
    }
	
	
    
    
    /**
     * A method that acts like an action listener for the pascal radio button.
     * It enables/disables the other components.
     */
    @FXML
    public void pascalSelected() {
    	// If this button es selected
    	if( pascalRB.isSelected() ) {
    		// Deactive the combo
    		specialCharC.setDisable( true );
    		
        	// Fields disabled if the button is picked
    		removePrefixCB.setDisable( true );
    		removePrefixCB.setSelected( false );
    		
    		casingSepC.setDisable( true );
        	postfixSepC.setDisable( true );
        	prefixTF.setDisable( true );
    	}

    }
	
	
    
    /**
     * A method that acts like an action listener for the camel radio button.
     * It enables/disables the other components.
     */
    @FXML
    public void camelSelected() {
    	// If this radio button is checked
    	if( camelRB.isSelected() ) {
    		// Deactive the combo
    		specialCharC.setDisable( true );
    		
        	// Fields disabled if the button is picked
    		removePrefixCB.setDisable( true );
    		removePrefixCB.setSelected( false );
    		
    		casingSepC.setDisable( true );
        	postfixSepC.setDisable( true );
        	prefixTF.setDisable( true );
    	}
    }
    
    
    
    
    
    /**
     * A method that acts like an action listener for the special radio button.
     * It enables/disables the other components.
     */
    @FXML
    public void specialSelected() {
    	// If this radio button is checked
    	if( specialRB.isSelected() ) {
    		
    		// Activate the combo
    		specialCharC.setDisable( false );
    		
        	// Fields disabled if the button is picked
    		removePrefixCB.setDisable( true );
    		removePrefixCB.setSelected( false );
    		
    		casingSepC.setDisable( true );
        	postfixSepC.setDisable( true );
        	prefixTF.setDisable( true );
    	}
    }
    
    
    
    
    /**
     * A method that acts like an action listener for the mixed radio button.
     * It enables/disables the other components.
     */
    @FXML
    public void mixedSelected() {
    	// If this radio button is checked
    	if( mixedRB.isSelected() ) {
    		// Deactive the combo
    		specialCharC.setDisable( true );
    		
        	// Fields enabled if the button is picked
    		removePrefixCB.setDisable( false );
    		casingSepC.setDisable( false );
    		postfixSepC.setDisable( false );
    	}
    }
    
    
    
    
    /**
     * A method that acts like an action listener for the remove check.
     * It enables/disables the other components.
     */
    @FXML
    public void removeChecked() {
       	// The field is enabled if the button is selected
    	prefixTF.setDisable( !removePrefixCB.isSelected() );
    	
    	// Check the separation
    	if( !casingSepC.getValue().toString().equals( WordSeparation.SPECIAL_CHARACTER.getName() ) )
    		postfixSepC.setDisable( removePrefixCB.isSelected() );
    }
    
    
    
    /**
     * A method that acts like an action listener for the postfix separation check.
     * It enables/disables other components.
     */
     @FXML
     public void changeSeparation() {
    	 // Check the selected
    	 postfixSepC.setDisable( !casingSepC.getValue().toString().equals( WordSeparation.SPECIAL_CHARACTER.getName()) );
     }
    
    
    
    
    
    
    @FXML
    public void pressNext() {
		// Get the instance of separation
		Separation sep = Separation.getInstance();
		
		// Now, if the main type is pascal...
		if( pascalRB.isSelected() ) {
			sep.setType( WordSeparation.PASCAL_CASE );
		}
		// If the main type is camel...
		else if( camelRB.isSelected() ) {
			sep.setType( WordSeparation.CAMEL_CASE );
		}
		// If the main type is character
		else if( specialRB.isSelected() ) {
			// Set the type
			sep.setType( WordSeparation.SPECIAL_CHARACTER );
			
			// And set the character
			sep.setCharacters( SeparationChars.getLabel( specialCharC.getValue().toString() ) );
		}
		// And finally, if it is mixed
		else if( mixedRB.isSelected() ) {
			// Set the type
			sep.setType( WordSeparation.PREFIX_CASING );
			
			// Now if we have a remove prefix
			if( removePrefixCB.isSelected() ) {
				// Set the boolean
				sep.setDeletePrefix( true );
				
				// Set the prefix
				sep.setPrefix( prefixTF.getText() );
			}
			// If we don't have the remove
			else {
				// Set the boolean to false
				sep.setDeletePrefix( false );
				
				// Set an empty string at prefix
				sep.setPrefix("");
			}
			
			// Set the complementary prefix
			sep.setExtraSeparation( WordSeparation.getLabel( casingSepC.getValue().toString() ) );
			
			// And the extra prefix
			sep.setExtraChars( SeparationChars.getLabel( postfixSepC.getValue().toString() ) );
		}
		
		// Change the panel
		mainApp.changePanel( FileLocation.PROGRESS_PANEL );
    }
    
    
    
    
    
}
