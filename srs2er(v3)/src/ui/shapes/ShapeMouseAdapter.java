package ui.shapes;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

import ui.view.editor.Editor;
import ui.view.editor.EditorGroupPanel;

//FIXME Shapes should not move out of editable area

public class ShapeMouseAdapter implements MouseMotionListener, MouseListener
{
	protected static ShapeMouseAdapter shapeMouseAdapter = new ShapeMouseAdapter();
	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (e.getButton() == MouseEvent.BUTTON1)
		{

			JPanel thisComponent = (JPanel) e.getComponent();
			Editor ed = (Editor) thisComponent.getParent();
			if (ed.line == true)
			{
				ed.setDest(thisComponent);
			}
			else
			{
				Point p = e.getPoint();
				p = SwingUtilities.convertPoint(thisComponent, p, thisComponent.getParent());

				JTextField rename = new JTextField();
				rename.setText(thisComponent.getName());
				rename.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				rename.setSize(thisComponent.getSize());
				rename.setLocation(thisComponent.getLocation());
				rename.setVisible(true);
				rename.addActionListener(new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent a)
					{
						if (((EditorGroupPanel) thisComponent.getParent().getParent().getParent()).sentence
								.getValue().contains(rename.getText()) == true)
						{
							thisComponent.setName(rename.getText());
							rename.setVisible(false);

							thisComponent.setVisible(true);
						}
						else
						{
							rename.setText("Error");
						}

					}

				});

				thisComponent.setVisible(false);

				thisComponent.getParent().add(rename);
			}
		}
		else if (e.getButton() == MouseEvent.BUTTON3)
		{
			/* Show pop up menu */
			ShapePopUpMenu menu = new ShapePopUpMenu();
			menu.setCaller(e.getComponent());
			menu.show(e.getComponent(), e.getX(), e.getY());

		}
		else
		{
			/* Invalid Button Pressed */

		}

	}
	@Override
	public void mouseEntered(MouseEvent e)
	{
		// JPanel thisComponent = (JPanel) e.getComponent();
		// thisComponent.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// JPanel thisComponent = (JPanel) e.getComponent();
		// thisComponent.setBorder(BorderFactory.createEmptyBorder());

	}

	boolean flag = false;
	@Override
	public void mousePressed(MouseEvent arg0)
	{
		flag = true;
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		flag = false;
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		if (flag == true)
		{
			Component compo = e.getComponent();
			Point p = e.getPoint();
			p = SwingUtilities.convertPoint(compo, p, compo.getParent());

			// System.out.println("Draged" + this.getLocation() + e.getPoint());

			int newX = p.x - (compo.getWidth() / 2);
			int newY = p.y - (compo.getHeight() / 2);

			/*
			 * This code helps in alignment of shapes. component is on content
			 * pane. (1st parent) which is on layered pane (2nd Parent) which is
			 * on root pane (3rd Parent) which is on Editor which is a JFrame
			 * (4ht Parent)
			 * 
			 * GOD HELP JAVA! :P (or help me)
			 */
			/*
			 * if (((Editor)
			 * compo.getParent().getParent().getParent().getParent(
			 * )).alignToGrid()) { newX -= (newX % 10); newY -= (newY % 10); }
			 */

			// set the calculated new location
			compo.setLocation(newX, newY);
			e.getComponent().repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{

	}
}
