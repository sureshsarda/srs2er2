package ui.erModelComponents;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class AttributeShape extends JPanel
{
	public AttributeShape(String name)
	{
		this.setName(name);
		this.setOpaque(false);
		this.setPreferredSize(new Dimension(50, 20));
		this.setVisible(true);
		this.addMouseListener(ShapeMouseAdapter.shapeMouseAdapter);
		this.addMouseMotionListener(ShapeMouseAdapter.shapeMouseAdapter);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHints(qualityHints);

		g2.setColor(new Color(0, 131, 185));
		
		g2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		FontMetrics fm = g2.getFontMetrics();
		int width = fm.stringWidth(this.getName());
		int height = fm.getHeight();

		this.setSize(width + 20, height + 20);
		g2.setStroke(new BasicStroke((float) 1.5));
		
		g2.drawRoundRect(1, 1, width + 18, height + 18, 20, 20);
		g2.drawString(this.getName(), 10, height + 10 - fm.getDescent());
		

		this.getParent().repaint();

	}
}
