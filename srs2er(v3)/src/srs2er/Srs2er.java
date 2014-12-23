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
        
        Paragraph p = new Paragraph("The restaurant has a kitchen which employs chefs. "
        							+ "Each branch has one manager to overlook its operations. "
        							+ "Manager procures furniture and staff for the restaurant.");
        p.acquireDataModel(trie);
        
        System.out.println(p.toString());
	}
}
