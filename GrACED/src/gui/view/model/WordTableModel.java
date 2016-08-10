package gui.view.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;




/**
 * Model for the table with the name and counted apparitions of a certain
 * word, to be used on the model of the words tables, in the Estimated
 * Hyphenation Correction chart.
 * @author Ing. Melina C. Vidoni - 2014
 *
 */
public class WordTableModel {
	private SimpleStringProperty word;
	private SimpleIntegerProperty count;
	
	
	
	
	
	/**
	 * Default constructor of the class.
	 * @param w The reference to the word to be saved.
	 * @param c The count of times this word has appeared.
	 */
	public WordTableModel(String w) {
		word = new SimpleStringProperty(w);
		count = new SimpleIntegerProperty(1);
	}


	
	

	/**
	 * Getter for the word saved as a simple string property.
	 * @return The word in string format.
	 */
	public String getWord() {
		return word.get();
	}


	

	/**
	 * Setter for the word that will be saved on this node.
	 * @param w The word in string format.
	 */
	public void setWord(String w) {
		word.set(w);
	}


	

	/**
	 * Getter for the count of times that this word has appeared.
	 * @return The count in integer format.
	 */
	public int getCount() {
		return count.get();
	}



	
	/**
	 * Setter for the count, directly increasing the current number
	 * by one.
	 * @param count
	 */
	public void setCount() {
		count.set( count.get() + 1 );
	}





}
