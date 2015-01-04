package srs2er;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

import nlp.objects.Sentences;
import nlp.processing.statistics.StatisticsCollector;
import tester.Paragraph;
import trie.Trie;
import trie.Trie.PrintDetail;
import util.logging.LoggerSetup;

/**
 * 
 * @author Suresh Sarda
 *
 */
public class Srs2er {

	/*
	 * List of training data files. All the sentences from all the files in the
	 * array will be trainied
	 */
	private static final String[] trainingDataFiles = {
	// "data/training/TrainingSentences1.xml",
	// "data/training/TrainingSentences2.xml",
	// "data/training/TrainingPooja.xml",
	// "data/training/TrainingRohini.xml",
	// "data/training/TrainingSuresh.xml",
	// "data/training/TrainingRohit.xml"
	"data/training/MegaTraining.xml" };

	/* List of test data file. Keep only one file and comment out the rest */
	private static final String testDataFile = "data/testing/e-exam.txt";
	// private static final String testDataFile = "data/testing/employee.txt";
	// private static final String testDataFile = "data/testing/employee.txt";
	// private static final String testDataFile = "data/testing/team.txt";
	// private static final String testDataFile = "data/testing/hostel.txt";
	// private static final String testDataFile = "data/testing/student.txt";
	// private static final String testDataFile = "data/testing/mobile.txt";

	/* Output file will be generated at this path */
	// private static final String outputFile = "out/out.erd";
	private static final String outputFile = "D:/Workplace/runtime-EclipseApplication/test/out.erd";
	private static final String statFile = "out/stat.csv";

	public static final Logger LOGGER = Logger
			.getLogger(Srs2er.class.getName());

	public static void main(String[] args) throws JAXBException, IOException {

		LoggerSetup.setup(LOGGER);

		// LOGGER.setLevel(Level.ALL);

		LOGGER.info("Loading trainig data");
		Sentences sentences = loadTrainingSentences();

		LOGGER.info("Training Trie...");
		Trie trie = new Trie();
		trie.insertIntoTrie(sentences);

		if (LOGGER.getLevel().intValue() <= Level.FINE.intValue()) {
			trie.print(System.out, PrintDetail.TAGS_ONLY);
		}

		/* Generate Statistics */
		File out = new File(statFile);
		PrintStream ps = new PrintStream(out);
		StatisticsCollector.Analyze(sentences, ps);

		LOGGER.info("Loading test paragraph...");
		Paragraph p = new Paragraph(new File(testDataFile));

		LOGGER.info("Acquiring data model...");
		p.acquireDataModel(trie);
		
		LOGGER.fine(p.getParagraphDataModel().toString());

		LOGGER.info("Saving the output...");
		p.saveAsXml(outputFile);
	}

	private static Sentences loadTrainingSentences() throws JAXBException {

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

		Sentences sentences = new Sentences();

		int totalTrained = 0;

		LOGGER.config("Unmarshalling training sentences...");
		for (int i = 0; i < trainingDataFiles.length; i++) {

			File xml = new File(trainingDataFiles[i]);
			Sentences sentSet = (Sentences) unmarshaller.unmarshal(xml);
			sentences.addSentence(sentSet.getSentence());

			LOGGER.config(String.format("Reading %d sentences from file %s...",
					sentSet.getSentence().size(), trainingDataFiles[i]));
			totalTrained += sentSet.getSentence().size();
		}

		LOGGER.config(String.format("Read %d sentences.", totalTrained));
		return sentences;
	}
}
