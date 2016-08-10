package xmlfiles;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javafx.scene.control.TreeItem;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import xmlfiles.labels.CatTablesLabels;
import xmlfiles.labels.DistributedInfoLabels;
import xmlfiles.labels.HyphenationLabels;
import xmlfiles.labels.RelationLabels;
import xmlfiles.labels.ResultsTypesLabels;
import xmlfiles.labels.UncatTablesLabels;
import core.objects.GraphRelation;
import core.objects.ParentGraphNode;
import database.ErpDB;
import database.objects.AnsiClass;
import database.objects.CategorizedTable;
import database.objects.Category;
import database.objects.ColumnName;
import database.objects.TableName;





/**
 * A class that works as a XML writer, that saves all the information of the classification
 * process into the mandatory predefined XML files.
 * @author Ing. Melina C. Vidoni - 2014
 *
 */
public class XMLWriterReader {
	private static String UNCLASS_FILE = "files/results/UnclassifiedTables_" + ErpDB.getInstance().getName() + ".xml";
	private static String CLASS_FILE = "files/results/ClassifiedTables_" + ErpDB.getInstance().getName() + ".xml";
	private static String TYPE_FILE = "files/results/ResultsTypes_" + ErpDB.getInstance().getName() + ".xml";
	private static String DISTINFO_FILE = "files/results/DistributedInfo_" + ErpDB.getInstance().getName() + ".xml";
	private static String HYPHENATION_FILE = "files/results/Hyphenation_" + ErpDB.getInstance().getName() + ".xml";
	private static String RELATION_FILE = "files/Relation.xml";
	private static int qtyClassTable = 0;
	private static int qtyTotalTable = 0;

	
	
	
	
