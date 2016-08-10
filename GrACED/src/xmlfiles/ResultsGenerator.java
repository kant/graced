package xmlfiles;

import gui.view.model.BarChartModel;
import gui.view.model.NameTableModel;
import gui.view.model.WordTableModel;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import xmlfiles.labels.CatTablesLabels;
import xmlfiles.labels.GraphLabels;
import xmlfiles.labels.HyphenationLabels;
import xmlfiles.labels.UncatTablesLabels;
import database.ErpDB;
import database.objects.AnsiClass;
import database.objects.CategorizedTable;





/**
 * A singleton class that has all the main method to generate the graphic results and
 * store it on static variables that can be later be accesed without getting to the
 * instance of the class.
 * @author Ing. Melina C. Vidoni - 2014
 *
 */
public class ResultsGenerator {
	private static String UNCLASS_FILE = "files/results/UnclassifiedTables_" + ErpDB.getInstance().getName() + ".xml";
	private static String CLASS_FILE = "files/results/ClassifiedTables_" + ErpDB.getInstance().getName() + ".xml";	
	private static String HYPHENATION_FILE = "files/results/Hyphenation_" + ErpDB.getInstance().getName() + ".xml";
	private static String GRAPH_FILE = "files/Graph.xml";
	private static ResultsGenerator instance = new ResultsGenerator();

	private static  ObservableList<PieChart.Data> infoMainPC;
	private int totalTables;
	private static ObservableList<NameTableModel> uncatTables;
	private static ObservableList<NameTableModel> catTables;
	
	private static TreeItem<String> rootItem;
	private static LinkedList<BarChartModel> barchartInfo;
	
	private static ObservableList<PieChart.Data> infoTypesPC;
	private static TreeItem<String> rootTypeTricky;
	private static TreeItem<String> rootTypeTrue;
	private static TreeItem<String> rootTypeNeutral;
	private static int totalClassification;
	
	private static TreeItem<String> graphRootItem;
	private static ObservableList<XYChart.Series<String, Number>> areaChartSeries;
	
	private static ObservableList<BarChart.Series<String, Number>> hyphenationChartSeries;
	private static double estimatedHyphenationPercent;
	private static ObservableList<WordTableModel> goodTableWords;
	private static ObservableList<WordTableModel> goodColumnWords;
	private static ObservableList<WordTableModel> badTableWords;
	private static ObservableList<WordTableModel> badColumnWords;
	
	
	
	
	
	/**
	 * Default constructor of the class.
	 */
	private ResultsGenerator() {
		// Initialize vars
		totalTables = 0;
		totalClassification = 0;
		estimatedHyphenationPercent = 0;
		
		// Open lists
		uncatTables = FXCollections.observableArrayList();
		catTables = FXCollections.observableArrayList();
		barchartInfo = new LinkedList<BarChartModel>();
		areaChartSeries = FXCollections.observableArrayList();
		hyphenationChartSeries = FXCollections.observableArrayList();
		
		goodTableWords = FXCollections.observableArrayList();
		goodColumnWords = FXCollections.observableArrayList();
		badTableWords = FXCollections.observableArrayList();
		badColumnWords = FXCollections.observableArrayList();
	}
	
	
	
	
	
	/**
	 * Getter for the instance, given that this is a singleton method.
	 * @return the only instance of the clas.
	 */
	public static ResultsGenerator getInstance() {
		return instance;
	}
	
	
	
	

	/**
	 * A method for generating the pie chart with all the types information. Also saves and classifies
	 * the tree items that will show when a pie is clicked.
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public void generateTypesPie() throws SAXException, IOException, ParserConfigurationException {
		// Add the first node to each root
		rootTypeTricky = new TreeItem<String> ("Tricky Classifications",  new ImageView(new Image("/gui/view/img/tree-root-tricky.png")) );
		rootTypeTricky.setExpanded(true);
		
		rootTypeTrue = new TreeItem<String> ("True Classifications",  new ImageView(new Image("/gui/view/img/tree-root-true.png")) );
		rootTypeTrue.setExpanded(true);

		rootTypeNeutral = new TreeItem<String> ("Neutral Classifications",  new ImageView(new Image("/gui/view/img/tree-root-neutral.png")) );
		rootTypeNeutral.setExpanded(true);

		
		// Prepare vars to save percents
		int trickyQty = 0;
		int trueQty = 0;
		@SuppressWarnings("unused")
		int neutralQty = 0;
        
		
	
		/*
		 * READ THE FILE AND MAKE THE TREES
		 */
		// Reference the file
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		Document synDoc = dbFactory.newDocumentBuilder().parse( new File( CLASS_FILE ) );
						 
		// Normalize it
		synDoc.getDocumentElement().normalize();
						 
		// Now get the nodes
		NodeList nodes = synDoc.getElementsByTagName( CatTablesLabels.TABLE.getLabel() );
						 
