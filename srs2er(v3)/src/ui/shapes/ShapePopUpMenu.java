package ui.shapes;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class ShapePopUpMenu extends JPopupMenu
{
	JMenuItem rename;
	JMenuItem delete;
	JMenuItem connect;

	Component caller;

	public ShapePopUpMenu()
	{
		intiItems();
	}

	private void intiItems()
	{
		this.add(rename = new JMenuItem("Rename"));
		this.add(delete = new JMenuItem("Delete"));
		this.add(connect = new JMenuItem("Connect"));
		
		addEventListener();
	}

	public void setCaller(Component component)
	{
		this.caller = component;
	}
	
	private void addEventListener() {
		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				caller.getParent().remove(caller);
			}
			
		});
	}
	
	

}