	/**
	 * A method for writing an XML file with the resulting separating of the classifications on
	 * the three types: tricky, true and neutral. This must be called after the results has been
	 * calculated by @see ResultsGenerator.
	 * @throws ParserConfigurationException Errors during parsing
	 * @throws TransformerException Generic Exception
	 */
	public static void writeTypesXML() throws ParserConfigurationException, TransformerException {
		// Get a reader
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		

		// Make root element
		Document doc = docBuilder.newDocument();
		Element rootElem = doc.createElement( ResultsTypesLabels.ROOT.getLabel() );
			rootElem.setAttribute("xmlns:tns", "http://www.example.org/ResultsTypesSchema");
			rootElem.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			rootElem.setAttribute("xsi:schemaLocation", "http://www.example.org/ResultsTypesSchema ResultsTypesSchema.xsd ");
			rootElem.setAttribute(ResultsTypesLabels.TOTAL_QTY.getLabel(), 
						Integer.toString(ResultsGenerator.getTotalClassification()) );
		
		doc.appendChild(rootElem);
		
		
		/*
		 * TRICKY TYPE
		 */
		Element trickyElem =  doc.createElement( ResultsTypesLabels.TYPES_TYPE.getLabel() );
			trickyElem.setAttribute( ResultsTypesLabels.TYPE_NAME.getLabel() , "Tricky");
		
		// Get the root
		TreeItem<String> rootTricky = ResultsGenerator.getTypesTreeRoot("Tricky");
		
		// For each item on the tree
		for(TreeItem<String> ti : rootTricky.getChildren() ) {
			// Create a new element
			Element tableElem = doc.createElement( ResultsTypesLabels.TABLE_TYPE.getLabel() );
			tableElem.setAttribute( ResultsTypesLabels.TABLE_NAME.getLabel() , ti.getValue() );
				
			// Add all the classification
			for(TreeItem<String> ci : ti.getChildren() ) {
				// Create the new element
				Element catElem = doc.createElement( ResultsTypesLabels.CATEGORY_TYPE.getLabel() );
				catElem.setAttribute( ResultsTypesLabels.CATEGORY_NAME.getLabel() , ci.getValue() );
				
				// Add it to the table
				tableElem.appendChild( catElem );
			}
			
			// Add it to the tricky part
			trickyElem.appendChild( tableElem );
		}
		
		// Add the type to the root
		rootElem.appendChild( trickyElem );

		
		
		/*
		 * TRUE TYPE
		 */
		Element trueElem =  doc.createElement( ResultsTypesLabels.TYPES_TYPE.getLabel() );
		trueElem.setAttribute( ResultsTypesLabels.TYPE_NAME.getLabel() , "True");
		
		// Get the root
		TreeItem<String> rootTrue = ResultsGenerator.getTypesTreeRoot("True");
		
		// For each item on the tree
		for(TreeItem<String> ti : rootTrue.getChildren() ) {
			// Create a new element
			Element tableElem = doc.createElement( ResultsTypesLabels.TABLE_TYPE.getLabel() );
			tableElem.setAttribute( ResultsTypesLabels.TABLE_NAME.getLabel() , ti.getValue() );
				
			// Add all the classification
			for(TreeItem<String> ci : ti.getChildren() ) {
				// Create the new element
				Element catElem = doc.createElement( ResultsTypesLabels.CATEGORY_TYPE.getLabel() );
				catElem.setAttribute( ResultsTypesLabels.CATEGORY_NAME.getLabel() , ci.getValue() );
				
				// Add it to the table
				tableElem.appendChild( catElem );
			}
			
			// Add it to the tricky part
			trueElem.appendChild( tableElem );
		}
		
		// Add the type to the root
		rootElem.appendChild( trueElem );
		
		

		/*
		 * NEUTRAL TYPE
		 */
		Element neutralElem =  doc.createElement( ResultsTypesLabels.TYPES_TYPE.getLabel() );
		neutralElem.setAttribute( ResultsTypesLabels.TYPE_NAME.getLabel() , "Neutral");
		
		// Get the root
		TreeItem<String> rootNeutral = ResultsGenerator.getTypesTreeRoot("Neutral");
		
		// For each item on the tree
		for(TreeItem<String> ti : rootNeutral.getChildren() ) {
			// Create a new element
			Element tableElem = doc.createElement( ResultsTypesLabels.TABLE_TYPE.getLabel() );
			tableElem.setAttribute( ResultsTypesLabels.TABLE_NAME.getLabel() , ti.getValue() );
				
			// Add all the classification
			for(TreeItem<String> ci : ti.getChildren() ) {
				// Create the new element
				Element catElem = doc.createElement( ResultsTypesLabels.CATEGORY_TYPE.getLabel() );
				catElem.setAttribute( ResultsTypesLabels.CATEGORY_NAME.getLabel() , ci.getValue() );
				
				// Add it to the table
				tableElem.appendChild( catElem );
			}
			
			// Add it to the tricky part
			neutralElem.appendChild( tableElem );
		}
		
		// Add the type to the root
		rootElem.appendChild( neutralElem );
		
		

		
		
		// Prepare source and result
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File( TYPE_FILE ));
 
