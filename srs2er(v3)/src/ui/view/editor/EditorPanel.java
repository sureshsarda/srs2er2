package ui.view.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nlp.objects.Model;

@SuppressWarnings("serial")
public class EditorPanel extends JPanel
{
	private Model dataModel;
	
	public EditorPanel(Model dataModel)
	{
		
		this.dataModel = dataModel;
		
		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);
		this.setVisible(true);
		this.setPreferredSize(new Dimension(300, 300));
		this.setMinimumSize(new Dimension(200, 200));
		
		/*
		 * Upper Sentence Text
		 */
		this.add(new JLabel("Sentence text here"), BorderLayout.PAGE_START);
		
		
		/*
		 * Center Editor Panel
		 */
		Editor e = new Editor(dataModel);
		e.setSize(300, 300);
		e.setLocation(10, 10);
		this.add(e, BorderLayout.CENTER);
		
		
		/*
		 * Lower Button Panel
		 */
		JPanel buttons = new JPanel();
		buttons.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		buttons.add(new JButton("Save This Diagram"));
		//buttons.add(new JButton("Reset"));
		buttons.setOpaque(false);
		this.add(buttons, BorderLayout.PAGE_END);
		
		
	}
}
