package core;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import xmlfiles.XMLWriterReader;
import xmlfiles.labels.BowLabels;
import xmlfiles.labels.GraphLabels;
import xmlfiles.labels.SynonymLabels;
import database.objects.Category;
import database.objects.ColumnName;
import database.objects.TableName;
import frsf.cidisi.faia.agent.reactive.ReactiveAction;
import frsf.cidisi.faia.state.AgentState;
import frsf.cidisi.faia.state.EnvironmentState;



/**
 * Action for classifying that contains all the steps of the
 * reason algorythm used to come up with a classification.
 * Implements @see ReactiveAction from FAIA framework.
 * @author Ing. Melina C. Vidoni - 2014
 *
 */
public class ClassifyAction extends ReactiveAction {
	
	
	/**
	 * Default constructor of the class.
	 */
	public ClassifyAction() { }

	
	
	/**
     * The string representation of the action is used by some components of
     * the framework, like LatexOutput.
     * It's very important to set a correct representation of the actions,
     * particularly when using knowledge based agent. Look at the
     * CalculusAction.toLogicName() method. The result of this one is lower
     * cased, and it's supposed you are using this one in your prolog file
     * (.pl file).
     */
	@Override
	public String toString() {
		return "Classify Action";
	}
	
	
	
    /**
     * This method updates the real state of the agent and the environment.
     * 
     * @param ast This is the agent's state to be updated.-
     * @param est This is the agent's environment to be updated.-
     */
	@Override
	public EnvironmentState execute(AgentState ast, EnvironmentState est) {
		try {
			GracedAgentState gas = (GracedAgentState) ast;
			
			
			// First, lets classify the tablename
			classifyTablename( gas );
						
			// Now classify the columname
			classifyColumname( gas );
			

			// If we have categories to write...
			if( gas.getTemporalClassification().size() > 0 ) {			
				// Call the writer
				XMLWriterReader.saveClassifiedTable( gas.getCurrentTablename(), gas.getTemporalClassification() );
			    
			}
			// In the other case, we write on the no-categories file
			else {
				// Call the writer
				XMLWriterReader.saveUnclassifiedTable( gas.getCurrentTablename().getName() );				
			}
		} 
		catch (SAXException saxe) {
			// TODO Auto-generated catch block
			saxe.printStackTrace();
		} 
		catch (IOException ioe) {
			// TODO Auto-generated catch block
			ioe.printStackTrace();
		} 
		catch (ParserConfigurationException oce) {
			// TODO Auto-generated catch block
			oce.printStackTrace();
		} 
		catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		
		// Mandatory return
		return null;
	}

	
	
	
	
	
	/**
	 * Method that classificates all the columns using each of the tablename classification
	 * in order to get a secondary percentage.
	 * @param gas The agent state information.
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	private void classifyColumname(GracedAgentState gas) throws SAXException, IOException, ParserConfigurationException {	
		// Get the columnames information
		LinkedList<String> columnsBows = getColumnBows( gas.getAnsiStandard(), gas.getTemporalClassification() );
		
    	// Now for each bow
    	for( String bow : columnsBows ) {
    		// Get the synonyms for the columns
    		LinkedList<String> columnWords = getColumnWords( bow, gas.getCurrentTablename().getColumnames() );
    		
    		// Now evaluate with those synonyms
    		Category colCat = evaluateColumnNames( bow, columnWords );
    		
    		// Save if it is worthy
    		// Here is where the column filter goes (original, 7)
    		boolean worthy = colCat.getColumnCategoryPercentage() > 5.0;
    		Category removeCat = null;
    		
    		// Now evaluate all of the categories
    		for( Category c : gas.getTemporalClassification() ) {

    			// If the column cat is this category
    			if( colCat.getColumnCategory().equals( c.getTableCategory() ) ) {
    				
    				// If this is worthy
    				if( worthy ) {
        				// Add the info
        				c.setColumnCategory( colCat.getColumnCategory() );
        				c.setColumnCategoryPercentage( colCat.getColumnCategoryPercentage() );
        				
        				// Then cut the loop
        				break;
    				}
    				
    				// if this shouldn't be added
    				else {
    					// Save the category to be eliminated
    					removeCat = c;
    				}
    			}
    		}
    		
    		// Now lets check
    		if( !worthy ) {
    			// Remove the category
    			gas.getTemporalClassification().remove( removeCat );
    		}
    	}
    	// End method
	}

	
	
	
	
	/**
	 * Method that evaluates all the columnames in a preclassificated category, in order
	 * to get a belonging percentage.
	 * @param bow The bag of words to use.
	 * @param columnWords A list with all the words of the column names of a tablename.
	 * @return The category loading only the information regarding columns.
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	private Category evaluateColumnNames(String bow, LinkedList<String> columnWords) throws SAXException, IOException, ParserConfigurationException {
		// Prepare the list of weights
		double usedWeights = 0.0;
		double totalWeight = 0.0;
				 
		// Reference the file
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		Document synDoc = dbFactory.newDocumentBuilder().parse( new File( bow ) );
				 
		// Normalize it
		synDoc.getDocumentElement().normalize();
				 
		// Now get the nodes
		NodeList nodes = synDoc.getElementsByTagName(BowLabels.WORD.getLabel());
				 
		// For all the nodes...
		for (int i = 0; i < nodes.getLength(); i++) {
			// Get the current one
			Node node = nodes.item(i);
					 
			// If it is a node
			if ( node.getNodeType() == Node.ELEMENT_NODE ) {
				// Get the element
				Element element = (Element) node;
						  
				// Separate this weight
				Double weight = Double.valueOf( element.getAttribute( BowLabels.WEIGHT.getLabel() ) );
				String word = element.getAttribute( BowLabels.WORD_NAME.getLabel() );
					
				// Check if some word is here
				for( String wl : columnWords ) {
					// If this word is equal
					if( wl.equals( word ) ) {
						// Save the information
						usedWeights = usedWeights + weight;
					}
							
				}
						
				// In any case, save that weight
				totalWeight = totalWeight + weight;		
			}
		}
				 	
		// Save the percent (columns)
		double percent = usedWeights ;

		
		// Create the category and send it
		Category cat = new Category();
					 
		// Add the name
		cat.setColumnCategory( synDoc.getDocumentElement().getAttribute( BowLabels.BOW_NAME.getLabel()) );
					 
		// Add the percentage
		cat.setColumnCategoryPercentage( percent );
		
		
		// Return the category
		return cat;
	}



	/**
	 * Gets all the column's splitted names and evaluate the synonyms. Compares the synonyms
	 * and decide to add the original word or the synonym, depending what you need to do.
	 * @param bow The original bag of words where all the synonyms are contained.
	 * @param columnames The list with all the columnames of a table.
	 * @return An only list of all the words. They could be repeated.
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	private LinkedList<String> getColumnWords(String bow, LinkedList<ColumnName> columnames) throws SAXException, IOException, ParserConfigurationException {
		// Get the synonyms list
		LinkedList<String> synonymsFiles = getColumnsSynonyms(bow);
		
		// Prepare a main list of words
		LinkedList<String> mainList = new LinkedList<String>();
		
		// For each columname
		for(ColumnName cn : columnames) {
			
			// For each word at the splitted name
			for( String word : cn.getSplittedName() ) {
				// Prepare a flag
				boolean replaced = false;
				String replaceFor = "";
					
				// Now go through each file...
				for( String file :  synonymsFiles ) {			
					
					// Reference the file
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					Document synDoc = dbFactory.newDocumentBuilder().parse( new File( file ) );
						 
					// Normalize it
					synDoc.getDocumentElement().normalize();
						 
					// Get the main word of the document
					String mainWord = synDoc.getDocumentElement().getAttribute(SynonymLabels.MAIN_WORD.getLabel());
						 
					// Now get the nodes
					NodeList nodes = synDoc.getElementsByTagName(SynonymLabels.WORD_NAME.getLabel());
						 
					// For all the nodes...
					for (int i = 0; i < nodes.getLength(); i++) {
						// Get the current one
						Node node = nodes.item(i);
							 
						// If it is a node
						if ( node.getNodeType() == Node.ELEMENT_NODE ) {
							// Get the element
							Element element = (Element) node;
								 
							// Get the word
							String currentWord = element.getAttribute(SynonymLabels.ORIGINAL.getLabel());
								 
							// Compare with the word
							if( word.equals( currentWord ) && !replaced ) {
								// Change the flag
								replaced = true;
									 
								// Save the word to change for
								replaceFor = mainWord;
									 
								// Break this loop
								break;
							}
						}
					}
						
					// Cut the looping if this was replaced
					if( replaced )
						break;

				}
				
				
				// If this was replaced and the synonym isn't on the list already
				if( replaced && !mainList.contains(replaceFor) ) {
					// Add the replacement word
					mainList.addLast( replaceFor );
				}
				// If this wasn't replaced
				else {
					// Add the original word
					mainList.addLast( word ) ;
				}
				
			}
			
		}
		
		// And return the new list
		return mainList;
	}

	
	

	/**
	 * Method that returns a list with all the synonyms files that would be used to
	 * replace the words in the columns.
	 * @param bow The main BOW where all the synonyms are stored.
	 * @return The list with the columns synonyms files.
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	private LinkedList<String> getColumnsSynonyms(String bow) throws SAXException, IOException, ParserConfigurationException {	
		// Reference the file 
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		Document graph = dbFactory.newDocumentBuilder().parse( new File(bow) );
		 
		// Normalize it
		graph.getDocumentElement().normalize();

		// Now get the nodes
		NodeList nodes = graph.getElementsByTagName( BowLabels.WORD.getLabel() );

		// Prepare a list
		LinkedList<String> mainList = new LinkedList<String>();
		 
		// For all the nodes...
		for (int i = 0; i < nodes.getLength(); i++) {
			// Get the current one
			Node node = nodes.item(i);
			 
			// If it is a node
			if ( node.getNodeType() == Node.ELEMENT_NODE ) {
				// Get the element
				Element element = (Element) node;
				 
				// If this has a synonyms file
				if( element.getAttribute( BowLabels.SYNONYMS.getLabel() ).equals("true") ) {
					// Add it to the list
					mainList.add( "files/synonyms/" + element.getAttribute( BowLabels.FILE.getLabel() ) );
				}
	
			}
		}
		
		// Return the list
		return mainList;
	}

	
	
	


	/**
     * Method that get the column name bows that can be used to classify column names, from the
     * ansi standard, on the agent state.
     * @param ansiStandard
     * @return The list with the bows file location.
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     */
	private LinkedList<String> getColumnBows(String ansiStandard, LinkedList<Category> temporalClassification) throws SAXException, IOException, ParserConfigurationException {
		// Make a list with the names
		LinkedList<String> tempTableCat = new LinkedList<String>();
		
		// For each category
		for(Category tc : temporalClassification) {
			// Add the name to this list
			tempTableCat.addLast( tc.getTableCategory() );
		}

		
		// Reference the file
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		Document graph = dbFactory.newDocumentBuilder().parse( new File( ansiStandard ) );
		 
		// Normalize it
		graph.getDocumentElement().normalize();

		// Now get the nodes
		NodeList nodes = graph.getElementsByTagName(GraphLabels.NODE.getLabel());

		// Prepare a list
		LinkedList<String> bowList = new LinkedList<String>();
		 
		// For all the nodes...
		for (int i = 0; i < nodes.getLength(); i++) {
			// Get the current one
			Node node = nodes.item(i);
			 
			// If it is a node
			if ( node.getNodeType() == Node.ELEMENT_NODE ) {
				// Get the element
				Element element = (Element) node;
				 
				// Get the name
				String name = element.getAttribute( GraphLabels.NODE_NAME.getLabel() );
				
				// If this is usable...
				if( tempTableCat.contains( name ) ) {
					
					// Add the tablename to the list
					bowList.addLast( "files/bow/" + element.getAttribute( GraphLabels.COLUMN_BOW.getLabel() ) );
				}
			}
		}
		
		// Return the main list
		return bowList;		
	}



	
	
