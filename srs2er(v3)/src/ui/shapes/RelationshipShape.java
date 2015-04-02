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

public class RelationshipShape extends JPanel
{
	public RelationshipShape(String name)
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

		this.setSize(width + width, height + height);
		g2.setStroke(new BasicStroke((float) 1.5));
		
		g2.drawString(this.getName(), width / 2, (int) (1.5 * height) - fm.getDescent());
		drawRelationshipPolygon(g2);

		this.getParent().repaint();

	}
	
	private void drawRelationshipPolygon(Graphics2D g2) {
		int[] x = new int[4];
		int[] y = new int[4];
		
		int h = this.getHeight();
		int w = this.getWidth();
		
		x[0] = 0;
		x[1] = w / 2;
		x[2] = w;
		x[3] = w / 2;
		
		y[0] = h / 2;
		y[1] = 0;
		y[2] = h / 2;
		y[3] = h - 2;
		
		g2.drawPolygon(x, y, 4);
	}
}
