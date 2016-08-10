package gui.application;



/**
 * Enumerated class for the routes of the view files.
 * @author Ing. Melina C. Vidoni - 2014
 *
 */
public enum FileLocation {
	LOGO_ICON("/gui/view/img/appicon.png"),
	ROOT_LAYOUT("/gui/view/RootLayout.fxml"),
	INTRO_PANEL("/gui/view/IntroPanel.fxml"),
	BASE_INFO_PANEL("/gui/view/BaseInfoPanel.fxml"),
	WORD_SEPARATION_PANEL("/gui/view/WordSeparationPanel.fxml"),
	PROGRESS_PANEL("/gui/view/ProgressPanel.fxml"),
	CHARTS_PANEL("/gui/view/ChartsPanel.fxml");
	

	
	private String route;

	
	
	
	/**
	 * Default constructor of the class.
	 * @param l A name.
	 */
	FileLocation(String l) {
		route = l;
	}
	
	
	
	
	/**
	 * Method that allows the system to get a enum that matches with the received label.
	 * @param n The label to be compared with.
	 * @return The enum object if it exists, or a null object.
	 */
	public static FileLocation getLabel(String n){
		if(LOGO_ICON.toString().equals(n))
			return LOGO_ICON;
		else if(ROOT_LAYOUT.toString().equals(n))
			return ROOT_LAYOUT;
		else if(INTRO_PANEL.toString().equals(n))
			return INTRO_PANEL;
		else if(BASE_INFO_PANEL.toString().equals(n))
			return BASE_INFO_PANEL;
		else if(WORD_SEPARATION_PANEL.toString().equals(n))
			return WORD_SEPARATION_PANEL;
		else if(PROGRESS_PANEL.toString().equals(n))
			return PROGRESS_PANEL;
		else if(CHARTS_PANEL.toString().equals(n))
			return CHARTS_PANEL;
		return null;
	}
	
	
	
	/**
	 * Method that converts the current enum on a string.
	 */
	public String toString() {
		return route;
	}
	
}