		// Transform
		transformer.transform(source, result);
		
		
	}
	
	
	
	
	/**
	 * Method that saves the information of classified tables into a predefined
	 * XML file, that will be later used on the graphics generation.
	 * @param currentT The current tablename.
	 * @param list The list with temporal classification.
	 * @throws SAXException Error on sax
	 * @throws IOException Error with input and output
	 * @throws ParserConfigurationException Parsing error config
	 * @throws TransformerException Transforming error
	 */
	public static void saveClassifiedTable(TableName currentT, LinkedList<Category> list) throws SAXException, IOException, ParserConfigurationException, TransformerException {
		File classFile = new File( CLASS_FILE );
		qtyClassTable++;
		qtyTotalTable++;
		
		// Create the file
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		Document doc = dbFactory.newDocumentBuilder().parse( classFile );
		
		// Get the root element
		Element rootNode = doc.getDocumentElement();
		
		/*
		 * WRITE
		 */
		// Create te element of the table
		Element table = doc.createElement( CatTablesLabels.TABLE.getLabel() );
			table.setAttribute( CatTablesLabels.TABLE_NAME.getLabel() , currentT.getName() );
		
			
		// For each category
		for( Category c : list ) {	
			// Calculate Final % (Original: average)
			c.setFinalPercentage( 0.5 * c.getTableCategoryPercentage() + 0.5 * c.getColumnCategoryPercentage() );		
			
			// Create class element
			Element classEl = doc.createElement( CatTablesLabels.CLASS.getLabel() );
				classEl.setAttribute( CatTablesLabels.CLASS_NAME.getLabel() , c.getTableCategory() );
				classEl.setAttribute(CatTablesLabels.CLASS_PERCENT.getLabel() , Double.toString(c.getFinalPercentage()) );
			
			// Create tablename category
			Element tableClass = doc.createElement( CatTablesLabels.TABLE_CLASS.getLabel() );
				tableClass.setAttribute( CatTablesLabels.CLASS_PERCENT.getLabel() , Double.toString( c.getTableCategoryPercentage() ) );
				classEl.appendChild(tableClass);
				
			// Create column name category
			Element columnClass = doc.createElement( CatTablesLabels.COLUMN_CLASS.getLabel() );
				columnClass.setAttribute( CatTablesLabels.CLASS_PERCENT.getLabel() , Double.toString( c.getColumnCategoryPercentage() ) );
				classEl.appendChild(columnClass);
				
			// Append the class
			table.appendChild(classEl);
		}
		
		
		//For each columname
		for( ColumnName cn : currentT.getColumnames() ) {
			// Create the element
			Element elemCol = doc.createElement( CatTablesLabels.COLUMN.getLabel() );
				elemCol.setAttribute( CatTablesLabels.COLUMN_NAME.getLabel() , cn.getName() );
			
			// Add it to the table
			table.appendChild(elemCol);
		}
	
		// Add to the root
		rootNode.appendChild(table);
		
		
		// Prepare for the transformation...
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult( classFile );
 
		// Transform the file
		transformer.transform(source, result);
	}
	
	
	
	
	
	
	
	
	/**
	 * Method that saves the information of not classified tables into a predefined
	 * XML file, that will be later used on the graphics generation.
	 * @param name The name of the table to save in this file.
	 * @throws TransformerException General transforming error
	 * @throws SAXException Errors with sax
	 * @throws IOException Erros with input and output
	 * @throws ParserConfigurationException Erros with persing files.
	 */
	public static void saveUnclassifiedTable(String name) throws TransformerException, SAXException, IOException, ParserConfigurationException {
		File unclassFile = new File( UNCLASS_FILE );
		qtyTotalTable++;
		
		// Create the file
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		Document doc = dbFactory.newDocumentBuilder().parse( unclassFile );
		
		// Get the root element
		Element rootNode = doc.getDocumentElement();
 
		// Create te element of the table
		Element table = doc.createElement( UncatTablesLabels.TABLE.getLabel() );
		table.setAttribute(UncatTablesLabels.TABLE_NAME.getLabel(), name);
		
		// Add the element to the root
		rootNode.appendChild( table );
		
		
		// Prepare for the transformation...
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult( unclassFile );
 
		// Transform the file
		transformer.transform(source, result);
	}




	
	/**
	 * Method that creates the raw files where the classification results are going to be added.
	 * @throws ParserConfigurationException For errors on parsing
	 * @throws TransformerException For errors on transforming
	 */
	public static void createFiles() throws ParserConfigurationException, TransformerException {
		// Get a reader
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		

		/*
		 * UNCLASSIFIED TABLES FILES
		 */
		// Make root element
		Document docUnclass = docBuilder.newDocument();
		Element rootElementUnclass = docUnclass.createElement( UncatTablesLabels.ROOT.getLabel() );
			rootElementUnclass.setAttribute("xmlns:tns", "http://www.example.org/BagOfWordsSchema");
			rootElementUnclass.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			rootElementUnclass.setAttribute("xsi:schemaLocation", "http://www.example.org/UnclassifiedTablesSchema ../results/UnclassifiedTablesSchema.xsd ");
		
		
		docUnclass.appendChild(rootElementUnclass);
		
		// Prepare source and result
		DOMSource sourceUnclass = new DOMSource(docUnclass);
		StreamResult resultUnclass = new StreamResult(new File( UNCLASS_FILE ));
 
		// Transform
		transformer.transform(sourceUnclass, resultUnclass);
		
		
		
		/*
		 * CLASSIFIED TABLES FILES
		 */
		// Make root element
		Document docClass = docBuilder.newDocument();
		Element rootElementClass = docClass.createElement( UncatTablesLabels.ROOT.getLabel() );
			rootElementClass.setAttribute("xmlns:tns", "http://www.example.org/BagOfWordsSchema");
			rootElementClass.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			rootElementClass.setAttribute("xsi:schemaLocation", "http://www.example.org/ClassifiedTablesSchema ../results/ClassifiedTablesSchema.xsd ");

		docClass.appendChild(rootElementClass);
		
		// Prepare source and result
		DOMSource sourceClass = new DOMSource(docClass);
		StreamResult resultClass = new StreamResult(new File( CLASS_FILE ));
 
		// Transform
		transformer.transform(sourceClass, resultClass);
		
		
		/*
		 * INFORMATION DISTRIBUTION FILE
		 */
		// Make root element
		Document docInfo = docBuilder.newDocument();
		Element rootElementInfo = docInfo.createElement( DistributedInfoLabels.ROOT.getLabel() );
			rootElementInfo.setAttribute("xmlns:tns", "http://www.example.org/DistributedInfoSchema");
			rootElementInfo.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			rootElementInfo.setAttribute("xsi:schemaLocation", "http://www.example.org/DistributedInfoSchema ../results/DistributedInfoSchema.xsd ");

		docInfo.appendChild(rootElementInfo);
		
		// Prepare source and result
		DOMSource sourceDistInfo = new DOMSource(docInfo);
		StreamResult resultDistInfo = new StreamResult(new File( DISTINFO_FILE ));
 
		// Transform
		transformer.transform(sourceDistInfo, resultDistInfo);
		
		
		/*
		 * HYPHENATION FILE
		 */
		// Make root element
		Document hyphFile = docBuilder.newDocument();
		Element rootElementHyph = hyphFile.createElement( HyphenationLabels.ROOT.getLabel() );
			rootElementHyph.setAttribute("xmlns:tns", "http://www.example.org/HyphenationSchema");
			rootElementHyph.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			rootElementHyph.setAttribute("xsi:schemaLocation", "http://www.example.org/HyphenationSchema HyphenationSchema.xsd ");
		
		// Create mandatory childs
		Element badSep = hyphFile.createElement( HyphenationLabels.BAD_SEPARATION.getLabel() );
			badSep.appendChild( hyphFile.createElement( HyphenationLabels.TABLE_NAMES.getLabel() ) );
			badSep.appendChild( hyphFile.createElement( HyphenationLabels.COLUMN_NAMES.getLabel() ) );
			
		Element goodSep = hyphFile.createElement( HyphenationLabels.GOOD_SEPARATION.getLabel() );
			goodSep.appendChild( hyphFile.createElement( HyphenationLabels.TABLE_NAMES.getLabel() ) );
			goodSep.appendChild( hyphFile.createElement( HyphenationLabels.COLUMN_NAMES.getLabel() ) );
			
		// Add them to the root	
		rootElementHyph.appendChild(badSep);
		rootElementHyph.appendChild(goodSep);
		
		hyphFile.appendChild(rootElementHyph);
		
		// Prepare source and result
		DOMSource sourceHyphFile = new DOMSource(hyphFile);
		StreamResult resultHyphFile = new StreamResult( new File( HYPHENATION_FILE ) );
		
		// Transform	
		transformer.transform(sourceHyphFile, resultHyphFile);
	}






	
	/**
	 * Method to get the total of classified tables.
	 * @return An integer with the flat number.
	 */
	public static int getTotalClassifiedTables() {
		return qtyClassTable;
	}




	/**
	 * Method that reads the classified tables file, adding all of the tables information into the list
	 * that will be used for the Propagator on the information distribution analysis.
	 * @return A list of TableName with the classification information of each of them.
	 * @throws SAXException For errors with sax.
	 * @throws IOException For input/output errors.
	 * @throws ParserConfigurationException For parsing errors on the files.
	 */
	public static LinkedList<TableName> getClassifiedTables() throws SAXException, IOException, ParserConfigurationException {
		// Create the list
		LinkedList<TableName> tablenameList = new LinkedList<TableName>();
		
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
				
				// Save the name
				TableName table = new TableName(element.getAttribute( CatTablesLabels.TABLE_NAME.getLabel() ));
				
				// Now get the categories
				NodeList classNodesList = element.getChildNodes();
				
				// For each category
				for(int j=0; j < classNodesList.getLength(); j++) {
					// Get current node
					Node classNode = classNodesList.item(j);
					
					// If it is an element and class type
					if( classNode.getNodeType() == Node.ELEMENT_NODE 
							&& classNode.getNodeName() == CatTablesLabels.CLASS.getLabel() ) {
						// Create a category
						Category cat = new Category();
						cat.setTableCategory( ((Element) classNode).getAttribute( CatTablesLabels.CLASS_NAME.getLabel() ) );
						cat.setFinalPercentage( Double.parseDouble(((Element) classNode).getAttribute( CatTablesLabels.CLASS_PERCENT.getLabel() )) );
					
						// Add it
						table.addCategory(cat);
					}
					
				}
				
				// Add the tablename
				tablenameList.addLast(table);
			 }
		 }
		 
		 return tablenameList;
	}




	
	
	/**
	 * Method that reads the Relation file for the first time, in order to add the list of classes to the
	 * list that the Propagator has. This allows a first and fast search, already setting the proportions.
	 * @return The list to be set up.
	 * @throws SAXException For sax errors
	 * @throws IOException For input and ouput errors
	 * @throws ParserConfigurationException For parsing config errors
	 */
	public static LinkedList<AnsiClass> getAnsiClasses() throws SAXException, IOException, ParserConfigurationException {
		// Create the list
		LinkedList<AnsiClass> classesList = new LinkedList<AnsiClass>();
		
		// Reference the file
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		Document synDoc = dbFactory.newDocumentBuilder().parse( new File( RELATION_FILE ) );
		 
		// Normalize it
		synDoc.getDocumentElement().normalize();
				 
		// Now get the nodes
		NodeList nodes = synDoc.getChildNodes().item(0).getChildNodes();
		
						 
		 // For all the nodes...
		 for (int i = 0; i < nodes.getLength(); i++) {
			 // Get the current one
			 Node node = nodes.item(i);
			 
			 // If it is a node
			 if ( node.getNodeType() == Node.ELEMENT_NODE 
					 && node.getNodeName().equals(RelationLabels.CHILD_NODE.getLabel()) ) {
				// Get the element
				Element element = (Element) node;
				
				// Save the name
				AnsiClass ansiClass = new AnsiClass();
				ansiClass.setName( element.getAttribute( RelationLabels.NODE_NAME.getLabel() ) );
				ansiClass.setDefaultProportion( Double.parseDouble(element.getAttribute( RelationLabels.PROP_X.getLabel() )) );
				
				// Lets check if it is already added
				if( ! alreadyAdded(classesList, ansiClass.getName()) )
					// Add it to the list
					classesList.addFirst(ansiClass);
				
				// Now check the parents
				NodeList pnodes = node.getChildNodes();
				for(int j=0; j < pnodes.getLength(); j++) {
					// Get the current
					Node pnode = pnodes.item(j);
					
					 // If it is a node
					 if ( pnode.getNodeType() == Node.ELEMENT_NODE 
							 && pnode.getNodeName() == RelationLabels.PARENT_NODE.getLabel() ) {
						// Get the element
						Element pelement = (Element) pnode;
						
						// Save the name
						AnsiClass pansiClass = new AnsiClass();
						pansiClass.setName( pelement.getAttribute( RelationLabels.NODE_NAME.getLabel() ) );
						pansiClass.setDefaultProportion( Double.parseDouble(pelement.getAttribute( RelationLabels.PROP_X.getLabel() )) );
						
						// Lets check if it is already added
						if( ! alreadyAdded(classesList, pansiClass.getName()) )
							// Add it to the list
							classesList.addFirst(pansiClass);
					 }
				}
			 }
		 }
		 
		 // Return the list of classes
		 return classesList;
	}




	/**
	 * Checks if the ANSI class was already added to the list.
	 * @param classesList The list of classes to evaluate if it is there.
	 * @param name The name of the class to be searched.
	 * @return @true if founded, @false if not founded.
	 */
	private static boolean alreadyAdded(LinkedList<AnsiClass> classesList,	String name) {
		// Lets evaluate the ansi list
		for(AnsiClass ac : classesList) {
			// Check the name
			if( ac.getName().equals(name) ) {
				// We stop and return that we found it
				return true;
			}
		}
		
		// If we are here, we didn't found it
		return false;
	}




	/**
	 * Getter for the total quantity of analized tables.
	 * @return the var qtyTotalTable with all the info.
	 */
	public static int getTotalTableQty() {
		return qtyTotalTable;
	}




	
	/**
	 * Method that reads the information about a node and all of its parents, 
	 * @param catName The name of the category to be searched for.
	 * @return The category in a graph relation object.
	 * @throws SAXException For sax errors
	 * @throws IOException For input and output errors
	 * @throws ParserConfigurationException For parsing configuration errors
	 */
	@SuppressWarnings("unused")
	public static GraphRelation getRelationInformationOf(String catName) throws SAXException, IOException, ParserConfigurationException {
		// Create the class
		GraphRelation gr = new GraphRelation(catName, 0.0);
		
		// Reference the file
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		Document synDoc = dbFactory.newDocumentBuilder().parse( new File( RELATION_FILE ) );
		 
		// Normalize it
		synDoc.getDocumentElement().normalize();
				 
		// Now get the nodes
		NodeList nodes = synDoc.getChildNodes().item(0).getChildNodes();
		
		// Make a flag
		boolean found = false;
		
						 
		 // For all the nodes...
		 for (int i = 0; i < nodes.getLength(); i++) {
			 // Get the current one
			 Node node = nodes.item(i);
			 
			 // If it is a child node
			 if ( node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName() == RelationLabels.CHILD_NODE.getLabel() ) {
				
				 // Get the element
				Element element = (Element) node;
				
				// If this is the one we need
				if( element.getAttribute( RelationLabels.NODE_NAME.getLabel() ).equals(catName) ) {
					// Change the flag
					found = true;
					
					// Save the prop
					gr.setPropX( Double.parseDouble(element.getAttribute( RelationLabels.PROP_X.getLabel() )) );
					
					// Now get the parents
					NodeList pnodes = node.getChildNodes();
					
					// Now check the parents
					for(int j=0; j < pnodes.getLength(); j++) {
						// Get the current
						Node pnode = pnodes.item(j);
						
						 // If it is a node and a parent
						 if ( pnode.getNodeType() == Node.ELEMENT_NODE && pnode.getNodeName() == RelationLabels.PARENT_NODE.getLabel() ) {
							// Get the element
							Element pelement = (Element) pnode;
							
							// Save the information
							ParentGraphNode parentNode 
								= new ParentGraphNode( pelement.getAttribute( RelationLabels.NODE_NAME.getLabel() ),
													   Double.parseDouble(pelement.getAttribute( RelationLabels.PROP_X.getLabel())),
													   Double.parseDouble(pelement.getAttribute(RelationLabels.PROP_Y_GIVEN_X.getLabel()))
													   );
							
							
							// Add it to the list of parents
							gr.addParent(parentNode);
						 }
					}
					
					 // Return the information
					 return gr;
				}
	
			 }
		 }
		 
		 // If not found
		 if( !found ) {
			// We are facing the root!
			// Now get the nodes
			nodes = synDoc.getElementsByTagName( RelationLabels.PARENT_NODE.getLabel() );
			
			// For all the nodes...
			for (int j = 0; j < nodes.getLength(); j++) {
				// Get the current one
				Node node = nodes.item(j);
		 
				// If it is a parent node
				if ( node.getNodeType() == Node.ELEMENT_NODE 
						&& node.getNodeName() == RelationLabels.CHILD_NODE.getLabel()
						&& ((Element) node).getAttribute(RelationLabels.NODE_NAME.getLabel()).equals(catName) ) {

					// Get the element
					Element element = (Element) node;
					
					// Save the prop
					gr.setPropX( Double.parseDouble(element.getAttribute( RelationLabels.PROP_X.getLabel() )) );

			}
			 
			// Return case for the root
			return gr;
		 }
		 }
		 
		 return gr;
	}



	
	
	

	/**
	 * Method that writes the information of the propagation of information through
	 * the ANSI/ISA-95 categories graphs.
	 * @param ansiClass The list of ANSI classes that will be used to write the information.
	 * @throws ParserConfigurationException For parsin errors
	 * @throws SAXException For sax errors
	 * @throws IOException For input or output errors
	 * @throws TransformerException For transforming errors
	 */
	public static void writeDistributedInfo(LinkedList<AnsiClass> ansiClass) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		// Get the file
		File distInfoFile = new File( DISTINFO_FILE );
		
		// Create the file
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		Document doc = dbFactory.newDocumentBuilder().parse( distInfoFile );
		
		// Get the root element
		Element rootNode = doc.getDocumentElement();
		
		// For each class...
		for(AnsiClass ac : ansiClass) {
			// Create te element of the class
			Element classes = doc.createElement( DistributedInfoLabels.ANSI_CLASS.getLabel() );
			
			// Add the attributes
			classes.setAttribute(DistributedInfoLabels.CLASS_NAME.getLabel(), ac.getName());
			classes.setAttribute(DistributedInfoLabels.AVERAGE_PERCENT.getLabel(), String.valueOf(ac.getAveragePercent()) );
			classes.setAttribute(DistributedInfoLabels.DEFAULT_PROPORTION.getLabel(), String.valueOf(ac.getDefaultProportion()) );
			classes.setAttribute(DistributedInfoLabels.MAX_PERCENT.getLabel(), String.valueOf(ac.getMaxPercent()) );
			classes.setAttribute(DistributedInfoLabels.MIN_PERCENT.getLabel(), String.valueOf(ac.getMinPercent()) );
			classes.setAttribute(DistributedInfoLabels.TABLE_QTY.getLabel(), String.valueOf(ac.getFlatTableQty()) );
			
			// Now for each class, add the table list
			for(CategorizedTable table : ac.getCatTableList()) {
				// Create the element of the table
				Element tElement = doc.createElement( DistributedInfoLabels.TABLE.getLabel() );
				
				// Add the attributes
				tElement.setAttribute(DistributedInfoLabels.TABLE_NAME.getLabel(), table.getName());
				tElement.setAttribute(DistributedInfoLabels.TABLE_PERCENT.getLabel(),  String.valueOf(table.getFinalPercent()) );
				
				// Append to the parent
				classes.appendChild(tElement);
			}
			
			
			// Add the element to the root
			rootNode.appendChild( classes );
		}
		
		
		// Prepare for the transformation...
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult( distInfoFile );
 
		// Transform the file
		transformer.transform(source, result);
	}

	
	
	


	/**
	 * A method to add a word to the Hyphenation File, proceeding from a table name
	 * considering if it is potentially good separated, or potentially bad separated.
	 * @param type The type, G is the word is good, B in the other hand.
	 * @param word The word to be saved.
	 * @throws SAXException For sax errors
	 * @throws IOException For input and output errors
	 * @throws ParserConfigurationException For configuration errors
	 * @throws TransformerException For transforming errors
	 */
	public static void saveTableWord(char type, String word) throws SAXException, IOException, ParserConfigurationException, TransformerException {
		// Create the file
		File hyphFile = new File( HYPHENATION_FILE );
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		Document doc = dbFactory.newDocumentBuilder().parse( hyphFile );
		
		// Get the root element
		Element rootNode = doc.getDocumentElement();

		// Create the word element
		Element newWord = doc.createElement( HyphenationLabels.NAME.getLabel() );
		newWord.setAttribute( HyphenationLabels.WORD.getLabel() , word);
		
		
		// Check the type of action
		switch(type) {
			case 'G': /*
					   * GOOD CASE
					   */
					  // Get the good element
					  Element gelem = (Element) rootNode
					  			.getElementsByTagName( HyphenationLabels.GOOD_SEPARATION.getLabel() ).item(0);
					  
					  // Now get the table element
					  Element tgelem = (Element) gelem
							  .getElementsByTagName( HyphenationLabels.TABLE_NAMES.getLabel() ).item(0);
				
					  // Add the word element
					  tgelem.appendChild( newWord );
					  
					  break;
				
				
			case 'B':/*
				      * BAD CASE
				      */
					 // Get the good element
				  	 Element belem = (Element) rootNode
				  			.getElementsByTagName( HyphenationLabels.BAD_SEPARATION.getLabel() ).item(0);
				  
				  	 // Now get the table element
				  	 Element tbelem = (Element) belem
						  .getElementsByTagName( HyphenationLabels.TABLE_NAMES.getLabel() ).item(0);
			
				  	 // Add the word element
				  	 tbelem.appendChild( newWord );
				  	 
				  	 break;
		}
	
		
		// Prepare for the transformation...
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult( hyphFile );
 
		// Transform the file
		transformer.transform(source, result);
	}
	
	

	
	/**
	 * A method to add a word to the Hyphenation File, proceeding from a column name
	 * considering if it is potentially good separated, or potentially bad separated.
	 * @param type The type, G is the word is good, B in the other hand.
	 * @param word The word to be saved.
	 * @throws SAXException For sax errors
	 * @throws IOException For input output errors
	 * @throws ParserConfigurationException For parsing errors
	 * @throws TransformerException For transofrming errors
	 */
	public static void saveColumnWord(char type, String word) throws SAXException, IOException, ParserConfigurationException, TransformerException {
		// Create the file
		File hyphFile = new File( HYPHENATION_FILE );
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		Document doc = dbFactory.newDocumentBuilder().parse( hyphFile );
		
		// Get the root element
		Element rootNode = doc.getDocumentElement();

		// Create the word element
		Element newWord = doc.createElement( HyphenationLabels.NAME.getLabel() );
		newWord.setAttribute( HyphenationLabels.WORD.getLabel() , word);
		
		
		// Check the type of action
		switch(type) {
			case 'G': /*
					   * GOOD CASE
					   */
					  // Get the good element
					  Element gelem = (Element) rootNode
					  			.getElementsByTagName( HyphenationLabels.GOOD_SEPARATION.getLabel() ).item(0);
					  
					  // Now get the column element
					  Element cgelem = (Element) gelem
							  .getElementsByTagName( HyphenationLabels.COLUMN_NAMES.getLabel() ).item(0);
				
					  // Add the word element
					  cgelem.appendChild( newWord );
					  
					  break;
				
				
			case 'B':/*
				      * BAD CASE
				      */
					 // Get the good element
				  	 Element belem = (Element) rootNode
				  			.getElementsByTagName( HyphenationLabels.BAD_SEPARATION.getLabel() ).item(0);
				  
				  	 // Now get the column element
				  	 Element cbelem = (Element) belem
						  .getElementsByTagName( HyphenationLabels.COLUMN_NAMES.getLabel() ).item(0);
			
				  	 // Add the word element
				  	 cbelem.appendChild( newWord );
				  	 
				  	 break;
		}
	
		
		// Prepare for the transformation...
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult( hyphFile );
 
		// Transform the file
		transformer.transform(source, result);
	}
	


	
	
	
}
