package ui.view;

import java.awt.Graphics2D;
import java.awt.SplashScreen;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;



public class Splash
{
	public static void main(String[] args)
	{
		SplashScreen splash = SplashScreen.getSplashScreen();
		
		if (splash == null)
		{
			System.out.println("SplashScreen.getSplashScreen() returned null");
		}
		else
		{
			Graphics2D g = splash.createGraphics();
		}
		RootFrame rf = new RootFrame();

	}
	
	public static void setCurrentSystemLookAndFeel() {
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
