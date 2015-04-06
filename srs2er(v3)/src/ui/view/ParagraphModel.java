package ui.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;

import nlp.objects.Model;
import ui.view.editor.Editor;

public class ParagraphModel extends JFrame
{
	public ParagraphModel(Model models)
	{
		Splash.setCurrentSystemLookAndFeel();

		this.setLocationRelativeTo(null);
		this.setTitle("Feedback");
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		this.setLayout(new BorderLayout(10, 10));
		
		Editor editor = new Editor((models));
		this.add(editor, BorderLayout.CENTER);
		
		JLabel warningLabel = new JLabel();
		warningLabel.setForeground(Color.RED);
		warningLabel.setBackground(Color.WHITE);
		warningLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		warningLabel.setText("Warning: Changes made here are not taken as feedback for the system.");
		this.add(warningLabel, BorderLayout.PAGE_END);

	}
}
