package database.enums;

import java.util.LinkedList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;




/**
 * Enumerated class containing all characters available to use
 * in the word separation.
 * @author Ing. Melina C. Vidoni
 *
 */
public enum SeparationChars {
	POINT("."),
	SPACE(" "),
	DASH("_"),
	HYPHEN("-");
	
	private String character;
	
	



	/**
	 * Default constructor of the class.
	 * @param c
	 */
	SeparationChars(String c) {
		character = c;
	}
	
	
	
	
	/**
	 * A method to get an observable list, ready to fill a combobox.
	 * @return The filled observable list.
	 */
	public static ObservableList<SeparationChars> getAll() {
		// Create the list
		List<SeparationChars> originalList = new LinkedList<SeparationChars>();
		originalList.add(POINT);
		originalList.add(SPACE);
		originalList.add(DASH);
		originalList.add(HYPHEN);
		
		// Change it to observable
		ObservableList<SeparationChars> list = FXCollections.observableList(originalList);
		
		// Return
		return list;
	}
	
	


	
	/**
	 * Method that allows the system to get a enum that matches with the received label.
	 * @param n The label to be compared with.
	 * @return The enum object if it exists, or a null object.
	 */
	public static SeparationChars getLabel(String n){
		if(POINT.toString().equals(n))
			return POINT;
		else if(SPACE.toString().equals(n))
			return SPACE;
		else if(DASH.toString().equals(n))
			return DASH;
		else if(HYPHEN.toString().equals(n))
			return HYPHEN;
		return null;
	}
	
	
	
	/**
	 * Method that converts the sequence to string.
	 */
	public String toString() {
		return character;
	}



	

}
