package ui.editor;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class EditorPopUpMenu extends JPopupMenu
{
	JMenuItem addEntity = new JMenuItem("Add Entity");
	JMenuItem addAttribute = new JMenuItem("Add Attribute");
	JMenuItem addRelationship = new JMenuItem("Add Relationship");
	
	Point sourceCoord;
	
	public EditorPopUpMenu() {
		add(addEntity);
		add(addAttribute);
		add(addRelationship);
		
		addEntity.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				/*Entity ent = new Entity("Test");
				ent.setLocation(sourceCoord);
				ent.setSize(50, 50);
				Editor.frame.add(ent);*/
				
			}
			
		});
	}
	
	public void setSourceCoord(Point p) {
		sourceCoord = p;
	}
}
