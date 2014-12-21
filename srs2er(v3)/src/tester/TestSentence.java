package tester;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nlp.objects.Sentence;
import nlp.processing.POSTagger;


public class TestSentence {
	
	String[] sentences;
	List<Sentence> sentence=new ArrayList<Sentence>();
	POSTagger post=new POSTagger();
	
	public TestSentence(String filename) throws IOException
	{
		extractSentences(filename);
		tagSentences();
	}

	private void tagSentences() {

		int sentenceIndex=0;
		for (String string : sentences) {
			sentence.add(post.tag(string,sentenceIndex));
			sentenceIndex++;
		}
		
		for (Sentence sentence : sentence) {
			System.out.println(sentence.getId());
			System.out.println(sentence.getValue());
			System.out.println(sentence.getTokens());
		}
	}

	private void extractSentences(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append(System.lineSeparator());
	            line = br.readLine();
	        }
	        String everything = sb.toString();
	        System.out.println("\n"+everything);
	        sentences=everything.split("\\.");
	    } finally {
	        br.close();
	    }
	}
	
	

}
