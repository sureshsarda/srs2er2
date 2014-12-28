package srs2er;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import nlp.objects.Sentences;
import nlp.processing.statistics.StatisticsCollector;
import tester.Paragraph;
import trie.Trie;
import util.Logger;

/**
 * 
 * @author Suresh Sarda
 *
 */


public class Srs2er {

	private static final String[] trainingDataFiles = {
			"data//TrainingSentences1.xml", "data/TrainingSentences2.xml",
			"data/TrainingPooja.xml", "data/TrainingRohini.xml" };
	private static final String testDataFile = "data/Test.txt";
	
	private static final String outputFile = "out/out.erd";

	public static void main(String[] args) throws JAXBException, FileNotFoundException {
		JAXBContext jc = JAXBContext.newInstance(Sentences.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();

		/*
		 * The following code overrides the default behavior of JABX which
		 * silently ignores error. Source:
		 * http://stackoverflow.com/questions/2633276
		 * /jaxb-unmarshall-created-an-empty-object
		 */
		unmarshaller.setEventHandler(new ValidationEventHandler() {
			public boolean handleEvent(ValidationEvent event) {
				throw new RuntimeException(event.getMessage(), event
						.getLinkedException());
			}
		});

		Trie trie = new Trie();
		Sentences sentences = new Sentences();
		
		for (int i = 0; i < trainingDataFiles.length; i++) {
			File xml = new File(trainingDataFiles[i]);
			Sentences sentSet = (Sentences) unmarshaller.unmarshal(xml);
			sentences.addSentence(sentSet.getSentence());
			
			Logger.Log(String.format(
					"Reading %d sentences from file %s...",
					sentSet.getSentence().size(), trainingDataFiles[i]));
		}
		
		trie.insertIntoTrie(sentences);
		
		
		//trie.print(System.out, PrintDetail.TAGS_ONLY);
		
		File out = new File("stats.csv");
		PrintStream ps = new PrintStream(out);
		StatisticsCollector.Analyze(sentences, ps);

		
		Paragraph p = new Paragraph(new File(testDataFile));
		p.acquireDataModel(trie);
		System.out.println(p.getParagraphDataModel().toString());
		
		
		
		try {
			ErdBuilder erdb = new ErdBuilder(new File(outputFile));	
			erdb.parse(p.getParagraphDataModel());
		}
		catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		}
		catch (TransformerException te) {
			te.printStackTrace();
		}
	}
}
