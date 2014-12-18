package srs2er;

import java.io.File;
import java.nio.charset.Charset;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

import nlp.objects.Sentences;
import trie.Trie;

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
        
        Sentences emps = (Sentences) unmarshaller.unmarshal(xml);
        

        Trie trie = new Trie();
        trie.InsertIntoTrie(emps);
        
        trie.Print(System.out);
        System.out.println("\n\n");
        
        System.out.println(trie.toString());
        //System.out.println(emps);
	}

}
