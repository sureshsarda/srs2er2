package ui.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nlp.test.TestParagraph;
import nlp.test.TestSentence;
import ui.view.editor.EditorGroupPanel;

@SuppressWarnings("serial")
public class Feedback extends JFrame
{
	public TestParagraph para;
	public static Feedback instance;
	protected EditorGroupPanel pan;
	
	public TestSentence currentSentence;
	public Feedback(TestParagraph para)
	{
		instance = this;
		this.para = para;
		
		Splash.setCurrentSystemLookAndFeel();
		
		this.setLocationRelativeTo(null);
		this.setTitle("Feedback");
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		this.setLayout(new BorderLayout(10, 10));
		
		this.add(new JLabel("This windows helps you to correct the model of every sentence."),BorderLayout.PAGE_START);

		JPanel sPan = new SummaryViewPane();
		this.add(sPan, BorderLayout.LINE_START);
		
		updateEditorGroupPanel(0);
		
		
		
		
	}
	
	public void updateEditorGroupPanel(int index) {
		if (pan == null) {
			pan = new EditorGroupPanel(para.getSentences().get(index));
		}
		pan.updateLayout(para.getSentences().get(index));
		this.setPreferredSize(new Dimension(200, 200));
		this.add(pan, BorderLayout.CENTER);
		
		currentSentence = para.getSentences().get(index);
	}
	/*public static void main(String[] args) {
		Feedback fb = new Feedback(Data.para);
		fb.revalidate();
		fb.repaint();
	}*/
}
