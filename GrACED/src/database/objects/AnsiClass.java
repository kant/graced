package database.objects;

import java.util.LinkedList;

import xmlfiles.XMLWriterReader;




/**
 * Class to keep up with the information about an Ansi Class, during the
 * information distribution analysis of the inteligent agent.
 * @author Ing. Melina C. Vidoni ~ 2014
 *
 */
public class AnsiClass {
	private LinkedList<CategorizedTable> catTableList;
	private String name;
	private int flatTableQty;
	private double defaultProportion;
	private double minPercent;
	private double maxPercent;
	private double percentSum;

	
	/**
	 * Default empty constructor of the class.
	 */
	public AnsiClass() { 
		catTableList = new LinkedList<CategorizedTable>();
		flatTableQty = 0;
		maxPercent = 0.0;
		minPercent = 100.0;
		percentSum = 0.0;
		name = "";
		defaultProportion = 0.0;
	}


	
	/**
	 * Getter for the name of the Ansi Class.
	 * @return the name of the class.
	 */
	public String getName() {
		return name;
	}


	/**
	 * Setter for the name of the class.
	 * @param n The new name to save on the class.
	 */
	public void setName(String n) {
		name = n;
	}


	/**
	 * Getter to know the quantity of table that were
	 * categorized on this class, or that were distributed to this
	 * particular class, from a child class.
	 * @return An integer number contabilizing the tables.
	 */
	public int getFlatTableQty() {
		return flatTableQty;
	}


	/**
	 * Setter to set a new value to the table quantity.
	 * @param tableQty The new value to be set.
	 */
	public void setFlatTableQty(int tableQty) {
		flatTableQty = tableQty;
	}


	/**
	 * Getter to get the minimun percent that a table got
	 * at being categorized on this class.
	 * @return A double number representing this percent.
	 */
	public double getMinPercent() {
		return minPercent;
	}


	
	/**
	 * Setter to add a new value as the minimun percent for this
	 * category.
	 * @param min The new double value to be saved.
	 */
	public void setMinPercent(double min) {
		minPercent = min;
	}


	/**
	 * Getter to get the maximun percent tthat a table got at
	 * being categorized on this class.
	 * @return A double number representing this percent.
	 */
	public double getMaxPercent() {
		return maxPercent;
	}


	/**
	 * Setter to set a new maximum value to be saved.
	 * @param max The new value to save.
	 */
	public void setMaxPercent(double max) {
		maxPercent = max;
	}


	/**
	 * Method that calculates the average percent of classification in
	 * this category, by dividing the percentSum by the flat table qty.
	 * @return The average percent of table categorization	 */
	public double getAveragePercent() {
		return percentSum / flatTableQty;
	}


	/**
	 * Adder to add a new percent to the percent sum.
	 * @param percent The new percent to be added.
	 */
	public void addPercentSum(double percent) {
		percentSum = percentSum + percent ;
	}

	
	/**
	 * Getter to get the default proportion for this category
	 * which was calculated in the moment of making the knowledge
	 * base of the agent. This serves as a point of comparison of
	 * the obtained proportion, to know if the distribution of
	 * information of the database is near the one considered as
	 * a good proportion.
	 * @return The default proportion value for this category.
	 */
	public double getDefaultProportion() {
		return defaultProportion;
	}

	
	/**
	 * Setter to set the default proportion for this category.
	 * @param dp A double number representing a proportion.
	 */
	public void setDefaultProportion(double dp) {
		defaultProportion = dp;
	}
	

	/**
	 * Method to calculate the obtained proportion. This means that
	 * the method calculates the proportion of tables that belongs to
	 * this category, on the current ERPDB that is being analized by
	 * GrACED, and returns this value.
	 * @return The proportion obtained by this category during the analysis
	 * of the database by GrACED.
	 */
	public double getObtainedProportion() {
		return (1.0 * flatTableQty) / (1.0 * XMLWriterReader.getTotalTableQty()) ;
	}

	
	
	
	/**
	 * Getter for the list of tables that were natively categorized in
	 * the category represented in the current instance of GraphRelation.
	 * @return The whole list of CategorizedTables.
	 */
	public LinkedList<CategorizedTable> getCatTableList() {
		return catTableList;
	}

	
	/**
	 * Setter to add a new categorized table to the end of the table list.
	 * @param table The new table to be added.
	 */
	public void addCatTable(CategorizedTable table) {
		catTableList.addLast(table);
	}
	
}
