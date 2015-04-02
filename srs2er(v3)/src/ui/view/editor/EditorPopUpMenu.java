package ui.view.editor;

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
	JMenuItem addConnection = new JMenuItem("Add Connection");
	
	Point sourceCoord;
	
	public EditorPopUpMenu() {
		add(addEntity);
		add(addAttribute);
		add(addRelationship);
		add(addConnection);
		
		addConnection.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				((Editor) e.getSource()).line = true;
				
			}
			
		});
		
	}
	
	public void setSourceCoord(Point p) {
		sourceCoord = p;
	}
}
