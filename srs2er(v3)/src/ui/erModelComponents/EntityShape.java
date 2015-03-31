package ui.erModelComponents;

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

public class EntityShape extends JPanel
{
	protected String name;

	public EntityShape(String name)
	{
		this.name = new String(name);
		this.setName(name);
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
