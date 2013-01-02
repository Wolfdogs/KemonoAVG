package com.github.zoharwolf.kemono.avg;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.github.zoharwolf.kemono.util.graph.DrawableContainer;
import com.github.zoharwolf.kemono.util.graph.QuadrangleDrawableObject;
import com.github.zoharwolf.kemono.util.graph.Stage;
import com.github.zoharwolf.kemono.util.resource.fs.FsDirResource;
import com.github.zoharwolf.kemono.util.resource.zip.ZipDirResource;

public class KemonoAVG
{
	private Config config;
	private float fps;
	private int fpsCount;
	private long updateFpsTick;
	
	private Stage stage;
	
	
	public KemonoAVG(Config config)
	{
		this.config = config;
		
		try
		{
			Class.forName(FsDirResource.class.getName());
			Class.forName(ZipDirResource.class.getName());
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public void start() throws LWJGLException
	{
		Display.setDisplayMode(new DisplayMode(config.getScreenWidth(), config.getScreenHeight()));
		Display.setFullscreen(config.isFullScreen());
		Display.setTitle(config.getTitle());
		Display.setVSyncEnabled(config.isVSyncEnabled());
		Display.create();
		
		// init OpenGL
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 800, 600, 0, 1, -1);
		
		updateFpsTick = System.nanoTime();
		
		stage = new Stage();
		
		DrawableContainer container = new DrawableContainer();
		
		QuadrangleDrawableObject object = new QuadrangleDrawableObject(200.0f, 200.0f);
		object.setX(0.0f);
		object.setY(0.0f);
		object.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		container.add(object);
		
		object = new QuadrangleDrawableObject(100.0f, 100.0f);
		object.setX(0.0f);
		object.setY(0.0f);
		object.setColor(1.0f, 0.0f, 0.0f, 1.0f);
		container.add(object);
		
		container.setX(100);
		container.setY(100);
		container.setCenterX(100.0f);
		container.setCenterY(100.0f);
		// container.setRotationZ( 90.0f );
		// container.setScaleY( 2.0f );
		stage.add(container);
		
		while (!Display.isCloseRequested())
		{
			render();
			Display.update();
			
			container.setRotationZ(container.getRotationZ() + 1.0f);
			
			fpsCount++;
			
			long tick = System.nanoTime();
			long tickDiff = tick - updateFpsTick;
			if (tickDiff >= 200 * 1000 * 1000) // 500 ms
			{
				fps = (float) fpsCount / tickDiff * 1000 * 1000 * 1000.0f;
				Display.setTitle(config.getTitle() + " - FPS: " + fps);
				
				updateFpsTick = tick;
				fpsCount = 0;
			}
		}
		
		Display.destroy();
	}
	
	public void render()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		
		stage.render();
	}
}
