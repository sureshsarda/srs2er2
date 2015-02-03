package nlp.objects;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * 
 * @author SureshSarda
 *
 */


/*
 * 
 * DO NOT AUTO FORMAT THIS FILE!
 * (Do NOT save it if you do.
 */

/*Singleton Object*/
public class TagDataLoader {

    public enum TagType {
	UNKNOWN(00),
	NN(1, 	"Noun (Singular)"),
	NNS(2,	"Noun (Plural)"), 
	NNP(3, 	"ProperNoun (Singular)"), 
	NNPS(4, "ProperNoun (Plural)"), 
	
	VB(5, 	"Verb (Base Form)"), 
	VBD(6, 	"Verb (Past Tense)"), 
	VBG(7, 	"Verb (Gerund/Present Participle)"), 
	VBN(8, 	"Verb (Past Participle"), 
	VBP(9, 	"Verb (Singular Present, Non 3rd person)"), 
	VBZ(10, "Verb (Singular Present, 3rd Person)"), 
	
	RB(11, 	"Adverb"), 
	RBR(12, "Adverb (Comparative)"),
	RBS(13, "Adverb (Superlative)"), 
	
	JJ(14, 	"Adjective"), 
	JJR(15, "Adjective (Comparative)"),
	JJS(16, "Adjective (Superlative)"),
	
	PRP(17, "Personal Pronoun"), 
	PRP$(18,"Possessive Pronoun"), 
	WP(19, 	"Wh­pronoun"), 
	WP$(20, "Possessive Wh­Pronoun"), 
	CC(21, 	"Coordinating Conjunction"), 
	CD(22, 	"Cardinal Number"), 
	DT(23), 
	EX(24), 
	FW(25), 
	IN(26), 
	LS(27), 
	MD(28), 
	PDT(29), 
	POS(30), 
	RP(31), 
	SYM(32), 
	TO(33), 
	UH(34), 
	WDT(35), 
	WRB(36);


	final static int count = 36;

	int value;
	String longString;
	int deletionCost;
	int insertionCost;
	List<Integer> substitutionCost;

	TagType(int val) {
	    value = val;
	}
	
	TagType(int val, String longString) {
	    value = val;
	    setLongString(longString);
	}

	void setLongString(String longString) {
	    this.longString = longString;
	}
	
	void setInsertionCost(int ins) {
	    insertionCost = ins;
	}
	void setDeletionCost(int del) {
	    deletionCost = del;
	}
	void setSubstutionCost(List<Integer> subs) {
	    substitutionCost = subs;
	}
	
	public int getInsertionCost() {
	    return insertionCost;
	}
	
	public int getDeletionCost() {
	    return deletionCost;
	}
	
	public int getSubstitutionCost(TagType subsWith) {
	    if (subsWith == TagType.UNKNOWN) {
		return 999;
	    }
	    return substitutionCost.get(subsWith.getValue() - 1);
	}
	
	public int getValue() {
	    return value;
	}

	public String toString() {
	    return this.name();
	}
	public String toLongString() {
	    return this.longString;
	}

    };
    /* TagType Enum ends here */
    
    
    
    private static TagDataLoader instance;
    
    public static TagDataLoader getInstance() throws IOException {
	if (instance == null) {
	    instance = new TagDataLoader();
	}
	return instance;
    }
    
    private TagDataLoader() {
	
    }
    
    public void Load() throws IOException {
	initTagCostMatrices();
    }


    File costMatrixFile = new File("data\\post\\cost2.csv");

    private void initTagCostMatrices() throws IOException {
	Iterator<String> lineIterator = Files.lines(costMatrixFile.toPath()).iterator();

	String firstLine = lineIterator.next();
	String insertion = lineIterator.next();
	String deletion = lineIterator.next();
	
	checkTagSequence(firstLine);
	assignCosts(insertion.split(","), deletion.split(","));
	
	while (lineIterator.hasNext()) {
	    assignCosts(lineIterator.next().split(","));
	}

    }
    private void assignCosts(String[] insertion, String[] deletion) {
	for (int i = 1; i < TagType.count; i++) {
	    TagType current = TagType.values()[i];
	    
	    current.setInsertionCost(Integer.parseInt(insertion[i]));
	    current.setDeletionCost(Integer.parseInt(deletion[i]));
	}
    }
    
    private void assignCosts(String[] subs) {
	List<Integer> subsCost = new ArrayList<Integer>(subs.length);
	for (int i = 1; i <= TagType.count; i++) {
	    subsCost.add(Integer.parseInt(subs[i]));
	}
	TagType.valueOf(subs[0]).setSubstutionCost(subsCost);
    }
    
    private boolean checkTagSequence(String firstLine) {
	return true;
    }
}
