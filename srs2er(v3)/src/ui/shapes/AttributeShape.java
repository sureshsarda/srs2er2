package ui.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class AttributeShape extends Shape
{
	public AttributeShape(String name)
	{
		super(name);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(new Color(0, 131, 185));
		
		g2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		FontMetrics fm = g2.getFontMetrics();
		int width = fm.stringWidth(this.getName());
		int height = fm.getHeight();

		this.setSize(width + 20, height + 20);
		g2.setStroke(new BasicStroke((float) 1.5));
		
		g2.drawRoundRect(1, 1, width + 18, height + 18, 20, 20);
		g2.drawString(this.getName(), 10, height + 10 - fm.getDescent());
		

//		this.getParent().repaint();

	}
}
