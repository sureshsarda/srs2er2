package ui.erModelComponents;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class ShapePopUpMenu extends JPopupMenu
{
	List<JMenuItem> items;

	public ShapePopUpMenu()
	{
		intiItems();
	}

	private void intiItems()
	{
		items = new ArrayList<JMenuItem>();

		items.add(new JMenuItem("Rename"));
		items.add(new JMenuItem("Delete"));
		
		for (JMenuItem item : items)
		{
			this.add(item);
		}
		
		
	}
}
