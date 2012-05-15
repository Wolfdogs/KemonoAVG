package com.github.zoharwolf.kemono.util.graph;

import java.util.LinkedList;
import java.util.List;

import org.lwjgl.opengl.GL11;

/**
 * 可绘画对象的容器。<p>
 * 用于管理可绘画对象的渲染循序以及事件处理。
 */

public class DrawableContainer extends DrawableObject
{
	private List<Renderable> drawables;
	
	
	public DrawableContainer()
	{
		drawables = new LinkedList<>();
	}
	
	public void add( Renderable drawable )
	{
		drawables.add( drawable );
	}

	@Override
	public void onDraw()
	{
		for( Renderable drawable : drawables )
		{	
			GL11.glMatrixMode( GL11.GL_PROJECTION );
			GL11.glPushMatrix();
			drawable.render();
			GL11.glPopMatrix();
		}
	}
}
