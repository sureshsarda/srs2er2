package ui.shapes;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import util.Tuple;

public class Shape extends JPanel
{
	public Shape(String name)
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
	}

	protected Point[] plugs;
	private Point[] getPlugs()
	{
		plugs = new Point[4];

		int x = this.getX();
		int y = this.getY();
		int w = this.getWidth();
		int h = this.getHeight();

		plugs[0] = new Point(x, y + h / 2);
		plugs[1] = new Point(x + w / 2, y);
		plugs[2] = new Point(x + w, y + h / 2);
		plugs[3] = new Point(x + w / 2, y + h);

		return plugs;
	}

	private Tuple<Point, Point> distance(Shape shape)
	{
		Point p1 = null, p2 = null;
		int in1, in2;
		double min = Double.MAX_VALUE;

		Point[] shapePlugs = shape.getPlugs();
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				double dist = this.plugs[i].distance(shapePlugs[j]);
				if (dist < min)
				{
					p1 = this.plugs[i];
					p2 = shapePlugs[j];
					in1 = i;
					in2 = j;
					min = dist;
				}
			}
		}
		return new Tuple<Point, Point>(p1, p2);
	}

	public Point[] getAnchors(Shape shape)
	{
		Point p1 = null, p2 = null;
		int in1 = 0, in2;
		double min = Double.MAX_VALUE;

		Point[] shapePlugs = shape.getPlugs();
		this.plugs = this.getPlugs();
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				double dist = this.plugs[i].distance(shapePlugs[j]);
				if (dist < min)
				{
					p1 = this.plugs[i];
					p2 = shapePlugs[j];
					in1 = i;
					in2 = j;
					min = dist;
				}
			}
		}

		Point anchors[] = new Point[3];
		anchors[0] = p1;
		anchors[2] = p2;

		Point anchor = new Point();
		switch (in1)
		{
			case 0 :
				anchor.x = p2.x;
				anchor.y = p1.y;
				break;
			case 1 :
				anchor.x = p1.x;
				anchor.y = p2.y;
				break;
			case 2 :
				anchor.x = p2.x;
				anchor.y = p1.y;
				break;
			case 3 :
				anchor.x = p1.x;
				anchor.y = p2.y;
				break;
			default :
				break;
		}
		anchors[1] = anchor;
		
		return anchors;
		
	}

}
