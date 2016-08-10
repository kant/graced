package database.objects;

import java.util.LinkedList;





/**
 * Instanciable class that represents a column-name of a table.
 * @author Ing. Melina C. Vidoni - 2014
 *
 */
public class ColumnName {
	private String name;
	private LinkedList<String> splittedName;
	private boolean classified;
	private String synonym;
	
	
	
	/**
	 * Default constructor of the class.
	 * @param n Name of the columname.
	 */
	public ColumnName(String n) {
		name = n;
		splittedName = new LinkedList<String>();
	}



	/**
	 * Getter for the column name.
	 * @return the name of the column.
	 */
	public String getName() {
		return name;
	}



	/**
	 * Getter to know if a columname was already classified or not.
	 * @return @true if it was classified, or @false in the other hand.
	 */
	public boolean isClassified() {
		return classified;
	}


	/**
	 * Setter to set a new classified state.
	 * @param c The state to be setted.
	 */
	public void setClassified(boolean c) {
		classified = c;
	}



	/**
	 * Getter to get the synonym of the word.
	 * @return the current synonym.
	 */
	public String getSynonym() {
		return synonym;
	}


	
	/**
	 * Setter for the synonym of the words.
	 * @param s the synonym to be setted.
	 */
	public void setSynonym(String s) {
		synonym = s;
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
	 * Method that allows to get all the splitted name of the column.
	 * @return The name, splitted, on list format.
	 */
	public LinkedList<String> getSplittedName() {
		return splittedName;
	}
}
