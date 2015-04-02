package ui.editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.plaf.ComponentUI;

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
		
		
	}
	
	public void setSourceCoord(Point p) {
		sourceCoord = p;
	}
}
