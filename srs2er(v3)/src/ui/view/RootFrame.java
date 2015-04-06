package ui.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;

import erTagger.ERTagger;

public class RootFrame
{
	public static JFrame rootFrame = new JFrame();
	public static ERTagger tagger;
	
	public RootFrame()
	{
		Splash.setCurrentSystemLookAndFeel();
		
		rootFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		rootFrame.setTitle("Data Model Extractor");
		rootFrame.setLocationRelativeTo(null);
		rootFrame.setSize(640, 480);
		repositionToCenter();
		
		
		InputFrame inputFrame = new InputFrame();
//		rootFrame.getContentPane().add(inputFrame, BorderLayout.CENTER);
		rootFrame.setContentPane(inputFrame);
		
		rootFrame.setVisible(true);
		
	}
	
	private void repositionToCenter() {
		Point loc = rootFrame.getLocation();
		Dimension size = rootFrame.getSize();
		
		Point newLoc = new Point();
		
		newLoc.x = loc.x - size.width / 2;
		newLoc.y = loc.y - size.height / 2;
		
		rootFrame.setLocation(newLoc);
	}


	

}
