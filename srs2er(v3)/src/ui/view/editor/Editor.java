package ui.view.editor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JPanel;

import nlp.objects.Model;
import ui.shapes.AttributeShape;
import ui.shapes.EntityShape;
import ui.shapes.RelationshipShape;
import ui.shapes.Shape;

public class Editor extends JPanel
{
	protected EditorMouseAdapter mouseAdapter;
	public DataModelAdapter data;

	public Editor(Model model)
	{
		this.setVisible(true);
		this.setLayout(null);
		this.setBackground(Color.WHITE);

		this.setPreferredSize(new Dimension(300, 300));
		this.setMinimumSize(new Dimension(200, 200));

		mouseAdapter = new EditorMouseAdapter();
		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);

		this.setDataModel(model);
	}

	@Override
	public Component getComponentAt(Point p)
	{
		return findComponentAt(p);
	}

	public void setDataModel(Model model)
	{
		data = new DataModelAdapter(model);
		this.paintDataModel();
	}
	private void paintDataModel()
	{
		int y = 50;
		for (String rel : data.relationships)
		{
			RelationshipShape a1 = new RelationshipShape(rel);
			a1.setLocation(20, y);
			a1.setSize(100, 100);
			this.add(a1);
			y = y + 100;
		}

		y = 50;
		for (String ent : data.entities)
		{
			EntityShape a1 = new EntityShape(ent);
			a1.setLocation(150, y);
			a1.setSize(100, 100);
			this.add(a1);
			y = y + 50;

		}

		y = 50;
		for (String atrib : data.attributes)
		{
			AttributeShape a2 = new AttributeShape(atrib);
			a2.setLocation(300, y);
			a2.setSize(100, 100);
			this.add(a2);
			y += 50;
		}
	}

	public boolean line = false;
	Component source, dest;
	public void setSource(Component source)
	{
		this.source = source;
		line = true;

	}

	public void setDest(Component dest)
	{
		this.dest = dest;
		line = false;

		System.out.println(source.getClass());
		System.out.println(dest.getClass());

		if ((source.getClass() == AttributeShape.class && dest.getClass() == EntityShape.class)
				|| (source.getClass() == EntityShape.class && dest.getClass() == RelationshipShape.class))
		{
			Component temp = source;
			source = dest;
			dest = temp;
		}
		if (data.connections.containsKey(source.getName()) == false)
		{
			data.connections.put(source.getName(), new ArrayList<String>());
		}
		data.connections.get(source.getName()).add(dest.getName());

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
		g2.setStroke(new BasicStroke((float) 1.5));

		for (String key : data.connections.keySet())
		{
			Shape source = (Shape) this.getComponentByName(key);
			for (String value : data.connections.get(key))
			{
				Shape dest = (Shape) this.getComponentByName(value);
				if (dest != null)
				{
					Point[] anchors = source.getAnchors(dest);
					
					
					g2.fillOval(anchors[0].x - 3, anchors[0].y - 3, 6, 6);
					for (int i = 1; i < anchors.length; i++)
					{
						g2.drawLine(anchors[i - 1].x, anchors[i - 1].y, anchors[i].x, anchors[i].y);
					}
					g2.fillOval(anchors[anchors.length - 1].x - 3, anchors[anchors.length - 1].y - 3, 6, 6);
					// g2.drawLine(source.getX(), source.getY(), dest.getX(),
					// dest.getY());

					// g2.drawLine(source.getX() + source.getWidth() / 2,
					// source.getY() + source.getHeight() / 2, dest.getX() +
					// dest.getWidth()
					// / 2, dest.getY() + dest.getHeight() / 2);
				}
			}
		}

		this.getParent().repaint();

	}
	protected void drawConnector(int x, int y)
	{
		if (line == true)
		{
			Graphics2D g2 = (Graphics2D) this.getGraphics();

			RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g2.setRenderingHints(qualityHints);

			g2.setColor(new Color(0, 131, 185));
			g2.setStroke(new BasicStroke((float) 1.5));

			g2.drawLine(source.getX(), source.getY(), x, y);
		}
	}

	private Component getComponentByName(String name)
	{
		for (Component compo : this.getComponents())
		{
			if (name.equals(compo.getName()) == true)
				return compo;
		}

		return null;
	}

}
