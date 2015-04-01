package ui.view;

import java.awt.Dimension;

import javax.swing.JFrame;

import ui.editor.EditorGroupPanel;

public class Feedback extends JFrame
{
	public Feedback()
	{
		this.setSize(new Dimension(800, 750));
		this.setLocationRelativeTo(null);
		this.setTitle("Feedback");
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
//		EditorPanel pan = new EditorPanel();
		EditorGroupPanel pan = new EditorGroupPanel();
		pan.setLocation(0, 0);
		pan.setSize(700, 700);
		this.add(pan);
		
	}
	
	public static void main(String[] args) {
		Feedback fb = new Feedback();
		fb.revalidate();
		fb.repaint();
	}
}
