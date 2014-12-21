package nlp.processing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*Singleton object*/
public class Stopwords {
	
	private static Stopwords instance;
	private final String filePath = "data//Stopwords.csv";
	private List<String> words;
	
	private Stopwords() {
		BufferedReader br;
		List<String> lines = new ArrayList<String>();
		
		/*Read all the lines from the input file*/
		try {
			br = new BufferedReader(new FileReader(filePath));
			String nextLine = null;
			while (( nextLine = br.readLine()) != null) {
				lines.add(nextLine);
			}
			
			/*Add all the words from a line to the list*/
			words = new ArrayList<String>();
			for (String line : lines) {
				words.addAll(Arrays.asList(line.split(",")));
			}
		}
		catch (IOException ioe) {
			ioe.printStackTrace(System.err);
			System.err.println("IO Exception thrown while loading the stopwrods file. Continuing with out stopwords.");
			words = new ArrayList<String>();
		}
		
		
	}
	
	public static Stopwords getInstance() {
		if (instance == null) {
			instance = new Stopwords();
		}
		return instance;
	}
	
	/**
	 * Checks if the list of the stop word contains a word.
	 * @param word
	 * @return boolean
	 */
	public boolean contains(String word) {
		return words.contains(word);
	}
	
}
