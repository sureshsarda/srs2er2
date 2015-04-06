package ui.view;

import java.awt.Graphics2D;
import java.awt.SplashScreen;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import erTagger.ERTagger;
import nlp.processing.StanfordProcessor;

public class Splash
{
	public static void main(String[] args)
	{

		Splash splash = new Splash();
		RootFrame rf = new RootFrame();

	}

	public Splash()
	{
		SplashScreen splash = SplashScreen.getSplashScreen();

		if (splash == null)
		{
			System.out.println("SplashScreen.getSplashScreen() returned null");
		}
		else
		{
			Graphics2D g2 = splash.createGraphics();

//			loadStanfordProcessor();
//			loadERTagger();

		}
	}

	public void loadERTagger()
	{
		try
		{
			Thread pro = new Thread(ERTagger.getInstance());
			pro.run();
			pro.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	public void loadStanfordProcessor()
	{
		try
		{
			Thread pro = new Thread(StanfordProcessor.getInstance());
			pro.run();
			pro.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public static void setCurrentSystemLookAndFeel()
	{
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
