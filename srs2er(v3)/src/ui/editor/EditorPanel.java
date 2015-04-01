package ui.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

public class EditorPanel extends JPanel
{
	public EditorPanel()
	{
		this.setLayout(new BorderLayout());
		//this.setBackground(Color.WHITE);
		this.setVisible(true);
		this.setPreferredSize(new Dimension(300, 300));
		this.setMinimumSize(new Dimension(200, 200));
		
		Editor e = new Editor();
		e.setSize(300, 300);
		e.setLocation(10, 10);
		this.add(e, BorderLayout.CENTER);
		
		JPanel buttons = new JPanel();
		buttons.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		buttons.add(new JButton("Save This Diagram"));
		buttons.add(new JButton("Reset"));
		
		
		this.add(buttons, BorderLayout.PAGE_END);
		
		
	}
}
