package ui.view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class ProgressBar extends JPanel
{
	public ProgressBar() {
		
		this.setBackground(Color.CYAN);
		this.setSize(200, 200);
		this.setPreferredSize(new Dimension(200, 200));
		
		this.setVisible(true);
	}
}
