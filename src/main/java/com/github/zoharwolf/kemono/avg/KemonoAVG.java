package com.github.zoharwolf.kemono.avg;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class KemonoAVG
{
	private Config config;
	private float fps;
	private int fpsCount;
	private long updateFpsTick;
	
	
	public KemonoAVG( Config config )
	{
		this.config = config;
	}
	
	public void start() throws LWJGLException
	{
		Display.setDisplayMode( new DisplayMode(config.getScreenWidth(), config.getScreenHeight()) );
		Display.setFullscreen( config.isFullScreen() );
		Display.setTitle( config.getTitle() );
		Display.setVSyncEnabled( config.isVSyncEnabled() );
		Display.create();

		// init OpenGL
		GL11.glEnable( GL11.GL_BLEND );
		GL11.glBlendFunc( GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA );
		
		GL11.glEnable( GL11.GL_TEXTURE_2D );
		GL11.glClearColor( 0.0f, 0.0f, 0.0f, 0.0f );
		
		GL11.glMatrixMode( GL11.GL_PROJECTION );
		GL11.glLoadIdentity();
		GL11.glOrtho( 0, 800, 0, 600, 1, -1 );
		GL11.glMatrixMode( GL11.GL_MODELVIEW );
		
		updateFpsTick = System.nanoTime();
		
		while( !Display.isCloseRequested() )
		{
			render();
			Display.update();
			
			fpsCount++;
			
			long tick = System.nanoTime();
			long tickDiff = tick - updateFpsTick;
			if( tickDiff >= 200*1000*1000 )			// 500 ms
			{
				fps = (float)fpsCount / tickDiff * 1000 * 1000 * 1000.0f;
				Display.setTitle( config.getTitle() + " - FPS: " + fps );
				
				updateFpsTick = tick;
				fpsCount = 0;
			}
		}
		
		Display.destroy();
	}
	
	public void render()
	{
		GL11.glClear( GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT );
		
		GL11.glColor4f( 1.0f, 0.0f, 0.0f, 0.5f );
		GL11.glBegin( GL11.GL_QUADS );
		GL11.glVertex2f( 100, 100 );
		GL11.glVertex2f( 100+200, 100 );
		GL11.glVertex2f( 100+200, 100+200 );
		GL11.glVertex2f( 100, 100+200 );
		
		GL11.glEnd();
	}
}
