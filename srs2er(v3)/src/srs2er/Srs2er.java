package srs2er;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

import nlp.objects.Sentences;
import tester.Paragraph;
import trie.Trie;
import trie.Trie.PrintDetail;
import util.Logger;

/**
 * 
 * @author Suresh Sarda
 *
 */
public class Srs2er {

	/*List of training data files. All the sentences from all the files in the array will be trainied*/
	private static final String[] trainingDataFiles = {
			"data/training/TrainingSentences1.xml", "data/training/TrainingSentences2.xml",
			"data/training/TrainingPooja.xml", "data/training/TrainingRohini.xml" };
	
	/*List of test data file. Keep only one file and comment out the rest*/
	private static final String testDataFile = "data/testing/employee.txt";
//	private static final String testDataFile = "data/testing/team.txt";
//	private static final String testDataFile = "data/testing/hostel.txt";
//	private static final String testDataFile = "data/testing/student.txt";
//	private static final String testDataFile = "data/testing/mobile.txt";
	
	/*Output file will be generated at this path*/
	//private static final String outputFile = "out/out.erd";
	private static final String outputFile = "D:/Workplace/runtime-EclipseApplication/test/out.erd";
	
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
		
		
		trie.print(System.out, PrintDetail.TAGS_ONLY);

		/*Generate Statistics*/ 
//		File out = new File("stats.csv");
//		PrintStream ps = new PrintStream(out);
//		StatisticsCollector.Analyze(sentences, ps);

		Logger.Log("Loading test document...");
		Paragraph p = new Paragraph(new File(testDataFile));
		
		Logger.Log("Acquiring data model...");
		p.acquireDataModel(trie);
		System.out.println(p.getParagraphDataModel().toString());
		
		p.saveAsXml(outputFile);
	}
}
