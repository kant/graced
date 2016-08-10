package gui.application;

import gui.controller.BaseInfoPanelController;
import gui.controller.Controller;
import gui.controller.IntroPanelController;
import gui.controller.ProgressPanelController;
import gui.controller.WordSepPanelController;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;




/**
 * Launcher and main stage of the application.
 * @author Ing. Melina C. Vidoni - 2014
 *
 */
public class MainApp extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private Controller controller;

    
    
    
    /**
     * Method that starts the stage and initializes it.
     */
    @Override
    public void start(Stage stage) {
   	
    	// Config the main Stage
        this.primaryStage = stage;
        this.primaryStage.setTitle("Gr.A.C.E.D.");
        this.primaryStage.setResizable(false);
        this.primaryStage.getIcons().add(new Image( FileLocation.LOGO_ICON.toString() ));
        setUserAgentStylesheet(getClass().getResource("/gui/view/Style.css").toExternalForm());
        

        try {
            // Load the root layout from the fxml file
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource( FileLocation.ROOT_LAYOUT.toString() ));
            

            rootLayout = (BorderPane) loader.load();
            Scene scene = new Scene(rootLayout);
            
            // Set the scene
            stage.setScene(scene);
            stage.show();
            
            
            // Change the panel
            changePanel( FileLocation.INTRO_PANEL );
            
            
        } 
        catch (IOException e) {
            // TODO Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
        }

       
    }

    
    
    
    
    /**
     * Getter for the primary stage.
     * @return The primary stage.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    
    
    
    
    
    /**
     * Changes the panel on the main stage.
     */
    public void changePanel(FileLocation p) {
        try {
            // Load the fxml file and set into the center of the main layout
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(p.toString()));
           	loader.setRoot(new GridPane());
            GridPane overviewPage = (GridPane) loader.load();
            
            rootLayout.setCenter(overviewPage);
  
            
            /*
             *  Give the controller access to the main app
             */
            // If it is IntroPanel
            if( p.equals( FileLocation.INTRO_PANEL ) ) {
            	controller = (IntroPanelController) loader.getController();           
                controller.setMainApp(this);
            }
            // If it is BaseInfoPanel
            else if( p.equals( FileLocation.BASE_INFO_PANEL ) ) {
            	controller = (BaseInfoPanelController) loader.getController();           
                controller.setMainApp(this);
            }
            // If it is WordSepPanel
            else if( p.equals( FileLocation.WORD_SEPARATION_PANEL ) ) {
            	controller = (WordSepPanelController) loader.getController();           
                controller.setMainApp(this);
            }
            // If it is ProgressPanel
            else if( p.equals( FileLocation.PROGRESS_PANEL ) ) {
            	controller = (ProgressPanelController) loader.getController();           
                controller.setMainApp(this);
            }
            // If it is ChartsPanel
            else if( p.equals( FileLocation.CHARTS_PANEL ) ) {
            	controller = (Controller) loader.getController();           
                controller.setMainApp(this);
            }

                   
        } 
        catch (IOException e) {
            // TODO Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
        }
    }

    
    
    
    
    /**
     * Default method that launchs the app.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}