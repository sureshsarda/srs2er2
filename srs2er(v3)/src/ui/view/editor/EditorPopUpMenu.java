package ui.view.editor;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import ui.shapes.AttributeShape;
import ui.shapes.EntityShape;
import ui.shapes.RelationshipShape;

public class EditorPopUpMenu extends JPopupMenu
{
	JMenuItem addEntity = new JMenuItem("Add Entity");
	JMenuItem addAttribute = new JMenuItem("Add Attribute");
	JMenuItem addRelationship = new JMenuItem("Add Relationship");

	Point sourceCoord;

	public EditorPopUpMenu(Editor editor)
	{
		add(addEntity);
		add(addAttribute);
		add(addRelationship);

		addEntity.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{

				EntityShape ent = new EntityShape("new entity");
				ent.setLocation(sourceCoord);
				ent.setSize(50, 50);
				ent.setVisible(true);

				editor.add(ent);

			}

		});

		addAttribute.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{

				AttributeShape ent = new AttributeShape("new attribute");
				ent.setLocation(sourceCoord);
				ent.setSize(50, 50);
				ent.setVisible(true);

				editor.add(ent);

			}

		});

		addRelationship.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{

				RelationshipShape ent = new RelationshipShape("new relation");
				ent.setLocation(sourceCoord);
				ent.setSize(50, 50);
				ent.setVisible(true);

				editor.add(ent);

			}

		});

	}

	public void setSourceCoord(Point p)
	{
		sourceCoord = p;
	}
}
