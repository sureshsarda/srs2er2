package ui.view.editor;

import java.awt.Color;

import javax.swing.JTabbedPane;

import nlp.test.LookupResultObject;
import nlp.test.TestSentence;

public class EditorGroupPanel extends JTabbedPane
{
	public TestSentence sentence;
	
	public EditorGroupPanel(TestSentence sentence)
	{
		
		this.setTabPlacement(BOTTOM);
		updateLayout(sentence);
		
	}
	
	public void updateLayout(TestSentence sent) {
		this.sentence = sent;
		
		this.removeAll();
		
		for (LookupResultObject obj : sentence.getResults())
		{
			EditorPanel pan = new EditorPanel(obj.getDataModel());
			String title = "Cost: " + obj.getCost();
			
			this.addTab(title, pan);
		}
	}
}
