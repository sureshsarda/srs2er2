package ui.shapes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.FocusManager;
import javax.swing.JPanel;

public class EntityShape extends Shape
{
	protected String name;

	public EntityShape(String name)
	{
		super(name);

	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		g2.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 20));
		FontMetrics fm = g2.getFontMetrics();
		int width = fm.stringWidth(this.getName());
		int height = fm.getAscent();
		
		this.setSize(width + 30, height + 30);
		
		g2.setColor(new Color(0, 131, 185));
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g2.setColor(Color.WHITE);
		g2.drawString(this.getName(), 15, height + 15 - fm.getDescent());

		this.getParent().repaint();

	}

}
