package ui.view;

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
		rootFrame.setExtendedState(rootFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		
		InputFrame inputFrame = new InputFrame();
		rootFrame.setContentPane(inputFrame);
		
		rootFrame.setVisible(true);
		
	}


	

}
