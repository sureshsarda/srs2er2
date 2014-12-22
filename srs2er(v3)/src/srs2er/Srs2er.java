package srs2er;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

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

	public static void main(String[] args) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(Sentences.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        File xml = new File("data//TrainingSentences.xml");
        
        /*The following code overrides the default behaviour of jabx
         * which silently ignores error.
         * Source: http://stackoverflow.com/questions/2633276/jaxb-unmarshall-created-an-empty-object
         */
        unmarshaller.setEventHandler(
        	    new ValidationEventHandler() {
        	        public boolean handleEvent(ValidationEvent event ) {
        	            throw new RuntimeException(event.getMessage(),
        	                                       event.getLinkedException());
        	        }
        	});
        
        Sentences sentences = (Sentences) unmarshaller.unmarshal(xml);
        

        Trie trie = new Trie();
        trie.insertIntoTrie(sentences);
        
        trie.print(System.out, PrintDetail.TAGS_ONLY);
        
        /*Prints WORD and POST uses on Stdout*/
        //StatisticsCollector.Analyze(sentences, System.out);
        
        Paragraph p = new Paragraph("Student details include username and password. Student shall be able to download the courses provided by the system. Instructor shall be capable of managing student grades.  Instructor shall evaluate student’s and group’s homework submissions online. Student shall upload solution of homework submission. Instructor shall be capable of automatically accepting Homework submissions.");
        p.acquireDataModel(trie);
        
        System.out.println(p.toString());
	}
}