	/**
	 * Method that controls the classification of the tablenames in all of the allowed
	 * table-bows on the ansi standard representation.
	 * @param gas The state of the agent.
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
    private void classifyTablename(GracedAgentState gas) throws SAXException, IOException, ParserConfigurationException {
		// Get the files of bow_tabs
    	LinkedList<String> tablesBows = getTableBows( gas.getAnsiStandard() );
  
    	// Now for each bow
    	for( String bow : tablesBows ) {
    		// Evaluate current table
    		Category newCat = evaluateTablename( bow, gas.getCurrentTablename() ) ;
    		
    		// If it isn't null
    		if( newCat != null ) 
    			// Add it
    			gas.addClassification( newCat );
    	}
	}





	/**
     * Method that evaluates a tablename regarding a particular BOW and returns a category
     * if the end percentage is superior than 5%. Returns null in the other case.
     * @param bow The bag of words to be used.
     * @param currentTablename The tablename to be evaluated.
     * @return The category registered.
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     */
    private Category evaluateTablename(String bow, TableName currentTablename) throws SAXException, IOException, ParserConfigurationException {
		// For this bag of words, get with all the synonyms files
    	LinkedList<String> synonymList = getTableSynonyms(bow);
    	
    	// Replace the words...
    	LinkedList<String> replacedWordList = replaceWords( synonymList, currentTablename.getSplittedName() );
    	
    	/*
    	 * EVALUATE THE TABLENAME
    	 */
    	 // Prepare the list of weights
		 double usedWeights = 0.0;
		 double totalWeight = 0.0;
		 int foundWords = 0;
		 
		 // Reference the file
		 DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		 Document synDoc = dbFactory.newDocumentBuilder().parse( new File( bow ) );
		 
		 // Normalize it
		 synDoc.getDocumentElement().normalize();
		 
		 // Now get the nodes
		 NodeList nodes = synDoc.getElementsByTagName(BowLabels.WORD.getLabel());
		 
		 // For all the nodes...
		 for (int i = 0; i < nodes.getLength(); i++) {
			 // Get the current one
			 Node node = nodes.item(i);
			 
			 // If it is a node
			 if ( node.getNodeType() == Node.ELEMENT_NODE ) {
				// Get the element
				Element element = (Element) node;
				  
				// Separate this weight
				Double weight = Double.valueOf( element.getAttribute( BowLabels.WEIGHT.getLabel() ) );
				String word = element.getAttribute( BowLabels.WORD_NAME.getLabel() );
				
				// Check if some word is here
				for( String wl : replacedWordList ) {
					// If this word is equal
					if( wl.equals( word ) ) {
						// Save the information
						usedWeights = usedWeights + weight;
						foundWords++;
					}
				}
				
				// In any case, save that weight
				totalWeight = totalWeight + weight;				
			 }
		 }
			 
		 
		 // If the founded words are more than half
		 if( foundWords >= (replacedWordList.size()/2) ) {
			 
			 // Save the percent
			 double percent = usedWeights;

			 // If the table percent is appropiate (original 10.0)
			 if( percent >= 10.0 ) {
				 // Create the category and send it
				 Category cat = new Category();
				 
				 // Add the name
				 cat.setTableCategory( synDoc.getDocumentElement().getAttribute( BowLabels.BOW_NAME.getLabel()) );
				 
				 // Add the percentage
				 cat.setTableCategoryPercentage( percent );
				 
				 // Return the category
				 return cat;
			 }
			 // If the minimum is not met
			 else {
				 // Return a null objevt
				 return null;
			 }
		 }
		 // If the minimum is not met
		 else {
			 // Return a null objevt
			 return null;
		 }	 
		 	

	}


    
    
    

    
    /**
     * Method that replaces the splittedf words using the synonyms of the files passed by in the other list.
     * @param synonyms The list of synonyms files.
     * @param splittedName The splitted name to replace.
     * @return The list with the words of the replaced name.
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     */
	private LinkedList<String> replaceWords(LinkedList<String> synonyms, LinkedList<String> splittedName) throws SAXException, IOException, ParserConfigurationException {
		// Prepare a new name
		LinkedList<String> newSplittedName = new LinkedList<String>();
		
		// For each word at the splittedName
		for(String word : splittedName) {
			// Prepare a flag
			boolean replaced = false;
			String replaceFor = "";
			
			// Now go through each file...
			for( String file :  synonyms ) {			
				 // Reference the file
				 DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				 Document synDoc = dbFactory.newDocumentBuilder().parse( new File( file ) );
				 
				 // Normalize it
				 synDoc.getDocumentElement().normalize();
				 
				 // Get the main word of the document
				 String mainWord = synDoc.getDocumentElement().getAttribute(SynonymLabels.MAIN_WORD.getLabel());

				 // Now get the nodes
				 NodeList nodes = synDoc.getElementsByTagName(SynonymLabels.WORD_NAME.getLabel());
				 
				 // For all the nodes...
				 for (int i = 0; i < nodes.getLength(); i++) {
					 // Get the current one
					 Node node = nodes.item(i);
					 
					 // If it is a node
					 if ( node.getNodeType() == Node.ELEMENT_NODE ) {
						 // Get the element
						 Element element = (Element) node;
						 
						 // Get the word
						 String currentWord = element.getAttribute(SynonymLabels.ORIGINAL.getLabel());

						 // Compare with the word
						 if( word.equals( currentWord ) && !replaced ) {
							 // Change the flag
							 replaced = true;
							 
							 // Save the word to change for
							 replaceFor = mainWord;
							 
							 // Break this loop
							 break;
						 }
					 }

				 }
				 				 
			}
			
			 // If the word was replaced and it doesn't exist on the list
			 if( replaced && !newSplittedName.contains(replaceFor) ) {
				 // Add the mainWord to the new list
				 newSplittedName.addLast( replaceFor );
			 }
			 // In the other hand
			 else {
				 // Just save the original word
				 newSplittedName.addLast( word );
			 }
		}
		
		// Return the new name
		return newSplittedName;
	}


			 
			 
			 
	/**
	 * Method that gets all of the synonyms files that are linked to a certain
	 * tablename bow file.
	 * @param bow The bow file to inspect.
	 * @return A list with the name/location of each synonym file linked to the bow.
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	private LinkedList<String> getTableSynonyms(String bow) throws SAXException, IOException, ParserConfigurationException {
		 // Reference the file
		 File graphFile = new File(bow);		 
		 DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		 Document bowDoc = dbFactory.newDocumentBuilder().parse( graphFile );
		 
		 // Normalize it
		 bowDoc.getDocumentElement().normalize();

		 // Now get the nodes
		 NodeList nodes = bowDoc.getElementsByTagName(BowLabels.WORD.getLabel());

		 // Prepare a list
		 LinkedList<String> synonymFiles = new LinkedList<String>();
		 
		 // For all the nodes...
		 for (int i = 0; i < nodes.getLength(); i++) {
			 // Get the current one
			 Node node = nodes.item(i);
			 
			 // If it is a node
			 if ( node.getNodeType() == Node.ELEMENT_NODE ) {
				// Get the element
				Element element = (Element) node;
				  
				// If there is a synonyms file...
				if( element.getAttribute(BowLabels.SYNONYMS.getLabel()).equals("true") ) {
					// Add the file to the list
					synonymFiles.addLast( "files/synonyms/" + element.getAttribute( BowLabels.FILE.getLabel() ) );
				}
			 }
		 }
		 
		 // Return the list
		 return synonymFiles;
	}



	/**
     * Method that get the tablename bows that can be used to classify tablenames, from the
     * ansi standard, on the agent state.
     * @param ansiStandard
     * @return
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     */
	private LinkedList<String> getTableBows(String ansiStandard) throws SAXException, IOException, ParserConfigurationException {
		 // Reference the file
		 File graphFile = new File( ansiStandard );		 
		 DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		 Document graph = dbFactory.newDocumentBuilder().parse( graphFile );
		 
		 // Normalize it
		 graph.getDocumentElement().normalize();

		 // Now get the nodes
		 NodeList nodes = graph.getElementsByTagName(GraphLabels.NODE.getLabel());

		 // Prepare a list
		 LinkedList<String> bowList = new LinkedList<String>();
		 
		 // For all the nodes...
		 for (int i = 0; i < nodes.getLength(); i++) {
			 // Get the current one
			 Node node = nodes.item(i);
			 
			 // If it is a node
			 if ( node.getNodeType() == Node.ELEMENT_NODE ) {
				 // Get the element
				 Element element = (Element) node;
				 
				 // If this is usable...
				 if( element.getAttribute(GraphLabels.USABLE.getLabel()).equals("true") ) {				 
					 // Add the tablename to the list
					 bowList.addLast( "files/bow/" + element.getAttribute( GraphLabels.TABLE_BOW.getLabel() ) );
				 }
			 }
		 }
		 
		 // Return the list
		 return bowList;
	}




}
