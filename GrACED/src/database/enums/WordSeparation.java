package database.enums;

import java.util.LinkedList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



/**
 * Enumerated class for all the casings allowed on the system.
 * @author Ing. Melina C. Vidoni - 2014
 *
 */
public enum WordSeparation {
	PASCAL_CASE("Pascal Casing", "i.e. WordSeparation"),
	CAMEL_CASE("Camel Casing", "i.e. wordSeparation"),
	SPECIAL_CHARACTER("Special Character", "i.e. word_separation"),
	PREFIX_CASING("Prefix and Casing", "i.e. a_wordSeparation");
	
	
	private String showName;
	private String example;


	
	

	/**
	 * Default constructor of the class.
	 * @param n
	 * @param e
	 */
	WordSeparation (String n, String e) {
		showName = n;
		example = e;
	}
	

	
	/**
	 * A method to get an observable list, ready to fill a combobox.
	 * @return The filled observable list.
	 */
	public static ObservableList<WordSeparation> getAll() {
		// Create the list
		List<WordSeparation> originalList = new LinkedList<WordSeparation>();
		originalList.add(PASCAL_CASE);
		originalList.add(CAMEL_CASE);
		originalList.add(SPECIAL_CHARACTER);
		
		// Change it to observable
		ObservableList<WordSeparation> list = FXCollections.observableList(originalList);
		
		// Return
		return list;
	}
	
	
	
	
	/**
	 * Getter method for the name of the database.
	 * @return The name of the database.
	 */
	public String getName() {
		return showName;
	}
	
	
	/**
	 * Getter method for the example information.
	 * @return The example string.
	 */
	public String getExample() {
		return example;
	}
	
	

	
	/**
	 * Method that allows the system to get a enum that matches with the received label.
	 * @param n The label to be compared with.
	 * @return The enum object if it exists, or a null object.
	 */
	public static WordSeparation getLabel(String n){
		if(PASCAL_CASE.getName().equals(n))
			return PASCAL_CASE;
		else if(CAMEL_CASE.getName().equals(n))
			return CAMEL_CASE;
		else if(SPECIAL_CHARACTER.getName().equals(n))
			return SPECIAL_CHARACTER;
		else if(PREFIX_CASING.getName().equals(n))
			return PREFIX_CASING;
		return null;
	}
	
	
	
	/**
	 * Method that converts the sequence to string.
	 */
	public String toString() {
		return getName();
	}


	
}