		// For all the nodes...
		for (int i = 0; i < nodes.getLength(); i++) {
			// Get the current one
			Node node = nodes.item(i);
			 
			// If it is a node
			if ( node.getNodeType() == Node.ELEMENT_NODE ) {
				// Get the element
				Element element = (Element) node;
				  
				// Separate this name
				String tname = element.getAttribute( CatTablesLabels.TABLE_NAME.getLabel() );
								
				// Get the column
				NodeList catNodes = element.getElementsByTagName( CatTablesLabels.CLASS.getLabel() );
				
				// For all the nodes...
				for(int j=0; j<catNodes.getLength(); j++) {
					 // Get the current one
					 Node cnode = catNodes.item(j);
					 
					 // If it is a node
					 if ( cnode.getNodeType() == Node.ELEMENT_NODE ) {
						 // Get the element
						 Element celement = (Element) cnode;
						 
						 // Get the class name and add it to the total
						 String className = celement.getAttribute( CatTablesLabels.CLASS_NAME.getLabel() );
						 totalClassification++;
						 
						 // Get the table classification
						 Node tabNode = celement.getElementsByTagName( CatTablesLabels.TABLE_CLASS.getLabel() ).item(0);
						 Element tabElement = (Element) tabNode;
						 double tabPercent = Double.valueOf( tabElement.getAttribute( CatTablesLabels.CLASS_PERCENT.getLabel()) ); 
						 
						 // Get the column classification
						 Node colNode = celement.getElementsByTagName( CatTablesLabels.COLUMN_CLASS.getLabel() ).item(0);
						 Element colElement = (Element) colNode;
						 double colPercent = Double.valueOf( colElement.getAttribute( CatTablesLabels.CLASS_PERCENT.getLabel() ) );
						 
						 /*
						  * NOW CHECK THE BELONGING
						  */
						 // If column % is less than table %...
						 if( (tabPercent <= 20.0) && (colPercent < 7.0) && (colPercent <= tabPercent) ) {
							 /*
							  * TRICKY CLASS
							  */
							 trickyQty++;
							 
							 // Add the information to the tree
							 addTypeTreeItem( tname, className, 'T' );
								
						 }
						 // If col % are greater than (original 50)...
						 else if( colPercent >= 40.0 || tabPercent >= 40.0) {
							 /*
							  * TRUE CLASS
							  */
							 trueQty++;
							 
							 // Save to the list
							 addTypeTreeItem( tname, className, 'V' );	
						 }
						 // In the other hand
						 else {
							 /*
							  * NEUTRAL CLASS
							  */
							 neutralQty++;
							 
							 // Save to the list
							 addTypeTreeItem( tname, className, 'N' );
						 }
					 }
				 } // Category Loop
				
			 }
		} // For each node
        
		
		
		double percentTricky = ( ((double) trickyQty ) * 100 ) / ((double) totalClassification);
		double percentTrue = ( ((double) trueQty ) * 100 ) / ((double) totalClassification);


