package ui.editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;

import javax.swing.JCheckBox;
import javax.swing.JFrame;

import ui.erModelComponents.AttributeShape;
import ui.erModelComponents.EntityShape;

public class Editor extends JFrame
{
	private EditorMouseAdapter mouseAdapter;
	
	private JCheckBox alignToGrid;

	public Editor()
	{

		
		this.setTitle("Data Model Editor");
		this.setVisible(true);
		this.setSize(640, 480);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.getContentPane().setLayout(null);
		this.getContentPane().setBackground(Color.WHITE);

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
		
		AttributeShape a1 = new AttributeShape("attribute");
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
	public static void main(String[] args)
	{
		Editor ed = new Editor();

	}

	public boolean alignToGrid() {
		return alignToGrid.isSelected();
	}
}