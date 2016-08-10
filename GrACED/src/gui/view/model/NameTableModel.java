package gui.view.model;

import javafx.beans.property.SimpleStringProperty;




/**
 * Model for the table with the names of tables, for the main pie chart.
 * @author Ing. Melina C. Vidoni - 2014
 *
 */
public class NameTableModel {
	private SimpleStringProperty name;
	
	
	
	/**
	 * Default constructor of the class.
	 * @param n The name to be added to the property.
	 */
	public NameTableModel(String n) {
		name = new SimpleStringProperty(n);
	}



	/**
	 * Getter for the name saved in this instance.
	 * @return the wrapped string on the property.
	 */
	public String getName() {
		return name.get();
	}



	/**
	 * Setter for the string property in this instance.
	 * @param n the string to be wrapped on the property.
	 */
	public void setName(String n) {
		name.set(n);
	}
	
	

}
