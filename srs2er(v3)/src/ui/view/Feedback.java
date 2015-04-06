package ui.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nlp.objects.Model;
import nlp.test.TestParagraph;
import nlp.test.TestSentence;
import ui.view.editor.EditorGroupPanel;

@SuppressWarnings("serial")
public class Feedback extends JPanel
{
	public TestParagraph para;
	public static Feedback instance;
	protected EditorGroupPanel pan;
	SummaryViewPane sPan;

	public List<Model> dataModels = new LinkedList<Model>();
	public TestSentence currentSentence;
	public Feedback(TestParagraph para)
	{
		instance = this;
		instance.para = para;
		
		Splash.setCurrentSystemLookAndFeel();
		
		instance.setVisible(true);

//		instance.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		instance.setLayout(new BorderLayout(10, 10));
		
		instance.add(new JLabel("This windows helps you to correct the model of every sentence."),BorderLayout.PAGE_START);

		sPan = new SummaryViewPane();
		instance.add(sPan, BorderLayout.LINE_START);
		
		updateEditorGroupPanel(0);
		
		
		
		
	}
	public void removeSentence(String sent) {
	
		para.removeSentence(sent);
		

		if (para.getSentenceList().size() <= 0) {
			
			Model merged = new Model(dataModels);
			new ParagraphModel(merged);
			//instance.dispose();
		}

		instance.remove(sPan);
		sPan = new SummaryViewPane();
		instance.add(sPan, BorderLayout.LINE_START);
		
		instance.revalidate();
		instance.repaint();
	}
	public void updateEditorGroupPanel(int index) {
		if (pan == null) {
			pan = new EditorGroupPanel(para.getSentences().get(index));
		}
		pan.updateLayout(para.getSentences().get(index));
		instance.setPreferredSize(new Dimension(200, 200));
		instance.add(pan, BorderLayout.CENTER);
		
		currentSentence = para.getSentences().get(index);
	}
	/*public static void main(String[] args) {
		Feedback fb = new Feedback(Data.para);
		fb.revalidate();
		fb.repaint();
	}*/
}
