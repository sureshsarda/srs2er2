package ui.view.editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import nlp.objects.Attribute;
import nlp.objects.Entity;
import nlp.objects.Model;
import nlp.objects.Relationship;
import ui.shapes.AttributeShape;
import ui.shapes.EntityShape;
import ui.shapes.RelationshipShape;

public class Editor extends JPanel
{
	protected EditorMouseAdapter mouseAdapter;
	protected Model dataModel;
	

	public Editor(Model model) {
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
		this.dataModel = model;
		this.paintDataModel();
	}
	private void paintDataModel()
	{
		int rely = 50;
		for (Relationship rel : dataModel.getRelationships())
		{
			RelationshipShape a1 = new RelationshipShape(rel.getLemmName());
			a1.setLocation(20, rely);
			a1.setSize(100, 100);
			this.add(a1);
			rely = rely + 100;
		}
		
		int enty = 50;
		int atry = 50;
		for (Entity ent : dataModel.getEntities())
		{
			EntityShape a1 = new EntityShape(ent.getLemmName());
			a1.setLocation(150, rely);
			a1.setSize(100, 100);
			this.add(a1);
			for (Attribute atrib : ent.getAttributes())
			{
				AttributeShape a2 = new AttributeShape(atrib.getLemmName());
				a2.setLocation(300, atry);
				a2.setSize(100, 100);
				this.add(a2);
				atry += 50;
			}
			enty = atry + 50;
			
			
		}
		
		JPanel pan = new JPanel();
		pan.setSize(200, 200);
		pan.setLocation(10, 10);
		pan.setBackground(new Color(0, 0, 0, 5));
		pan.setVisible(true);
		this.add(pan, 0);
	}
	
	protected boolean line = false;
	protected Point source;
	Point dest;
	public void setSource(Point source) {
		this.source = source;
	}
	@Override
	protected void paintComponent(Graphics g)
	{
		if (line == true) {
			
		}
		super.paintComponent(g);
	}
}
