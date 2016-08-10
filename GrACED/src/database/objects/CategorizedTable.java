package database.objects;

import java.text.DecimalFormat;





/**
 * Class to save the information of a table on the category,
 * that will also be printed in the tree view model of the
 * final chart view.
 * @author Ing. Melina C. Vidoni ~ 2014
 *
 */
public class CategorizedTable {
	private String name;
	private double finalPercent;
	
	
	
	/**
	 * Default constructor of the class.
	 * @param n Name of the table
	 * @param fp Final percent achieved.
	 */
	public CategorizedTable(String n, double fp) {
		name = n;
		finalPercent = fp;
	}
	
	
	/**
	 * Method to convert the instance to string.
	 */
	@Override
	public String toString() {
		// Get the format
		DecimalFormat df = new DecimalFormat("00.000");
		
		// Return the string
		return name + ": " + df.format(finalPercent) + "%";
	}



	/**
	 * Getter for the name of the table.
	 * @return The name of the tabled saved on the instance.
	 */
	public String getName() {
		return name;
	}

	
	/**
	 * Setter for the name of the table.
	 * @param n Name to be setted in the table.
	 */
	public void setName(String n) {
		name = n;
	}

	
	/**
	 * Getter for the final percent of the table.
	 * @return A double number, representing the saved final
	 * percent of the table.
	 */
	public double getFinalPercent() {
		return finalPercent;
	}

	
	/**
	 * Setter to add a custom final percent to the table.
	 * @param fp The double value to be saved.
	 */
	public void setFinalPercent(double fp) {
		finalPercent = fp;
	}
	
	

}
