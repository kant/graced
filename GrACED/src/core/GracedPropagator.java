package core;

import java.io.IOException;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import xmlfiles.XMLWriterReader;
import core.objects.GraphRelation;
import core.objects.ParentGraphNode;
import database.objects.AnsiClass;
import database.objects.CategorizedTable;
import database.objects.Category;
import database.objects.TableName;



/**
 * Class that contains all the attributes and logic to make the information
 * distribution analysis, reading data from the basic classification, and
 * formatting it to the distribution data.
 * @author Ing. Melina C. Vidoni - 2014
 *
 */
public class GracedPropagator {
	private LinkedList<TableName> tablenameList;
	private static LinkedList<AnsiClass> classesList;
	private LinkedList<GraphRelation> graphRelations;
	private int current;
	
	
	
	/**
	 * Default constructor of the class.
	 */
	public GracedPropagator() {
		try {
			// Initialize local attributes
			graphRelations = new LinkedList<GraphRelation>();
			tablenameList = XMLWriterReader.getClassifiedTables();
			classesList = XMLWriterReader.getAnsiClasses();
			current = 0;
			
			// For each class of the ansi classes, we get the graph relations
			for( AnsiClass ac : classesList ) {
				// Add the class
				graphRelations.add( XMLWriterReader.getRelationInformationOf(ac.getName()) );
			}
			
		} 
		catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	/**
	 * Method to check the condition if the propagator
	 * still has work to do.
	 * @return true if the lenght of the tablename list is
	 * still bigger than zero.
	 */
	public boolean hasPendingWork() {
		return tablenameList.size() > 0;
	}


	

	/**
	 * Getter for the first item on the list, that should be the
	 * one currently being analized.
	 * @return the first element on the tablename list.
	 */
	public TableName getTablename() {
		return tablenameList.getFirst();
	}


	

	/**
	 * Getter for the current number of evaluated tables.
	 * @return The number of evaluated tables.
	 */
	public int getCurrent() {
		return current;
	}


	

	/**
	 * Return the list for the ansi classes.
	 * @return The list with the finished ansi classes.
	 */
	public LinkedList<AnsiClass> getAnsiClassList() {
		return classesList;
	}


	

	/**
	 * Method that has the main logic for the distribution of information.
	 */
	public void distribute() {
		// First, get the table and update current
		TableName table = tablenameList.removeFirst();
		current++;
		
		// STEP 1: Get base category for the table
		GraphRelation category = getBaseCategory(table);

		// STEP 2: Update base ansi class
		updateBaseAnsiClass(table, category.getName());
		
		
		// STEP 3: Update uptree
		updateUptree(table, category);
	}



	
	/**
	 * Method that starts the uptree update, using the information it got
	 * as a parameter. It searchs for the category information.
	 * @param table The table that is being analized.
	 * @param graphRel The base category.
	 */
	private void updateUptree(TableName table, GraphRelation graphRel) {
		// Make a flag...
		Category categ = null;
		
		// Search the category
		for(Category c : table.getCategories() ) {
			// If this is the one...
			if( c.getTableCategory().equals( graphRel.getName() ) ) {
				// Save it
				categ = c; 
			}
		}
		
		/*
		 * IF THE CATEGORY HAS ONLY ONE PARENT CATEGORY
		 */
		if( graphRel.getParentList().size() == 1 ) {
			// Just call the recursive updating
			updateCategory( graphRel.getParentList().getFirst(), categ.getFinalPercentage() );		
		}
		/*
		 * IN THE CASE IT HAS MORE THAN ONE, WE NEED TO PICK ONE
		 */
		else if( graphRel.getParentList().size() > 1 ){
			// TODO MORE THAN ONE PARENT CATEGORY
		}
		/*
		 * IN THIS CASE, THERE IS NO PARENT -> ROOT!
		 * And so, we stop!
		 */
	}

	
	

	/**
	 * Recursive method that updates uptree with the information of the table
	 * and the propagation of the table quantity.
	 * @param parent The first parent node to be used.
	 * @param finalPercentage the percentage being expanded.
	 */
	private void updateCategory(ParentGraphNode parent, double finalPercentage) {
		// Get the GraphRelation item
		GraphRelation gr = getCategoryByName( parent.getName() );
		
		// Get the ANSI element
		AnsiClass ansiClass = getAnsiByName( parent.getName() );
		
		// Update the Ansi Class
		ansiClass.setFlatTableQty( ansiClass.getFlatTableQty() + 1 );
		ansiClass.addPercentSum( finalPercentage );
		if( ansiClass.getMaxPercent() < finalPercentage )
			ansiClass.setMaxPercent(finalPercentage);
		if( ansiClass.getMinPercent() > finalPercentage )
			ansiClass.setMinPercent(finalPercentage);
		
		// If the class has only one parent
		if( gr.getParentList().size() == 1 ) {
			// Recursive method emporwerment
			updateCategory( gr.getParentList().get(0), finalPercentage );
		}
		// If it has more than one parent...
		else if( gr.getParentList().size() > 1 ) {
			// TODO PROPAGATE UPTREE USING BAYES (MORE THAN 1 PARENT)
		}
		// In the case it has no parents (it is the root!), it stops!
	}



	/**
	 * Method that updates the base class of the classification, in order
	 * to get the information updated.
	 * @param table The table that is being classified.
	 * @param catName The category name classified into.
	 */
	private void updateBaseAnsiClass(TableName table, String catName) {
		// Make a flag...
		Category categ = null;
		
		// Search the category
		for(Category c : table.getCategories() ) {
			// If this is the one...
			if( c.getTableCategory().equals( catName ) ) {
				// Save it
				categ = c; 
			}
		}
		
		
		// Find the ANSI class
		AnsiClass ansiClass = getAnsiByName(catName);

		// If true, start saving...
		ansiClass.setFlatTableQty( ansiClass.getFlatTableQty() + 1 );
		ansiClass.addPercentSum( categ.getFinalPercentage() );
		ansiClass.addCatTable( new CategorizedTable(table.getName(), categ.getFinalPercentage()) );
				
		// Check if max
		if( ansiClass.getMaxPercent() < categ.getFinalPercentage() )
			ansiClass.setMaxPercent( categ.getFinalPercentage() );
				
		// Check if min
		if( ansiClass.getMinPercent() > categ.getFinalPercentage() )
			ansiClass.setMinPercent( categ.getFinalPercentage() );
	}


	
	
	
	

	/**
	 * Method that gets the base category for a table.
	 * @param table The table to be evaluated.
	 * @return The graph relation version of the category that was selected.
	 */
	private GraphRelation getBaseCategory(TableName table) {
		/*
		 * IF THE TABLE HAS ONLY ONE CATEGORY...
		 */
		if( table.getCategories().size() == 1 ) {
			// We are done! Find it on the list...
			return getCategoryByName( table.getCategories().get(0).getTableCategory() );
		}
		/*
		 * IN THE OTHER HAND, IT HAS MORE THAN ONE
		 */
		else {
			// Make a flag
			Category flagCat = null;
			
			// Now iterate checking the values...
			for( Category testCat : table.getCategories() ) {	
				// Compare. If testCat is bigger (Original: +10.0 sum!)...
				if( testCat.getFinalPercentage() 
								> (flagCat!=null ? (flagCat.getFinalPercentage() + 2.0) : 0.0) ) {
					// Then save testCat!
					flagCat = testCat;
				}
			}
				
			/*
			 * IF THERE WAS A BIGGER DIFFERENCE, WE HAVE A WINNER
			 */
			if( flagCat != null ) {
				// So go get the winner in relation version
				return getCategoryByName( flagCat.getTableCategory() );
			}
			
			/*
			 * IN THE OTHER HAND, WE ARE IN THE MOST COMPLICATED CASE
			 */
			else {
				// Here we have many categories, and each of them may have one
				// ...or more parent categories. So we need to compare them all
				// ...ignoring the fact of how many parents they may have.
				// First, we need to get them in GraphRelation mode.
				LinkedList<GraphRelation> tempGraphList = new LinkedList<GraphRelation>();
				for( Category c : table.getCategories() ) {
					// Get the graph
					GraphRelation gr = getCategoryByName(c.getTableCategory());
					
					// Now that we have the graphs we must add the bayes proportion 
					// ...to each parent because we don't know how many parents 
					// ...each of them has.
					for( ParentGraphNode pgn : gr.getParentList() ) {
						// FIXME Calculate bayes ~ Evaluate where is the % going
						double bayes = ( pgn.getPropX() * pgn.getPropYgivenX() ) / ( c.getFinalPercentage() / 100.0) ;
						
						// Set the bayes on the parent
						pgn.setPropXgivenYforTable(bayes);
					}
					
					// Add this graph to the list
					tempGraphList.add(gr);
				}
				
				
				// Now we have our list full, and we make a flag
				GraphRelation flagGR = null;
				
				// We go through each of the list
				for( GraphRelation gr : tempGraphList ) {
					// Evaluate, if current gr's bayes is bigger
					if( gr.getBiggerBayes() > (flagGR!=null ? flagGR.getBiggerBayes() : 0.0) ) {
						// If it is, save this
						flagGR = gr;
					}
					
				}

				// Now, flagGR shouldn't be null, and this should be our base category		
				return flagGR;
			}
		}
	}


	
	

	
	
	/**
	 * Method that searchs a given category on the graph relation list
	 * to get the mandatory information.
	 * @param category The name of the category to be searched.
	 * @return The GraphRelation object of that category.
	 */
	private GraphRelation getCategoryByName(String category) {
		// Go through each category
		for(GraphRelation gr : graphRelations) {
			// If this is it
			if( gr.getName().equals( category ) ) {
				// Return this, and stop iterating
				return gr;
			}
		}
		
		// Else, return null
		return null;
	}

	
	
	
	/**
	 * Method that searchs a given category on the ansi class list
	 * to get the mandatory information.
	 * @param category The name of the category to be searched.
	 * @return The AnsiClass object of that category.
	 */
	public static AnsiClass getAnsiByName(String catName) {
		// Go through each class
		for( AnsiClass ansic : classesList ) {
			// Check if this is the one we are looking for
			if( ansic.getName().equals( catName ) ) {
				return ansic;
			}
		}
		return null;
	}
	
}