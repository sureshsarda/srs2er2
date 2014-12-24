package srs2er;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

import edu.stanford.nlp.parser.shiftreduce.ShiftReduceTrainOptions.TrainingMethod;
import nlp.objects.Sentences;
import tester.Paragraph;
import trie.Trie;
import trie.Trie.PrintDetail;

/**
 * 
 * @author Suresh Sarda
 *
 */
public class Srs2er {

	private static final String traningDataFile = "data//TrainingSentences2.xml";
	private static final String testDataFile = "data/Test.txt";
	
	public static void main(String[] args) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(Sentences.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		File xml = new File(traningDataFile);

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

		Sentences sentences = (Sentences) unmarshaller.unmarshal(xml);

		Trie trie = new Trie();
		trie.insertIntoTrie(sentences);

		trie.print(System.out, PrintDetail.TAGS_ONLY);

		/* Prints WORD and POST uses on Stdout */
		// StatisticsCollector.Analyze(sentences, System.out);

		Paragraph p = new Paragraph(testDataFile);
		p.acquireDataModel(trie);

		System.out.println(p.toString());
	}
}
