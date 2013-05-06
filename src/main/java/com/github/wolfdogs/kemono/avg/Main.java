package com.github.wolfdogs.kemono.avg;

import org.lwjgl.LWJGLException;

public class Main
{
	public static void main(String[] args) throws LWJGLException
	{
		Config config = new Config();
		config.setScreenWidth(800);
		config.setScreenHeight(600);
		config.setFullScreen(false);
		config.setTitle("KemonoAVG");
		config.setVSyncEnabled(true);
		
		KemonoAVG kemonoAVG = new KemonoAVG(config);
		kemonoAVG.start();
	}
}
