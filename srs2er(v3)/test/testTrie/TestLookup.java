package testTrie;

import java.util.LinkedList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Test;

import trie.Node;

public class TestLookup {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testSearchList() {
		List<Node> list = new LinkedList<Node>();
		Node noun = new Node("NN", "Noun");
		Node adverb = new Node("RB", "Adverb");
		Node adj = new Node("JJ", "Adj");
		Node nounpl = new Node("NNP", "NounPl");
		list.add(noun);
		list.add(adverb);
		list.add(adj);
		list.add(nounpl);
		
//		assertEquals("Should return true for noun", noun, Lookup.searchList(list, "NN"));
//		assertEquals("Should return true for noun", adj, Lookup.searchList(list, "JJ"));
//		assertSame("Should be same", adverb, Lookup.searchList(list, "RB"));
//		assertNull("Not found should return null", Lookup.searchList(list, "RBS"));
		
	}

}
