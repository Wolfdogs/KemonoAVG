package com.github.zoharwolf.kemono.util.graph;

import java.util.NavigableSet;
import java.util.concurrent.ConcurrentSkipListSet;

import org.lwjgl.opengl.GL11;

public class DrawableContainer implements Drawable
{
	private NavigableSet<Drawable> drawables;
	
	
	public DrawableContainer()
	{
		drawables = new ConcurrentSkipListSet<>();
	}

	@Override
	public void draw( GL11 gl )
	{
		for( Drawable drawable : drawables )
		{
			drawable.draw( gl );
		}
	}
}
