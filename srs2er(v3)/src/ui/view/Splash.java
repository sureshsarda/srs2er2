package ui.view;

import java.awt.Graphics2D;
import java.awt.SplashScreen;

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

}
