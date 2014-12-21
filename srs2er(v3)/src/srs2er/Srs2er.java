package srs2er;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

import nlp.objects.Sentences;
import nlp.processing.POSTagger;
import nlp.processing.statistics.StatisticsCollector;
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
        
        trie.print(System.out, PrintDetail.TAGS_AND_PROBABILITY);
        
        
        StatisticsCollector.Analyze(sentences, System.out);
        
	}

}
