package ui.view;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class RootFrame
{
	JFrame rootFrame = new JFrame();
	public RootFrame()
	{
		setCurrentSystemLookAndFeel();
		
		rootFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		rootFrame.setTitle("Data Model Extractor");
		rootFrame.setLocationRelativeTo(null);
		rootFrame.setResizable(false);
		rootFrame.setSize(400, 300);
		
		Container inputFrame = new InputFrame();
		rootFrame.setContentPane(inputFrame);
		
		rootFrame.setVisible(true);
		
	}

	private void setCurrentSystemLookAndFeel() {
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}
	}


}
