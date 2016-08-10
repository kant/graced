package gui.controller;

import gui.view.model.BarChartModel;
import gui.view.model.MouseExitAnimation;
import gui.view.model.MouseHoverAnimation;
import gui.view.model.NameTableModel;
import gui.view.model.WordTableModel;

import java.text.DecimalFormat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import xmlfiles.ResultsGenerator;
import core.GracedPropagator;
import database.objects.AnsiClass;




/**
 * Controller class for the charts panel ChartsPanel.fxml with all the results of the execution
 * of the agent. It initializes all the mandatory listeners.
 * @author Ing. Melina C. Vidoni - 2014
 *
 */
public class ChartsPanelController extends Controller {
	@FXML private PieChart mainPie;
	@FXML private TableView<NameTableModel> tableMainPie;
	@FXML private TableColumn<NameTableModel, String> tableMPColumn;
	@FXML private Label titleMainLabel;
	@FXML private Label descMainLabel;
	
	@FXML private TreeView<String> treeview;
	@FXML private BarChart<String, Number> categoryBC;
	@FXML private CategoryAxis xAxis;
	@FXML private NumberAxis yAxis;
	
	@FXML private PieChart typesPie;
	@FXML private TreeView<String> typesTreeview;
	@FXML private Label titleTypeLabel;
	@FXML private Label descTypeLabel;
	
	@FXML private TreeView<String> graphTreeView;
	@FXML private AreaChart<String, Number> areaChart;
	
	@FXML private Label totalTableProportion;
	@FXML private Label flatTableQty;
	@FXML private Label showMinPercent;
	@FXML private Label showMaxPercent;
	@FXML private Label showAveragePercent;
	@FXML private Label categoryName;
	
	@FXML private BarChart<String, Number> hyphenationBC;
	@FXML private TableView<WordTableModel> hyphenationTW;
	@FXML private TableColumn<WordTableModel, String> wordHyphColumn;
	@FXML private TableColumn<WordTableModel, Integer> countHyphColumn;
	@FXML private Label hyphenationTitle;
	@FXML private Label hyphenationDescription;

	

	
    /**
     * The default constructor of the class.
     * The constructor is called before the initialize() method.
     */
	public ChartsPanelController() { }
	
	
	
	
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() { 
    	
    	/*
    	 * PREPARE MAIN PIE
    	 */
    	configMainPie();
    	
    	
    	/*
    	 * CONFIG BAR CHART
    	 */
    	configBarChart();
    	
    	
    	/*
    	 * CONFIG TYPES PIE
    	 */
    	configTypesPie();
    	
    	
    	/*
    	 * CONFIG INFO DISTRIBUTION
    	 */
    	configInfoDistribution();
    	
    	
    	/*
    	 * CONFIG HYPHENATION INFO
    	 */
    	configHyphenationInfo();
    }



    

