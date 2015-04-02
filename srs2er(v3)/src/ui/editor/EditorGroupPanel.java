package ui.editor;

import java.awt.Color;

import javax.swing.JTabbedPane;

import nlp.test.LookupResultObject;
import nlp.test.TestSentence;

public class EditorGroupPanel extends JTabbedPane
{
	private TestSentence sentence;
	
	public EditorGroupPanel(TestSentence sentence)
	{
		this.sentence = sentence;
		
		
		this.setTabPlacement(BOTTOM);
		
		for (LookupResultObject obj : sentence.getResults())
		{
			EditorPanel pan = new EditorPanel(obj.getDataModel());
			String title = "Cost: " + obj.getCost();
			
			this.addTab(title, pan);
		}
		
		
	}
}
