package nlp.processing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nlp.objects.Sentence;
import nlp.objects.Sentences;
import nlp.objects.Word;

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
	
	public void removeStopwrods(Sentences sentences) {
		for (Sentence sentence : sentences.getSentence()) {
			removeStopwords(sentence);
		}
	}
	private void removeStopwords(Sentence sentence) {
		List<Word> tokens = sentence.getTokens();
		int length = tokens.size();
		
		for (int i = 0 ; i < length; i++) {
			Word currentWord = tokens.get(i);
			if (Stopwords.getInstance().contains(currentWord.getName().toLowerCase()) == true) {
				tokens.remove(i);
				length--;
				/*for(int j = i; j < length; j++) {
					Word word = tokens.get(j);
					word.setId(word.getId() - 1);
					tokens.set(j, word);
				}*/
				i--;
				
			}
		}
		sentence.setTokens(tokens);
	}
}
