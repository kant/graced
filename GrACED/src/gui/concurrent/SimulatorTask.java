package gui.concurrent;





import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import javafx.concurrent.Task;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import xmlfiles.ResultsGenerator;
import xmlfiles.XMLWriterReader;
import core.DatabaseEnvironment;
import core.GracedAgent;
import core.GracedPropagator;
import core.GracedSimulator;
import database.DatabaseException;
import database.objects.AnsiClass;






/**
 * First task of the process, that shows only the generation of the agent.
 * @author Ing. Melina C. Vidoni
 *
 */
public class SimulatorTask extends Task<Object> {

	/**
	 * Default constructor of the class.
	 */
	public SimulatorTask() { }

	
	


	/**
	 * A method called when the task starts.
	 */
	@Override
	protected Object call() {	
		try {
			// Call first step
			GracedSimulator graced = step1();
			
			// Call second step
			step2(graced);
			
			// Call third step
			LinkedList<AnsiClass> ansiClass = step3();
			
			// Prepare interfaces
			step4(ansiClass);
			
			// Change title
			updateTitle("5");
			
			return null;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	

	/**
	 * Method that executes the first step of the progress in the agent logic.
	 * @return an instance of the simulator.
	 * @throws DatabaseException
	 * @throws SQLException
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	private GracedSimulator step1() throws DatabaseException, SQLException, ParserConfigurationException, TransformerException {
		// Set the step
		updateTitle("1");
		updateMessage("Waking up GrACED");
		updateProgress(0, 100);
		
		// PART 1: Prepare Environment
		DatabaseEnvironment environment = new DatabaseEnvironment();
		environment.getTablenames();
		updateProgress(25, 100);
		
		// PART 2: Create the Agent
		GracedAgent agent = new GracedAgent();
		updateProgress(50, 100);
		
		// PART 3: Create Agent Simulator
		GracedSimulator simulator = new GracedSimulator(environment, agent);
		updateProgress(75, 100);
		
		
		// PART 4: Prepare the files
		XMLWriterReader.createFiles();
		updateProgress(100, 100);
		
		// Return
		return simulator;
	}


	
	
	

	/**
	 * A method with the second step of the task, which is to classify
	 * the database tables.
	 * @param graced
	 * @throws InterruptedException 
	 */
	private void step2(GracedSimulator graced) throws InterruptedException {
		// Change the main information
		updateTitle("2");
	
		// Get some variables
		int current = 0;
		int total = graced.getTotal();		
		
		do {
			// Update the progress
			updateProgress(current, total);
			updateMessage( graced.getTablename() );
			
			// Execute an action
			graced.start();
			
			// Get the new current
			current = graced.getCurrent();
		}
		while( !graced.agentSucceeded(null) );
		
	}
	
	
	
	

	
	
	/**
	 * A method that evaluates each table classification, and 
	 * then generates proportion stadistics based on that.
	 * @return 
	 */
	private LinkedList<AnsiClass> step3() {
		// Set the step
		updateTitle("3");
		updateMessage("Propagating...");
		
		// Get some variables
		int current = 0;
		int total = XMLWriterReader.getTotalClassifiedTables();	
		
		// Create the propagator
		GracedPropagator propagator = new GracedPropagator();
		
		// While we have work to do...
		while( propagator.hasPendingWork() ) {
			// Update the progress
			updateProgress(current, total);
			updateMessage( propagator.getTablename().getName() );
			
			// We distribute the information of the current table
			propagator.distribute();
			
			// Update the current
			current = propagator.getCurrent();
		}
			
		// Return the FINISHED ansi class list
		return propagator.getAnsiClassList();
	}
	
	
	
	
	
	/**
	 * A method that reads the final XML files, generating the charts that will be shown.
	 * @param ansiClass 
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws TransformerException 
	 */
	private void step4(LinkedList<AnsiClass> ansiClass) throws SAXException, IOException, ParserConfigurationException, TransformerException {
		// Set the step
		updateTitle("4");
		
		// Prepare a generator
		ResultsGenerator generator = ResultsGenerator.getInstance();
		
		/*
		 * MAIN PIE INFO
		 */
		updateMessage("Generating Distribution Chart...");
		updateProgress(0, 100);
		generator.generateMainPie();
		

        /*
         * SECOND PIE INFO 
         */
		updateMessage("Generating Types Pie...");
		updateProgress(16, 100);
		generator.generateTypesPie();
		XMLWriterReader.writeTypesXML();
		
		

		/*
		 * INFO DISTRIBUTION INFOGRAPHICS
		 */
		updateMessage("Generating Distribution Infographics...");
		updateProgress(33, 100);
		generator.generateInfographics(ansiClass);
		XMLWriterReader.writeDistributedInfo(ansiClass);
		
		
		
		/*
		 * BAR CHART AND TREE VIEW 
		 */
		updateMessage("Generating tree view...");
		updateProgress(50,100);
		generator.generateTreeView();
		
		updateMessage("Generating Bar Charts...");
		updateProgress(66, 100);
		generator.generateBarCharts();
		
		
		
		/*
		 * HYPHENATION INTERFACE
		 */
		updateMessage("Generation Hyphenation Charts...");
		updateProgress(83, 100);
		generator.generateHyphenationChart();
		
		
		/*
		 * FINISH
		 */
		updateProgress(100, 100);
	}


	
	
	
	
	
}
