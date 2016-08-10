package database.objects;

import java.util.LinkedList;




/**
 * Instanciable class that represents a tablename in the agent domain.
 * @author Ing. Melina C. Vidoni - 2014
 *
 */
public class TableName {
	private String originalName;
	private LinkedList<String> splittedName;
	private LinkedList<ColumnName> columnames;
	private LinkedList<Category> categories;
	
	
	
	/**
	 * Default constructor of the class.
	 * @param n Name of the tablename.
	 */
	public TableName(String n) {
		originalName = n;
		columnames = new LinkedList<ColumnName>();
		categories = new LinkedList<Category>();
		splittedName = new LinkedList<String>();
	}
	
	
	
	/**
	 * Getter for the name of the table.
	 * @return The name of the table.
	 */
	public String getName() {
		return originalName;
	}
	
	
	
	/**
	 * Method that adds a category to the list.
	 * @param c The category to be added.
	 */
	public void addCategory(Category c) {
		// Add the category
		categories.add( c );
	}
	
	
	/**
	 * Method that adds a columname to the list.
	 * @param name Columname to be added.
	 */
	public void addColumname(String name) {
		// Add the columname
		columnames.add( new ColumnName(name) );
	}
	
	
	
	
	/**
	 * Method that returns all the available column names.
	 * @return all the columns names in object format.
	 */
	public LinkedList<ColumnName> getColumnames() {
		return columnames;
	}
	
	
	
	
	/**
	 * Method that returns all the available categories.
	 * @return all the available categories in object format.
	 */
	public LinkedList<Category> getCategories() {
		return categories;
	}


	
	/**
	 * Method that adds all the columns of a list, to the current list.
	 * @param columns The columns to be added.
	 */
	public void setColumnsNames(LinkedList<ColumnName> columns) {
		columnames.addAll( columns );
	}
	
	
	
	/**
	 * Method that adds all the splitted name of the table, into the
	 * current list.
	 * @param sn The splitted name.
	 */
	public void addSplittedName(LinkedList<String> sn) {
		splittedName.addAll(sn);
	}

	
	
	/**
	 * Getter to get the splitted name of this tablename.
	 * @return the list with all the words of the tablename.
	 */
	public LinkedList<String> getSplittedName() {
		return splittedName;
	}


}
