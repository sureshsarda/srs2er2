package ui.shapes;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import ui.view.editor.Editor;

public class ShapePopUpMenu extends JPopupMenu
{
	JMenuItem rename;
	JMenuItem delete;
	JMenuItem connect;

	Component caller;

	public ShapePopUpMenu()
	{

		this.add(rename = new JMenuItem("Rename"));
		this.add(delete = new JMenuItem("Delete"));
		this.add(connect = new JMenuItem("Connect"));

		delete.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Editor ed = (Editor) caller.getParent();
				ed.remove(caller);
				
				ed.data.remove(caller.getName());
				
			}

		});

		connect.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				((Editor)caller.getParent()).setSource(caller);
			}

		});

	}

	public void setCaller(Component component)
	{
		this.caller = component;
	}

}
