package gui.view.model;

import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;



/**
 * A model with the information for a barchart for the table that is saved
 * as an attribute on each instance.
 * @author Ing. Melina C. Vidoni - 2014
 *
 */
public class BarChartModel {
	private String tablename;
	private XYChart.Series<String, Double> tableCategorySeries;
	private XYChart.Series<String, Double> columnCategorySeries;
	private XYChart.Series<String, Double> finalCategorySeries;
	
	
	
	/**
	 * Default constructor of the class.
	 * @param tn The name of the tablename.
	 */
	public BarChartModel(String tn) {
		// Set the tablename
		tablename = tn;
		
		// Set the categories
		tableCategorySeries = new XYChart.Series<>();
		tableCategorySeries.setName("% for Tablename");
		
		columnCategorySeries = new XYChart.Series<>();
		columnCategorySeries.setName("% for Columname");
		
		finalCategorySeries = new XYChart.Series<>();
		finalCategorySeries.setName("Average %");
	}


	/**
	 * A method to get the name of the table.
	 * @return the name of the table of this info.
	 */
	public String getTable() {
		return tablename;
	}


	
	/**
	 * A method to add information to the table clasification series.
	 * @param c Name of the category.
	 * @param perc Percentage obtained.
	 */
	public void addInfoTableSeries( String c, Double perc ) {
		// Add the information
		tableCategorySeries.getData().add( new Data<String, Double>(c, perc) );
	}

	
	
	
	/**
	 * A method to add information to the column classification series.
	 * @param c Name of the category.
	 * @param perc Percentage obtained.
	 */
	public void addInfoColumnSeries( String c, Double perc ) {
		// Add the information
		columnCategorySeries.getData().add( new Data<String, Double>(c, perc) );
	}
	
	
	
	/**
	 * A method to add information to the final classification series.
	 * @param c Name of the category.
	 * @param perc Percentage obtained.
	 */
	public void addInfoFinalSeries( String c, Double perc ) {
		// Add the information
		finalCategorySeries.getData().add( new Data<String, Double>(c, perc) );
	}
	
	
	
	
	/**
	 * A method to get the series for the table clasification.
	 * @return The XY series.
	 */
	@SuppressWarnings("rawtypes")
	public XYChart.Series getTableSeries() {
		return tableCategorySeries;
	}
	
	
	
	
	/**
	 * A method to get the series for the column classification.
	 * @return The XY series.
	 */
	@SuppressWarnings("rawtypes")
	public XYChart.Series getColumnSeries() {
		return columnCategorySeries;
	}
	
	
	/**
	 * A method to get the series for the final classification.
	 * @return The XY series.
	 */
	@SuppressWarnings("rawtypes")
	public XYChart.Series getFinalSeries() {
		return finalCategorySeries;
	}
}
