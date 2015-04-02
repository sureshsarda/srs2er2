package ui.view;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class RootFrame
{
	public static JFrame rootFrame = new JFrame();
	public RootFrame()
	{
		Splash.setCurrentSystemLookAndFeel();
		
		rootFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		rootFrame.setTitle("Data Model Extractor");
		rootFrame.setLocationRelativeTo(null);
		rootFrame.setSize(400, 300);
		
		InputFrame inputFrame = new InputFrame();
		rootFrame.setContentPane(inputFrame);
		
		rootFrame.setVisible(true);
		
	}




}