		// Generate the data
		infoTypesPC = FXCollections.observableArrayList(
                new PieChart.Data("Tricky", percentTricky ),
                new PieChart.Data("Neutral", (100 - percentTricky - percentTrue) ),
                new PieChart.Data("True", percentTrue) );
	}
	
	
	
	
	/**
	 * A method for adding a treeitem with a subitem in a given type. If the treeitem
	 * already exists, it just simply add the subitem.
	 * @param tname The table treeitem to be searched for.
	 * @param className The class subtreeitem to be added.
	 * @param c The type of tree where the logic searches.
	 */
	private void addTypeTreeItem(String tname, String className, char c) {
		// Prepare a flag
		boolean added = false;
		
		// Regarding each type
		switch( c ) {
		case 'T': // TRICKY CASE
				  
				  // Look in the tree
				  for( TreeItem<String> item : rootTypeTricky.getChildren() ) {
					  // If this item has the name
					  if( item.getValue().equals(tname) ) {
						  // Add the category
						  TreeItem<String> categoryNode = new TreeItem<String>( className, new ImageView(new Image("/gui/view/img/tree-category.png")) );
						  item.getChildren().add(categoryNode);
						  
						  // Set to true
						  added = true;
						  break;
					  }
				  }
				  
				  // Check if it wasn't added
				  if( !added ) {
						 // Create the table node
						 TreeItem<String> tableNode = new TreeItem<String>( tname, new ImageView(new Image("/gui/view/img/tree-table.png")) );
						 TreeItem<String> categoryNode = new TreeItem<String>( className, new ImageView(new Image("/gui/view/img/tree-category.png")) );
						 
						 // Add it to the root
						 tableNode.getChildren().add( categoryNode );
						 rootTypeTricky.getChildren().add( tableNode );
				  }
				  break;
			
		case 'V': // TRUE CASE
			  
				  // Look in the tree
				  for( TreeItem<String> item : rootTypeTrue.getChildren() ) {
					  // If this item has the name
					  if( item.getValue().equals(tname) ) {
						  // Add the category
						  TreeItem<String> categoryNode = new TreeItem<String>( className, new ImageView(new Image("/gui/view/img/tree-category.png")) );
						  item.getChildren().add(categoryNode);
						  
						  // Set to true
						  added = true;
						  break;
					  }
				  }
				  
				  // Check if it wasn't added
				  if( !added ) {
						 // Create the table node
						 TreeItem<String> tableNode = new TreeItem<String>( tname, new ImageView(new Image("/gui/view/img/tree-table.png")) );
						 TreeItem<String> categoryNode = new TreeItem<String>( className, new ImageView(new Image("/gui/view/img/tree-category.png")) );
						 
						 // Add it to the root
						 tableNode.getChildren().add( categoryNode );
						 rootTypeTrue.getChildren().add( tableNode );
				  }
				  break;
			
		case 'N': // NEUTRAL CASE
			
				  // Look in the tree
				  for( TreeItem<String> item : rootTypeNeutral.getChildren() ) {
					  // If this item has the name
					  if( item.getValue().equals(tname) ) {
						  // Add the category
						  TreeItem<String> categoryNode = new TreeItem<String>( className, new ImageView(new Image("/gui/view/img/tree-category.png")) );
						  item.getChildren().add(categoryNode);
						  
						  // Set to true
						  added = true;
						  break;
					  }
				  }
				  
				  // Check if it wasn't added
				  if( !added ) {
						 // Create the table node
						 TreeItem<String> tableNode = new TreeItem<String>( tname, new ImageView(new Image("/gui/view/img/tree-table.png")) );
						 TreeItem<String> categoryNode = new TreeItem<String>( className, new ImageView(new Image("/gui/view/img/tree-category.png")) );
						 
						 // Add it to the root
						 tableNode.getChildren().add( categoryNode );
						 rootTypeNeutral.getChildren().add( tableNode );
				  }
				  break;
		}
	}



	/**
	 * Generates the models for the barcharts of all the tables.
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public void generateBarCharts() throws SAXException, IOException, ParserConfigurationException {
		// Reference the file
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		Document synDoc = dbFactory.newDocumentBuilder().parse( new File( CLASS_FILE ) );
				 
		// Normalize it
		synDoc.getDocumentElement().normalize();
				 
		// Now get the nodes
		NodeList nodes = synDoc.getElementsByTagName( CatTablesLabels.TABLE.getLabel() );
				 
		 // For all the nodes...
		 for (int i = 0; i < nodes.getLength(); i++) {
			 // Get the current one
			 Node node = nodes.item(i);
			 
			 // If it is a node
			 if ( node.getNodeType() == Node.ELEMENT_NODE ) {
				// Get the element
				Element element = (Element) node;
				  
				// Separate this name
				String name = element.getAttribute( CatTablesLabels.TABLE_NAME.getLabel() );
				
				// Create a new model
				BarChartModel newModel = new BarChartModel(name);
				
				// Get the column
				NodeList catNodes = element.getElementsByTagName( CatTablesLabels.CLASS.getLabel() );
				
				// For all the nodes...
				for(int j=0; j<catNodes.getLength(); j++) {
					 // Get the current one
					 Node cnode = catNodes.item(j);
					 
					 // If it is a node
					 if ( cnode.getNodeType() == Node.ELEMENT_NODE ) {
						 // Get the element
						 Element celement = (Element) cnode;
						 
						 // Get the class name
						 String className = celement.getAttribute( CatTablesLabels.CLASS_NAME.getLabel() );
						 String classPercent = celement.getAttribute( CatTablesLabels.CLASS_PERCENT.getLabel() );
						 
						 // Get the table classification
						 Node tabNode = celement.getElementsByTagName( CatTablesLabels.TABLE_CLASS.getLabel() ).item(0);
						 Element tabElement = (Element) tabNode;
						 newModel.addInfoTableSeries(className, Double.valueOf( tabElement.getAttribute( CatTablesLabels.CLASS_PERCENT.getLabel())) );
						 
						 // Get the column classification
						 Node colNode = celement.getElementsByTagName( CatTablesLabels.COLUMN_CLASS.getLabel() ).item(0);
						 Element colElement = (Element) colNode;
						 newModel.addInfoColumnSeries(className, Double.valueOf( colElement.getAttribute( CatTablesLabels.CLASS_PERCENT.getLabel() ) ));
						 
						 // Add the final classification
						 newModel.addInfoFinalSeries(className, Double.valueOf(classPercent) );
					 }
				 }
				
				// Add the model
				barchartInfo.add(newModel);
			
			 }
		 }
		
		
	}
	
	
	
	
	
	
	/**
	 * A method to get the model for the barchart of an specific table.
	 * @param table The table to search for.
	 * @return The model to get.
	 */
	public static BarChartModel getModelFor(String table) {
		// Go through the list
		for( BarChartModel model : barchartInfo ) {
			// Compare the name
			if( table.equals( model.getTable() ) ) {
				// Then return
				return model;
			}
		}
		
		// Return nothing
		return null;
	}
	
	
	
	
	
	/**
	 * Method that generates the tree views and already sets up the format, so that the panel will
	 * only need to retrieve it and show it.
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public void generateTreeView() throws SAXException, IOException, ParserConfigurationException {
		// Icons
		javafx.scene.Node rootIcon = new ImageView(new Image("/gui/view/img/tree-root.png"));
		
		// Create the base for the tree
		rootItem = new TreeItem<String> ("Tables", rootIcon);
        rootItem.setExpanded(true);
        
        // Now get a list
        LinkedList<TreeItem<String>> nodesList = getTreeNodes();
        
        // Now add it
        for( TreeItem<String> node : nodesList ) {
        	rootItem.getChildren().add(node);
        	node.setExpanded(false);
        }
       
	}
	
	
	
	
	
	/**
	 * A method to get the tree nodes for the treeview on the view for the barchart.
	 * @return The list of all the nodes to add to the root.
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	private LinkedList<TreeItem<String>> getTreeNodes() throws SAXException, IOException, ParserConfigurationException {
		// Create the list
		LinkedList<TreeItem<String>> nodesList = new LinkedList<TreeItem<String>>();
		
		// Reference the file
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		Document synDoc = dbFactory.newDocumentBuilder().parse( new File( CLASS_FILE ) );
						 
		// Normalize it
		synDoc.getDocumentElement().normalize();
						 
		// Now get the nodes
		NodeList nodes = synDoc.getElementsByTagName( CatTablesLabels.TABLE.getLabel() );
						 
		 // For all the nodes...
		 for (int i = 0; i < nodes.getLength(); i++) {
			 // Get the current one
			 Node node = nodes.item(i);
			 
			 // If it is a node
			 if ( node.getNodeType() == Node.ELEMENT_NODE ) {
				// Get the element
				Element element = (Element) node;
				
				// Create the image
				javafx.scene.Node tableIcon = new ImageView(new Image("/gui/view/img/tree-table.png"));
				
				// Create the tablenode
				TreeItem<String> tableItem = new TreeItem<String>
							( element.getAttribute( CatTablesLabels.TABLE_NAME.getLabel() ), tableIcon );
				  
				// Get the column
				NodeList colNodes = element.getElementsByTagName( CatTablesLabels.COLUMN.getLabel() );

				
				// For all the nodes...
				for(int j=0; j<colNodes.getLength(); j++) {
					 // Get the current one
					 Node cnode = colNodes.item(j);
					 
					 // If it is a node
					 if ( cnode.getNodeType() == Node.ELEMENT_NODE ) {
						 
						// Get the element
						Element celement = (Element) cnode;
						
						javafx.scene.Node columnIcon = new ImageView(new Image("/gui/view/img/tree-column.png"));
										 
						 // Create an item
						 TreeItem<String> colItem = new TreeItem<String>
						 	 	( celement.getAttribute( CatTablesLabels.COLUMN_NAME.getLabel() ), columnIcon );
						 
						 // Add it
						 tableItem.getChildren().add(colItem);
						 
					 }
				 }
				
				 // Add the table
				 nodesList.add( tableItem );
				 
			 }
		 }
		
				 
		// Return the list
		return nodesList;	
		
	}



	/**
	 * Method that generates the first mainpie with the information for
	 * cateogrized vs uncategorized tables.
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	@SuppressWarnings("unused")
	public void generateMainPie() throws SAXException, IOException, ParserConfigurationException {
		// Get the information
		int uncatQty = getUncatTableInfo();
		int catQty = getCatTableInfo();

		// Get %
		double percentUnQty = ( ((double)uncatQty) * 100) / ((double)totalTables);
		
		System.out.println("No categorizado: " + percentUnQty);
		
		// Generate the data
		infoMainPC = FXCollections.observableArrayList(
                new PieChart.Data("Uncategorized", percentUnQty ),
                new PieChart.Data("Categorized", (100 - percentUnQty) ) );
	}
	
	
	
	

	
	
	/**
	 * A method to get the info on the categorized tables of the database.
	 * @return the qty of categorized tables.
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public int getCatTableInfo() throws SAXException, IOException, ParserConfigurationException {
		// Subtotal
		int qty = 0;
		
		// Reference the file
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		Document synDoc = dbFactory.newDocumentBuilder().parse( new File( CLASS_FILE ) );
				 
		// Normalize it
		synDoc.getDocumentElement().normalize();
				 
		// Now get the nodes
		NodeList nodes = synDoc.getElementsByTagName( CatTablesLabels.TABLE.getLabel() );
				 
		 // For all the nodes...
		 for (int i = 0; i < nodes.getLength(); i++) {
			 // Get the current one
			 Node node = nodes.item(i);
			 
			 // If it is a node
			 if ( node.getNodeType() == Node.ELEMENT_NODE ) {
				// Get the element
				Element element = (Element) node;
				  
				// Separate this name
				String word = element.getAttribute( CatTablesLabels.TABLE_NAME.getLabel() );
				
				// Add the info
				catTables.add(  new NameTableModel(word) );
				qty++;
				totalTables++;
			 }
		 }
		 
		 // Gave results back
		 return qty;
	}

	
	
	
	
	
	
	/**
	 * A method to get the info on the uncategorized tables of the database.
	 * @return the qty of uncategorized tables.
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public int getUncatTableInfo() throws SAXException, IOException, ParserConfigurationException {
		// Subtotal
		int qty = 0;
		
		// Reference the file
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		Document synDoc = dbFactory.newDocumentBuilder().parse( new File( UNCLASS_FILE ) );
				 
		// Normalize it
		synDoc.getDocumentElement().normalize();
				 
		// Now get the nodes
		NodeList nodes = synDoc.getElementsByTagName( UncatTablesLabels.TABLE.getLabel() );
				 
		 // For all the nodes...
		 for (int i = 0; i < nodes.getLength(); i++) {
			 // Get the current one
			 Node node = nodes.item(i);
			 
			 // If it is a node
			 if ( node.getNodeType() == Node.ELEMENT_NODE ) {
				// Get the element
				Element element = (Element) node;
				  
				// Separate this name
				String word = element.getAttribute( UncatTablesLabels.TABLE_NAME.getLabel() );
				
				// Add the info
				uncatTables.add( new NameTableModel(word) );
				qty++;
				totalTables++;
			 }
		 }
		 
		 // Gave results back
		 return qty;
	}



	
	
	
	
	
	/**
	 * A method to get the data for the main piechart.
	 * @return the corresponding data.
	 */
	public static ObservableList<PieChart.Data> getInfoMainPC() {
		return infoMainPC;
	}


	
	

	/**
	 * Returns the correct list with the info, depending on the name received.
	 * @param n The name of the list to be returned.
	 * @return a list with the name of the tables in the n category.
	 */
	public static ObservableList<NameTableModel> getNamelist(String n) {
		if( n.equals("Categorized") )
			return catTables;
		else
			return uncatTables;
	}


	
	

	
	/**
	 * A method to get the data for the treeview.
	 * @return The root node for the treeview.
	 */
	public static TreeItem<String> getTreeRoot() {
		return rootItem;
	}




	
	
	/**
	 * A method to get the data for the types piechart.
	 * @return The series of the pie chart.
	 */
	public static ObservableList<Data> getTypesPie() {
		return infoTypesPC;
	}





	/**
	 * A method to get the root node for the tree on the
	 * types tab.
	 * @param t The type of the pie that was requested.
	 * @return The root node of the tree.
	 */
	public static TreeItem<String> getTypesTreeRoot(String t) {
		// If it is tricky...
		if( t.equals("Tricky") )
			return rootTypeTricky;
		
		// If it is true...
		else if( t.equals("True") )
			return rootTypeTrue;
		
		// If it is neutral...
		else if( t.equals("Neutral") )
			return rootTypeNeutral;
		
		// In other case, return null.
		return null;
	}

	
	
	
	/**
	 * A method to get the final number of total classifications.
	 * @return The total number of all classifications.
	 */
	public static int getTotalClassification() {
		return totalClassification;
	}


	
	
	/**
	 * Method to get the root node for the tree that shows
	 * the graph structure in tree form, with listeners on
	 * each node.
	 * @return The root of the treeview.
	 */
	public static TreeItem<String> getGraphRoot() {
		return graphRootItem;
	}



	/**
	 * Method that generates the infografics with the area chart, the graph tree view,
	 * and the listener for each tree node.
	 * @param ansiClass
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public void generateInfographics(LinkedList<AnsiClass> ansiClass) throws SAXException, IOException, ParserConfigurationException {
		// Lets generate the tree view
		this.generateGraphTreeView(ansiClass);
		
		// Generate the series
		XYChart.Series<String, Number> idealSeries = new XYChart.Series<String, Number>();
		idealSeries.setName("Ideal Proportion");
	    
	    XYChart.Series<String, Number> realSeries = new XYChart.Series<String, Number>();
	    realSeries.setName("Obtained Proportion");
	    
	    // Now go trough the data
	    for(AnsiClass ac : ansiClass) {  		
	    	// Add the info
	    	idealSeries.getData().add( new XYChart.Data<String, Number>(ac.getName(), ac.getDefaultProportion()) );
	    	realSeries.getData().add( new XYChart.Data<String, Number>(ac.getName(), ac.getObtainedProportion() ) );
	    }

	    // Add it to the area chart
	    areaChartSeries.add(idealSeries);
	    areaChartSeries.add(realSeries);
	}
	
	
	
	/**
	 * Method that generates the tree views and already sets up the format, so that the panel will
	 * only need to retrieve it and show it.
	 * @param ansiClass 
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public void generateGraphTreeView(LinkedList<AnsiClass> ansiClass) throws SAXException, IOException, ParserConfigurationException {
		// Icons
		javafx.scene.Node rootIcon = new ImageView(new Image("/gui/view/img/graph-root.png"));
		
		// Create the base for the tree
		graphRootItem = new TreeItem<String> (getRootName(), rootIcon);
		graphRootItem.setExpanded(true);
        
        // Now get a list
        LinkedList<TreeItem<String>> nodesList = getGraphTreeNodes();
        
        // Add tables to the list
        addTablesToGraphView(nodesList, ansiClass);
        
        // Now add it
        for( TreeItem<String> node : nodesList ) {
        	graphRootItem.getChildren().add(node);
        	node.setExpanded(true);
        }
       
	}
	
	
	
	/**
	 * Method that adds the selected tables to each corresponding category.
	 * This doesn't read any extra file, just uses the in-memory list of AnsiClass.
	 * @see This method will requiere changing to add the multiple hierarchy handling.
	 * @param nodesList The list of tree view nodes.
	 * @param ansiClass The list of ansi classes with the links to the tables.
	 */
	private void addTablesToGraphView(List<TreeItem<String>> nodesList, LinkedList<AnsiClass> ansiClass) {
		// FIXME THIS METHOD WILL NEED TO CHANGE TO ADD THE MULTIPLE HIERARCHY HANDLING
		// For each node
		for(TreeItem<String> item : nodesList) {
			// For each class
			for(AnsiClass ac : ansiClass) {
				// If the class has tables and it is the same of the item...
				if( ac.getCatTableList().size() > 0 && ac.getName().equals(item.getValue()) ) {
					// Then, for each table...
					for(CategorizedTable table : ac.getCatTableList()) {
						// Create the icon:
						javafx.scene.Node tIcon = new ImageView(new Image("/gui/view/img/tree-table.png"));
									
						// Now create the tablenode
						TreeItem<String> tableItem = new TreeItem<String>( table.toString(), tIcon );
						
						// Lets add the new item
						item.getChildren().add(tableItem);
					}
				}
			}
			
			// Once we are here, call the method with the children of this tree item
			addTablesToGraphView(item.getChildren(), ansiClass);
		}
	}







	/**
	 * A method to get the tree nodes for the treeview on the view for the barchart.
	 * @return The list of all the nodes to add to the root.
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	private String getRootName() throws SAXException, IOException, ParserConfigurationException {
		// Save a variable for the name
		String name = "";
		
		// Reference the file
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		Document synDoc = dbFactory.newDocumentBuilder().parse( new File( GRAPH_FILE ) );
						 
		// Normalize it
		synDoc.getDocumentElement().normalize();
						 
		// Now get the nodes
		NodeList nodes = synDoc.getElementsByTagName( GraphLabels.ROOT.getLabel() );
		
		// For all the nodes...
		for (int i = 0; i < nodes.getLength(); i++) {
			// Get the current one
			Node node = nodes.item(i);
					 
			// If it is a node
			if ( node.getNodeType() == Node.ELEMENT_NODE ) {
				// Get the element
				Element element = (Element) node;
				name = element.getAttribute( GraphLabels.NODE_NAME.getLabel() );
			}
		}
		
		// Lets return the name
		return name;
	}





	/**
	 * A method to get the tree nodes for the treeview on the view for the barchart.
	 * @return The list of all the nodes to add to the root.
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	private LinkedList<TreeItem<String>> getGraphTreeNodes() throws SAXException, IOException, ParserConfigurationException {
		// Create the list
		LinkedList<TreeItem<String>> nodesList = new LinkedList<TreeItem<String>>();
		
		// Reference the file
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		Document synDoc = dbFactory.newDocumentBuilder().parse( new File( GRAPH_FILE ) );
						 
		// Normalize it
		synDoc.getDocumentElement().normalize();
		
		// Now get the root
		Node root = synDoc.getElementsByTagName( GraphLabels.ROOT.getLabel() ).item(0);

		// Now get the nodes
		NodeList nodes = ((Element) root).getChildNodes();
		 // For all the nodes...
		 for (int i = 0; i < nodes.getLength(); i++) {
			 // Get the current one
			 Node node = nodes.item(i);
			 
			 // If it is a node
			 if ( node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName() == GraphLabels.NODE.getLabel() ) {
				// Get the element
				Element element = (Element) node;
				
				// Create the image
				javafx.scene.Node nodeIcon = new ImageView(new Image("/gui/view/img/graph-middle.png"));
				
				// Lets get the childs...
				NodeList childs = element.getChildNodes();
				
				// Check the lenght
				if( childs.getLength() == 0) {
					// If there is no child, change the icon
					nodeIcon = new ImageView(new Image("/gui/view/img/graph-leaf.png"));
				}
									
				
				// Now create the tablenode
				TreeItem<String> tableItem = new TreeItem<String>
							( element.getAttribute( GraphLabels.NODE_NAME.getLabel() ), nodeIcon );
				
				
				// Check again, if there is no child...
				if(childs.getLength() != 0) {
					// For each children
					for(int j=0; j<childs.getLength(); j++) {
						 // Get the current one
						 Node cnode = childs.item(j);
						 
						 // If it is a node
						 if ( cnode.getNodeType() == Node.ELEMENT_NODE && cnode.getNodeName() == GraphLabels.NODE.getLabel()) {
							 tableItem.getChildren().add( getGraphChildrenOf(cnode) );
						 }

					}
				}
				
				// Add the item to the list
				nodesList.add(tableItem) ;
			 }
		 }
		 
		 
			 
		 
		// Return the list
		return nodesList;		 		 
	}




	/**
	 * A method to get the child tree nodes for the treeview on the view for the barchart.
	 * @return The list of all the nodes to add to the root.
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	private TreeItem<String> getGraphChildrenOf(Node cnode) {
		// Create the image
		javafx.scene.Node cnodeIcon = new ImageView(new Image("/gui/view/img/graph-middle.png"));
		
		// Lets get the childs...
		NodeList childs = ((Element) cnode).getChildNodes();
		
				
		// Check the lenght
		if( childs.getLength() == 1) {
			// If there is no child, change the icon
			cnodeIcon = new ImageView(new Image("/gui/view/img/graph-leaf.png"));
		}
				
		// Now create the tablenode
		TreeItem<String> nodeItem = new TreeItem<String>
					( ((Element) cnode).getAttribute( GraphLabels.NODE_NAME.getLabel() ), cnodeIcon );
		
		

		
		
		// Check again, if there is no child...
		if(childs.getLength() != 0) {
			// For each children
			for(int j=0; j<childs.getLength(); j++) {
				 // Get the current one
				 Node c1node = childs.item(j);
				 
				 // If it is a node
				 if ( c1node.getNodeType() == Node.ELEMENT_NODE && c1node.getNodeName() == GraphLabels.NODE.getLabel() ) {
					 nodeItem.getChildren().add( getGraphChildrenOf(c1node) );
				 }
			}
		}
		
		// Return the item
		return nodeItem;
	 }





	/**
	 * Method that returns the information model for the area
	 * chart with the distribution of information.
	 * @return The series of data.
	 */
	public static ObservableList<Series<String, Number>> getAreaGraphData() {
		return areaChartSeries;
	}




	/**
	 * Method that returns the information model for the area
	 * chart with the hyphenation estimation.
	 * @return The series of data.
	 */
	public static ObservableList<BarChart.Series<String, Number>> getHyphChartData() {
		return hyphenationChartSeries;
	}
	
	
	

	
	
	/**
	 * A method to analyze the saved data and generate the bar chart with the information regarding
	 * the estimated hyphenation of the ERP's database. It also generates the list for the table
	 * names.
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public void generateHyphenationChart() throws SAXException, IOException, ParserConfigurationException {
		// Create the counters
		int countGoodTables = 0;
		int countBadTables = 0;
		int countGoodColumns = 0;
		int countBadColumns = 0;
		

		
		// Now evaluate the Hyphenation file...
		File hyphFile = new File( HYPHENATION_FILE );
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		Document doc = dbFactory.newDocumentBuilder().parse( hyphFile );
		
		// Get the root element
		Element rootNode = doc.getDocumentElement();
		
		
		/*
		 * Get the good element
		 */
		 Element gelem = (Element) rootNode
		  			.getElementsByTagName( HyphenationLabels.GOOD_SEPARATION.getLabel() ).item(0);
		  
		 // For the tables
		 NodeList goodTableNodes = ((Element) gelem
				  .getElementsByTagName( HyphenationLabels.TABLE_NAMES.getLabel() ).item(0)).getChildNodes();
		 
		 for(int i=0; i<goodTableNodes.getLength(); i++) {
			 // Get the element
			 Node gtNode = goodTableNodes.item(i);
			 
			// If it is a node
			if( gtNode.getNodeType() == Node.ELEMENT_NODE 
					&& gtNode.getNodeName() == HyphenationLabels.NAME.getLabel() ) {
				// Count it
				countGoodTables++;
				
				// Get the word
				String word = ((Element) gtNode).getAttribute( HyphenationLabels.WORD.getLabel() );
				
				// Get a possible model
				WordTableModel wtm = alreadySaved(goodTableWords, word);
				
				// Check if it is null
				if( wtm == null ) {
					// Create one, and add it
					goodTableWords.add( new WordTableModel( word ) );
				}
				// In the other case, just increase it
				else {
					wtm.setCount();
				}
				
			}
		 }
		 
		 // Now for the columns
		 NodeList goodColumnNodes = ((Element) gelem
				  .getElementsByTagName( HyphenationLabels.COLUMN_NAMES.getLabel() ).item(0)).getChildNodes();
		
		 for(int i=0; i<goodColumnNodes.getLength(); i++) {
			 // Get the element
			 Node gcNode = goodColumnNodes.item(i);
			 
			// If it is a node
			if( gcNode.getNodeType() == Node.ELEMENT_NODE 
					&& gcNode.getNodeName() == HyphenationLabels.NAME.getLabel() ) {
				// Count it
				countGoodColumns++;
				
				// Get the word
				String word = ((Element) gcNode).getAttribute( HyphenationLabels.WORD.getLabel() );
				
				// Get a possible model
				WordTableModel wtm = alreadySaved(goodColumnWords, word);
				
				// Check if it is null
				if( wtm == null ) {
					// Create one, and add it
					goodColumnWords.add( new WordTableModel( word ) );
				}
				// In the other case, just increase it
				else {
					wtm.setCount();
				}
				
			}
		 }
		
		 
		 
		/*
		 * Get the bad element
		 */
		 Element belem = (Element) rootNode
		  			.getElementsByTagName( HyphenationLabels.BAD_SEPARATION.getLabel() ).item(0);
		  
		 // For the tables
		 NodeList badTableNodes = ((Element) belem
				  .getElementsByTagName( HyphenationLabels.TABLE_NAMES.getLabel() ).item(0)).getChildNodes();
		 
		 for(int i=0; i<badTableNodes.getLength(); i++) {
			 // Get the element
			 Node btNode = badTableNodes.item(i);
			 
			// If it is a node
			if( btNode.getNodeType() == Node.ELEMENT_NODE 
					&& btNode.getNodeName() == HyphenationLabels.NAME.getLabel() ) {
				// Count it
				countBadTables++;
				
				// Get the word
				String word = ((Element) btNode).getAttribute( HyphenationLabels.WORD.getLabel() );
				
				// Get a possible model
				WordTableModel wtm = alreadySaved(badTableWords, word);
				
				// Check if it is null
				if( wtm == null ) {
					// Create one, and add it
					badTableWords.add( new WordTableModel( word ) );
				}
				// In the other case, just increase it
				else {
					wtm.setCount();
				}
			}
		 }
		 
		 // Now for the columns
		 NodeList badColumnNodes = ((Element) belem
				  .getElementsByTagName( HyphenationLabels.COLUMN_NAMES.getLabel() ).item(0)).getChildNodes();
		
		 for(int i=0; i<badColumnNodes.getLength(); i++) {
			 // Get the element
			 Node bcNode = badColumnNodes.item(i);
			 
			// If it is a node
			if( bcNode.getNodeType() == Node.ELEMENT_NODE 
					&& bcNode.getNodeName() == HyphenationLabels.NAME.getLabel() ) {
				// Count it
				countBadColumns++;
				
				// Get the word
				String word = ((Element) bcNode).getAttribute( HyphenationLabels.WORD.getLabel() );
				
				// Get a possible model
				WordTableModel wtm = alreadySaved(badColumnWords, word);
				
				// Check if it is null
				if( wtm == null ) {
					// Create one, and add it
					badColumnWords.add( new WordTableModel( word ) );
				}
				// In the other case, just increase it
				else {
					wtm.setCount();
				}
			}
		 }
	
		
		// Create the table series
		XYChart.Series<String, Number> seriesTable = new XYChart.Series<String, Number>();
		seriesTable.setName("Table Words");
		seriesTable.getData().add( new XYChart.Data<String, Number>("Good Separation", countGoodTables) );
		seriesTable.getData().add( new XYChart.Data<String, Number>("Bad Separation", countBadTables) );
		
		// Then, create the column series
		XYChart.Series<String, Number> seriesColumns = new XYChart.Series<String, Number>();
		seriesColumns.setName("Columns Words");
		seriesColumns.getData().add( new XYChart.Data<String, Number>("Good Separation", countGoodColumns) );
		seriesColumns.getData().add( new XYChart.Data<String, Number>("Bad Separation", countBadColumns) );
		
		// And add the series
        hyphenationChartSeries.add(seriesTable);
        hyphenationChartSeries.add(seriesColumns);
        
        
        // Save the general good proportion
        estimatedHyphenationPercent = ( ( (countGoodTables + countGoodColumns) * 1.0  )
        		/ ((countGoodTables + countGoodColumns + countBadTables + countBadColumns) * 1.0) ) * 100;
	}





	/**
	 * Method that analyses an observable list composed of a Word Table Model, to check if a
	 * certain word is already on that list, or not.
	 * @param wordList The list to check.
	 * @param word The word to be searched for.
	 * @return The WordModel if found, null if not.
	 */
	private WordTableModel alreadySaved(ObservableList<WordTableModel> wordList, String word) {
		// For each element of the list
		for(WordTableModel wtm : wordList) {
			// If this is it
			if(wtm.getWord().equals(word)) {
				// Return this
				return wtm;
			}
		}
		
		// If we got out, the word isn't here
		return null;
	}





	/**
	 * Method to get the estimated hyphenation percent, to show
	 * on the label in the visual chart.
	 * @return The estimated hyphenation value.
	 */
	public static double getEstHyphPercent() {
		return estimatedHyphenationPercent;
	}




	/**
	 * Method to get the list of the words, according to the text
	 * received as a parameter.
	 * @param n Text indicating which list they need.
	 * @return The words list in a tableview format.
	 */
	public static ObservableList<WordTableModel> getWordsList(String n) {
		if( n.equals("Good Separation Table Words")  )
			return goodTableWords;
		else if( n.equals("Good Separation Columns Words") )
			return goodColumnWords;
		else if( n.equals("Bad Separation Table Words") )
			return badTableWords;
		else
			return badColumnWords;
	}



}