	/**
     * A method for configuring the types pie chart, and adding the click listener
     * to each of the slices, that will allow to change from one tree to another.
     * Also sets the animation on the slides, and the labels with the descriptions
     * of the types.
     */
    private void configTypesPie() {
    	// Fixed descriptions
    	final String trickyDesc = "This are categorizations where the belonging of the table's name is greater than "
    			+ "the obtained with the column's names. This happens in cases where the table's name has really "
    			+ "specific words for a certain category, but the columns uses generics words with medium or low"
    			+ " weights.\nThis categorizations aren't deprecated because despise of the obtained belonging, "
    			+ "the table may contain significant information.";
    	
    	final String trueDesc = "This type represents all of those categorizations where both belongings are high, and "
    			+ "the column belonging is higher than 30%, because both the table and the columns contains really"
    			+ " significative words, that have an important weight on the bag of word."
    			+ "\nUsually, a very few tables per category belong to this type.";
    	
    	final String neutralDesc = "This are categorizations that doesn't belong to any of the other two types, where both"
    			+ " belongings are in ones. Usually, this tables contains information that complements the data found"
    			+ " on the tables of the True type.";
    	
    	
    	
    	
    	// Config the pie chart
    	typesPie.setTitle("Types of Classification");
    	typesPie.setData( ResultsGenerator.getTypesPie() );
    	
    	// Default
    	typesTreeview.setRoot( ResultsGenerator.getTypesTreeRoot("Neutral") );
    	titleTypeLabel.setText("Type: Neutral");
    	descTypeLabel.setText(neutralDesc);

    	
    	
    	// Add listeners
    	for (final PieChart.Data data : typesPie.getData()) {
    		// Add animation
    		data.getNode().setOnMouseEntered(new MouseHoverAnimation(data, typesPie));
    		data.getNode().setOnMouseExited(new MouseExitAnimation());
    		
    		
    		// Add listener for the click
    	    data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
    	            @Override 
    	            public void handle(MouseEvent e) {   	      
    	            	// Clear the tree
    	            	typesTreeview.setRoot( ResultsGenerator.getTypesTreeRoot(data.getName()) );
    	            	
    	            	// Set the text
    	            	String type = data.getName();
    	            	
    	            	System.out.println( type + " " + data.getPieValue() );
    	            	
    	            	titleTypeLabel.setText( "Type: " + type );
    	            	
    	            	// Evaluate the name
    	            	if( type.equals("Tricky") ) {
    	            		descTypeLabel.setText( trickyDesc );
    	            	}
    	            	else if( type.equals("True") ) {
    	            		descTypeLabel.setText( trueDesc );
    	            	}
    	            	else if( type.equals("Neutral") ) {
    	            		descTypeLabel.setText( neutralDesc );
    	            	}
    	             }
    	    });
    	}
	}




	/**
     * A method for configuring the treeview and the barcharts regarding belonging percentages
     * on each of the categories found. It adds the listener for each of the tree nodes.
     */
    @SuppressWarnings("deprecation")
	private void configBarChart() {
    	// Generate the tree
		treeview.setRoot( ResultsGenerator.getTreeRoot() );
		treeview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		// Add the listener
		treeview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {

				@SuppressWarnings("unchecked")
				@Override
				public void changed(ObservableValue<? extends TreeItem<String>> observable,
											TreeItem<String> oldValue, TreeItem<String> newValue) {

					// If user clicked on a table
	                if( TreeView.getNodeLevel(newValue) == 1 ) {
	                	// Clean the chart
	                	categoryBC.getData().clear();
	                	
	                	// Set the title of the barchart
	                	categoryBC.setTitle("Table: " + newValue.getValue());
	                	
	                	// Get the model
	                	BarChartModel model = ResultsGenerator.getModelFor( newValue.getValue() );
	                	categoryBC.getData().addAll( model.getTableSeries(), model.getColumnSeries() , model.getFinalSeries() );
	                	
	                }
	                	
				}
	            
	            
	        });
		
		
		// Bar chart
		yAxis.setLabel("Belonging %");
		yAxis.setAutoRanging(false);
		yAxis.setUpperBound(100.0);
		yAxis.setTickUnit(10.0);
		
		
		xAxis.setLabel("Categories");
	}




	/**
     * Method that configures the main pie of the panel.
     */
	private void configMainPie() {
    	// Prepare the descriptions
    	final String descCat = "This type of tables counts all the tables that has been categorized in at least"
				+ " one of the available classes generated using the standard ANSI/ISA-95. The tables contained"
				+ " in this type are tables that most likely contain manufacturing information.";
    	final String descUncat = "This type of tables counts all the tables that hasn't been able to get a category"
				+ " from the classes availables, due to getting low belonging percentage, or low matching of words."
				+ " This tables are most likely to no contain any manufacturing information at all.";
    	
    	
    	// Set first pie
    	mainPie.setTitle("Uncategorized vs Categorized");
    	mainPie.setData( ResultsGenerator.getInfoMainPC() );
    	tableMPColumn.setCellValueFactory(new PropertyValueFactory<NameTableModel, String>("name"));
    	tableMainPie.setPlaceholder(new Label("< Click Pie for Information >"));
    	
    	// Load info
    	tableMainPie.setItems( ResultsGenerator.getNamelist("Categorized") );
    	titleMainLabel.setText("Categorized");
    	descMainLabel.setText( descCat );
    	
    	
    	// Add listeners
    	for (final PieChart.Data data : mainPie.getData()) {
    		// Add animation
    		data.getNode().setOnMouseEntered(new MouseHoverAnimation(data,mainPie));
    		data.getNode().setOnMouseExited(new MouseExitAnimation());
    		
    		// Add listener for the click
    	    data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
    	            @Override 
    	            public void handle(MouseEvent e) {   	      
    	            	
    	            	
    	            	// Add the items
    	            	tableMainPie.setItems( ResultsGenerator.getNamelist(data.getName())  );
    	            	
    	            	// Change the description
    	            	titleMainLabel.setText( data.getName() );
    	            	if( data.getName().equals("Categorized") ) {
    	            		descMainLabel.setText( descCat );
    	            	}
    	            	else {
    	            		descMainLabel.setText( descUncat );
    	            	}
    	             }
    	    });
    	}
	}
	
	
  
	
	/**
	 * Method that configures the tab with the data about information distribution, that results
	 * of the propagation of proportions, during the third phase of the agent.
	 */
	private void configInfoDistribution() {
    	// Generate the tree
    	graphTreeView.setRoot( ResultsGenerator.getGraphRoot() );
    	graphTreeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		// Add the listener
    	graphTreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {

				@Override
				public void changed(ObservableValue<? extends TreeItem<String>> observable,
											TreeItem<String> oldValue, TreeItem<String> newValue) {
					// If this isn't a table node... and it isn't null...
					if( newValue != null && newValue.getValue().matches("[a-zA-Z\\s']+") ) {
						// Get the format
						DecimalFormat df = new DecimalFormat("0.0000");
		                	
		                // Get the class
		                AnsiClass ac = GracedPropagator.getAnsiByName( newValue.getValue().toString() );
		                	
		                // Set the values
		                totalTableProportion.setText(String.valueOf( df.format(ac.getObtainedProportion()) ));
		                flatTableQty.setText(String.valueOf(ac.getFlatTableQty()));
		                showMinPercent.setText(String.valueOf( df.format(ac.getMinPercent()) ) + " %");
		                showMaxPercent.setText(String.valueOf( df.format(ac.getMaxPercent()) ) + " %");
		                showAveragePercent.setText(String.valueOf( df.format(ac.getAveragePercent()) ) + " %");
		                categoryName.setText("Category: " + ac.getName());
					}
				}
	            
	            
	        });
		
    	
    	// Set a selected ittem
    	graphTreeView.getSelectionModel().select( graphTreeView.getRoot() );
    	
    	// Generate the area
    	areaChart.setTitle("Manufacturing Information Tables Distribution");
    	areaChart.getData().addAll( ResultsGenerator.getAreaGraphData() );	   
	}

    
    
	
	
	/**
	 * A method for configuring the hyphenation information with the barchart, the tableview
	 * with corresponding listeners on the barchart, and the information labels.
	 */
	private void configHyphenationInfo() {
		// Get the format
		DecimalFormat df = new DecimalFormat("00.000");
		
		// Add the explanation
		hyphenationTitle.setText("Word Separation Estimated Correction (" 
						+ df.format(ResultsGenerator.getEstHyphPercent()) + ")");
		hyphenationDescription.setText("The evaluation shown in this stadistics are just an estimated"
				+ " analysis of words made by studying their length after separation the names of tables"
				+ " and columns by the proposed separation method. The length used was obtained from"
				+ " (Sigurd, Eeg-Olofson & Weiger, 2004)."
				+ "\n\nPlease take in mind that this is just an estimation based on a tested word length"
				+ " for the english language that showed acceptable results after several testings.");
		
		// Set title for the graph
		hyphenationBC.setTitle("Estimated Hyphenation Correction");
		
		// Load the chart
		hyphenationBC.getData().addAll( ResultsGenerator.getHyphChartData() );
		
		// Now we need to load the table
		wordHyphColumn.setCellValueFactory(new PropertyValueFactory<WordTableModel, String>("word"));
		countHyphColumn.setCellValueFactory(new PropertyValueFactory<WordTableModel, Integer>("count"));
		hyphenationTW.setPlaceholder(new Label("< Click Bars for Information >"));
		
		// Add the listener, for each series...
        for(BarChart.Series<String, Number> serie: hyphenationBC.getData()) {
        	// For each bar, of each series...
            for (BarChart.Data<String, Number> item: serie.getData()){
            	// Set a mouse listener
                item.getNode().setOnMousePressed((MouseEvent event) -> {
                	// Prepare the name
                	String name = item.toString().split(",")[0].substring(5) + " " + serie.getName();

                	// Now set it
                	hyphenationTW.setItems( ResultsGenerator.getWordsList(name) );
                   
                	System.out.println(item.toString() + " " + serie.getName());
                    
                });
            }
        }
		
	}
    
    
    
    
    
    

}
