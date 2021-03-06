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

import ui.view.editor.EditorStatus;

public class RelationshipShape extends Shape
{
	public RelationshipShape(String name)
	{
		super(name);
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(EditorStatus.contrastColor);
		
		g2.setFont(EditorStatus.shapeFont);
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
