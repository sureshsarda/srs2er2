package ui.editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import nlp.objects.Model;
import ui.shapes.AttributeShape;
import ui.shapes.EntityShape;
import ui.shapes.RelationshipShape;

public class Editor extends JPanel
{
	private EditorMouseAdapter mouseAdapter;
	private Model dataModel;
	private JCheckBox alignToGrid;

	public Editor()
	{
		this.setVisible(true);
		this.setLayout(null);
		this.setBackground(Color.WHITE);

		this.setPreferredSize(new Dimension(300, 300));
		this.setMinimumSize(new Dimension(200, 200));

		mouseAdapter = new EditorMouseAdapter();
		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);

		EntityShape e1 = new EntityShape("Entity");
		e1.setLocation(10, 10);
		e1.setSize(100, 100);
		this.add(e1);

		EntityShape e2 = new EntityShape("longer entity");
		e2.setLocation(10, 100);
		e2.setSize(100, 100);
		this.add(e2);

		RelationshipShape a1 = new RelationshipShape("relationship");
		a1.setLocation(200, 10);
		a1.setSize(100, 100);
		this.add(a1);

		AttributeShape a2 = new AttributeShape("longer attribute");
		a2.setLocation(200, 100);
		a2.setSize(100, 100);
		this.add(a2);

		alignToGrid = new JCheckBox();
		alignToGrid.setOpaque(false);
		alignToGrid.setText("Align to grid");
		alignToGrid.setLocation(this.getWidth() - 100, 50);
		alignToGrid.setSize(100, 50);

		this.add(alignToGrid);

	}

	@Override
	public Component getComponentAt(Point p)
	{
		return findComponentAt(p);
	}

	public boolean alignToGrid()
	{
		return alignToGrid.isSelected();
	}

	public void setDataModel(Model model)
	{
		this.dataModel = model;
		this.paintDataModel();
	}
	private void paintDataModel()
	{
	}
}
