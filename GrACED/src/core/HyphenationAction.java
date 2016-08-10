package core;

import java.io.IOException;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import xmlfiles.XMLWriterReader;
import database.objects.ColumnName;
import frsf.cidisi.faia.agent.reactive.ReactiveAction;
import frsf.cidisi.faia.state.AgentState;
import frsf.cidisi.faia.state.EnvironmentState;





/**
 * Action for evalauting the hyphenation of the words in the tables
 * and columns names, for each step of the agent.
 * Implements @see ReactiveAction from FAIA framework.
 * @author Ing. Melina C. Vidoni - 2014
 *
 */
public class HyphenationAction extends ReactiveAction {
	// TODO CHANGE THE WORD LENGTH TO CONSIDER IT BADLY SEPARATED
	private static int WORD_LENGTH = 7;

	
	/**
	 * Default constructor of the class.
	 */
	public HyphenationAction() { }
	
	
	
	
    /**
     * This method updates the real state of the agent and the environment.
     * 
     * @param ast This is the agent's state to be updated.-
     * @param est This is the agent's environment to be updated.-
     */
	@Override
	public EnvironmentState execute(AgentState ast, EnvironmentState est) {
		try {
			// Convert the Agent State
			GracedAgentState gas = (GracedAgentState) ast;
			
			// Do the evaluation both for table and columname
			evaluateTablename( gas.getCurrentTablename().getSplittedName() );
			evaluateColumnames( gas.getCurrentTablename().getColumnames() ); 
			
		} 
		catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Mandatory Return
		return null;
	}

	
	


	/**
	 * Method that evaluate the separated name of the table, and using the selected
	 * word length, decides if it is potentially good separated or badly separated.
	 * @param splittedName The table name, already separated in words, using the proposed
	 * user-selected naming convention.
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	private void evaluateTablename(LinkedList<String> splittedName) throws SAXException, IOException, ParserConfigurationException, TransformerException {
		// For each for of the splitted name...
		for(String word : splittedName){
			// Compare the length
			if( word.length() <= WORD_LENGTH ) {
				// Save it as correct
				XMLWriterReader.saveTableWord('G', word);
			}
			else {
				// Save it as incorrect
				XMLWriterReader.saveTableWord('B', word);
			}
		}
	}

	
	
	
	/**
	 * Method that evaluates all of the names of the columns of certaintable, the gets
	 * the splitted name, and decides if each of them are potentially good or badly
	 * seoarated.
	 * @param columnames The list of perceived columnames of certain table.
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	private void evaluateColumnames(LinkedList<ColumnName> columnames) throws SAXException, IOException, ParserConfigurationException, TransformerException {
		// For each column...
		for(ColumnName column : columnames) {
			// Also, for each splitted word...
			for(String word : column.getSplittedName()) {
				// Compare the length
				if( word.length() <= WORD_LENGTH ) {
					// Save it as correct
					XMLWriterReader.saveColumnWord('G', word);
				}
				else {
					// Save it as incorrect
					XMLWriterReader.saveColumnWord('B', word);
				}
			}
		}
	}
	
	
	


	/**
     * The string representation of the action is used by some components of
     * the framework, like LatexOutput.
     * It's very important to set a correct representation of the actions,
     * particularly when using knowledge based agent. Look at the
     * CalculusAction.toLogicName() method. The result of this one is lower
     * cased, and it's supposed you are using this one in your prolog file
     * (.pl file).
     */
	@Override
	public String toString() {
		return "Hyphenation Action";
	}

}
