package database.objects;




/**
 * Instanceable class that represents a category obtained by the agent in regards
 * of a certain table and its columnames.
 * @author Ing. Melina C. Vidoni - 2014.
 *
 */
public class Category {
	private String categoryName;
	private String columnCategory;
	
	private double tableCategoryPercentage;
	private double columnCategoryPercentage;
	private double finalPercentage;
	
	private double bayesProp;
	
	
	
	
	/**
	 * Default constructor of the class.
	 */
	public Category() {
		categoryName = "";
		columnCategory = "";
		tableCategoryPercentage = 0.0;
		columnCategoryPercentage = 0.0;
		bayesProp = 0.0;
	}
	
	
	
	/**
	 * Getter of the name of the category.	
	 * @return The name of the category.
	 */
	public String getTableCategory() {
		return categoryName;
	}
	
	
	/**
	 * Getter for the table belonging percentage.
	 * @return Double number representing this percent.
	 */
	public double getTableCategoryPercentage() {
		return tableCategoryPercentage;
	}
	

	/**
	 * Getter for the final percent of the category.
	 * This comes up after weighting both table and column
	 * belonging %, and operating them together.
	 * @return The final percent of the category.
	 */
	public double getFinalPercentage() {
		return finalPercentage;
	}
	
	
	/**
	 * Getter for the category belonging percent.
	 * @return Double number representing this percent.
	 */
	public double getColumnCategoryPercentage() {
		return columnCategoryPercentage;
	}
	
	
	/**
	 * Getter for the name of the column category.	
	 * @return The name of the column category.
	 */
	public String getColumnCategory() {
		return columnCategory;
	}
	
	
	/**
	 * Setter to set the name of the column category.
	 * @param tc The new name to be saved.
	 */
	public void setTableCategory(String tc) {
		categoryName = tc;
	}

	
	/**
	 * Setter for the percentage obtained after the table
	 * categorization procedure.
	 * @param tcp The percent to be stored.
	 */
	public void setTableCategoryPercentage(double tcp) {
		tableCategoryPercentage = tcp;
	}
	
	
	/**
	 * Setter for the percentage obtained after the column
	 * categorization procedure.
	 * @param tcp The percent to be stored.
	 */
	public void setColumnCategoryPercentage(double ccp) {
		columnCategoryPercentage = ccp;
	}


	/**
	 * Setter to save the final percent obtained after operating
	 * the individual percents.
	 * @param fp The new percent to be stored.
	 */
	public void setFinalPercentage(double fp) {
		finalPercentage = fp;
	}


	/**
	 * Setter for the name of the column category.
	 * @param cc The new name to be stored.
	 */
	public void setColumnCategory(String cc) {
		columnCategory = cc;
	}

	
	/**
	 * Getter for the bayes proportion obtained.
	 * @return The double value to get.
	 */
	public double getBayesProp() {
		return bayesProp;
	}

	
	/**
	 * Setter to save the value of a bayes proportion calculated.
	 * @param bp The value to be saved.
	 */
	public void setBayesProp(double bp) {
		bayesProp = bp;
	}




}
