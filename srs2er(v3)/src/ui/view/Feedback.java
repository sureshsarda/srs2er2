package ui.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nlp.test.TestParagraph;
import ui.data.Data;
import ui.editor.EditorGroupPanel;

public class Feedback extends JFrame
{
	public Feedback(TestParagraph para)
	{
		Splash.setCurrentSystemLookAndFeel();
		
		this.setLocationRelativeTo(null);
		this.setTitle("Feedback");
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		this.setLayout(new BorderLayout(10, 10));
		
		
		JPanel sPan = new SummaryViewPanel();
		this.add(sPan, BorderLayout.LINE_START);
		

		updateEditorGroupPanel(0);
		
		this.add(new JLabel("This windows helps you to correct the model of every sentence."),BorderLayout.PAGE_START);
		
		
	}
	
	public void updateEditorGroupPanel(int index) {
		EditorGroupPanel pan = new EditorGroupPanel(Data.para.getSentences().get(0));
		this.setPreferredSize(new Dimension(200, 200));
		this.add(pan, BorderLayout.CENTER);
	}
	/*public static void main(String[] args) {
		Feedback fb = new Feedback(Data.para);
		fb.revalidate();
		fb.repaint();
	}*/
}
